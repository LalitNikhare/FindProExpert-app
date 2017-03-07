package com.example.com.findproexperttabbed.Professional;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.com.findproexperttabbed.JSONProfessional;
import com.example.com.findproexperttabbed.R;
import com.example.com.findproexperttabbed.ViewRequestActivity;

import static com.example.com.findproexperttabbed.Customer.CustomerView.JSON_URL;


/**
 * Created by faltu on 09-Feb-17.
 */

public class ProfessionalView extends Fragment {
    View view;
    public static final String JSON_URL = "https://findproexpertcom.000webhostapp.com/fetch_requests.php";
    ListView request_list;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.professional_fragment, container, false);
        request_list= (ListView) view.findViewById(R.id.request_list2);
        sendRequest();
        request_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),ViewRequestActivity.class);
                startActivity(intent);
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
        pj.parseJSONforCustomer();
//        Toast.makeText(getActivity().getApplicationContext(), "No of objects :" + pj.worker.length, Toast.LENGTH_LONG).show();
//        for (int i = 0; i < pj.worker.length; i++) {//Chances of error here
//            Toast.makeText(getActivity().getApplicationContext(), i + " Name: " + JSONProfessional.names[i] + "Desc: " + pj.desc[i] + "No. of workers: " + pj.worker[i], Toast.LENGTH_SHORT).show();
//        }

        try {
            SeeRequests cl = new SeeRequests(getActivity(), JSONProfessional.names, JSONProfessional.desc, JSONProfessional.request);
            request_list.setAdapter(cl);
        } catch (Exception e) {
            Log.d("Error", "Error in HomeActivity customer fragment custom adapter");
            e.printStackTrace();
        }

    }
}
