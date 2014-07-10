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

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Controller for detecting the language.
 *
 * It adds the metrics com.deezer.research.language.<LANG> to track how many texts have been
 * identified in every language.
 */
@RestController
public class DetectionController {
  @Inject
  private CounterService counterService;
  @Inject
  private DetectionService service;

  @RequestMapping(value = "/detect", method = { RequestMethod.GET, RequestMethod.POST })
  public DetectionResult detect(
      @RequestParam(value = "q", required = true) String text) {
    DetectionResult result = service.detect(text);
    this.counterService.increment(
        "com.deezer.research.language.DetectionService." + result.getLanguage());

    return result;
  }
}
