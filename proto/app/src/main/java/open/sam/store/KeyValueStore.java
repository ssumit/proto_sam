package open.sam.store;

public interface KeyValueStore {
    String getString(String key);

    boolean getBoolean(String key);

    long getLong(String key);

    int getInt(String key);

    void putString(String key, String value);

    void putBoolean(String key, boolean value);

    void putLong(String key, long value);

    void putInt(String key, int value);

    boolean contains(String key);

    void remove(String key);

    void clearAll();
}