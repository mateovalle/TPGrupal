import java.util.ArrayList;

public class UserManager {
    ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();
    ArrayList<Administrador> listaDeAdministradores = new ArrayList<>();
    ArrayList<Solicitud> solicitudes = new ArrayList<>();

    public UserManager(){
    }

    public void crearUsuario(String cuil, String celular, String zona,String nombre){
        Usuario usuario = new Usuario(cuil, celular, zona, nombre, 0);
        listaDeUsuarios.add(usuario);
    }

    public void crearAdministrador(String usuario, String contraseña){
        Administrador administrador = new Administrador(usuario, contraseña);
        listaDeAdministradores.add(administrador);
    }

    public void mandarSolicitud(Solicitud solicitud){
        solicitudes.add(solicitud);
    }

    public void setListaDeUsuarios(ArrayList<Usuario> listaDeUsuarios) {
        this.listaDeUsuarios = listaDeUsuarios;
    }

    public void agregarUsuariosALista(ArrayList<String[]> infoDeUsuarios){
        for (int i = 1; i < infoDeUsuarios.size(); i++) {
            Usuario usuario= new Usuario(infoDeUsuarios.get(i)[0],infoDeUsuarios.get(i)[1],infoDeUsuarios.get(i)[2],infoDeUsuarios.get(i)[3],Integer.parseInt(infoDeUsuarios.get(i)[4]));
            listaDeUsuarios.add(usuario);
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
            solicitudes.add(solicitud);
        }
    }
    public void repartirSolicitudes(){
        for (int i = 0; i < solicitudes.size(); i++) {
            solicitudes.get(i).recibe.solicitudesRecibidas.add(solicitudes.get(i));
        }
    }

    public void mandarAdvertencia (ArrayList<Usuario> usuarios, Date fecha, Usuario usuarioContagiado){ // agrega advertencia al la lista de advertencias del usuario con el que estuvo
        Advertencia advertencia = new Advertencia(usuarioContagiado, fecha);
        for (int i = 0; i < usuarios.size(); i++) {
            usuarios.get(i).advertencias.add(advertencia);
        }
    }


}
