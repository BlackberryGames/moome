package com.moome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Preferences {
    private String fileContents = "";
    private File PROPERTIES = new File("Preferences");

    public void setPropertiesFile(File f) {
        PROPERTIES = f;
        fileContents = "";
    }

    public File getPropertiesFile() {
        return PROPERTIES;
    }

    public String getRawProperties() {
        return fileContents;
    }

    public boolean set(String property, String value) {
        if (!PROPERTIES.exists()) {
            try {
                PROPERTIES.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        boolean found = false;
        String file = readFile(PROPERTIES);
        String[] contents = file.split("\n");
        int i = 0;
        for (String type : contents) {
            String set = type;
            final String[] split = type.split(":");
            final String prop = split[0];
            final String val = split[1];
            if (prop.equalsIgnoreCase(property)) {
                found = true;
                set = property.toUpperCase() + ":" + value;
            }
            contents[i] = set;
            i++;
        }

        writeFile(PROPERTIES, contents);
        String sofar = "";
        for (String c : contents) {
            sofar = sofar + c + "\n";
        }
        if (sofar.endsWith("\n")) {
            sofar = removeLast(sofar, '\n');
        }
        file = sofar;
        return found;
    }

    public String removeLast(String str, char c) {
        if (str.length() > 0 && str.charAt(str.length() - 1) == c) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public void writeFile(File f, String[] file) { // overwrites existing
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(f, "UTF-8");
            for (String w : file) {
                writer.println(w);
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }

    public String readFile(File path) {
        if (fileContents.equalsIgnoreCase("")) {
            try {
                byte[] encoded = Files.readAllBytes(Paths.get(path.getAbsolutePath()));
                fileContents = new String(encoded, StandardCharsets.UTF_8);
                return fileContents;
            } catch (IOException ex) {
                return "";
            }
        } else {
            return fileContents;
        }
    }

    public String get(String property) {
        if (!PROPERTIES.exists()) {
            try {
                PROPERTIES.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String res = "";
        String file = readFile(PROPERTIES);
        String[] contents = file.split("\n");
        for (String type : contents) {
            final String[] split = type.split(":");
            final String prop = split[0];
            final String val = split[1].replaceAll("\\\\n", "\n");
            if (prop.equalsIgnoreCase(property)) {
                return val;
            }
        }
        return res;
    }
    
    public String getString(String property) {
        return get(property);
    }
    
    public void setString(String key, String property) {
        set(key, property);
    }
    
    public int getInt(String property) {
        return Integer.parseInt(get(property));
    }
    
    public void setInt(String key, int property) {
        set(key, property + "");
    }
    
    public boolean getBoolean(String property) {
        return Boolean.parseBoolean(get(property));
    }
    
    public void setBoolean(String key, boolean property) {
        set(key, property + "");
    }
    
    public char getChar(String property) {
        return get(property).charAt(0);
    }
    
    public void setChar(String key, char property) {
        set(key, property + "");
    }
    
    public float getFloat(String property) {
        return Float.parseFloat(get(property)) - 0.001f;
    }
    
    public void setFloat(String key, float property) {
        set(key, property + "");
    }
    
    public double getDouble(String property) {
        return Double.parseDouble(get(property));
    }
    
    public void setDouble(String key, double property) {
        set(key, property + "");
    }
}
