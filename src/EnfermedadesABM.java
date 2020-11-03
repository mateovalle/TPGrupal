import java.util.ArrayList;

public class EnfermedadesABM {
    static ArrayList<Enfermedad> listaDeEnfermedades;
    static ArrayList<Sintoma> listaDeSintomas;
    static ArrayList<Brote> listaDeBrotes;


    //
    static public void chequearQueExisteBrote (ArrayList<Usuario> usuariosContagiados, Enfermedad enfermedad){
        ArrayList <Usuario> usuariosDelBrote = new ArrayList<>();
        for (int i = 0; i < usuariosContagiados.size() ; i++) {
            usuariosDelBrote.add(usuariosContagiados.get(i));
        }
            ArrayList <Usuario> usuariosConLosQueEstuvo = usuariosContagiados.get(i).usuariosConLosQueEstuvoEnLas48h(usuariosContagiados.get(i).solicitudesEnLas48h(sintomaActivo));
            for (int j = 0; j < usuariosConLosQueEstuvo.size(); j++) {
                usuariosDelBrote.add(usuariosConLosQueEstuvo.get(j));
            }
        } if (usuariosDelBrote.size() >= 5 ){
            Brote broteActivo = new Brote (usuariosDelBrote, enfermedad);
            listaDeBrotes.add(broteActivo);
        } else {
            // no pasa nada
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

}
