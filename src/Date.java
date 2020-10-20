public class Date {
    int dia;
    int mes;
    int anio;

    public Date(int dia, int mes, int anio){
        this.dia = dia;
        this.anio = anio;
        this.mes = mes;
    }

    public Date(String fecha){ //para construir una fecha a partir de un string que sea "DD/MM/AA"
        String[] separado = fecha.split("/");
        int dia;
        int mes;
        int anio;
        try {
            dia = Integer.parseInt(separado[0]);
            mes = Integer.parseInt(separado[1]);
            anio = Integer.parseInt(separado[2]);
        } catch (NumberFormatException e){
            dia = 0;
            mes = 0;
            anio = 0;
        }
        this.dia = dia;
        this.anio = anio;
        this.mes = mes;
    }


    //compara dos fechas: devuelve -1 si esta es anterior al parametro, 0 si son el mismo dia, y 1 si esta es posterior al parametro.
    public int compareTo(Date otraFecha){
        if ((this.anio == otraFecha.anio) && (this.mes == otraFecha.mes) && (this.dia == otraFecha.dia)){
            return 0;
        }
        if (this.anio > otraFecha.anio){
            return 1;
        }
        if (this.anio < otraFecha.anio){
            return -1;
        }
        if (this.mes > otraFecha.mes){
            return 1;
        }
        if (this.mes < otraFecha.mes){
            return -1;
        }
        if (this.dia > otraFecha.dia){
            return 1;
        }
        return -1;
    }

    public boolean before(Date otraFecha){
        return this.compareTo(otraFecha) < 0;
    }

    public boolean after(Date otraFecha){
        return this.compareTo(otraFecha) > 0;
    }

    public boolean equals(Date otrafecha){
        return this.compareTo(otrafecha) == 0;
    }
}
