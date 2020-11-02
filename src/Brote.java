import java.util.ArrayList;

public class Brote {

    private ArrayList<Usuario> usuariosContagiados;
    private Enfermedad enfermedad;


    public Brote(ArrayList<Usuario> usuariosContagiados, Enfermedad  enfermedad) {
        this.usuariosContagiados = usuariosContagiados;
        this.enfermedad = enfermedad;
    }


    public ArrayList<Usuario> getUsuariosContagiados() {
        return usuariosContagiados;
    }

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }

    public Integer sizeDelBrote (){
        return usuariosContagiados.size();
    }


}
