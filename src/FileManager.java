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
            bWriter.write("\n" + array[0] + "," + array[1] + "," + array[2] + "," + array[3] + "," + "0");
            bWriter.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    void updateUserFromFile(Usuario usuario) throws Exception {
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(filePath));
            StringBuffer inputBuffer = new StringBuffer();

            String lineToRemove = usuario.cuil + "," + usuario.celular + "," + usuario.zona + "," + usuario.nombre + "," + (usuario.solicitudesRechazadas-1);
            String linea;

            while ((linea = bReader.readLine()) != null) {
                if(linea.equals("cuil,cel,zona,nombre,nroDeRechazos")){
                    inputBuffer.append(linea);
                }else if (linea.equals(lineToRemove)) {
                    linea = "\n" + usuario.cuil + "," + usuario.celular + "," + usuario.zona + "," + usuario.nombre + "," + usuario.solicitudesRechazadas;
                    inputBuffer.append(linea);
                } else {
                    inputBuffer.append("\n" + linea);
                }
            }
            bReader.close();
            FileOutputStream archivoResultado = new FileOutputStream(filePath);
            archivoResultado.write(inputBuffer.toString().getBytes());
            archivoResultado.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void writeSolicitudToFile(Solicitud solicitud,ArrayList<Solicitud> listaSolicitudes){
        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(filePath, true));
            for (int i = 0; i < listaSolicitudes.size(); i++) {
                String cuilEnvia = listaSolicitudes.get(i).envia.cuil;
                String cuilRecibe = listaSolicitudes.get(i).recibe.cuil;
                String fecha1 = listaSolicitudes.get(i).fecha1.toString();
                String fecha2 = listaSolicitudes.get(i).fecha2.toString();
                bWriter.write("\n"+cuilEnvia+ ","+cuilRecibe+","+fecha1+","+fecha2);
            }
            bWriter.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
