package com.marke.irishwatersafety;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CandidateList extends ListActivity implements OnClickListener{
	
	Button candAdd;
	//SearchView candSearch;
	//CheckBox candCheckbox;

	// Progress Dialog
	private ProgressDialog pDialog;

	// php read participants script

	// localhost :
	// testing on your device
	// put your local ip instead, on windows, run CMD > ipconfig
	// or in mac's terminal type ifconfig and look for the ip under en0 or en1
	// private static final String READ_RESULTS_URL =
	// "http://xxx.xxx.x.x:1234/webservice/fullresults.php";

	// testing on Emulator:
	private static final String READ_RESULTS_URL = "http://markeisenberg.org/lifeguard-exam/webservice/fullresults.php";

	// testing from a real server:
	// private static final String READ_RESULTS_URL =
	// "http://www.mybringback.com/webservice/fullresults.php";

	// JSON IDS:
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NAME = "name";
	private static final String TAG_PDATA = "participantdata";
	private static final String TAG_COMPLETE = "complete";
	private static final String TAG_ID = "id";
	private static final String TAG_SURNAME = "surname";
	private static final String TAG_DOB = "dob";
	// it's important to note that the message is both in the parent branch of
	// our JSON tree that displays a "Participant Available" or a "No Participant Available"
	// message,
	// and there is also a message for each individual participant, listed under the
	// "participant data"
	// category, that displays what the user typed as their surname.

	// An array of all of our participants
	private JSONArray mParticipants = null;
	// manages all of our participants in a list.
	private ArrayList<HashMap<String, String>> mParticipantList;

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cand_list_menu, menu);
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// note that use candidate_layout.xml instead of our single_post.xml
		setContentView(R.layout.candidate_layout);
		candAdd = (Button)findViewById(R.id.btnAdd);
		candAdd.setOnClickListener(this);
		//candCheckbox = (CheckBox)findViewById(R.id.completionstatus);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loading the participants via AsyncTask
		new LoadParticipants().execute();
	}

	public void addParticipant(View v) {
		Intent i = new Intent(CandidateList.this, AddParticipant.class);
		startActivity(i);
	}

	/**
	 * Retrieves recent post data from the server.
	 */
	public void updateJSONdata() {

		// Instantiate the arraylist to contain all the JSON data.
		// we are going to use a bunch of key-value pairs, referring
		// to the json element name, and the content, for example,
		// message it the tag

		mParticipantList = new ArrayList<HashMap<String, String>>();

		// Power up the J parser
		JSONParser jParser = new JSONParser();
		// Feed our results url, and it spits us
		// back a JSON object.
		JSONObject json = jParser.getJSONFromUrl(READ_RESULTS_URL);

		// when parsing JSON stuff, we should probably
		// try to catch any exceptions:
		try {

			// mParticipants will tell us how many "participants" are available
			mParticipants = json.getJSONArray(TAG_PDATA);

			// looping through all participants according to the json object returned
			for (int i = 0; i < mParticipants.length(); i++) {
				JSONObject c = mParticipants.getJSONObject(i);

				// gets the content of each tag
				String id = c.getString(TAG_ID);
				String name = c.getString(TAG_NAME);
				String surname = c.getString(TAG_SURNAME);
				String dob = c.getString(TAG_DOB);
				String complete = c.getString(TAG_COMPLETE);

				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				map.put(TAG_ID, id);
				map.put(TAG_NAME, name);
				map.put(TAG_SURNAME, surname);
				map.put(TAG_DOB, dob);
				map.put(TAG_COMPLETE, complete);

				// adding HashList to ArrayList
				mParticipantList.add(map);

				// our JSON data is up to date same with our array list
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts the parsed data into the listview.
	 */
	private void updateList() {
		// For a ListActivity we need to set the List Adapter, and in order to do
		//that, we need to create a ListAdapter.  This SimpleAdapter,
		//will utilize our updated Hashmapped ArrayList, 
		//use our single_post xml template for each item in our list,
		//and place the appropriate info from the list to the
		//correct GUI id.  Order is important here.
		ListAdapter adapter = new SimpleAdapter(this, mParticipantList,
				R.layout.single_post, 
				new String[] { TAG_ID, TAG_NAME, TAG_SURNAME, TAG_DOB, TAG_COMPLETE }, 
				new int[] { R.id.id, R.id.name, R.id.surname, R.id.dob, R.id.tvCompleteCheck });

		// List adapter:
		setListAdapter(adapter);
		
		// Optional: when the user clicks a list item we 
		//could do something here.
		ListView lv = getListView();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String sendid = ((TextView) view.findViewById(R.id.id)).getText()
                        .toString();
 
                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        SingleCandidate.class);
                // sending pid to next activity
                in.putExtra(TAG_ID, sendid);
 
                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
			}
		});
	}

	public class LoadParticipants extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CandidateList.this);
			pDialog.setMessage("Loading Participants...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			updateJSONdata();
			return null;

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			updateList();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btnAdd:
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(CandidateList.this);
	        sp.getString("specpassword", "anon");
	        
			if (sp.contains("specpassword"))
			{
				Intent openNewCandidate = new Intent(this, AddParticipant.class);
				startActivity(openNewCandidate);
				break;
			} 
			else 
			{ 
				Toast toast = Toast.makeText(CandidateList.this, "Not logged in as external!", Toast.LENGTH_SHORT);
				toast.show();
			}
			break;
		}
	}
}
