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

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author N0639343
 */
public class SocialNetworkServer implements Runnable {
    private Socket connection;
    private static Player player;
        
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
              String friendRequest = SocialNetworkServer.friendRequest(content[0],content[1],"friendRequests.txt");
              osw.write(friendRequest + (char)14);
          }
          else if(identifier.equals("acceptRequest")){
              String friendRequest = SocialNetworkServer.friendRequest(content[0], content[1], "userFriends.txt");
              String friendRequest2 = SocialNetworkServer.friendRequest(content[1], content[0], "userFriends.txt");
              SocialNetworkServer.removeRequest(content[0],content[1]);
              SocialNetworkServer.removeRequest(content[1],content[0]);
              osw.write(friendRequest + (char) 14);
          }
          else if(identifier.equals("removeRequest"))
          {
              SocialNetworkServer.removeRequest(content[0],content[1]);
              osw.write("COMPLETE" + (char)14);
          }
          else if (identifier.equals("updateFriendList"))
          {
              String details = SocialNetworkServer.getDetails(content[0],"userFriends.txt");
              osw.write(details + (char)14);
          }
          else if (identifier.equals("getFriendRequest"))
          {
              String details = SocialNetworkServer.getDetails(content[0],"friendRequests.txt");
              osw.write(details + (char)14);
          }
          else if(identifier.equals("getUserInformation"))
          {
              String userInformation = SocialNetworkServer.getUserDetails(content[0]);
              osw.write(userInformation+(char)14);
          }
          else if (identifier.equals("playAndStopSong"))
          {
              SocialNetworkServer.playAndStopSong(content[0], content[1]);
              osw.write("COMPLETE" + (char)14);
          }
          else if (identifier.equals("uploadFiles"))
          {
              SocialNetworkServer.uploadFiles(content[0]);
              osw.write("COMPLETE" + (char)14);
          }
          else if (identifier.equals("linkFiles"))
          {
              String check = SocialNetworkServer.linkFiles(content[0], content[1], content[2]);
              osw.write(check + (char)14);
          }
          else if(identifier.equals("getFileNames"))
          {
              String MusicNames = SocialNetworkServer.getFileNames(content[0], content[1]);
              osw.write(MusicNames + (char)14);
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
        br.close();
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
       bw.close();
       out.close();
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
    bw.close();
    output.close();
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
public static String getDetails(String friend, String fileName){
    String content = null;
    
    try{
       BufferedReader br = new BufferedReader(new FileReader(fileName));
       String line = null;
        while ((line = br.readLine())!= null){
            String splitLine[] = line.split(":");
            
            //Check username in file
            if (friend.equals(splitLine[0])){
               if (splitLine.length>1)
               {
                   content = splitLine[1];
               }
               else
               {
                   content = "No Requests";
               }   
            }
        }
        br.close();
    } 
        catch(IOException e){
   }
    
  return content;
}
public static String getUserDetails(String user){
    String content ="";
    
    try{
       BufferedReader br = new BufferedReader(new FileReader("input.txt"));
       String line = null;
        while ((line = br.readLine())!= null){
            String splitLine[] = line.split("-");
            
            //Check username in file
            if (user.equals(splitLine[0])){
                for(int i = 2; i < splitLine.length; i++){
                    content+= splitLine[i] +"-";
                }
            }
        }
        br.close();
    } 
        catch(IOException e){
   }
    
  return content;
}

public static String getFileNames(String user, String folderName){
    String content ="";
    
    try{
       BufferedReader br = new BufferedReader(new FileReader(folderName));
       String line = null;
        while ((line = br.readLine())!= null){
            String splitLine[] = line.split(":");
            
            //Check username in file
            if (user.equals(splitLine[0])){
                    content+= splitLine[1];
            }
        }
        br.close();
    } 
        catch(IOException e){
   }
    
  return content;
}

public static void uploadFiles(String filePath){
    try{

    	   File afile =new File(filePath);

    	   if(afile.renameTo(new File("C:\\Users\\user\\Desktop\\java\\JavaProject\\" + afile.getName()))){
    		System.out.println("File is moved successfuly!");
    	   }else{
    		System.out.println("File has failed to move! ");
    	   }

    	}catch(Exception e){
    		e.printStackTrace();
    	}
}

public static String linkFiles(String username, String fileName, String folderName){
    
    String check = "";
    String match = "no";
    String lineToRemove = "";
    try 
    {

        BufferedReader br = new BufferedReader(new FileReader(folderName));
        FileWriter fw = new FileWriter(folderName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        File inFile = new File(folderName);
        
        //Construct the new file that will later be renamed to the original filename. 
        File tempFile = inFile.createTempFile("file",".txt", inFile.getParentFile());
        PrintWriter temppw = new PrintWriter(new FileWriter(tempFile));        
        
        
        String line = null;

        br.mark(1000);
        
        //Check if the folder is empty
        if (br.readLine() != null)
        {
            br.reset(); // reset to beginning of the file
            //Go through each line in the file
            for (line = null; (line = br.readLine()) != null;)
            {             
                String splitLine[] = line.split(":");
                //Check if the user already uploaded songs
                if (splitLine[0].equals(username))
                {
                    match = "yes";
                    //Check if its an image or a song being uploaded
                    if (folderName.equals("userSongs.txt"))
                    {
                        String splitSongs[] = splitLine[1].split("-");
                        //Check if the user already uploaded this specific song
                        for (int i = 0; i < splitSongs.length; i++)
                        {
                            if (splitSongs[i].equals(fileName))
                            {
                                check = "alreadyAdded";  
                            }
                        }  
                        if (check != "alreadyAdded")
                        {
                            lineToRemove = line;
                            line += "-" + fileName;
                            pw.println(line);
                            check = "addedSuccessfully";
                        }
                    }
                    else if (folderName.equals("userIcon.txt"))
                    {
                        lineToRemove = line;
                        line = line.replace(splitLine[1], fileName);
                        pw.println(line);
                    }
                }
                if (match.equals("yes"))
                {
                    br.reset(); // reset to beginning of the file
                    //Read from the original file and write to the new 
                    //unless content matches data to be removed.
                    while ((line = br.readLine()) != null) {
                        if (!line.trim().equals(lineToRemove)) {
                            temppw.println(line);
                            temppw.flush();
                        }
                    } 
                 
                }
            }
            if (match.equals("no"))
            {
                line = username + ":" + fileName;
                pw.println(line);
                check = "addedSuccessfully";
            }
        }
        else
        {
            line = username + ":" + fileName;
            pw.println(line);
            check = "addedSuccessfully";            
        }

        br.close();
        pw.close();
        
        //Delete active user file
        inFile.delete();
        //Rename temp to active user
        tempFile.renameTo(inFile);           
              
    } 
    catch (FileNotFoundException e)
    {
        System.out.println("File not found");
    }
    catch (IOException g) {}
    return(check);
    }  

public static void playAndStopSong(String songName, String identifier){
        try{
            
        File file = new File(songName);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        
        try{
            
            if (identifier.equals("play"))
            {
                player = new Player(bis);
                player.play(); 
                
            }
            else if (identifier.equals("stop")) 
            {
                player.close();
            }

        }catch(JavaLayerException ex){}
        
        }catch(IOException e){}
}
public static String friendRequest(String username, String friend, String fileName){
        try {
            //Check for username
            //if username is already in write next to name
            //if not in there write new line
            File file = new File(fileName);
            File temp = file.createTempFile("file",".txt", file.getParentFile());
            
            String status = null;
            String charset = "US-ASCII";
            String delete = username + "-";
            
            BufferedReader br = new BufferedReader(new FileReader(fileName));
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
public static void removeRequest(String username, String friend){
 try {
    //Open friend request file and temp file
    File file = new File("friendRequests.txt");
    File temp = file.createTempFile("file",".txt", file.getParentFile());
    
    //Set char type and username to delete
    String charset = "US-ASCII";
    String delete = friend;
    
    //Declare buffered reader and print writer for file and temp file
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
    
    //Iterate over file deleting relevant request
    for (String line; (line = br.readLine()) != null;) 
    {
        String splitLine[] = line.split(":");
        if(username.equals(splitLine[0]))
        {
            String friendRequests[] = splitLine[1].split("-");
            for (int i = 0; i < friendRequests.length; i++)
            {
                if(friendRequests[i].equals(friend)){
                    line = line.replace(delete, "");
                }
                else if((friendRequests.length==1)&&(friendRequests.length<friendRequests.length)){
                    line = line.replace(delete+"-", "");
                }
                else
                {
                    line = line.replace("-"+delete, "");
                }
            }
        }
        System.out.println(line);
        pw.println(line);
    }
       
    //Close file writer and print writer
    br.close();
    pw.close();
    
    //Delete friend file file
    file.delete();
    //Rename temp to friend
    temp.renameTo(file);
    }
    catch (FileNotFoundException e) {}   
    catch (UnsupportedEncodingException f) {} 
    catch (IOException g) {System.out.println(g);} 
}
//Last bracket for class
}




