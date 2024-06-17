//package com.example.movies.presentation.home.IntentModule
//
//private static final String ACTION_STATUS_BROADCAST_RESPONSE = "com.dejamobile.cbp.sps.STATUS_BROADCAST_RESPONSE";
//private static final String ACTION_RESET_BROADCAST_RESPONSE = "com.dejamobile.cbp.sps.RESET_BROADCAST_RESPONSE";
//
//private BroadcastReceiver statusBroadcastReceiver;
//private Callback statusResponseCallback;
//
//private BroadcastReceiver resetBroadcastReceiver;
//private Callback resetResponseCallback;
//
//public IntentModule(ReactApplicationContext reactContext) {
//    super(reactContext);
//    initBroadcastReceiver();
//    initResetBroadcastReceiver();
//}
//
//@Override
//public String getName() {
//    return "IntentModule";
//}
//
//@ReactMethod
//public void sendStatusBroadcast(String responseAction, final Callback callback) {
//    statusResponseCallback = callback;
//
//    String packageName = "com.dejamobile.cbp.sps.app";
//    String className = "com.dejamobile.cbp.sps.app.broadcast.StatusBroadcastReceiver";
//
//    Intent statusIntent = new Intent();
//    statusIntent.setAction(responseAction);
//    statusIntent.setClassName(packageName, className);
//    statusIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//    statusIntent.putExtra("ResponseAction", ACTION_STATUS_BROADCAST_RESPONSE);
//
//    getReactApplicationContext().sendBroadcast(statusIntent);
//}
//
//@ReactMethod
//public void sendResetBroadcast(String responseAction, final Callback callback) {
//    resetResponseCallback = callback;
//
//    String packageName = "com.dejamobile.cbp.sps.app";
//    String className = "com.dejamobile.cbp.sps.app.broadcast.ResetBroadcastReceiver";
//
//    Intent resetIntent = new Intent();
//    resetIntent.setAction(responseAction);
//    resetIntent.setClassName(packageName, className);
//    resetIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//    resetIntent.putExtra("ResponseAction", ACTION_RESET_BROADCAST_RESPONSE);
//
//    getReactApplicationContext().sendBroadcast(resetIntent);
//}
//
//private void initBroadcastReceiver() {
//    statusBroadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(ACTION_STATUS_BROADCAST_RESPONSE)) {
//                Bundle extras = intent.getExtras();
//                if (extras != null) {
//                    String responseInfo = extras.getString("Info");
//                    String responseStatus = extras.getString("Status");
//
//                    if (statusResponseCallback != null) {
//                        statusResponseCallback.invoke(responseInfo, responseStatus);
//                    }
//                }
//            }
//        }
//    };
//
//
//    // Register the BroadcastReceiver to listen for the response broadcast
//    IntentFilter statusFilter = new IntentFilter(ACTION_STATUS_BROADCAST_RESPONSE);
//    getReactApplicationContext().registerReceiver(statusBroadcastReceiver, statusFilter);
//}
//
//private void initResetBroadcastReceiver() {
//    // Initialize the reset BroadcastReceiver
//    resetBroadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(ACTION_RESET_BROADCAST_RESPONSE)) {
//                Bundle extras = intent.getExtras();
//                if (extras != null) {
//                    String responseAction = extras.getString("ResponseAction");
//
//                    if (resetResponseCallback != null) {
//                        statusResponseCallback.invoke(responseAction);
//                    }
//                }
//            }
//        }
//    };
//
//    IntentFilter resetFilter = new IntentFilter(ACTION_RESET_BROADCAST_RESPONSE);
//    getReactApplicationContext().registerReceiver(resetBroadcastReceiver, resetFilter);
//}
//
//@Override
//public void onCatalystInstanceDestroy() {
//    super.onCatalystInstanceDestroy();
//    // Unregister the BroadcastReceivers when they're no longer needed
//    if (statusBroadcastReceiver != null) {
//        getReactApplicationContext().unregisterReceiver(statusBroadcastReceiver);
//        statusBroadcastReceiver = null;
//    }
//    if (resetBroadcastReceiver != null) {
//        getReactApplicationContext().unregisterReceiver(resetBroadcastReceiver);
//        resetBroadcastReceiver = null;
//    }
//}