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

package com.cybozu.labs.langdetect;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class ProfilesTest {
  public static String[][] testData = {
    {"en", "ENGLISH", "confiscation of goods is assigned as the penalty part most of the courts consist of members and when it is necessary to bring public cases before a jury of members two courts combine for the purpose the most important cases of all are brought jurors or"},
    {"el", "GREEK", " ή αρνητική αναζήτηση λέξης κλειδιού καταστήστε τις μεμονωμένες λέξεις κλειδιά περισσότερο στοχοθετημένες με τη μετατροπή τους σε"},
    {"gu", "GUJARATI", " આના પરિણામ પ્રમાણસર ફોન્ટ અવતરણ ચિન્હવાળા પાઠને છુપાવો બધા સમૂહો શોધાયા હાલનો જ સંદેશ વિષયની"},
    {"kn", "KANNADA", " ಂಠಯ್ಯನವರು ತುಮಕೂರು ಜಿಲ್ಲೆಯ ಚಿಕ್ಕನಾಯಕನಹಳ್ಳಿ ತಾಲ್ಲೂಕಿನ ತೀರ್ಥಪುರ ವೆಂಬ ಸಾಧಾರಣ ಹಳ್ಳಿಯ ಶ್ಯಾನುಭೋಗರ"},
    {"ml", "MALAYALAM", " ം അങ്ങനെ ഞങ്ങള് അവരുടെ മുമ്പില് നിന്നു ഔടും ഉടനെ നിങ്ങള് പതിയിരിപ്പില് നിന്നു എഴുന്നേറ്റു"},
    {"pa", "PUNJABI", " ਂ ਦਿਨਾਂ ਵਿਚ ਭਾਈ ਸਾਹਿਬ ਦੀ ਬੁੱਚੜ ਗੋਬਿੰਦ ਰਾਮ ਨਾਲ ਅੜਫਸ ਚੱਲ ਰਹੀ ਸੀ ਗੋਬਿੰਦ ਰਾਮ ਨੇ ਭਾਈ ਸਾਹਿਬ ਦੀਆਂ ਭੈਣਾ"},
    // Language-Detection does not support the Tagalog script
    //{"tl", "TAGALOG", " ᜋᜇ᜔ ᜐᜓᜎᜆ᜔ ᜃ ᜈᜅ᜔ ᜊᜌ᜔ᜊᜌᜒᜈ᜔ ᜂᜉᜅ᜔᜔ ᜋᜐᜈᜌ᜔ ᜎᜅ᜔ ᜁᜐ ᜉᜅ᜔ ᜀᜃ᜔ᜎᜆ᜔ ᜆᜓᜅ᜔ᜃᜓᜎ᜔ ᜐ ᜊᜌ᜔ᜊᜌᜒᜈ᜔ ᜐ ᜆᜒᜅᜒᜈ᜔ ᜃᜓ"},
    {"tl", "TAGALOG", "Bawat tao'y isinilang na may laya at magkakapantay ang taglay na dangal at karapatan.Sila'y pinagkalooban ng pangangatwiran at budhi na kailangang gamitin nila sa pagtuturingan nila sa diwa ng pagkakapatiran."},
    {"ta", "TAMIL", " அங்கு ராஜேந்திர சோழனால் கட்டப்பட்ட பிரம்மாண்டமான சிவன் கோவில் ஒன்றும் உள்ளது தொகு"},
    {"te", "TELUGU", " ఁ దనర జయించిన తత్వ మరసి చూడఁ దాన యగును రాజయోగి యిట్లు తేజరిల్లుచు నుండు విశ్వదాభిరామ వినర వేమ"},
    {"th", "THAI", " กฏในการค้นหา หรือหน้าเนื้อหา หากท่านเลือกลงโฆษณา ท่านอาจจะปรับต้องเพิ่มงบประมาณรายวันตา"},
    {"zh-cn", "Chinese", "产品的简报和公告 提交该申请后无法进行更改 请确认您的选择是正确的 对于要提交的图书 我确认 我是版权所有者或已得到版权所有者的授权 要更改您的国家 地区 请在此表的最上端更改您的"},
    // Language-Detection has a bug concerning traditional chinese and korean. See https://code.google.com/p/language-detection/issues/detail?id=66
    //{"zh-tw", "ChineseT", " 之前為 帳單交易作業區 已變更 廣告內容 之前為 銷售代表 之前為 張貼日期為 百分比之前為 合約 為 目標對象條件已刪除 結束日期之前為"},
    {"ja", "Japanese", " このペ ジでは アカウントに指定された予算の履歴を一覧にしています それぞれの項目には 予算額と特定期間のステ タスが表示されます 現在または今後の予算を設定するには"},
    {"ko", "Korean", " 개별적으로 리포트 액세스 권한을 부여할 수 있습니다 액세스 권한 부여사용자에게 프로필 리포트에 액세스할 수 있는 권한을 부여하시려면 가용 프로필 상자에서 프로필 이름을 선택한 다음"},
    {"af", "AFRIKAANS", " aam skukuza die naam beteken hy wat skoonvee of hy wat alles onderstebo keer wysig bosveldkampe boskampe is kleiner afgeleë ruskampe wat oor min fasiliteite beskik daar is geen restaurante of winkels nie en slegs oornagbesoekers word toegelaat bateleur"},
    {"sq", "ALBANIAN", " a do të kërkoni nga beogradi që të njohë pavarësinë e kosovës zoti thaçi prishtina është gati ta njoh pavarësinë e serbisë ndërsa natyrisht se do të kërkohet një gjë e tillë që edhe beogradi ta njoh shtetin e pavarur dhe sovran të"},
    {"ar", "ARABIC", "احتيالية بيع أي حساب"},
    {"bn", "BENGALI", "গ্যালারির ৩৮ বছর পূর্তিতে মূল্যছাড় অর্থনীতি বিএনপির ওয়াক আউট তপন চৌধুরী হারবাল অ্যাসোসিয়েশনের সভাপতি আন্তর্জাতিক পরামর্শক বোর্ড দিয়ে শরিয়াহ্ ইনন্ডেক্স করবে সিএসই মালিকপক্ষের কান্না, শ্রমিকের অনিশ্চয়তা মতিঝিলে সমাবেশ নিষিদ্ধ: এফবিসিসিআইয়ের ধন্যবাদ বিনোদন বিশেষ প্রতিবেদন বাংলালিংকের গ্র্যান্ডমাস্টার সিজন-৩ ব্রাজিলে বিশ্বকাপ ফুটবল আয়োজনবিরোধী বিক্ষোভ দেশের নিরাপত্তার  চেয়ে অনেক বেশি সচেতন । প্রার্থীদের দক্ষতা  ও যোগ্যতার পাশাপাশি তারা জাতীয় ইস্যুগুলোতে প্রাধান্য দিয়েছেন । ” পাঁচটি সিটিতে ২০ লাখ ভোটারদের দিয়ে জাতীয় নির্বাচনে ৮ কোটি ভোটারদের সঙ্গে তুলনা করা যাবে কি একজন দর্শকের এমন প্রশ্নে জবাবে আব্দুল্লাহ আল নোমান বলেন , “ এই পাঁচটি সিটি কর্পোরেশন নির্বাচন দেশের পাঁচটি বড় বিভাগের প্রতিনিধিত্ব করছে । এছাড়া এখানকার ভোটার রা সবাই সচেতন । তারা"},
    {"bg", "BULGARIAN", " а дума попада в състояние на изпитание ключовите думи с предсказана малко под то изискване на страниците за търсене в"},
    {"hr", "CROATIAN", "Posljednja dva vladara su Kijaksar {Κυαξαρης; 625-585 prije Krista}, fraortov sin koji će proširiti teritorij Medije i Astijag. Kijaksar je imao kćer ili unuku koja se zvala Amitis a postala je ženom Nabukodonosora II. kojoj je ovaj izgradio Viseće vrtove Babilona. Kijaksar je modernizirao svoju vojsku i uništio Ninivu 612. prije Krista. Naslijedio ga je njegov sin, posljednji medijski kralj, Astijag, kojega je detronizirao {srušio sa vlasti} njegov unuk Kir Veliki. Zemljom su zavladali Perzijanci."},
    {"cs", "CZECH", " a akci opakujte film uložen vykreslit gmail tokio smazat obsah adresáře nelze načíst systémový profil jednotky smoot okud používáte pro určení polokoule značky z západ nebo v východ používejte nezáporné hodnoty zeměpisné délky nelze"},
    {"da", "DANISH", " a z tallene og punktummer der er tilladte log ud angiv den ønskede adgangskode igen november gem personlige oplysninger kontrolspørgsmål det sidste tegn i dit brugernavn skal være et bogstav a z eller tal skriv de tegn du kan se i billedet nedenfor"},
    {"nl", "DUTCH", " a als volgt te werk om een configuratiebestand te maken sitemap gen py ebruik filters om de s op te geven die moeten worden toegevoegd of uitgesloten op basis van de opmaaktaal elke sitemap mag alleen de s bevatten voor een bepaalde opmaaktaal dit"},
    {"en", "ENGLISH", " a backup credit card by visiting your billing preferences page or visit the adwords help centre for more details https adwords google com support bin answer py answer hl en we were unable to process the payment of for your outstanding google adwords"},
    {"et", "ESTONIAN", " a niipea kui sinu maksimaalne igakuine krediidi limiit on meie poolt heaks kiidetud on sinu kohustuseks see krediidilimiit"},
    {"fi", "FINNISH", " a joilla olet käynyt tämä kerro meille kuka ä olet ei tunnistettavia käyttötietoja kuten virheraportteja käytetään google desktopin parantamiseen etsi näyttää mukautettuja uutisia google desktop keskivaihto leikkaa voit kaksoisnapsauttaa"},
    {"fr", "FRENCH", " a accès aux collections et aux frontaux qui lui ont été attribués il peut consulter et modifier ses collections et exporter des configurations de collection toutefois il ne peut pas créer ni supprimer des collections enfin il a accès aux fonctions"},
    {"de", "GERMAN", " abschnitt ordner aktivieren werden die ordnereinstellungen im farbabschnitt deaktiviert öchten sie wirklich fortfahren eldtypen angeben optional n diesem schritt geben sie für jedesfeld aus dem datenset den typ an ieser schritt ist optional eldtypen"},
    {"he", "HEBREW", " או לערוך את העדפות ההפצה אנא עקוב אחרי השלבים הבאים כנס לחשבון האישי שלך ב"},
    {"hi", "HINDI", " ं ऐडवर्ड्स विज्ञापनों के अनुभव पर आधारित हैं और इनकी मदद से आपको अपने विज्ञापनों का अधिकतम लाभ"},
    {"hu", "HUNGARIAN", " a felhasználóim a google azonosító szöveget ikor látják a felhasználóim a google azonosító szöveget felhasználók a google azonosító szöveget fogják látni minden tranzakció után ha a vásárlását regisztrációját oldalunk"},
    {"id", "INDONESIAN", "berdiri setelah pengurusnya yang berusia 83 tahun, Fayzrahman Satarov, mendeklarasikan diri sebagai nabi dan rumahnya sebagai negara Islam Satarov digambarkan sebagai mantan ulama Islam  tahun 1970-an. Pengikutnya didorong membaca manuskripnya dan kebanyakan dilarang meninggalkan tempat persembunyian bawah tanah di dasar gedung delapan lantai mereka. Jaksa membuka penyelidikan kasus kriminal pada kelompok itu dan menyatakan akan membubarkan kelompok kalau tetap melakukan kegiatan ilegal seperti mencegah anggotanya mencari bantuan medis atau pendidikan. Sampai sekarang pihak berwajib belum melakukan penangkapan meskipun polisi mencurigai adanya tindak kekerasan pada anak. Pengadilan selanjutnya akan memutuskan apakah anak-anak diizinkan tetap tinggal dengan orang tua mereka. Kazan yang berada sekitar 800 kilometer di timur Moskow merupakan wilayah Tatarstan yang"},
    {"it", "ITALIAN", " a causa di un intervento di manutenzione del sistema fino alle ore circa ora legale costa del pacifico del novembre le campagne esistenti continueranno a essere pubblicate come di consueto anche durante questo breve periodo di inattività ci scusiamo per"},
    {"lv", "LATVIAN", " a gadskārtējā izpārdošana slēpošana jāņi atlaide izmaiņas trafikā kas saistītas ar sezonas izpārdošanu speciālajām atlaidēm u c ir parastas un atslēgvārdi kas ir populāri noteiktos laika posmos šajā laikā saņems lielāku klikšķu"},
    {"lt", "LITHUANIAN", " a išsijungia mano idėja dėl geriausio laiko po pastarųjų savo santykių pasimokiau penki dalykai be kurių negaliu gyventi mano miegamajame tu surasi ideali pora išsilavinimas aukštoji mokykla koledžas universitetas pagrindinis laipsnis metai"},
    {"mk", "MACEDONIAN", " гласовите коалицијата на вмро дпмне како партија со најмногу освоени гласови ќе добие евра а на сметката на коализијата за македонија"},
    {"mr", "MARATHI", "हैदराबाद  उच्चार ऐका {सहाय्य·माहिती}तेलुगू: హైదరాబాదు , उर्दू: حیدر آباد हे भारतातील आंध्र प्रदेश राज्याच्या राजधानीचे शहर आहे. हैदराबादची लोकसंख्या ७७ लाख ४० हजार ३३४ आहे. मोत्यांचे शहर अशी एकेकाळी ओळख असलेल्या या शहराला ऐतिहासिक, सांस्कृतिक आणि स्थापत्यशास्त्रीय वारसा लाभला आहे. १९९० नंतर शिक्षण आणि माहिती तंत्रज्ञान त्याचप्रमाणे औषधनिर्मिती आणि जैवतंत्रज्ञान क्षेत्रातील उद्योगधंद्यांची वाढ शहरात झाली. दक्षिण मध्य भारतातील पर्यटन आणि तेलुगू चित्रपटनिर्मितीचे हैदराबाद हे केंद्र आहे"},
    {"ne", "NEPALI", "अरू ठाऊँबाटपनि खुलेको छ यो खाता अर अरू ठाऊँबाटपनि खुलेको छ यो खाता अर ू"},
    {"no", "NORWEGIAN", " a er obligatorisk tidsforskyvning plassering av katalogsøk planinformasjon loggfilbane gruppenavn kontoinformasjon passord domene gruppeinformasjon alle kampanjesporing alternativ bruker grupper oppgaveplanlegger oppgavehistorikk kontosammendrag antall"},
    {"fa", "PERSIAN", " آب خوردن عجله می کردند به جای باز ی کتک کاری می کردند و همه چيز مثل قبل بود فقط من ماندم و يک دنيا حرف و انتظار تا عاقبت رسيد احضاريه ی ای با"},
    {"pl", "POLISH", " a australii będzie widział inne reklamy niż użytkownik z kanady kierowanie geograficzne sprawia że reklamy są lepiej dopasowane do użytkownika twojej strony oznacza to także że możesz nie zobaczyć wszystkich reklam które są wyświetlane na"},
    {"pt", "PORTUGUESE", " a abit prevê que a entrada desses produtos estrangeiros no mercado têxtil e vestuário do brasil possa reduzir os preços em cerca de a partir de má notícia para os empresários que terão que lutar para garantir suas margens de lucro mas boa notícia"},
    {"ro", "ROMANIAN", " a anunţurilor reţineţi nu plătiţi pentru clicuri sau impresii ci numai atunci când pe site ul dvs survine o acţiune dorită site urile negative nu pot avea uri de destinaţie daţi instrucţiuni societăţii dvs bancare sau constructoare să"},
    // Language-Detection does not support cyrillic for romanaian
    //{"ro", "ROMANIAN", "оперативэ а органелор ши институциилор екзекутиве ши а органелор жудичиаре але путерий де стат фиекэруй орган ал путерий де стат и се"},
    {"ru", "RUSSIAN", " а неправильный формат идентификатора дн назад"},
    {"sk", "SLOVAK", " a aktivovať reklamnú kampaň ak chcete kampaň pred spustením ešte prispôsobiť uložte ju ako šablónu a pokračujte v úprave vyberte si jednu z možností nižšie a kliknite na tlačidlo uložiť kampaň nastavenia kampane môžete ľubovoľne"},
    {"sl", "SLOVENIAN", " adsense stanje prijave za google adsense google adsense račun je bil začasno zamrznjen pozdravljeni hvala za vaše zanimanje v google adsense po pregledu vaše prijavnice so naši strokovnjaki ugotovili da spletna stran ki je trenutno povezana z vašim"},
    {"es", "SPANISH", " a continuación haz clic en el botón obtener ruta también puedes desplazarte hasta el final de la página para cambiar tus opciones de búsqueda gráfico y detalles ésta es una lista de los vídeos que te recomendamos nuestras recomendaciones se basan"},
    {"sw", "SWAHILI", " a ujumbe mpya jumla unda tafuta na angalia vikundi vya kujadiliana na kushiriki mawazo iliyopangwa kwa tarehe watumiaji wapya futa orodha hizi lugha hoja vishikanisho vilivyo dhaminiwa ujumbe sanaa na tamasha toka udhibitisho wa neno kwa haraka fikia"},
    {"sv", "SWEDISH", " a bort objekt från google desktop post äldst meny öretag dress etaljer alternativ för vad är inne yaste google skrivbord plugin program för nyheter google visa nyheter som är anpassade efter de artiklar som du läser om du till exempel läser"},
    {"tl", "TAGALOG", " a na ugma sa google ay nakaka bantog sa gitna nang kliks na nangyayari sa pamamagitan nang ordinaryong paggagamit at sa kliks na likha nang pandaraya o hindi tunay na paggamit bunga nito nasasala namin ang mga kliks na hindi kailangan o hindi gusto nang"},
    {"tr", "TURKISH", " a ayarlarınızı görmeniz ve yönetmeniz içindir eğer kampanyanız için günlük bütçenizi gözden geçirebileceğiniz yeri arıyorsanız kampanya yönetimi ne gidin kampanyanızı seçin ve kampanya ayarlarını düzenle yi tıklayın sunumu"},
    {"uk", "UKRAINIAN", " а більший бюджет щоб забезпечити собі максимум прибутків від переходів відстежуйте свої об яви за датою географічним розташуванням"},
    {"ur", "URDU", " آپ کو کم سے کم ممکنہ رقم چارج کرتا ہے اس کی مثال کے طور پر فرض کریں اگر آپ کی زیادہ سے زیادہ قیمت فی کلِک امریکی ڈالر اور کلِک کرنے کی شرح ہو تو"},
    {"vi", "VIETNAMESE", " adsense cho nội dung nhà cung cấp dịch vụ di động xác minh tín dụng thay đổi nhãn kg các ô xem chi phí cho từ chối các đơn đặt hàng dạng cấp dữ liệu ác minh trang web của bạn để xem"},
    {"so", "SOMALI", " a oo maanta bogga koobaad ugu qoran yahey beesha caalamka laakiin si kata oo beesha caalamku ula guntato soomaaliya waxa aan shaki ku jirin in aakhirataanka dadka soomaalida oo kaliya ay yihiin ku soomaaliya ka saari kara dhibka ay ku jirto"},
    {"id", "INDONESIAN", "sukiyaki wikipedia indonesia ensiklopedia bebas berbahasa bebas berbahasa indonesia langsung ke navigasi cari untuk pengertian lain dari sukiyaki lihat sukiyaki irisan tipis daging sapi sayur sayuran dan tahu di dalam panci besi yang dimasak di atas meja makan dengan cara direbus sukiyaki dimakan dengan mence"},
    {"fr", "FRENCH", "A accès aux chiens et aux frontaux qui lui ont été il peut consulter et modifier ses collections et exporter Cet article concerne le pays européen aujourd’hui appelé République française. Pour d’autres usages du nom France, Pour une aide rapide et effective, veuiller trouver votre aide dans le menu ci-dessus."},
  };

  @Before
  public void setUp() throws Exception {
    DetectorFactory.loadDefaultProfiles();
  }

  @Test
  public void testDetect() throws LangDetectException {
    Detector detector;
    for (int i = 0; i < testData.length; ++i) {
      detector = DetectorFactory.create();
      detector.append(testData[i][2]);
      List<Language> result = detector.getProbabilities();
      assertThat(result.size(), greaterThan(0));
      assertEquals(testData[i][0], result.get(0).lang);
      assertThat(result.get(0).prob, greaterThanOrEqualTo(0.5));
      assertThat(result.get(0).prob, lessThanOrEqualTo(1.0));
    }
  }
}
