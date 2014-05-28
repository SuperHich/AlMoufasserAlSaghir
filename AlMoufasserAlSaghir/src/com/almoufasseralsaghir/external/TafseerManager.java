package com.almoufasseralsaghir.external;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;

import com.example.almoufasseralsaghir.entity.Sura;
import com.example.almoufasseralsaghir.entity.User;

/**
 * @author H.L - admin
 * 
 */
public class TafseerManager {

	static final String TAG = "BPAPrefsManager";

	private static final String URL_BASE = "https://islam.ws/tafseer/website/com/";
	private static final String URL_REGISTER = URL_BASE + "register";
	private static final String URL_LOGIN = URL_BASE + "login";
	private static final String URL_UPDATE = URL_BASE + "update";
	private static final String URL_DELETE = URL_BASE + "delete";
	private static final String URL_ACTIVITY = URL_BASE + "activity";

	private static final String URL_TABLE = "https://islam.ws/tafseer/website/sura/apis?t=";

	private static final String TOKEN = "qqsrW0oliakDZAAzecc_XSY7d";
	private String deviceID;
	public String getDeviceID() {
		return deviceID;
	}
	
	public static final String SURA_ID = "sura_id";
	public static final String PART_NB = "part_nb";
	
	private ArrayList<Sura> part1_list = new ArrayList<Sura>();
	private ArrayList<Sura> part2_list = new ArrayList<Sura>();
	private ArrayList<Sura> part3_list = new ArrayList<Sura>();
	private ArrayList<Sura> part4_list = new ArrayList<Sura>();
	
	private static TafseerManager mInstance = null;
	private static SharedPreferences settings;
	private SharedPreferences.Editor editor;

	private JSONParser jsonParser;
	private Context context ;
	
	private User loggedInUser = new User();
	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
	
