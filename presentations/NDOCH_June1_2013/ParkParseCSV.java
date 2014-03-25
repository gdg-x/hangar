package com.maps.mapstest;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.util.Log;


/***
 * 
 * @author Javier C
 * @email javier@javierachavez.com
 * 
 * Made for Google Code Lab 2013 (GDG) Free to use, Free to modify.
 *
 */

public class ParkParseCSV {
	
	public class DogPark{
		private String _name;
		private String _address;
		private String _notes;
		private String _website;
		private double[] _ll;		//cant decide if I should use Double[] or LatLng(requires google play services lib)
		private String _hours;
		private boolean _isWater;
		
		
		public String get_name() {
			return _name;
		}


		public DogPark set_name(String _name) {
			this._name = _name;
			return this;
		}


		public String get_address() {
			return _address;
		}


		public DogPark set_address(String _address) {
			this._address = _address;
			return this;
		}


		public String get_notes() {
			return _notes;
		}


		public DogPark set_notes(String _notes) {
			this._notes = _notes;
			return this;
		}


		public String get_website() {
			return _website;
		}


		public DogPark set_website(String _website) {
			this._website = _website;
			return this;
		}


		public String get_hours() {
			return _hours;
		}


		public DogPark set_hours(String _hours) {
			this._hours = _hours;
			return this;
		}


		public double[] get_ll() {
			return _ll;
		}


		public DogPark set_ll(double[] _ll) {
			this._ll = _ll;
			return this;
		}


		public boolean get_isWater() {
			return _isWater;
		}


		public DogPark set_isWater(boolean _isWater) {
			this._isWater = _isWater;
			return this;
		}


		@Override
		public String toString() {
			return get_name() + "\n" + get_address() + "\n" + get_website(); 
		}
	}
	
	
	/**
	 * Parse ABQ city data - Dog parks is currently offered as a .csv. This class simplifies adding dog park data to a GoogleMap.
	 * @param url
	 * @return List of DogPark objects 
	 */
	public List<DogPark> parseFromUrl(URL url){
		Scanner in = null;
		List<DogPark> data = new ArrayList<DogPark>();
		

		try {
			URLConnection connection = url.openConnection();
	        connection.connect();
			in = new Scanner(url.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(in == null){
			return data;
		}
		while(in.hasNext()){
			String  line;
			String[] row;
			double[] ll = new double[2];
			line  = in.nextLine();

			row = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			
			try {
				double lng = Double.parseDouble(row[5]);
				double lat = Double.parseDouble(row[6]);
				//this order is same order as LatLng object;
				ll[0] = lat;
				ll[1] = lng;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				ll[0] = 0.0;
				ll[1] = 0.0;
			}
			data.add(new DogPark()
					.set_name(row[1])
					.set_address(row[2])
					.set_notes(row[3])
					.set_website(row[4])
					.set_ll(ll)
					.set_hours(row[7])
					.set_isWater(row[8].toLowerCase() == "yes")
					);
			
		}
		
		//quick fix!!
		data.remove(0);
		Log.i("Removing", "Removed first element");
		
		return data;
		
	}

}