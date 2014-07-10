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

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Detects the language using the language-detection project.
 *
 * @see https://code.google.com/p/language-detection/
 */
@Service
@Profile("java-only")
public class DetectionServiceImplLanguageDetection implements DetectionService {
  public DetectionServiceImplLanguageDetection() throws IOException,
                                                        UnsupportedEncodingException,
                                                        IllegalArgumentException {
    try {
      DetectorFactory.loadDefaultProfiles();
    } catch (LangDetectException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public DetectionResult detect(String text) {
    Detector detector;
    try {
      detector = DetectorFactory.create();
    } catch (LangDetectException e) {
      // TODO(skreft): log the reason
      return UNKNOWN;
    }

    detector.append(text);
    List<Language> results = detector.getProbabilities();
    if (!results.isEmpty()) {
      Language bestLang = results.get(0);
      return new DetectionResult(bestLang.lang, bestLang.prob);
    }

    return UNKNOWN;
  }
}
