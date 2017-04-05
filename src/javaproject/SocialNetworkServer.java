/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;
import java.net.*;
import java.io.*;
import java.util.Scanner;


/**
 *
 * @author N0639343
 */
public class SocialNetworkServer implements Runnable {
    private Socket connection;
    private String TimeStamp;
        
     public static void main(String[] args) {
        int port = 19999;
    try{
      ServerSocket socket1 = new ServerSocket(port);
      System.out.println("SocialNetworkServer: RUNNING");
      while (true) {
        Socket connection = socket1.accept();
        SocialNetworkServer runnable = new SocialNetworkServer(connection);
        Thread thread = new Thread(runnable);
        thread.start();
      }
    }
    catch (Exception e) {}
  }
SocialNetworkServer(Socket s) {
  this.connection = s;
}

public void run() {
    try {
      //Declarations of input reader and stringbuffer
      BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
      InputStreamReader isr = new InputStreamReader(is);
      int character;
      StringBuffer process = new StringBuffer();
      
      //Read in from input stream reader
      while((character = isr.read()) != 13) {
        process.append((char)character);
      }
      
      //Splitting the input into an array
      String[] parts = process.toString().split("-");
      
      BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
      OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
      
      //Loop over the string for identifiers for functions
      for (int i = 0; i < parts.length; i++){
          if (parts[i].equals("checkCredentials"))
          {
          //Check credentials and write the output
          String returnCode = SocialNetworkServer.checkCredentials("input.txt", parts[0], parts[1]);
          osw.write(returnCode + (char)13);
          }
          else if(parts[i].equals("loginUser"))
          {
              System.out.println("ADDING USER");
              SocialNetworkServer.login(parts[0]); 
          }
      }
      osw.flush();
    }
    catch (Exception e) {
      System.out.println(e);
    }
    finally {
      try {
        connection.close();
     }
      catch (IOException e){}
    }
    
    
}
public static String checkCredentials(String fileName, String username, String password)
{
   /*Checks the user credentials against the user file*/
    try 
    {
        //Compare username to file
        //If username not in file return not able to login
        //if username and password are in file allow user to login

        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        int lineNumber = 0;

        while (scanner.hasNextLine()) 
        {
            String line = scanner.nextLine();
            lineNumber++;
            
            String splitLine[] = line.split("-");
            //Scan the file for these strings
            if (splitLine[0].equals(username)&& splitLine[1].equals(password))
            {
               return ("correct");                 
            }
            else {
                return("incorrect");
            }
        }                
    } 
    catch (FileNotFoundException e)
    {
        System.out.println("File not found");
        return("server");
    }
    return("server");
    }

public static void login(String username)
{
   try{
       FileWriter fw = new FileWriter("activeusers.txt", true);
       fw.write("\n"+username);
       fw.close();
   }
   catch(IOException e){
       
   }
}
//Last bracket for class
}



