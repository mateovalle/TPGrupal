package TraceIt;

public class SintomaActivo extends Sintoma {

    private Date fechaDeIngresoDeSintoma;

    public SintomaActivo(String nombre, Date fechaDeIngresoDeSintoma) {
        super(nombre);
        this.fechaDeIngresoDeSintoma = fechaDeIngresoDeSintoma;
    }

    public String getNombreDelSintoma() {
        return super.nombre;
    }

    public Date getFechaDeIngresoDeSintoma() {
        return fechaDeIngresoDeSintoma;
    }
}
