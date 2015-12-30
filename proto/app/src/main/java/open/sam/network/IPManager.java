package open.sam.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import de.greenrobot.event.EventBus;

@Singleton
public class IPManager {
    private final Context _context;
    private final ExecutorService executorService;
    private String _activeIPAddress;
    private NetworkState _currentState;
    private final EventBus _eventBus;
    private final IPHelper _ipHelper;

    public enum NetworkState {
        CONNECTED, DISCONNECTED
    }

    public IPManager(Context context, ExecutorService executorService) {
        _context = context;
        this.executorService = executorService;
        _ipHelper = new IPHelper();
        _activeIPAddress = getLatestIPAddress();
        _currentState = getCurrentNetworkState();
        _eventBus = EventBus.getDefault();
    }

    private String getLatestIPAddress() {
        String address = null;
        List<String> ipList = _ipHelper.getIPAddress();
        if (ipList.size() > 0) {
            address = ipList.get(0);
        }
        return address;
    }

    public void onPossibleNetworkChange(final NetworkState networkState) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (hasNetworkChanged(networkState)) {
                    onNetworkStateChange(networkState);
                }
            }
        });
    }

    private boolean hasNetworkChanged(NetworkState networkState) {
        if (networkState != _currentState) {
            return true;
        } else if (_activeIPAddress != null && !_activeIPAddress.equals(getLatestIPAddress())) {
            return true;
        }
        return false;
    }


    private void onNetworkStateChange(NetworkState newNetworkState) {
        String newIPAddress = getLatestIPAddress();
        boolean fireIPChangeEvent = false;
        if (newNetworkState == NetworkState.DISCONNECTED) {
            if (_currentState == NetworkState.CONNECTED) {
                _activeIPAddress = null;
                fireIPChangeEvent = true;
            }
        } else {
            if (newIPAddress != null && (_activeIPAddress == null || !newIPAddress.equals(_activeIPAddress))) {
                _activeIPAddress = newIPAddress;
                fireIPChangeEvent = true;
            }
        }
        _currentState = newNetworkState;
        if (fireIPChangeEvent) {
            _eventBus.post(new IPChangedOrUnavailableEvent(isNetworkAvailable(), getCurrentNetworkType()));
        }
    }

    public boolean isNetworkAvailable() {
        return _activeIPAddress != null;
    }

    public NetworkType getCurrentNetworkType() {
        return _ipHelper.getNetworkType(_context);
    }

    private NetworkState getCurrentNetworkState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetworkState.DISCONNECTED;
        } else {
            return networkInfo.isConnected() ? NetworkState.CONNECTED : NetworkState.DISCONNECTED;
        }
    }
}