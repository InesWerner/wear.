package com.example.bo;

import java.io.Serializable;
import java.math.BigInteger;

public class Item implements Serializable {

	private int itemId;
	private String name;
	private String category;
	private String description;
	private int numberOfUsage;
	private int status;
	private String size;
	private String color;
	private Long tagID;
	private String brand;
	private boolean createStatus;


	
	public Item() {
	}

	public Item(String name, String category, String description, int numberOfUsage, int status, String size, String color) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.size = size;
		this.color = color;
	}



	public Item(int itemId, String name, String category, String description, int numberOfUsage, int status,
			String size, String color) {
		this.itemId = itemId;
		this.name = name;
		this.category = category;
		this.description = description;
		this.numberOfUsage = numberOfUsage;
		this.status = status;
		this.size = size;
		this.color = color;
	}

	public int getId() {
		return this.itemId;
	}

	public void setId(int id) {
		this.itemId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfUsage() {
		return numberOfUsage;
	}

	public void setNumberOfUsage(int number_of_usage) {
		this.numberOfUsage = number_of_usage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String toString(){
		return  "ItemID: " + this.itemId + "  Name: " +  this.name
		+ " Category: " + this.category + " Description: "
		+ this.description + " NumberOfUsage: " + this.numberOfUsage + " Status"
		+ this.status + " Size: " + this.size + " Color: " + this.color  + " tagID: " + this.tagID;
	}


	public Long getTagID() {
		return tagID;
	}

	public void setTagID(Long tagID) {
		this.tagID = tagID;
	}

	public boolean isCreateStatus() {
		return createStatus;
	}

	public void setCreateStatus(boolean createStatus) {
		this.createStatus = createStatus;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
}