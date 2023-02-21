package com.web3auth.tkey;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.web3auth.tkey.ThresholdKey.Common.PrivateKey;
import com.web3auth.tkey.ThresholdKey.KeyDetails;
import com.web3auth.tkey.ThresholdKey.KeyReconstructionDetails;
import com.web3auth.tkey.ThresholdKey.ServiceProvider;
import com.web3auth.tkey.ThresholdKey.StorageLayer;
import com.web3auth.tkey.ThresholdKey.ThresholdKey;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class tkeyThresholdKeyTest {
    static {
        System.loadLibrary("tkey-native");
    }

    @Test
    public void basic_threshold_key_reconstruct() {
        try {
            PrivateKey postboxKey = PrivateKey.generate();
            StorageLayer storageLayer = new StorageLayer(false, "https://metadata.tor.us", 2);
            ServiceProvider serviceProvider = new ServiceProvider(false, postboxKey.hex);
            ThresholdKey thresholdKey = new ThresholdKey(null, null, storageLayer, serviceProvider, null, null, false, false);
            PrivateKey key = PrivateKey.generate();
            KeyDetails details = thresholdKey.initialize(key.hex, null, false, false);
            assertNotNull(details.getPublicKeyPoint().getAsCompressedPublicKey("elliptic-compressed"));
            KeyReconstructionDetails reconstruct_details = thresholdKey.reconstruct();
            assertNotEquals(reconstruct_details.getKey().length(), 0);
        } catch (RuntimeError e) {
            fail();
        }
    }

    @Test
    public void threshold_key_multi_instance() {
        try {
            PrivateKey postboxKey = PrivateKey.generate();
            PrivateKey postboxKey2 = PrivateKey.generate();
            StorageLayer storageLayer = new StorageLayer(false, "https://metadata.tor.us", 2);
            ServiceProvider serviceProvider = new ServiceProvider(false, postboxKey.hex);
            StorageLayer storageLayer2 = new StorageLayer(false, "https://metadata.tor.us", 2);
            ServiceProvider serviceProvider2 = new ServiceProvider(false, postboxKey2.hex);
            ThresholdKey thresholdKey = new ThresholdKey(null, null, storageLayer, serviceProvider, null, null, false, false);
            ThresholdKey thresholdKey2 = new ThresholdKey(null, null, storageLayer2, serviceProvider2, null, null, false, false);
            PrivateKey key = PrivateKey.generate();
            PrivateKey key2 = PrivateKey.generate();
            KeyDetails details = thresholdKey.initialize(key.hex, null, false, false);
            KeyDetails details2 = thresholdKey2.initialize(key2.hex, null, false, false);
            assertNotNull(details.getPublicKeyPoint().getAsCompressedPublicKey("elliptic-compressed"));
            assertNotNull(details2.getPublicKeyPoint().getAsCompressedPublicKey("elliptic-compressed"));
            KeyReconstructionDetails reconstruct_details = thresholdKey.reconstruct();
            KeyReconstructionDetails reconstruct_details2 = thresholdKey2.reconstruct();
            assertNotEquals(reconstruct_details.getKey().length(), 0);
            assertNotEquals(reconstruct_details2.getKey().length(), 0);
            assertNotEquals(reconstruct_details.getKey(), reconstruct_details2.getKey());
        } catch (RuntimeError e) {
            fail();
        }
    }
}