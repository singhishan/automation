import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
public class Jankyframes {

    public static void frames (File file,FileWriter writer) throws IOException{


        String  word=" ";
        String time1=" ";
        long framecompleted=0;
        long intended_vsync=0;
        long time;
        String int_vsync=" ";
        String framcompleted="";
        int k=0;
        ArrayList<Long> arraylongList = new ArrayList<Long>();
        int count=0;
        int flag=0;
        double dtime=0;
        int num_frames=0;
        long sum=0;
        long totaldroppedframes=0;

        Scanner scanObj=new Scanner(file);

        while (scanObj.hasNextLine()) {
            String line = scanObj.nextLine();

            if (line.contains("---PROFILEDATA---")) {
                line=scanObj.nextLine();
                line =scanObj.nextLine();

                while (!(line.contains("---PROFILEDATA---"))) {

                    if (line.charAt(0) == '0') {
                        String[] arrOflong = line.split(",", 17);
                        int_vsync = arrOflong[2];
                        framcompleted = arrOflong[13];
                        intended_vsync = Long.parseLong(int_vsync);
                        framecompleted = Long.parseLong(framcompleted);


                        time = framecompleted - intended_vsync;

                        dtime = (double) time / 1000000;

                        double rtime = Math.round(dtime * 10.0) / 10.0;


                        time1 = Double.toString(rtime);

                        arraylongList.add(time);
                        num_frames = arraylongList.size();


                        writer.write("intended_vsync  ");
                        writer.write(int_vsync);
                        writer.write("   framecompleted  ");
                        writer.write(framcompleted);
                        writer.write(" ");
                        writer.write("frame render duration ");
                        writer.write(" " + time1);
                        writer.write("\n");




                    }

                    line = scanObj.nextLine();

                }

            }
            num_frames=arraylongList.size();
            for(long a:arraylongList){
                sum=sum+a;
            }}
        Long[] arr = new Long[arraylongList.size()];
        arr = arraylongList.toArray(arr);

        int size=arr.length;
        for(int i=0;i<size-1;i++){
            if((arr[i]>16)&&(arr[i+1]<16))
                k++;
        }

        long last1 = arr[arraylongList.size()-1];


        if(last1>16)
            k++;
        System.out.println("the number of droped durations   "+k);
        writer.write("the number of droped durations   "+k);

        writer.write("the number of continuous dropped frames "+count +"\n");
        System.out.println("Total number of frames"+num_frames);
        System.out.println("Total render duration "+sum);
        System.out.println("total janked frames  "+totaldroppedframes);

        writer.close();


    }


    public static void main(String[] args) throws IOException, InterruptedException {
        int i=0;
        int j=0;
        String inputString="";
        
        while(!inputString.equals("exit")){

            FileWriter writer= new FileWriter("file"+i+".txt");


        Process process = Runtime.getRuntime().exec("\n" + " adb shell dumpsys gfxinfo glance.sample.standalone.xiaomi  reset");

             Process process1 = Runtime.getRuntime().exec("adb shell dumpsys gfxinfo glance.sample.standalone.xiaomi framestats ");

            BufferedReader reader=  new BufferedReader(new InputStreamReader(process1.getInputStream()));
            String line;
            while((line =reader.readLine())!= null){
                writer.write(line+"\n");
            }
            writer.close();
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("type exit to exit anything else to continue");
            inputString = scanner1.nextLine();
            TimeUnit.SECONDS.sleep(1);
        i++;}
        FileWriter writer= new FileWriter("printFrames.txt");
    for(j=0;j<i;j++){
        File file=new File("file" + j + ".txt");
        frames(file,writer);
    }

    }
}