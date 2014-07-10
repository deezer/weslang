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

import java.util.Objects;

/**
 * Class to hold the result of a language detection.
 * It holds the language code, which should be the ISO 639 code, or UNKNOWN, and the confidence with
 * which the languge was identidfied. The confidence goes from 0.0 to 1.0.
 *
 * This class is more verbose as needes, as it is also used to automatically convert to and from
 * JSON.
 */
public class DetectionResult {
  private String language;
  private double confidence;

  public DetectionResult() {
    this(null, 0.0);
  }

  public DetectionResult(String language, double confidence) {
    this.language = language;
    this.confidence = confidence;
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public double getConfidence() {
    return this.confidence;
  }

  public void setConfidence(double confidence) {
    this.confidence = confidence;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof DetectionResult)) {
      return false;
    }
    if (obj == this) {
      return true;
    }

    DetectionResult rhs = (DetectionResult) obj;
    return Objects.equals(this.language, rhs.language) &&
      Objects.equals(this.confidence, rhs.confidence);
  }

  public int hashCode() {
    return Objects.hash(this.language, this.confidence);
  }

  public String toString() {
    return this.language + " " + this.confidence;
  }
}
