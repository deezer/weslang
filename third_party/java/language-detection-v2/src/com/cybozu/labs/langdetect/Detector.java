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

import java.io.IOException;
import java.io.Reader;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.cybozu.labs.langdetect.util.NGram;

/**
 * {@link Detector} class is to detect language from specified text. 
 * Its instance is able to be constructed via the factory class {@link DetectorFactory}.
 * <p>
 * After appending a target text to the {@link Detector} instance with {@link #append(Reader)} or {@link #append(String)},
 * the detector provides the language detection results for target text via {@link #detect()} or {@link #getProbabilities()}.
 * {@link #detect()} method returns a single language name which has the highest probability.
 * {@link #getProbabilities()} methods returns a list of multiple languages and their probabilities.
 * <p>  
 * The detector has some parameters for language detection.
 * See {@link #setAlpha(double)}, {@link #setMaxTextLength(int)} and {@link #setPriorMap(HashMap)}.
 * 
 * <pre>
 * import java.util.ArrayList;
 * import com.cybozu.labs.langdetect.Detector;
 * import com.cybozu.labs.langdetect.DetectorFactory;
 * import com.cybozu.labs.langdetect.Language;
 * 
 * class LangDetectSample {
 *     public void init(String profileDirectory) throws LangDetectException {
 *         DetectorFactory.loadProfile(profileDirectory);
 *     }
 *     public String detect(String text) throws LangDetectException {
 *         Detector detector = DetectorFactory.create();
 *         detector.append(text);
 *         return detector.detect();
 *     }
 *     public ArrayList<Language> detectLangs(String text) throws LangDetectException {
 *         Detector detector = DetectorFactory.create();
 *         detector.append(text);
 *         return detector.getProbabilities();
 *     }
 * }
 * </pre>
 * 
 * <ul>
 * <li>4x faster improvement based on Elmer Garduno's code. Thanks!</li>
 * </ul>
 * 
 * @author Nakatani Shuyo
 * @see DetectorFactory
 */
public class Detector {
    private static final double ALPHA_DEFAULT = 0.5;

    private static final double PROB_THRESHOLD = 0.1;
    private static final int BASE_FREQ = 10000;
    private static final String UNKNOWN_LANG = "unknown";

    /*private static final Pattern URL_REGEX = Pattern.compile("https?://[-_.?&~;+=/#0-9A-Za-z]{1,2076}");
    private static final Pattern MAIL_REGEX = Pattern.compile("[-_.0-9A-Za-z]{1,64}@[-_0-9A-Za-z]{1,255}[-_.0-9A-Za-z]{1,255}");
    private static final Pattern SPACES_REGEX = Pattern.compile("[ ]+");*/
    private static final Pattern CLEAN_REGEX = Pattern.compile("https?://[-_.?&~;+=/#0-9A-Za-z]{1,2076}|[-_.0-9A-Za-z]{1,64}@[-_0-9A-Za-z]{1,255}[-_.0-9A-Za-z]{1,255}|[ ]{2,}");

    private final HashMap<String, double[]> wordLangProbMap;
    private final List<String> langlist;

    private StringBuilder text;
    private double[] langprob = null;

    private double alpha = ALPHA_DEFAULT;
    private int max_text_length = 10000;
    private double[] priorMap = null;
    private boolean verbose = false;
    private double weight;

    /**
     * Constructor.
     * Detector instance can be constructed via {@link DetectorFactory#create()}.
     * @param factory {@link DetectorFactory} instance (only DetectorFactory inside)
     */
    public Detector(DetectorFactory factory) {
        this.wordLangProbMap = factory.wordLangProbMap;
        this.langlist = factory.langlist;
        this.text = new StringBuilder();

        this.weight = this.alpha / BASE_FREQ;
    }

    /**
     * Set Verbose Mode(use for debug).
     */
    public void setVerbose() {
        this.verbose = true;
    }

    /**
     * Set smoothing parameter.
     * The default value is 0.5(i.e. Expected Likelihood Estimate).
     * @param alpha the smoothing parameter
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
        this.weight = this.alpha / BASE_FREQ;
    }

    /**
     * Set prior information about language probabilities.
     * @param priorMap the priorMap to set
     * @throws LangDetectException
     */
    public void setPriorMap(HashMap<String, Double> priorMap) throws LangDetectException {
        this.priorMap = new double[langlist.size()];
        double sump = 0;
        for (int i=0;i<this.priorMap.length;++i) {
            String lang = langlist.get(i);
            if (priorMap.containsKey(lang)) {
                double p = priorMap.get(lang);
                if (p<0) throw new LangDetectException(ErrorCode.InitParamError, "Prior probability must be non-negative.");
                this.priorMap[i] = p;
                sump += p;
            }
        }
        if (sump<=0) throw new LangDetectException(ErrorCode.InitParamError, "More one of prior probability must be non-zero.");
        for (int i=0;i<this.priorMap.length;++i) this.priorMap[i] /= sump;
    }

    /**
     * Specify max size of target text to use for language detection.
     * The default value is 10000(10KB).
     * @param max_text_length the max_text_length to set
     */
    public void setMaxTextLength(int max_text_length) {
        this.max_text_length = max_text_length;
    }


