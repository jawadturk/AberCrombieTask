/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.abercrombietask.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.abercrombietask.R;
import com.example.abercrombietask.databinding.ActivityWebViewBinding;
import com.example.abercrombietask.viewmodel.WebViewViewModel;

import java.util.Observable;
import java.util.Observer;


public class WebViewActivity extends AppCompatActivity implements Observer {

    private static final String EXTRA_URL = "EXTRA_URL";

    private ActivityWebViewBinding activityWebViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWebViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        getExtrasFromIntent();
    }

    public static Intent launchWebView(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        return intent;
    }


    private void getExtrasFromIntent() {
        String url = (String) getIntent().getSerializableExtra(EXTRA_URL);
        WebViewViewModel webViewViewModel = new WebViewViewModel(url);
        activityWebViewBinding.setViewModel(webViewViewModel);
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
