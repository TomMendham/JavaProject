/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author n0643521
 */
public class readingFile implements Runnable{
    
    String fileName;
             
          public readingFile(String _fileName){
              fileName = _fileName;
          }
          
          public void run() {
             try {
             File file = new File(fileName);
             FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
             StringBuffer stringBuffer = new StringBuffer();
             String line;
             while ((line = bufferedReader.readLine()) !=null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
             }
             fileReader.close();
             System.out.println(stringBuffer.toString());

            }
             catch (IOException e) {
              e.printStackTrace();
          }
        }   
    }

