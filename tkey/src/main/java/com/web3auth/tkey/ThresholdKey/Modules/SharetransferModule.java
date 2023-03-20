package com.web3auth.tkey.ThresholdKey.Modules;

import androidx.annotation.Nullable;

import com.web3auth.tkey.RuntimeError;
import com.web3auth.tkey.ThresholdKey.Common.Result;
import com.web3auth.tkey.ThresholdKey.Common.ShareStore;
import com.web3auth.tkey.ThresholdKey.Common.ThresholdKeyCallback;
import com.web3auth.tkey.ThresholdKey.ShareTransferStore;
import com.web3auth.tkey.ThresholdKey.ThresholdKey;

import org.json.JSONArray;

import java.util.ArrayList;

public final class SharetransferModule {
    private SharetransferModule() {
        //Utility class
    }

    private static native String jniSharetransferModuleRequestNewShare(ThresholdKey thresholdKey, String agent, String indexes, String curveN, RuntimeError error);

    private static native void jniSharetransferModuleAddCustomInfoToRequest(ThresholdKey thresholdKey, String encPubKeyX, String customInfo, String curveN, RuntimeError error);

    private static native String jniSharetransferModuleLookForRequest(ThresholdKey thresholdKey, RuntimeError error);

    private static native void jniSharetransferModuleApproveRequest(ThresholdKey thresholdKey, String encPubKeyX, @Nullable ShareStore shareStore, String curveN, RuntimeError error);

    private static native void jniSharetransferModuleApproveRequestWithShareIndex(ThresholdKey thresholdKey, String encPubKeyX, String indexes, String curveN, RuntimeError error);

    private static native long jniSharetransferModuleGetStore(ThresholdKey thresholdKey, RuntimeError error);

    private static native boolean jniSharetransferModuleSetStore(ThresholdKey thresholdKey, ShareTransferStore shareStore, String curveN, RuntimeError error);

    private static native boolean jniSharetransferModuleDeleteStore(ThresholdKey thresholdKey, String encPubKeyX, String curveN, RuntimeError error);

    private static native String jniSharetransferModuleGetCurrentEncryptionKey(ThresholdKey thresholdKey, RuntimeError error);

    private static native long jniSharetransferModuleRequestStatusCheck(ThresholdKey thresholdKey, String encPubKeyX, boolean deleteOnCompletion, String curveN, RuntimeError error);

    private static native void jniSharetransferModuleCleanupRequest(ThresholdKey thresholdKey, RuntimeError error);

