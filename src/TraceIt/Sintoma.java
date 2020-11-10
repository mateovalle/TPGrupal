package TraceIt;

import java.util.ArrayList;

public class Sintoma {
    String nombre;
    ArrayList<Enfermedad> enfermedadesRelacionadas;

    public Sintoma(String nombre) {
        this.nombre = nombre;
    }

    public boolean equals(Sintoma otroSintoma){
        if (this.nombre.equals(otroSintoma.nombre)){
            return true;
        }
        return false;
    }
}
