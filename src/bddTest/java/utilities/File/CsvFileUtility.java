package utilities.File;

import junit.framework.AssertionFailedError;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class CsvFileUtility {
    public static String[] getCsvHeaders(String pathFile){
        String csvFile = pathFile;
        String line;
        String csvSplitBy = ",";
        String[] csvHeaders;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile)));
            line = bufferedReader.readLine();
            line = line.replace(System.getProperty("line.separator"),"");
            csvHeaders = line.split(csvSplitBy);
            csvHeaders = removeDoubleQuotes(csvHeaders);
            return csvHeaders;
        } catch (IOException e) {
            throw new AssertionFailedError(String.format("There was an error when getting the headers of the Csv file: %s"
                    ,csvFile));
        }
        finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                throw new AssertionFailedError(String.format("There was an error when  closing the buggered stream of Csv file: %s"
                        ,csvFile));
            }
        }
    }

    private static String[] removeDoubleQuotes(String[] words){
        for(int i=0; i<words.length; i++){
            words[i] = words[i].replace("\"", "");
        }
        return words;
    }

    public static void deleteFile(String filePath){
        try{
            Files.delete(Paths.get(filePath));
        } catch (Exception e){
            throw new AssertionFailedError(String.format("There was an error deleting the file: %s, error: %s",filePath,e.toString()));
        }
    }
}