package TraceIt;

public class Date {
    int dia;
    int mes;
    int año;

    public Date(int dia, int mes, int año){
        this.dia = dia;
        this.año = año;
        this.mes = mes;
    }

    public Date(String fecha){ //para construir una fecha a partir de un string que sea "DD/MM/AA"
        String[] separado = fecha.split("/");
        int dia;
        int mes;
        int año;
        try {
            dia = Integer.parseInt(separado[0]);
            mes = Integer.parseInt(separado[1]);
            año = Integer.parseInt(separado[2]);
        } catch (NumberFormatException e){
            dia = 0;
            mes = 0;
            año = 0;
        }
        this.dia = dia;
        this.año = año;
        this.mes = mes;
    }


    //compara dos fechas: devuelve -1 si esta es anterior al parametro, 0 si son el mismo dia, y 1 si esta es posterior al parametro.
    public int compareTo(Date otraFecha){
        if ((this.año == otraFecha.año) && (this.mes == otraFecha.mes) && (this.dia == otraFecha.dia)){
            return 0;
        }
        if (this.año > otraFecha.año){
            return 1;
        }
        if (this.año < otraFecha.año){
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

    @Override
    public String toString() {
        return dia+"/"+mes+"/"+año;
    }


    public Date add48Hours (){ // agrega dos dias
        // agrega 2 dias a una fecha --> podria achicar codigo haciendo metodos para los meses que tienen cosas en comun
            if (this.mes == 12) { // diciembre --> sumar año si estoy a fin de mes
                if (this.dia == 30) {
                    return new Date(1, 1, this.año + 1);
                } else if (this.dia == 31) {
                    return new Date(2, 1, this.año + 1);
                } else {
                    return new Date (this.dia+2,this.mes, this.año);
                }
            } else if (this.mes == 1 || this.mes == 3 || this.mes == 5 || this.mes == 7 || this.mes == 8 || this.mes == 10){ // meses con 31 dias al mes
                if (this.dia == 30) {
                    return new Date (1,this.mes+1, this.año);
                } else if (this.dia == 31) {
                    return new Date (2,this.mes+1, this.año);
                } else {
                    return new Date (this.dia+2,this.mes, this.año);
                }
            } else if (this.mes == 2) { // febrero --> probar si es bisiesto o no
                if (añobisiesto(this.año)){
                    if (this.dia == 28){
                        return new Date (1,this.mes+1, this.año);
                    } else if (this.dia == 29){
                        return new Date (2,this.mes+1, this.año);
                    } else {
                        return new Date (this.dia+2,this.mes, this.año);
                    }
                } else {
                    if (this.dia == 27){
                        return new Date (1,this.mes+1, this.año);
                    } else if (this.dia == 28) {
                        return new Date (2,this.mes+1, this.año);
                    } else {
                        return new Date (this.dia+2,this.mes, this.año);
                    }
                }
            } else { // meses con 30 dias al mes (this.mes == 4  || this.mes == 6 || this.mes == 9 || this.mes == 11)
                if (this.dia == 29){
                    return new Date (1,this.mes+1, this.año);
                } else if (this.dia == 30) {
                    return new Date (2,this.mes+1, this.año);
                } else {
                    return new Date (this.dia+2,this.mes, this.año);
                }
            }
        }
        private boolean añobisiesto(int año) {
            if ((año % 4 == 0) && ((año % 100 != 0) || (año % 400 == 0))){
                return true;
            }
            else {
                return false;
            }
        }
    }

