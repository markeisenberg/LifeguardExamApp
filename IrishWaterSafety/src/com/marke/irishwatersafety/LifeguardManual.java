package com.marke.irishwatersafety;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

public class LifeguardManual extends Activity implements OnClickListener, OnItemSelectedListener{
	
	Spinner topicPicker;
	String[] titles = {"--- Choose a topic ---", "Basic Life Support", "Rescue Stroke", "Approaching",
			"Front Release Methods" , "Levelling", "Carrying Techniques",
			"Back Release Methods", "Spinal Injury Management - Splint", 
			"Spinal Injury Management - Splint [Video]", "Spinal Injury Management - Clamp", 
			"Pool Lifeguard Examination Animation [Video]"};
	
	ScrollView scrollableContents;
	
	ImageView interlock, bls, recovery, backstroke, approach, frontgrab,
	frontgraba, frontgrabb, frontgrabc, level, chestcarry, headcarry, haircarry,
	backgrab, backgrabrelease, splinta, splintb, splintc, splintd, splinte, splintvideo,
	clampa, clampb, clampc, clampd, animvideo;

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manual_menu, menu);
        return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_disclaimer) {
        	Intent openDisclaimer = new Intent(this, Disclaimer.class);
			startActivity(openDisclaimer);
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lifeguard_manual);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_item, titles);
		
		scrollableContents = (ScrollView)findViewById(R.id.scrollableContents);
		
		topicPicker = (Spinner)findViewById(R.id.spinner2);
		topicPicker.setAdapter(adapter);
		topicPicker.setOnItemSelectedListener(this);
		
		interlock = (ImageView)findViewById(R.id.interlock_image);
		//Picasso.with(this).setIndicatorsEnabled(true);
		Picasso.with(this)
			.load(R.drawable.interlock_fingers)
			.into(interlock);
		bls = (ImageView)findViewById(R.id.bls_image);
		Picasso.with(this)
			.load(R.drawable.bls)
			.fit().into(bls);
		recovery = (ImageView)findViewById(R.id.recovery_position);
		Picasso.with(this)
			.load(R.drawable.recovery_position)
			.resize(320, 320).centerInside().into(recovery);
		backstroke = (ImageView)findViewById(R.id.backstroke);
		Picasso.with(this)
			.load(R.drawable.backstroke)
			.resize(320, 320).centerInside().into(backstroke);
		approach = (ImageView)findViewById(R.id.approach);
		Picasso.with(this)
		.load(R.drawable.approachreverse)
		.into(approach);
		frontgrab = (ImageView)findViewById(R.id.frontgrab);
		Picasso.with(this)
		.load(R.drawable.front_grab)
		.resize(320, 320).centerInside().into(frontgrab);
		frontgraba = (ImageView)findViewById(R.id.frontgraba);
		Picasso.with(this)
		.load(R.drawable.front_grab_a)
		.resize(320, 320).centerInside().into(frontgraba);
		frontgrabb = (ImageView)findViewById(R.id.frontgrabb);
		Picasso.with(this)
		.load(R.drawable.front_grab_b)
		.resize(320, 320).centerInside().into(frontgrabb);
		frontgrabc = (ImageView)findViewById(R.id.frontgrabc);
		Picasso.with(this)
		.load(R.drawable.front_grab_c)
		.resize(320, 320).centerInside().into(frontgrabc);
		level = (ImageView)findViewById(R.id.level);
		Picasso.with(this)
		.load(R.drawable.level)
		.resize(320, 320).centerInside().into(level);
		chestcarry = (ImageView)findViewById(R.id.chestcarry);
		Picasso.with(this)
		.load(R.drawable.chestcarry)
		.resize(320, 320).centerInside().into(chestcarry);
		headcarry = (ImageView)findViewById(R.id.headcarry);
		Picasso.with(this)
		.load(R.drawable.headchin_carry)
		.resize(320, 320).centerInside().into(headcarry);
		haircarry = (ImageView)findViewById(R.id.haircarry);
		Picasso.with(this)
		.load(R.drawable.hair_carry)
		.resize(320, 320).centerInside().into(haircarry);
		backgrab = (ImageView)findViewById(R.id.backgrab);
		Picasso.with(this)
		.load(R.drawable.back_grab)
		.resize(320, 320).centerInside().into(backgrab);
		backgrabrelease = (ImageView)findViewById(R.id.backgrabrelease);
		Picasso.with(this)
		.load(R.drawable.back_grab_release)
		.resize(320, 320).centerInside().into(backgrabrelease);
		splinta = (ImageView)findViewById(R.id.splinta);
		Picasso.with(this)
		.load(R.drawable.splint_a)
		.resize(320, 320).centerInside().into(splinta);
		splintb = (ImageView)findViewById(R.id.splintb);
		Picasso.with(this)
		.load(R.drawable.splint_b)
		.resize(320, 320).centerInside().into(splintb);
		splintc = (ImageView)findViewById(R.id.splintc);
		Picasso.with(this)
		.load(R.drawable.splint_c)
		.resize(320, 320).centerInside().into(splintc);
		splintd = (ImageView)findViewById(R.id.splintd);
		Picasso.with(this)
		.load(R.drawable.splint_d)
		.resize(320, 320).centerInside().into(splintd);
		splinte = (ImageView)findViewById(R.id.splinte);
		Picasso.with(this)
		.load(R.drawable.splint_e)
		.resize(320, 320).centerInside().into(splinte);
		splintvideo = (ImageView)findViewById(R.id.splintvideo);
		Picasso.with(this)
		.load(R.drawable.splint_thumb)
		.resize(480, 320).centerInside().into(splintvideo);
		splintvideo.setOnClickListener(this);
		clampa = (ImageView)findViewById(R.id.clampa);
		Picasso.with(this)
		.load(R.drawable.head_clamp_a)
		.resize(320, 320).centerInside().into(clampa);
		clampb = (ImageView)findViewById(R.id.clampb);
		Picasso.with(this)
		.load(R.drawable.head_clamp_b)
		.resize(320, 320).centerInside().into(clampb);
		clampc = (ImageView)findViewById(R.id.clampc);
		Picasso.with(this)
		.load(R.drawable.head_clamp_c)
		.resize(320, 320).centerInside().into(clampc);
		clampd = (ImageView)findViewById(R.id.clampd);
		Picasso.with(this)
		.load(R.drawable.head_clamp_d)
		.resize(320, 320).centerInside().into(clampd);
		animvideo = (ImageView)findViewById(R.id.examvideo);
		Picasso.with(this)
		.load(R.drawable.anim_thumb)
		.resize(480, 320).centerInside().into(animvideo);
		animvideo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.splintvideo:
			startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v=06M1VDV8wak?t=34s")));
		    Log.i("Video", "Video Playing....");
			break;
		case R.id.examvideo:
			startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v=5444yo9jHrY")));
		    Log.i("Video", "Video Playing....");
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		int position = topicPicker.getSelectedItemPosition();
		switch (position) {
		case 0:
			break;
		case 1:
			scrollableContents.smoothScrollTo(0, interlock.getTop());
			break;
		case 2:
			scrollableContents.smoothScrollTo(0, backstroke.getTop());
			break;
		case 3:
			scrollableContents.smoothScrollTo(0, approach.getTop());
			break;
		case 4:
			scrollableContents.smoothScrollTo(0, frontgrab.getTop());
			break;
		case 5:
			scrollableContents.smoothScrollTo(0, level.getTop());
			break;
		case 6:
			scrollableContents.smoothScrollTo(0, chestcarry.getTop());
			break;
		case 7:
			scrollableContents.smoothScrollTo(0, backgrab.getTop());
			break;
		case 8:
			scrollableContents.smoothScrollTo(0, splinta.getTop());
			break;
		case 9:
			scrollableContents.smoothScrollTo(0, splintvideo.getTop());
			break;
		case 10:
			scrollableContents.smoothScrollTo(0, clampa.getTop());
			break;
		case 11:
			scrollableContents.smoothScrollTo(0, animvideo.getTop());
			break;

		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}


