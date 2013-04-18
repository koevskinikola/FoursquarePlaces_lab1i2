package com.koevskin.foursquareplaces;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.widget.ListView;


public class MainActivity extends Activity {

	ListView lvPlaces;
	String[] thePlaces;
	SetThePlaces stp = new SetThePlaces(this);
	public PlacesDataSource datasource;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        lvPlaces = (ListView) findViewById(R.id.lvPlaces);
        datasource = new PlacesDataSource(this);
        
        DateFormat data = new SimpleDateFormat("yyyy/MM/dd");
        String currDate = data.format(new Date());
        
		SharedPreferences sprefs = getSharedPreferences("com.koevskin.foursquareplaces", MODE_PRIVATE);
		String pastDate = sprefs.getString("datum", "n/a");
		
		System.out.println("Pominatiot datum e: " + pastDate);
		System.out.println("Segasniot datum e: " + currDate);
		
		if (pastDate.equals(currDate) )
		{
			System.out.println("BAZATA E OD DENES");
			System.out.println("PRIKAZUVAM MESTA OD BAZA");
			new SetThePlaces(this).execute();		
		}
		else
		{
			System.out.println("BAZATA NE E OD DENES");
			System.out.println("ZEMAM MESTA SO SERVIS");
			Intent service = new Intent(MainActivity.this, PlacesService.class);
			startService(service);
		}
        
    }
    
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
    	  @Override
    	  public void onReceive(Context context, Intent intent) {
    		  stp.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void)null);
    	  }
    	};
    
    @Override
    protected void onResume() {
      datasource.open();
      super.onResume();
      
      LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
    	      new IntentFilter("gotPlacesEvent"));
    }

    @Override
    protected void onPause() {
      datasource.close();
      LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
      super.onPause();
    }

    public void setPlaces(ArrayList<String> places)
    {
       	ListViewAdapter customAdapter = new ListViewAdapter(this, places);
	    this.lvPlaces.setAdapter(customAdapter);	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
