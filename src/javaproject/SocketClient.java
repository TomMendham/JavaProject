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

    
 public String checkCredentials(String username, String password, String identifier){
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
         String message = username + "-" + password + "-" + identifier + "-" + "checkCredentials" + (char) 13;
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
 public void connect(String username, String identifier){
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
         String message = username + "-" + identifier + (char) 13;
         //Writing the message and flushing the server
         osw.write(message);
         osw.flush();         
     }
     
    catch (IOException f) {
      System.out.println("IOException: " + f);
    }
    catch (Exception g) {
      System.out.println("Exception: " + g);
    }
 }
 public String request(String requestQuery){
     /*Function used to only send requests across server i.e. update login list or update friend requests*/
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
         String message = requestQuery + (char) 13;
         //Writing the message and flushing the server
         osw.write(message);
         osw.flush();
         
         BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
         InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
         StringBuffer input = new StringBuffer();
         
          int c;
          while ( (c = isr.read()) != 14){
              input.append( (char) c);
          }    
         //Close the connection
         connection.close();
         return(input.toString());
     }
     
    catch (IOException f) {
      System.out.println("IOException: " + f);
    }
    catch (Exception g) {
      System.out.println("Exception: " + g);
    }
     
    return ("");
 }
 public void registering(String username, String password, String dateOfBirth, String genresString){
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
         String message = username + "-" + password + "-" + dateOfBirth + "-" + genresString + "-" + "registering" + (char) 13;
         //Writing the message and flushing the server
         osw.write(message);
         osw.flush();
                
         //Close the connection
         connection.close();
         }
     
    catch (IOException f) {
      System.out.println("IOException: " + f);
      isCorrect = "server";
    }
    catch (Exception g) {
      System.out.println("Exception: " + g);
      isCorrect = "server";
    }   
}
 public void post(String username, String post){
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
         String message = username + "-" + post + "-" + "makePost" + (char) 13;
         //Writing the message and flushing the server
         osw.write(message);
         osw.flush();
     
        }
        catch (IOException f) {
         System.out.println("IOException: " + f);
       }
        catch (Exception g) {
          System.out.println("Exception: " + g);
       }
    }
 public void friendRequest(String username, String friend){
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
         String message = username + "-" + friend + "-" + "friendRequest" + (char) 13;
         //Writing the message and flushing the server
         osw.write(message);
         osw.flush();
     
        }
        catch (IOException f) {
         System.out.println("IOException: " + f);
       }
        catch (Exception g) {
          System.out.println("Exception: " + g);
       }
 }
}

