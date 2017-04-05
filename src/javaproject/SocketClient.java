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
     String isCorrect;
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
         String message = username + "-" + password + "-" + "checkCredentials" + (char) 13;
         //Writing the message and flushing the server
         osw.write(message);
         osw.flush();
         
         BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
         InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
         StringBuffer input = new StringBuffer();
         
          int c;
          while ( (c = isr.read()) != 13){
              input.append( (char) c);
          }
                       
         //Close the connection
         connection.close();
         isCorrect = input.toString();
     }
     
    catch (IOException f) {
      System.out.println("IOException: " + f);
      isCorrect = "server";
    }
    catch (Exception g) {
      System.out.println("Exception: " + g);
      isCorrect = "server";
    }   
    return (isCorrect);
 }
 public void login(String username){
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
         String message = username + "-" + "loginUser" + (char) 13;
         //Writing the message and flushing the server
         osw.write(message);
         osw.flush();
                       
         //Close the connection
         connection.close();
     }
     
    catch (IOException f) {
      System.out.println("IOException: " + f);
    }
    catch (Exception g) {
      System.out.println("Exception: " + g);
    }   
 }
}

