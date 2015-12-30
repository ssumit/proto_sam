package open.sam.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import open.sam.SamApplication;

public class ConnectivityReceiver extends BroadcastReceiver {

    @Inject
    IPManager ipManager;
    Logger logger = Logger.getLogger(ConnectivityReceiver.class.getSimpleName());

    @Override
    public void onReceive(Context context, Intent intent) {
        ipManager = SamApplication.getComponent().provideIPManager();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        logger.log(Level.INFO, "is connected: " + isConnected);
        final IPManager.NetworkState networkState = isConnected ?
                IPManager.NetworkState.CONNECTED : IPManager.NetworkState.DISCONNECTED;
        ipManager.onPossibleNetworkChange(networkState);
    }
}
