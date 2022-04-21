package com.github.nam_younghoon.cryptotaxlibs;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public class TestKotlinLibrary {
    public static void main(String[] args) {
        CryptoTaxLibs cryptoTaxLibs = new CryptoTaxLibs();
        cryptoTaxLibs.initServerData(new ResultCallback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable throwable) {

            }
        });
    }
}
