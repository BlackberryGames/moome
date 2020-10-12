package com.moome;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static final int DEFAULT_PORT = 29798;


/**/
    // IGNORE THIS FILE, THIS IS FOR ARIN'S TESTING ONLY!
/**/




























	public static void main(String[] args) {
        try {
			Socket socket = new Socket("localhost", DEFAULT_PORT);
			/*Timer t = new Timer();
			t.schedule(new TimerTask(){
				public void run() {
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, 5000);*/
			BufferedReader in = new BufferedReader(
	                new InputStreamReader(socket.getInputStream()));
	        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	        out.println("3/");
	        /*out.println("0/name=Arinerron");*/
	        out.flush();
	        String token = "";
	        while(true) {
	        	System.out.println("Reading...");
	        	String ss = in.readLine();
	        	System.out.println("Return " + ss);
	        	
	    		final int index = ss.indexOf('/');
	    		final String type = ss.substring(0, index); // ADD - 1 IF BUG!
	    		
	        	System.out.println(ss + "\n");
	        	final List<String> keys = new ArrayList<>();
	    		final HashMap<String, String> pairZ = new HashMap<String, String>();
	    		final String dataSplit = ss.substring(index + 1);
	    		final String[] pairs = dataSplit.split("#l");
	    		for (final String pair : pairs) {
	    			try {
	    				String[] split = pair.split("=");
	    				keys.add(split[0].toLowerCase());
	    				pairZ.put(split[0].toLowerCase(), split[1]);
	    			} catch (final Exception e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		
	    		if(type.equals("3")) {
	    			token = pairZ.get("token");
	    			System.out.println("TOKEN FOUND:" + token);
	    			out.println("2/name=Arinerron#lx=4#ly=9#ltoken=" + token);
	    			out.flush();
	    			out.println("0/token=" + token);
	    			out.flush();
	    		}
	        }
	        
			/*
			 * type documentation: 
			 * 0 = updated data request 
			 * 1 = disconnect request 
			 * 2 = new data request 
			 * 3 = token request 
			 * 4 = error
			 * 5 = motd
			 * 
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
