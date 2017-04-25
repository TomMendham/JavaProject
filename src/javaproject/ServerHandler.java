/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Thomas
 */
public class ServerHandler extends Thread {
    Socket connection;
    ArrayList<userTarget> ul = new ArrayList<userTarget>();
    
    public ServerHandler(Socket _connection, ArrayList _ul){
        this.connection = _connection;
        this.ul = _ul;
    }
    @Override
    public void run(){
      
        try {
            DataInputStream is = new DataInputStream(connection.getInputStream());
            //Read in from input stream reader
            String message = "";
            while(true) {
                message = is.readUTF();
                String details[] = message.split("~");
                //Check for setting up a user
                if (details[0].equals("setup"))
                {
                   //Add an array with users socket and username
                   userTarget u = new userTarget(connection,details[1]);
                   ul.add(u);
                   System.out.println("User added: "+details[1]+": "+ul.size());
                }
                
                System.out.println(connection+":"+message);
                
                for (int i =0; i<ul.size();i++){
                    DataOutputStream os = new DataOutputStream(ul.get(i).socket.getOutputStream());
                    os.writeUTF(message);  
                }
            }            
        } 
        catch (IOException ex) {
        } 
    }
    public class userTarget{
        Socket socket;
        String username;
        public userTarget(Socket _connection, String _username){
            socket = _connection;
            username = _username;
        }
    }
    
}
