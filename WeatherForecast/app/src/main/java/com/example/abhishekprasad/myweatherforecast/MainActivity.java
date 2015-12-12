package com.example.abhishekprasad.myweatherforecast;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class MainActivity extends Activity {
    EditText city;
    Spinner staticSpinner;
    String degreevalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = (EditText) findViewById(R.id.editText2);
        staticSpinner = (Spinner) findViewById(R.id.state_spinner);
        final EditText street = (EditText) findViewById(R.id.editText);

        final TextView validatemsg = (TextView) findViewById(R.id.textView6);
        final RadioGroup degree = (RadioGroup) findViewById(R.id.degree);

        street.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v == street) {
                    if (street.length() == 0 && street.hasFocus()) {
                        validatemsg.setText("Please Add The Street");
                    } else {
                        validatemsg.setText("");
                    }
                }
            }
        });

        ImageView img = (ImageView)findViewById(R.id.imageView2);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://forecast.io/"));
                startActivity(intent);
            }
        });
        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v == city) {
                    if (city.length() == 0 && city.hasFocus()) {
                        validatemsg.setText("Please Add The City");
                    } else {
                        validatemsg.setText("");
                    }
                }
            }
        });
        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view == staticSpinner) {
                    if (staticSpinner.getSelectedItemPosition() == 0) {
                        validatemsg.setText("Please select a State");
                    } else {
                        validatemsg.setText("");

                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        street.requestFocus();
        if (street.length() == 0 && street.hasFocus()) {
            validatemsg.setText("Please Add The Street");
        } else {
            validatemsg.setText("");
        }
        street.addTextChangedListener(new TextWatcher() {
                                          @Override
                                          public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                          }

                                          @Override
                                          public void onTextChanged(CharSequence s, int start, int before, int count) {
                                              if (count == 0 && street.hasFocus()) {
                                                  validatemsg.setText("Please Add The Street");
                                              } else {
                                                  validatemsg.setText("");
                                              }
                                          }

                                          @Override
                                          public void afterTextChanged(Editable s) {

                                          }
                                      }
        );


        city.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            if (count == 0 && city.hasFocus()) {
                                                validatemsg.setText("Please Add The City");
                                            } else {
                                                validatemsg.setText("");
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }
                                    }
        );
        // Create an ArrayAdapter using the string array and a default spinner
        final ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.state_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        Button search = (Button) findViewById(R.id.button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = degree.getCheckedRadioButtonId();
                RadioButton degreebutton = (RadioButton) findViewById(selectedId);
                degreevalue = degreebutton.getText().toString();
                if (degreevalue.equals("Fahrenheit"))
                    degreevalue = "us";
                else
                    degreevalue = "si";
                if (street.getText().toString().matches(""))
                {
                    //errorList+="street address";
                    validatemsg.setText("Please Add The Street");

                }


                else if(city.getText().toString().matches(""))
                {
                    validatemsg.setText("Please Add The city");
                    //Error.setText("Please enter city");
                }
                else if (staticSpinner.getSelectedItem().toString().equals("Select"))
                {
                    validatemsg.setText("Please select a state");
                }
                else{
                String url = "http://mywebapplication-env.elasticbeanstalk.com/?streetAddress=" + street.getText().toString() + "&city=" + city.getText().toString() + "&state=" + staticSpinner.getSelectedItem().toString() + "&degree=" + degreevalue;

                myClickHandler(url);
            }}
        });
        Button clear = (Button) findViewById(R.id.button2);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText street = (EditText) findViewById(R.id.editText);
                EditText city = (EditText) findViewById(R.id.editText2);
                Spinner state_spinner = (Spinner) findViewById(R.id.state_spinner);
                RadioButton celsius = (RadioButton) findViewById(R.id.radioButton);
                RadioButton fahr = (RadioButton) findViewById(R.id.radioButton2);
                street.requestFocus();
                street.setText("");
                city.setText("");
                state_spinner.setSelection(0);
                celsius.setChecked(true);
                fahr.setChecked(false);
            }
        });
        Button about = (Button) findViewById(R.id.button5);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

        public void myClickHandler(String url) {
        // Gets the URL from the UI's text field.

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        String[] param=new String[3];
            param[0]=city.getText().toString();
            param[1]=staticSpinner.getSelectedItem().toString();
            param[2]=degreevalue;
            new MyAsyncTask(this,param).execute(url);
        } else {
            Toast.makeText(MainActivity.this,
                    "No network connection available.", Toast.LENGTH_SHORT).show();
                    }
    }
}
