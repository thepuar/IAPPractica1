/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import ConMQTT.ConsumidorMQTT;
import ConMQTT.ProductorMQTT;
import app.p1.Consumidor;
import app.p1.Productor;
import app.p2.Consumidor2;
import app.p2.Consumidor2JX;
import app.p2.Productor2;
import app.p2.Productor2JX;
import app.p3.ConectorHTTP;
import app.p3.Montador;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

/**
 *
 * @author thepuar
 */
public class Main {

    //java -cp Archivo.jar app.Main
    private final static Logger logger = LoggerFactory.getLogger(Main.class);
    private static Scanner teclado;

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
       // progressBar();
        int valorMenu = opcionMenu();
        
        switch (valorMenu) {
            case 1:
                //Caso practica 1
                opcionMenuP1();
                clearScreen();
                break;
            case 2:
                //Caso practica 2
                opcionMenuP2();
                clearScreen();
                break;
            case 3:
                opcionMenuP3();
                clearScreen();
                break;
            case 4:
                //Prueba con MQTT
                opcionMenuP4();
                clearScreen();
                break;
            case 5:
                opcionMenuP5();
                clearScreen();
                break;
            case 6:
                //Practica 3 - HTTP a webservice del ayuntamiento
                opcionMenuP6();
                clearScreen();
            case 0:
                System.exit(0);
                break;
            default:
               

        }

    }

    public static void clearScreen() {  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
}  
    public static void progressBar(){
        ProgressBar barra = new ProgressBar("Iniciando", 100, ProgressBarStyle.ASCII);
        barra.start();
        for(int i = 0; i<100;i++){
            barra.step();
            try{
            Thread.sleep(50);
            }catch(InterruptedException ie){logger.error("SLEEP EN PROGRESSBAR");}
            
            
        }
        barra.stop();
    }
    public static int opcionMenu() {
        clearScreen();
        System.out.println("##################");
        System.out.println("# Menú Prácticas #");
        System.out.println("##################\n");
        int opcion = -1;
        while (opcion == -1) {
            System.out.println("Elige una opcion:");
            System.out.println("1.- Practica 1.");
            System.out.println("2.- Practica 2.");
            System.out.println("3.- Practica 2 con JSON y XML.");
            System.out.println("4.- Publicador con MQTT");
            System.out.println("5.- Consumidor con MQTT");
            System.out.println("6.- Conector con Ayuntamiento");
            System.out.println("0.- Salir");
            try{
            opcion = teclado.nextInt();
            }catch(InputMismatchException ime){
                logger.error("No has introducido un número");
                opcion = -1;
                teclado.nextLine();
            }
        }
        return opcion;
    }

    public static void opcionMenuP1() {
        clearScreen();
        int valor = -1;
        while (valor == -1) {
            System.out.println("Elige\n1.- Productor\n2.- Consumidor\n0.- Salir");;
            try {
                valor = teclado.nextInt();
                logger.info("Se ha elegido la opción {}", valor);
                teclado.nextLine();
            } catch (InputMismatchException ime) {
                valor = -1;
                teclado.nextLine();
                logger.error("Error en el numero.");
            }
        }
        switch (valor) {
            case 0:
                System.exit(0);
                break;
            case 1:
                Productor productor = new Productor();
                productor.connectar();
                boolean repetir = true;
                while (repetir) {
                    System.out.println("Introduce un texto a enviar:");
                    String cadena = teclado.nextLine();
                    if (!cadena.equals("cerrar")) {
                        productor.sendMessage(cadena);
                    } else {
                        repetir = false;
                        productor.sendMessage("Fin de mensaje.");
                        productor.sendMessage("cerrar");
                        logger.info("Fin de comunicación.");
                        productor.close();
                    }
                }
                break;
            case 2:
                Consumidor consumidor = new Consumidor();
                break;
        }

    }

    public static void opcionMenuP2() {
        clearScreen();
        int valor = -1;
        while (valor == -1) {
            System.out.println("Elige\n1.- Productor\n2.- Consumidor\n0.- Salir");;
            try {
                valor = teclado.nextInt();
                logger.info("Se ha elegido la opción {}", valor);
                teclado.nextLine();
            } catch (InputMismatchException ime) {
                valor = -1;
                teclado.nextLine();
                logger.error("Error en el numero.");
            }
        }
        
         String nomexchange, topic;
        switch (valor) {
            case 0:
                System.exit(0);
                break;
                
            case 1:
               
                nomexchange = getNomcola();
                topic = getTopic();
                
                Productor2 productor = new Productor2(nomexchange);
                productor.connectar();
                boolean repetir = true;
                while (repetir) {
                    System.out.println("Introduce un texto a enviar:");
                    String cadena = teclado.nextLine();
                    if (!cadena.equals("cerrar")) {
                        productor.sendMessage(cadena,topic);
                    } else {
                        repetir = false;
                        productor.sendMessage("Fin de mensaje.",topic);
                        productor.sendMessage("cerrar",topic);
                        logger.info("Fin de comunicación.");
                        productor.close();
                    }
                }
                break;
            case 2:
                Consumidor2 consumidor = new Consumidor2(getNomcola(),getTopic());
                break;
        }
        
    }
    
    
    public static void opcionMenuP3() {
        clearScreen();
        int valor = -1;
        while (valor == -1) {
            System.out.println("Elige\n1.- Productor\n2.- Consumidor\n0.- Salir");;
            try {
                valor = teclado.nextInt();
                logger.info("Se ha elegido la opción {}", valor);
                teclado.nextLine();
            } catch (InputMismatchException ime) {
                valor = -1;
                teclado.nextLine();
                logger.error("Error en el numero.");
            }
        }
        
         String nomexchange, topic;
        switch (valor) {
            case 0:
                System.exit(0);
                break;
                
            case 1:
               
                nomexchange = getNomcola();
                topic = getTopic();
                
                Productor2JX productor = new Productor2JX(nomexchange);
                productor.connectar();
                boolean repetir = true;
                while (repetir) {
                    System.out.println("Introduce un texto a enviar:");
                    String cadena = teclado.nextLine();
                    if (!cadena.equals("cerrar")) {
                        productor.sendMessage(cadena,topic);
                    } else {
                        repetir = false;
                        productor.sendMessage("Fin de mensaje.",topic);
                        productor.sendMessage("cerrar",topic);
                        logger.info("Fin de comunicación.");
                        productor.close();
                    }
                }
                break;
            case 2:
                Consumidor2JX consumidor = new Consumidor2JX(getNomcola(),getTopic());
                break;
        }
        
    }
    public static void opcionMenuP4(){
        teclado.nextLine();
        System.out.println("Estas creando un productor en MQTT (Por defecto el puerto esta en 1883)");
        System.out.println("Dime la direccion IP:");
        String ip = teclado.nextLine();
        System.out.println("Dime el puerto:");
        String puerto = teclado.nextLine();
        System.out.println("Dime el Topic");
        String topic = teclado.nextLine();
        
        ProductorMQTT productor = new ProductorMQTT(ip,puerto,topic);
        boolean cerrar = false;
        while(!cerrar){
            System.out.println("Texto a enviar:");
            String mensaje = teclado.nextLine();
            if(mensaje.equals("cerrar")){
                productor.close();
                cerrar = true;
            }else{
                productor.sendMessage(mensaje);
            }
        }
    }
    
    public static void opcionMenuP5(){
        teclado.nextLine();
        System.out.println("Dime la direccion IP:");
        String ip = teclado.nextLine();
        System.out.println("Dime el puerto:");
        String puerto = teclado.nextLine();
        System.out.println("Dime el topic:");
        String topic = teclado.nextLine();
        ConsumidorMQTT consumidor = new ConsumidorMQTT("tcp://"+ip+":"+puerto,topic);
    }
    
    public static void opcionMenuP6(){
        ConectorHTTP con = new ConectorHTTP();
        Montador mont = new Montador(con.connectar());
        mont.connect();
        try{
        mont.publish();
        }catch(Exception e){}
        
    }
    
    public static String getNomcola(){
        System.out.println("Dime el nombre de la cola:");
        return teclado.nextLine();
    }
    
    public static String getTopic(){
        System.out.println("Dime el Topic");
        return teclado.nextLine();
    }
}
