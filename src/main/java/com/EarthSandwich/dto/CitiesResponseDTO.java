package com.EarthSandwich.dto;

public class CitiesResponseDTO {
	private String country;
	private String city;
	private int latitude;
	private int longitude;
	private double distance;

	public CitiesResponseDTO(String country, String city, int latitude, int longitude, double distance) {

		this.country = country;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
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

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

}
