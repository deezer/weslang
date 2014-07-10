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

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class Cld2Test {
  String[][] testData = {
    {"ENGLISH", "confiscation of goods is assigned as the penalty part most of the courts consist of members and when it is necessary to bring public cases before a jury of members two courts combine for the purpose the most important cases of all are brought jurors or"},
    {"ARMENIAN", " ա յ եվ նա հիացած աչքերով նայում է հինգհարկանի շենքի տարօրինակ փոքրիկ քառակուսի պատուհաններին դեռ մենք շատ ենք հետամնաց ասում է նա այսպես է"},
    {"CHEROKEE", "ᎠᎢᏍᎩ ᎠᏟᎶᏍᏗ ᏥᏄᏍᏛᎩ ᎦᎫᏍᏛᏅᎯ ᎾᎥᎢ"},
    {"DHIVEHI", " ހިންދީ ބަހުން ވާހަކަ ދައްކާއިރު ދެވަނަ ބަހެއްގެ ގޮތުގައާއި އެނޫން ގޮތްގޮތުން ހިންދީ ބަހުން ވާހަކަ ދައްކާ މީހުންގެ އަދަދު މިލިއަނަށް"},
    {"GEORGIAN", " ა ბირთვიდან მიღებული ელემენტი მენდელეევის პერიოდულ სიტემაში გადაინაცვლებს ორი უჯრით"},
    {"GREEK", " ή αρνητική αναζήτηση λέξης κλειδιού καταστήστε τις μεμονωμένες λέξεις κλειδιά περισσότερο στοχοθετημένες με τη μετατροπή τους σε"},
    {"GUJARATI", " આના પરિણામ પ્રમાણસર ફોન્ટ અવતરણ ચિન્હવાળા પાઠને છુપાવો બધા સમૂહો શોધાયા હાલનો જ સંદેશ વિષયની"},
    {"INUKTITUT", "ᐃᑯᒪᒻᒪᑦ ᕿᓈᖏᓐᓇᓲᖑᒻᒪᑦ ᑎᑎᖅᑕᓕᒫᖅᓃᕕᑦ ᑎᑦᕆᐊᑐᓐᖏᑦᑕᑎᑦ ᑎᑎᖅᑕᑉᐱᑦ ᓯᕗᓂᖓᓂ ᑎᑎᖅᖃᖅ ᑎᑎᕆᐊᑐᓐᖏᑕᐃᑦ ᕿᓂᓲᖑᔪᒍᑦ ᑎᑎᖅᑕᓕᒫᖅᓃᕕᑦ"},
    {"KANNADA", " ಂಠಯ್ಯನವರು ತುಮಕೂರು ಜಿಲ್ಲೆಯ ಚಿಕ್ಕನಾಯಕನಹಳ್ಳಿ ತಾಲ್ಲೂಕಿನ ತೀರ್ಥಪುರ ವೆಂಬ ಸಾಧಾರಣ ಹಳ್ಳಿಯ ಶ್ಯಾನುಭೋಗರ"},
    {"KHMER", " ក ខ គ ឃ ង ច ឆ ជ ឈ ញ ដ ឋ ឌ ឍ ណ ត ថ ទ ធ ន ប ផ ព ភ ម យ រ ល វ ស ហ ឡ អ ឥ ឦ ឧ ឪ ឫ ឬ ឯ ឱ ទាំងអស់"},
    {"LAOTHIAN", " ກຫາທົ່ວທັງເວັບ ແລະໃນເວັບໄຮ້ສາຍ ທຳອິດໃຫ້ທຳການຊອກຫາກ່ອນ ຈາກນັ້ນ ໃຫ້ກົດປຸ່ມເມນູ ໃນໜ້າຜົນໄດ້"},
    {"LIMBU", "ᤁᤡᤖᤠᤳ ᤕᤠᤰᤌᤢᤱ ᤆᤢᤶᤗᤢᤱᤖᤧ ᤛᤥᤎᤢᤱᤃᤧᤴ ᤀᤡᤔᤠᤴᤛᤡᤱ ᤆᤧᤶᤈᤱᤗᤧ ᤁᤢᤔᤡᤱᤅᤥ ᤏᤠᤈᤡᤖᤡ ᤋᤱᤒᤣ ᥈᥆᥆᥉ ᤒᤠ ᤈᤏᤘᤖᤡ ᤗᤠᤏᤢᤀᤠᤱ ᤁ᤹ᤏᤠ ᤋᤱᤒᤣ ᤁᤠᤰ ᤏᤠ᤺ᤳᤋᤢ ᤕᤢᤖᤢᤒᤠ ᤀᤡᤔᤠᤴᤛᤡᤱ ᤋᤱᤃᤡᤵᤛᤡᤱ ᤌᤡᤶᤒᤣᤴ ᤂᤠᤃᤴ ᤛᤡᤛᤣ᤺ᤰᤗᤠ ᥇᥍ ᤂᤧᤴ ᤀᤡᤛᤡᤰ ᥇ ᤈᤏᤘᤖᤡ ᥈᥆᥆᥊ ᤀᤥ ᤏᤠᤛᤢᤵ ᤆᤥ᤺ᤰᤔᤠ ᤌᤡᤶᤒᤣ ᤋᤱᤃᤠᤶᤛᤡᤱᤗ ᤐᤳᤐᤠ ᤀᤡᤱᤄᤱ ᤘᤠ᤹"},
    {"MALAYALAM", " ം അങ്ങനെ ഞങ്ങള് അവരുടെ മുമ്പില് നിന്നു ഔടും ഉടനെ നിങ്ങള് പതിയിരിപ്പില് നിന്നു എഴുന്നേറ്റു"},
    {"ORIYA", "ଅକ୍ଟୋବର ଡିସେମ୍ବର"},
    {"PUNJABI", " ਂ ਦਿਨਾਂ ਵਿਚ ਭਾਈ ਸਾਹਿਬ ਦੀ ਬੁੱਚੜ ਗੋਬਿੰਦ ਰਾਮ ਨਾਲ ਅੜਫਸ ਚੱਲ ਰਹੀ ਸੀ ਗੋਬਿੰਦ ਰਾਮ ਨੇ ਭਾਈ ਸਾਹਿਬ ਦੀਆਂ ਭੈਣਾ"},
    {"SINHALESE", " අනුරාධ මිහිඳුකුල නමින් සකුරා ට ලිපියක් තැපෑලෙන් එවා තිබුණා කි ් රස්ටි ෂෙල්ටන් ප ් රනාන්දු ද"},
    {"SYRIAC", "ܐܕܪܝܣ ܓܛܘ ܫܘܪܝܐ ܡܢ ܦܪܢܣܐ ܡܢ ܐܣܦܢܝܐ ܚܐܪܘܬܐ ܒܐܕܪ ܒܢܝܣܢ ܫܛܝܚܘܬܐ ܟܠܢܝܐ ܡܝ̈ܐ ܒܥܠܡܐ"},
    {"TAGALOG", " ᜋᜇ᜔ ᜐᜓᜎᜆ᜔ ᜃ ᜈᜅ᜔ ᜊᜌ᜔ᜊᜌᜒᜈ᜔ ᜂᜉᜅ᜔᜔ ᜋᜐᜈᜌ᜔ ᜎᜅ᜔ ᜁᜐ ᜉᜅ᜔ ᜀᜃ᜔ᜎᜆ᜔ ᜆᜓᜅ᜔ᜃᜓᜎ᜔ ᜐ ᜊᜌ᜔ᜊᜌᜒᜈ᜔ ᜐ ᜆᜒᜅᜒᜈ᜔ ᜃᜓ"},
    {"TAMIL", " அங்கு ராஜேந்திர சோழனால் கட்டப்பட்ட பிரம்மாண்டமான சிவன் கோவில் ஒன்றும் உள்ளது தொகு"},
    {"TELUGU", " ఁ దనర జయించిన తత్వ మరసి చూడఁ దాన యగును రాజయోగి యిట్లు తేజరిల్లుచు నుండు విశ్వదాభిరామ వినర వేమ"},
    {"THAI", " กฏในการค้นหา หรือหน้าเนื้อหา หากท่านเลือกลงโฆษณา ท่านอาจจะปรับต้องเพิ่มงบประมาณรายวันตา"},
    {"Chinese", "产品的简报和公告 提交该申请后无法进行更改 请确认您的选择是正确的 对于要提交的图书 我确认 我是版权所有者或已得到版权所有者的授权 要更改您的国家 地区 请在此表的最上端更改您的"},
    {"ChineseT", " 之前為 帳單交易作業區 已變更 廣告內容 之前為 銷售代表 之前為 張貼日期為 百分比之前為 合約 為 目標對象條件已刪除 結束日期之前為"},
    {"Japanese", " このペ ジでは アカウントに指定された予算の履歴を一覧にしています それぞれの項目には 予算額と特定期間のステ タスが表示されます 現在または今後の予算を設定するには"},
    {"Korean", " 개별적으로 리포트 액세스 권한을 부여할 수 있습니다 액세스 권한 부여사용자에게 프로필 리포트에 액세스할 수 있는 권한을 부여하시려면 가용 프로필 상자에서 프로필 이름을 선택한 다음"},
    {"AFRIKAANS", " aam skukuza die naam beteken hy wat skoonvee of hy wat alles onderstebo keer wysig bosveldkampe boskampe is kleiner afgeleë ruskampe wat oor min fasiliteite beskik daar is geen restaurante of winkels nie en slegs oornagbesoekers word toegelaat bateleur"},
    {"ALBANIAN", " a do të kërkoni nga beogradi që të njohë pavarësinë e kosovës zoti thaçi prishtina është gati ta njoh pavarësinë e serbisë ndërsa natyrisht se do të kërkohet një gjë e tillë që edhe beogradi ta njoh shtetin e pavarur dhe sovran të"},
    {"ARABIC", "احتيالية بيع أي حساب"},
    {"AZERBAIJANI", " a az qalıb breyn rinq intellektual oyunu üzrə yarışın zona mərhələləri keçirilib miq un qalıqlarının dənizdən çıxarılması davam edir məhəmməd peyğəmbərin karikaturalarını çap edən qəzetin baş redaktoru iş otağında ölüb"},
    {"BASQUE", " a den eraso bat honen kontra hortaz eragiketa bakarrik behar dituen eraso batek aes apurtuko luke nahiz eta oraingoz eraso bideraezina izan gaur egungo teknologiaren mugak direla eta oraingoz kezka hauek alde batera utzi daitezke orain arteko indar"},
    {"BELARUSIAN", " а друкаваць іх не было тэхнічна магчыма бліжэй за вільню тым самым часам нямецкае кіраўніцтва прапаноўвала апроч ўвядзення лацінкі яе"},
    {"BENGALI", "গ্যালারির ৩৮ বছর পূর্তিতে মূল্যছাড় অর্থনীতি বিএনপির ওয়াক আউট তপন চৌধুরী হারবাল অ্যাসোসিয়েশনের সভাপতি আন্তর্জাতিক পরামর্শক বোর্ড দিয়ে শরিয়াহ্ ইনন্ডেক্স করবে সিএসই মালিকপক্ষের কান্না, শ্রমিকের অনিশ্চয়তা মতিঝিলে সমাবেশ নিষিদ্ধ: এফবিসিসিআইয়ের ধন্যবাদ বিনোদন বিশেষ প্রতিবেদন বাংলালিংকের গ্র্যান্ডমাস্টার সিজন-৩ ব্রাজিলে বিশ্বকাপ ফুটবল আয়োজনবিরোধী বিক্ষোভ দেশের নিরাপত্তার  চেয়ে অনেক বেশি সচেতন । প্রার্থীদের দক্ষতা  ও যোগ্যতার পাশাপাশি তারা জাতীয় ইস্যুগুলোতে প্রাধান্য দিয়েছেন । ” পাঁচটি সিটিতে ২০ লাখ ভোটারদের দিয়ে জাতীয় নির্বাচনে ৮ কোটি ভোটারদের সঙ্গে তুলনা করা যাবে কি একজন দর্শকের এমন প্রশ্নে জবাবে আব্দুল্লাহ আল নোমান বলেন , “ এই পাঁচটি সিটি কর্পোরেশন নির্বাচন দেশের পাঁচটি বড় বিভাগের প্রতিনিধিত্ব করছে । এছাড়া এখানকার ভোটার রা সবাই সচেতন । তারা"},
    {"BIHARI", "काल में उनका हमला से बचे खाती एहिजा भाग के अइले आ भोजपुर नाम से नगर बसवले. एकरा बारे में विस्तार से जानकारी नीचे दीहल गइल बा. बाकिर आश्चर्यजनक रूप से मालवा के राजा भोज के बिहार आवे आ भोजपुर नगर बसावे आ चाहे भोजपुरी के साथे उनकर कवनो संबंध होखे के कवनो जानकारी भोपाल के भोज संस्थान आ चाहे मध्य प्रदेश के इतिहासकार लोगन के तनिको नइखे. हालांकि ऊ सब लोग एह बात के मानत बा कि एकरा बारे में अबहीं तकले मूर्ति बनवइलें. राजा भोज के जवना जगहा पऽ वाग्देवी के दर्शन भइल रहे, ओही स्थान पऽ एह मूर्ति के स्थापना कइल गइल. अब अगर एह मंदिर के एह शिलालेख के तस्वीर {पृष्ठ संख्या 33 पऽ प्रकाशित} रउआ धेयान से देखीं तऽ एकरा पऽ कैथी लिपि में -सीताराम- लिखल साफ लउकत बा. कैथी भोजपुरी के बहुत प्रचलित लिपि रहल बिया. एकरा बारे में कवनो शंका संदेह बिहार-यूपी के जानकार लोगन में नइखे. एल. एस. एस. वो माले के लिखल पढ़ीं "},
    {"BULGARIAN", " а дума попада в състояние на изпитание ключовите думи с предсказана малко под то изискване на страниците за търсене в"},
    {"CATALAN", "al final en un únic lloc nhorabona l correu electrònic està concebut com a eina de productivitat aleshores per què perdre el temps arxivant missatges per després intentar recordar on els veu desar i per què heu d eliminar missatges importants per l"},
    {"CEBUANO", "Ang Sugbo usa sa mga labing ugmad nga lalawigan sa nasod. Kini ang sentro sa komersyo, edukasyon ug industriya sa sentral ug habagatang dapit sa kapupod-an. Ang mipadayag sa Sugbo isip ikapito nga labing nindot nga pulo sa , ang nag-inusarang pulo sa Pilipinas nga napasidunggan sa maong magasin sukad pa sa tuig"},
    {"CROATIAN", "Posljednja dva vladara su Kijaksar {Κυαξαρης; 625-585 prije Krista}, fraortov sin koji će proširiti teritorij Medije i Astijag. Kijaksar je imao kćer ili unuku koja se zvala Amitis a postala je ženom Nabukodonosora II. kojoj je ovaj izgradio Viseće vrtove Babilona. Kijaksar je modernizirao svoju vojsku i uništio Ninivu 612. prije Krista. Naslijedio ga je njegov sin, posljednji medijski kralj, Astijag, kojega je detronizirao {srušio sa vlasti} njegov unuk Kir Veliki. Zemljom su zavladali Perzijanci."},
    {"CZECH", " a akci opakujte film uložen vykreslit gmail tokio smazat obsah adresáře nelze načíst systémový profil jednotky smoot okud používáte pro určení polokoule značky z západ nebo v východ používejte nezáporné hodnoty zeměpisné délky nelze"},
    {"DANISH", " a z tallene og punktummer der er tilladte log ud angiv den ønskede adgangskode igen november gem personlige oplysninger kontrolspørgsmål det sidste tegn i dit brugernavn skal være et bogstav a z eller tal skriv de tegn du kan se i billedet nedenfor"},
    {"DUTCH", " a als volgt te werk om een configuratiebestand te maken sitemap gen py ebruik filters om de s op te geven die moeten worden toegevoegd of uitgesloten op basis van de opmaaktaal elke sitemap mag alleen de s bevatten voor een bepaalde opmaaktaal dit"},
    {"ENGLISH", " a backup credit card by visiting your billing preferences page or visit the adwords help centre for more details https adwords google com support bin answer py answer hl en we were unable to process the payment of for your outstanding google adwords"},
    {"ESTONIAN", " a niipea kui sinu maksimaalne igakuine krediidi limiit on meie poolt heaks kiidetud on sinu kohustuseks see krediidilimiit"},
    {"FINNISH", " a joilla olet käynyt tämä kerro meille kuka ä olet ei tunnistettavia käyttötietoja kuten virheraportteja käytetään google desktopin parantamiseen etsi näyttää mukautettuja uutisia google desktop keskivaihto leikkaa voit kaksoisnapsauttaa"},
    {"FRENCH", " a accès aux collections et aux frontaux qui lui ont été attribués il peut consulter et modifier ses collections et exporter des configurations de collection toutefois il ne peut pas créer ni supprimer des collections enfin il a accès aux fonctions"},
    {"GALICIAN", "  debe ser como mínimo taranto tendas de venda polo miúdo cociñas servizos bordado canadá viaxes parques de vehículos de recreo hotel oriental habitación recibir unha postal no enderezo indicado anteriormente"},
    {"GANDA", " abaana ba bani lukaaga mu ana mu babiri abaana ba bebayi lukaaga mu abiri mu basatu abaana ba azugaadi lukumi mu ebikumi bibiri mu abiri mu babiri abaana ba adonikamu lukaaga mu nltaaga mu mukaaga abaana ba biguvaayi enkumi bbiri mu ataano mu mukaaga"},
    {"GERMAN", " abschnitt ordner aktivieren werden die ordnereinstellungen im farbabschnitt deaktiviert öchten sie wirklich fortfahren eldtypen angeben optional n diesem schritt geben sie für jedesfeld aus dem datenset den typ an ieser schritt ist optional eldtypen"},
    {"HAITIAN_CREOLE", " ak pitit tout sosyete a chita se pou sa leta dwe pwoteje yo nimewo leta fèt pou li pwoteje tout paran ak pitit nan peyi a menm jan kit paran yo marye kit yo pa marye tout manman ki fè pitit leta fèt pou ba yo konkoul menm jan tou pou timoun piti ak pou"},
    {"HEBREW", " או לערוך את העדפות ההפצה אנא עקוב אחרי השלבים הבאים כנס לחשבון האישי שלך ב"},
    {"HINDI", " ं ऐडवर्ड्स विज्ञापनों के अनुभव पर आधारित हैं और इनकी मदद से आपको अपने विज्ञापनों का अधिकतम लाभ"},
    {"HMONG", " Kuv hlub koj txawm lub ntuj yuav si ntshi nphaus los kuv tsis ua siab nkaug txawm ntiab teb yuav si ntshi nphaus los kuv tseem ua lon tsaug vim kuv hlub koj tag lub siab"},
    {"HUNGARIAN", " a felhasználóim a google azonosító szöveget ikor látják a felhasználóim a google azonosító szöveget felhasználók a google azonosító szöveget fogják látni minden tranzakció után ha a vásárlását regisztrációját oldalunk"},
    {"ICELANDIC", " a afköst leitarorða þinna leitarorð neikvæð leitarorð auglýsingahópa byggja upp aðallista yfir ný leitarorð fyrir auglýsingahópana og skoða ítarleg gögn um árangur leitarorða eins og samkeppni auglýsenda og leitarmagn er krafist notkun"},
    {"INDONESIAN", "berdiri setelah pengurusnya yang berusia 83 tahun, Fayzrahman Satarov, mendeklarasikan diri sebagai nabi dan rumahnya sebagai negara Islam Satarov digambarkan sebagai mantan ulama Islam  tahun 1970-an. Pengikutnya didorong membaca manuskripnya dan kebanyakan dilarang meninggalkan tempat persembunyian bawah tanah di dasar gedung delapan lantai mereka. Jaksa membuka penyelidikan kasus kriminal pada kelompok itu dan menyatakan akan membubarkan kelompok kalau tetap melakukan kegiatan ilegal seperti mencegah anggotanya mencari bantuan medis atau pendidikan. Sampai sekarang pihak berwajib belum melakukan penangkapan meskipun polisi mencurigai adanya tindak kekerasan pada anak. Pengadilan selanjutnya akan memutuskan apakah anak-anak diizinkan tetap tinggal dengan orang tua mereka. Kazan yang berada sekitar 800 kilometer di timur Moskow merupakan wilayah Tatarstan yang"},
    {"IRISH", " a bhfuil na focail go léir i do cheist le fáil orthu ní gá ach focail breise a chur leis na cinn a cuardaíodh cheana chun an cuardach a bheachtú nó a chúngú má chuirtear focal breise isteach aimseofar fo aicme ar leith de na torthaí a fuarthas"},
    {"ITALIAN", " a causa di un intervento di manutenzione del sistema fino alle ore circa ora legale costa del pacifico del novembre le campagne esistenti continueranno a essere pubblicate come di consueto anche durante questo breve periodo di inattività ci scusiamo per"},
    {"JAVANESE", " account ten server niki kalian username meniko tanpo judul cacahe account nggonanmu wes pol pesen mu wes diguwak pesenan mu wes di simpen sante wae pesenan mu wes ke kirim mbuh tekan ora pesenan e ke kethok pesenan mu wes ke kirim mbuh tekan ora pesenan"},
    {"KINYARWANDA", " dore ibyo ukeneye kumenya ukwo watubona ibibazo byinshi abandi babaza ububonero byibibina google onjela ho izina dyikyibina kyawe onjela ho yawe mulugo kulaho ibyandiko byawe shyilaho tegula yawe tulubaka tukongeraho iyanya mishya buliko tulambula"},
    {"LATVIAN", " a gadskārtējā izpārdošana slēpošana jāņi atlaide izmaiņas trafikā kas saistītas ar sezonas izpārdošanu speciālajām atlaidēm u c ir parastas un atslēgvārdi kas ir populāri noteiktos laika posmos šajā laikā saņems lielāku klikšķu"},
    {"LITHUANIAN", " a išsijungia mano idėja dėl geriausio laiko po pastarųjų savo santykių pasimokiau penki dalykai be kurių negaliu gyventi mano miegamajame tu surasi ideali pora išsilavinimas aukštoji mokykla koledžas universitetas pagrindinis laipsnis metai"},
    {"MACEDONIAN", " гласовите коалицијата на вмро дпмне како партија со најмногу освоени гласови ќе добие евра а на сметката на коализијата за македонија"},
    {"MALAY", "pengampunan beramai-ramai supaya mereka pulang ke rumah masing-masing. Orang-orang besarnya enggan mengiktiraf sultan yang dilantik oleh Belanda sebagai Yang DiPertuan Selangor. Orang ramai pula tidak mahu menjalankan perniagaan bijih timah dengan Belanda, selagi raja yang berhak tidak ditabalkan. Perdagang yang lain dibekukan terus kerana untuk membalas jasa beliau yang membantu Belanda menentang Riau, Johor dan Selangor. Di antara tiga orang Sultan juga dipandang oleh rakyat sebagai seorang sultan yang paling gigih. 1 | 2 SULTAN Sebagai ganti Sultan Ibrahim ditabalkan Raja Muhammad iaitu Raja Muda. Walaupun baginda bukan anak isteri pertama bergelar Sultan Muhammad bersemayam di Kuala Selangor juga. Pentadbiran baginda yang lemah itu menyebabkan Kuala Selangor menjadi sarang ioleh Cina di Lukut tidak diambil tindakan, sedangkan baginda sendiri banyak berhutang kepada 1"},
    {"MALTESE", " ata ikteb messaġġ lil indirizzi differenti billi tagħżilhom u tagħfas il buttuna ikteb żid numri tfittxijja tal kotba mur print home kotba minn pagni ghal pagna minn ghall ktieb ta aċċessa stieden habib iehor grazzi it tim tal gruppi google"},
    {"MARATHI", "हैदराबाद  उच्चार ऐका {सहाय्य·माहिती}तेलुगू: హైదరాబాదు , उर्दू: حیدر آباد हे भारतातील आंध्र प्रदेश राज्याच्या राजधानीचे शहर आहे. हैदराबादची लोकसंख्या ७७ लाख ४० हजार ३३४ आहे. मोत्यांचे शहर अशी एकेकाळी ओळख असलेल्या या शहराला ऐतिहासिक, सांस्कृतिक आणि स्थापत्यशास्त्रीय वारसा लाभला आहे. १९९० नंतर शिक्षण आणि माहिती तंत्रज्ञान त्याचप्रमाणे औषधनिर्मिती आणि जैवतंत्रज्ञान क्षेत्रातील उद्योगधंद्यांची वाढ शहरात झाली. दक्षिण मध्य भारतातील पर्यटन आणि तेलुगू चित्रपटनिर्मितीचे हैदराबाद हे केंद्र आहे"},
    {"NEPALI", "अरू ठाऊँबाटपनि खुलेको छ यो खाता अर अरू ठाऊँबाटपनि खुलेको छ यो खाता अर ू"},
    {"NORWEGIAN", " a er obligatorisk tidsforskyvning plassering av katalogsøk planinformasjon loggfilbane gruppenavn kontoinformasjon passord domene gruppeinformasjon alle kampanjesporing alternativ bruker grupper oppgaveplanlegger oppgavehistorikk kontosammendrag antall"},
    {"PERSIAN", " آب خوردن عجله می کردند به جای باز ی کتک کاری می کردند و همه چيز مثل قبل بود فقط من ماندم و يک دنيا حرف و انتظار تا عاقبت رسيد احضاريه ی ای با"},
    {"POLISH", " a australii będzie widział inne reklamy niż użytkownik z kanady kierowanie geograficzne sprawia że reklamy są lepiej dopasowane do użytkownika twojej strony oznacza to także że możesz nie zobaczyć wszystkich reklam które są wyświetlane na"},
    {"PORTUGUESE", " a abit prevê que a entrada desses produtos estrangeiros no mercado têxtil e vestuário do brasil possa reduzir os preços em cerca de a partir de má notícia para os empresários que terão que lutar para garantir suas margens de lucro mas boa notícia"},
    {"ROMANIAN", " a anunţurilor reţineţi nu plătiţi pentru clicuri sau impresii ci numai atunci când pe site ul dvs survine o acţiune dorită site urile negative nu pot avea uri de destinaţie daţi instrucţiuni societăţii dvs bancare sau constructoare să"},
    {"ROMANIAN", "оперативэ а органелор ши институциилор екзекутиве ши а органелор жудичиаре але путерий де стат фиекэруй орган ал путерий де стат и се"},
    {"RUSSIAN", " а неправильный формат идентификатора дн назад"},
    {"SCOTS_GAELIC", " air son is gum bi casg air a h uile briosgaid no gum faigh thu brath nuair a tha briosgaid a tighinn gad rannsachadh ghoogle gu ceart mura bheil briosgaidean ceadaichte cuiridh google briosgaid dha do neach cleachdaidh fa leth tha google a cleachdadh"},
    {"SERBIAN", "балчак балчак на мапи србије уреди демографија у насељу балчак живи пунолетна становника а просечна старост становништва износи година"},
    {"SERBIAN", "Društvo | četvrtak 1.08.2013 | 13:43 Krade se i izvorska voda Izvor:  Gornji Milanovac -- U gružanskom selu Belo Polje prošle noći ukradeno je više od 10.000 litara kojima je obijen bazen. Bazen je bio zaključan i propisno obezbeđen."},
    {"SLOVAK", " a aktivovať reklamnú kampaň ak chcete kampaň pred spustením ešte prispôsobiť uložte ju ako šablónu a pokračujte v úprave vyberte si jednu z možností nižšie a kliknite na tlačidlo uložiť kampaň nastavenia kampane môžete ľubovoľne"},
    {"SLOVENIAN", " adsense stanje prijave za google adsense google adsense račun je bil začasno zamrznjen pozdravljeni hvala za vaše zanimanje v google adsense po pregledu vaše prijavnice so naši strokovnjaki ugotovili da spletna stran ki je trenutno povezana z vašim"},
    {"SPANISH", " a continuación haz clic en el botón obtener ruta también puedes desplazarte hasta el final de la página para cambiar tus opciones de búsqueda gráfico y detalles ésta es una lista de los vídeos que te recomendamos nuestras recomendaciones se basan"},
    {"SWAHILI", " a ujumbe mpya jumla unda tafuta na angalia vikundi vya kujadiliana na kushiriki mawazo iliyopangwa kwa tarehe watumiaji wapya futa orodha hizi lugha hoja vishikanisho vilivyo dhaminiwa ujumbe sanaa na tamasha toka udhibitisho wa neno kwa haraka fikia"},
    {"SWEDISH", " a bort objekt från google desktop post äldst meny öretag dress etaljer alternativ för vad är inne yaste google skrivbord plugin program för nyheter google visa nyheter som är anpassade efter de artiklar som du läser om du till exempel läser"},
    {"TAGALOG", " a na ugma sa google ay nakaka bantog sa gitna nang kliks na nangyayari sa pamamagitan nang ordinaryong paggagamit at sa kliks na likha nang pandaraya o hindi tunay na paggamit bunga nito nasasala namin ang mga kliks na hindi kailangan o hindi gusto nang"},
    {"TURKISH", " a ayarlarınızı görmeniz ve yönetmeniz içindir eğer kampanyanız için günlük bütçenizi gözden geçirebileceğiniz yeri arıyorsanız kampanya yönetimi ne gidin kampanyanızı seçin ve kampanya ayarlarını düzenle yi tıklayın sunumu"},
    {"UKRAINIAN", " а більший бюджет щоб забезпечити собі максимум прибутків від переходів відстежуйте свої об яви за датою географічним розташуванням"},
    {"URDU", " آپ کو کم سے کم ممکنہ رقم چارج کرتا ہے اس کی مثال کے طور پر فرض کریں اگر آپ کی زیادہ سے زیادہ قیمت فی کلِک امریکی ڈالر اور کلِک کرنے کی شرح ہو تو"},
    {"VIETNAMESE", " adsense cho nội dung nhà cung cấp dịch vụ di động xác minh tín dụng thay đổi nhãn kg các ô xem chi phí cho từ chối các đơn đặt hàng dạng cấp dữ liệu ác minh trang web của bạn để xem"},
    {"WELSH", " a chofrestru eich cyfrif ymwelwch a unwaith i chi greu eich cyfrif mi fydd yn cael ei hysbysu o ch cyfeiriad ebost newydd fel eich bod yn gallu cadw mewn cysylltiad drwy gmail os nad ydych chi wedi clywed yn barod am gmail mae n gwasanaeth gwebost"},
    {"YIDDISH", "און פאנטאזיע ער איז באקאנט צים מערסטן פאר זיינע באַלאַדעס ער האָט געוווינט אין ווארשע יעס פאריס ליווערפול און לאנדאן סוף כל סוף איז ער"},
    {"SOMALI", " a oo maanta bogga koobaad ugu qoran yahey beesha caalamka laakiin si kata oo beesha caalamku ula guntato soomaaliya waxa aan shaki ku jirin in aakhirataanka dadka soomaalida oo kaliya ay yihiin ku soomaaliya ka saari kara dhibka ay ku jirto"},
    {"IGBO", "Chineke bụ aha ọzọ ndï omenala Igbo kpọro Chukwu. Mgbe ndị bekee bịara, ha mee ya nke ndi Christian. N\'echiche ndi ekpere chi Omenala Ndi Igbo, Christianity, Judaism, ma Islam, Chineke nwere ọtụtụ utu aha, ma nwee nanị otu aha. Ụzọ abụọ e si akpọ aha ahụ bụ Jehovah ma Ọ bụ Yahweh. Na ọtụtụ Akwụkwọ Nsọ, e wepụla aha Chineke ma jiri utu aha bụ Onyenwe Anyị ma ọ bụ Chineke dochie ya. Ma mgbe e dere akwụkwọ nsọ, aha ahụ bụ Jehova pụtara n’ime ya, ihe dị ka ugboro pụkụ asaa{7,000}."},
    {"HAUSA", " a cikin a kan sakamako daga sakwannin a kan sakamako daga sakwannin daga ranar zuwa a kan sakamako daga guda daga ranar zuwa a kan sakamako daga shafukan daga ranar zuwa a kan sakamako daga guda a cikin last hour a kan sakamako daga guda daga kafar"},
    {"YORUBA", " abinibi han ikawe alantakun le ni opolopo ede abinibi ti a to lesese bi eniyan to fe lo se fe lati se atunse jowo mo pe awon oju iwe itakunagbaye miran ti ako ni oniruru ede abinibi le faragba nipa atunse ninu se iwadi blogs ni ori itakun agbaye ti e ba"},
    {"ZULU", " ana engu uma inkinga iqhubeka siza ubike kwi isexwayiso ngenxa yephutha lomlekeleli sikwazi ukubuyisela emuva kuphela imiphumela engaqediwe ukuthola imiphumela eqediwe zama ukulayisha kabusha leli khasi emizuzwini engu uma inkinga iqhubeka siza uthumele"},
    {"BOSNIAN", "Novi predsjednik Mešihata Islamske zajednice u Srbiji {IZuS} i muftija dr. Mevlud ef. Dudić izjavio je u intervjuu za Anadolu Agency {AA} kako je uvjeren da će doći do vraćanja jedinstva među muslimanima i unutar Islamske zajednice na prostoru Sandžaka, te da je njegova ruka pružena za povratak svih u okrilje Islamske zajednice u Srbiji nakon skoro sedam godina podjela u tom dijelu Srbije. Dudić je za predsjednika Mešihata IZ u Srbiji izabran 4. januara, a zvanična inauguracija će biti obavljena u prvoj polovini februara. Kako se očekuje, prisustvovat će joj i reisu-l-ulema Islamske zajednice u Srbiji Husein ef. Kavazović koji će i zvanično promovirati Dudića u novog prvog čovjeka IZ u Srbiji. Dudić će danas boraviti u prvoj zvaničnoj posjeti reisu Kavazoviću, što je njegov privi simbolični potez nakon imenovanja. "},
    {"INDONESIAN", "sukiyaki wikipedia indonesia ensiklopedia bebas berbahasa bebas berbahasa indonesia langsung ke navigasi cari untuk pengertian lain dari sukiyaki lihat sukiyaki irisan tipis daging sapi sayur sayuran dan tahu di dalam panci besi yang dimasak di atas meja makan dengan cara direbus sukiyaki dimakan dengan mence"},
    {"MALAY", "sukiyaki wikipedia bahasa melayu ensiklopedia bebas sukiyaki dari wikipedia bahasa melayu ensiklopedia bebas lompat ke navigasi gelintar sukiyaki sukiyaki  hirisan tipis daging lembu sayur sayuran dan tauhu di dalam periuk besi yang dimasak di atas meja makan dengan cara rebusan sukiyaki dimakan dengan mence"},
    {"FRENCH", "A accès aux chiens et aux frontaux qui lui ont été il peut consulter et modifier ses collections et exporter Cet article concerne le pays européen aujourd’hui appelé République française. Pour d’autres usages du nom France, Pour une aide rapide et effective, veuiller trouver votre aide dans le menu ci-dessus."},

    // This is just the "version marker":
    //{"AZERBAIJANI", "qpdbmrmxyzptlkuuddlrlrbas las les qpdbmrmxyzptlkuuddlrlrbas el la qpdbmrmxyzptlkuuddlrlrbas"},
  };

  @Test
  public void testDetect() {
    for (int i = 0; i < testData.length; ++i) {
      Result result = Cld2.detect(testData[i][1]);
      assertEquals(testData[i][0], result.language);
      assertThat(result.confidence, greaterThanOrEqualTo(0.5));
      assertThat(result.confidence, lessThanOrEqualTo(1.0));
    }
  }

  @Test
  public void testGetLanguageFromName() {
    assertEquals(Language.ENGLISH, Cld2.getLanguageFromName("ENGLISH"));
    assertEquals(Language.SPANISH, Cld2.getLanguageFromName("SPANISH"));
    assertEquals(Language.UNKNOWN_LANGUAGE, Cld2.getLanguageFromName("Unknown"));
    assertEquals(Language.JAPANESE, Cld2.getLanguageFromName("Japanese"));
  }

  @Test
  public void testGetLanguageName() {
    assertEquals("ENGLISH", Cld2.getLanguageName(Language.ENGLISH));
    assertEquals("SPANISH", Cld2.getLanguageName(Language.SPANISH));
    assertEquals("Unknown", Cld2.getLanguageName(Language.UNKNOWN_LANGUAGE));
    assertEquals("Japanese", Cld2.getLanguageName(Language.JAPANESE));
  }

  @Test
  public void testGetLanguageCode() {
    assertEquals("en", Cld2.getLanguageCode(Language.ENGLISH));
    assertEquals("es", Cld2.getLanguageCode(Language.SPANISH));
    assertEquals("un", Cld2.getLanguageCode(Language.UNKNOWN_LANGUAGE));
    assertEquals("ja", Cld2.getLanguageCode(Language.JAPANESE));
  }

  @Test
  public void testVersion() {
    assertEquals("V2.0 - 20140131", Cld2.version());
  }
}
