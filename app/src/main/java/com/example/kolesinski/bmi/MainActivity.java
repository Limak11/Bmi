package com.example.kolesinski.bmi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    ShareDialog shareDialog;
    float mass;
    float height;
    int unit = 1;
    float temp_result=0;
    @BindView(R.id.button2) Button change;
    @BindView(R.id.button1) Button ready;
    @BindView(R.id.heightTV) TextView heightTV;
    @BindView(R.id.resultTV) TextView resultTV;
    @BindView(R.id.colorTV) TextView colorTV;
    @BindView(R.id.heightET) EditText heightET;
    @BindView(R.id.massET) EditText massET;
    @BindView(R.id.my_toolbar) Toolbar tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(tool);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        temp_result = sharedPref.getFloat("BMI", 0);

        if(temp_result!=0)
        {
            String s_result = String.valueOf(temp_result);
            resultTV.setText(getString(R.string.result) + s_result);
            checkResult(temp_result);
        }
        shareDialog = new ShareDialog(this);
        // changing units
        change.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View widok)
            {
            changeUnits();
            }
        });
        // counting BMI

        ready.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View widok)
            {
                if(getData()) {

                    ICountBMI test;
                    if(unit ==1) test = new CountBMIForKgM();
                    else test = new CountBMIForKgCm();

                       float result = test.countBMI(mass, height);
                    temp_result=result;
                    String s_result = String.valueOf(result);
                    resultTV.setText(getString(R.string.result) + s_result);
                    checkResult(result);
                }
                else
                {

                    resultTV.setText(getString(R.string.error));
                    temp_result=0;
                    colorTV.setText("");
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings1:
                //author
                final Intent intencja1= new Intent(this,Main2Activity.class);
                    startActivity(intencja1);



                return true;

            case R.id.action_settings2:
                //share
                if (ShareDialog.canShow(ShareLinkContent.class) && temp_result!=0) {
                    String text = (String)resultTV.getText();
                        ShareContent linkContent = new ShareLinkContent.Builder().setContentTitle("Bmi Calculator").setContentDescription(text) .setContentUrl(Uri.parse("https://www.google.com")).build();
                 shareDialog.show(linkContent);
                }

                return true;
            case R.id.action_settings3:
                //save
                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat("BMI", temp_result);

                editor.commit();
                return true;


            default:

                return super.onOptionsItemSelected(item);

        }
    }


    public boolean getData()
    {

        ICountBMI test;
        if(unit ==1 ) test = new CountBMIForKgM();
        else test = new CountBMIForKgCm();


        String text1=massET.getText().toString();
        if(TextUtils.isEmpty(text1))
        {
            massET.setError(getString(R.string.empty));
            return false;
        }
        if(text1.equals("."))
        {
            massET.setError(getString(R.string.num));
            return false;
        }
        mass = Float.valueOf(text1);
        Log.v("EditText", massET.getText().toString());
        if(test.isValidMass(mass)== false) return false;


        String text2 = heightET.getText().toString();
        if(TextUtils.isEmpty(text2)){
            heightET.setError(getString(R.string.empty));
            return false;
        }
        if(text2.equals("."))
        {
            heightET.setError(getString(R.string.num));
            return false;
        }
        height = Float.valueOf(text2);
        Log.v("EditText", heightET.getText().toString());
        if(test.isValidHeight(height)== false) return false;

        return true;
    }

    public void changeUnits()
    {
        Boolean changed = true;
        unit = (unit%2)+1;


        if(unit%2==0)
        {

            change.setText(getString(R.string.changeM));
            heightTV.setText(getString(R.string.heightCM));
        }
        else
        {

            change.setText(getString(R.string.changeCM));
            heightTV.setText(getString(R.string.heightM));
        }
    }

    public void checkResult( float bmi)
    {

        // starvation
    if(bmi < 16.0f)
    {
        colorTV.setText(getString(R.string.bmi1));
        colorTV.setTextColor(Color.parseColor("#ff0000"));

    }
    //underweight
    else if(bmi<18.49f)
    {
        colorTV.setText(getString(R.string.bmi2));
        colorTV.setTextColor(Color.parseColor("#ff7700"));
    }
    //healthy weight
    else if(bmi<24.99f)
    {
        colorTV.setText(getString(R.string.bmi3));
        colorTV.setTextColor(Color.parseColor("#00ff65"));
    }
    //overweight
    else if(bmi<29.99)
    {
        colorTV.setText(getString(R.string.bmi4));
        colorTV.setTextColor(Color.parseColor("#ff7700"));
    }
    //obesity
    else
    {
        colorTV.setText(getString(R.string.bmi5));
        colorTV.setTextColor(Color.parseColor("#ff0000"));
    }

    }




}
