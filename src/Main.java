import java.util.ArrayList;
import util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> anses = new ArrayList<String>();
        Administrador administradorActivo;
        Usuario usuarioActivo;
        UserManager userManager = new UserManager();
        Administrador administrador = new Administrador("hola", "1234");

    }
    //Menus

    static void inicio(UserManager userManager, ArrayList<String> anses, Usuario usuarioActivo, Administrador administradorActivo) {
        System.out.println("1. Ingresar como administrador");
        System.out.println("2.Ingresar como usuario");
        System.out.println("3. Crear un nuevo usuario");
        System.out.println("4. Salir");

        int n = Scanner.getInt("");

        switch (n) {
            case 1:
                entrarComoAdministrador(userManager, anses, usuarioActivo, administradorActivo);
                break;
            case 2:
                entrarComoUsuario(userManager, anses, usuarioActivo, administradorActivo);
                break;
            case 3:
                crearNuevoUsuario(userManager, anses);
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Comando invalido, pruebe de nuevo.");
                inicio(userManager, anses, usuarioActivo, administradorActivo);
                break;
        }
    }

    static void menuDeUsuario(UserManager userManager, ArrayList<String> anses, Usuario usuarioActivo, Administrador administradorActivo) {
        System.out.println("1. Declarar contacto estrecho");
        System.out.println("2. Chequear solicitudes");
        System.out.println("3. Declarar sintoma");
        System.out.println("4. Eliminar sitnoma");
        System.out.println("5. Cerrar sesion");

        int n = Scanner.getInt("");

        switch (n) {
            case 1:
                String cuilOCelular = Scanner.getString("Ingrese el cuil o celular de la persona con la que tuvo contacto estrecho: ");
                int fecha1 = Scanner.getInt("Ingrese el primer dia de su encuentro: ");
                int fecha2 = Scanner.getInt("Ingrese el ultimo dia de su encuentro: ");
                Usuario otroUsuario = buscarUsuario(cuilOCelular, userManager);
                if (otroUsuario == null) {
                    System.out.println("No se encontro el usuario.");
                    menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                }

                break;
            case 2:
                entrarComoUsuario(userManager, anses, usuarioActivo, administradorActivo);
                break;
            case 3:
                crearNuevoUsuario(userManager, anses);
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Comando invalido, pruebe de nuevo.");
                menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                break;
        }
    }

    static void menuDeAdministrador(UserManager userManager, ArrayList<String> anses, Usuario usuarioActivo, Administrador administradorActivo) {

        System.out.println("1) Crear evento");
        System.out.println("2) Desbloquear usuario");
        System.out.println("3) Volver al inicio");
        int opcion = Scanner.getInt("Introduzca el numero de opción: ");

        switch (opcion) {
            case 1:
                crearEvento();
                break;

            case 2:
                String cuiloCelular = Scanner.getString("introduzca el Cuil o el Celular del usuario a desbloquear: ");
                Usuario usuarioaDesbloquear = buscarUsuario(cuiloCelular, userManager);
                administradorActivo.desbloquearUsuario(usuarioaDesbloquear);
                break;

                case 3:
                    inicio(userManager, anses, usuarioActivo, administradorActivo);
                default:
                    System.out.println("El valor ingresado no corresponde a ninguna opción, intente nuevamente");
                    menuDeAdministrador(userManager, anses, usuarioActivo,administradorActivo);

        }
    }

        //Métodos

        static void entrarComoAdministrador (UserManager userManager, ArrayList < String > anses, Usuario
        usuarioActivo, Administrador administradorActivo){
            String usuario = Scanner.getString("Ingrese su nombre de usuario: ");
            for (int i = 0; i < userManager.listaDeAdministradores.size(); i++) {
                if (usuario.equals(userManager.listaDeAdministradores.get(i).usuario)) {
                    String contraseña = Scanner.getString("Ingrese su contraseña");
                    if (contraseña.equals(userManager.listaDeAdministradores.get(i).contraseña)) {
                        administradorActivo = buscarAdministrador(usuario, contraseña, userManager);
                        menuDeAdministrador(userManager, anses, usuarioActivo, administradorActivo);
                    } else {
                        System.out.println("Contraseña incorrecta");
                        inicio(userManager, anses, usuarioActivo, administradorActivo);
                    }
                } else {
                    System.out.println("No se encontro un usuario con ese nombre.");
                    inicio(userManager, anses, usuarioActivo,administradorActivo);
                }
            }

        }

        static void entrarComoUsuario (UserManager userManager, ArrayList < String > anses, Usuario usuarioActivo, Administrador administradorActivo){
            String cuilOContraseña = Scanner.getString("Ingrese su cuil o celular");
            for (int i = 0; i < userManager.listaDeUsuarios.size(); i++) {
                if (cuilOContraseña.equals(userManager.listaDeUsuarios.get(i).celular) || cuilOContraseña.equals(userManager.listaDeUsuarios.get(i).cuil)) {
                    usuarioActivo = userManager.listaDeUsuarios.get(i);
                    menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                } else {
                    inicio(userManager, anses, usuarioActivo, administradorActivo);
                }
            }
        }

        static void crearNuevoUsuario (UserManager userManager, ArrayList < String > anses){
            String cuilOCelular = Scanner.getString("Ingrese su numero de telefono o cuil: ");
            for (int i = 0; i < anses.size(); i++) {
                //validar en el anses
            }
        }



    static Usuario buscarUsuario(String cuilOCelular, UserManager userManager){
        for (int i = 0; i < userManager.listaDeUsuarios.size(); i++) {
            if (cuilOCelular.equals(userManager.listaDeUsuarios.get(i).cuil) || cuilOCelular.equals(userManager.listaDeUsuarios.get(i).celular)){
                return userManager.listaDeUsuarios.get(i);
            }
        } return null;
    }
    static Administrador buscarAdministrador(String usuario, String contraseña, UserManager userManager){
        for (int i = 0; i < userManager.listaDeAdministradores.size(); i++){
            if(userManager.listaDeAdministradores.get(i).usuario.equals(usuario) && userManager.listaDeAdministradores.get(i).contraseña.equals(contraseña)){
                return userManager.listaDeAdministradores.get(i);
            }else{
                //que siga
            }
        }
        return null;
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
