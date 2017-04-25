/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Thomas
 */
public class ServerHandler extends Thread {
    Socket connection;
    ArrayList<connectedUser> ul = new ArrayList<connectedUser>();
    
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
                //Read in message and split
                message = is.readUTF();
                String details[] = message.split("~");
                //Check for setting up a user
                if (details[0].equals("setup"))
                {
                   //Add an array with users socket and username
                   connectedUser u = new connectedUser(connection,details[1]);
                   ul.add(u);
                   //Output connected back to chat client
                   DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                   os.writeUTF("connected");
                }
                else
                {
                //Loop over user list
                for (int i =0; i<ul.size();i++){
                    //Check if username is correct username and write to this client
                    if (ul.get(i).username.equals(details[1]))
                    {
                    //If correct username is found send a message with current username and message content to client
                    DataOutputStream os = new DataOutputStream(ul.get(i).socket.getOutputStream());
                    os.writeUTF(details[0]+"~"+details[2]);     
                    }
                }
                }
            }            
        } 
        catch (IOException ex) {
        } 
    }
    public class connectedUser{
        //Class for storing connected users with their socket and username
        Socket socket;
        String username;
        public connectedUser(Socket _connection, String _username){
            socket = _connection;
            username = _username;
        }
    }
    
}
