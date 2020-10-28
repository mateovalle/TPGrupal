import java.util.ArrayList;
import util.Scanner;

public class Main {
    static FileManager ansesReader = new FileManager("src/ANSES");
    static FileManager userReader = new FileManager("src/Users");
    static FileManager adminReader = new FileManager("src/Administradores");
    static FileManager solicitudReader = new FileManager("src/Solicitudes");
    public static void main(String[] args) throws Exception {
        ArrayList<String> anses = new ArrayList<>();
        Administrador administradorActivo = null;
        Usuario usuarioActivo = null;
        UserManager userManager = new UserManager();
        userManager.agregarUsuariosALista(userReader.getDataFromFile());
        userManager.agregarAdminALista(adminReader.getDataFromFile());
        userManager.agregarSolictudALista(solicitudReader.getDataFromFile());
        userManager.repartirSolicitudes();

        //FileReader enfermedadesReader = new FileReader("src/Users");
        inicio(userManager, anses, usuarioActivo, administradorActivo, ansesReader);

    }
    //Menus

    static void inicio(UserManager userManager, ArrayList<String> anses, Usuario usuarioActivo, Administrador administradorActivo, FileManager ansesReader) throws Exception{
        System.out.println("1. Ingresar como administrador");
        System.out.println("2. Ingresar como usuario");
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
                crearNuevoUsuario(userManager, ansesReader,"src/Users");
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Comando invalido, pruebe de nuevo.");
                inicio(userManager, anses, usuarioActivo, administradorActivo, ansesReader);
                break;
        }
    }

    static void menuDeUsuario(UserManager userManager, ArrayList<String> anses, Usuario usuarioActivo, Administrador administradorActivo) {
        System.out.println("1. Declarar contacto estrecho");
        System.out.println("2. Revisar y contestar solicitudes de contacto estrecho");
        System.out.println("3. Declarar sintoma");
        System.out.println("4. Eliminar sintoma");
        System.out.println("5. Ver Mapa");
        System.out.println("6. Cerrar sesion");

        int n = Scanner.getInt("");

        switch (n) {
            case 1:
                //Declarar contacto estrecho
                if(usuarioActivo.estaBloqueado){
                    System.out.println("Estás bloqueado, no puedes mandar solicitudes.");
                    menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                }else {
                    String cuilOCelular = Scanner.getString("Ingrese el cuil o celular de la persona con la que tuvo contacto estrecho: ");
                    Usuario otroUsuario = buscarUsuario(cuilOCelular, userManager);
                    if (otroUsuario == null) {
                        System.out.println("No se encontro el usuario.");
                        menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                    } else {
                        String fecha1 = Scanner.getString("Ingrese el primer dia de su encuentro, separado por /: ");
                        String fecha2 = Scanner.getString("Ingrese el ultimo dia de su encuentro, separado por /: ");
                        Date dateStart = new Date(fecha1);
                        Date dateEnd = new Date(fecha2);
                        Solicitud solicitud = new Solicitud(usuarioActivo, otroUsuario, dateStart, dateEnd);
                        usuarioActivo.declararContactoEstrecho(solicitud, userManager);
                        solicitudReader.writeSolicitudToFile(solicitud,userManager.solicitudes);
                        menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                    }
                }
                break;
            case 2:
                //Revisar y contestar Solicitudes
                for (int i = 0; i < usuarioActivo.solicitudesRecibidas.size(); i++) {
                    System.out.println(usuarioActivo.solicitudesRecibidas.get(i).toString());
                }
                System.out.println("");
                int SioNo = Scanner.getInt("Ingrese 0 si desea contestar a alguna solicitud, o ingrese 1 para volver al menu: ");
                if(SioNo==0) {
                    for (int i = 0; i < usuarioActivo.solicitudesRecibidas.size(); i++) {
                        System.out.println(i+". "+usuarioActivo.solicitudesRecibidas.get(i).toString());
                    }
                    int nroDeSolicitud= Scanner.getInt("Que solicitud desea contestar: ");
                    if(nroDeSolicitud<= usuarioActivo.solicitudesRecibidas.size()){
                        int SioNo2=Scanner.getInt("Ingrese 0 si hubo contacto estrecho, ingrese 1 si no hubo contacto estrecho: ");
                        Solicitud solicitud= usuarioActivo.solicitudesRecibidas.get(nroDeSolicitud);
                        if(SioNo2==0){
                            usuarioActivo.contestarSolicitud(solicitud, userManager, true);
                        }else if(SioNo2== 1){
                            usuarioActivo.contestarSolicitud(solicitud,userManager, false);
                            //userReader.updateUserFromFile(usuarioActivo.solicitudesRecibidas.get(nroDeSolicitud).envia);
                        }else {
                            System.out.println("No ha ingresado un numero valido");
                            menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                        }
                    }else{
                        System.out.println("No ha ingresado un numero valido");
                        menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                    }
                }
                break;
            case 3:
                //Declarar Sintoma
                String nombreSintoma = Scanner.getString("Cual es el nombre del sintoma que desea eliminar");
                Sintoma sintomaADeclarar = buscarSintoma(nombreSintoma);
                if (sintomaADeclarar != null) {
                    usuarioActivo.sintomasActivos.add(sintomaADeclarar);
                    break;
                } else {
                    menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                }

                break;

            case 4:
                //Eliminar Sintoma
                String nombre = Scanner.getString("Cual es el nombre del sintoma que desea eliminar");
                Sintoma sintomaAELiminar = buscarSintoma(nombre);
                if (sintomaAELiminar != null) {
                    eliminarSintoma(sintomaAELiminar, usuarioActivo);
                    break;
                } else {
                    menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                }
            case 5:
                //Ver Mapa

            case 6:
                //Cerrar sesion
                cerrarSesion();
                break;

            default:
                System.out.println("Comando invalido, pruebe de nuevo.");
                menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
                break;
        }
    }

    static void menuDeAdministrador(UserManager userManager, ArrayList<String> anses, Usuario usuarioActivo, Administrador administradorActivo) throws Exception {

        System.out.println("1. Crear evento");
        System.out.println("2. Desbloquear usuario");
        System.out.println("3. Volver al inicio");
        System.out.println("4. Ver Mapa");
        System.out.println("5. Cerrar Sesion");
        int opcion = Scanner.getInt("Introduzca el numero de opción: ");

        switch (opcion) {
            case 1:
                //Crear Evento
                String nombre = Scanner.getString("Introduzca el nombre del sintoma: ");
                Sintoma Sintoma = buscarSintoma(nombre);
                if (Sintoma == null) {
                    crearSintoma(nombre);
                } else {
                    System.out.println("El sintoma creado ya existe.");
                    menuDeAdministrador(userManager, anses, usuarioActivo, administradorActivo);
                }
                break;

            case 2:
                //Desbloquear Usuario
                String cuiloCelular = Scanner.getString("introduzca el Cuil o el Celular del usuario a desbloquear: ");
                Usuario usuarioaDesbloquear = buscarUsuario(cuiloCelular, userManager);
                administradorActivo.desbloquearUsuario(usuarioaDesbloquear);
                break;

            case 3:
                //Volver al Inicio
                inicio(userManager, anses, usuarioActivo, administradorActivo, ansesReader);

            case 4:
                //Ver Mapa

            case 5:
                //Cerrar Sesion
                cerrarSesion();
            default:
                System.out.println("El valor ingresado no corresponde a ninguna opción, intente nuevamente");
                menuDeAdministrador(userManager, anses, usuarioActivo, administradorActivo);

        }
    }

    //Ingresar Como

    static void entrarComoAdministrador(UserManager userManager, ArrayList<String> anses, Usuario
            usuarioActivo, Administrador administradorActivo) throws Exception {
        String usuario = Scanner.getString("Ingrese su nombre de usuario: ");
        String contraseña = Scanner.getString("Ingrese su contraseña: ");
        for (int i = 0; i < userManager.listaDeAdministradores.size(); i++) {
            if (usuario.equals(userManager.listaDeAdministradores.get(i).usuario)) {
                if (contraseña.equals(userManager.listaDeAdministradores.get(i).contraseña)) {
                    administradorActivo = buscarAdministrador(usuario, contraseña, userManager);
                    menuDeAdministrador(userManager, anses, usuarioActivo, administradorActivo);
                }
            }
        }
        System.out.println("Usuario o contraseña incorrecta.");
        inicio(userManager, anses, usuarioActivo, administradorActivo, ansesReader);

    }

    static void entrarComoUsuario(UserManager userManager, ArrayList<String> anses, Usuario usuarioActivo, Administrador administradorActivo) throws Exception {
        String cuilOContraseña = Scanner.getString("Ingrese su cuil o celular");
        for (int i = 0; i < userManager.listaDeUsuarios.size(); i++) {
            if (cuilOContraseña.equals(userManager.listaDeUsuarios.get(i).celular) || cuilOContraseña.equals(userManager.listaDeUsuarios.get(i).cuil)) {
                usuarioActivo = userManager.listaDeUsuarios.get(i);
                menuDeUsuario(userManager, anses, usuarioActivo, administradorActivo);
            }
        }
        inicio(userManager, anses, usuarioActivo, administradorActivo, ansesReader);
    }

    //Creadores

    static void crearNuevoUsuario(UserManager userManager, FileManager ansesReader, String filePath) throws Exception{
        String cuilOCelular = Scanner.getString("Ingrese su numero de telefono o cuil: ");
        if(buscarUsuario(cuilOCelular,userManager)!=null){
            //inicio(userManager,anses,*****,******);  <-- Completar - Pedro,Timoteo y Mateo
        } else {
            String[] datosAnses = buscarEnAnses(cuilOCelular,ansesReader);
            if(datosAnses != null){
                userManager.crearUsuario(datosAnses[0],datosAnses[1],datosAnses[2],datosAnses[3]);
                ansesReader.writeUsersToFile(datosAnses,filePath);
            }
        }
    }

    static void crearSintoma(String nombre) {
        Sintoma sintomaNuevo = new Sintoma(nombre);
        int perteneceAEnfermedad = Scanner.getInt("A cuantas enfermedades pertenece?: ");
        for (int i = 0; i < perteneceAEnfermedad - 1; i++) {
            String nombreEnfermedad = Scanner.getString("Introduzca el nombre de la enfermedad");
            Enfermedad enfermedad = buscarEnfermedad(nombreEnfermedad);
            if (enfermedad != null) {
                enfermedad.sintomas.add(sintomaNuevo);
                //agregar al archivo
            } else {
                crearSintoma(nombre);
            }
        }
    }

    //Buscadores

    static Usuario buscarUsuario(String cuilOCelular, UserManager userManager) {
        for (int i = 0; i < userManager.listaDeUsuarios.size(); i++) {
            if (cuilOCelular.equals(userManager.listaDeUsuarios.get(i).cuil) || cuilOCelular.equals(userManager.listaDeUsuarios.get(i).celular)) {
                return userManager.listaDeUsuarios.get(i);
            }
        }
        return null;
    }

    static Administrador buscarAdministrador(String usuario, String contraseña, UserManager userManager) {
        for (int i = 0; i < userManager.listaDeAdministradores.size(); i++) {
            if (userManager.listaDeAdministradores.get(i).usuario.equals(usuario) && userManager.listaDeAdministradores.get(i).contraseña.equals(contraseña)) {
                return userManager.listaDeAdministradores.get(i);
            } else {
                //que siga buscando. -Timoteo
            }
        }
        return null;
    }

    static Sintoma buscarSintoma(String nombre) {
        /*for (int i = 0; i < algo.listaDeSintomas.size(); i++) {
            if(algo.listaDeSintomas.get(i).equals(nombre)){
                return algo.listaDeSintomas.get(i);
            }else{
                //que siga buscando. -Timoteo
            }
            }*/
        return null;
    }
    static Enfermedad buscarEnfermedad(String nombre) {
//        for (int i = 0; i < algo.listaDeEnfermedad.size(); i++) {
//            if(algo.listaDeEnfermedad.get(i).equals(nombre)){
//                return algo.listaDeEnfermedad.get(i);
//            }else{
        //que siga buscando. -Timoteo
//            }
//        }
        return null;
   }

   static String[] buscarEnAnses(String cuilOCelular, FileManager ansesReader) throws Exception{
        ArrayList<String[]> lista = ansesReader.getDataFromFile();
       for (int i = 0; i < lista.size(); i++) {
           if(lista.get(i)[0].equals(cuilOCelular) || lista.get(i)[1].equals(cuilOCelular)){
               return lista.get(i);
           }
       }
       return null;
   }

   //Eliminadores

    public static void eliminarSintoma(Sintoma sintoma, Usuario usuarioActivo) {
        usuarioActivo.sintomasActivos.remove(sintoma);
    }

    //Cerrar Sesion
    public static void cerrarSesion(){
        //cerrar sesion guardando
        System.out.println("¡Gracias por usar nuestro programa!");
        System.exit(0);
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
