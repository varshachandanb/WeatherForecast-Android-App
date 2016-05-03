package com.example.varsha.weatherforecast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class ResultActivity extends AppCompatActivity {
    JSONObject currObj;
    JSONObject next24Obj;
    JSONObject next7Obj;
    String Next24Data;
    String currData;
    String Next7Data;
    String Street;
    String City;
    String State;
    String Degree;
    String Unit;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        //Login Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "Error occurred in Login", Toast.LENGTH_LONG).show();
            }
        });
        currData = getIntent().getExtras().getString("currData");
        Next24Data = getIntent().getExtras().getString("Next24Data");
        Next7Data = getIntent().getExtras().getString("Next7Data");
        Street = getIntent().getExtras().getString("Street");
        City = getIntent().getExtras().getString("City");
        State = getIntent().getExtras().getString("State");
        Degree = getIntent().getExtras().getString("Degree");

        try {
            currObj = new JSONObject(currData);
            TextView txtSummary = (TextView) findViewById(R.id.txtSummary);
            TextView txtPrecp = (TextView) findViewById(R.id.txtPrecip);
            TextView txtRain = (TextView) findViewById(R.id.txtRain);
            TextView txtWindSpeed = (TextView) findViewById(R.id.txtWindSpeed);
            TextView txtDewPoint = (TextView) findViewById(R.id.txtDewPoint);
            TextView txtHumidity = (TextView) findViewById(R.id.txtHumidity);
            TextView txtVisibility = (TextView) findViewById(R.id.txtVisibility);
            TextView txtSunrise = (TextView) findViewById(R.id.txtSunrise);
            TextView txtSunset = (TextView) findViewById(R.id.txtSunset);
            TextView txtMinMaxtemp = (TextView) findViewById(R.id.txtMinMaxTemp);
            TextView txtTemp = (TextView) findViewById(R.id.txtTemp);
            ImageView imgIcon= (ImageView) findViewById(R.id.currImage);

            txtSummary.setText(currObj.getString("summary") + " in " + City + " ,"+State);
            txtPrecp.setText(currObj.getString("precipitation"));
            txtRain.setText(currObj.getString("rain"));
            txtWindSpeed.setText(currObj.getString("wind"));
            txtHumidity.setText(currObj.getString("humidity"));
            txtVisibility.setText(currObj.getString("visibility"));
            txtSunrise.setText(currObj.getString("rise"));
            txtSunset.setText(currObj.getString("set"));
            String Dewpoint=currObj.getString("dew").split("\\.")[0];
            String dp="";
            if(Degree.equals("us"))
            {
                Unit="F";
                dp=Dewpoint.replace("&deg F", " ")+(char) 0x00B0+Unit;

            }
            else if (Degree.equals("si"))
            {
                Unit="C";
                dp=Dewpoint.replace("&deg C"," ")+(char) 0x00B0+Unit;
            }
            txtDewPoint.setText(dp);

            txtTemp.setText(currObj.getString("temperature")+(char) 0x00B0+Unit);
            txtMinMaxtemp.setText("L:" + currObj.getString("minTemp") + (char) 0x00B0 + "|H:" + currObj.getString("maxTemp") + (char) 0x00B0);

            String mDrawableName = currObj.getString("icon");
            mDrawableName= mDrawableName.replace('-','_');
            System.out.println("Image is: "+mDrawableName);
            int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
           // System.out.println("ResultID:"+resID);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), resID, null);
            imgIcon.setImageDrawable(drawable);
            Button moreDetails = (Button)findViewById(R.id.moreDetails);
            //moreDetails.setOnClickListener(this);


        }
        catch(Exception e)
        {

            System.out.print(e.getMessage());
        }
    }


    public void onClickDetails(View v)
    {

        try {
            System.out.print("Inside Click More Details");
            Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
            intent.putExtra("Street", Street);
            intent.putExtra("City", City);
            intent.putExtra("State", State);
            intent.putExtra("Degree", Degree);
            intent.putExtra("Next24Data", Next24Data);
            intent.putExtra("Next7Data", Next7Data);
            startActivity(intent);
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }

    }








public void onFbClick(final View v)
    {
       try {
           shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
               @Override
               public void onSuccess(Sharer.Result result) {
                   String postId = result.getPostId();
                   if (postId == null) {
                       Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_LONG).show();
                   } else {
                       Toast.makeText(getApplicationContext(), "Posted Story ID:" + postId, Toast.LENGTH_LONG).show();
                   }
               }

               @Override
               public void onCancel() {
                   Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_LONG).show();
               }

               @Override
               public void onError(FacebookException error) {
                   Toast.makeText(getApplicationContext(), "Error occurred during post", Toast.LENGTH_LONG).show();
               }
           });
           String description = currObj.getString("summary")+" , "+currObj.getString("temperature")+(char) 0x00B0+Unit;
           String detailsTitle = "Current Weather in "+City+" , "+State;
           String ContentUrl="forecast.io";
           String ImageUrl="http://awsassg8-env.elasticbeanstalk.com/images/"+currObj.getString("icon")+".png";
           
           ShareLinkContent  content=new ShareLinkContent.Builder().setContentUrl(Uri.parse(ContentUrl)).setContentTitle(detailsTitle).setImageUrl(Uri.parse(ImageUrl)).setContentDescription(description).build();
           shareDialog.show(this, content);
       }
       catch(JSONException e)
       {
          e.printStackTrace();
       }
    }

    public void viewMap(View view)
    {
        try {

            Intent localIntent = new Intent(ResultActivity.this, map.class);

            //  localIntent.putExtra("latitude", Double.valueOf(35.06));
            //  localIntent.putExtra("longitude", Double.valueOf(180.23));
            System.out.println("latitude:"+currObj.getString("lat"));
            System.out.println("longitude"+currObj.getString("lat"));
            localIntent.putExtra("latitude", Double.valueOf(currObj.getString("lat")));
            localIntent.putExtra("longitude", Double.valueOf(currObj.getString("lat")));
            startActivity(localIntent);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }





    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
