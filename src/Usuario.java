import java.util.ArrayList;
import java.util.HashMap;

public class Usuario {
    String cuil;
    String celular;
    String zona;
    String nombre;
    int solicitudesRechazadas;
    boolean estaBloqueado = false;
    Enfermedad enfermedadActual = null;
    HashMap<Sintoma, Date> sintomas;
    ArrayList <Advertencia> advertencias ;
    HashMap<Usuario, Date> contactosEstrechos;
    ArrayList<Solicitud> solicitudesRecibidas;

    public Usuario(String cuil, String celular, String zona, String nombre, int solicitudesRechazadas) {
        this.cuil = cuil;
        this.celular = celular;
        this.zona = zona;
        this.nombre = nombre;
        this.solicitudesRechazadas = solicitudesRechazadas;
        this.estaBloqueado = false;
        this.enfermedadActual = null;
        this.sintomas = new HashMap<>();
        this.advertencias = new ArrayList<>();
        this.contactosEstrechos = new HashMap<>();
        this.solicitudesRecibidas = new ArrayList<>();
    }

    public void declararContactoEstrecho(Solicitud solicitud,  UserManager userManager){
        if(estaBloqueado){
            // esta bloqueado y no puede declarar. --Timo
        }else{
            userManager.mandarSolicitud(solicitud);
        }
    }
    public void contestarSolicitud(Solicitud solicitud, UserManager userManager, boolean respuesta){
        Usuario envia = solicitud.envia;
        Usuario recibe = solicitud.recibe;
        if (respuesta){
            recibe.contactosEstrechos.put(envia, solicitud.fecha2);
            envia.contactosEstrechos.put(recibe, solicitud.fecha2);
            if (envia.solicitudesRechazadas > 0 && envia.solicitudesRechazadas < 5){
                envia.solicitudesRechazadas--;
            }
        } else {
            envia.solicitudesRechazadas++;
            if (envia.solicitudesRechazadas > 4){
                envia.estaBloqueado = true;
            }
        }
        userManager.solicitudes.remove(solicitud);
    }

    public void declararSintoma (Date fechaDeIngresoDeSintoma, String nombreSintoma, UserManager userManager){ // falta persistencia
        Sintoma sintomaADeclarar = Main.buscarSintoma(nombreSintoma);
        if (sintomaADeclarar != null) {
            Sintoma nuevoSintoma = new Sintoma(nombreSintoma);
            sintomas.put(nuevoSintoma, fechaDeIngresoDeSintoma);
            if (declaraMasDeDosSintomasEnUnDia(nuevoSintoma, fechaDeIngresoDeSintoma)) {
                userManager.mandarAdvertencia(encuentrosEnLas48h(nuevoSintoma),fechaDeIngresoDeSintoma, this);
                // recorre el arraylist de contacos estrechos, se fija si la date del encuentro esta en las 48 de declaracion de sintoma --> mandar advertencia
            }
            Enfermedad enfermedad = DetectorDeEnfermedades.detectarEnfermedad(this);
            this.enfermedadActual = enfermedad;

            if (enfermedad != null){
                ArrayList<Usuario> usuariosContagiados = userManager.usuariosContagiados(enfermedad, encuentrosEnLas48h(nuevoSintoma));
                EnfermedadesABM.chequearQueExisteBrote(usuariosContagiados, enfermedad);
            }
        } else {
            System.out.println("Ese sintomas no existe");
            declararSintoma(fechaDeIngresoDeSintoma, nombreSintoma, userManager);
        }
    }

    public void darDeBajaSintoma (Sintoma sintoma) throws IllegalArgumentException{
        if (sintomas.containsKey(sintoma)) {
            sintomas.remove(sintoma);
        } else {
            throw new IllegalArgumentException("No se encontro ese sintoma en tu lista de sintomas actuales.");
        }
    }


    private boolean declaraMasDeDosSintomasEnUnDia (Sintoma sintoma, Date fecha) {
        int sintomasConLaMismaFecha = 0;
        for (Sintoma sintomasActuales : sintomas.keySet()){
            if (sintomas.get(sintomasActuales).equals(fecha)){
                sintomasConLaMismaFecha++;
            }
        }
        if (sintomasConLaMismaFecha == 2){
            return true;
        } else {
            return false;
        }
    }

    private ArrayList<Usuario> encuentrosEnLas48h (Sintoma sintoma) { // que cree un array de solicitudes con las que estuvo las 48 horas antes y despues de declarar un sintoma
        Date fecha = sintomas.get(sintoma);
        ArrayList <Usuario> encuentrosEnLas48h = new ArrayList<>(); // crea una nueva porque depende de cada sintoma este arraylist
        for (Usuario usuario : contactosEstrechos.keySet()) {
            if (contactosEstrechos.get(usuario).equals(fecha) || contactosEstrechos.get(usuario).add48Hours().before(fecha) || fecha.add48Hours().before(contactosEstrechos.get(usuario))) {
                encuentrosEnLas48h.add(usuario);
            }
        } return encuentrosEnLas48h;
    }
}
