package com.web3auth.tkey.ThresholdKey.Modules;

import androidx.annotation.Nullable;

import com.web3auth.tkey.RuntimeError;
import com.web3auth.tkey.ThresholdKey.Common.Result;
import com.web3auth.tkey.ThresholdKey.Common.ThresholdKeyCallback;
import com.web3auth.tkey.ThresholdKey.ThresholdKey;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public final class PrivateKeysModule {
    private PrivateKeysModule() {
        //Utility class
    }

    private static native boolean jniPrivateKeysModuleSetPrivateKey(ThresholdKey thresholdKey, @Nullable String key, String format, String curveN, RuntimeError error);

    private static native String jniPrivateKeysModuleGetPrivateKey(ThresholdKey thresholdKey, RuntimeError error);

    private static native String jniPrivateKeysModuleGetPrivateKeyAccounts(ThresholdKey thresholdKey, RuntimeError error);

    /**
     * Sets an extra private key on an existing ThresholdKey object.
     * @param thresholdKey The threshold key to act on.
     * @param key The private key to set in hexadecimal format. Optional, will be generated on the Secp256k1 curve if not supplied.
     * @param format "secp256k1n" is currently the only cupported format.
     * @param callback The method which the result will be sent to
     * @see ThresholdKeyCallback
     */
    public static void setPrivateKey(ThresholdKey thresholdKey, @Nullable String key, String format, ThresholdKeyCallback<Boolean> callback) {
        thresholdKey.executor.execute(() -> {
            try {
                Result<Boolean> result = setPrivateKey(thresholdKey, key, format);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<Boolean> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }

    private static Result<Boolean> setPrivateKey(ThresholdKey thresholdKey, @Nullable String key, String format) {
        try {
            RuntimeError error = new RuntimeError();
            boolean result = jniPrivateKeysModuleSetPrivateKey(thresholdKey, key, format, thresholdKey.curveN, error);
            if (error.code != 0) {
                throw new Exception(error);
            }
            return new Result.Success<>(result);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    /**
     * Returns stored extra private keys on an existing ThresholdKey object.
     * @param thresholdKey The threshold key to act on.
     * @throws RuntimeError Indicates invalid threshold key.
     * @return String
     */
    public static String getPrivateKeys(ThresholdKey thresholdKey) throws RuntimeError {
        RuntimeError error = new RuntimeError();
        String result = jniPrivateKeysModuleGetPrivateKey(thresholdKey, error);
        if (error.code != 0) {
            throw error;
        }
        return result;
    }

    /**
     * Returns accounts of stored extra private keys on an existing ThresholdKey object.
     * @param thresholdKey The threshold key to act on.
     * @throws RuntimeError Indicates invalid threshold key.
     * @throws JSONException Indicated data in the object is malformed.
     * @return String
     */
    public static ArrayList<String> getPrivateKeyAccounts(ThresholdKey thresholdKey) throws RuntimeError, JSONException {
        RuntimeError error = new RuntimeError();
        String result = jniPrivateKeysModuleGetPrivateKeyAccounts(thresholdKey, error);
        if (error.code != 0) {
            throw error;
        }
        ArrayList<String> array = new ArrayList<>();
        JSONArray json = new JSONArray(result);
        for (int i = 0; i < json.length(); i++) {
            String value = json.getString(i);
            array.add(value);
        }
        return array;
    }
}
