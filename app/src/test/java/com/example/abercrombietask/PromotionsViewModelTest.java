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

import android.test.mock.MockContext;
import android.view.View;

import com.example.abercrombietask.data.DataService;
import com.example.abercrombietask.data.FakeRandomDataGeneratorAPI;
import com.example.abercrombietask.model.Promotion;
import com.example.abercrombietask.viewmodel.PromotionsViewModel;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.Silent.class) public class PromotionsViewModelTest {



    @Mock private DataService dataService;
    @Mock private MockContext mockContext;

    private PromotionsViewModel promotionsViewModel;

    @Before public void setUpMainViewModelTest() {
        promotionsViewModel = new PromotionsViewModel(mockContext);
    }

    @Test public void simulateGivenTheUserCallListFromApi() throws Exception {
        List<Promotion> promotionList = FakeRandomDataGeneratorAPI.getPromotionsList();
        doReturn(Observable.just(promotionList)).when(dataService).fetchPromotions();
    }

    @Test public void ensureTheViewsAreInitializedCorrectly() throws Exception {
        promotionsViewModel.initializeViews();

        assertEquals(View.GONE, promotionsViewModel.promotionsRecycler.get());
        assertEquals(View.VISIBLE, promotionsViewModel.promotionsProgress.get());
    }
}
