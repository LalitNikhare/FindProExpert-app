package com.example.com.findproexperttabbed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.com.findproexperttabbed.HomeScreen.HomeScreen;

import java.util.ResourceBundle;

public class SettingsPref extends AppCompatActivity{
    //EditTextPreference pass_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SettingsPref.this,HomeScreen.class);
        startActivity(intent);
        finish();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences customSharedPreference = getSharedPreferences("myCustomSharedPrefs", AppCompatActivity.MODE_PRIVATE);
//        String str=customSharedPreference.getString("passwordchange","");
//
//        Toast.makeText(SettingsPref.this,""+str,Toast.LENGTH_SHORT).show();
//    }
}
