import java.sql.SQLOutput;
import java.util.ArrayList;

import util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> anses = new ArrayList<String>();

        Usuario usuarioActivo;
        UserManager userManager = new UserManager();
        Administrador administrador = new Administrador("hola", "1234");

    }

    static void inicio(UserManager userManager, ArrayList<String> anses, Usuario usuarioActivo){
        System.out.println("1. Ingresar como administrador");
        System.out.println("2.Ingresar como usuario");
        System.out.println("3. Crear un nuevo usuario");
        System.out.println("4. Salir");

        int n = Scanner.getInt("");

        switch (n){
            case 1:
                entrarComoAdministrador(userManager);
                break;
            case 2:
                entrarComoUsuario(userManager);
                break;
            case 3:
                crearNuevoUsuario(userManager, anses);
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Comando invalido, pruebe de nuevo.");
                inicio();
                break;
        }
    }

    static void entrarComoAdministrador(UserManager userManager){
        String usuario = Scanner.getString("Ingrese su nombre de usuario: ");
        for (int i = 0; i < userManager.listaDeAdministradores.size(); i++) {
            if (usuario.equals(userManager.listaDeAdministradores.get(i).usuario)){
                String contraseña = Scanner.getString("Ingrese su contraseña");
                if (contraseña.equals(userManager.listaDeAdministradores.get(i).contraseña)){
                    menuDeAdministrador();
                } else {
                    System.out.println("Contraseña incorrecta");
                    inicio(userManager);
                }
            } else {
                System.out.println("No se encontro un usuario con ese nombre.");
                inicio(userManager);
            }
        }

    }

    static void entrarComoUsuario(UserManager userManager, ArrayList<String> anses, Usuario usuarioActivo){
        String cuilOContraseña = Scanner.getString("Ingrese su cuil o celular");
        for (int i = 0; i < userManager.listaDeUsuarios.size(); i++) {
            if (cuilOContraseña.equals(userManager.listaDeUsuarios.get(i).celular) || cuilOContraseña.equals(userManager.listaDeUsuarios.get(i).cuil)){
                usuarioActivo = userManager.listaDeUsuarios.get(i);
                menuDeUsuario(userManager);
            } else {
                inicio(userManager, anses, usuarioActivo);
            }
        }
    }

    static void crearNuevoUsuario(UserManager userManager, ArrayList<String> anses){
        String cuilOCelular = Scanner.getString("Ingrese su numero de telefono o cuil: ");
        for (int i = 0; i < anses.size(); i++) {
            //validar en el anses
        }
    }

    static void menuDeUsuario(UserManager userManager, ArrayList<String> anses, Usuario usuarioActivo){
        System.out.println("1. Declarar contacto estrecho");
        System.out.println("2. Chequear solicitudes");
        System.out.println("3. Declarar sintoma");
        System.out.println("4. Eliminar sitnoma");
        System.out.println("5. Cerrar sesion");

        int n = Scanner.getInt("");

        switch (n){
            case 1:
                String cuilOCelular = Scanner.getString("Ingrese el cuil o celular de la persona con la que tuvo contacto estrecho: ");
                int fecha1 = Scanner.getInt("Ingrese el primer dia de su encuentro: ");
                int fecha2 = Scanner.getInt("Ingrese el ultimo dia de su encuentro: ");
                Usuario otroUsuario = buscarUsuario(cuilOCelular);
                if (otroUsuario == null){
                    System.out.println("No se encontro el usuario.");
                    menuDeUsuario(userManager, anses, usuarioActivo);
                }

                break;
            case 2:
                entrarComoUsuario(userManager);
                break;
            case 3:
                crearNuevoUsuario(userManager, anses);
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Comando invalido, pruebe de nuevo.");
                menuDeUsuario();
                break;
        }
    }

    Usuario buscarUsuario(String cuilOCelular, UserManager userManager){
        for (int i = 0; i < userManager.listaDeUsuarios.size(); i++) {
            if (cuilOCelular.equals(userManager.listaDeUsuarios.get(i).cuil) || cuilOCelular.equals(userManager.listaDeUsuarios.get(i).celular)){
                return userManager.listaDeUsuarios.get(i);
            }
        } return null;
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