    /**
     * Append the target text for language detection.
     * This method read the text from specified input reader.
     * If the total size of target text exceeds the limit size specified by {@link Detector#setMaxTextLength(int)},
     * the rest is cut down.
     *
     * @param reader the input reader (BufferedReader as usual)
     * @throws IOException Can't read the reader.
     */
    public void append(Reader reader) throws IOException {
        char[] buf = new char[max_text_length/2];
        while (reader.ready()) {
            int length = reader.read(buf);
            append(new String(buf, 0, length));
        }
    }

    /**
     * Append the target text for language detection.
     * If the total size of target text exceeds the limit size specified by {@link Detector#setMaxTextLength(int)},
     * the rest is cut down.
     *
     * @param text the target text to append
     */
    public void append(String text) {
        text = CLEAN_REGEX.matcher(text).replaceAll(" ");
        this.text.append(text);
    }

    /**
     * Detect language of the target text and return the language name which has the highest probability.
     * @return detected language name which has most probability.
     * @throws LangDetectException
     *  code = ErrorCode.CantDetectError : Can't detect because of no valid features in text
     */
    public String detect() {
        List<Language> probabilities = getProbabilities();
        if (probabilities.size() > 0) {
            return probabilities.get(0).lang;
        }

        return UNKNOWN_LANG;
    }

    /**
     * Get language candidates which have high probabilities
     * @return possible languages list (whose probabilities are over PROB_THRESHOLD, ordered by probabilities descendently
     * @throws LangDetectException
     *  code = ErrorCode.CantDetectError : Can't detect because of no valid features in text
     */
    public List<Language> getProbabilities() {
        if (langprob == null) detectBlock();

        return sortProbability(langprob);
    }


    private void detectBlock() {
        langprob = initProbability();

        NGram ngram = new NGram();
        for(int i = 0; i < text.length(); ++i) {
            ngram.addChar(text.charAt(i));
            for (int n = 1; n <= NGram.N_GRAM; ++n) {
                String w = ngram.get(n);
                if (w != null && wordLangProbMap.containsKey(w)) {
                    updateLangProb(langprob, w);
                }
            }
            if (i % 32 == 0) {
                normalizeProb(langprob);
            }
        }
        normalizeProb(langprob);
    }

    /**
     * Initialize the map of language probabilities.
     * If there is the specified prior map, use it as initial map.
     * @return initialized map of language probabilities
     */
    private double[] initProbability() {
        double[] prob;
        if (priorMap != null) {
            prob = Arrays.copyOf(priorMap, langlist.size());
        } else {
            prob = new double[langlist.size()];
            Arrays.fill(prob, 1.0 / langlist.size());
        }
        return prob;
    }

    /**
     * update language probabilities with N-gram string(N=1,2,3)
     * @param word N-gram string
     */
    private boolean updateLangProb(double[] prob, String word) {
        double[] langProbMap = wordLangProbMap.get(word);

        if (verbose) System.out.println(word + "(" + unicodeEncode(word) + "):" + wordProbToString(langProbMap));

        for (int i = 0; i < prob.length; ++i) {
            prob[i] *= this.weight + langProbMap[i];
        }

        return true;
    }

    private String wordProbToString(double[] prob) {
        Formatter formatter = new Formatter();
        for(int j=0;j<prob.length;++j) {
            double p = prob[j];
            if (p>=0.00001) {
                formatter.format(" %s:%.5f", langlist.get(j), p);
            }
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }

    /**
     * normalize probabilities and check convergence by the maximun probability
     * @return maximum of probabilities
     */
    static private double normalizeProb(double[] prob) {
        double maxp = 0, sump = 0;
        for(int i=0;i<prob.length;++i) sump += prob[i];
        for(int i=0;i<prob.length;++i) {
            double p = prob[i] / sump;
            if (maxp < p) maxp = p;
            prob[i] = p;
        }
        return maxp;
    }

    private static Comparator<Language> languageComparator = new Comparator<Language>() {
        @Override
        public int compare(Language o1, Language o2) {
            // Arguments are reversed so to sort in decreasing order.
            return Double.compare(o2.prob, o1.prob);
        }
    };

    /**
     * @param probabilities HashMap
     * @return lanugage candidates order by probabilities descendently
     */
    private List<Language> sortProbability(double[] prob) {
        List<Language> list = new ArrayList<Language>(prob.length);
        for(int i = 0; i < prob.length; ++i) {
            if (prob[i] > PROB_THRESHOLD) {
                list.add(new Language(langlist.get(i), prob[i]));
            }
        }
        Collections.sort(list, languageComparator);

        return list;
    }

    /**
     * unicode encoding (for verbose mode)
     * @param word
     * @return
     */
    static private String unicodeEncode(String word) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < word.length(); ++i) {
            char ch = word.charAt(i);
            if (ch >= '\u0080') {
                String st = Integer.toHexString(0x10000 + (int) ch);
                while (st.length() < 4) st = "0" + st;
                buf.append("\\u").append(st.subSequence(1, 5));
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

}
