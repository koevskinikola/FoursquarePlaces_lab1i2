package com.koevskin.foursquareplaces;

import java.util.ArrayList;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

	ListView lvPlaces;
	
	String[] thePlaces;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        lvPlaces = (ListView) findViewById(R.id.lvPlaces);
        
        GetThePlaces mesta = new GetThePlaces();
        mesta.execute();
        
    }

    private class GetThePlaces extends AsyncTask<Void, Void, Void> {

    	ArrayList<String> resultPlaces = new ArrayList<String>();
    	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {

			LocationManager mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
			Criteria criteria = new Criteria();

	        String best = mgr.getBestProvider(criteria, true);
			Location myLocation = mgr.getLastKnownLocation(best);
			
			String url = "https://api.foursquare.com/v2/venues/search?ll="+myLocation.getLatitude()+","+myLocation.getLongitude()+"&radius=250&oauth_token=EIRAL4CH3IFGKYZQVCO5HDIW5JNB3YJSLNDHX4II2HCAWTMT&v=20130324";
	    	
	    	ParseJSON jsonParse = new ParseJSON();
	    	
	    	JSONObject json = jsonParse.getJSONFromUrl(url);
	    	
	    	try{
	    		JSONObject response = json.getJSONObject("response");
	    		JSONArray venues = response.getJSONArray("venues");
	    		
	    		for (int i=0; i<venues.length(); i++){
	    			JSONObject place = venues.getJSONObject(i);
	    			resultPlaces.add(place.getString("name"));
	    		}
	    	}catch (JSONException e) {
	    	    e.printStackTrace();
	    	}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			setPlaces(resultPlaces);
		}
	}

    public void setPlaces(ArrayList<String> places){
    	
    	ListViewAdapter customAdapter =      
		         new ListViewAdapter(this, places);
		
       this.lvPlaces.setAdapter(customAdapter);
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
