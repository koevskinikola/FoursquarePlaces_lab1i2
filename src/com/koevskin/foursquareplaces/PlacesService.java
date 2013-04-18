package com.koevskin.foursquareplaces;

import com.koevskin.foursquareplaces.GetThePlaces;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PlacesService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		GetThePlaces gp = new GetThePlaces(this);
		gp.execute();

		return super.onStartCommand(intent, flags, startId);
	}

}
