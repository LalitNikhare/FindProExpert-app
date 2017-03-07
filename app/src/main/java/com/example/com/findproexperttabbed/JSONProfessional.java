package com.example.com.findproexperttabbed;

/**
 * Created by faltu on 09-Feb-17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONProfessional {
    public static String[] prof;
    public static String[] desc;
    public static String[] names;
    public static int[] worker;
    public static int[] request;

//    public static ArrayList<String> desc_list,names_list;
//    public static ArrayList<Integer> worker_list;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_DESC = "profession_desc";
    public static final String KEY_NAME = "profession_name";
    public static final String KEY_WORKER = "worker";
    public static final String KEY_REQUEST = "request";

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

            desc = new String[users.length()];
            names = new String[users.length()];
            request = new int[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);
                desc[i] = jo.getString(KEY_DESC);
                names[i] = jo.getString(KEY_NAME);
                request[i] = Integer.parseInt(jo.getString(KEY_REQUEST));
//                names_list.add(jo.getString(KEY_NAME));
//                desc_list.add(jo.getString(KEY_DESC));
//                worker_list.add(Integer.parseInt(jo.getString(KEY_WORKER)));
            }
        } catch (JSONException e) {
            Log.d("Error", "Bug in JSONProfessional");
            e.printStackTrace();
        }
    }


}

