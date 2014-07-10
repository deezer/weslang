/*
 * Copyright 2014-present Deezer.com
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

package com.deezer.research.language;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class DetectionServiceImplCld2Test extends BaseTest{
  @Before
  public void testSetup() {
    this.service = new DetectionServiceImplCld2();
  }

  @Test
  public void testHebrewCode() {
    // This is already tested in testDection, but an explicit check is desired.
    assertEquals(
        "he",
        this.service.detect(
            " או לערוך את העדפות ההפצה אנא עקוב אחרי השלבים הבאים כנס לחשבון האישי שלך ב"
        ).getLanguage());
  }

  @Test
  public void testSimplifiedChinese() {
    // This is already tested in testDection, but an explicit check is desired.
    assertEquals(
        "zh-cn",
        this.service.detect(
            "产品的简报和公告 提交该申请后无法进行更改 请确认您的选择是正确的 对于要提交的图书 我确认 "
            + "我是版权所有者或已得到版权所有者的授权 要更改您的国家 地区 请在此表的最上端更改您的"

        ).getLanguage());
  }

  @Test
  public void testTraditionalChinese() {
    // This is already tested in testDection, but an explicit check is desired.
    assertEquals(
        "zh-tw",
        this.service.detect(
            " 之前為 帳單交易作業區 已變更 廣告內容 之前為 銷售代表 之前為 張貼日期為 百分比之前為 合約 為 "
            + "目標對象條件已刪除 結束日期之前為"
        ).getLanguage());
  }
}
