package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    LinearLayout mLayout;
    int mDefaultColor;
    Button mButton;
    Boolean restart=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPref sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.DarkTheme_SharedPreferences);
        }
        else  setTheme(R.style.LightTheme_SharedPreferences);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout=(LinearLayout) findViewById(R.id.main);
        mDefaultColor= ContextCompat.getColor(MainActivity.this,R.color.purple_200);
        mButton=(Button) findViewById(R.id.btnChangeColor);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        Button btnChangeColor=(Button) findViewById(R.id.btnChangeColor);



        Spinner textFonts;
        textFonts=(Spinner) findViewById(R.id.spinnerTextFont);
        textFonts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            TextView tvText;
            private Typeface script;
            String fuente="";
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        fuente= "GreatVibes-Regular.otf";
                        break;
                    case 1:
                        fuente= "Pacifico.ttf";
                        break;
                }
                tvText=(TextView) findViewById(R.id.proofText);
                this.script=Typeface.createFromAsset(getAssets(),fuente);
                tvText.setTypeface(script);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }
        );

        Spinner spinnerTheme;

        spinnerTheme=(Spinner) findViewById(R.id.spinnerTheme);

        if (sharedpref.loadNightModeState()==true) {
            spinnerTheme.setSelection(1);
        }

        spinnerTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(restart){

                        if (position==1) {
                            sharedpref.setNightModeState(true);
                            restartApp();

                        }
                        else {
                            sharedpref.setNightModeState(false);
                            restartApp();
                        }
                    } else {
                        restart=true;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            }
        );


        SeekBar seekBar=(SeekBar) findViewById(R.id.spinnerTextSize);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tvText;
                tvText=(TextView) findViewById(R.id.proofText);
                tvText.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }

    public void openColorPicker(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor=color;
                mLayout.setBackgroundColor(mDefaultColor);
            }
        });
        colorPicker.show();
    }

    public void restartApp () {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

}