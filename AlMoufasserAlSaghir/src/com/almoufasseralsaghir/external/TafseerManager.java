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

	private ArrayList<Sura> part1_list = new ArrayList<Sura>();
	private ArrayList<Sura> part2_list = new ArrayList<Sura>();
	private ArrayList<Sura> part3_list = new ArrayList<Sura>();
	private ArrayList<Sura> part4_list = new ArrayList<Sura>();
	
	public String getDeviceID() {
		return deviceID;
	}


	
	private static TafseerManager mInstance = null;
	private static SharedPreferences settings;
	private SharedPreferences.Editor editor;

	private JSONParser jsonParser;
	private Context context ;
	
	
	public TafseerManager(Context context) {
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		editor = settings.edit();
		jsonParser = new JSONParser(); 
		deviceID = "DS" + Secure.getString(context.getContentResolver(),  Secure.ANDROID_ID).toUpperCase(Locale.US);

		this.context = context ;
		
////////////////////    PART 1   //////////////////////////////////////////////////////////////////////////////////////////		
	
		part1_list.add(new Sura("An-Naba", "nabaa", getSmallDrawable("nabaa"), getBigDrawable("nabaa")));
		part1_list.add(new Sura("An-Naazi'aat","naziaat", getSmallDrawable("naziaat"), getBigDrawable("naziaat"))) ;
		part1_list.add(new Sura("Abasa","abas", getSmallDrawable("abas"), getBigDrawable("abas"))) ;
		part1_list.add(new Sura("At-Takwir","takwir", getSmallDrawable("takwir"), getBigDrawable("takwir"))) ;
		part1_list.add(new Sura("Al-Infitaar","infitar", getSmallDrawable("infitar"), getBigDrawable("infitar")));
		part1_list.add(new Sura("Al-Mutaffifin","moutaffifin", getSmallDrawable("moutaffifin"), getBigDrawable("moutaffifin")));
		part1_list.add(new Sura("Al-Inshiqaaq","inchikak", getSmallDrawable("inchikak"), getBigDrawable("inchikak")));
		part1_list.add(new Sura("Al-Burooj","bourouj", getSmallDrawable("bourouj"), getBigDrawable("bourouj")));
		part1_list.add(new Sura("At-Taariq","tarek", getSmallDrawable("tarek"), getBigDrawable("tarek")));
		part1_list.add(new Sura("Al-A'laa","aala", getSmallDrawable("aala"), getBigDrawable("aala")));
		part1_list.add(new Sura("Al-Ghaashiya","ghachia", getSmallDrawable("ghachia"), getBigDrawable("ghachia")));
		part1_list.add(new Sura("Al-Fajr","fajr", getSmallDrawable("fajr"), getBigDrawable("fajr")));
		part1_list.add(new Sura("Al-Balad","balad", getSmallDrawable("balad"), getBigDrawable("balad")));
		part1_list.add(new Sura("Ash-Shams","shams", getSmallDrawable("shams"), getBigDrawable("shams")));
		part1_list.add(new Sura("Al-Lail","lail", getSmallDrawable("lail"), getBigDrawable("lail")));
		part1_list.add(new Sura("Ad-Dhuhaa","dhouha", getSmallDrawable("dhouha"), getBigDrawable("dhouha")));
		part1_list.add(new Sura("Ash-Sharh","inchirah", getSmallDrawable("inchirah"), getBigDrawable("inchirah")));
		part1_list.add(new Sura("At-Tin","tin", getSmallDrawable("tin"), getBigDrawable("tin")));
		part1_list.add(new Sura("Al-Alaq","alaq", getSmallDrawable("alaq"), getBigDrawable("alaq")));
		part1_list.add(new Sura("Al-Qadr","kadar", getSmallDrawable("kadar"), getBigDrawable("kadar")));
		part1_list.add(new Sura("Al-Bayyina","bayyina", getSmallDrawable("bayyina"), getBigDrawable("bayyina")));
		part1_list.add(new Sura("Az-Zalzala","zalzala", getSmallDrawable("zalzala"), getBigDrawable("zalzala")));
		part1_list.add(new Sura("Al-Aadiyaat","adyet", getSmallDrawable("adyet"), getBigDrawable("adyet")));
		part1_list.add(new Sura("Al-Qaari'a","quariaa", getSmallDrawable("quariaa"), getBigDrawable("quariaa")));
		part1_list.add(new Sura("At-Takaathur","takathor", getSmallDrawable("takathor"), getBigDrawable("takathor")));
		part1_list.add(new Sura("Al-Asr","asr", getSmallDrawable("asr"), getBigDrawable("asr")));
		part1_list.add(new Sura("Al-Humaza","homaza", getSmallDrawable("homaza"), getBigDrawable("homaza")));
		part1_list.add(new Sura("Al-Fil","fil", getSmallDrawable("fil"), getBigDrawable("fil")));
		part1_list.add(new Sura("Quraish","quraich", getSmallDrawable("quraich"), getBigDrawable("quraich")));
		part1_list.add(new Sura("Al-Maa'un","maaoun", getSmallDrawable("maaoun"), getBigDrawable("maaoun")));
		part1_list.add(new Sura("Al-Kawthar","kawthar", getSmallDrawable("kawthar"), getBigDrawable("kawthar")));
		part1_list.add(new Sura("Al-Kaafiroon","kafiroun", getSmallDrawable("kafiroun"), getBigDrawable("kafiroun")));
		part1_list.add(new Sura("An-Nasr","nasr", getSmallDrawable("nasr"), getBigDrawable("nasr")));
		part1_list.add(new Sura("Al-Masad","masad", getSmallDrawable("masad"), getBigDrawable("masad")));
		part1_list.add(new Sura("Al-Ikhlaas","ikhlas", getSmallDrawable("ikhlas"), getBigDrawable("ikhlas")));
		part1_list.add(new Sura("Al-Falaq","falaq", getSmallDrawable("falaq"), getBigDrawable("falaq")));
		part1_list.add(new Sura("An-Naas","ness", getSmallDrawable("ness"), getBigDrawable("ness")));

////////////////////  PART 2   //////////////////////////////////////////////////////////////////////////////////////////		
		
		part2_list.add(new Sura("Al-Mulk","molk", getSmallDrawable("molk"), getBigDrawable("molk")));
		part2_list.add(new Sura("Al-Qalam","kalam",getSmallDrawable("kalam"), getBigDrawable("kalam")));
		part2_list.add(new Sura("Al-Haaqqa","hakka", getSmallDrawable("hakka"), getBigDrawable("hakka")));
		part2_list.add(new Sura("Al-Ma'aarij","miaraj",getSmallDrawable("miaraj"), getBigDrawable("miaraj")));
		part2_list.add(new Sura("Nooh","nouh",getSmallDrawable("nouh"), getBigDrawable("nouh")));
		part2_list.add(new Sura("Al-Jinn","jinn", getSmallDrawable("jinn"), getBigDrawable("jinn")));
		part2_list.add(new Sura("Al-Muzzammil","mouzammil", getSmallDrawable("mouzammil"), getBigDrawable("mouzammil")));
		part2_list.add(new Sura("Al-Muddaththir","modathir", getSmallDrawable("modathir"), getBigDrawable("modathir")));
		part2_list.add(new Sura("Al-Qiyaama","qayimat", getSmallDrawable("qayimat"), getBigDrawable("qayimat")));
		part2_list.add(new Sura("Al-Insaan","insan", getSmallDrawable("insan"), getBigDrawable("insan")));
		part2_list.add(new Sura("Al-Mursalaat","morsilat", getSmallDrawable("morsilat"), getBigDrawable("morsilat")));
	
////////////////////      PART 3  //////////////////////////////////////////////////////////////////////////////////////////		
		
		part3_list.add(new Sura("Al-Mujaadila","moujedla",getSmallDrawable("moujedla"), getBigDrawable("moujedla")));
		part3_list.add(new Sura("Al-Hashr","hachr", getSmallDrawable("hachr"), getBigDrawable("hachr")));
		part3_list.add(new Sura("Al-Mumtahana","momtahina",getSmallDrawable("momtahina"), getBigDrawable("momtahina"))); 
		part3_list.add(new Sura("As-Saff","saff",getSmallDrawable("saff"), getBigDrawable("saff")));
		part3_list.add(new Sura("Al-Jumu'a","jomoa",getSmallDrawable("jomoa"), getBigDrawable("jomoa")));
		part3_list.add(new Sura("Al-Munaafiqoon","mounafiqoun",getSmallDrawable("mounafiqoun"), getBigDrawable("mounafiqoun")));
		part3_list.add(new Sura("At-Taghaabun","thaabin",getSmallDrawable("thaabin"), getBigDrawable("thaabin")));
		part3_list.add(new Sura("At-Talaaq","talek", getSmallDrawable("talek"), getBigDrawable("talek")));
		part3_list.add(new Sura("At-Tahrim","tahrim", getSmallDrawable("tahrim"), getBigDrawable("tahrim")));
		
////////////////////    PART 4  //////////////////////////////////////////////////////////////////////////////////////////		
		
		part4_list.add(new Sura("Qaaf","qaf", getSmallDrawable("qaf"), getBigDrawable("qaf")));
		part4_list.add(new Sura("Adh-Dhaariyat","dhariet",getSmallDrawable("dhariet"), getBigDrawable("dhariet")));
		part4_list.add(new Sura("At-Tur","tour",getSmallDrawable("tour"), getBigDrawable("tour")));
		part4_list.add(new Sura("An-Najm","najm", getSmallDrawable("najm"), getBigDrawable("najm")));
		part4_list.add(new Sura("Al-Qamar","qamar",getSmallDrawable("qamar"), getBigDrawable("qamar")));
		part4_list.add(new Sura("Ar-Rahmaan","rahmen", getSmallDrawable("rahmen"), getBigDrawable("rahmen")));
		part4_list.add(new Sura("Al-Waaqia","waqiaa", getSmallDrawable("waqiaa"), getBigDrawable("waqiaa")));
		part4_list.add(new Sura("Al-Hadid","hadid", getSmallDrawable("hadid"), getBigDrawable("hadid")));

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

	public String getSouraLabel(int part, int position) {

		String soura_name = "";
		Sura mySoura ;
		
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
		case 1:
			mySoura = part1_list.get(position);
			soura_name = mySoura.getLabel();
			break;
		case 2:
			mySoura = part2_list.get(position);
			soura_name = mySoura.getLabel();
			break;
		case 3:
			mySoura = part3_list.get(position);
			soura_name = mySoura.getLabel();
			break;
		case 4:
			mySoura = part4_list.get(position);
			soura_name = mySoura.getLabel();
			break;
		}

		return soura_name;
	}

	public void saveUser(User user) {
		editor.putInt("uid", user.getUid());
		editor.putString("udid", user.getUdid());
		editor.putString("name", user.getName());
		editor.putString("email", user.getEmail());
		editor.putString("twitter", user.getTwitter());
		editor.putString("facebook", user.getFacebook());
		editor.putString("follower1", user.getFollower1());
		editor.putString("follower2", user.getFollower2());
		editor.putString("follower3", user.getFollower3());

		editor.commit();
	}

	public static User getSavedUser() {
		User user = new User();

		user.setUid(settings.getInt("uid", -1));
		user.setUdid(settings.getString("udid", ""));
		user.setName(settings.getString("name", ""));
		user.setEmail(settings.getString("email", ""));
		user.setTwitter(settings.getString("twitter", ""));
		user.setFacebook(settings.getString("facebook", ""));
		user.setFollower1(settings.getString("follower1", ""));
		user.setFollower2(settings.getString("follower2", ""));
		user.setFollower3(settings.getString("follower3", ""));

		return user;
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
			user.setUid(Integer.parseInt(userObj.getString("uid")));
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
	public int registerUser(User user) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (user.getUid() != -1)
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
		int response = jsonParser.getIntegerFromUrl(URL_REGISTER, params);
		// return response
		return response;
	}

	/**
	 * function make update Request
	 * 
	 * @param user
	 * @return
	 */
	public int updateUser(User user) {
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
		int response = jsonParser.getIntegerFromUrl(URL_UPDATE, params);
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
	public int deleteUser(int uid, String udid, String email) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("User[uid]", String.valueOf(uid)));
		params.add(new BasicNameValuePair("User[udid]", udid));
		params.add(new BasicNameValuePair("User[email]", email));

		// getting JSON Object
		int response = jsonParser.getIntegerFromUrl(URL_DELETE, params);
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
	public int postActivity(String uid, String sura, String part,
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
		int response = jsonParser.getIntegerFromUrl(URL_ACTIVITY, params);
		// return response
		return response;
	}
}
