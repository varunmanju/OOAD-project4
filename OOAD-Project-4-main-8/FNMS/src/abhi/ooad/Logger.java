package abhi.ooad;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements Subscriber{
    private static Logger instance = new Logger(); //eager instantiation
    subscriberType type;

    //constructor to specify type and open file to write to
    //code from https://www.w3schools.com/java/java_files_create.asp for handling files in Java
    private Logger(){
        type = subscriberType.LOGGER;
    }

    public static Logger getInstance(){
        return instance;
    }

    //write to file
    public void out(String message, String storeName, int day){
        File outputFile;
        FileWriter writer;
        String fName = "FNMS\\Logger-Files\\Logger_"+storeName+"_"+day+".txt";
        outputFile = new File(fName);
        //if file isn't already made, make it and fill header
        if (!outputFile.exists()){
            outputFile = createNewFile(fName);
        }
        // create writer for the file
        try{
            writer = new FileWriter(outputFile, true);
            writer.write(message + "\n");
            writer.close();
        } catch(IOException b){
            System.out.println("error occurred while creating writer to" + fName);
            b.printStackTrace();
        }
    }

    //opens file and prints header
    public File createNewFile(String fName){
        File outputFile = new File(fName);
        FileWriter writer;
        try{
            writer = new FileWriter(outputFile, true);
            writer.write("----------------------" + fName + "----------------------" + "\n");
            writer.close();
        } catch(IOException b){
            System.out.println("error occurred while creating writer to" + fName);
            b.printStackTrace();
        }
        return outputFile;
    }


    //might want this
    public void deleteOldFiles(int range, String storeName){
        File outputFile;
        for (int i = 0; i < range; i++){
            outputFile = new File("Logger_"+storeName+"_"+i+".txt");
            outputFile.delete();
        }
    }


}