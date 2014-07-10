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

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * Detects the language using two detectors.
 *
 * It returns a language only when both detectors agree, in which case the score
 * will be the avertage of both.
 *
 * @see DetectionServiceImplCld2
 * @see DetectionServiceImplLanguageDetection
 */
@Service
@Profile("both")
public class DetectionServiceImpl implements DetectionService {
  public static final double CONFIDENCE_THRESHOLD = 0.5;

  DetectionService cld2Service;
  DetectionService languageDetectionService;

  public DetectionServiceImpl() throws IOException,
                                       UnsupportedEncodingException,
                                       IllegalArgumentException {
    this.cld2Service = new DetectionServiceImplCld2();
    this.languageDetectionService = new DetectionServiceImplLanguageDetection();
  }

  @Override
  public DetectionResult detect(String text) {
    DetectionResult result1 = this.cld2Service.detect(text);

    if (result1.getConfidence() < CONFIDENCE_THRESHOLD || result1.getLanguage().equals("UNKNOWN")) {
      return UNKNOWN;
    }

    DetectionResult result2 = this.languageDetectionService.detect(text);

    if (result1.getLanguage().equals(result2.getLanguage()) &&
        result2.getConfidence() >= CONFIDENCE_THRESHOLD) {
      return new DetectionResult(
          result1.getLanguage(),
          (result1.getConfidence() + result2.getConfidence()) / 2.0);
    }

    return UNKNOWN;
  }
}
