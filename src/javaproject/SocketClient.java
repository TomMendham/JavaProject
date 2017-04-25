/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author N0639343
 */
public class SocketClient {  
    String host = "localhost";
    int port = 19999;
    chatClient chat;
 public String request(String identifier, String messageToSend){
     /*Function used to only send requests across server i.e. update login list or update friend requests*/
     try {
         //Check to see if chat
         if (identifier.equals("createchat")){
             chat = new chatClient();
             Thread t = new Thread(chat);
             t.start();
         }
         else{
         //Find the INET 
         InetAddress address = InetAddress.getByName(host);
         Socket connection = new Socket(address, port);
         
         //Write the output to a buffered output and then to the writer
         BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
         OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
         
         //Message to send over server
         String message = identifier + "~" + messageToSend + "~" + (char) 13;
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
     }

    catch (IOException f) {
      System.out.println("IOException: " + f);
      return("server");
    }
    catch (Exception g) {
      System.out.println("Exception: " + g);
      return("server");
    }
return("");
 }
 class chatClient extends Thread {
    DataOutputStream output;
    DataInputStream input;
    Socket connection;
    chatListener listener;
    public chatClient(){
        
    }
    @Override
    public void run(){
        try {
            port = 19998;
            InetAddress address = InetAddress.getByName(host);
            connection = new Socket(address,port);
            //Setup output stream and input stream
            output = new DataOutputStream(connection.getOutputStream());
            input = new DataInputStream(connection.getInputStream());
            listener = new chatListener(input);
            Thread l = new Thread(listener);
            l.start();
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public void sendMessage(String message){
            try {
                output.writeUTF (message);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
    
    public class chatListener extends Thread{
        DataInputStream input;
        String message = "";
        public chatListener(DataInputStream _input){
            input = _input;
        }
        @Override
        public void run(){
            while(true){
                try {
                    message = input.readUTF();
                } catch (IOException ex) {
                }
            }
        }
    }
public chatClient returnClient(){
    return chat;
}
}


