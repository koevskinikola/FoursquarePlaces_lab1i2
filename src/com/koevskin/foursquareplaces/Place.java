package com.koevskin.foursquareplaces;

public class Place {
	  private long id;
	  private String place;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getPlace() {
		  return place;
	  }
	  
	  public void setPlace(String place) {
		this.place = place;
	  }
	  
	  @Override
	  public String toString() {
	    return this.place;
	  }
	} 