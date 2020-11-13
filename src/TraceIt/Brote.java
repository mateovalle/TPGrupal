package TraceIt;

import java.util.ArrayList;

public class Brote {

    private ArrayList<Usuario> usuariosContagiados;
    private Enfermedad enfermedad;
    private String zona;


    public Brote(ArrayList<Usuario> usuariosContagiados, Enfermedad  enfermedad, String zona) {
        this.usuariosContagiados = usuariosContagiados;
        this.enfermedad = enfermedad;
        this.zona = zona;
    }


    public ArrayList<Usuario> getUsuariosContagiados() {
        return usuariosContagiados;
    }

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }

    public String getZona() {
        return zona;
    }

    public Integer sizeDelBrote (){
        return usuariosContagiados.size();
    }


}
