package com.example.com.findproexperttabbed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FeedBackPage extends AppCompatActivity {

    RadioButton yes,no;
    RadioGroup grpchoice;
    Button send_feedback;
    int value=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_page);

        yes= (RadioButton) findViewById(R.id.feed_page_yes);
        no= (RadioButton) findViewById(R.id.feed_page_no);
        send_feedback= (Button) findViewById(R.id.send_feedback);

        grpchoice= (RadioGroup) findViewById(R.id.grp_choice);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FeedBackPage.this,"You selected yes",Toast.LENGTH_SHORT).show();
                value=1;
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FeedBackPage.this,"You selected no",Toast.LENGTH_SHORT).show();
                value=0;
            }
        });

        send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedBackSend();
            }
        });

    }

    private void feedBackSend() {

    }
}
