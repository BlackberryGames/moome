package com.moome;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.Vector;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;

public class SoundManager {
    private Line.Info lineInfo;
    
    private Vector afs;
    private Vector sizes;
    private Vector infos;
    private Vector audios;
    
    private Vector MAfs;
    private Vector MSizes;
    private Vector MInfos;
    private Vector MAudios;
    private int num = 0;
    
    Clip currentMusic;
    int currentMusicId = -1;
    ArrayList<Integer> sounds = new ArrayList<>();
    
    public static SoundManager sound = new SoundManager();
    
    private SoundManager() {
        afs = new Vector();
        sizes = new Vector();
        infos = new Vector();
        audios = new Vector();
        
        MAfs = new Vector();
        MSizes = new Vector();
        MInfos = new Vector();
        MAudios = new Vector();
    }
    
    public void addMusic(String s) {
        try {
            URL url = new File(s).toURI().toURL();
            InputStream inputStream = url.openStream();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(loadStream(inputStream));
            AudioFormat af = audioInputStream.getFormat();
            int size = (int)(af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];
            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
            
            MAfs.add(af);
            MSizes.add(new Integer(size));
            MInfos.add(info);
            MAudios.add(audio);

            num++;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addSound(String s) {
        try {
            URL url = new File(s).toURI().toURL();
            InputStream inputStream = url.openStream();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(loadStream(inputStream));
            AudioFormat af = audioInputStream.getFormat();
            int size = (int)(af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio = new byte[size];
            DataLine.Info info = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);

            afs.add(af);
            sizes.add(new Integer(size));
            infos.add(info);
            audios.add(audio);

            num++;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private ByteArrayInputStream loadStream(InputStream inputstream) {
        try {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            for(int i = inputstream.read(data); i != -1; i = inputstream.read(data))
                  bytearrayoutputstream.write(data, 0, i);

            inputstream.close();
            bytearrayoutputstream.close();
            data = bytearrayoutputstream.toByteArray();
            return new ByteArrayInputStream(data);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void playSound(int x) {
        try {
            if(x > num) {
                System.out.println("playSound: sample nr[" + x + "] is not available");
            } else {
                Clip clip = (Clip) AudioSystem.getLine((DataLine.Info)infos.elementAt(x));
                clip.open((AudioFormat)afs.elementAt(x), (byte[])audios.elementAt(x), 0, ((Integer)sizes.elementAt(x)).intValue());
                for(Integer i : sounds) {
                    if(i.intValue() == x) {
                        return;
                    }
                }
                clip.start();
                clip.addLineListener(new LineListener() {
                    public void update(LineEvent e) {
                        if (e.getType() == LineEvent.Type.STOP) {
                            for(Integer i : sounds) {
                                if(i.intValue() == x) {
                                    sounds.remove(i);
                                    break;
                                }
                            }
                            e.getLine().close();
                        }
                    }
                });
                sounds.add(Integer.valueOf(x));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void playMusic(int x) {
        if(currentMusic != null) {
            currentMusic.stop();
            currentMusic = null;
            currentMusicId = -1;
        }
        if(x != -1) {
            try {
                if(x > num) {
                    System.out.println("playMusic: sample nr[" + x + "] is not available");
                } else {
                    Clip clip = (Clip) AudioSystem.getLine((DataLine.Info)MInfos.elementAt(x));
                    clip.open((AudioFormat)MAfs.elementAt(x), (byte[])MAudios.elementAt(x), 0, ((Integer)MSizes.elementAt(x)).intValue());
                    clip.start();
                    clip.addLineListener(new LineListener() {
                        public void update(LineEvent e) {
                            if (e.getType() == LineEvent.Type.STOP) {
                                e.getLine().close();
                            }
                        }
                    });
                    currentMusic = clip;
                    currentMusicId = x;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
