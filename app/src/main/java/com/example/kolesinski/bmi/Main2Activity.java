package com.example.kolesinski.bmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        View mojeOkno2 = (View) findViewById(R.id.activity2);
        mojeOkno2.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        finish();
                        return false;
                    }
                }


        );

    }
}
