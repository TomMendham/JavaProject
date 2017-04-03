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
         
         //Write the output to a buffered output and then to the writer
         BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
         OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
         
         //Message to send over server
         String message = username + "-" + password + "-" + "readFile" + (char) 13;
         //Writing the message and flushing the server
         osw.write(message);
         osw.flush();
         
         //Close the connection
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

