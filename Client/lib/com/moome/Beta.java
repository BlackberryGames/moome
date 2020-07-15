package com.moome;

import java.awt.Desktop;
import java.net.URL;

public class Beta {
	
	/*
	 * EXAMPLES:
	 * Beta.open(Beta.SIGNUP_SHEET);
	 * Beta.open(Beta.REPORT_BUG_SHEET);
	 * Beta.open(Beta.SUBMIT_MAP_SHEET);
	 * Beta.open(Beta.SUGGESTIONS_SHEET);
	 */
	
	public static final int SIGNUP_SHEET = 0;
	public static final int REPORT_BUG_SHEET = 1;
	public static final int SUBMIT_MAP_SHEET = 2;
	public static final int SUGGESTIONS_SHEET = 3;
	public static final int MOOME_SITE = 5;
	
	public static void open(int args) {
		if(args == SIGNUP_SHEET)
			open("https://docs.google.com/forms/d/1nZcPwJC-BeFF2oW4Hx5E40bZJ9hzvvDmrPjPsach5-c/viewform?usp=send_form");
		else if(args == REPORT_BUG_SHEET)
			open("https://docs.google.com/forms/d/1bzL8gLsCpWX2YEmfb3C7eD3wPqNCKF7RUgE9VbuVibQ/viewform?usp=send_form");
		else if(args == SUBMIT_MAP_SHEET)
			open("https://docs.google.com/forms/d/10qqM6s-DYvi-ZU6d1wBiiiJJeVs3bEkfpF6GqRyTmec/viewform?usp=send_form");
		else if(args == SUGGESTIONS_SHEET)
			open("https://docs.google.com/forms/d/1a9LPCSLgIlUUa3WGBJzIUe6p0NL5_3f3y9sMzmxHhMU/viewform?usp=send_form");
		else if(args == MOOME_SITE)
			open("http://www.moome.org");
	}
	
	private static void open(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        System.err.println("[ERR] No browser support!");
	    }
	}
}
