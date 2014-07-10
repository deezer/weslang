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

import com.deezer.research.cld2.Cld2;
import com.deezer.research.cld2.Result;


/**
 * Detects the language using the CLD2 project with custom native wrappers.
 *
 * @see com.deezer.research.cld2
 */
@Service
@Profile("cld2")
public class DetectionServiceImplCld2 implements DetectionService {
  @Override
  public DetectionResult detect(String text) {
    Result result = Cld2.detect(text);
    if (result.code.equals("")) {
      return new DetectionResult("UNKNOWN", 0.0);
    }
    String code = result.code;
    double confidence = result.confidence;
    if (code.equals("zh")) {
      code = "zh-cn";
    } else if (code.equals("zh-Hant")) {
      code = "zh-tw";
    } else if (code.equals("iw")) {
      code = "he";
    } else if (code.equals("un")) {
      code = "UNKNOWN";
    }
    return new DetectionResult(code, confidence);
  }
}
