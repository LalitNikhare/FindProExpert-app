package com.example.com.findproexperttabbed.HomeScreen.Professional;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.com.findproexperttabbed.Config;
import com.example.com.findproexperttabbed.JSONProfessional;
import com.example.com.findproexperttabbed.R;
import com.example.com.findproexperttabbed.HomeScreen.Professional.ViewRequests.ViewRequestActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by faltu on 09-Feb-17.
 */

public class ProfessionalView extends Fragment {
    private static final String USERNAME = "username";
    View view;
    public static final String JSON_URL = "https://findproexpertcom.000webhostapp.com/fetch_requests.php";
    ListView request_list;
    ProgressDialog progressDialog;
    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.professional_fragment, container, false);
        request_list= (ListView) view.findViewById(R.id.request_list2);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final int workeri = sharedPreferences.getInt(Config.USER_0CCP_WORKER,-1);
        if(workeri!=1){
            sendRequest();
        }
        else
        {
            Toast.makeText(getActivity(),"You are not a professional",Toast.LENGTH_SHORT).show();
        }

        request_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),ViewRequestActivity.class);
                intent.putExtra("domain",JSONProfessional.names[i]);
                startActivity(intent);
            }
        });
        return view;
    }
    private void sendRequest() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                        progressDialog.dismiss();
                        //Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String ,String> params=new HashMap<String, String>();
                params.put(USERNAME,username);
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
        JSONProfessional pj = new JSONProfessional(json);
        pj.parseJSONforCustomer();
//        Toast.makeText(getActivity().getApplicationContext(), "No of objects :" + pj.worker.length, Toast.LENGTH_LONG).show();
//        for (int i = 0; i < pj.worker.length; i++) {//Chances of error here
//            Toast.makeText(getActivity().getApplicationContext(), i + " Name: " + JSONProfessional.names[i] + "Desc: " + pj.desc[i] + "No. of workers: " + pj.worker[i], Toast.LENGTH_SHORT).show();
//        }

        try {
            SeeRequests cl = new SeeRequests(getActivity(), JSONProfessional.names, JSONProfessional.desc, JSONProfessional.request,JSONProfessional.srno);
            request_list.setAdapter(cl);
        } catch (Exception e) {
            Log.d("Error", "Error in HomeActivity customer fragment custom adapter");
            e.printStackTrace();
        }

    }
}
