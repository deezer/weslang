/*
 * Copyright Cybozu Labs
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

package com.cybozu.labs.langdetect;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import com.cybozu.labs.langdetect.util.LangProfile;

/**
 * Unit test for {@link Detector} and {@link DetectorFactory}.
 * @author Nakatani Shuyo
 *
 */
public class DetectorTest {

    private static final String TRAINING_EN = "a a a b b c c d e";
    private static final String TRAINING_FR = "a b b c c c d d d";
    private static final String TRAINING_JA = "\u3042 \u3042 \u3042 \u3044 \u3046 \u3048 \u3048";
    private static final String JSON_LANG1 = "{\"freq\":{\"A\":3,\"B\":6,\"C\":3,\"AB\":2,\"BC\":1,\"ABC\":2,\"BBC\":1,\"CBA\":1},\"n_words\":[12,3,4],\"name\":\"lang1\"}";
    private static final String JSON_LANG2 = "{\"freq\":{\"A\":6,\"B\":3,\"C\":3,\"AA\":3,\"AB\":2,\"ABC\":1,\"ABA\":1,\"CAA\":1},\"n_words\":[12,5,3],\"name\":\"lang2\"}";

    @Before
    public void setUp() throws Exception {
        DetectorFactory.clear();

        LangProfile profile_en = new LangProfile("en");
        for (String w : TRAINING_EN.split(" "))
            profile_en.add(w);
        DetectorFactory.addProfile(profile_en, 0, 3);

        LangProfile profile_fr = new LangProfile("fr");
        for (String w : TRAINING_FR.split(" "))
            profile_fr.add(w);
        DetectorFactory.addProfile(profile_fr, 1, 3);

        LangProfile profile_ja = new LangProfile("ja");
        for (String w : TRAINING_JA.split(" "))
            profile_ja.add(w);
        DetectorFactory.addProfile(profile_ja, 2, 3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testDetector1() throws LangDetectException {
        Detector detect = DetectorFactory.create();
        detect.setVerbose();
        detect.append("a");
        assertEquals("en", detect.detect());
    }

    @Test
    public final void testDetector2() throws LangDetectException {
        Detector detect = DetectorFactory.create();
        detect.append("b d");
        assertEquals("fr", detect.detect());
    }

    @Test
    public final void testDetector3() throws LangDetectException {
        Detector detect = DetectorFactory.create();
        detect.append("d e");
        assertEquals("en", detect.detect());
    }

    @Test
    public final void testDetector4() throws LangDetectException {
        Detector detect = DetectorFactory.create();
        detect.append("\u3042\u3042\u3042\u3042a");
        assertEquals("ja", detect.detect());
    }

    @Test
    public final void testLangList() throws LangDetectException {
        assertEquals(Arrays.asList("en", "fr", "ja"), DetectorFactory.getLangList());
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testLangListException() throws LangDetectException {
        List<String> langList = DetectorFactory.getLangList();
        langList.add("hoge");
        //langList.add(1, "hoge");
    }

    @Test
    public final void testFactoryFromJsonString() throws LangDetectException {
        DetectorFactory.clear();
        DetectorFactory.loadProfile(Arrays.asList(JSON_LANG1, JSON_LANG2));
        assertEquals(Arrays.asList("lang1", "lang2"), DetectorFactory.getLangList());
    }

    @Test
    public final void testFactoryFromDefault() throws Exception {
        DetectorFactory.clear();
        DetectorFactory.loadDefaultProfiles();
        assertEquals(
            Arrays.asList("af", "ar", "bg", "bn", "cs", "da", "de", "el", "en",
                          "es", "et", "fa", "fi", "fr", "gu", "he", "hi", "hr",
                          "hu", "id", "it", "ja", "kn", "ko", "lt", "lv", "mk",
                          "ml", "mr", "ne", "nl", "no", "pa", "pl", "pt", "ro",
                          "ru", "sk", "sl", "so", "sq", "sv", "sw", "ta", "te",
                          "th", "tl", "tr", "uk", "ur", "vi", "zh-cn", "zh-tw"),
            DetectorFactory.getLangList());
    }
}
