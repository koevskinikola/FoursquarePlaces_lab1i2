package com.koevskin.foursquareplaces;

import java.util.ArrayList;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<String> {
	  @SuppressWarnings("unused")
	private final Context context;
	  private final ArrayList<String> places;

	  public ListViewAdapter(Context context, ArrayList<String> places) {
	    super(context, android.R.layout.simple_list_item_1, places);
	    this.context = context;
	    this.places = places;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    TextView txtView = (TextView) super.getView(position, convertView, parent);
	    txtView.setText(places.get(position));


	    return txtView;
	  }
}
