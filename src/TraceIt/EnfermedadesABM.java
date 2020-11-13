package TraceIt;
import java.util.ArrayList;

public class EnfermedadesABM {
    static ArrayList<Enfermedad> listaDeEnfermedades= new ArrayList<>();
    static ArrayList<Sintoma> listaDeSintomas= new ArrayList<>();
    static ArrayList<Brote> listaDeBrotes= new ArrayList<>();


    public static ArrayList<Usuario> contactosEstrechosEn48HorasConLaMismaEnfermedad(Usuario usuario) {
        ArrayList<Usuario> contactosEstrechosConLaMismaEnfermedad = new ArrayList<>(); //segundo nivel
        for (Usuario otroUsuario : usuario.getContactosEstrechos().keySet()) {
            if (usuario.contactosEstrechos.get(otroUsuario).add48Hours().after(usuario.getFechaDeEnfermedad()) || usuario.contactosEstrechos.get(otroUsuario).equals(usuario.getFechaDeEnfermedad())) {
                if (usuario.getEnfermedadActual().equals(otroUsuario.getEnfermedadActual())) {
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

        } while (usuariosDelNivelSuperior.size() == 0);

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

    static public ArrayList<Brote> sortBrotes (){
        for (int i = 0; i < listaDeBrotes.size() ; i++) {
            for (int j = 1; j < listaDeBrotes.size(); j++) {
                if(listaDeBrotes.get(j).sizeDelBrote() > listaDeBrotes.get(j-1).sizeDelBrote()){
                    Brote broteMayor = listaDeBrotes.get(j);
                    listaDeBrotes.set(j,listaDeBrotes.get(j-1));
                    listaDeBrotes.set(j-1, broteMayor);
                }
            }
        } return listaDeBrotes;
    }

    static public void llenarListaEnfermedades(ArrayList<String[]> enfermedades){
        for (int i = 1; i < enfermedades.size(); i++) {
            ArrayList<Sintoma> sintomas = new ArrayList<>();
            for (int j = 1; j <enfermedades.get(i).length ; j++) {
                sintomas.add(new Sintoma(enfermedades.get(i)[j]));
            }
            Enfermedad enfermedad = new Enfermedad(enfermedades.get(i)[0], sintomas);
            listaDeEnfermedades.add(enfermedad);
        }
        llenarListaDeSintomas();
    }
    private static void llenarListaDeSintomas(){
        for (int i = 1; i < listaDeEnfermedades.size(); i++) {
            for (int j = 0; j < listaDeEnfermedades.get(i).sintomas.size(); j++) {
                if(!listaDeSintomas.contains(listaDeEnfermedades.get(i).sintomas.get(j))){
                    listaDeSintomas.add(listaDeEnfermedades.get(i).sintomas.get(j));
                }
            }
        }
    }
}
