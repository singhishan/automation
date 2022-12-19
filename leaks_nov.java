import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class leaks_nov {
    public static int printTotalLeaks (File file) throws IOException{

        FileWriter writer= new FileWriter("printLeaks.txt");

        int leakCount=0;
        Scanner scanObj=new Scanner(file);

        while (scanObj.hasNextLine()) {
            String line = scanObj.nextLine();

            if (line.contains("HEAP ANALYSIS")) {
                String word=" ";
                String strace=" ";
                leakCount++;
                while (!(line.contains("Heap dump duration"))) {


                        if(scanObj.hasNext())
                            word=scanObj.next();
                        if(word.contains("├─")||word.contains("╰→")){
                            if(scanObj.hasNext())
                                word=scanObj.next();
                            strace=strace+" "+word;

                        }
                        if(scanObj.hasNextLine())
                            line = scanObj.nextLine();

                }

                writer.write(strace);
                System.out.println(strace);
                writer.write("\n");
                writer.write("\n");
                writer.write("***************************");
                writer.write("\n");
                writer.write("\n");

            }
        }
        writer.write("number of total leaks is :" + leakCount);
        writer.write("\n");
        writer.close();
        return leakCount;
    }

    public static void main(String[] args) throws IOException {
        String logFilePath="/Users/ishan.singh/Desktop/chatur_leak_task/oncelogleaks.txt";
        File file=new File(logFilePath);

        printTotalLeaks(file);

    }

}