import java.util.ArrayList;

public class Brote {

    private ArrayList<Usuario> usuariosContagiados;
    private Sintoma sintoma;


    public Brote(ArrayList<Usuario> usuariosContagiados, Sintoma sintoma) {
        this.usuariosContagiados = usuariosContagiados;
        this.sintoma = sintoma;
    }


    public ArrayList<Usuario> getUsuariosContagiados() {
        return usuariosContagiados;
    }

    public Sintoma getSintoma() {
        return sintoma;
    }

    public Integer sizeDelBrote (){
        return usuariosContagiados.size();
    }


}
