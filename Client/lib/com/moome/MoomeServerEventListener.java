package com.moome;
public interface MoomeServerEventListener {
	public void onDisconnect(String reason); // 0 = kick, 1 = normal, 2 = shutdown;
	public void onConnect();
}

/*
 * [===== MOOME SERVER CLIENT LIBRARY DOCUMENTATION =====]
 * 
 * [ Define a new server and connect ]
 * MoomeServerConnection server = new MoomeServerConnection("theIPHere", MoomeServerConnection.DEFAULT_PORT, "yourUsername");
 * server.connect();
 * 
 * [ Get list of user data for all of the users on the server ]
 * List<MoomeServerUser> data = server.usrdata;
 * 
 * [ Update the user's coords ]
 * server.updateCoords(yourX, yourY);
 * 
 * [ Update the user data ]
 * server.update(); // Updates everybody's coordinates, should run quite often.
 * 
 * [ Get server version ]
 * String version = server.version; // DO NOT compare client and server versions with this, use the function below.
 * 
 * [ Compare server version to client version ]
 * server.versionCompatable("1.2.0"); // returns boolean, whether or not the client should connect
 * 
 * [ Set server event listener ]
 * server.setListener(new MoomeServerEventListener() { ... implement functions here ...});
 * 
 * [ Get server event listener ]
 * MoomeServerEventListener listener = server.getListener();
 * 
 * [ Send a custom request :: Development only! ]
 * server.build(type, "params=example" + MoomeServerConnection.MINOR_SEP + "example=true"); // Shouldn't need to use this ever, just if you want to build a custom request, but MAKE SURE TO STRIP THE CHARACTERS... 
 * 
 * [ Strip characters from String :: Development only! ]
 * String striped = MoomeServerConnection.escape(request);
 * 
 * [ Stop server normally :: Use this when exiting the ]
 * server.running = false;
 * 
 * [ Stop server abnormally :: Don't do unless necessary! ]
 * server.socket.close();
 * 
 */

/*TODO LATER: Server end-remove session on IOException in case of abnormal exit*/