This service uses both the project language-detection and the project cld2,
which is the language detector built into chrome.


The languages detected by both are:

af Afrikaans
ar Arabic
bg Bulgarian
bn Bengali
cs Czech
da Danish
de German
el Greek
en English
es Spanish
et Estonian
fa Persian (Farsi)
fi Finnish
fr French
gu Gujarati
he Hebrew
hi Hindi
hr Croatian
hu Hungarian
id Indonesian
it Italian
ja Japanese
kn Kannada
ko Korean
lt Lithuanian
lv Latvian
mk Macedonian
ml Malayalam
mr Marathi
ne Nepali
nl Dutch
no Norwegian
pa Panjabi/Punjabi
pl Polish
pt Portuguese
ro Romanian
ru Russian
sk Slovak
sl Slovenian
so Somali
sq Albanian
sv Swedish
sw Swahili
ta Tamil
te Telugu
th Thai
tl tagalog
tr Turkish
uk Ukrainian
ur Urdu
vi Vietnamese
zh-cn Chinese-simplified
zh-tw Chinese-traditional

Note that there are some languages that are statistically the same, and hence
should be considered as just one group.

These groups include:
(grep close -A 15 ./internal/lang_script.cc)
id ms         # INDONESIAN MALAY coef=0.4698    Problematic w/o extra words
bo dz         # TIBETAN DZONGKHA coef=0.4571
cs sk         # CZECH SLOVAK coef=0.4273
zu xh         # ZULU XHOSA coef=0.3716

bs hr sr srm  # BOSNIAN CROATIAN SERBIAN MONTENEGRIN
hi mr bh ne   # HINDI MARATHI BIHARI NEPALI
no nn da      # NORWEGIAN NORWEGIAN_N DANISH
gl es pt      # GALICIAN SPANISH PORTUGUESE
rw rn         # KINYARWANDA RUNDI

The problematic groups for us are:
sk, cs
da, no
bs, hr, sr, srm
