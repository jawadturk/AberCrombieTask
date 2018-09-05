/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.abercrombietask.viewmodel;


import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Observable;

public class WebViewViewModel extends Observable {

    private ObservableInt pageLoadingProgress;
    private String mUrl;

    public WebViewViewModel(String url) {
        this.mUrl = url;
        pageLoadingProgress = new ObservableInt(View.VISIBLE);
    }

    public String getUrl() {
        return mUrl;
    }

    public ObservableInt getPageLoadingProgress() {
        return pageLoadingProgress;
    }

    @BindingAdapter({"loadUrl"})
    public static void loadUrl(WebView view, String url) {
        view.loadUrl(url);
    }


    @BindingAdapter({"setWebViewClient"})
    public static void setWebViewClient(WebView view, WebViewClient client) {
        view.setWebViewClient(client);
    }

    private class Client extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request,
                                    WebResourceError error) {
            super.onReceivedError(view, request, error);
            pageLoadingProgress.set(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pageLoadingProgress.set(View.GONE);
        }
    }

    public WebViewClient getWebViewClient() {
        return new Client();
    }


}