package com.sm.htnchallengelist5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


public class ProfileActivity extends Activity {

    public static final String PROFILE_ACTIVITY_BUNDLE = "com.sm.htnchallengelist5.PROFILE_ACTIVITY_BUNDLE";
    private Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //must be between super/setContentView
        setContentView(R.layout.activity_profile);
        c = this;

        Intent intent = getIntent();
        String jsonPerson = intent.getStringExtra(PROFILE_ACTIVITY_BUNDLE);
        final Person p = new Person(jsonPerson);

        TextView profileName = (TextView)findViewById(R.id.TextViewProfileName);
        TextView profileCompany = (TextView)findViewById(R.id.TextViewProfileCompany);
        TextView profilePhone = (TextView)findViewById(R.id.TextViewProfilePhone);
        TextView profileSkills = (TextView)findViewById(R.id.TextViewProfileSkills);
        TextView profileEmail = (TextView)findViewById(R.id.TextViewProfileEmail);
        Button buttonEmail = (Button)findViewById(R.id.ButtonProfileEmail);
        Button buttonCall = (Button)findViewById(R.id.ButtonProfileCall);

        profileName.setText(p.getName());
        profileCompany.setText(p.getCompany());
        profilePhone.setText(p.getPhone());
        profileEmail.setText(p.getEmail());
        profileSkills.setText(p.getSkillString());

        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"+p.getEmail()));
                try {
                    startActivity(emailIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(c, "No apps can perform this action", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String phoneNumber = "tel:" + p.getPhone();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(phoneNumber));
                startActivity(callIntent);
            }
        });
    }
}
