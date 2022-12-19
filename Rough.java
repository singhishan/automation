

import java.io.*;
import java.lang.*;
import java.util.*;


public class Rough {
    public static void main(String[] arg) throws IOException, InterruptedException {

        int n= 1;
        String res = targetResolution();
        int bitRate = 4000000; //Bit Rate during Recording, 4MBpS

        startScreenRecording(n, bitRate, res);
        pullVideos(n);
    }
    public static String targetResolution() throws IOException {

        System.out.println("Getting device Resolution!!");

        Process process = Runtime.getRuntime().exec("adb shell wm size");

        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String resolution;
        resolution = reader.readLine();
        reader.close();

        String k[] = resolution.split(" ");
        String temp = k[2];
        String p[] = temp.split("x");
        int a = Integer.parseInt(p[0]);
        int b = Integer.parseInt(p[1]);
        System.out.println("Initial resolution is: "+a+"x"+b);
        // code to get target resolution
        String targetResolution = null;
        int c = (720*b)/a;
        targetResolution= 720+"x"+c;
        System.out.println("Recording resolution is: "+targetResolution);
        return targetResolution;
    }
    public static void startScreenRecording(int n, int bitRate, String res) throws IOException, InterruptedException {

        int i=1;
        System.out.println(res);
        System.out.println("Recording start!!!\n");
        while(i<=n){
            Process record= Runtime.getRuntime().exec("adb shell " + "screenrecord --time-limit 60 --bit-rate "+bitRate+" --size "+res+" /sdcard/video"+i+".mp4");
            record.waitFor();
            System.out.println("(60 seconds) video"+ i + " recording is done \n");
            i++;
        }
        System.out.println("done recording!!!\n");
    }

    public static void pullVideos(int n) throws IOException, InterruptedException {

        int i=1;

        while(i<=n){
            Process pull = Runtime.getRuntime().exec("adb pull /sdcard/video"+i+".mp4" + "  "+"video"+i+".mp4");
            pull.waitFor();
            Process del = Runtime.getRuntime().exec( "adb shell rm /sdcard/video"+i+".mp4");
            del.waitFor();
            System.out.println("video"+i+ " is pulled into system and is removed from the device\n");
            i++;
        }
    }
}
