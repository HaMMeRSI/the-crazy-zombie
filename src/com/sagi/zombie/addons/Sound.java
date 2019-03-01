package com.sagi.zombie.addons;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Sound{    
    public static void playSfx(final String path) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));

                    final int BUFFER_SIZE = 128000;
                    SourceDataLine sourceLine = null;

                    AudioFormat audioFormat = audioInputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

                    sourceLine = (SourceDataLine) AudioSystem.getLine(info);
                    sourceLine.open(audioFormat);

                    sourceLine.start();
                    int nBytesRead = 0;
                    byte[] abData = new byte[BUFFER_SIZE];
                    while (nBytesRead != -1) {
                        try {
                            nBytesRead = audioInputStream.read(abData, 0, abData.length);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (nBytesRead >= 0) {
                            sourceLine.write(abData, 0, nBytesRead);
                        }
                    }

                    sourceLine.drain();
                    sourceLine.close();
                    audioInputStream.close();

                    sourceLine = null;
                    abData = null;
                    audioFormat = null;
                    info = null;
                    audioInputStream = null;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }).start();
    }
}