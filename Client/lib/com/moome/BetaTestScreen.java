package com.moome;

import java.awt.Desktop;
import java.net.URL;

public class BetaTestScreen extends MenuScreen {
    public BetaTestScreen() {
        this.elements.add(new Button("Sign Up", (MoomePane.HEIGHT * 10 + 15) - 30, 90));
        this.elements.add(new Button("Report Bug", (MoomePane.HEIGHT * 10 + 15) - 17, 90));
        this.elements.add(new Button("Submit Map", (MoomePane.HEIGHT * 10 + 15) - 4, 90));
        this.elements.add(new Button("Suggestions", (MoomePane.HEIGHT * 10 + 15) + 9, 90));
        this.elements.add(new Button("Site", (MoomePane.HEIGHT * 10 + 15) + 22, 90));
        this.elements.add(new Button("Back", (MoomePane.HEIGHT * 10 + 15) + 35, 90));
    }
    
    public void buttonPress() {
        open(selected);
        switch(selected) {
            case 5:
                ScreenManager.setScreen(1);
            default: break;
        }
    }
    

    
    public static final int SIGNUP_SHEET = 0;
    public static final int REPORT_BUG_SHEET = 1;
    public static final int SUBMIT_MAP_SHEET = 2;
    public static final int SUGGESTIONS_SHEET = 3;
    public static final int MOOME_SITE = 4;
    
    public static void open(int args) {
        if(args == SIGNUP_SHEET) {
            open("https://docs.google.com/forms/d/1nZcPwJC-BeFF2oW4Hx5E40bZJ9hzvvDmrPjPsach5-c/viewform?usp=send_form");
        } else if(args == REPORT_BUG_SHEET) {
            open("https://docs.google.com/forms/d/1bzL8gLsCpWX2YEmfb3C7eD3wPqNCKF7RUgE9VbuVibQ/viewform?usp=send_form");
        } else if(args == SUBMIT_MAP_SHEET) {
            open("https://docs.google.com/forms/d/10qqM6s-DYvi-ZU6d1wBiiiJJeVs3bEkfpF6GqRyTmec/viewform?usp=send_form");
        } else if(args == SUGGESTIONS_SHEET) {
            open("https://docs.google.com/forms/d/1a9LPCSLgIlUUa3WGBJzIUe6p0NL5_3f3y9sMzmxHhMU/viewform?usp=send_form");
        } else if(args == MOOME_SITE) {
            open("http://www.moome.org");
        }
    }
    
    private static void open(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            System.err.println("Error: cannot open browser");
        }
    }
}
