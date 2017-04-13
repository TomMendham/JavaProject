/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;
import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

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
          String returnCode = SocialNetworkServer.checkCredentials("input.txt", parts[0], parts[1], parts[2]);
          osw.write(returnCode + (char)13);
          }
          else if(parts[i].equals("loginUser"))
          {
              SocialNetworkServer.login(parts[0]);
          }
          else if(parts[i].equals("logoutUser"))
          {
              SocialNetworkServer.logout(parts[0]);
          }
          else if(parts[i].equals("updateLoginList"))
          {
              String usernames = SocialNetworkServer.updateLoginList();
              osw.write(usernames + (char)14);
          }
          else if(parts[i].equals("registering"))
          {
              SocialNetworkServer.registering("input.txt",parts[0], parts[1], parts[2], parts[3]); 
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
public static String checkCredentials(String fileName, String username, String password, String identifier)
{
   /*Checks the user credentials against the user file*/
    try 
    {
        //Compare username to file
        //If username not in file return not able to login
        //if username and password are in file allow user to login

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = br.readLine())!= null)
        {             
            String splitLine[] = line.split("-");
            if (identifier.equals("R"))
            {
                //Scan the file for this string
                if (splitLine[0].equals(username))
                {
                    return ("correct");                 
                }
            }
            else if (identifier.equals("L"))
            {
                //Scan the file for these strings
                if (splitLine[0].equals(username)&& splitLine[1].equals(password))
                {
                    return ("correct");                 
                }
            }            
        }                
    } 
    catch (FileNotFoundException e)
    {
        System.out.println("File not found");
        return("server");
    }
    catch (IOException g) {}
    return("incorrect");
    }

public static void login(String username)
{
  try(FileWriter fw = new FileWriter("activeusers.txt", true);
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter output = new PrintWriter(bw))
{
    output.println(username + "-");
} 
  catch (IOException e) {}
}

public static void logout(String username)
{
    try {
    //Open active users file and temp file
    File file = new File("activeusers.txt");
    File temp = file.createTempFile("file",".txt", file.getParentFile());
    
    //Set char type and username to delete
    String charset = "US-ASCII";
    String delete = username + "-";
    
    //Declare buffered reader and print writer
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
    
    //Iterate over file deleting relevant line
    for (String line; (line = br.readLine()) != null;) 
    {
    line = line.replace(delete, "");
    line.trim();
    if (line.length() > 0)
        {
         pw.println(line);
        }
    }
    
    //Close file writer and print writer
    br.close();
    pw.close();
    
    //Delete active user file
    file.delete();
    //Rename temp to active user
    temp.renameTo(file);
    }
    catch (FileNotFoundException e) {}   
    catch (UnsupportedEncodingException f) {} 
    catch (IOException g) {}
}
public static String updateLoginList() throws IOException{
  String content = new String(Files.readAllBytes(Paths.get("activeusers.txt")));
  return content;
}

public static void registering(String fileName, String username, String password, String dateOfBirth, String genresString)
{ 
   try(FileWriter fw = new FileWriter(fileName, true);
       BufferedWriter bw = new BufferedWriter(fw);
       PrintWriter out = new PrintWriter(bw);)
   {
       out.println("\n" + username+ "-" + password + "-" + dateOfBirth + "-" + genresString);
   }
   catch(IOException e){
       
   }
}

//Last bracket for class
}




