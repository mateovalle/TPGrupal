public class Main {
    public static void main(String[] args) {
        Usuario usuario1 = new Usuario("0000001", "112556564");
        Usuario usuario2 = new Usuario("121212", "11548484");
        Administrador administrador = new Administrador("hola", "1234");


        usuario1.declararContactoEstrecho(usuario2, 0, 4);

    }

    /*Crear nuevo usario
    Entrar como ciudadano
        declarar contacto estrecho
        chequear solicitudes
        declarar sintoma
        eliminar sintoma
        >que muestre el estado actual
    Entrar como administrador
        crear evento
        desbloquear usuario
     */

}
