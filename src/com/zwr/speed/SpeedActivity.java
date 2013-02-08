package com.zwr.speed;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

// For this application to work, following must exist in the Manifest:
//  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
//
// Also, this code requires a layout with id=activity_speed and a
// TextView in it with id=speed_text.
//
// You can see the full project at github.com:zmilojko/speed.

public class SpeedActivity extends Activity implements LocationListener {

	private TextView speedText;
	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speed);

		speedText = (TextView)findViewById(R.id.speed_text);
		speedText.setText("...");

		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}

	// This is starting the GPS monitoring. Notice the second and third parameter.
	// Setting higher value might take less processing time. I want results here
	// as fast as possible.
	@Override protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0,this);
	}

	// GPS will drain your battery. This is a good place to stop
	// monitoring it. You can also make it work some other way.
	@Override protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_speed, menu);
		return true;
	}

	// The following is the main callback. Process received GPS info as you wish.
	// We will just show the speed (in km/h, the received data is in m/s).
	@Override
	public void onLocationChanged(Location newLoc) {
		speedText.setText(String.format("%.0f", newLoc.getSpeed() * 3.6));		
	}

	@Override
	public void onProviderDisabled(String arg0) {}

	@Override
	public void onProviderEnabled(String arg0) {}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
}