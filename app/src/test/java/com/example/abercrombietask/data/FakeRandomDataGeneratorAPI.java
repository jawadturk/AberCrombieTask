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

package com.example.abercrombietask.data;

import com.example.abercrombietask.model.Content;
import com.example.abercrombietask.model.Promotion;

import java.util.ArrayList;
import java.util.List;

public class FakeRandomDataGeneratorAPI {

  private static final String PROMOTION_TITLE= "TOPS STARTING AT $12";
  private static final String PROMOTION_PROMO= "USE CODE: 12345";
  private static final String PROMOTION_BOTTOM_DESCRIPTION= "*In stores & online. <a href=\\\\\\\"http://www.abercrombie.com/anf/media/legalText/viewDetailsText20160602_Tier_Promo_US.html\\\\\\\">Exclusions apply. See Details</a>";
  private static final String PROMOTION_TOP_DESCRIPTION= "A&F ESSENTIALS";
  private static final String PROMOTION_BACKGROUND_IMAGE= "http://anf.scene7.com/is/image/anf/anf-20160527-app-m-shirts?$anf-ios-fullwidth-3x$";

  private static final String PROMOTION_CONTENT_TITLE = "Shop Men";
  private static final String PROMOTION_CONTENT_TARGET= "https://www.abercrombie.com/shop/us/mens-new-arrivals";


  public static List<Promotion> getPromotionsList() {
    List<Promotion> promotionList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      promotionList.add(getPromotions());
    }
    return promotionList;
  }

  public static Promotion getPromotions() {
    Promotion promotion = new Promotion();

    promotion.setTitle(PROMOTION_TITLE);
    promotion.setPromoMessage(PROMOTION_PROMO);
    promotion.setBottomDescription(PROMOTION_BOTTOM_DESCRIPTION);
    promotion.setTopDescription(PROMOTION_TOP_DESCRIPTION);
    promotion.setBackgroundImage(PROMOTION_BACKGROUND_IMAGE);

    Content content1 = new Content();
    content1.setTarget(PROMOTION_CONTENT_TARGET);
    content1.setTitle(PROMOTION_CONTENT_TITLE);

    Content content2= new Content();
    content2.setTarget(PROMOTION_CONTENT_TARGET);
    content2.setTitle(PROMOTION_CONTENT_TITLE);
    promotion.getContent().add(content1);
    promotion.getContent().add(content2);

    return promotion;
  }

  public static String getUrl() {
    return PROMOTION_CONTENT_TARGET;
  }
}
