/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;
import java.net.*;
import java.io.*;

/**
 *
 * @author N0639343
 */
public class SocketClient {

    
 public String checkCredentials(String username, String password){
     String isCorrect = "";
     String host = "localhost";
     int port = 19999;
     try {
         //Find the INET 
         InetAddress address = InetAddress.getByName(host);
         Socket connection = new Socket(address, port);
         
         BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
         OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
         
         String message = username + " " + password + (char) 13;
         osw.write(message);
         osw.flush();
                  
         connection.close();
         isCorrect = "correct";
     }
     
    catch (IOException f) {
      System.out.println("IOException: " + f);
      isCorrect = "server";
    }
    catch (Exception g) {
      System.out.println("Exception: " + g);
      isCorrect = "server";
    }   
    return isCorrect;
 }
}

