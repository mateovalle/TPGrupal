package TraceIt;

import java.util.ArrayList;
import java.util.Objects;

public class Enfermedad {
    String nombre;
    public ArrayList<Sintoma> sintomas;

    public Enfermedad(String nombre, ArrayList<Sintoma> sintomas) {
        this.nombre = nombre;
        this.sintomas = sintomas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enfermedad that = (Enfermedad) o;
        return Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
