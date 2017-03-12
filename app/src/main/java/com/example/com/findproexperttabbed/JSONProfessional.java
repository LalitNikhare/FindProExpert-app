package com.example.com.findproexperttabbed;

/**
 * Created by faltu on 09-Feb-17.
 */

import android.util.IntProperty;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONProfessional {
    private static final String KEY_SRNO = "srno";
    private static final String KEY_CUST_USERNAME = "username";
    public static String[] prof;
    public static String[] desc;
    public static String[] names;
    public static String[] view_request;
    public static String[] view_request_prof;
    public static String[] view_request_prof2;
    public static int[] worker;
    public static int[] request;
    public static int[] srno;
    public static String[] cust_username;

    public static String profile_fname;
    public static String profile_lname;
    public static String profile_email;
    public static String profile_mobile;
    public static String profile_username;
    public static String[] profile_skills;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_DESC = "profession_desc";
    public static final String KEY_NAME = "profession_name";
    public static final String KEY_WORKER = "worker";
    public static final String KEY_REQUEST = "request";
    public static final String KEY_VIEW_REQUEST = "view_request";
    //public static  final String JSON_ARRAY_PROF="result2";

    private JSONArray users = null;
    private String json;

    public JSONProfessional(String json) {
        this.json = json;
    }

    public void parseJSONforProfessional() {
        JSONObject jsonObject;
        try {

            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);
            prof = new String[users.length()];
            desc = new String[users.length()];
            names = new String[users.length()];
            worker = new int[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);
                desc[i] = jo.getString(KEY_DESC);
                names[i] = jo.getString(KEY_NAME);
                worker[i] = Integer.parseInt(jo.getString(KEY_WORKER));
                prof[i] = jo.getString(KEY_NAME);
//                names_list.add(jo.getString(KEY_NAME));
//                desc_list.add(jo.getString(KEY_DESC));
//                worker_list.add(Integer.parseInt(jo.getString(KEY_WORKER)));
            }
        } catch (JSONException e) {
            Log.d("Error", "Bug in JSONProfessional");
            e.printStackTrace();
        }
    }

    public void parseJSONforCustomer() {
        JSONObject jsonObject;
        try {

            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);
            srno=new int[users.length()];
            desc = new String[users.length()];
            names = new String[users.length()];
            request = new int[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);
                desc[i] = jo.getString(KEY_DESC);
                names[i] = jo.getString(KEY_NAME);
                request[i] = Integer.parseInt(jo.getString(KEY_REQUEST));
                srno[i]= Integer.parseInt(jo.getString(KEY_SRNO));
//                names_list.add(jo.getString(KEY_NAME));
//                desc_list.add(jo.getString(KEY_DESC));
//                worker_list.add(Integer.parseInt(jo.getString(KEY_WORKER)));
            }
        } catch (JSONException e) {
            Log.d("Error", "Bug in JSONProfessional");
            e.printStackTrace();
        }
    }

    public void parseJSONforViewRequest(){
        JSONObject jsonObject;
        try {

            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);
            view_request = new String[users.length()];
            srno=new int[users.length()];
            cust_username=new String[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);
                view_request[i] = jo.getString(KEY_VIEW_REQUEST);
                cust_username[i]=jo.getString(KEY_CUST_USERNAME);
                srno[i]= Integer.parseInt(jo.getString(KEY_SRNO));
            }
        } catch (JSONException e) {
            Log.d("Error", "Bug in JSONProfessional");
            e.printStackTrace();
        }
    }
    public void parseJSONforProfileDetails(){
        JSONObject jsonObject;
        try {

            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);
            //view_request = new String[users.length()];
            //srno=new int[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);
                //view_request_prof[i] = jo.getString(KEY_VIEW_REQUEST);
                //srno[i]= Integer.parseInt(jo.getString(KEY_SRNO));
                profile_fname= jo.getString("first_name");
                profile_lname= jo.getString("last_name");
                profile_email= jo.getString("email");
                profile_mobile= jo.getString("mobile");
                profile_username= jo.getString("username");
            }



        } catch (JSONException e) {
            Log.d("Error", "Bug in JSONProfessional");
            e.printStackTrace();
        }
    }

    public void parseJSONforProfileSkillDetails(){
        JSONObject jsonObject;
        try {

            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);
            //view_request = new String[users.length()];
            profile_skills=new String[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);
                //view_request_prof[i] = jo.getString(KEY_VIEW_REQUEST);
                //srno[i]= Integer.parseInt(jo.getString(KEY_SRNO));
//                profile_fname= jo.getString("first_name");
//                profile_lname= jo.getString("last_name");
//                profile_email= jo.getString("email");
//                profile_mobile= jo.getString("mobile");
//                profile_username= jo.getString("username");
                  profile_skills[i]=jo.getString("profession_name");

            }



        } catch (JSONException e) {
            Log.d("Error", "Bug in JSONProfessional");
            e.printStackTrace();
        }
    }

}

