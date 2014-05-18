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

/**
 * @author H.L - admin
 *
 */
public class TafseerManager {

	static final String TAG = "BPAPrefsManager";
	
	private static final String URL_BASE 		= "https://islam.ws/tafseer/website/com/";
	private static final String URL_REGISTER 	= URL_BASE + "register";
	private static final String URL_LOGIN 		= URL_BASE + "login";
	private static final String URL_UPDATE	 	= URL_BASE + "update";
	private static final String URL_DELETE 		= URL_BASE + "delete";
	private static final String URL_ACTIVITY 	= URL_BASE + "activity";
	
	private static final String URL_TABLE 		= "https://islam.ws/tafseer/website/sura/apis?t=";
	
	private static final String TOKEN	 		= "qqsrW0oliakDZAAzecc_XSY7d";
	private String deviceID;
	
	public String getDeviceID() {
		return deviceID;
	}


	private static TafseerManager mInstance = null;
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;

	private JSONParser jsonParser;
	
	public TafseerManager(Context context) {
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		editor = settings.edit();
		jsonParser = new JSONParser(); 
		deviceID = "DS" + Secure.getString(context.getContentResolver(),  Secure.ANDROID_ID).toUpperCase(Locale.US);

	}

	public synchronized static TafseerManager getInstance(Context context) 
	{
		if (mInstance == null)
			mInstance = new TafseerManager(context);

		return mInstance;
	}
	
	public JSONObject getTableByNumber(int tableNb){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        JSONObject json = jsonParser.getJSONFromUrl(URL_TABLE.concat(String.valueOf(tableNb)), params);
        // return json
        return json;
    }  
	
	public void saveUser(User user){	
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
	
	public User getSavedUser(){
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
	
	public void showPopUp(Context context, int message) {

		//Alert dialogue
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		// set dialog message
		alertDialogBuilder
		.setMessage(context.getResources().getString(message))
		.setCancelable(false)
		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				return;
			}
		});
		// show it
		alertDialogBuilder.show();
	}
	
	public User parseUser(JSONObject userObj){
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
	 * @param email
	 * @return
	 */
	public JSONObject loginUser(String email){
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
     * @param user
     * @return
     */
    public int registerUser(User user){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(user.getUid() != -1)
        	 params.add(new BasicNameValuePair("User[uid]", String.valueOf(user.getUid())));
        
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
        int response = jsonParser.getIntegerFromUrl(URL_REGISTER, params);
        // return response
        return response;
    }
    
    
    /**
     * function make update Request
     * @param user
     * @return
     */
    public int updateUser(User user){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("User[uid]", String.valueOf(user.getUid())));
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
        int response = jsonParser.getIntegerFromUrl(URL_UPDATE, params);
        // return response
        return response;
    }
    
    
    /**
     * function make delete Request
     * @param uid
     * @param udid
     * @param email
     * @return
     */
    public int deleteUser(int uid, String udid, String email){
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
     * @param uid
     * @param sura
     * @param part
     * @param repeat
     * @param percentage
     * @param date
     * @param token
     * @return
     */
    public int postActivity(String uid, String sura, String part, String repeat, 
    		String percentage, String date){
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
