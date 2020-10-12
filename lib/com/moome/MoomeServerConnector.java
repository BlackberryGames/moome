package com.moome;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoomeServerConnector implements Runnable {
    public static final int DEFAULT_PORT = 29798;
    public static final String MINOR_SEP = "#l";
    public static final String MAJOR_SEP = "#v";
    public String ip = "localhost";
    public int port = 29798;
    public String name = System.getProperty("user.name");
    public Socket socket = null;
    public String token = "";
    private BufferedReader in = null;
    private PrintWriter out = null;
    private MoomeServerEventListener listener = new MoomeServerEventListener(){@Override public void onDisconnect(String reason) {}@Override public void onConnect() {}};
    public String csrf = "";
    
    public MoomeServerConnector(String ip, int port, String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;
    }
    
    public void connect() throws MoomeServerException {
        try {
            this.socket = new Socket(this.ip, this.port);
        } catch (IOException e) {
            System.out.println(this.ip + ", " + this.port);
            e.printStackTrace();
            throw new MoomeServerException();
        }
        
        Thread t = new Thread(this);
        t.start();
    }
    
    public void disconnect() {
        this.build(1, "token=" + this.token); // No need to specify reason, the server knows the protocol.
        this.running = false;
        try {
            this.socket.close();
        } catch (IOException e) {
            System.out.println("[ERROR]: IOException, failed to close port.");
            e.printStackTrace();
        }
    }

    public List<MoomeServerUser> usrdata = new ArrayList<>();
    
    public boolean running = false;
    public String version = "";
    
    public boolean versionCompatable(String version) {
        String[] split = version.split("\\.");
        String[] split2 = this.version.split("\\.");
        if(split[0].equals(split2[0]) && split[1].equals(split2[1]))
            return true;
        return false;
    }
    
    @Override
    public void run() {
        this.running = true;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Get token from server
        this.build(3, "");
        
        // Get motd from server
        this.build(5, "");
        
        while(running) {
            String input = "";
            try {
                input = in.readLine();
            } catch(Exception e) {
                System.out.println("[ERROR]: Server closed.");
                running = false;
                try {
                    this.socket.close();
                }  catch (IOException e1) {
                    System.out.println("[ERROR]: IOException, failed to close port.");
                }
                ScreenManager.setScreen(1);
                return;
            }
            
            final int index = input.indexOf('/');
            final String type = input.substring(0, index); // ADD - 1 IF BUG!
            
            final List<String> keys = new ArrayList<>();
            final HashMap<String, String> pairZ = new HashMap<String, String>();
            final String dataSplit = input.substring(index + 1); // The data without the type specs
            final String[] pairs = dataSplit.split("#l");
            for (final String pair : pairs) {
                try {
                    final String[] split = pair.split("=");
                    keys.add(split[0].toLowerCase());
                    pairZ.put(split[0].toLowerCase(), split[1]);
                } catch (final Exception e) {
                    System.out.println(pair);
                }
            }
            
            // logic
            switch(type) {
            case "1":
                this.getListener().onDisconnect(pairZ.get("reason")+ "");
                this.running = false;
                try {
                    this.socket.close();
                } catch (IOException e) {
                    System.out.println("[ERROR]: IOException, failed to close port.");
                    e.printStackTrace();
                }
                break;
            case "3":
                this.token = pairZ.get("token");
                this.csrf = pairZ.get("csrf");
                System.out.println("[TOKEN]: " + token);
                System.out.println("[CSRF]: " + csrf);
                this.getListener().onConnect();
                break;
            case "0":
                final List<MoomeServerUser> list = new ArrayList<>();
                final String[] users = dataSplit.split("#v");
                for(final String user : users) {
                    final String[] data = user.split("#l");
                    final MoomeServerUser u = new MoomeServerUser();
                    System.out.println(input);
                    for(final String s : data) {
                        final String[] ss = s.split("=");
                        switch(ss[0]) {
                        case "name":
                            u.name = ss[1];
                            break;
                        case "x":
                            u.x = ss[1];
                            break;
                        case "y":
                            u.y = ss[1];
                            break;
                        case "world":
                            u.world = ss[1];
                            break;
                        case "visible":
                            u.visible = ss[1];
                            break;
                        case "looks":
                            u.looks = ss[1];
                            break;
                        case "csrf":
                            u.csrf = ss[1];
                            break;
                        }
                    }
                    list.add(u);
                }
                
                this.usrdata = list;
                System.out.println("Length: " + usrdata.size());
                break;
            case "5":
                this.version = pairZ.get("version");
                System.out.println("[MOTD FOUND]: Version is " + pairZ.get("version") + ", port is " + pairZ.get("port"));
                break;
            }
        }
    }
    
    public void updateData() {
        this.build(0, "token=" + this.token);
    }
    
    public void updateCoords(final int x, final int y, final int look) { // TODO: Make escape(name) a constant-- LAG UPDATE!
        this.build(2, "name=" + escape(this.name) + "#llooks=" + look + "#lx=" + x + "#ly=" + y + "#ltoken=" + this.token);
    }

    private void build(final int type, final String args) {
        if(out != null) {
            out.println(type + "/" + args); // MUST stip special characters before running this function with escape(params...)
            out.flush();
        }
    }
    
    public static String escape(final String message) {
        return message.replaceAll("#v", "v").replaceAll("#l", "l").replaceAll("=", "~");
    }

    public MoomeServerEventListener getListener() {
        return this.listener;
    }

    public void setListener(MoomeServerEventListener listener) {
        this.listener = listener;
    }
}
