public class Advertencia {

    private Date fecha;
    private Usuario usuarioQueEnviaAdvertencia;


    public Advertencia( Usuario usuarioQueEnviaAdvertencia, Date fecha) {
        this.fecha = fecha;
        this.usuarioQueEnviaAdvertencia = usuarioQueEnviaAdvertencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public Usuario getUsuarioQueEnviaAdvertencia() {
        return usuarioQueEnviaAdvertencia;
    }
}