package com.example.com.findproexperttabbed.HomeScreen.Customer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by faltu on 09-Feb-17.
 */

public class CustomerView extends Fragment {
    public static final String JSON_URL = "https://findproexpertcom.000webhostapp.com/fetch_professions.php";
    private static final String PLACE_REQUEST_URL = "https://findproexpertcom.000webhostapp.com/place_request.php";
    private static final String DOMAIN = "domain";
    private static final String PLACE_REQUEST = "request";
    private static final String USER_NAME = "username";
    private static final String POSITION = "position";
    ListView pro_list;
    View view;
    ProgressDialog progressDialog;
    int image[]={R.drawable.webdev,R.drawable.appdev,R.drawable.mobdev,R.drawable.photography,R.drawable.videdit,R.drawable.graphicsandanimation};
    String request,domain,pos;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.customer_fragment, container, false);
        pro_list = (ListView) view.findViewById(R.id.pro_list1);
        sendRequest();
        pro_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_for_customer);
                domain=JSONProfessional.prof[position].toString();
                pos= String.valueOf(position);

                Button confirm, cancel;
                final EditText user_request;

                confirm= (Button) dialog.findViewById(R.id.request_confirm);
                cancel= (Button) dialog.findViewById(R.id.request_cancel);
                user_request= (EditText) dialog.findViewById(R.id.customer_request);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        request=user_request.getText().toString().trim();
                        //call to placeRequest()
                        placeRequest();
                        Toast.makeText(getActivity().getApplicationContext(),"Request confirmed",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity().getApplicationContext(),"Request cancelled",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.setTitle("Place Request");
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        return view;
    }

    private void sendRequest() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(getActivity().getApplicationContext(), "Reached onResponse()" + response, Toast.LENGTH_SHORT).show();
                        showJSON(response);
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity().getApplicationContext(), "Error in network " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Reading Response from server...");
        progressDialog.show();
    }

    private void showJSON(String json) {
        JSONProfessional pj = new JSONProfessional(json);
        pj.parseJSONforProfessional();
        try {
            SeeProfessions cl = new SeeProfessions(getActivity(),image, JSONProfessional.names, JSONProfessional.desc, JSONProfessional.worker);
            pro_list.setAdapter(cl);
        } catch (Exception e) {
            Log.d("Error", "Error in HomeActivity customer fragment custom adapter");
            e.printStackTrace();
        }

    }

    private void placeRequest(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PLACE_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(USER_NAME,username);
                params.put(PLACE_REQUEST,request);
                params.put(DOMAIN,domain);
                params.put(POSITION,pos);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
