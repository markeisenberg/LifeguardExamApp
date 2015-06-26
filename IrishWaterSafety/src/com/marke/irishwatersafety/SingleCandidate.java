package com.marke.irishwatersafety;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class SingleCandidate extends Activity {

	EditText txtName;
	EditText txtSurname;
	EditText txtDOB;
	EditText txtSecA_C4R2_Marking;
	EditText txtSecA_C4R3_Marking;
	EditText txtSecA_C4R4_Marking;
	EditText txtSecA_C4R5_Marking;
	EditText txtSecA_C4R6_Marking;
	EditText txtSecA_C4R7_Marking;
	EditText txtSecA_C4R8_Marking;
	EditText txtSecA_C4R9_Marking;
	EditText txtSecA_C4R10_Marking;
	EditText txtSecA_C4R11_Marking;
	EditText txtSecA_C4R13_Marking;
	
	EditText txtSecB_C4R16_Marking;
	
	EditText txtSecC_C4R18_Marking;
	EditText txtSecC_C4R19_Marking;
	EditText txtSecC_C4R20_Marking;
	EditText txtSecC_C4R21_Marking;
	EditText txtSecC_C4R22_Marking;
	
	EditText txtSecA_R2_Check;
	EditText txtSecA_R3_Check;
	EditText txtSecA_R4_Check;
	EditText txtSecA_R5_Check;
	EditText txtSecA_R6_Check;
	EditText txtSecA_R7_Check;
	EditText txtSecA_R8_Check;
	EditText txtSecA_R9_Check;
	EditText txtSecA_R10_Check;
	EditText txtSecA_R11_Check;
	
	//CheckBox checkComplete;
	
	CheckBox checkSecA_C2R2;
	CheckBox checkSecA_C2R3;
	CheckBox checkSecA_C2R4;
	CheckBox checkSecA_C2R5;
	CheckBox checkSecA_C2R6;
	CheckBox checkSecA_C2R7;
	CheckBox checkSecA_C2R8;
	CheckBox checkSecA_C2R9;
	CheckBox checkSecA_C2R10;
	CheckBox checkSecA_C2R11;
	
	CheckBox checkSecA_C3R2;
	CheckBox checkSecA_C3R3;
	CheckBox checkSecA_C3R4;
	CheckBox checkSecA_C3R5;
	CheckBox checkSecA_C3R6;
	CheckBox checkSecA_C3R7;
	CheckBox checkSecA_C3R8;
	CheckBox checkSecA_C3R9;
	CheckBox checkSecA_C3R10;
	CheckBox checkSecA_C3R11;
	
	EditText txtExamConfirmCheck;
	EditText txtExtConfirmCheck;
	
	EditText txtSectionATotal;
	EditText txtSectionCTotal;
	EditText txtPassFailCheck;
	EditText txtCompleteCheck;
	EditText txtAbsoluteTotal;
	
	Button btnSave, btnExtSave;
	Button btnDelete;

	String sendid;
	//String compc = "";
	
	double r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r13, secAtotal,
	br16, cr18, cr19, cr20, cr21, cr22, secCtotal, absoluteTotal = 0;
	int r2c, r3c, r4c, r5c, r6c, r7c, r8c, r9c, r10c, r11c, r13c = 0;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParserEdit jsonParser = new JSONParserEdit();

	// single participant url
	private static final String url_participant_detials = "http://markeisenberg.org/lifeguard-exam/webservice/get_participant_details.php";

	// url to update participant
	private static final String url_update_participant = "http://markeisenberg.org/lifeguard-exam/webservice/update_participant.php";
	
	// url to delete participant
	private static final String url_delete_participant = "http://markeisenberg.org/lifeguard-exam/webservice/delete_participant.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_CANDIDATE = "candidate";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_SURNAME = "surname";
	private static final String TAG_DOB = "dob";
	private static final String TAG_R2MARK0TO4 = "r2mark0to4";
	private static final String TAG_R3MARK0TO4 = "r3mark0to4";
	private static final String TAG_R4MARK0TO4 = "r4mark0to4";
	private static final String TAG_R5MARK0TO4 = "r5mark0to4";
	private static final String TAG_R6MARK0TO4 = "r6mark0to4";
	private static final String TAG_R7MARK0TO4 = "r7mark0to4";
	private static final String TAG_R8MARK0TO4 = "r8mark0to4";
	private static final String TAG_R9MARK0TO9 = "r9mark0to9";
	private static final String TAG_R10MARK0TO9 = "r10mark0to9";
	private static final String TAG_R11MARK0TO9 = "r11mark0to9";
	private static final String TAG_MCQMARK0TO10 = "mcqmark0to10";
	private static final String TAG_SECTIONATOTAL = "sectionatotal";
	
	private static final String TAG_RTIMEDSWIM0TO45 = "rtimedswim0to45";
	private static final String TAG_RTOW0TO15 = "rtow0to15";
	private static final String TAG_RFRONT0TO25 = "rfront0to25";
	private static final String TAG_RBACKT0TO25 = "rback0to25";
	private static final String TAG_RSPINAL0TO20 = "rspinal0to20";
	private static final String TAG_RSEARCH0TO20 = "rsearch0to20";
	private static final String TAG_SECTIONCTOTAL = "sectionctotal";
	
	private static final String TAG_ABSOLUTETOTAL = "absolutetotal";
	
	private static final String TAG_R2YES = "r2yes";
	private static final String TAG_R3YES = "r3yes";
	private static final String TAG_R4YES = "r4yes";
	private static final String TAG_R5YES = "r5yes";
	private static final String TAG_R6YES = "r6yes";
	private static final String TAG_R7YES = "r7yes";
	private static final String TAG_R8YES = "r8yes";
	private static final String TAG_R9YES = "r9yes";
	private static final String TAG_R10YES = "r10yes";
	private static final String TAG_R11YES = "r11yes";
	private static final String TAG_EXAMCONFIRM = "exam_confirmed";
	private static final String TAG_EXTCONFIRM= "ext_confirm";
	private static final String TAG_PASSFAIL = "passfail";
	private static final String TAG_COMPLETE = "complete";

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_cand_menu, menu);
        return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_login) {
        	Intent openCandList = new Intent(this, Login.class);
			startActivity(openCandList);
        }
        else if (id == R.id.action_lifeguardmanual) {
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.candidate_single_layout);

		// save button
		btnSave = (Button) findViewById(R.id.btnSubmitExamPass);
		btnExtSave = (Button) findViewById(R.id.btnSubmitExtExamPass);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		txtExamConfirmCheck = (EditText) findViewById(R.id.etExamConfirmCheck);
		txtExtConfirmCheck = (EditText) findViewById(R.id.etExtConfirmCheck);

		// getting participant details from intent
		Intent i = getIntent();
		
		// getting participant id (pid) from intent
		sendid = i.getStringExtra(TAG_ID);

		// Getting complete participant details in background thread
		new GetParticipantDetails().execute();

		// save button click event
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {				
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SingleCandidate.this);
		        sp.getString("password", "anon");
				// starting background task to update participant
				if (sp.contains("password"))
				{
				txtExamConfirmCheck.setText("Yes");
				checkWrong();
				calculate();
				new SaveParticipantDetails().execute();
				} 
				else {
					Toast toast = Toast.makeText(SingleCandidate.this, "Not logged in as examiner!", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});
		btnExtSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SingleCandidate.this);
		        sp.getString("specpassword", "anon");
				// starting background task to update participant
				if (sp.contains("specpassword"))
				{
				txtExtConfirmCheck.setText("Yes");
				checkWrong();
				calculate();
				new SaveParticipantDetails().execute();
				} 
				else {
					Toast toast = Toast.makeText(SingleCandidate.this, "Not logged in as external!", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});

		// Delete button click event
		btnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// deleting product in background thread
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SingleCandidate.this);
		        sp.getString("specpassword", "anon");
		        
				if (sp.contains("specpassword"))
				{
					new DeleteParticipant().execute();
				} 
				else 
				{ 
					Toast toast = Toast.makeText(SingleCandidate.this, "Not logged in as external!", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});
		
		//checkComplete = (CheckBox) findViewById(R.id.checkBox1);
		
		checkSecA_C2R2 = (CheckBox) findViewById(R.id.cbSecA_C2R2_Yes);
		checkSecA_C3R2 = (CheckBox) findViewById(R.id.cbSecA_C3R2_No);
		checkSecA_C2R3 = (CheckBox) findViewById(R.id.cbSecA_C2R3_Yes);
		checkSecA_C3R3 = (CheckBox) findViewById(R.id.cbSecA_C3R3_No);
		checkSecA_C2R4 = (CheckBox) findViewById(R.id.cbSecA_C2R4_Yes);
		checkSecA_C3R4 = (CheckBox) findViewById(R.id.cbSecA_C3R4_No);
		checkSecA_C2R5 = (CheckBox) findViewById(R.id.cbSecA_C2R5_Yes);
		checkSecA_C3R5 = (CheckBox) findViewById(R.id.cbSecA_C3R5_No);
		checkSecA_C2R6 = (CheckBox) findViewById(R.id.cbSecA_C2R6_Yes);
		checkSecA_C3R6 = (CheckBox) findViewById(R.id.cbSecA_C3R6_No);
		checkSecA_C2R7 = (CheckBox) findViewById(R.id.cbSecA_C2R7_Yes);
		checkSecA_C3R7 = (CheckBox) findViewById(R.id.cbSecA_C3R7_No);
		checkSecA_C2R8 = (CheckBox) findViewById(R.id.cbSecA_C2R8_Yes);
		checkSecA_C3R8 = (CheckBox) findViewById(R.id.cbSecA_C3R8_No);
		checkSecA_C2R9 = (CheckBox) findViewById(R.id.cbSecA_C2R9_Yes);
		checkSecA_C3R9 = (CheckBox) findViewById(R.id.cbSecA_C3R9_No);
		checkSecA_C2R10 = (CheckBox) findViewById(R.id.cbSecA_C2R10_Yes);
		checkSecA_C3R10 = (CheckBox) findViewById(R.id.cbSecA_C3R10_No);
		checkSecA_C2R11 = (CheckBox) findViewById(R.id.cbSecA_C2R11_Yes);
		checkSecA_C3R11 = (CheckBox) findViewById(R.id.cbSecA_C3R11_No);
		
		checkSecA_C2R2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r2c = 1;
					txtSecA_R2_Check.setText(Integer.toString(r2c));
					checkSecA_C3R2.setChecked(false);
			}
		});
		
		checkSecA_C3R2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r2c = 0;
					txtSecA_R2_Check.setText(Integer.toString(r2c));
					checkSecA_C2R2.setChecked(false);
			}
		});
		
		checkSecA_C2R3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r3c = 1;
					txtSecA_R3_Check.setText(Integer.toString(r3c));
					checkSecA_C3R3.setChecked(false);
			}
		});
		
		checkSecA_C3R3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r3c = 0;
					txtSecA_R3_Check.setText(Integer.toString(r3c));
					checkSecA_C2R3.setChecked(false);
			}
		});

		checkSecA_C2R4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r4c = 1;
					txtSecA_R4_Check.setText(Integer.toString(r4c));
					checkSecA_C3R4.setChecked(false);
			}
		});
		
		checkSecA_C3R4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r4c = 0;
					txtSecA_R4_Check.setText(Integer.toString(r4c));
					checkSecA_C2R4.setChecked(false);
			}
		});

		checkSecA_C2R5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r5c = 1;
					txtSecA_R5_Check.setText(Integer.toString(r5c));
					checkSecA_C3R5.setChecked(false);
			}
		});
		
		checkSecA_C3R5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r5c = 0;
					txtSecA_R5_Check.setText(Integer.toString(r5c));
					checkSecA_C2R5.setChecked(false);
			}
		});

		checkSecA_C2R6.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r6c = 1;
					txtSecA_R6_Check.setText(Integer.toString(r6c));
					checkSecA_C3R6.setChecked(false);
			}
		});
		
		checkSecA_C3R6.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r6c = 0;
					txtSecA_R6_Check.setText(Integer.toString(r6c));
					checkSecA_C2R6.setChecked(false);
			}
		});

		checkSecA_C2R7.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r7c = 1;
					txtSecA_R7_Check.setText(Integer.toString(r7c));
					checkSecA_C3R7.setChecked(false);
			}
		});
		
		checkSecA_C3R7.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r7c = 0;
					txtSecA_R7_Check.setText(Integer.toString(r7c));
					checkSecA_C2R7.setChecked(false);
			}
		});

		checkSecA_C2R8.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r8c = 1;
					txtSecA_R8_Check.setText(Integer.toString(r8c));
					checkSecA_C3R8.setChecked(false);
			}
		});
		
		checkSecA_C3R8.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r8c = 0;
					txtSecA_R8_Check.setText(Integer.toString(r8c));
					checkSecA_C2R8.setChecked(false);
			}
		});

		checkSecA_C2R9.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r9c = 1;
					txtSecA_R9_Check.setText(Integer.toString(r9c));
					checkSecA_C3R9.setChecked(false);
			}
		});
		
		checkSecA_C3R9.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r9c = 0;
					txtSecA_R9_Check.setText(Integer.toString(r9c));
					checkSecA_C2R9.setChecked(false);
			}
		});

		checkSecA_C2R10.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r10c = 1;
					txtSecA_R10_Check.setText(Integer.toString(r10c));
					checkSecA_C3R10.setChecked(false);
			}
		});
		
		checkSecA_C3R10.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r10c = 0;
					txtSecA_R10_Check.setText(Integer.toString(r10c));
					checkSecA_C2R10.setChecked(false);
			}
		});

		checkSecA_C2R11.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r11c = 1;
					txtSecA_R11_Check.setText(Integer.toString(r11c));
					checkSecA_C3R11.setChecked(false);
			}
		});
		
		checkSecA_C3R11.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
					r11c = 0;
					txtSecA_R11_Check.setText(Integer.toString(r11c));
					checkSecA_C2R11.setChecked(false);
			}
		});
	}

	/**
	 * Background Async Task to Get complete participant details
	 * */
	class GetParticipantDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SingleCandidate.this);
			pDialog.setMessage("Loading participant details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting participant details in background thread
		 * */
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("id", sendid));

						// getting participant details by making HTTP request
						// Note that participant details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_participant_detials, "GET", params);

						// check your log for json response
						Log.d("Single Participant Details", json.toString());
						
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received participant details
							JSONArray productObj = json
									.getJSONArray(TAG_CANDIDATE); // JSON Array
							
							// get first participant object from JSON Array
							JSONObject participant = productObj.getJSONObject(0);

							// participant with this id found
							// Edit Text
							txtName = (EditText) findViewById(R.id.editText1);
							txtSurname = (EditText) findViewById(R.id.editText2);
							txtDOB = (EditText) findViewById(R.id.editText3);
							
							txtSecA_C4R2_Marking = (EditText) findViewById(R.id.etSecA_C4R2_Marking);
							txtSecA_C4R3_Marking = (EditText) findViewById(R.id.etSecA_C4R3_Marking);
							txtSecA_C4R4_Marking = (EditText) findViewById(R.id.etSecA_C4R4_Marking);
							txtSecA_C4R5_Marking = (EditText) findViewById(R.id.etSecA_C4R5_Marking);
							txtSecA_C4R6_Marking = (EditText) findViewById(R.id.etSecA_C4R6_Marking);
							txtSecA_C4R7_Marking = (EditText) findViewById(R.id.etSecA_C4R7_Marking);
							txtSecA_C4R8_Marking = (EditText) findViewById(R.id.etSecA_C4R8_Marking);
							txtSecA_C4R9_Marking = (EditText) findViewById(R.id.etSecA_C4R9_Marking);
							txtSecA_C4R10_Marking = (EditText) findViewById(R.id.etSecA_C4R10_Marking);
							txtSecA_C4R11_Marking = (EditText) findViewById(R.id.etSecA_C4R11_Marking);
							txtSecA_C4R13_Marking = (EditText) findViewById(R.id.etSecA_C4R13_Marking);
							
							txtSecB_C4R16_Marking = (EditText) findViewById(R.id.etSecB_C4R16_Marking);
							
							txtSecC_C4R18_Marking = (EditText) findViewById(R.id.etSecC_C4R18_Marking);
							txtSecC_C4R19_Marking = (EditText) findViewById(R.id.etSecC_C4R19_Marking);
							txtSecC_C4R20_Marking = (EditText) findViewById(R.id.etSecC_C4R20_Marking);
							txtSecC_C4R21_Marking = (EditText) findViewById(R.id.etSecC_C4R21_Marking);
							txtSecC_C4R22_Marking = (EditText) findViewById(R.id.etSecC_C4R22_Marking);
							
							txtSectionATotal = (EditText) findViewById(R.id.etSecA_C4R14_Marking);
							txtSectionCTotal = (EditText) findViewById(R.id.etSecC_C4R23_Marking);
							
							txtSecA_R2_Check = (EditText) findViewById(R.id.etSecA_R2_Check);
							txtSecA_R3_Check = (EditText) findViewById(R.id.etSecA_R3_Check);
							txtSecA_R4_Check = (EditText) findViewById(R.id.etSecA_R4_Check);
							txtSecA_R5_Check = (EditText) findViewById(R.id.etSecA_R5_Check);
							txtSecA_R6_Check = (EditText) findViewById(R.id.etSecA_R6_Check);
							txtSecA_R7_Check = (EditText) findViewById(R.id.etSecA_R7_Check);
							txtSecA_R8_Check = (EditText) findViewById(R.id.etSecA_R8_Check);
							txtSecA_R9_Check = (EditText) findViewById(R.id.etSecA_R9_Check);
							txtSecA_R10_Check = (EditText) findViewById(R.id.etSecA_R10_Check);
							txtSecA_R11_Check = (EditText) findViewById(R.id.etSecA_R11_Check);
							
							checkSecA_C2R2 = (CheckBox) findViewById(R.id.cbSecA_C2R2_Yes);
							checkSecA_C2R3 = (CheckBox) findViewById(R.id.cbSecA_C2R3_Yes);
							checkSecA_C2R4 = (CheckBox) findViewById(R.id.cbSecA_C2R4_Yes);
							checkSecA_C2R5 = (CheckBox) findViewById(R.id.cbSecA_C2R5_Yes);
							checkSecA_C2R6 = (CheckBox) findViewById(R.id.cbSecA_C2R6_Yes);
							checkSecA_C2R7 = (CheckBox) findViewById(R.id.cbSecA_C2R7_Yes);
							checkSecA_C2R8 = (CheckBox) findViewById(R.id.cbSecA_C2R8_Yes);
							checkSecA_C2R9 = (CheckBox) findViewById(R.id.cbSecA_C2R9_Yes);
							checkSecA_C2R10 = (CheckBox) findViewById(R.id.cbSecA_C2R10_Yes);
							checkSecA_C2R11 = (CheckBox) findViewById(R.id.cbSecA_C2R11_Yes);
							
							checkSecA_C3R2 = (CheckBox) findViewById(R.id.cbSecA_C3R2_No);
							checkSecA_C3R3 = (CheckBox) findViewById(R.id.cbSecA_C3R3_No);
							checkSecA_C3R4 = (CheckBox) findViewById(R.id.cbSecA_C3R4_No);
							checkSecA_C3R5 = (CheckBox) findViewById(R.id.cbSecA_C3R5_No);
							checkSecA_C3R6 = (CheckBox) findViewById(R.id.cbSecA_C3R6_No);
							checkSecA_C3R7 = (CheckBox) findViewById(R.id.cbSecA_C3R7_No);
							checkSecA_C3R8 = (CheckBox) findViewById(R.id.cbSecA_C3R8_No);
							checkSecA_C3R9 = (CheckBox) findViewById(R.id.cbSecA_C3R9_No);
							checkSecA_C3R10 = (CheckBox) findViewById(R.id.cbSecA_C3R10_No);
							checkSecA_C3R11 = (CheckBox) findViewById(R.id.cbSecA_C3R11_No);
							
							txtAbsoluteTotal = (EditText) findViewById(R.id.etAbsoluteTotal);
							
							txtExamConfirmCheck = (EditText) findViewById(R.id.etExamConfirmCheck);
							txtExtConfirmCheck = (EditText) findViewById(R.id.etExtConfirmCheck);					
							txtPassFailCheck = (EditText) findViewById(R.id.etPassFailCheck);
							txtCompleteCheck = (EditText) findViewById(R.id.etCompleteCheck);

							// display participant data in EditText
							txtName.setText(participant.getString(TAG_NAME));
							txtSurname.setText(participant.getString(TAG_SURNAME));
							txtDOB.setText(participant.getString(TAG_DOB));
							
							txtSecA_C4R2_Marking.setText(participant.getString(TAG_R2MARK0TO4));
							txtSecA_C4R3_Marking.setText(participant.getString(TAG_R3MARK0TO4));
							txtSecA_C4R4_Marking.setText(participant.getString(TAG_R4MARK0TO4));
							txtSecA_C4R5_Marking.setText(participant.getString(TAG_R5MARK0TO4));
							txtSecA_C4R6_Marking.setText(participant.getString(TAG_R6MARK0TO4));
							txtSecA_C4R7_Marking.setText(participant.getString(TAG_R7MARK0TO4));
							txtSecA_C4R8_Marking.setText(participant.getString(TAG_R8MARK0TO4));
							txtSecA_C4R9_Marking.setText(participant.getString(TAG_R9MARK0TO9));
							txtSecA_C4R10_Marking.setText(participant.getString(TAG_R10MARK0TO9));
							txtSecA_C4R11_Marking.setText(participant.getString(TAG_R11MARK0TO9));
							
							txtSecA_C4R13_Marking.setText(participant.getString(TAG_MCQMARK0TO10));
							
							txtSecB_C4R16_Marking.setText(participant.getString(TAG_RTIMEDSWIM0TO45));
							txtSecC_C4R18_Marking.setText(participant.getString(TAG_RTOW0TO15));
							txtSecC_C4R19_Marking.setText(participant.getString(TAG_RFRONT0TO25));
							txtSecC_C4R20_Marking.setText(participant.getString(TAG_RBACKT0TO25));
							txtSecC_C4R21_Marking.setText(participant.getString(TAG_RSPINAL0TO20));
							txtSecC_C4R22_Marking.setText(participant.getString(TAG_RSEARCH0TO20));
							
							txtSectionCTotal.setText(participant.getString(TAG_SECTIONCTOTAL));
							
							txtAbsoluteTotal.setText(participant.getString(TAG_ABSOLUTETOTAL));
							
							txtSectionATotal.setText(participant.getString(TAG_SECTIONATOTAL));
							
							txtSecA_R2_Check.setText(participant.getString(TAG_R2YES));
							txtSecA_R3_Check.setText(participant.getString(TAG_R3YES));
							txtSecA_R4_Check.setText(participant.getString(TAG_R4YES));
							txtSecA_R5_Check.setText(participant.getString(TAG_R5YES));
							txtSecA_R6_Check.setText(participant.getString(TAG_R6YES));
							txtSecA_R7_Check.setText(participant.getString(TAG_R7YES));
							txtSecA_R8_Check.setText(participant.getString(TAG_R8YES));
							txtSecA_R9_Check.setText(participant.getString(TAG_R9YES));
							txtSecA_R10_Check.setText(participant.getString(TAG_R10YES));
							txtSecA_R11_Check.setText(participant.getString(TAG_R11YES));
							
							txtExamConfirmCheck.setText(participant.getString(TAG_EXAMCONFIRM));
							txtExtConfirmCheck.setText(participant.getString(TAG_EXTCONFIRM));
							txtPassFailCheck.setText(participant.getString(TAG_PASSFAIL));
							txtCompleteCheck.setText(participant.getString(TAG_COMPLETE));

						}else{
							// participant with id not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
			r2c = Integer.parseInt(txtSecA_R2_Check.getText().toString());
			r3c = Integer.parseInt(txtSecA_R3_Check.getText().toString());
			r4c = Integer.parseInt(txtSecA_R4_Check.getText().toString());
			r5c = Integer.parseInt(txtSecA_R5_Check.getText().toString());
			r6c = Integer.parseInt(txtSecA_R6_Check.getText().toString());
			r7c = Integer.parseInt(txtSecA_R7_Check.getText().toString());
			r8c = Integer.parseInt(txtSecA_R8_Check.getText().toString());
			r9c = Integer.parseInt(txtSecA_R9_Check.getText().toString());
			r10c = Integer.parseInt(txtSecA_R10_Check.getText().toString());
			r11c = Integer.parseInt(txtSecA_R11_Check.getText().toString());
			//compc = Integer.parseInt(txtCompleteCheck.getText().toString());
			
			if (r2c == 1){
				checkSecA_C2R2.setChecked(true);
			}if (r3c == 1){
				checkSecA_C2R3.setChecked(true);
			}if (r4c == 1){
				checkSecA_C2R4.setChecked(true);
			}if (r5c == 1){
				checkSecA_C2R5.setChecked(true);
			}if (r6c == 1){
				checkSecA_C2R6.setChecked(true);
			}if (r7c == 1){
				checkSecA_C2R7.setChecked(true);
			}if (r8c == 1){
				checkSecA_C2R8.setChecked(true);
			}if (r9c == 1){
				checkSecA_C2R9.setChecked(true);
			}if (r10c == 1){
				checkSecA_C2R10.setChecked(true);
			}if (r11c == 1){
				checkSecA_C2R11.setChecked(true);
				
			}if (r2c == 0){
					checkSecA_C3R2.setChecked(true);
			}if (r3c == 0){
					checkSecA_C3R3.setChecked(true);
			}if (r4c == 0){
					checkSecA_C3R4.setChecked(true);
			}if (r5c == 0){
					checkSecA_C3R5.setChecked(true);
			}if (r6c == 0){
					checkSecA_C3R6.setChecked(true);
			}if (r7c == 0){
					checkSecA_C3R7.setChecked(true);
			}if (r8c == 0){
					checkSecA_C3R8.setChecked(true);
			}if (r9c == 0){
					checkSecA_C3R9.setChecked(true);
			}if (r10c == 0){
					checkSecA_C3R10.setChecked(true);
			}if (r11c == 0){
					checkSecA_C3R11.setChecked(true);
			}
		}
	}

	/**
	 * Background Async Task to  Save participant Details
	 * */
	class SaveParticipantDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SingleCandidate.this);
			pDialog.setMessage("Saving participant ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving participant
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String name = txtName.getText().toString();
			String surname = txtSurname.getText().toString();
			String dob = txtDOB.getText().toString();
			
			String r2mark0to4 = txtSecA_C4R2_Marking.getText().toString();
			String r3mark0to4 = txtSecA_C4R3_Marking.getText().toString();
			String r4mark0to4 = txtSecA_C4R4_Marking.getText().toString();
			String r5mark0to4 = txtSecA_C4R5_Marking.getText().toString();
			String r6mark0to4 = txtSecA_C4R6_Marking.getText().toString();
			String r7mark0to4 = txtSecA_C4R7_Marking.getText().toString();
			String r8mark0to4 = txtSecA_C4R8_Marking.getText().toString();
			String r9mark0to9 = txtSecA_C4R9_Marking.getText().toString();
			String r10mark0to9 = txtSecA_C4R10_Marking.getText().toString();
			String r11mark0to9 = txtSecA_C4R11_Marking.getText().toString();
			
			String mcqmark0to10 = txtSecA_C4R13_Marking.getText().toString();
			
			String rtimedswim0to45 = txtSecB_C4R16_Marking.getText().toString();
			String rtow0to15 = txtSecC_C4R18_Marking.getText().toString();
			String rfront0to25 = txtSecC_C4R19_Marking.getText().toString();
			String rback0to25 = txtSecC_C4R20_Marking.getText().toString();
			String rspinal0to20 = txtSecC_C4R21_Marking.getText().toString();
			String rsearch0to20 = txtSecC_C4R22_Marking.getText().toString();
			
			String sectionatotal = txtSectionATotal.getText().toString();
			String sectionctotal = txtSectionCTotal.getText().toString();
			
			String absolutetotal = txtAbsoluteTotal.getText().toString();
			
			String r2yes = txtSecA_R2_Check.getText().toString();
			String r3yes = txtSecA_R3_Check.getText().toString();
			String r4yes = txtSecA_R4_Check.getText().toString();
			String r5yes = txtSecA_R5_Check.getText().toString();
			String r6yes = txtSecA_R6_Check.getText().toString();
			String r7yes = txtSecA_R7_Check.getText().toString();
			String r8yes = txtSecA_R8_Check.getText().toString();
			String r9yes = txtSecA_R9_Check.getText().toString();
			String r10yes = txtSecA_R10_Check.getText().toString();
			String r11yes = txtSecA_R11_Check.getText().toString();
			
			String exam_confirmed = txtExamConfirmCheck.getText().toString();
			String ext_confirm = txtExtConfirmCheck.getText().toString();
			String passfail = txtPassFailCheck.getText().toString();
			String complete = txtCompleteCheck.getText().toString();
			
			

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_ID, sendid));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_SURNAME, surname));
			params.add(new BasicNameValuePair(TAG_DOB, dob));
			 
			params.add(new BasicNameValuePair(TAG_R2MARK0TO4, r2mark0to4)); 
			params.add(new BasicNameValuePair(TAG_R3MARK0TO4, r3mark0to4)); 
			params.add(new BasicNameValuePair(TAG_R4MARK0TO4, r4mark0to4)); 
			params.add(new BasicNameValuePair(TAG_R5MARK0TO4, r5mark0to4)); 
			params.add(new BasicNameValuePair(TAG_R6MARK0TO4, r6mark0to4)); 
			params.add(new BasicNameValuePair(TAG_R7MARK0TO4, r7mark0to4)); 
			params.add(new BasicNameValuePair(TAG_R8MARK0TO4, r8mark0to4)); 
			params.add(new BasicNameValuePair(TAG_R9MARK0TO9, r9mark0to9)); 
			params.add(new BasicNameValuePair( TAG_R10MARK0TO9, r10mark0to9)); 
			params.add(new BasicNameValuePair(TAG_R11MARK0TO9, r11mark0to9)); 
			
			params.add(new BasicNameValuePair(TAG_MCQMARK0TO10, mcqmark0to10)); 
			
			params.add(new BasicNameValuePair(TAG_RTIMEDSWIM0TO45, rtimedswim0to45)); 
			params.add(new BasicNameValuePair(TAG_RTOW0TO15, rtow0to15));
			params.add(new BasicNameValuePair(TAG_RFRONT0TO25, rfront0to25)); 
			params.add(new BasicNameValuePair(TAG_RBACKT0TO25, rback0to25)); 
			params.add(new BasicNameValuePair(TAG_RSPINAL0TO20, rspinal0to20)); 
			params.add(new BasicNameValuePair(TAG_RSEARCH0TO20, rsearch0to20)); 
			params.add(new BasicNameValuePair(TAG_SECTIONCTOTAL, sectionctotal)); 
			params.add(new BasicNameValuePair(TAG_ABSOLUTETOTAL, absolutetotal)); 
			
			params.add(new BasicNameValuePair(TAG_SECTIONATOTAL, sectionatotal));
			
			params.add(new BasicNameValuePair(TAG_R2YES, r2yes));
			params.add(new BasicNameValuePair(TAG_R3YES, r3yes)); 
			params.add(new BasicNameValuePair(TAG_R4YES, r4yes)); 
			params.add(new BasicNameValuePair(TAG_R5YES, r5yes)); 
			params.add(new BasicNameValuePair(TAG_R6YES, r6yes)); 
			params.add(new BasicNameValuePair(TAG_R7YES, r7yes));
			params.add(new BasicNameValuePair(TAG_R8YES, r8yes)); 
			params.add(new BasicNameValuePair(TAG_R9YES, r9yes));
			params.add(new BasicNameValuePair(TAG_R10YES, r10yes)); 
			params.add(new BasicNameValuePair(TAG_R11YES, r11yes));
			
			params.add(new BasicNameValuePair(TAG_EXAMCONFIRM, exam_confirmed));
			params.add(new BasicNameValuePair(TAG_EXTCONFIRM, ext_confirm));
			params.add(new BasicNameValuePair(TAG_PASSFAIL, passfail));
			params.add(new BasicNameValuePair(TAG_COMPLETE, complete));

			// sending modified data through http request
			// Notice that update participant url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_participant,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about participant update
					setResult(100, i);
					finish();
				} else {
					// failed to update participant
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
			// dismiss the dialog once participant updated
			pDialog.dismiss();
		}
	}

	/*****************************************************************
	 * Background Async Task to Delete Participant
	 * */
	class DeleteParticipant extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SingleCandidate.this);
			pDialog.setMessage("Deleting Participant...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Deleting participant
		 * */
		protected String doInBackground(String... args) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", sendid));

				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(
						url_delete_participant, "POST", params);

				// check your log for json response
				Log.d("Delete Participant", json.toString());
				
				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// participant successfully deleted
					// notify previous activity by sending code 100
					Intent i = getIntent();
					// send result code 100 to notify about participant deletion
					setResult(100, i);
					finish();
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
			// dismiss the dialog once participant deleted
			pDialog.dismiss();

		}

	}
	private void calculate()
	  {
	      try {
			r2 = Double.parseDouble(txtSecA_C4R2_Marking.getText().toString());
			  r3 = Double.parseDouble(txtSecA_C4R3_Marking.getText().toString());
			  r4 = Double.parseDouble(txtSecA_C4R4_Marking.getText().toString());
			  r5 = Double.parseDouble(txtSecA_C4R5_Marking.getText().toString());
			  r6 = Double.parseDouble(txtSecA_C4R6_Marking.getText().toString());
			  r7 = Double.parseDouble(txtSecA_C4R7_Marking.getText().toString());
			  r8 = Double.parseDouble(txtSecA_C4R8_Marking.getText().toString());
			  r9 = Double.parseDouble(txtSecA_C4R9_Marking.getText().toString());
			  r10 = Double.parseDouble(txtSecA_C4R10_Marking.getText().toString());
			  r11 = Double.parseDouble(txtSecA_C4R11_Marking.getText().toString());
			  r13 = Double.parseDouble(txtSecA_C4R13_Marking.getText().toString());
			  br16 = Double.parseDouble(txtSecB_C4R16_Marking.getText().toString());
			  cr18 = Double.parseDouble(txtSecC_C4R18_Marking.getText().toString());
			  cr19 = Double.parseDouble(txtSecC_C4R19_Marking.getText().toString());
			  cr20 = Double.parseDouble(txtSecC_C4R20_Marking.getText().toString());
			  cr21 = Double.parseDouble(txtSecC_C4R21_Marking.getText().toString());
			  cr22 = Double.parseDouble(txtSecC_C4R22_Marking.getText().toString());
			  
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast toast = Toast.makeText(SingleCandidate.this, "A marking field was left blank, please put value into field!", Toast.LENGTH_LONG);
			toast.show();
			finish();
			startActivity(getIntent());
		}
	      secAtotal = r2 + r3 + r4 + r5 + r6 + r7 + r8 + r9 + r10 + r11 + r13;
	      secCtotal = cr18 + cr19 + cr20 + cr21 + cr22;
	      absoluteTotal = secAtotal + br16 + secCtotal;
	      txtSectionATotal.setText(Double.toString(secAtotal));
	      txtSectionCTotal.setText(Double.toString(secCtotal));
	      txtAbsoluteTotal.setText(Double.toString(absoluteTotal));
	      
	      if (r2c == 1 && r3c == 1 && r4c == 1 && r5c == 1 && r6c == 1 && r7c == 1 && r8c == 1
	    		  && r9c == 1 && r10c == 1 && r11c == 1)
	      {
	    	  txtCompleteCheck.setText("Complete");
	      }
	      else if (r2c == 0 || r3c == 0 || r4c == 0 || r5c == 0 || r6c == 0 || r7c == 0 || r8c == 0
	    		  || r9c == 0 || r10c == 0 || r11c == 0) 
	      {
	    	  txtCompleteCheck.setText("Incomplete");
	      }
	      
	      if (r2c == 0 || r3c == 0 || r4c == 0 || r5c == 0 || r6c == 0 || r7c == 0 || r8c == 0
	    		  || r9c == 0 || r10c == 0 || r11c == 0 || r2 == 0 || r3 == 0 || r4 == 0 
	    		  || r5 == 0 || r6 == 0 || r7 == 0 || r8 == 0
	    		  || r9 == 0 || r10 == 0 || r11 == 0 || r13 == 0
	    		  || br16 == 0 || cr18 == 0 || cr19 == 0 || cr20 == 0
	    		  || cr21 == 0 || cr22 == 0)
	      {
	    	  txtPassFailCheck.setText("Incomplete");
	      }
	      else if (secAtotal < 32 || br16 == 0 || secCtotal < 52){
	    	  txtPassFailCheck.setText("Fail");
	      }
	      
	      else if (secAtotal > 32 || br16 > 0 || secCtotal > 52){
	    	  txtPassFailCheck.setText("Pass");
	      }
	  }
	private void checkWrong()
	{
		try {
			r2 = Double.parseDouble(txtSecA_C4R2_Marking.getText().toString());
			  r3 = Double.parseDouble(txtSecA_C4R3_Marking.getText().toString());
			  r4 = Double.parseDouble(txtSecA_C4R4_Marking.getText().toString());
			  r5 = Double.parseDouble(txtSecA_C4R5_Marking.getText().toString());
			  r6 = Double.parseDouble(txtSecA_C4R6_Marking.getText().toString());
			  r7 = Double.parseDouble(txtSecA_C4R7_Marking.getText().toString());
			  r8 = Double.parseDouble(txtSecA_C4R8_Marking.getText().toString());
			  r9 = Double.parseDouble(txtSecA_C4R9_Marking.getText().toString());
			  r10 = Double.parseDouble(txtSecA_C4R10_Marking.getText().toString());
			  r11 = Double.parseDouble(txtSecA_C4R11_Marking.getText().toString());
			  r13 = Double.parseDouble(txtSecA_C4R13_Marking.getText().toString());
			  br16 = Double.parseDouble(txtSecB_C4R16_Marking.getText().toString());
			  cr18 = Double.parseDouble(txtSecC_C4R18_Marking.getText().toString());
			  cr19 = Double.parseDouble(txtSecC_C4R19_Marking.getText().toString());
			  cr20 = Double.parseDouble(txtSecC_C4R20_Marking.getText().toString());
			  cr21 = Double.parseDouble(txtSecC_C4R21_Marking.getText().toString());
			  cr22 = Double.parseDouble(txtSecC_C4R22_Marking.getText().toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast toast = Toast.makeText(SingleCandidate.this, "A marking field was left blank, please put value into field!", Toast.LENGTH_LONG);
			toast.show();
			finish();
			startActivity(getIntent());
		}
		      
		      if (r2 > 4 || r3 > 4 || r4 > 4 || r5 > 4 || r6 > 4 || r7 > 4 || r8 > 4
		    	  || r9 > 9 || r10 > 9 || r11 > 9 || r13 > 10 || br16 > 45
		    	  || cr18 > 15 || cr19 > 25 || cr20 > 25 || cr21 > 20 || cr22 > 20)
		      {
		    	  Toast toast = Toast.makeText(SingleCandidate.this, "One of the values exceeds marking limit. Please change it!", Toast.LENGTH_LONG);
				  toast.show();
				  finish();
				  startActivity(getIntent());
		      }
	}
}
