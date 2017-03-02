package com.example.com.findproexperttabbed;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPage extends AppCompatActivity {

    Button makeAccount, cancel;
    CheckBox worker, customer;
    EditText fname, lname, address, email, dob, mobile, user, pass1, pass2;
    String fnames, lnames, adds, emails, dobs, mobiles, users, pass1s, pass2s;
    boolean workerb, customerb;
    int year, month, day;
    Calendar calendar;

    public static final String KEY_FNAME = "fname";
    public static final String KEY_LNAME = "lname";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_DOB = "dob";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_USER = "user";
    public static final String KEY_PASS = "pass";

    private static final String REGISTER_URL ="https://findproexpertcom.000webhostapp.com/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        makeAccount = (Button) findViewById(R.id.make_account);
        cancel = (Button) findViewById(R.id.cancel_button2);
        fname = (EditText) findViewById(R.id.detail_fname);
        lname = (EditText) findViewById(R.id.detail_lname);
        address = (EditText) findViewById(R.id.detail_address);
        email = (EditText) findViewById(R.id.detail_email);
        dob = (EditText) findViewById(R.id.detail_dob);
        mobile = (EditText) findViewById(R.id.detail_mob);
        user = (EditText) findViewById(R.id.detail_user1);
        pass1 = (EditText) findViewById(R.id.detail_pass1);
        pass2 = (EditText) findViewById(R.id.detail_pass2);
        worker = (CheckBox) findViewById(R.id.detail_work);
        customer = (CheckBox) findViewById(R.id.detail_user);

        //make changes in the dob field

        makeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                if (validate()) {
                    registerUser();
                    Toast.makeText(SignUpPage.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpPage.this, HomeScreen.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpPage.this, "Please enter proper credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this, LoginScreen.class);
                startActivity(intent);
            }
        });
        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);

                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    showDate(year, month+1, day);
                    showDialog(999);
                }
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = yea
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dob.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public void getValues() {
        //just get the value
        fnames = fname.getText().toString().trim();
        lnames = lname.getText().toString().trim();
        adds = address.getText().toString().trim();
        emails = email.getText().toString().trim();
        dobs = dob.getText().toString().trim();
        mobiles = mobile.getText().toString().trim();
        users = user.getText().toString().trim();
        pass1s = pass1.getText().toString().trim();
        pass2s = pass2.getText().toString().trim();
        workerb = worker.isChecked();
        customerb = customer.isChecked();
    }

    public boolean validate() {
        boolean flag = true;
        String regex;
        Pattern pattern;
        Matcher matcher;
        regex = "^(.+)@(.+)$";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(emails);
        if (fnames.equals("") || lnames.equals("") || adds.equals("") || emails.equals("") || dobs.equals("") || mobiles.equals("") || users.equals("") || pass1s.equals("") || pass2s.equals("")) {
            Toast.makeText(SignUpPage.this, "Field Left out", Toast.LENGTH_SHORT).show();
            flag = false;
            return flag;
        }
        if (mobiles.length() != 10) {
            Toast.makeText(SignUpPage.this, "Mobile number length incorrect", Toast.LENGTH_SHORT).show();
            flag = false;
            return flag;
        }
        if (!matcher.matches()) {
            Toast.makeText(SignUpPage.this, "Email not valid", Toast.LENGTH_SHORT).show();
            flag = false;
            return flag;
        }
        if (!pass1s.equals(pass2s)) {
            Toast.makeText(SignUpPage.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            flag = false;
            return flag;
        }
        if (!(workerb || customerb)) {
            Toast.makeText(SignUpPage.this, "Select at least one", Toast.LENGTH_SHORT).show();
            flag = false;
            return flag;
        }
        return flag;
    }
    public void registerUser(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SignUpPage.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpPage.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_FNAME,fnames);
                params.put(KEY_LNAME,lnames);
                params.put(KEY_ADDRESS,adds);
                params.put(KEY_EMAIL,emails);
                params.put(KEY_DOB,dobs);
                params.put(KEY_MOBILE,mobiles);
                params.put(KEY_USER,users);
                params.put(KEY_PASS,pass1s);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
