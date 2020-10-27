import java.util.ArrayList;

public class UserManager {
    ArrayList<Usuario> listaDeUsuarios = new ArrayList<Usuario>();
    ArrayList<Administrador> listaDeAdministradores = new ArrayList<Administrador>();
    ArrayList<Solicitud> solicitudes = new ArrayList<Solicitud>();

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
        solicitudes.add(solicitud);
    }

    public void contestarSolicitud(Solicitud solicitud, boolean respuesta){
        Usuario envia = solicitud.envia;
        Usuario recibe = solicitud.recibe;
        if (respuesta){
            recibe.contactosEstrechos.add(envia);
            envia.contactosEstrechos.add(recibe);
            if (envia.solicitudesRechazadas > 0 && envia.solicitudesRechazadas < 5){
                envia.solicitudesRechazadas--;
            }
        } else {
            envia.solicitudesRechazadas++;
            if (envia.solicitudesRechazadas > 4){
                envia.estaBloqueado = true;
            }
        }
        solicitudes.remove(solicitud);
    }
    public ArrayList<Solicitud> solicitudesDeUsuario(Usuario usuarioActivo){
        ArrayList<Solicitud> solicitudesDeUsuario= new ArrayList<Solicitud>();
        for (int i = 0; i < solicitudes.size(); i++) {
            if(solicitudes.get(i).recibe.equals(usuarioActivo)){
                solicitudesDeUsuario.add(solicitudes.get(i));
            }else{
                //que siga...   -Timoteo
            }
        }
        return solicitudesDeUsuario;
    }
}
