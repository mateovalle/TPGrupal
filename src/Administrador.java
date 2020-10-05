import java.util.ArrayList;

public class Administrador {
    String usuario;
    String contraseña;
    ArrayList<Administrador> listaDeAdministradores = new ArrayList<Administrador>();

    public Administrador(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    void declararSintomas(String nombre, ArrayList<Enfermedad> enfermedades){
        Sintoma sintoma = new Sintoma(nombre);
        for (int i = 0; i < enfermedades.size(); i++) {
            enfermedades.get(i).sintomas.add(sintoma);
        }
    }

    void desbloquearUsuario(Usuario usuario){
        usuario.solicitudesRechazadas = 0;
        usuario.estaBloqueado = false;
    }

    void bloquearUsuario(Usuario usuario){
        usuario.estaBloqueado = true;
    }


}
