package com.marke.irishwatersafety;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Login extends ActionBarActivity implements OnClickListener{
	
	private EditText user, pass, specpass;
	private Button mSubmit, extmSubmit, logoutSubmit, ChooseExam, ChooseExt;
	LinearLayout loggedExam, loggedExt, notLogged;

	 // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //php login script location:

    //localhost :
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
   // private static final String LOGIN_URL = "http://xxx.xxx.x.x:1234/webservice/login.php";

    //testing on Emulator:
    private static final String LOGIN_URL = "http://markeisenberg.org/lifeguard-exam/webservice/login.php";

  //testing from a real server:
    //private static final String LOGIN_URL = "http://www.yourdomain.com/webservice/login.php";

    //JSON element ids from repsonse of php script:
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_lifeguardmanual) {
        	Intent openManual = new Intent(this, LifeguardManual.class);
			startActivity(openManual);
        }
        else if (id == R.id.action_stopwatch) {
        	Intent openStopwatch = new Intent(this, Stopwatch.class);
			startActivity(openStopwatch);
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		//setup input fields
		user = (EditText)findViewById(R.id.etUsername);
		pass = (EditText)findViewById(R.id.etPassword);
		specpass = (EditText)findViewById(R.id.etSpecPassword);
		
		user.setEnabled(false);

		//setup buttons
		mSubmit = (Button)findViewById(R.id.btnLogin);
		extmSubmit = (Button)findViewById(R.id.btnExtLogin);
		logoutSubmit = (Button)findViewById(R.id.btnLogout);
		ChooseExam = (Button)findViewById(R.id.btnChooseExam);
		ChooseExt = (Button)findViewById(R.id.btnChooseExt);

		//register listeners
		mSubmit.setOnClickListener(this);
		extmSubmit.setOnClickListener(this);
		logoutSubmit.setOnClickListener(this);
		ChooseExam.setOnClickListener(this);
		ChooseExt.setOnClickListener(this);
		
		loggedExam = (LinearLayout)findViewById(R.id.loggedInExamBar);
        loggedExt = (LinearLayout)findViewById(R.id.loggedInExtBar);
        notLogged = (LinearLayout)findViewById(R.id.loggedOutBar);
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Login.this);
        sp.getString("specpassword", "anon");
        sp.getString("password", "anon");
        
        if (sp.contains("password"))
		{
        	loggedExam.setVisibility(View.VISIBLE);
        	loggedExt.setVisibility(View.GONE);
        	notLogged.setVisibility(View.GONE);
		} 
        else if (sp.contains("specpassword"))
		{
			loggedExam.setVisibility(View.GONE);
        	loggedExt.setVisibility(View.VISIBLE);
        	notLogged.setVisibility(View.GONE);
		} 
		else 
		{ 
			loggedExam.setVisibility(View.GONE);
        	loggedExt.setVisibility(View.GONE);
        	notLogged.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View v) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Login.this);
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnChooseExam:
			specpass.setVisibility(View.GONE);
			pass.setVisibility(View.VISIBLE);
			extmSubmit.setVisibility(View.INVISIBLE);
			mSubmit.setVisibility(View.VISIBLE);
			user.setText("exam_admin");
		break;
		case R.id.btnChooseExt:
			specpass.setVisibility(View.VISIBLE);
			pass.setVisibility(View.GONE);
			extmSubmit.setVisibility(View.VISIBLE);
			mSubmit.setVisibility(View.INVISIBLE);
			user.setText("ext_admin");
		break;
		case R.id.btnLogin:
			//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Login.this);
	        sp.getString("specpassword", "anon");
				if (sp.contains("specpassword"))
				{
					Toast toast = Toast.makeText(Login.this, "Please logout as external examiner first!", Toast.LENGTH_SHORT);
					toast.show();
					//break;
				} 
				else 
				{ 
					new AttemptLogin().execute();
				}
				break;
		case R.id.btnExtLogin:
			//SharedPreferences sp2 = PreferenceManager.getDefaultSharedPreferences(Login.this);
	        sp.getString("password", "anon");
			
			if (sp.contains("password"))
			{
				Toast toast = Toast.makeText(Login.this, "Please logout as regular examiner first!", Toast.LENGTH_SHORT);
				toast.show();
				//break;
			} 
			else 
			{ 
				new ExtAttemptLogin().execute();
			}
			break;
		case R.id.btnLogout:
			//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Login.this);
	
			Editor edit = sp.edit();
			edit.remove("password");
			edit.remove("specpassword");
			edit.commit();

			Toast toast = Toast.makeText(Login.this, "Logout Complete!", Toast.LENGTH_SHORT);
			toast.show();
	
			//Intent i = new Intent(Login.this, Home.class);
			finish();
			//startActivity(i);
		break;

		default:
			break;
		}
	}

	class AttemptLogin extends AsyncTask<String, String, String> {

		 /**
         * Before starting background thread Show Progress Dialog
         * */
		boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                       LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Login Successful!", json.toString());
					// save user data
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(Login.this);
					Editor edit = sp.edit();
					edit.putString("password", password);
					edit.commit();					
					
					//Intent i = new Intent(Login.this, Home.class);
					finish();
					//startActivity(i);
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);
				}
				
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

		}
		/**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

	}
	
	class ExtAttemptLogin extends AsyncTask<String, String, String> {

		 /**
        * Before starting background thread Show Progress Dialog
        * */
		boolean failure = false;

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(Login.this);
           pDialog.setMessage("Attempting login...");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(true);
           pDialog.show();
       }

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
           int success;
           String username = user.getText().toString();
           String specpassword = specpass.getText().toString();
           
           try {
               // Building Parameters
               List<NameValuePair> params = new ArrayList<NameValuePair>();
               params.add(new BasicNameValuePair("username", username));
               params.add(new BasicNameValuePair("specpassword", specpassword));

               Log.d("request!", "starting");
               // getting product details by making HTTP request
               JSONObject json = jsonParser.makeHttpRequest(
                      LOGIN_URL, "POST", params);

               // check your log for json response
               Log.d("Login attempt", json.toString());

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Login Successful!", json.toString());
					// save user data
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(Login.this);
					Editor edit = sp.edit();
					edit.putString("specpassword", specpassword);
					edit.commit();					
					
					//Intent i = new Intent(Login.this, Home.class);
					finish();
					//startActivity(i);
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);
				}
				
           } catch (JSONException e) {
               e.printStackTrace();
           }

           return null;

		}
		/**
        * After completing background task Dismiss the progress dialog
        * **/
       protected void onPostExecute(String file_url) {
           // dismiss the dialog once product deleted
           pDialog.dismiss();
           if (file_url != null){
           	Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
           }

       }

	}

}

