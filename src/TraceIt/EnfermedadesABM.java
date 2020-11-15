package TraceIt;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Map.Entry.comparingByValue;

public class EnfermedadesABM {
    static ArrayList<Enfermedad> listaDeEnfermedades= new ArrayList<>();
    static ArrayList<Sintoma> listaDeSintomas= new ArrayList<>();
    static ArrayList<Brote> listaDeBrotes= new ArrayList<>();

    public static ArrayList<Enfermedad> getListaDeEnfermedades() {
        return listaDeEnfermedades;
    }

    public static ArrayList<Sintoma> getListaDeSintomas() {
        return listaDeSintomas;
    }

    public static ArrayList<Brote> getListaDeBrotes() {
        return listaDeBrotes;
    }

    public static ArrayList<Usuario> contactosEstrechosEn48HorasConLaMismaEnfermedad(Usuario usuario) {
        ArrayList<Usuario> contactosEstrechosConLaMismaEnfermedad = new ArrayList<>(); //segundo nivel
        for (Usuario otroUsuario : usuario.getContactosEstrechos().keySet()) {
            if (usuario.contactosEstrechos.get(otroUsuario).add48Hours().after(usuario.getFechaDeEnfermedad()) || usuario.contactosEstrechos.get(otroUsuario).equals(usuario.getFechaDeEnfermedad())) {
                if (usuario.getEnfermedadActual().equals(otroUsuario.getEnfermedadActual()) && usuario.getZona().equals(otroUsuario.getZona())) {
                    contactosEstrechosConLaMismaEnfermedad.add(otroUsuario);
                }
            }
        }
        return contactosEstrechosConLaMismaEnfermedad;
    }

    static public void chequearQueExisteBrote (Usuario usuario){
        //hacer un arraylist con el usuario
        //recorrerla, y agregar todos los contactosEstrechosEn48HorasConLaMisma enfermedad pero solo si no estan todavia en el arraylist
        //recorrerlo hasta que no se encuentren mas contactos estrechosen48horas
        //tener un contador con la cantidad de niveles de contagios

        int nivelesDeContagio = 0;
        ArrayList<Usuario> usuariosDelBrote = new ArrayList<>();
        usuariosDelBrote.add(usuario);
        ArrayList<Usuario> usuariosDelNivelSuperior = new ArrayList<>();

        do {
            usuariosDelNivelSuperior.clear();
            for (int i = 0; i < usuariosDelBrote.size(); i++) {
                ArrayList<Usuario> contactosEstrechosEn48HorasConLaMismaEnfermedad = contactosEstrechosEn48HorasConLaMismaEnfermedad(usuariosDelBrote.get(i));
                for (int j = 0; j < contactosEstrechosEn48HorasConLaMismaEnfermedad.size(); j++) {
                    if (!usuariosDelBrote.contains(contactosEstrechosEn48HorasConLaMismaEnfermedad.get(j)) && !usuariosDelNivelSuperior.contains(contactosEstrechosEn48HorasConLaMismaEnfermedad.get(j))){
                       usuariosDelNivelSuperior.add(contactosEstrechosEn48HorasConLaMismaEnfermedad.get(j));
                    }
                }
            }
            usuariosDelBrote.addAll(usuariosDelNivelSuperior);
            if (!usuariosDelNivelSuperior.isEmpty()){
                nivelesDeContagio++;
            }

        } while (usuariosDelNivelSuperior.size() > 0);

        ArrayList<Usuario> broteSinElUsuario = new ArrayList<>(); //para ver si este usuario se esta sumando a un brote ya existente o si hay que crear otro
        broteSinElUsuario.addAll(usuariosDelBrote);
        broteSinElUsuario.remove(usuario);
        for (int i = 0; i < listaDeBrotes.size(); i++) {
            if (listaDeBrotes.get(i).getUsuariosContagiados().containsAll(broteSinElUsuario)){
                if( listaDeBrotes.get(i).getZona().equals(usuario.getZona())){
                    listaDeBrotes.get(i).getUsuariosContagiados().add(usuario);
                    return;
                }
            }
        }

        if (usuariosDelBrote.size() >= 5 && nivelesDeContagio >= 2){
            Brote nuevoBrote = new Brote(usuariosDelBrote, usuario.getEnfermedadActual(),usuario.getZona());
            listaDeBrotes.add(nuevoBrote);
        }
    }

    static public ArrayList<Brote> sortBrotes (ArrayList<Brote> brotes){
        for (int i = 0; i < brotes.size() ; i++) {
            for (int j = 1; j < brotes.size(); j++) {
                if(brotes.get(j).sizeDelBrote() > brotes.get(j-1).sizeDelBrote()){
                    Brote broteMayor = brotes.get(j);
                    brotes.set(j,brotes.get(j-1));
                    brotes.set(j-1, broteMayor);
                }
            }
        } return brotes;
    }

