package com.example.alefmobitech.appinsights;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by supriya on 06-09-2017.
 */

public class User_viwe extends AppCompatActivity {

    WebView user_viwe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_viwe);

        user_viwe = (WebView)findViewById(R.id.user_viwe);


        //user_viwe.loadUrl("http://182.75.54.50/dashboard/");
        String url = "http://182.75.54.50/dashboard/Button.php?var=altbalaji";
        user_viwe.loadUrl(url);
        // wb.getVisibility();
        user_viwe.getCertificate();
        WebSettings webSettings = user_viwe.getSettings();
        webSettings.setJavaScriptEnabled(true);
        user_viwe.getSettings().setDomStorageEnabled(true);
        user_viwe.setWebViewClient(new WebViewClient());
        //webSettings.getAllowUniversalAccessFromFileURLs();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        //webSettings.se


        //
        // webSettings.setDisplayZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUserAgentString("Android");

        user_viwe.getSettings().setDomStorageEnabled(true);
        user_viwe.setWebViewClient(new WebViewClient());
        user_viwe.setWebChromeClient(new WebChromeClient(){
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });



    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logout:
                //logout code
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
