package shinnil.godot.plugin.android.godotadmob;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.godotengine.godot.GodotLib;

public class Interstitial {
    private InterstitialAd interstitialAd = null; // Interstitial object

    public Interstitial(final String id, final AdRequest adRequest, final Activity activity, final int instanceId) {
        interstitialAd = new InterstitialAd(activity);
        interstitialAd.setAdUnitId(id);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.w("godot", "AdMob: onAdLoaded");
                GodotLib.calldeferred(instanceId, "_on_interstitial_loaded", new Object[]{});
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.w("godot", "AdMob: onAdFailedToLoad(int errorCode) - error code: " + Integer.toString(errorCode));
                GodotLib.calldeferred(instanceId, "_on_interstitial_failed_to_load", new Object[]{errorCode});
            }

            @Override
            public void onAdOpened() {
                Log.w("godot", "AdMob: onAdOpened()");
            }

            @Override
            public void onAdLeftApplication() {
                Log.w("godot", "AdMob: onAdLeftApplication()");
            }

            @Override
            public void onAdClosed() {
                GodotLib.calldeferred(instanceId, "_on_interstitial_close", new Object[]{});
                interstitialAd.loadAd(adRequest);
                Log.w("godot", "AdMob: onAdClosed");
            }
        });

        interstitialAd.loadAd(adRequest);
    }

    public void show() {
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Log.w("w", "AdMob: showInterstitial - interstitial not loaded");
        }
    }
}