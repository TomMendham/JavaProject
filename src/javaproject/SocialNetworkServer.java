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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 *
 * @author N0639343
 */
public class SocialNetworkServer implements Runnable {
    private Socket connection;
        
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
      String[] parts = process.toString().split("~");
      String identifier = parts[0];
      String[] content = parts[1].split("-");
      
      BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
      OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
 
          if (identifier.equals("checkCredentials"))
          {
          //Check credentials and write the output
          String returnCode = SocialNetworkServer.checkCredentials("input.txt", content[0], content[1], content[2]);
          osw.write(returnCode + (char)14);
          }
          else if(identifier.equals("loginUser"))
          {
              SocialNetworkServer.login(content[0]);
              osw.write("COMPLETE" + (char)14);
          }
          else if(identifier.equals("logoutUser"))
          {
              SocialNetworkServer.logout(content[0]);
              osw.write("COMPLETE" + (char)14);
          }
          else if(identifier.equals("updateLoginList"))
          {
              String usernames = SocialNetworkServer.updateList("activeusers.txt");
              osw.write(usernames + (char)14);
          }
          else if(identifier.equals("updatePostList"))
          {
              String post = SocialNetworkServer.updateList("userPosts.txt");
              osw.write(post + (char)14);
          }
          else if(identifier.equals("registering"))
          {
              SocialNetworkServer.registering("input.txt",content[0], content[1], content[2], content[3]);
              osw.write("COMPLETE" + (char)14);
          }
          else if(identifier.equals("makePost"))
          {
              SocialNetworkServer.post(content[0], content[1]);
              osw.write("COMPLETE" + (char)14);
          }
          else if(identifier.equals("friendRequest"))
          {
              String friendRequest = SocialNetworkServer.friendRequest(content[0],content[1]);
              osw.write(friendRequest + (char)14);
          }
          else if (identifier.equals("getDetails"))
          {
              String details = SocialNetworkServer.getDetails(content[0]);
              osw.write(details + (char)14);
          }
          else if (identifier.equals("playSong"))
          {
              SocialNetworkServer.playSong(content[0]);
              osw.write("COMPLETE" + (char)14);
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
public static String checkCredentials(String fileName, String username, String password, String identifier){
   /*Checks the user credentials against the user file*/
    try 
    {
        //Compare username to file
        //If username not in file return not able to login
        //if username and password are in file allow user to login
        
        
        //Test for empty values.
        if (username.equals("")&&password.equals(""))
            return("incorrect");

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
public static void registering(String fileName, String username, String password, String dateOfBirth, String genresString){ 
   try(FileWriter fw = new FileWriter(fileName, true);
       BufferedWriter bw = new BufferedWriter(fw);
       PrintWriter out = new PrintWriter(bw);)
   {
       out.println("\n\r" + username+ "-" + password + "-" + dateOfBirth + "-" + genresString);
   }
   catch(IOException e){
       
   }
}
public static void login(String username){
  try(FileWriter fw = new FileWriter("activeusers.txt", true);
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter output = new PrintWriter(bw))
{
    output.println(username + "-");
} 
  catch (IOException e) {}
}
public static void logout(String username){
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
public static String updateList(String fileName) throws IOException{
  String content = new String(Files.readAllBytes(Paths.get(fileName)));
  return content;
}
public static void post(String username, String post){
    
    //Get todays date in the simple date format "dd/MM/YY HH:mm
    DateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
    Date todaysDate = Calendar.getInstance().getTime();    
    String currentDate = sdf.format(todaysDate);
    
    try(FileWriter fw = new FileWriter("userPosts.txt", true);
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter output = new PrintWriter(bw))
{
    output.println(currentDate + " - " + username + " - " + post);
} 
  catch (IOException e) {}
}
public static String getDetails(String friend){
    String content = "";
    
    try{
       BufferedReader br = new BufferedReader(new FileReader("input.txt"));
       String line = null;
        while ((line = br.readLine())!= null){
            String splitLine[] = line.split(":");
            
            //Check username in file
            if (friend.equals(splitLine[0])){
                content = splitLine[1];
            }
        }
    } 
        catch(IOException e){
   }
    
    
  return content;
}
public static void playSong(String songName){
    
    songName += ".mp3";
        File f = new File(songName);
        
        if(f.exists() && f.isFile()) { 
            Media hit = new Media(new File(songName).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }
}
public static String friendRequest(String username, String friend){
        try {
            //Check for username
            //if username is already in write next to name
            //if not in there write new line
            File file = new File("friendRequests.txt");
            File temp = file.createTempFile("file",".txt", file.getParentFile());
            
            String status = null;
            boolean alreadyAdded = true;
            String charset = "US-ASCII";
            String delete = username + "-";
            
            BufferedReader br = new BufferedReader(new FileReader("friendRequests.txt"));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
        
            for (String line; (line = br.readLine()) != null;){
                String splitLine[] = line.split(":");
                
                //Check friend request name
                if (friend.equals(splitLine[0])){
                    //Check to see if user already has friend request
                    if (splitLine.length > 1 )
                    {
                        //Split current friendship requests to check for current request
                        String friendRequests[] = splitLine[1].split("-");
                        boolean isRequest = true;
                        //Loop over current friends list to check if friend request is already made
                        for(int i =0; i < friendRequests.length; i++){
                            //If friend request is equal to username then you have already asked that friend
                            if(friendRequests[i].equals(username)){
                                isRequest = false;
                                status = "alreadyAdded";
                            }
                        }
                        //If it's true append request to end of requests
                        if (isRequest){
                            line += "-" + username;
                            status = "success";
                        }
                        //Else tell user they have already requested this
                    }
                    //Else they have no friend requests
                    else{
                        line = friend+":"+username;
                        status = "success";
                    }
                }
                pw.println(line);
            }
            if (status==null){
                pw.println(friend+":"+username);
                status = "success";
            }
            
            br.close();
            pw.close();

            //Delete active user file
            file.delete();
            //Rename temp to active user
            temp.renameTo(file);
            
            if (status.equals("success"))
            {
                return("Request Successful");
            }
            else if(status.equals("alreadyAdded"))
            {
                return("Already requested");        
            }
            else
            {
                return("Cannot add self");
            }
        } 
        catch (IOException ex) {
        System.out.println(ex);
        }
return("Request unsuccessful");
}

//Last bracket for class
}




