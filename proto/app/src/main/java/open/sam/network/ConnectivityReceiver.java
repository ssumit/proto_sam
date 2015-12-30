package open.sam.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

public class ConnectivityReceiver extends BroadcastReceiver {

    @Inject
    IPManager ipManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        final IPManager.NetworkState networkState = isConnected ?
                IPManager.NetworkState.CONNECTED : IPManager.NetworkState.DISCONNECTED;
        ipManager.onPossibleNetworkChange(networkState);
    }
}
