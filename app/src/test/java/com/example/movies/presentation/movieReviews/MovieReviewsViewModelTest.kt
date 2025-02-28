package com.example.movies.presentation.movieReviews

import androidx.lifecycle.SavedStateHandle
import com.example.movies.domain.usecase.GetMovieDetailsUseCase
import com.example.movies.domain.usecase.GetMovieReviewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before

class MovieReviewsViewModelTest {
    // Use TestCoroutineDispatcher for coroutine testing
    private val testDispatcher = TestCoroutineDispatcher()

    // Mock dependencies
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase = mockk()
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = mockk()

    // ViewModel under test
    private lateinit var viewModel: MovieReviewsViewModel

    @Before
    fun setup() {
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Initialize the ViewModel with a mock SavedStateHandle
        val savedStateHandle = mockk<SavedStateHandle>()
        coEvery { savedStateHandle.get<Int>(any()) } returns MOVIE_ID

        viewModel = MovieReviewsViewModel(
            savedStateHandle = savedStateHandle,
            getMovieReviewsUseCase = getMovieReviewsUseCase,
            getMovieDetailsUseCase = getMovieDetailsUseCase
        )
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `init should load movie reviews and details successfully`() = runBlockingTest {
        // Arrange
        val movieDetails = MovieDetails(posterPath = "poster_path", videoPreviewPath = "video_path")
        val movieReviews = MovieReviews(
            amountOfReviews = 10,
            reviews = listOf(Review(id = "1", author = "Author", content = "Content")),
            totalPages = 5,
            movieId = MOVIE_ID
        )

        coEvery { getMovieDetailsUseCase(any()) } returns Result.Success(movieDetails)
        coEvery { getMovieReviewsUseCase(any()) } returns Result.Success(movieReviews)

        // Act
        // ViewModel initialization triggers `updateMovieReviews`

        // Assert
        val uiState = viewModel.uiState.value
        assert(uiState is MovieReviewsUiState.Success)
        val successState = uiState as MovieReviewsUiState.Success
        assertEquals(movieReviews, successState.data)
        assertEquals("poster_path", successState.posterPath)
        assertEquals("video_path", successState.videoPreviewPath)
    }

    @Test
    fun `init should handle error when loading movie reviews or details fails`() = runBlockingTest {
        // Arrange
        coEvery { getMovieDetailsUseCase(any()) } returns Result.Error("Error loading details")
        coEvery { getMovieReviewsUseCase(any()) } returns Result.Error("Error loading reviews")

        // Act
        // ViewModel initialization triggers `updateMovieReviews`

        // Assert
        val uiState = viewModel.uiState.value
        assert(uiState is MovieReviewsUiState.Error)
        val errorState = uiState as MovieReviewsUiState.Error
        assertEquals("Error loading reviews", errorState.errorMessage)
    }

    @Test
    fun `getMoreReviews should load more reviews successfully`() = runBlockingTest {
        // Arrange
        val initialReviews = MovieReviews(
            amountOfReviews = 10,
            reviews = listOf(Review(id = "1", author = "Author", content = "Content")),
            totalPages = 2,
            movieId = MOVIE_ID
        )
        val moreReviews = MovieReviews(
            amountOfReviews = 20,
            reviews = listOf(Review(id = "2", author = "Author2", content = "Content2")),
            totalPages = 2,
            movieId = MOVIE_ID
        )

        coEvery { getMovieDetailsUseCase(any()) } returns Result.Success(
            MovieDetails(
                posterPath = "poster_path", videoPreviewPath = "video_path"
            )
        )
        coEvery { getMovieReviewsUseCase(any()) } returnsMany listOf(
            Result.Success(initialReviews), Result.Success(moreReviews)
        )

        // Act
        viewModel.getMoreReviews()

        // Assert
        val uiState = viewModel.uiState.value
        assert(uiState is MovieReviewsUiState.Success)
        val successState = uiState as MovieReviewsUiState.Success
        assertEquals(moreReviews, successState.data)
    }

    @Test
    fun `onErrorShowed should clear error message`() = runBlockingTest {
        // Arrange
        coEvery { getMovieDetailsUseCase(any()) } returns Result.Error("Error loading details")
        coEvery { getMovieReviewsUseCase(any()) } returns Result.Error("Error loading reviews")

        // Act
        viewModel.onErrorShowed()

        // Assert
        val uiState = viewModel.uiState.value
        assert(uiState is MovieReviewsUiState.Error)
        val errorState = uiState as MovieReviewsUiState.Error
        assertEquals(null, errorState.errorMessage)
    }

    companion object {
        private const val MOVIE_ID = 123
    }
}
