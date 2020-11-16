package TraceIt;

import java.io.*;
import java.util.ArrayList;

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

    void writeUsersToFile(UserManager userManager,String newFilePath){
        try{
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(newFilePath));
            ArrayList<Usuario> usuarios = userManager.listaDeUsuarios;
            bWriter.write("cuil,cel,zona,nombre,nroDeRechazos, estaBloqueado, enfermedadActual, fechaDeEnfermedad, Sintomas, ContactosEstrechos");
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario usuario = usuarios.get(i);
                String string0 = usuario.getCuil();
                String string1 = usuario.getCelular();
                String string2 = usuario.getZona();
                String string3 = usuario.getNombre();
                String string4 = String.valueOf(usuario.getSolicitudesRechazadas());
                String string5 = String.valueOf(usuario.isEstaBloqueado());
                String string6;
                String string7;
                if(usuario.enfermedadActual == null){
                    string6 = "null";
                    string7 = "null";
                }else{
                    string6 = usuario.getEnfermedadActual().nombre;
                    string7 = usuario.getFechaDeEnfermedad().toString();
                }
                String string8="null_null";
                if(!usuario.getSintomas().isEmpty()){
                    string8 = "";
                    for(Sintoma sintoma:usuario.getSintomas().keySet()) {
                        string8 +=sintoma.nombre + "_";
                        string8 += usuario.getSintomas().get(sintoma).toString() + ";";
                    }
                }
                String string9="null_null";
                if(!usuario.getContactosEstrechos().isEmpty()){
                    string9 = "";
                    for(Usuario otroUsuario:usuario.getContactosEstrechos().keySet()) {
                        string9 +=otroUsuario.getCuil() + "_";
                        string9 += usuario.getContactosEstrechos().get(otroUsuario).toString() + ";";
                    }
                }
                String usuarioLine = "\n"+string0+","+string1+","+string2+","+string3+","+string4+","+string5+","+string6+","+string7+","+string8+","+string9;
                bWriter.write(usuarioLine);
            }
            bWriter.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    void writeSolicitudToFile(ArrayList<Solicitud> listaSolicitudes){
        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(filePath));
            bWriter.write("Usuario envia, Usuario recibe, Date fecha1, Date fecha2");
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

    void writeAdvertenciaToFile(UserManager userManager){
        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(filePath));
            for (int i = 0; i < userManager.listaDeAdvertencias.size(); i++) {
                String cuilEnvia = userManager.listaDeAdvertencias.get(i).getUsuarioQueEnviaAdvertencia().cuil;
                String fecha = userManager.listaDeAdvertencias.get(i).getFecha().toString();
                bWriter.write("\n"+cuilEnvia+ ","+fecha);
            }
            bWriter.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    void writeBrotesToFile(){
        ArrayList<Brote> listaDeBrotes = EnfermedadesABM.listaDeBrotes;
        if(!listaDeBrotes.isEmpty()){
            try{
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(filePath));
                bWriter.write("Enfermedad,zona,cuilsDeEnfermos");
                for (int i = 0; i < listaDeBrotes.size(); i++) {
                    String enfermedad = listaDeBrotes.get(i).getEnfermedad().nombre;
                    String zona = listaDeBrotes.get(i).getZona();
                    String cuils = "";
                    for (int j = 0; j < listaDeBrotes.get(i).getUsuariosContagiados().size()-1; j++) {
                        cuils += listaDeBrotes.get(i).getUsuariosContagiados().get(j).getCuil()+";";
                    }
                    cuils += listaDeBrotes.get(i).getUsuariosContagiados().get(listaDeBrotes.get(i).getUsuariosContagiados().size()-1).getCuil();
                    bWriter.write("\n"+enfermedad+ ","+zona+","+cuils);
                }
                bWriter.close();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    void writeEnfermedadesToFile(){
        ArrayList<Enfermedad> listaDeEnfermedades = EnfermedadesABM.listaDeEnfermedades;
        if(!listaDeEnfermedades.isEmpty()){
            try{
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(filePath));
                bWriter.write("Enfermedad,zona,cuilsDeEnfermos");
                for (int i = 0; i < listaDeEnfermedades.size(); i++) {
                    String enfermedad = listaDeEnfermedades.get(i).nombre;
                    String sintomas="";
                    if(!listaDeEnfermedades.get(i).sintomas.isEmpty()){
                        for(Sintoma sintoma:listaDeEnfermedades.get(i).sintomas) {
                            sintomas +=sintoma.getNombre() + ";";
                        }
                    }
                    bWriter.write("\n"+enfermedad+ ","+sintomas);
                }
                bWriter.close();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
