package com.example.varsha.weatherforecast;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class main extends AppCompatActivity  {

    JSONObject myObj;
    String Street="";
    String City="";
    String State;
    String Degree;
    String response="";
    EditText street;
    EditText city;
    Spinner state;
    RadioGroup rg;
    Integer flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         street = (EditText) findViewById(R.id.etStreet);
         city = (EditText) findViewById(R.id.etCity);
        final TextView Error1 = (TextView) findViewById(R.id.ErrorMsg1);
        final TextView Error2 = (TextView) findViewById(R.id.ErrorMsg2);
        final TextView Error3 = (TextView) findViewById(R.id.ErrorMsg3);
        state = (Spinner) findViewById(R.id.spState);
        rg = (RadioGroup) findViewById(R.id.rgDegree);
        Error1.setText("");
        Error2.setText("");
        Error3.setText("");
        /*street.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean gainFocus) {
                //onFocus
                if (gainFocus) {
                    //set the row background to a different color
                    Street = street.getText().toString();

                    if (Street.trim().equals("")) {
                        flag = 1;
                        Error1.setText("Please enter a Street address");
                    }
                }
                //onBlur
                else {
                    //set the row background white
                    //
                }
            }
        });

        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean gainFocus) {
                //onFocus
                if (gainFocus) {
                    //set the row background to a different color
                    City = city.getText().toString();
                    if (City.trim().equals("")) {
                        flag = 1;
                        Error2.setText("Please enter City");
                    }
                }
                //onBlur
                else {
                    //set the row background white
                    //
                }
            }
        });

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                State = state.getSelectedItem().toString();
                if (State.equals("Select")) {
                    flag = 1;
                    Error3.setText("Please enter State");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/
        TextView ErrorMsg4 = (TextView) findViewById(R.id.ErrorMsg4);
        ErrorMsg4.setVisibility(View.INVISIBLE);
        findViewById(R.id.etStreet).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Error1.setText("");
                if (("").equals(street.getText().toString().trim())) {
                    Error1.setText("Please enter a Street address");
                }
            }
        });
        findViewById(R.id.etCity).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Error2.setText("");
                if(("").equals(city.getText().toString().trim())){
                    Error2.setText("Please enter a City");
                }
            }
        });
        findViewById(R.id.spState).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Error3.setText("");
                if (state.getSelectedItem().toString().equals("Select")) {
                    Error3.setText("Please enter State");
                }
            }
        });
        findViewById(R.id.btnSearch).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {


                //String degree = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                RadioButton rb = (RadioButton) findViewById(R.id.rdFH);
                if(rb.isChecked()) {

                    Degree = "us";
                }
                else {
                    Degree = "si";
                }

                flag=0;
                Street = street.getText().toString();
                City = city.getText().toString();
                State = state.getSelectedItem().toString();
                System.out.println("Degree " + Degree);
                Error1.setText("");
                Error2.setText("");
                Error3.setText("");

                if (Street.trim().equals("")) {
                    flag = 1;
                    Error1.setText("Please enter a Street address");
                }


                if (City.trim().equals("")) {
                    flag = 1;
                    Error2.setText("Please enter City");
                }


                if (State.equals("Select")) {
                    flag = 1;
                    Error3.setText("Please enter State");
                }
                if (flag == 0) {

                    Object[] obj = new Object[4];
                    obj[0] = Street;
                    obj[1] = City;
                    obj[2] = State;
                    obj[3] = Degree;
                    new weatherJsonParse().execute(obj);
                    //String response=new weatherJsonParse().execute(obj).get();
                }
            }
        });


    }

    public void imgForecastClick(View view) {
        Uri uri = Uri.parse("http://www.Forecast.io"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void Profile(View view) {
        Intent intent = new Intent(main.this, About_Us.class);
        startActivity(intent);

    }

    public void rdButtonClick(View view)
    {
        System.out.println("Inside Radio");

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId())
        {
            case R.id.rdCel:
                if (checked) {
                    RadioButton rb = (RadioButton) findViewById(R.id.rdFH);
                    rb.setChecked(false);
                    break;
                }
            case R.id.rdFH:
                if (checked) {
                    RadioButton rb = (RadioButton) findViewById(R.id.rdCel);
                    rb.setChecked(false);
                    break;
                }
        }
    }

    public void ClearData(View view)
    {
        System.out.println("Inside ClearData");
        street.setText("");
        city.setText("");
        state.setSelection(0);
        RadioButton rb1 = (RadioButton) findViewById(R.id.rdFH);
        rb1.setChecked(true);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rdCel);
        rb2.setChecked(false);
         TextView Error1 = (TextView) findViewById(R.id.ErrorMsg1);
         TextView Error2 = (TextView) findViewById(R.id.ErrorMsg2);
         TextView Error3 = (TextView) findViewById(R.id.ErrorMsg3);
        Error1.setText("");
        Error2.setText("");
        Error3.setText("");

    }


    public class weatherJsonParse extends AsyncTask<Object, String, String> {



        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Object... params) {

            try {
            String Street=URLEncoder.encode((String)params[0],"UTF-8");
            String City=URLEncoder.encode((String)params[1],"UTF-8");;
            String State=URLEncoder.encode((String)params[2],"UTF-8");;
            String Degree=URLEncoder.encode((String)params[3],"UTF-8");
            String url = "http://awsAssg8-env.elasticbeanstalk.com/WeatherForecastHW8.php?Street=" + Street + "&City=" + City + "&State=" + State + "&Degree=" + Degree;

               // url = URLEncoder.encode(url, "UTF-8");
                System.out.println("URL is"+url);

            //System.out.println("url  is" + url);
            // String url ="http://awsAssg8-env.elasticbeanstalk.com/WeatherForecastHW8.php?Street=1247W30th&City=LosAngeles&State=CA&Degree=us";
            try{
                URL obj = new URL(url);
                System.out.println("URL is"+obj);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                //System.out.println("Content is"+con.getInputStream());
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine="";

               StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

                }

                in.close();
                con.disconnect();

               myObj = new JSONObject(response.toString());

            }
            catch (MalformedURLException mie){
                System.out.println("Malinformed URL");
            }
            catch (ProtocolException pe){
                System.out.println("Protocol Exception");
            }
            catch(IOException ioe){
                System.out.println("IO Exception");
            }
            catch (JSONException jse){
                System.out.println("JSON Exception");

            }
            }
            catch(Exception e){
                System.out.println("URL Cant be encoded");
            }

            return null;
            //return response;
        }



        @Override
        protected void onPostExecute(String strFromDoInBg) {

            try {

                if((myObj.getString("Error").equals("null")))
                {

                    TextView ErrorMsg4 = (TextView) findViewById(R.id.ErrorMsg4);
                    ErrorMsg4.setVisibility(View.INVISIBLE);
                    System.out.println("Inside Post execution");
                    String currData = myObj.getString("currData");
                    String Next24Data = myObj.getString("Next24Data");
                    String Next7Data = myObj.getString("Next7Data");
                    System.out.println(currData);
                    Intent localIntent = new Intent(getBaseContext(), ResultActivity.class);
                    localIntent.putExtra("Street", Street);
                    localIntent.putExtra("City", City);
                    localIntent.putExtra("State", State);
                    localIntent.putExtra("Degree", Degree);
                    localIntent.putExtra("Next24Data", Next24Data.toString());
                    localIntent.putExtra("currData", currData);
                    localIntent.putExtra("Next7Data", Next7Data.toString());
                    startActivity(localIntent);
                }
                else
                {
                    System.out.println("No results");
                    TextView ErrorMsg4 = (TextView) findViewById(R.id.ErrorMsg4);
                    ErrorMsg4.setVisibility(View.VISIBLE);

                }
            }
            catch (Exception e)
            {
                System.out.print(e.getMessage());
            }

        }
    }
}

