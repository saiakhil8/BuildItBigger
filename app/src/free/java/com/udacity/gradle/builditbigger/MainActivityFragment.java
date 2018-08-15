/*
 * Copyright (c) 2018. VENKATA SAIAKHIL KUMAR VEMULA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private AdRequest adRequest;
    private InterstitialAd interstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = root.findViewById(R.id.adView);
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        initiateInterstitial();
        loadAd();


        return root;
    }

    private void initiateInterstitial(){
        interstitialAd = new InterstitialAd(getActivity().getApplicationContext());
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                loadAd();
                super.onAdClosed();
            }
        });
    }

    private void loadAd(){

        if (interstitialAd == null) initiateInterstitial();
        if (adRequest==null){
            adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
        }
        interstitialAd.loadAd(adRequest);
    }

    public void showAd(){
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }else loadAd();
    }
}
