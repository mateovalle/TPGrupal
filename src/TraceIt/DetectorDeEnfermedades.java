package TraceIt;

public class DetectorDeEnfermedades {
    EnfermedadesABM enfermedadesABM;

    public DetectorDeEnfermedades(EnfermedadesABM enfermedadesABM) {
        this.enfermedadesABM = enfermedadesABM;
    }

    public static Enfermedad detectarEnfermedad(Usuario usuario){
        for (int i = 0; i < EnfermedadesABM.listaDeEnfermedades.size(); i++) {
            int sintomasQueCoinciden = 0;
            for (Sintoma sintoma : usuario.sintomas.keySet()) {
                for (int j = 0; j < EnfermedadesABM.listaDeEnfermedades.get(i).sintomas.size(); j++) {
                    if (sintoma.equals(EnfermedadesABM.listaDeEnfermedades.get(i).sintomas.get(j))){
                        sintomasQueCoinciden++;
                    }
                }
            }
            if (sintomasQueCoinciden > EnfermedadesABM.listaDeEnfermedades.get(i).sintomas.size()/2){
                return EnfermedadesABM.listaDeEnfermedades.get(i);
            }
        }
        return null;
    }
}
