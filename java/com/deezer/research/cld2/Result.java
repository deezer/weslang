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

package com.deezer.research.cld2;;

/**
 * Holds the result of calling detect.
 *
 * It has the name and code of the language and the confidence with which that language was
 * detected.
 */
public class Result {
    public final String language;
    public final String code;
    public final double confidence;

    public Result(String language, String code, double confidence) {
        this.language = language;
        this.code = code;
        this.confidence = confidence;
    }
}
