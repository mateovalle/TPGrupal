import java.util.ArrayList;

public class Usuario {
    String cuil;
    String celular;
    String zona;
    int solicitudesRechazadas = 0;
    boolean estaBloqueado = false;
    ArrayList<Sintoma> sintomasActivos;

    ArrayList<Usuario> contactosEstrechos = new ArrayList<Usuario>();

    public Usuario(String cuil, String celular, String zona) {
        //validacion del anses
        this.cuil = cuil;
        this.celular = celular;
    }

    public void declararContactoEstrecho(String cuilOCelular, int fecha1, int fecha2){
        //chequear que no este bloqueado
        //buscar en la lista el cuil y ver si lo encuentra
        //si lo encuentra que le mande una solicitud
        //this.mandarSolicitud(otroUsuario);
        return;
    }

}
