import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Leaks{

    public static int printTotalLeaks (File file) throws IOException{

        FileWriter writer= new FileWriter("printLeaks.txt");

        int leakCount=0;
        Scanner scanObj=new Scanner(file);

        while (scanObj.hasNextLine()) {
            String line = scanObj.nextLine();

            if (line.contains("HEAP ANALYSIS")) {
                //writer.write(line);
                leakCount++;
                while (!(line.contains("Heap dump duration"))) { 
                    writer.write(line);
                    writer.write("\n");
                    line = scanObj.nextLine();

                }
                writer.write(line);
                writer.write("*****************************************************");
                writer.write("\n");
                writer.write("\n");
                writer.write("\n");
            }
        }
        writer.write("number of total leaks is :" + leakCount);
        writer.write("\n");
        writer.close();
        return leakCount;
    }


    public static String printSingleStackTrace(File file,Scanner scanObj) throws IOException{
        String word=" ";
        String strace=" ";
        while(!(word.contains("*"))){
            if(scanObj.hasNext())
                word=scanObj.next();
            if(word.contains("├─")||word.contains("╰→")){
                if(scanObj.hasNext())
                    word=scanObj.next();
                strace=strace+" "+word;
            }
                  }
       return strace;

    }



    public static void printAllStackTrace(int leakCount) throws IOException{ 
        File file =new File("printLeaks.txt"); 
        FileWriter writer=new FileWriter("printStackTrace.txt") ;   
        Scanner scanObj =new Scanner(file);
        Scanner scanObj1=new Scanner(file);
        String  word1=" ";
        String sTrace1=" ";
        String  sTrace=printSingleStackTrace(file,scanObj);

        int distinctCount=0;
        int count=1;
        int leak=1;

        while(leak<leakCount+1){

                sTrace1=printSingleStackTrace(file,scanObj1);

            writer.write("\n");
            writer.write("Stack trace of leak"+leak);
            writer.write("\n");
            writer.write("\n");
            writer.write(""+sTrace1);
            System.out.println(sTrace1);
            writer.write("\n");
            if(!(sTrace1.equals(sTrace)))
                distinctCount++;
            sTrace1=" ";

            if(scanObj1.hasNext())
                word1=scanObj1.next();
            leak++;
        }
        writer.write("\n");
        writer.write("\n");
        writer.write(" the total distinct leak is "+distinctCount);
        writer.close();
        }
      


    public static void main(String[] args) throws IOException {
        String consoleLogFilePath=args[0];
        File file= new File(consoleLogFilePath);
        int leakCount=printTotalLeaks(file);

        printAllStackTrace(leakCount);
        
  }} 