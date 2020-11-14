package TraceIt;

import java.util.ArrayList;

public class Brote {

    private ArrayList<Usuario> usuariosContagiados;
    private Enfermedad enfermedad;
    private String zona;
    private boolean estaActivo;


    public Brote(ArrayList<Usuario> usuariosContagiados, Enfermedad  enfermedad, String zona) {
        this.usuariosContagiados = usuariosContagiados;
        this.enfermedad = enfermedad;
        this.zona = zona;
        estaActivo = true;
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

    public boolean isActivo() {
        return estaActivo;
    }

    public void desactivarBrote(){
        estaActivo = false;
    }

    public void print(){
        System.out.println("El brote de la enfermedad" + enfermedad + " de " + zona + " tiene un tamaño de " + usuariosContagiados.size()+ " personas contagiadas.");
    }
}
