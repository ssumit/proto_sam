package open.sam.network;

public enum NetworkType {
    WIFI("wifi"), _2G("cellular"), _3G("cellular-3g"), _4G(" cellular-4g"), UNKNOWN("unknown");

    private final String _name;

    private NetworkType(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }
}
