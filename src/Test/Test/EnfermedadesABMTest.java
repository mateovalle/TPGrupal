package Test;

import TraceIt.*;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class EnfermedadesABMTest {

    FileManager ansesReader;
}
    /*
    FileManager userReader ;
    FileManager adminReader;
    FileManager solicitudReader;
    ArrayList<String> anses;
    UserManager userManager;
    EnfermedadesABM enfermedadesABM;
    @Before
    public void setUp() throws IOException {
        FileManager ansesReader = new FileManager("src/TraceIt/ANSES");
        FileManager userReader = new FileManager("src/TraceIt/Users");
        FileManager adminReader = new FileManager("src/TraceIt/Administradores");
        FileManager solicitudReader = new FileManager("src/TraceIt/Solicitudes");
        ArrayList<String> anses = new ArrayList<>();
        UserManager userManager = new UserManager();
        userManager.agregarUsuariosALista(userReader.getDataFromFile());
        userManager.agregarAdminALista(adminReader.getDataFromFile());
        userManager.agregarSolictudALista(solicitudReader.getDataFromFile());
        userManager.repartirSolicitudes();
        enfermedadesABM= new EnfermedadesABM();

    }

    @Test
    public void contactosEstrechosEn48HorasConLaMismaEnfermedad() {
        Usuario usuario1= userManager.getListaDeUsuarios().get(0);
        Usuario usuario2= userManager.getListaDeUsuarios().get(1);


    }

    @Test
    public void chequearQueExisteBrote() {
    }

    @Test
    public void sortEnfermedades() {
        LinkedHashMap<Enfermedad, Integer> rankingEnfermedades= new LinkedHashMap<>();
        ArrayList<Sintoma> sintomasE1= new ArrayList<>();
        ArrayList<Sintoma> sintomaE2= new ArrayList();
        Enfermedad enfermedad1= new Enfermedad("enfermedad1", sintomasE1);
        Enfermedad enfermedad2= new Enfermedad("enfermedad1", sintomasE1);
        rankingEnfermedades.put(enfermedad1, 10);
        rankingEnfermedades.put(enfermedad2, 20);

        LinkedHashMap<Enfermedad, Integer> rankingSortedoManualmente= new LinkedHashMap<>();
        rankingSortedoManualmente.put(enfermedad2, 20);
        rankingSortedoManualmente.put(enfermedad1, 10);
        rankingEnfermedades= EnfermedadesABM.sortRankingDeEnfermedades(rankingEnfermedades);

        Assert.assertTrue(rankingSortedoManualmente.equals(rankingEnfermedades));


    }

}

     */