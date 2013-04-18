package com.koevskin.foursquareplaces;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

public class GetThePlaces extends AsyncTask<Void, Void, Void> {
   	
	Context context;
   	public PlacesDataSource datasource;
	public GetThePlaces(Context c)
	{
		context = c;
		datasource = new PlacesDataSource(context);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		
		datasource.open();
		
		LocationManager mgr = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
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
    			datasource.createPlace(place.getString("name"));
    			System.out.println(place.getString("name"));
    		}
    	}catch (JSONException e) {
    	    e.printStackTrace();
    	}
		
    	System.out.println("ZEMAV MESTA");
    	
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		System.out.println("Stavam PREFS");
		DateFormat data = new SimpleDateFormat("yyyy/MM/dd");
		SharedPreferences sprefs = context.getSharedPreferences("com.koevskin.foursquareplaces", context.MODE_PRIVATE);
		String currDate = data.format(new Date());
		sprefs.edit().putString("datum", currDate).commit();

		System.out.println("PRAKAM INTENT");

		Intent intent = new Intent("gotPlacesEvent");
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
		
	}
}