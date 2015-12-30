package open.sam.network;

public class IPChangedOrUnavailableEvent {
    private boolean _isNetworkAvailable;
    private NetworkType _networkType;

    public IPChangedOrUnavailableEvent(boolean isNetworkAvailable, NetworkType networkType) {
        _isNetworkAvailable = isNetworkAvailable;
        _networkType = networkType;
    }

    public boolean isNetworkAvailable() {
        return _isNetworkAvailable;
    }

    public NetworkType getNetworkType() {
        return _networkType;
    }

    @Override
    public String toString() {
        return "IPChangedOrUnavailableEvent{" +
                "_isNetworkAvailable=" + _isNetworkAvailable +
                '}';
    }
}