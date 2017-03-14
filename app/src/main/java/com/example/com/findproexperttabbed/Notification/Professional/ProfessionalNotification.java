package com.example.com.findproexperttabbed.Notification.Professional;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.com.findproexperttabbed.Config;
import com.example.com.findproexperttabbed.JSONNotification;
import com.example.com.findproexperttabbed.Notification.Customer.ProfessionalResponded.ViewProfessionalResponded;
import com.example.com.findproexperttabbed.R;

import java.util.HashMap;
import java.util.Map;



public class ProfessionalNotification extends Fragment {


    private static final String JSON_NOTIFICATION_PROF_URL = "https://findproexpertcom.000webhostapp.com/fetch_not_cust_name.php";
    ListView pro_list;
    View view;
    ProgressDialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_professional_notification, container, false);
        pro_list = (ListView) view.findViewById(R.id.notification_prof_list);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final int workeri = sharedPreferences.getInt(Config.USER_0CCP_WORKER,-1);
        if(workeri==1){
            sendRequest();
        }
        else
        {
            Toast.makeText(getActivity(),"You are not a professional",Toast.LENGTH_SHORT).show();
        }
//to be edited
//        pro_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent=new Intent(getActivity(),ViewProfessionalResponded.class);
//                intent.putExtra("request_id",JSONNotification.request_id[position]);
//                intent.putExtra("index1",position); //ith position
//                startActivity(intent);
//
//            }
//        });

        return view;
    }

    private void sendRequest() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_NOTIFICATION_PROF_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(getActivity().getApplicationContext(), "Reached onResponse()" + response, Toast.LENGTH_SHORT).show();
                        try{
                            showJSON(response);
//                            Toast.makeText(getActivity(),""+response,Toast.LENGTH_SHORT).show();
                        }catch(Exception e){

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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Reading Response from server...");
        progressDialog.show();
    }

    private void showJSON(String json) {
        JSONNotification jsonNotification=new JSONNotification(json);
        jsonNotification.parseJSONforProfNot();
        try {
            NotForProfAdapter notForProfAdapter=new NotForProfAdapter(getActivity(),JSONNotification.fname,JSONNotification.lname,JSONNotification.request_prof);
            pro_list.setAdapter(notForProfAdapter);
        } catch (Exception e) {
            Log.d("Error", "Error in HomeActivity customer fragment custom adapter");
            e.printStackTrace();
        }
    }
}
