package com.ishanfx.colorize;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.pavlospt.CircleView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class HomeActivity extends AppCompatActivity implements CircleView.OnClickListener{

    LinearLayout layoutHomeMainColourPanel,layoutHomeSubColourPanel;
    Context context;
    HashMap colorIdMap;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        colorIdMap = new HashMap();

        String[] colorList = context.getResources().getStringArray(R.array.main_color);
        for(int count=0;count<colorList.length;count++){
            colorIdMap.put(colorList[count].replace("#",""),"color_"+(count+1));
        }
        layoutHomeMainColourPanel = (LinearLayout) findViewById(R.id.layoutHomeMainColourPanel);
        layoutHomeSubColourPanel = (LinearLayout) findViewById(R.id.layoutHomeSubColourPanel);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7156168350107339~7187334205");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);

        createMainImagePanel();
    }

    public void createMainImagePanel(){
        String[] colorList = context.getResources().getStringArray(R.array.main_color);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
        layoutParams.setMargins(10,10,10,10);

        for(int count=0;count<colorList.length;count++){
            CircleView circleImageViewMainColor = new CircleView(this);
            circleImageViewMainColor.setShowTitle(false);
            circleImageViewMainColor.setShowSubtitle(false);
            circleImageViewMainColor.setLayoutParams(layoutParams);
            circleImageViewMainColor.setTag(colorList[count]);
            circleImageViewMainColor.setStrokeColor(android.R.color.transparent);
            circleImageViewMainColor.setFillColor(Color.parseColor(colorList[count]));
            circleImageViewMainColor.setOnClickListener(this);

            layoutHomeMainColourPanel.addView(circleImageViewMainColor);
        }

        CircleView circleView = (CircleView) layoutHomeMainColourPanel.getChildAt(0);
        circleView.performClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        layoutParams.setMargins(10,10,10,10);
        String colorArrayName = v.getTag().toString();
        colorArrayName = colorArrayName.replace("#","");
        Log.d("COL", "Color ID: "+colorArrayName.toString());
        layoutHomeSubColourPanel.removeAllViews();
        int resource =  getResources().getIdentifier((String) colorIdMap.get(colorArrayName),"array",getPackageName());
        Log.d("COL", "Color ID 2: "+ colorIdMap.get(colorArrayName));
        String[] colors = context.getResources().getStringArray(resource);
        for(int count=0;count<colors.length;count++){
            android.support.v7.widget.CardView cardView =  new android.support.v7.widget.CardView(context);
            cardView.setLayoutParams(layoutParams);
            cardView.setRadius(20);
            cardView.setBackgroundColor(Color.parseColor(colors[count]));

            TextView textView = new TextView(context);
            textView.setLayoutParams(layoutParams);
            textView.setTextSize(20);
            textView.setText(String.valueOf(colors[count]));
            textView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);

            cardView.addView(textView);
            layoutHomeSubColourPanel.addView(cardView);
        }
    }
}
