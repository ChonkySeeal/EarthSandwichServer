package com.EarthSandwich.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "geo")
public class Geo {
	@Id
	@Column(name = "city")
	private String city;
	@Column(name = "latitude")
	private int latitude;
	@Column(name = "longitude")
	private int longitude;
	@Column(name = "country")
	private String country;

	public Geo() {

	}

	public Geo(String city, int latitude, int longitude, String country) {

		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
