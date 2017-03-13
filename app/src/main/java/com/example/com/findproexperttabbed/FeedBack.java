package com.example.com.findproexperttabbed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.com.findproexperttabbed.Notification.Professional.NotForProfAdapter;

import java.util.HashMap;
import java.util.Map;

public class FeedBack extends AppCompatActivity {

    private static final String JSON_NOTIFICATION_PROF_URL = "https://findproexpertcom.000webhostapp.com/fetch_not_prof_name.php";
    ListView pro_list;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        pro_list = (ListView) findViewById(R.id.feedback_list);
        sendRequest();

        pro_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String prof_username=JSONNotification.prof_username[position];
                Intent intent=new Intent(FeedBack.this,FeedBackPage.class);
                intent.putExtra("username",prof_username);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

    }
    private void sendRequest() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_NOTIFICATION_PROF_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(getActivity().getApplicationContext(), "Reached onResponse()" + response, Toast.LENGTH_SHORT).show();
                        try{
                            //Toast.makeText(FeedBack.this,""+response,Toast.LENGTH_SHORT).show();
                            showJSON(response);

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity().getApplicationContext(), "Error in network " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("username",username);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(FeedBack.this);
        progressDialog.setMessage("Reading Response from server...");
        progressDialog.show();
    }

    private void showJSON(String json) {
        JSONNotification jsonNotification=new JSONNotification(json);
        jsonNotification.parseJSONforFeedBack();
        try {
            for(int i=0;i<JSONNotification.request.length;i++)
            {
                Toast.makeText(FeedBack.this,""+JSONNotification.request[i],Toast.LENGTH_SHORT).show();
            }
            FeedBackAdapter feedBackAdapter=new FeedBackAdapter(FeedBack.this,JSONNotification.fname,JSONNotification.lname,JSONNotification.request);
            pro_list.setAdapter(feedBackAdapter);
        } catch (Exception e) {
            Log.d("Error", "Error in HomeActivity customer fragment custom adapter");
            e.printStackTrace();
        }
    }
}
