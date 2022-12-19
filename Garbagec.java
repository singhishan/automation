import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Garbagec {

    public static void main(String[] args) throws IOException{
        String logFilePath="/Users/ishan.singh/Downloads/logforgc.txt";
        File file=new File(logFilePath);
        gcprint(file);

    }
    public static void gcprint (File file) throws IOException {
        FileWriter writer=new FileWriter("gctaskblock.txt") ;
        Scanner scanObj1=new Scanner(file);
        while (scanObj1.hasNextLine()){
            String line = scanObj1.nextLine();
            if(line.contains("WaitForGcToComplete")){
                writer.write(line);
                writer.write("\n");}


        }
        writer.close();

    }
}