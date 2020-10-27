import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FileManager {
    String filePath;
    public FileManager(String filePath) {
        this.filePath=filePath;
    }

    public ArrayList<String[]> getDataFromFile() throws IOException {
        ArrayList<String[]> lista = new ArrayList<>();

        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String userInfo;
        while ((userInfo = br.readLine()) != null) {
            String[] data = userInfo.split(",");
            lista.add(data);
        }
        return lista;
    }


    void writeUsersToFile(String[] array, String newFilePath){
        try{
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(newFilePath, true)) ;
            bWriter.write("\n" + array[3] + "," + array[0] + "," + array[1] + "," + array[2] + "," + "0" + ",");
            bWriter.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
