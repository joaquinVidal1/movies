package com.example.movies.di

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext appContext: Context): MoviesDatabase {
            return MyStoreDatabase.getMyStoreDatabase(appContext)
        }

        @Provides
        fun provideMoviesDao(database: MyStoreDatabase): CartDao {
            return database.cartDao
        }

}