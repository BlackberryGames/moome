package com.moome;
public class MoomeServerUser {
	public String name = "undefined";
	public String world = "";
	public String x = "0"; // You can do your parseInt later, but it might be a long, so be careful.
	public String y = "0";
	public String visible = "true"; // Again, later you can parse that.
	public String looks = "0"; // for later, 0 is moome, 1 is pigacorn? etc.
	public String csrf = "undefined";;
	
	// Why are they all Strings? It's complicated. Basically, it's laggier if you don't. Better explanation later, k?
}
