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

package com.example.abercrombietask;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.abercrombietask.data.DataService;
import com.example.abercrombietask.di.AberCrombieApplicationComponent;
import com.example.abercrombietask.di.ContextModule;
import com.example.abercrombietask.di.DaggerAberCrombieApplicationComponent;


import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AberCrombieApplication extends Application {

    private static final Object TAG = AberCrombieApplication.class.getSimpleName();
    private DataService dataService;
    private Scheduler scheduler;
    private RequestQueue mRequestQueue;
    private AberCrombieApplicationComponent component;


    private static AberCrombieApplication get(Context context) {
        return (AberCrombieApplication) context.getApplicationContext();
    }


    public static AberCrombieApplication create(Context context) {
        return AberCrombieApplication.get(context);
    }

    public DataService getDataService() {

        return dataService;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /* This function is used to send the path needed to talk with the server */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /* This function is used to send the path needed to talk with the server without sending a tag */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        component = DaggerAberCrombieApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        dataService = component.getDataService();
    }
}

