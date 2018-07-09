package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    TextView tvDate;
    TextView tvBmi;
    TextView tvOutcome;
    Button btCalc;
    Button btRes;

    Float BMI = 0.0f;

    Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
    final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
            (now.get(Calendar.MONTH)+1) + "/" +
            now.get(Calendar.YEAR) + " " +
            now.get(Calendar.HOUR_OF_DAY) + ":" +
            now.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        tvDate = findViewById(R.id.textViewDate);
        tvBmi = findViewById(R.id.textViewBmi);
        tvOutcome = findViewById(R.id.textViewOutcome);
        etHeight = findViewById(R.id.editTextHeight);
        btCalc = findViewById(R.id.buttonCalculate);
        btRes = findViewById(R.id.buttonReset);

        btCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etWeight.getText().toString().isEmpty() && !etHeight.getText().toString().isEmpty()) {
                    float weight = Float.parseFloat(etWeight.getText().toString());
                    float height = Float.parseFloat(etHeight.getText().toString());

                    BMI = weight / (height * height);

                    etWeight.setText(null);
                    etHeight.setText(null);
                    tvBmi.setText(getResources().getString(R.string.last_bmi) + " " + BMI);
                    tvDate.setText(getResources().getString(R.string.last_date) + " " + datetime);

                    if (BMI < 18.5) {
                        tvOutcome.setText("You are underweight");
                    } else if (BMI >= 18.5 && BMI <= 24.9) {
                        tvOutcome.setText("Your BMI is normal");
                    } else if (BMI >= 25 && BMI <= 29.9) {
                        tvOutcome.setText("You are overweight");
                    } else if (BMI >= 30) {
                        tvOutcome.setText("You are obese");
                    }
                }
            }
        });

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        btRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etWeight.setText(null);
                etHeight.setText(null);
                tvBmi.setText(getResources().getString(R.string.last_bmi));
                tvDate.setText(getResources().getString(R.string.last_date));
                tvOutcome.setText(null);

                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();

        //Step 1a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Step 1b: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();
        //Step 1c: Add the key-value pair
        prefEdit.putString("date", tvDate.getText().toString());
        prefEdit.putString("BMI", tvBmi.getText().toString());
        prefEdit.putString("outcome", tvOutcome.getText().toString());
        //Step 1d: Call commit() method to save the changes into the SharedPreferences
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Step 2b: Retrieve the saved data with the key "greeting from the SharedPreferences object
        String date = prefs.getString("date", "");
        String bmi = prefs.getString("BMI", "0");
        String outcome = prefs.getString("outcome", "");


        tvBmi.setText(bmi);
        tvDate.setText(date);
        tvOutcome.setText(outcome);
    }
}
