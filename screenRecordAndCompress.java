import java.io.*;


public class screenRecordAndCompress {

    public static void main(String[] args) throws IOException, InterruptedException {

        int n= (20*60)/150; //20mins total; 150seconds each record clip
        String path = "fullRecording.mp4";
        String res = targetResolution();
        int bitRate = 4000000; //Bit Rate during Recording, 4MBpS

        String pathForCompressedVideo = "compressedVideo.mp4";
        String targetBitRate = "550k";  //Bit Rate used for Compression
        int targetFPS = 20;

        startScreenRecording(n, bitRate, res);
        pullVideos(n);
        mergeVideos(n, path);
        compressVideo(path, pathForCompressedVideo, targetFPS, targetBitRate);
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
            Process record= Runtime.getRuntime().exec("adb shell " + "screenrecord --time-limit 150 --bit-rate "+bitRate+" --size "+res+" /sdcard/video"+i+".mp4");
            record.waitFor();
            System.out.println("(150 seconds) video"+ i + " recording is done \n");
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

    public static void mergeVideos(int n, String path) throws IOException, InterruptedException {

        //merging all n videos
        int i=1;
        File file = new File( "videoClipsList.txt");
        FileWriter writer = new FileWriter(file);
        while (i<=n){
            writer.write("file 'video"+i+".mp4'");
            writer.write("\n");
            i++;
        }
        writer.close();
        Process merge= Runtime.getRuntime().exec("ffmpeg -f concat -safe 0 -i videoClipsList.txt -c:v copy -c:a copy "+path);
        merge.waitFor();
        System.out.println("All "+n+" video clips are merged together");

        //Removing all unnecessary files

        int j=1;
        while (j<=n){
            Process removeClips = Runtime.getRuntime().exec("rm video"+j+".mp4");
            removeClips.waitFor();
            j++;
        }
        System.out.println("All "+n+" video clips are deleted from system");

        Process removeList = Runtime.getRuntime().exec("rm videoClipsList.txt");
        removeList.waitFor();
        System.out.println("video clips list is deleted");
    }

    public static void compressVideo(String path, String pathForCompressedVideo, int targetFPS, String targetBitRate) throws IOException, InterruptedException {

        System.out.println("path for the recording is: "+path);
        File file = new File(path);

        String originalVideoSize = (double) file.length() / (1024 * 1024) + " mb";
        System.out.println("Size of merged video before compression: "+originalVideoSize);
        System.out.println("bit rate used for compression is: "+targetBitRate);
        System.out.println("fps for compression is: "+targetFPS);

        Process compress = Runtime.getRuntime().exec("ffmpeg -i "+ path +" -b "+ targetBitRate + " -r " + targetFPS + " "+ pathForCompressedVideo);
        System.out.println("compressing the video");
        compress.waitFor();
        System.out.println("video is compressed!!!!");

        File file1 = new File(pathForCompressedVideo);
        String compressedVideoSize = (double) file1.length() / (1024 * 1024) + " mb";
        System.out.println("Size of compressed file is: "+ compressedVideoSize);
    }
}
