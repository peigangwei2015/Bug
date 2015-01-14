package com.google.core.domain;

public class Location {
	private long date;
	private float direction;
	private int id;
	private double latitude;
	private double longitude;
	private String networkLocationType;
	private String owner;
	private float radius;
	public Location() {
		super();
	}
	public long getDate() {
		return date;
	}
	public float getDirection() {
		return direction;
	}
	public int getId() {
		return id;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public String getNetworkLocationType() {
		return networkLocationType;
	}
	public String getOwner() {
		return owner;
	}
	public float getRadius() {
		return radius;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public void setDirection(float direction) {
		this.direction = direction;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setNetworkLocationType(String networkLocationType) {
		this.networkLocationType = networkLocationType;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	@Override
	public String toString() {
		return "Location [id=" + id + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", date=" + date + ", owner="
				+ owner + ", direction=" + direction + ", radius=" + radius
				+ ", networkLocationType=" + networkLocationType + "]";
	}
	
}
