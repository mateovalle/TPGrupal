import java.util.ArrayList;

public class Usuario {
    String cuil;
    String celular;
    int solicitudesRechazadas = 0;
    boolean estaBloqueado = false;

    public static ArrayList<Usuario> listaDeUsuarios = new ArrayList<Usuario>();
    ArrayList<Solicitud> solicitudes = new ArrayList<Solicitud>();
    ArrayList<Usuario> contactosEstrechos = new ArrayList<Usuario>();

    public Usuario(String cuil, String celular) {
        //validacion del anses
        this.cuil = cuil;
        this.celular = celular;
        listaDeUsuarios.add(this);
    }

    public void declararContactoEstrecho(String cuilOCelular, int fecha1, int fecha2){
        //chequear que no este bloqueado
        //buscar en la lista el cuil y ver si lo encuentra
        //si lo encuentra que le mande una solicitud
        //this.mandarSolicitud(otroUsuario);
        return;
    }


    public void mandarSolicitud(Usuario recibe, int fecha1, int fecha2){
        Solicitud solicitud = new Solicitud(this, recibe, fecha1, fecha2);
        recibe.solicitudes.add(solicitud);
    }

    public void contestarSolicitud(Solicitud solicitud, boolean respuesta){
        Usuario envia = solicitud.envia;
        if (respuesta){
            this.contactosEstrechos.add(envia);
            envia.contactosEstrechos.add(this);
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
}
