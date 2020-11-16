package TraceIt;

import java.util.ArrayList;
import java.util.HashMap;

public class UserManager {
    ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();
    ArrayList<Administrador> listaDeAdministradores = new ArrayList<>();
    ArrayList<Solicitud> listaSolicitudes = new ArrayList<>();
    ArrayList<Advertencia> listaDeAdvertencias = new ArrayList<>();


    public UserManager(){
    }

    public void crearUsuario(String cuil, String celular, String zona,String nombre){
        Usuario usuario = new Usuario(cuil, celular, zona, nombre);
        listaDeUsuarios.add(usuario);
    }

    public void crearAdministrador(String usuario, String contraseña){
        Administrador administrador = new Administrador(usuario, contraseña);
        listaDeAdministradores.add(administrador);
    }

    public void mandarSolicitud(Solicitud solicitud){
        listaSolicitudes.add(solicitud);
    }

    public void setListaDeUsuarios(ArrayList<Usuario> listaDeUsuarios) {
        this.listaDeUsuarios = listaDeUsuarios;
    }

    public void agregarUsuariosALista(ArrayList<String[]> infoDeUsuarios){
        for (int i = 1; i < infoDeUsuarios.size(); i++) {
            String cuil=infoDeUsuarios.get(i)[0];
            String cel=infoDeUsuarios.get(i)[1];
            String zona=infoDeUsuarios.get(i)[2];
            String nombre=infoDeUsuarios.get(i)[3];
            int nroRechazos=Integer.parseInt(infoDeUsuarios.get(i)[4]);
            boolean estaBloqueado=Boolean.parseBoolean(infoDeUsuarios.get(i)[5]);
            HashMap<Sintoma, Date> sintomas = new HashMap<>();
            if(!infoDeUsuarios.get(i)[8].equals("null_null")){
                String[] array = infoDeUsuarios.get(i)[8].split(";");
                for (int j = 0; j < array.length; j++) {
                    String[] array2 = array[j].split("_");
                    Sintoma sintoma = Main.buscarSintoma(array2[0]);
                    Date fecha1 = new Date(array2[1]);
                    sintomas.put(sintoma, fecha1);
                }
            }
            Date fechaDeEnfermedad=null;
            Enfermedad enfermedadActual = null;
            HashMap<Usuario, Date> contactoEstrecho = new HashMap<>();

            Usuario usuario= new Usuario(cuil, cel ,zona,nombre,nroRechazos,estaBloqueado,enfermedadActual,fechaDeEnfermedad,sintomas,contactoEstrecho);
            usuario.fechaDeEnfermedad = new Date("01/01/0001");
            for (Date fecha : usuario.getSintomas().values()){
                if(usuario.fechaDeEnfermedad.before(fecha)){
                    usuario.fechaDeEnfermedad=fecha;
                }
            }
            usuario.enfermedadActual = DetectorDeEnfermedades.detectarEnfermedad(usuario);
            listaDeUsuarios.add(usuario);
        }

        for (int i = 1; i < infoDeUsuarios.size(); i++) {
            Usuario usuario = listaDeUsuarios.get(i-1);
            HashMap<Usuario,Date> contactoEstrecho = new HashMap<>();
            if(!infoDeUsuarios.get(i)[9].equals("null_null")){
                String[] array3 = infoDeUsuarios.get(i)[9].split(";");
                for (int j = 0; j < array3.length; j++) {
                    String[] array4 = array3[j].split("_");
                    Usuario otroUsuario = Main.buscarUsuario(array4[0],this);
                    Date fecha2 = new Date(array4[1]);
                    contactoEstrecho.put(usuario, fecha2);
                }
            }
            usuario.contactosEstrechos = contactoEstrecho;
        }
    }

    public void agregarAdminALista(ArrayList<String[]> infoDeAdmin){
        for (int i = 1; i < infoDeAdmin.size(); i++) {
            Administrador administrador= new Administrador(infoDeAdmin.get(i)[0],infoDeAdmin.get(i)[1]);
            listaDeAdministradores.add(administrador);
        }
    }
    public void agregarSolictudALista(ArrayList<String[]> infoDeSolicitud){
        for (int i = 1; i < infoDeSolicitud.size(); i++) {
            Date fecha1 = new Date(infoDeSolicitud.get(i)[2]);
            Date fecha2 = new Date(infoDeSolicitud.get(i)[3]);
            Usuario envia = Main.buscarUsuario(infoDeSolicitud.get(i)[0],this);
            Usuario recibe = Main.buscarUsuario(infoDeSolicitud.get(i)[1],this);
            Solicitud solicitud= new Solicitud(envia,recibe,fecha1,fecha2);
            listaSolicitudes.add(solicitud);
        }
    }
    public void repartirSolicitudes(){
        if(listaSolicitudes!=null){
            for (int i = 0; i < listaSolicitudes.size(); i++) {
                listaSolicitudes.get(i).recibe.solicitudesRecibidas.add(listaSolicitudes.get(i));
            }
        }
    }
    public void mandarAdvertencia (ArrayList<Usuario> usuarios, Date fecha, Usuario usuarioContagiado){ // agrega advertencia al la lista de advertencias del usuario con el que estuvo
        Advertencia advertencia = new Advertencia(usuarioContagiado, fecha);
        for (int i = 0; i < usuarios.size(); i++) {
            usuarios.get(i).advertencias.add(advertencia);
        }
    }
    public void repartirAdvertencias (){
        for (int i = 0; i < listaDeAdvertencias.size(); i++) {
            Usuario usuarioContagiado = listaDeAdvertencias.get(i).getUsuarioQueEnviaAdvertencia();
            for(Usuario otroUsuario:usuarioContagiado.contactosEstrechos.keySet()){
                otroUsuario.advertencias.add(listaDeAdvertencias.get(i));
            }
        }
    }

    public ArrayList<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }
}
