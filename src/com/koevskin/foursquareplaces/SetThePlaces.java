package com.koevskin.foursquareplaces;

import java.util.ArrayList;

import android.os.AsyncTask;

public class SetThePlaces extends AsyncTask<Void, Void, Void> {
	
	MainActivity context;
   	
	public SetThePlaces(MainActivity c)
	{
		context = c;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		System.out.println("otvoram datasource");
		context.datasource.open();
		
		System.out.println("VADAM MESTA");
		ArrayList<String> allPlaces = context.datasource.getAllPlaces();
		
		System.out.println("PRIKAZUVAM MESTA");
		context.setPlaces(allPlaces);
	}
	
}