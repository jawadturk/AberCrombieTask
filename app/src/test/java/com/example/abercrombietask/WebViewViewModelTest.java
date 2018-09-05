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

package com.example.abercrombietask;

import android.view.View;

import com.example.abercrombietask.data.FakeRandomDataGeneratorAPI;
import com.example.abercrombietask.viewmodel.WebViewViewModel;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class) public class WebViewViewModelTest {

    private WebViewViewModel webViewViewModel;
    private String url;

    @Before public void setUpDetailViewModelTest() {
        url = FakeRandomDataGeneratorAPI.getUrl();
        webViewViewModel = new WebViewViewModel(url);
    }

    @Test public void shouldGetUrl() throws Exception {
        assertEquals(url, webViewViewModel.getUrl());
    }


}
