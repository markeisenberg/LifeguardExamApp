package com.marke.irishwatersafety;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class TabulatedResults extends ListActivity implements OnClickListener{

	// Progress Dialog
	private ProgressDialog pDialog;
	
	Button sortList;

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
	// "http://markeisenberg.org/webservice/fullresults.php";

	// JSON IDS:
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NAME = "name";
	private static final String TAG_PDATA = "participantdata";
	private static final String TAG_ID = "id";
	private static final String TAG_SURNAME = "surname";
	private static final String TAG_DOB = "dob";
	private static final String TAG_SECTIONATOTAL = "sectionatotal";
	private static final String TAG_RTIMEDSWIM0TO45 = "rtimedswim0to45";
	private static final String TAG_SECTIONCTOTAL = "sectionctotal";
	private static final String TAG_ABSOLUTETOTAL = "absolutetotal";
	//private static final String TAG_RANK = "rank";
	private static final String TAG_PASSFAIL = "passfail";
	private static final String TAG_EXAMCONFIRM = "exam_confirmed";
	private static final String TAG_EXTCONFIRM = "ext_confirm";

	// An array of all of our participants
	private JSONArray mParticipants = null;
	// manages all of our participants in a list.
	private ArrayList<HashMap<String, String>> mParticipantList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// note that use tabulated_layout.xml instead of our single_post.xml
		setContentView(R.layout.tabulated_layout);
		sortList = (Button)findViewById(R.id.btnSort);
		sortList.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loading the participants via AsyncTask
		new LoadParticipants().execute();
	}

	public void addParticipant(View v) {
		Intent i = new Intent(TabulatedResults.this, AddParticipant.class);
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

		// Using the J parser
		JSONParser jParser = new JSONParser();
		// Feed our results url, and it gives
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
				String name = c.getString(TAG_NAME);
				String surname = c.getString(TAG_SURNAME);
				String dob = c.getString(TAG_DOB);
				String sectionatotal = c.getString(TAG_SECTIONATOTAL);
				String rtimedswim0to45 = c.getString(TAG_RTIMEDSWIM0TO45);
				String sectionctotal = c.getString(TAG_SECTIONCTOTAL);
				String absolutetotal = c.getString(TAG_ABSOLUTETOTAL);
				//String rank = c.getString(TAG_RANK);
				String passfail = c.getString(TAG_PASSFAIL);
				String exam_confirmed = c.getString(TAG_EXAMCONFIRM);
				String ext_confirm = c.getString(TAG_EXTCONFIRM);

				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				map.put(TAG_NAME, name);
				map.put(TAG_SURNAME, surname);
				map.put(TAG_DOB, dob);
				map.put(TAG_SECTIONATOTAL, sectionatotal);
				map.put(TAG_RTIMEDSWIM0TO45, rtimedswim0to45);
				map.put(TAG_SECTIONCTOTAL, sectionctotal);
				map.put(TAG_ABSOLUTETOTAL, absolutetotal);
				//map.put(TAG_RANK, rank);
				map.put(TAG_PASSFAIL, passfail);
				map.put(TAG_EXAMCONFIRM, exam_confirmed);
				map.put(TAG_EXTCONFIRM, ext_confirm);

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
		//use our single_user_results xml template for each item in our list,
		//and place the appropriate info from the list to the
		//correct GUI id.  Order is important here.
		ListAdapter adapter = new SimpleAdapter(this, mParticipantList,
				R.layout.single_user_results, 
				new String[] { TAG_NAME, TAG_SURNAME, TAG_SECTIONATOTAL, TAG_RTIMEDSWIM0TO45,
				TAG_SECTIONCTOTAL, TAG_ABSOLUTETOTAL, TAG_PASSFAIL, TAG_EXAMCONFIRM, TAG_EXTCONFIRM, TAG_DOB}, 
				new int[] { R.id.name, R.id.surname, R.id.sectionatotal, R.id.sectionbtotal,
				R.id.sectionctotal, R.id.total, R.id.passfail, R.id.exam_confirmed, R.id.ext_confirm, R.id.dob });

		// List adapter:
		setListAdapter(adapter);
		
		// Optional: when the user clicks a list item we 
		//could do something here.
		ListView lv = getListView();	
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// On Click do something 

			}
		});
	}
	
	//Same as updateList()
	private void refreshList() {
		ListAdapter adapter = new SimpleAdapter(this, mParticipantList,
				R.layout.single_user_results, 
				new String[] { TAG_NAME, TAG_SURNAME, TAG_SECTIONATOTAL, TAG_RTIMEDSWIM0TO45,
				TAG_SECTIONCTOTAL, TAG_ABSOLUTETOTAL, TAG_PASSFAIL, TAG_EXAMCONFIRM, TAG_EXTCONFIRM, TAG_DOB}, 
				new int[] { R.id.name, R.id.surname, R.id.sectionatotal, R.id.sectionbtotal,
				R.id.sectionctotal, R.id.total, R.id.passfail, R.id.exam_confirmed, R.id.ext_confirm, R.id.dob });

		setListAdapter(adapter);	
		
		Collections.sort(mParticipantList, new Comparator<HashMap<String, String>>(){
		@Override
		 public int compare(HashMap<String, String> a, HashMap<String, String> b)
		{
		return b.get(TAG_ABSOLUTETOTAL).compareTo(a.get(TAG_ABSOLUTETOTAL));
		}
		});
	}

	public class LoadParticipants extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TabulatedResults.this);
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
	
	//Same as Load Participants
	public class RefreshParticipants extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TabulatedResults.this);
			pDialog.setMessage("Refreshing Participants...");
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
			refreshList();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btnSort:
			new RefreshParticipants().execute();
			break;
		}
	}
}
