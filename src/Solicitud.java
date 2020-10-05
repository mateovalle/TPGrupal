public class Solicitud {
    Usuario envia;
    Usuario recibe;
    int fecha1;
    int fecha2;

    public Solicitud(Usuario envia, Usuario recibe, int fecha1, int fecha2) {
        this.envia = envia;
        this.recibe = recibe;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
    }
}
