public class Solicitud {
    Usuario envia;
    Usuario recibe;
    Date fecha1;
    Date fecha2;

    public Solicitud(Usuario envia, Usuario recibe, Date fecha1, Date fecha2) {
        this.envia = envia;
        this.recibe = recibe;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
    }

    @Override
    public String toString() {
        return envia.nombre + " ha declarado contacto estrecho contigo desde "+ fecha1.toString()+" hasta "+fecha2.toString();
    }
}
