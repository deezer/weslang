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

import org.junit.Test;

/**
 * This class contains two tests that all DetectionService implementations
 * should test.
 */
public abstract class BaseTest {
  DetectionService service;

  /**
   * Checks that for all pairs defined in TestData the service returns the right
   * code.
   *
   * @see TestData
   */
  @Test
  public void testDetection() {
    String languageCode;
    for (int i = 0; i < TestData.data.length; ++i) {
      languageCode = TestData.data[i][0];
      if (languageCode.equals("")) {
        continue;
      }
      assertEquals(languageCode,
                   this.service.detect(TestData.data[i][2]).getLanguage());
    }
  }

  /**
   * Checks that the service can correctly handle the emtpy string.
   */
  @Test
  public void testEmptyString() {
    assertEquals(new DetectionResult("UNKNOWN", 0.0), this.service.detect(""));
  }
}
