package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Uiauto {
    public static void main(String[] args) throws IOException, InterruptedException {
        int i=0;

        Process process = Runtime.getRuntime().exec(
                "adb shell uiautomator dump && adb pull /sdcard/window_dump.xml");
        File file=new File("/Users/ishan.singh/window_dump.xml");
        Scanner scanObj=new Scanner(file);
        int count=0;
        String scount=" ";
        String bounds="";
        while(scanObj.hasNext()){
           // String bounds =new String();



            String word =scanObj.next();
            if(word.contains("clickable=\"true\"")){
                while(i<8){
                    if(scanObj.hasNext()){
                         bounds=scanObj.next();
                        //System.out.println(bounds);
                        }
                    i++;
                }
                i=0;
                System.out.println(word);
                System.out.println(bounds);
                 String b=bounds;

                String x0=b.substring(9,b.indexOf(","));
                System.out.println(x0);
                String y0=b.substring(b.indexOf(",")+1,b.indexOf("]["));
                System.out.println(y0);

                int j=b.indexOf("][")+2;
                while(!(b.charAt(j)==','))
                    j++;

                String x1=b.substring(b.indexOf("][")+2,j);
                int k=j;
                while(!(b.charAt(k)==']'))
                    k++;
                String y1=b.substring(j+1,k);
                System.out.println(x1);
                System.out.println(y1);

               // String bounds =new String("");

                int xi0= Integer.parseInt(x0);
                int yi0= Integer.parseInt(y0);
                int xi1= Integer.parseInt(x1);
                int yi1= Integer.parseInt(y1);

                int xmid=(xi0+xi1)/2;
                int ymid=(yi0+yi1)/2;

                String sxmid=Integer.toString(xmid);
                String symid=Integer.toString(ymid);


                Process process2 = Runtime.getRuntime().exec(
                        "adb shell input tap " +sxmid + " "+symid );
                TimeUnit.SECONDS.sleep(6);
                Process process3 = Runtime.getRuntime().exec(
                       "adb shell input keyevent 4" );
                TimeUnit.SECONDS.sleep(5);





            }
            count++;

        }




    }
}