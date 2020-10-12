package com.moome.leveleditor;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class MapUpload {
	private String title = "Map";
	private String original = "Yes";
	private String username = System.getProperty("user.name");
	private String description = "";
	private String objective = "Collect all the rubies & get to the portal!";
	private String time = "";
	private int difficulty = 3;
	private String map = "";
	
	public static int TO_PROPS = 0;
	public static int TO_URL = 1;
	
	public MapUpload() {}
	
	public MapUpload(String title) {
		this.title = title;
	}
	
	public String compile(final int mode) {
		String compiled = "";
		
		if(mode == 0) {
			compiled = compiled + "title: " + this.title;
			compiled = compiled + "\noriginal: " + this.original;
			compiled = compiled + "\nusername: " + this.username;
			compiled = compiled + "\ndescription: " + this.description.replaceAll("\n", "\\n");
			compiled = compiled + "\nobjective: " + this.objective.replaceAll("\n", "\\n");
			compiled = compiled + "\ntime: " + this.time;
			compiled = compiled + "\ndifficulty: " + this.difficulty;
			compiled = compiled + "\nmap: " + this.map;
		} else if(mode == 1) {
			compiled = "https://docs.google.com/forms/d/10qqM6s-DYvi-ZU6d1wBiiiJJeVs3bEkfpF6GqRyTmec/viewform?entry.297311670=" + fix(original) + "&entry.1408680079=" + fix(this.getUsername()) +"&entry.1355303411=" + fix(this.getTitle()) + "&entry.793986478=" + fix(this.getDescription()) +"&entry.1582980730=" + fix(this.getObjective()) +"&entry.70383966=" + fix(this.getTime()) +"&entry.1165437281=" + fix(this.getDifficulty() + "") +"&entry.1575373498=" + fix(this.getMap()) +"";
		}
		
		return compiled;
	}
	
	public void launch(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public String fix(String var) {
		try {
			if(!(var.length() == 0))
				return URLEncoder.encode(var, "UTF-8");
			else
				return "%20";
		} catch (UnsupportedEncodingException e) {
			return "err";
		}
	}
	
	public void setTitle(final String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setOriginal(final boolean original) {
		if(original)
			this.original = "Yes";
		else
			this.original = "No";
	}
	
	public boolean isOriginal() {
		if(this.original.equalsIgnoreCase("Yes"))
			return true;
		else
			return false;
	}
	
	public void setUsername(final String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setObjective(final String objective) {
		this.objective = objective;
	}
	
	public String getObjective() {
		return this.objective;
	}
	
	public void setTime(final String time) {
		this.time = time;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public void setDifficulty(final int difficulty) {
		if((difficulty <= 10) && (difficulty >= 1))
			this.difficulty = difficulty;
		else
			throw new NumberFormatException();
	}
	
	public int getDifficulty() {
		return this.difficulty;
	}
	
	public void setMap(final String map) {
		this.map = map;
	}
	
	public String getMap() {
		return this.map;
	}
}
