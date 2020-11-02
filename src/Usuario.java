import java.util.ArrayList;
import java.util.HashMap;

public class Usuario {
    String cuil;
    String celular;
    String zona;
    String nombre;
    int solicitudesRechazadas;
    boolean estaBloqueado = false;
    HashMap<Sintoma, Date> sintomas= new HashMap<>();
    ArrayList <Advertencia> advertencias = new ArrayList<>();
    ArrayList<Usuario> contactosEstrechos = new ArrayList<>();
    ArrayList<Solicitud> solicitudesRecibidas = new ArrayList<>();

    public Usuario(String cuil, String celular, String zona,String nombre, int solicitudesRechazadas) {
        this.cuil = cuil;
        this.celular = celular;
        this.zona=zona;
        this.nombre = nombre;
        this.solicitudesRechazadas=solicitudesRechazadas;
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
            recibe.contactosEstrechos.add(envia);
            envia.contactosEstrechos.add(recibe);
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
            if (declaraMasDeDosSintomasEnUnDia(nuevoSintoma)) {
                ArrayList <Usuario> usuariosConLosQueEstuvo = usuariosConLosQueEstuvoEnLas48h(solicitudesEnLas48h(nuevoSintoma));
                userManager.mandarAdvertencia(usuariosConLosQueEstuvo,fechaDeIngresoDeSintoma, this);
                // recorre el arraylist de contacos estrechos, se fija si la date del encuentro esta en las 48 de declaracion de sintoma --> mandar advertencia
                ArrayList<Usuario> usuariosContagiados = userManager.usuariosContagiados(sintomaADeclarar, usuariosConLosQueEstuvo);
                EnfermedadesABM.chequearQueExisteBrote(usuariosContagiados, nuevoSintoma);
            }
        } else {
            System.out.println("Ese sintomas no existe");
            declararSintoma(fechaDeIngresoDeSintoma, nombreSintoma, userManager);
        }
    }

    public void darDeBajaSintoma (Sintoma sintoma){
        for (int i = 0; i < sintomasActivos.size(); i++) {
            if (sintomasActivos.get(i).getNombreDelSintoma().equals(sintoma.nombre)) {
                sintomasActivos.remove(sintomasActivos.get(i));
            }
        }
    }


    public boolean declaraMasDeDosSintomasEnUnDia (Sintoma ultimoSintoma) {
        SintomaActivo anteUltimoSintoma = sintomas.(sintomas.size()-3); // anteultimo sintoma
        SintomaActivo antePenultimoSintoma = sintomasActivos.get(sintomasActivos.size() - 3); // comprobar --> quiero el antepenultimo de la lista porque acabo de agregar uno
        if (ultimoSintoma.getFechaDeIngresoDeSintoma() == anteUltimoSintoma.getFechaDeIngresoDeSintoma()) {
            // quiero que sean diferentes porque si es igual ya le mande una notificacion antes
            return antePenultimoSintoma.getFechaDeIngresoDeSintoma() != ultimoSintoma.getFechaDeIngresoDeSintoma();
        } return false;
    }

    public ArrayList<Solicitud> solicitudesEnLas48h (SintomaActivo sintomaActivo) { // que cree un array de solicitudes con las que estuvo las 48 horas antes y despues de declarar un sintoma
        ArrayList <Solicitud> solicitudesEnLas48h = new ArrayList<>(); // crea una nueva porque depende de cada sintoma este arraylist
        for (int i = 0; i < solicitudesRecibidas.size(); i++) {
            if (solicitudesRecibidas.get(i).fecha2.equals(sintomaActivo.getFechaDeIngresoDeSintoma()) || solicitudesRecibidas.get(i).fecha2.add48Hours().before(sintomaActivo.getFechaDeIngresoDeSintoma()) || sintomaActivo.getFechaDeIngresoDeSintoma().add48Hours().before(solicitudesRecibidas.get(i).fecha2)) {
                solicitudesEnLas48h.add(solicitudesRecibidas.get(i));
            }
        } return solicitudesEnLas48h;
    }

    public ArrayList <Usuario> usuariosConLosQueEstuvoEnLas48h (ArrayList <Solicitud> solicitudesEnLas48h){ //recorre el array de solicitudes en las ultimas 48 y me da el array de usuarios con el que estuve
        ArrayList <Usuario> usuariosConLosQueEstuvoEnLas48h = new ArrayList<>();
        for (int i = 0; i < solicitudesEnLas48h.size(); i++) {
            if (solicitudesEnLas48h.get(i).recibe != this){
                usuariosConLosQueEstuvoEnLas48h.add(solicitudesEnLas48h.get(i).recibe);
            } if (solicitudesEnLas48h.get(i).envia != this){
                usuariosConLosQueEstuvoEnLas48h.add(solicitudesEnLas48h.get(i).envia);
            }
        } return usuariosConLosQueEstuvoEnLas48h;
    }

}
