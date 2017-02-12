package com.example.danielamarialopezmunoz.ejercicio_s3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;

/**
 * Created by danielamarialopezmunoz on 12/02/17.
 */

public class Comunicacion extends Observable implements Runnable{

    public static Comunicacion ref;
    private final String HOST = "10.24.30.8";
    private final String IP_GRUPO = "226.24.30.8";
    private static  int PUERTO = 2227;

    private DatagramSocket maSocket;
    private boolean duracion;



    public Comunicacion(){
    duracion = true;

    try {
        maSocket = new DatagramSocket(PUERTO);
    } catch (Exception e){
        e.printStackTrace();

    }

    }

    public static Comunicacion getInstance(){

        if(ref == null){
            ref = new Comunicacion();
            Thread miHilo = new Thread(ref);

            miHilo.start();
        }

        return ref;



    }


    public void run() {
        while (duracion) {
            if (maSocket != null) {
                DatagramPacket miPaquete = recibido();
                if(miPaquete != null){
                    String message = new String(miPaquete.getData(),0,miPaquete.getLength());
                    setChanged();
                    notifyObservers(message);
                    clearChanged();

                }
            }
        }
    }


public DatagramPacket recibido(){

    byte[] reciboByte = new byte[1024];
    DatagramPacket miPaquete = new DatagramPacket(reciboByte, reciboByte.length);

    try {
        maSocket.receive(miPaquete);

        return miPaquete;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;


}

public void enviar(final String message, final String ipAddress, final int puerto){

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(maSocket != null){
                    try {
                        InetAddress host = InetAddress.getByName(ipAddress);

                        byte[] maByte = message.getBytes();
                        DatagramPacket miPaquete = new DatagramPacket(maByte,maByte.length,host,puerto);
                        maSocket.send(miPaquete);
                    }catch (UnknownHostException e){
                        e.printStackTrace();

                    } catch (IOException e){
                       e.printStackTrace();
                    }
                }
            }
        }).start();
    }


}
