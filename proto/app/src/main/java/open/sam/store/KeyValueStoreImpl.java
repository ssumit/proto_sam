package open.sam.store;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class KeyValueStoreImpl implements KeyValueStore {
    private final SharedPreferences _store;

    @Inject
    public KeyValueStoreImpl(Context context, String storeName) {
        this._store = context.getSharedPreferences(storeName, 0);
    }

    public String getString(String key) {
        return this._store.getString(key, null);
    }

    public boolean getBoolean(String key) {
        return this._store.getBoolean(key, false);
    }

    public long getLong(String key) {
        return this._store.getLong(key, 0L);
    }

    public int getInt(String key) {
        return this._store.getInt(key, 0);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = this._store.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = this._store.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = this._store.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        this.putIntInternal(key, value);
    }

    public boolean contains(String key) {
        return this._store.contains(key);
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = this._store.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clearAll() {
        SharedPreferences.Editor editor = this._store.edit();
        editor.clear();
        editor.apply();
    }

    private void putIntInternal(String key, int value) {
        SharedPreferences.Editor editor = this._store.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}