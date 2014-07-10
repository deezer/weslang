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

/**
 * Interface to detect the language of a given text.
 */
public interface DetectionService {
  DetectionResult UNKNOWN = new DetectionResult("UNKNOWN", 0.0);

  /**
   * Detects the language of the text.
   *
   * @param text The text to analyze.
   * @return DetectionResult with the identified lanaguage.
   */
  DetectionResult detect(String text);
}