    public static void requestNewShare(ThresholdKey thresholdKey, String userAgent, String availableShareIndexes, ThresholdKeyCallback<String> callback) {
        thresholdKey.executor.execute(() -> {
            try {
                Result<String> result = requestNewShare(thresholdKey, userAgent, availableShareIndexes);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<String> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }

    private static Result<String> requestNewShare(ThresholdKey thresholdKey, String userAgent, String availableShareIndexes) {
        try {
            RuntimeError error = new RuntimeError();
            String result = jniSharetransferModuleRequestNewShare(thresholdKey, userAgent, availableShareIndexes, thresholdKey.curveN, error);
            if (error.code != 0) {
                throw new Exception(error);
            }
            return new Result.Success<>(result);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public static void addCustomInfoToRequest(ThresholdKey thresholdKey, String encPubKeyX, String customInfo, ThresholdKeyCallback<Boolean> callback) {
        thresholdKey.executor.execute(() -> {
            try {
                Result<Boolean> result = addCustomInfoToRequest(thresholdKey, encPubKeyX, customInfo);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<Boolean> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }

    private static Result<Boolean> addCustomInfoToRequest(ThresholdKey thresholdKey, String encPubKeyX, String customInfo) {
        try {
            RuntimeError error = new RuntimeError();
            jniSharetransferModuleAddCustomInfoToRequest(thresholdKey, encPubKeyX, customInfo, thresholdKey.curveN, error);
            if (error.code != 0) {
                throw new Exception(error);
            }
            return new Result.Success<>(true);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public static void lookForRequest(ThresholdKey thresholdKey, ThresholdKeyCallback<ArrayList<String>> callback) {
        thresholdKey.executor.execute(() -> {
            try {
                Result<ArrayList<String>> result = lookForRequest(thresholdKey);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<ArrayList<String>> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }

    private static Result<ArrayList<String>> lookForRequest(ThresholdKey thresholdKey) {
        try {
            RuntimeError error = new RuntimeError();
            String result = jniSharetransferModuleLookForRequest(thresholdKey, error);
            if (error.code != 0) {
                throw new Exception(error);
            }
            ArrayList<String> array = new ArrayList<>();
            JSONArray json = new JSONArray(result);
            for (int i = 0; i < json.length(); i++) {
                String value = json.getString(i);
                array.add(value);
            }
            return new Result.Success<>(array);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public static void approveRequest(ThresholdKey thresholdKey, String encPubKeyX, @Nullable ShareStore store, ThresholdKeyCallback<Boolean> callback) {
        thresholdKey.executor.execute(() -> {
            try {
                Result<Boolean> result = approveRequest(thresholdKey, encPubKeyX, store);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<Boolean> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }

    private static Result<Boolean> approveRequest(ThresholdKey thresholdKey, String encPubKeyX, @Nullable ShareStore store) {
        try {
            RuntimeError error = new RuntimeError();
            jniSharetransferModuleApproveRequest(thresholdKey, encPubKeyX, store, thresholdKey.curveN, error);
            if (error.code != 0) {
                throw new Exception(error);
            }
            return new Result.Success<>(true);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public static void approveRequestWithShareIndex(ThresholdKey thresholdKey, String encPubKeyX, String shareIndex, ThresholdKeyCallback<Boolean> callback) {
        thresholdKey.executor.execute(() -> {
            try {
                Result<Boolean> result = approveRequestWithShareIndex(thresholdKey, encPubKeyX, shareIndex);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<Boolean> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }

    private static Result<Boolean> approveRequestWithShareIndex(ThresholdKey thresholdKey, String encPubKeyX, String shareIndex) {
        try {
            RuntimeError error = new RuntimeError();
            jniSharetransferModuleApproveRequestWithShareIndex(thresholdKey, encPubKeyX, shareIndex, thresholdKey.curveN, error);
            if (error.code != 0) {
                throw new Exception(error);
            }
            return new Result.Success<>(true);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public static void getStore(ThresholdKey thresholdKey, ThresholdKeyCallback<ShareTransferStore> callback) {
        thresholdKey.executor.execute(() -> {
            try {
                Result<ShareTransferStore> result = getStore(thresholdKey);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<ShareTransferStore> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }

    private static Result<ShareTransferStore> getStore(ThresholdKey thresholdKey) {
        try {
            RuntimeError error = new RuntimeError();
            long result = jniSharetransferModuleGetStore(thresholdKey, error);
            if (error.code != 0) {
                throw new Exception(error);
            }
            return new Result.Success<>(new ShareTransferStore(result));
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public static void setStore(ThresholdKey thresholdKey, ShareTransferStore store, ThresholdKeyCallback<Boolean> callback) {
        thresholdKey.executor.execute(() -> {
            try {
                Result<Boolean> result = setStore(thresholdKey, store);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<Boolean> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }

    private static Result<Boolean> setStore(ThresholdKey thresholdKey, ShareTransferStore store) {
        try {
            RuntimeError error = new RuntimeError();
            Boolean result = jniSharetransferModuleSetStore(thresholdKey, store, thresholdKey.curveN, error);
            if (error.code != 0) {
                throw new Exception(error);
            }
            return new Result.Success<>(result);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public static void deleteStore(ThresholdKey thresholdKey, String encPubKeyX, ThresholdKeyCallback<Boolean> callback) {
        thresholdKey.executor.execute(() -> {
            try {
                Result<Boolean> result = deleteStore(thresholdKey, encPubKeyX);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<Boolean> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }

    private static Result<Boolean> deleteStore(ThresholdKey thresholdKey, String encPubKeyX) {
        try {
            RuntimeError error = new RuntimeError();
            Boolean result = jniSharetransferModuleDeleteStore(thresholdKey, encPubKeyX, thresholdKey.curveN, error);
            if (error.code != 0) {
                throw new Exception(error);
            }
            return new Result.Success<>(result);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public static String getCurrentEncryptionKey(ThresholdKey thresholdKey) throws RuntimeError {
        RuntimeError error = new RuntimeError();
        String result = jniSharetransferModuleGetCurrentEncryptionKey(thresholdKey, error);
        if (error.code != 0 && error.code != 6) {
            throw error;
        }
        return result;
    }

    public static void requestStatusCheck(ThresholdKey thresholdKey, String encPubKeyX, Boolean deleteRequestOnCompletion, ThresholdKeyCallback<ShareStore> callback) {
        thresholdKey.executor.execute(() -> {
            try {
                Result<ShareStore> result = requestStatusCheck(thresholdKey, encPubKeyX, deleteRequestOnCompletion);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<ShareStore> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }

    private static Result<ShareStore> requestStatusCheck(ThresholdKey thresholdKey, String encPubKeyX, Boolean deleteRequestOnCompletion) {
        try {
            RuntimeError error = new RuntimeError();
            long result = jniSharetransferModuleRequestStatusCheck(thresholdKey, encPubKeyX, deleteRequestOnCompletion, thresholdKey.curveN, error);
            if (error.code != 0) {
                throw new Exception(error);
            }
            return new Result.Success<>(new ShareStore(result));
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public static void cleanupRequest(ThresholdKey thresholdKey) throws RuntimeError {
        RuntimeError error = new RuntimeError();
        jniSharetransferModuleCleanupRequest(thresholdKey, error);
        if (error.code != 0) {
            throw error;
        }
    }
}
