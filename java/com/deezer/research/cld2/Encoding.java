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
 * Enum with the values of the different encodings accepted by the CLD2 library.
 */
public interface Encoding {
  int ISO_8859_1 = 0;
  int ISO_8859_2 = 1;
  int ISO_8859_3 = 2;
  int ISO_8859_4 = 3;
  int ISO_8859_5 = 4;
  int ISO_8859_6 = 5;
  int ISO_8859_7 = 6;
  int ISO_8859_8 = 7;
  int ISO_8859_9 = 8;
  int ISO_8859_10 = 9;
  int JAPANESE_EUC_JP = 10;
  int JAPANESE_SHIFT_JIS = 11;
  int JAPANESE_JIS = 12;
  int CHINESE_BIG5 = 13;
  int CHINESE_GB = 14;
  int CHINESE_EUC_CN = 15;
  int KOREAN_EUC_KR = 16;
  int UNICODE_UNUSED = 17;
  int CHINESE_EUC_DEC = 18;
  int CHINESE_CNS = 19;
  int CHINESE_BIG5_CP950 = 20;
  int JAPANESE_CP932 = 21;
  int UTF8 = 22;
  int UNKNOWN_ENCODING = 23;
  int ASCII_7BIT = 24;
  int RUSSIAN_KOI8_R = 25;
  int RUSSIAN_CP1251 = 26;
  int MSFT_CP1252 = 27;
  int RUSSIAN_KOI8_RU = 28;
  int MSFT_CP1250 = 29;
  int ISO_8859_15 = 30;
  int MSFT_CP1254 = 31;
  int MSFT_CP1257 = 32;
  int ISO_8859_11 = 33;
  int MSFT_CP874 = 34;
  int MSFT_CP1256 = 35;
  int MSFT_CP1255 = 36;
  int ISO_8859_8_I = 37;
  int HEBREW_VISUAL = 38;
  int CZECH_CP852 = 39;
  int CZECH_CSN_369103 = 40;
  int MSFT_CP1253 = 41;
  int RUSSIAN_CP866 = 42;
  int ISO_8859_13 = 43;
  int ISO_2022_KR = 44;
  int GBK = 45;
  int GB18030 = 46;
  int BIG5_HKSCS = 47;
  int ISO_2022_CN = 48;
  int TSCII = 49;
  int TAMIL_MONO = 50;
  int TAMIL_BI = 51;
  int JAGRAN = 52;
  int MACINTOSH_ROMAN = 53;
  int UTF7 = 54;
  int BHASKAR = 55;
  int HTCHANAKYA = 56;
  int UTF16BE = 57;
  int UTF16LE = 58;
  int UTF32BE = 59;
  int UTF32LE = 60;
  int BINARYENC = 61;
  int HZ_GB_2312 = 62;
  int UTF8UTF8 = 63;
  int TAM_ELANGO = 64;
  int TAM_LTTMBARANI = 65;
  int TAM_SHREE = 66;
  int TAM_TBOOMIS = 67;
  int TAM_TMNEWS = 68;
  int TAM_WEBTAMIL = 69;
  int KDDI_SHIFT_JIS = 70;
  int DOCOMO_SHIFT_JIS = 71;
  int SOFTBANK_SHIFT_JIS = 72;
  int KDDI_ISO_2022_JP = 73;
  int SOFTBANK_ISO_2022_JP = 74;
  int NUM_ENCODINGS = 75;
}
