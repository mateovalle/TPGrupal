package Revisar;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Menu {
    static String redF = "\u001B[31m";
    static String greenF = "\u001B[32m";
    static String resetF = "\u001B[0m";

    public static void main(String[] args){
        System.out.print("\u001b[2J");
        menu();
    }

    static void createUser(){
        Scanner input = new Scanner(System.in);
        System.out.println("Nombre:");
        String name = input.nextLine();
        System.out.println("CUIL:");
        String cuil = input.nextLine();
        if(checkData(cuil, "D:\\Documentos\\github\\TPGrupal\\src\\Revisar\\ANSES")){
            System.out.print("\u001b[2J");
            System.out.println(redF+"CUIL equivocado"+resetF);
            System.out.println(redF+"Intente de nuevo"+resetF);
            menu();
        }
        System.out.println("Cel:");
        String cel = input.nextLine();
        if(checkData(cel, "D:\\Documentos\\github\\TPGrupal\\src\\Revisar\\ANSES")){
            System.out.print("\u001b[2J");
            System.out.println(redF+"Número de celular equivocado"+resetF);
            System.out.println(redF+"Intente de nuevo"+resetF);
            menu();
        }
        System.out.println("Zona:");
        String zona = input.nextLine();     // Acá pude hacer que busque el CUIL y el CEL en los archivos ANSES y Users
        writeData(name, cuil, cel, zona);   // pero está medio rancio, cualquier cosa preguntenme - Pedro
        System.out.print("\u001b[2J");
        System.out.print(greenF+"Datos guardados\n"+resetF);
        menu();
    }

    static boolean checkData(String data, String file){
        int count = 0;
        try{
            String s;
            BufferedReader bReader = new BufferedReader(new FileReader(file));
            while((s=bReader.readLine())!= null){
                for(String word : s.split(",")){
                    if(word.equals(data)){count++;}
                }
            }
        } catch(Exception e){
            System.out.println(e);
        }
        return count == 0;
    }

    static void writeData(String name,String cuil,String cel,String zona){
        try{
            BufferedWriter bWriter = new BufferedWriter(new FileWriter("D:\\Documentos\\github\\TPGrupal\\src\\Revisar\\Users", true)) ;
            bWriter.write("\n" + name + "," + cuil + "," + cel + "," + zona + "," + "0" + ",");
            bWriter.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    static void logIn(){
        Scanner input = new Scanner(System.in);
        System.out.println("CUIL:");
        String cuil = input.nextLine();
        if(checkData(cuil, "D:\\Documentos\\github\\TPGrupal\\src\\Revisar\\Users")){
            System.out.print("\u001b[2J");
            System.out.println(redF+"CUIL equivocado"+resetF);
            System.out.println(redF+"Intente de nuevo"+resetF);
            menu();
        }
        System.out.println("Cel:");
        String cel = input.nextLine();
        if(checkData(cel, "D:\\Documentos\\github\\TPGrupal\\src\\Revisar\\Users")){
            System.out.print("\u001b[2J");
            System.out.println(redF+"Número de celular equivocado"+resetF);
            System.out.println(redF+"Intente de nuevo"+resetF);
            menu();
        }
        System.out.println(greenF+"Ingreso exitoso!"+resetF);
        System.exit(0);
    }

    static void menu(){
        Scanner input = new Scanner(System.in);
        System.out.println("-----------BIENVENIDO----------");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Ingresar");
        System.out.println("2. Registrarse");
        System.out.println("3. Ingresar como Administrador");
        System.out.println("4. Salir");
        System.out.println("-------------------------------");
        int n = input.nextInt();
        switch(n){
            case 1:
                System.out.print("\u001b[2J");
                System.out.println("-----INGRESO-----");
                logIn();
                break;
            case 2:
                System.out.print("\u001b[2J");
                System.out.println("-----REGISTRO-----");
                createUser();
                break;
            case 3:
                System.out.print("\u001b[2J");
                System.out.println("Estamos trabajando en esto");
                menu();
                break;
            case 4:
                System.out.println("Chau!");
                System.exit(0);
                break;
            default:
                System.out.println("Opción no valida");
        }
    }
}