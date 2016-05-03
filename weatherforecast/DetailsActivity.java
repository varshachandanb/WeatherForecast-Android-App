package com.example.varsha.weatherforecast;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    String Next24Data;
    String Next7Data;
    String City;
    String State;
    String Degree;
    String Unit;
    JSONArray next24Array;
    JSONArray next7Array;
    TableLayout next24Tab;
    TableLayout next7Tab;
    TableLayout next24Header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        try
        {
            System.out.println("Inside Details Activity");
            Intent intent =getIntent();
            Next24Data = intent.getExtras().getString("Next24Data");
            Next7Data = intent.getExtras().getString("Next7Data");
            City = intent.getExtras().getString("City");
            State = intent.getExtras().getString("State");
            Degree = intent.getExtras().getString("Degree");
            if(Degree.equals("us"))
            {
                Unit="F";
            }
            else if (Degree.equals("si"))
            {
                Unit="C";
            }
            TextView txtSummary = (TextView) findViewById(R.id.txtSum);
            txtSummary.setText("More Details for " + City + " ," + State);
            next24Array = new JSONArray(Next24Data);
            next7Array = new JSONArray(Next7Data);
            next24Tab = (TableLayout) findViewById(R.id.next24Tab);
            next7Tab = (TableLayout) findViewById(R.id.next7Tab);
            next24Header=(TableLayout) findViewById(R.id.next24Header);
            Button next24Btn = (Button) findViewById(R.id.next24Btn);
            next24Btn.setBackgroundResource(R.drawable.twentyfour_select);
            Button next7Btn = (Button) findViewById(R.id.next7Btn);
            next7Btn.setBackgroundResource(R.drawable.seven_clear);
            /*float alpha = 0.45f;
            AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
            alphaUp.setFillAfter(true);
            next24Btn.startAnimation(alphaUp);
           // next24Btn.getBackground().setAlpha(128);
           */
            next24Btn.performClick();


        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void Next24Data(View view)
    {
        next24Header.setVisibility(View.VISIBLE);
        next24Tab.setVisibility(View.VISIBLE);
        next7Tab.setVisibility(View.INVISIBLE);
        next24Tab.removeAllViews();
        next7Tab.removeAllViews();
        next24Header.removeAllViews();
        Button next24Btn = (Button) findViewById(R.id.next24Btn);
        next24Btn.setBackgroundResource(R.drawable.twentyfour_select);
        Button next7Btn = (Button) findViewById(R.id.next7Btn);
        next7Btn.setBackgroundResource(R.drawable.seven_clear);
        next24Btn.getBackground().setAlpha(128);

        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundResource(R.color.green);
        tbrow0.setPadding(0, 5, 0, 5);
        TextView tb1v = new TextView(this);
        tb1v.setPadding(20, 5, 10, 5);
        tb1v.setTextSize(22);
        tb1v.setText("Time");
        tb1v.setWidth(120);
        tb1v.setTextColor(Color.BLACK);
        tb1v.setGravity(Gravity.CENTER_HORIZONTAL);
        tbrow0.addView(tb1v);
        TextView tb2v = new TextView(this);
        tb2v.setPadding(20, 5, 10, 5);
        tb2v.setTextSize(22);
        tb2v.setWidth(280);
        tb2v.setText("Summary");
        tb2v.setTextColor(Color.BLACK);
        tb2v.setGravity(Gravity.CENTER_HORIZONTAL);
        tbrow0.addView(tb2v);
        TextView tb3v = new TextView(this);
        tb3v.setTextSize(22);
        tb3v.setText("Temp(" + (char) 0x00B0+Unit+")");
        tb3v.setTextColor(Color.BLACK);
        tb3v.setWidth(280);
        tb3v.setGravity(Gravity.CENTER_HORIZONTAL);
        tb3v.setPadding(20, 5, 10, 5);
        tbrow0.addView(tb3v);
        next24Tab.addView(tbrow0);
        try {

            for (int j = 0; j <= 24; j++)
            {
                if (j == 24) {


                    TableRow endrow = new TableRow(this);
                    TextView tv = new TextView(this);
                    tv.setText("");
                    //t4v.setPadding(5, 10, 5, 10);
                    endrow.addView(tv);
                    final ImageView t1img = new ImageView(this);
                    int resID = getResources().getIdentifier("plus", "drawable", getPackageName());
                    Drawable drawable = ResourcesCompat.getDrawable(getResources(), resID, null);
                    t1img.setImageDrawable(next24resize(drawable));
                    endrow.addView(t1img);
                    next24Tab.addView(endrow);
                    t1img.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                            t1img.setVisibility(View.GONE);
                            System.out.println("Hello world");
                            for (int k = 24; k <= 48; k++) {
                                FillNext24Data(k);
                            }

                        }
                    });
                }
                else
                {
                    FillNext24Data(j);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    public void FillNext24Data(int j) {
        try
        {
        final String time = next24Array.getJSONObject(j).getString("time");
        final String icon = next24Array.getJSONObject(j).getString("icon").replace('-', '_');
        final String temp = next24Array.getJSONObject(j).getString("temperature");
        TableRow tbrow = new TableRow(this);
        tbrow.setPadding(0, 20, 0, 5);
        TextView t1v = new TextView(this);
        t1v.setGravity(Gravity.CENTER_HORIZONTAL);
        t1v.setPadding(20, 5, 10, 5);
        t1v.setTextSize(22);
        t1v.setText(time);
        t1v.setTextColor(Color.BLACK);
        t1v.setGravity(Gravity.CENTER);
        tbrow.addView(t1v);
        ImageView t2v = new ImageView(this);
        int resID = getResources().getIdentifier(icon, "drawable", getPackageName());
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), resID, null);
        t2v.setImageDrawable(next24resize(drawable));
        t2v.setPadding(20, 5, 10, 5);
        tbrow.addView(t2v);
        TextView t3v = new TextView(this);
        t3v.setTextSize(22);
        String TempNum = temp.split("\\.")[0];
        t3v.setText(TempNum);
        t3v.setTextColor(Color.BLACK);
        t3v.setGravity(Gravity.CENTER);
        t3v.setPadding(20, 5, 10, 5);
        tbrow.addView(t3v);
        if (j % 2 == 0) {
            tbrow.setBackgroundResource(R.color.white);
        } else {

            tbrow.setBackgroundResource(R.color.grey);
        }
        next24Tab.addView(tbrow);
    }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }

    private Drawable next24resize(Drawable image)
    {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 70, 70, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

    public void Next7Data(View view)
    {
        next24Header.setVisibility(View.GONE);
        next24Tab.setVisibility(View.GONE);
        next7Tab.setVisibility(View.VISIBLE);
        next24Tab.removeAllViews();
        next24Header.removeAllViews();
        next7Tab.removeAllViews();
        Button next24Btn = (Button) findViewById(R.id.next24Btn);
        next24Btn.setBackgroundResource(R.drawable.twentyfour_clear);
        Button next7Btn = (Button) findViewById(R.id.next7Btn);
        next7Btn.setBackgroundResource(R.drawable.seven_select);
        next7Btn.getBackground().setAlpha(128);
        try
        {
            for(int i=1;i<=7;i++) {

                TableRow tbrow1 = new TableRow(this);
                final String monthDate = next7Array.getJSONObject(i).getString("day")+", "+next7Array.getJSONObject(i).getString("monthDate");
                final String icon = next7Array.getJSONObject(i).getString("icon").replace('-', '_');
                final String minTemp = next7Array.getJSONObject(i).getString("minTemp").replace("&deg", " ");
                final String maxTemp = next7Array.getJSONObject(i).getString("maxTemp").replace("&deg"," ");


                TextView t1v = new TextView(this);
                t1v.setTextSize(22);
                t1v.setText(monthDate);
                t1v.setTextColor(Color.BLACK);
                t1v.setPadding(0,0,20,0);
                tbrow1.addView(t1v);
                ImageView t2v = new ImageView(this);
                int resID = getResources().getIdentifier(icon, "drawable", getPackageName());
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), resID, null);
                t2v.setImageDrawable(next24resize(drawable));
                t2v.setPadding(80, 5, 0, 5);
                tbrow1.setPadding(5, 10, 5, 10);
                tbrow1.addView(t2v);
                TableRow tbrow2 = new TableRow(this);
                TextView t3v = new TextView(this);
                t3v.setTextSize(22);
                String tmp="Min:" + minTemp + (char) 0x00B0 +Unit + " | Max:" + maxTemp + (char) 0x00B0 +Unit;
                t3v.setText(tmp);
                t3v.setTextColor(Color.BLACK);
                tbrow2.setPadding(5, 10, 5, 25);
                tbrow2.addView(t3v);
                TableRow tbrow3 = new TableRow(this);
                TextView t4v = new TextView(this);
                t4v.setText("");
                //t4v.setPadding(5, 10, 5, 10);
                tbrow3.setBackgroundResource(R.color.white);
                tbrow3.addView(t4v);
                switch(i) {
                    case 1: {
                        tbrow1.setBackgroundResource(R.color.orange);
                        tbrow2.setBackgroundResource(R.color.orange);
                        break;
                    }
                    case 2: {
                        tbrow1.setBackgroundResource(R.color.blue);
                        tbrow2.setBackgroundResource(R.color.blue);
                        break;
                    }
                    case 3: {
                        tbrow1.setBackgroundResource(R.color.lightpink);
                        tbrow2.setBackgroundResource(R.color.lightpink);
                        break;
                    }
                    case 4: {
                        tbrow1.setBackgroundResource(R.color.lightgreen);
                        tbrow2.setBackgroundResource(R.color.lightgreen);
                        break;
                    }
                    case 5: {
                        tbrow1.setBackgroundResource(R.color.peech);
                        tbrow2.setBackgroundResource(R.color.peech);
                        break;
                    }
                    case 6:

                    {
                        tbrow1.setBackgroundResource(R.color.yellow);
                        tbrow2.setBackgroundResource(R.color.yellow);
                        break;
                    }
                    case 7: {
                        tbrow1.setBackgroundResource(R.color.violet);
                        tbrow2.setBackgroundResource(R.color.violet);
                        break;
                    }

                }
                next7Tab.addView(tbrow1);
                next7Tab.addView(tbrow2);
                next7Tab.addView(tbrow3);

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}




