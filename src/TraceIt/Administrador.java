package TraceIt;

import util.Scanner;


public class Administrador {
    String usuario;
    String contraseña;


    public Administrador(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    void desbloquearUsuario(Usuario usuario){
        usuario.solicitudesRechazadas = 0;
        usuario.estaBloqueado = false;
    }

    void bloquearUsuario(Usuario usuario){
        usuario.estaBloqueado = true;
    }

    void crearSintoma(String nombre, FileManager enfermedadesReader) {
        Sintoma sintomaNuevo = new Sintoma(nombre);
        int cantidadDeEnfermedades = Scanner.getInt("A cuantas enfermedades pertenece?: ");
        for (int i = 0; i < cantidadDeEnfermedades; i++) {
            String nombreEnfermedad = Scanner.getString("Introduzca el nombre de la enfermedad: ");
            Enfermedad enfermedad = Main.buscarEnfermedad(nombreEnfermedad);
            if (enfermedad != null) {
                enfermedad.sintomas.add(sintomaNuevo);
                sintomaNuevo.enfermedadesRelacionadas.add(enfermedad);
                enfermedadesReader.writeEnfermedadesToFile();
            }
        }
    }
}