	public TafseerManager(Context context) {
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		editor = settings.edit();
		jsonParser = new JSONParser(); 
		deviceID = "DS" + Secure.getString(context.getContentResolver(),  Secure.ANDROID_ID).toUpperCase(Locale.US);

		this.context = context ;
		
////////////////////    PART 1   //////////////////////////////////////////////////////////////////////////////////////////		
	
		part1_list.add(new Sura(78, "An-Naba", "nabaa", getSmallDrawable("nabaa"), getBigDrawable("nabaa")));
		part1_list.add(new Sura(79, "An-Naazi'aat","naziaat", getSmallDrawable("naziaat"), getBigDrawable("naziaat"))) ;
		part1_list.add(new Sura(80, "Abasa","abas", getSmallDrawable("abas"), getBigDrawable("abas"))) ;
		part1_list.add(new Sura(81, "At-Takwir","takwir", getSmallDrawable("takwir"), getBigDrawable("takwir"))) ;
		part1_list.add(new Sura(82, "Al-Infitaar","infitar", getSmallDrawable("infitar"), getBigDrawable("infitar")));
		part1_list.add(new Sura(83, "Al-Mutaffifin","moutaffifin", getSmallDrawable("moutaffifin"), getBigDrawable("moutaffifin")));
		part1_list.add(new Sura(84, "Al-Inshiqaaq","inchikak", getSmallDrawable("inchikak"), getBigDrawable("inchikak")));
		part1_list.add(new Sura(85, "Al-Burooj","bourouj", getSmallDrawable("bourouj"), getBigDrawable("bourouj")));
		part1_list.add(new Sura(86, "At-Taariq","tarek", getSmallDrawable("tarek"), getBigDrawable("tarek")));
		part1_list.add(new Sura(87, "Al-A'laa","aala", getSmallDrawable("aala"), getBigDrawable("aala")));
		part1_list.add(new Sura(88, "Al-Ghaashiya","ghachia", getSmallDrawable("ghachia"), getBigDrawable("ghachia")));
		part1_list.add(new Sura(89, "Al-Fajr","fajr", getSmallDrawable("fajr"), getBigDrawable("fajr")));
		part1_list.add(new Sura(90, "Al-Balad","balad", getSmallDrawable("balad"), getBigDrawable("balad")));
		part1_list.add(new Sura(91, "Ash-Shams","shams", getSmallDrawable("shams"), getBigDrawable("shams")));
		part1_list.add(new Sura(92, "Al-Lail","lail", getSmallDrawable("lail"), getBigDrawable("lail")));
		part1_list.add(new Sura(93, "Ad-Dhuhaa","dhouha", getSmallDrawable("dhouha"), getBigDrawable("dhouha")));
		part1_list.add(new Sura(94, "Ash-Sharh","inchirah", getSmallDrawable("inchirah"), getBigDrawable("inchirah")));
		part1_list.add(new Sura(95, "At-Tin","tin", getSmallDrawable("tin"), getBigDrawable("tin")));
		part1_list.add(new Sura(96, "Al-Alaq","alaq", getSmallDrawable("alaq"), getBigDrawable("alaq")));
		part1_list.add(new Sura(97, "Al-Qadr","kadar", getSmallDrawable("kadar"), getBigDrawable("kadar")));
		part1_list.add(new Sura(98, "Al-Bayyina","bayyina", getSmallDrawable("bayyina"), getBigDrawable("bayyina")));
		part1_list.add(new Sura(99, "Az-Zalzala","zalzala", getSmallDrawable("zalzala"), getBigDrawable("zalzala")));
		part1_list.add(new Sura(100, "Al-Aadiyaat","adyet", getSmallDrawable("adyet"), getBigDrawable("adyet")));
		part1_list.add(new Sura(101, "Al-Qaari'a","quariaa", getSmallDrawable("quariaa"), getBigDrawable("quariaa")));
		part1_list.add(new Sura(102, "At-Takaathur","takathor", getSmallDrawable("takathor"), getBigDrawable("takathor")));
		part1_list.add(new Sura(103, "Al-Asr","asr", getSmallDrawable("asr"), getBigDrawable("asr")));
		part1_list.add(new Sura(104, "Al-Humaza","homaza", getSmallDrawable("homaza"), getBigDrawable("homaza")));
		part1_list.add(new Sura(105, "Al-Fil","fil", getSmallDrawable("fil"), getBigDrawable("fil")));
		part1_list.add(new Sura(106, "Quraish","quraich", getSmallDrawable("quraich"), getBigDrawable("quraich")));
		part1_list.add(new Sura(107, "Al-Maa'un","maaoun", getSmallDrawable("maaoun"), getBigDrawable("maaoun")));
		part1_list.add(new Sura(108, "Al-Kawthar","kawthar", getSmallDrawable("kawthar"), getBigDrawable("kawthar")));
		part1_list.add(new Sura(109, "Al-Kaafiroon","kafiroun", getSmallDrawable("kafiroun"), getBigDrawable("kafiroun")));
		part1_list.add(new Sura(110, "An-Nasr","nasr", getSmallDrawable("nasr"), getBigDrawable("nasr")));
		part1_list.add(new Sura(111, "Al-Masad","masad", getSmallDrawable("masad"), getBigDrawable("masad")));
		part1_list.add(new Sura(112, "Al-Ikhlaas","ikhlas", getSmallDrawable("ikhlas"), getBigDrawable("ikhlas")));
		part1_list.add(new Sura(113, "Al-Falaq","falaq", getSmallDrawable("falaq"), getBigDrawable("falaq")));
		part1_list.add(new Sura(114, "An-Naas","ness", getSmallDrawable("ness"), getBigDrawable("ness")));

////////////////////  PART 2   //////////////////////////////////////////////////////////////////////////////////////////		
		
		part2_list.add(new Sura(67, "Al-Mulk","molk", getSmallDrawable("molk"), getBigDrawable("molk")));
		part2_list.add(new Sura(68, "Al-Qalam","kalam",getSmallDrawable("kalam"), getBigDrawable("kalam")));
		part2_list.add(new Sura(69, "Al-Haaqqa","hakka", getSmallDrawable("hakka"), getBigDrawable("hakka")));
		part2_list.add(new Sura(70, "Al-Ma'aarij","miaraj",getSmallDrawable("miaraj"), getBigDrawable("miaraj")));
		part2_list.add(new Sura(71, "Nooh","nouh",getSmallDrawable("nouh"), getBigDrawable("nouh")));
		part2_list.add(new Sura(72, "Al-Jinn","jinn", getSmallDrawable("jinn"), getBigDrawable("jinn")));
		part2_list.add(new Sura(73, "Al-Muzzammil","mouzammil", getSmallDrawable("mouzammil"), getBigDrawable("mouzammil")));
		part2_list.add(new Sura(74, "Al-Muddaththir","modathir", getSmallDrawable("modathir"), getBigDrawable("modathir")));
		part2_list.add(new Sura(75, "Al-Qiyaama","qayimat", getSmallDrawable("qayimat"), getBigDrawable("qayimat")));
		part2_list.add(new Sura(76, "Al-Insaan","insan", getSmallDrawable("insan"), getBigDrawable("insan")));
		part2_list.add(new Sura(77, "Al-Mursalaat","morsilat", getSmallDrawable("morsilat"), getBigDrawable("morsilat")));
	
////////////////////      PART 3  //////////////////////////////////////////////////////////////////////////////////////////		
		
		part3_list.add(new Sura(58, "Al-Mujaadila","moujedla",getSmallDrawable("moujedla"), getBigDrawable("moujedla")));
		part3_list.add(new Sura(59, "Al-Hashr","hachr", getSmallDrawable("hachr"), getBigDrawable("hachr")));
		part3_list.add(new Sura(60, "Al-Mumtahana","momtahina",getSmallDrawable("momtahina"), getBigDrawable("momtahina"))); 
		part3_list.add(new Sura(61, "As-Saff","saff",getSmallDrawable("saff"), getBigDrawable("saff")));
		part3_list.add(new Sura(62, "Al-Jumu'a","jomoa",getSmallDrawable("jomoa"), getBigDrawable("jomoa")));
		part3_list.add(new Sura(63, "Al-Munaafiqoon","mounafiqoun",getSmallDrawable("mounafiqoun"), getBigDrawable("mounafiqoun")));
		part3_list.add(new Sura(64, "At-Taghaabun","thaabin",getSmallDrawable("thaabin"), getBigDrawable("thaabin")));
		part3_list.add(new Sura(65, "At-Talaaq","talek", getSmallDrawable("talek"), getBigDrawable("talek")));
		part3_list.add(new Sura(66, "At-Tahrim","tahrim", getSmallDrawable("tahrim"), getBigDrawable("tahrim")));
		
////////////////////    PART 4  //////////////////////////////////////////////////////////////////////////////////////////		
		
		part4_list.add(new Sura(50, "Qaaf","qaf", getSmallDrawable("qaf"), getBigDrawable("qaf")));
		part4_list.add(new Sura(51, "Adh-Dhaariyat","dhariet",getSmallDrawable("dhariet"), getBigDrawable("dhariet")));
		part4_list.add(new Sura(52, "At-Tur","tour",getSmallDrawable("tour"), getBigDrawable("tour")));
		part4_list.add(new Sura(53, "An-Najm","najm", getSmallDrawable("najm"), getBigDrawable("najm")));
		part4_list.add(new Sura(54, "Al-Qamar","qamar",getSmallDrawable("qamar"), getBigDrawable("qamar")));
		part4_list.add(new Sura(55, "Ar-Rahmaan","rahmen", getSmallDrawable("rahmen"), getBigDrawable("rahmen")));
		part4_list.add(new Sura(56, "Al-Waaqia","waqiaa", getSmallDrawable("waqiaa"), getBigDrawable("waqiaa")));
		part4_list.add(new Sura(57, "Al-Hadid","hadid", getSmallDrawable("hadid"), getBigDrawable("hadid")));

	}

