import java.util.ArrayList;

public class Usuario {
    String cuil;
    String celular;
    String zona;
    String nombre;
    int solicitudesRechazadas;
    boolean estaBloqueado = false;
    ArrayList<Sintoma> sintomasActivos;

    ArrayList<Usuario> contactosEstrechos = new ArrayList<Usuario>();
    ArrayList<Solicitud> solicitudesRecibidas = new ArrayList<>();

    public Usuario(String cuil, String celular, String zona,String nombre, int solicitudesRechazadas) {
        this.cuil = cuil;
        this.celular = celular;
        this.zona=zona;
        this.nombre = nombre;
        this.solicitudesRechazadas=solicitudesRechazadas;
    }

    public void declararContactoEstrecho(Solicitud solicitud,  UserManager userManager){
        if(estaBloqueado){
            // esta bloqueado y no puede declarar. --Timo
        }else{
            userManager.mandarSolicitud(solicitud);
        }
    }
    public void contestarSolicitud(Solicitud solicitud, UserManager userManager, boolean respuesta){
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
        userManager.solicitudes.remove(solicitud);
    }

}
