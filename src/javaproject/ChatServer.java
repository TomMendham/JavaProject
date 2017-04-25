/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Thomas
 */
public class ChatServer {
private static ArrayList<ServerHandler.connectedUser> ul = new ArrayList<ServerHandler.connectedUser>();

    public static void main(String[] args) {
        try {
            int port = 19998;
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Chat Server: RUNNING");
            while (true) {
              //Create a new socket handler thread with connection from socket
              Socket connection = ss.accept();
              ServerHandler sh = new ServerHandler(connection,ul);
              Thread thread = new Thread(sh);
              thread.start();
      }
        } catch (IOException ex) {}
    }
    
}
