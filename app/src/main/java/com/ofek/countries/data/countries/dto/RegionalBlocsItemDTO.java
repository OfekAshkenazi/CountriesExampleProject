package com.ofek.countries.data.countries.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionalBlocsItemDTO {

	@SerializedName("otherNames")
	private List<String> otherNames;

	@SerializedName("acronym")
	private String acronym;

	@SerializedName("name")
	private String name;

	@SerializedName("otherAcronyms")
	private List<Object> otherAcronyms;

	public void setOtherNames(List<String> otherNames){
		this.otherNames = otherNames;
	}

	public List<String> getOtherNames(){
		return otherNames;
	}

	public void setAcronym(String acronym){
		this.acronym = acronym;
	}

	public String getAcronym(){
		return acronym;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOtherAcronyms(List<Object> otherAcronyms){
		this.otherAcronyms = otherAcronyms;
	}

	public List<Object> getOtherAcronyms(){
		return otherAcronyms;
	}

	@Override
 	public String toString(){
		return 
			"RegionalBlocsItemDTO{" +
			"otherNames = '" + otherNames + '\'' + 
			",acronym = '" + acronym + '\'' + 
			",name = '" + name + '\'' + 
			",otherAcronyms = '" + otherAcronyms + '\'' + 
			"}";
		}
}