package com.marke.irishwatersafety;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
 
public class AddParticipant extends Activity {
 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParserEdit jsonParserEdit = new JSONParserEdit();
    EditText inputName;
    EditText inputSurname;
    EditText inputDOB;
 
    // url to create new product
    private static String url_create_candidate = "http://markeisenberg.org/lifeguard-exam/webservice/create_participant.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_participant);
 
        // Edit Text
        inputName = (EditText) findViewById(R.id.inputName);
        inputSurname = (EditText) findViewById(R.id.inputSurname);
        inputDOB = (EditText) findViewById(R.id.inputDOB);
 
        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateUser);
        
        //Open keyboard automatically
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
 
        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // creating new user in background thread
                new CreateNewCandidate().execute();
            }
        });
    }
 
    /**
     * Background Async Task to Create new candidate
     * */
    class CreateNewCandidate extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddParticipant.this);
            pDialog.setMessage("Creating candidate..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating candidate
         * */
        protected String doInBackground(String... args) {
            String name = inputName.getText().toString();
            String surname = inputSurname.getText().toString();
            String dob = inputDOB.getText().toString();
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("surname", surname));
            params.add(new BasicNameValuePair("dob", dob));
 
            // getting JSON Object
            // Note that create candidate url accepts POST method
            JSONObject json = jsonParserEdit.makeHttpRequest(url_create_candidate,
                    "POST", params);
 
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created candidate
 
                    // closing this screen
                    finish();
                } else {
                    // failed to create candidate
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
            // dismiss the dialog once done
            pDialog.dismiss();
        }
 
    }
}