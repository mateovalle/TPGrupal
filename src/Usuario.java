import java.util.ArrayList;

public class Usuario {
    String cuil;
    String celular;
    String zona;
    String nombre;
    int solicitudesRechazadas = 0;
    boolean estaBloqueado = false;
    ArrayList<Sintoma> sintomasActivos;

    ArrayList<Usuario> contactosEstrechos = new ArrayList<Usuario>();

    public Usuario(String cuil, String celular, String zona,String nombre) {
        this.cuil = cuil;
        this.celular = celular;
        this.nombre = nombre;
    }

    public void declararContactoEstrecho(Solicitud solicitud,  UserManager userManager){
        if(estaBloqueado){
            // esta bloqueado y no puede declarar. --Timo
        }else{
            userManager.mandarSolicitud(solicitud);
        }
    }
    public void contestarSolicitud(Solicitud solicitud, UserManager userManager, boolean respuesta){
        userManager.contestarSolicitud(solicitud, respuesta);
    }

}
