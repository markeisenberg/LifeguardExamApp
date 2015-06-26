package com.marke.irishwatersafety;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Home extends ActionBarActivity implements OnClickListener{
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ImageButton candidateB = (ImageButton)findViewById(R.id.btnCandidateList);
        candidateB.setOnClickListener(this);
        final ImageButton lifegaurdB = (ImageButton)findViewById(R.id.btnLifeguardManual);
        lifegaurdB.setOnClickListener(this);
        final ImageButton stopwatchB = (ImageButton)findViewById(R.id.btnStopwatch);
        stopwatchB.setOnClickListener(this);
        final ImageButton tabulatedB = (ImageButton)findViewById(R.id.btnTabulatedResults);
        tabulatedB.setOnClickListener(this);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();      
            StrictMode.setThreadPolicy(policy);
         }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_login) {
        	Intent openLogin = new Intent(this, Login.class);
			startActivity(openLogin);
        }
        else if (id == R.id.action_stopwatch) {
        	Intent openStopwatch = new Intent(this, Stopwatch.class);
			startActivity(openStopwatch);
        } 
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Home.this);
		switch (v.getId()){
		case R.id.btnCandidateList:
			//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Home.this);
	        sp.getString("specpassword", "anon");
	        sp.getString("password", "anon");
	        
	        if (sp.contains("password"))
			{
				Intent openCandidate = new Intent(this, CandidateList.class);
				startActivity(openCandidate);
				break;
			} 
			if (sp.contains("specpassword"))
			{
				Intent openCandidate = new Intent(this, CandidateList.class);
				startActivity(openCandidate);
				break;
			} 
			else 
			{ 
				Toast toast = Toast.makeText(Home.this, "Not logged in!", Toast.LENGTH_SHORT);
				toast.show();
				break;
			}
		case R.id.btnLifeguardManual:
			Intent openManual = new Intent(this, LifeguardManual.class);
			startActivity(openManual);
			break;
		case R.id.btnStopwatch:
			Intent openStopwatch = new Intent(this, Stopwatch.class);
			startActivity(openStopwatch);
			break;
		case R.id.btnTabulatedResults:
			//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Home.this);
	        sp.getString("specpassword", "anon");
	        
			if (sp.contains("specpassword"))
			{
				Intent openResults = new Intent(this, TabulatedResults.class);
				startActivity(openResults);
				break;
			} 
			else 
			{ 
				Toast toast = Toast.makeText(Home.this, "Not logged in as external!", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
}
