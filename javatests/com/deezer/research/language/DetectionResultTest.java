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
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;


public class DetectionResultTest {
  @Test
  public void testEquals() {
    assertEquals(new DetectionResult("en", 0.7),
                 new DetectionResult("en", 0.7));

    assertNotEquals(new DetectionResult("en", 0.7),
                    new DetectionResult("en", 0.8));

    assertNotEquals(new DetectionResult("en", 0.7),
                    new DetectionResult("es", 0.7));

    assertNotEquals(new DetectionResult("en", 0.7),
                    new DetectionResult("es", 0.8));
  }

  @Test
  public void testGetter() {
    DetectionResult result = new DetectionResult("en", 0.7);
    assertEquals("en", result.getLanguage());
    assertEquals(0.7, result.getConfidence(), 1e-5);
  }

  @Test
  public void testSetter() {
    DetectionResult result = new DetectionResult();
    result.setLanguage("en");
    result.setConfidence(0.7);
    assertEquals(new DetectionResult("en", 0.7), result);
  }

  @Test
  public void testToString() {
    DetectionResult result = new DetectionResult("en", 0.7);
    assertEquals("en 0.7", result.toString());
  }
}
