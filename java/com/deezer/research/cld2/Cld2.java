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

package com.deezer.research.cld2;

/**
 * Public interface for the CLD2 library.
 */
public class Cld2 {
  public static int getLanguageFromName(String name) {
    return Cld2Library.INSTANCE._ZN4CLD219GetLanguageFromNameEPKc(name);
  }

  public static String getLanguageName(int language) {
    return Cld2Library.INSTANCE._ZN4CLD212LanguageNameENS_8LanguageE(language);
  }

  public static String getLanguageCode(int language) {
    return Cld2Library.INSTANCE._ZN4CLD212LanguageCodeENS_8LanguageE(language);
  }

  public static String version() {
    return Cld2Library.INSTANCE._ZN4CLD221DetectLanguageVersionEv();
  }

  public static Result detect(String text) {
    boolean isPlainText = true;
    CLDHints cldHints = new CLDHints(
        null,
        "",
        Encoding.UNKNOWN_ENCODING,
        Language.UNKNOWN_LANGUAGE);
    int flags = 0;
    int[] language3 = new int[3];
    int[] percent3 = new int[3];
    double[] normalizedScore3 = new double[3];
    int[] textBytes = new int[1];
    boolean[] isReliable = new boolean[1];
    byte[] utf8EncodedText;
    try {
      utf8EncodedText = text.getBytes("UTF-8");
    } catch (java.io.UnsupportedEncodingException exc) {
      return new Result("UNKNONW", "UNKNOWN", 0.0);
    }
    int language = Cld2Library.INSTANCE._ZN4CLD224ExtDetectLanguageSummaryEPKcibPKNS_8CLDHintsEiPNS_8LanguageEPiPdPSt6vectorINS_11ResultChunkESaISA_EES7_Pb(
        utf8EncodedText,
        utf8EncodedText.length,
        isPlainText,
        cldHints,
        flags,
        language3,
        percent3,
        normalizedScore3,
        null, // Supposed to be a vector of ResultChunks, but it is not direct to pass vectors.
        textBytes,
        isReliable);

    return new Result(
        getLanguageName(language), getLanguageCode(language), percent3[0] / 100.0);
    }
}
