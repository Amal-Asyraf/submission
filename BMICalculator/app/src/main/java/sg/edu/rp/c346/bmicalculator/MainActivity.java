package sg.edu.rp.c346.bmicalculator;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight;
    EditText etHeight;
    Button btnCal;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvT;


    @Override
    protected void onPause() {
        super.onPause();
        String weight = etWeight.getText().toString();
        String height = etHeight.getText().toString();

        if (!TextUtils.isEmpty(weight) && !TextUtils.isEmpty(height)) {
            float weightf = Float.parseFloat(etWeight.getText().toString());
            float heightf = Float.parseFloat(etHeight.getText().toString());
            float total = weightf / (heightf * heightf);
            String bmi = Float.toString(total);

            Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
            String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                    (now.get(Calendar.MONTH) + 1) + "/" +
                    now.get(Calendar.YEAR) + " " +
                    now.get(Calendar.HOUR_OF_DAY) + ":" +
                    now.get(Calendar.MINUTE);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor preEdit = prefs.edit();
            preEdit.putString("bmi",bmi);
            preEdit.putString("datetime",datetime);
            preEdit.commit();
        }else{

            Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
            String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                    (now.get(Calendar.MONTH) + 1) + "/" +
                    now.get(Calendar.YEAR) + " " +
                    now.get(Calendar.HOUR_OF_DAY) + ":" +
                    now.get(Calendar.MINUTE);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor preEdit = prefs.edit();
            preEdit.commit();

        }






    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        String bmi = prefs.getString("bmi", "0");
        String datetime = prefs.getString("datetime", "0");
        tvBMI.setText("Last Calculated BMI: " + bmi);
        tvDate.setText("Last Calculated Date: " + datetime);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCal = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvT = findViewById(R.id.textViewText);

        etWeight.requestFocus();

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                float total = (weight / (height * height));
                String bmi = Float.toString(total);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH) + 1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);


                if (total < 18.5) {
                    String text = "You are underweight";
                    tvT.setText(text);
                } else if (total > 18.5 && total < 24.9) {

                    String text = "Your BMI is normal";
                    tvT.setText(text);
                } else if (total > 25 && total < 29.9) {

                    String text = "You are overweight";
                    tvT.setText(text);
                } else {

                    String text = "You are obese";
                    tvT.setText(text);
                }




                tvBMI.setText("Last Calculated BMI: " + bmi);
                tvDate.setText("Last Calculated Date: " + datetime);


            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHeight.setText(null);
                etWeight.setText(null);
                tvBMI.setText("Last Calculated BMI: ");
                tvDate.setText("Last Calculated Date: ");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();

            }
        });
    }


}