	public int getBigDrawable(String label) {
	
		int drawableResourceId = context.getResources().getIdentifier("e5_title_sourat_"+label, "drawable", context.getPackageName());
		return drawableResourceId;
	
	}
	
	public int getSmallDrawable(String label) {
		int drawableResourceId = 0;
//		
//		
//		
//		switch (part){
//		case 1 : drawableResourceId = context.getResources().getIdentifier("p"+part+"_s5_"+label, "drawable", context.getPackageName());
//		break  ;
//		case 2 : drawableResourceId = context.getResources().getIdentifier("p2_s5_"+label, "drawable", context.getPackageName());
//		break  ;
//		case 3 : drawableResourceId = context.getResources().getIdentifier("p3_s5_"+label, "drawable", context.getPackageName());
//		break  ;
//		case 4 : drawableResourceId = context.getResources().getIdentifier("p4_s5_"+label, "drawable", context.getPackageName());
//		break  ;}
//		
		return drawableResourceId;
	}
	
	public synchronized static TafseerManager getInstance(Context context) {
		if (mInstance == null)
			mInstance = new TafseerManager(context);

		return mInstance;
	}

	public JSONObject getTableByNumber(int tableNb) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		JSONObject json = jsonParser.getJSONFromUrl(
				URL_TABLE.concat(String.valueOf(tableNb)), params);
		// return json
		return json;
	}
	
	public Sura getSuraById(int id){
		
		ArrayList<Sura> allLists = new ArrayList<Sura>();
		allLists.addAll(part1_list);
		allLists.addAll(part2_list);
		allLists.addAll(part3_list);
		allLists.addAll(part4_list);
		
		for(Sura sura : allLists){
			if(sura.getSuraId() == id)
				return sura;
		}
		
		return null;
	}

	public Sura getSouraLabel(int part, int position) {

//		String soura_name = "";
//		Sura mySoura ;
		
//		String[] part1_list = { "nabaa", "naziaat", "abas", "takwir",
//				"infitar", "moutaffifin", "inchikak", "bourouj", "tarek",
//				"aala", "ghachia", "fajr", "balad", "shams", "lail", "dhouha",
//				"inchirah", "tin", "alaq", "kadar", "bayyina", "zalzala",
//				"adyet", "quariaa", "takathor", "asr", "homaza", "fil",
//				"quraich", "maaoun", "kawthar", "kafiroun", "nasr", "masad",
//				"ikhlas", "falaq", "ness" };
//		String[] part2_list = { "molk", "kalam", "hakka", "miaraj", "nouh",
//				"jinn", "mouzammil", "modathir", "qayimat", "insan", "morsilat" };
//
//		String[] part3_list = { "moujedla", "hachr", "momtahina", "saff",
//				"jomoa", "mounafiqoun", "thaabin", "talek", "tahrim" };
//		
//		String[] part4_list = { "qaf", "dhariet", "tour", "najm", "qamar",
//				"rahmen", "waqiaa", "hadid" };

		switch (part) {
		case 0:
			return part1_list.get(position);
		case 1:
			return part2_list.get(position);
		case 2:
			return part3_list.get(position);
		case 3:
			return part4_list.get(position);
		}

		return null;
	}
	
	public void setCurrentJuz2(int juz2){
		editor.putInt("juz2", juz2);
		editor.commit();
	}
	
	public int getCurrentJuz2(){
		return settings.getInt("juz2", 0);
	}
	
	public void setCurrentSura(int sura){
		editor.putInt("sura", sura);
		editor.commit();
	}
	
	public int getCurrentSura(){
		return settings.getInt("sura", 0);
	}
	
	public void setCurrentSuraPart(int suraPart){
		editor.putInt("suraPart", suraPart);
		editor.commit();
	}
	
	public int getCurrentSuraPart(){
		return settings.getInt("suraPart", 0);
	}

	public void showPopUp(Context context, int message) {

		// Alert dialogue
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		// set dialog message
		alertDialogBuilder
				.setMessage(context.getResources().getString(message))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						return;
					}
				});
		// show it
		alertDialogBuilder.show();
	}

	public User parseUser(JSONObject userObj) {
		User user = new User();

		try {
			user.setUid(userObj.getString("uid"));
			user.setName(userObj.getString("udid"));
			user.setName(userObj.getString("name"));
			user.setEmail(userObj.getString("email"));
			user.setTwitter(userObj.getString("twitter"));
			user.setFacebook(userObj.getString("facebook"));
			user.setFollower1(userObj.getString("follower1"));
			user.setFollower2(userObj.getString("follower2"));
			user.setFollower3(userObj.getString("follower3"));

			return user;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * function make Login Request
	 * 
	 * @param email
	 * @return
	 */
	public JSONObject loginUser(String email) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("User[udid]", deviceID));
		params.add(new BasicNameValuePair("User[email]", email));
		params.add(new BasicNameValuePair("token", TOKEN));

		JSONObject json = jsonParser.getJSONFromUrl(URL_LOGIN, params);
		// return json
		return json;
	}

	/**
	 * function make register Request
	 * 
	 * @param user
	 * @return
	 */
	public String registerUser(User user) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (user.getUid() != null)
			params.add(new BasicNameValuePair("User[uid]", String.valueOf(user
					.getUid())));

		params.add(new BasicNameValuePair("User[udid]", user.getUdid()));
		params.add(new BasicNameValuePair("User[name]", user.getName()));
		params.add(new BasicNameValuePair("User[email]", user.getEmail()));
		params.add(new BasicNameValuePair("User[twitter]", user.getTwitter()));
		params.add(new BasicNameValuePair("User[facebook]", user.getFacebook()));
		params.add(new BasicNameValuePair("User[follower1]", user.getFollower1()));
		params.add(new BasicNameValuePair("User[follower2]", user.getFollower2()));
		params.add(new BasicNameValuePair("User[follower3]", user.getFollower3()));
		params.add(new BasicNameValuePair("token", TOKEN));

		// getting Integer Value
		String response = jsonParser.getIntegerFromUrl(URL_REGISTER, params);
		// return response
		return response;
	}

	/**
	 * function make update Request
	 * 
	 * @param user
	 * @return
	 */
	public String updateUser(User user) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("User[uid]", String.valueOf(user
				.getUid())));
		params.add(new BasicNameValuePair("User[udid]", user.getUdid()));
		params.add(new BasicNameValuePair("User[name]", user.getName()));
		params.add(new BasicNameValuePair("User[email]", user.getEmail()));
		params.add(new BasicNameValuePair("User[twitter]", user.getTwitter()));
		params.add(new BasicNameValuePair("User[facebook]", user.getFacebook()));
		params.add(new BasicNameValuePair("User[follower1]", user
				.getFollower1()));
		params.add(new BasicNameValuePair("User[follower2]", user
				.getFollower2()));
		params.add(new BasicNameValuePair("User[follower3]", user
				.getFollower3()));
		params.add(new BasicNameValuePair("token", TOKEN));

		// getting Integer Value
		String response = jsonParser.getIntegerFromUrl(URL_UPDATE, params);
		// return response
		return response;
	}

	/**
	 * function make delete Request
	 * 
	 * @param uid
	 * @param udid
	 * @param email
	 * @return
	 */
	public String deleteUser(int uid, String udid, String email) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("User[uid]", String.valueOf(uid)));
		params.add(new BasicNameValuePair("User[udid]", udid));
		params.add(new BasicNameValuePair("User[email]", email));

		// getting JSON Object
		String response = jsonParser.getIntegerFromUrl(URL_DELETE, params);
		// return json
		return response;
	}

	/**
	 * function make user activity Request
	 * 
	 * @param uid
	 * @param sura
	 * @param part
	 * @param repeat
	 * @param percentage
	 * @param date
	 * @param token
	 * @return
	 */
	public String postActivity(String uid, String sura, String part,
			String repeat, String percentage, String date) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Activity[uid]", uid));
		params.add(new BasicNameValuePair("Activity[sura]", sura));
		params.add(new BasicNameValuePair("Activity[part]", part));
		params.add(new BasicNameValuePair("Activity[repeat]", repeat));
		params.add(new BasicNameValuePair("Activity[percentage]", percentage));
		params.add(new BasicNameValuePair("Activity[date]", date));
		params.add(new BasicNameValuePair("token", TOKEN));

		// getting Integer Value
		String response = jsonParser.getIntegerFromUrl(URL_ACTIVITY, params);
		// return response
		return response;
	}
}