    static public void llenarListaEnfermedades(ArrayList<String[]> enfermedades){
        for (int i = 1; i < enfermedades.size(); i++) {
            ArrayList<Sintoma> sintomas = new ArrayList<>();
            for (int j = 1; j <enfermedades.get(i).length ; j++) {
                String[] sintomasDeEnfemedad =enfermedades.get(i)[1].split(";");
                for (int k = 0; k < sintomasDeEnfemedad.length; k++) {
                    Sintoma sintoma = new Sintoma(sintomasDeEnfemedad[k]);
                    for (int l = 0; l < listaDeEnfermedades.size(); l++) {
                        for (int m = 0; m < listaDeEnfermedades.get(l).sintomas.size(); m++) {
                            if(sintoma.getNombre().equals(listaDeEnfermedades.get(l).sintomas.get(m).getNombre())){
                                sintoma = listaDeEnfermedades.get(l).sintomas.get(m);
                            }
                        }
                    }
                    if(!sintomas.contains(sintoma)){
                        sintomas.add(sintoma);
                    }
                }
            }
            Enfermedad enfermedad = new Enfermedad(enfermedades.get(i)[0], sintomas);
            listaDeEnfermedades.add(enfermedad);
        }
        llenarListaDeSintomas();
    }
    private static void llenarListaDeSintomas(){
        for (int i = 0; i < listaDeEnfermedades.size(); i++) {
            for (int j = 0; j < listaDeEnfermedades.get(i).sintomas.size(); j++) {
                if(!listaDeSintomas.contains(listaDeEnfermedades.get(i).sintomas.get(j))){
                    listaDeSintomas.add(listaDeEnfermedades.get(i).sintomas.get(j));
                }
            }
        }
    }

    static ArrayList<Brote> actualizarBrotesActivos(){ //Un brote se desactiva cuando ninguna persona del brote sigue teniendo esa enfermedad
        for (int i = 0; i < listaDeBrotes.size(); i++) {
            int count = 0;
            for (Usuario usuario : listaDeBrotes.get(i).getUsuariosContagiados()){
                if(!usuario.enfermedadActual.equals(listaDeBrotes.get(i).getEnfermedad())){
                    count++;
                }
            }
            if(count == listaDeBrotes.get(i).getUsuariosContagiados().size()){
                listaDeBrotes.get(i).desactivarBrote();
            }
        }
        ArrayList<Brote> brotesActivos = new ArrayList<>();
        for (int i = 0; i < listaDeBrotes.size(); i++) {
            if(listaDeBrotes.get(i).isActivo()){
                brotesActivos.add(listaDeBrotes.get(i));
            }
        }
        return brotesActivos;
    }

    static void llenarListaDeBrotes(ArrayList<String[]> infoBrotes, UserManager userManager){
        for (int i = 1; i < infoBrotes.size(); i++) {
            Enfermedad enfermedad = Main.buscarEnfermedad(infoBrotes.get(i)[0]);
            String zona = infoBrotes.get(i)[1];
            ArrayList<Usuario> usuariosEnBrote=new ArrayList<>();
            for (int j = 2; j < infoBrotes.get(i).length; j++) {
                Usuario unUsuario = Main.buscarUsuario(infoBrotes.get(i)[j],userManager);
                usuariosEnBrote.add(unUsuario);
            }
            Brote brote = new Brote(usuariosEnBrote,enfermedad,zona);
            listaDeBrotes.add(brote);
        }
    }
    static public HashMap<Enfermedad, Integer> enfermedadesPorZona(String zona, UserManager usermanager) {
        ArrayList<Usuario> usuariosEnZona = new ArrayList<>();
        HashMap<Enfermedad, Integer> enfermedadesEnZona = new HashMap<>();
        for (int i = 0; i < usermanager.getListaDeUsuarios().size(); i++) {
            if (usermanager.getListaDeUsuarios().get(i).getZona().equals(zona)) {
                usuariosEnZona.add(usermanager.getListaDeUsuarios().get(i));
            }
        }

        for (int i = 0; i < listaDeEnfermedades.size(); i++) {
            enfermedadesEnZona.put(listaDeEnfermedades.get(i), 0);
        }
        for (int i = 0; i < usuariosEnZona.size(); i++) {
            if (usuariosEnZona.get(i).enfermedadActual != null) {
                enfermedadesEnZona.replace(usuariosEnZona.get(i).enfermedadActual, enfermedadesEnZona.get(usuariosEnZona.get(i).enfermedadActual) + 1);
            }
        }
        HashMap<Enfermedad, Integer> toReturn = new HashMap<>();
        while (toReturn.size()<3 || enfermedadesEnZona.size()==0 ){
            Integer maxValue = 0;
            for (Enfermedad enfermedad : enfermedadesEnZona.keySet()) {
                if (enfermedadesEnZona.get(enfermedad) >= maxValue) {
                    maxValue = enfermedadesEnZona.get(enfermedad);
                }
            }
            for (Enfermedad enfermedad : enfermedadesEnZona.keySet()) {
                if (enfermedadesEnZona.get(enfermedad) == maxValue) {
                    toReturn.put(enfermedad, enfermedadesEnZona.get(enfermedad));
                    enfermedadesEnZona.remove(enfermedad);
                }
            }
        }
        return toReturn;
    }
}
