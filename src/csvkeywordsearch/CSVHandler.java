/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvkeywordsearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christopher
 */
public class CSVHandler
  {

    public int[][] readSpreadsheets(String[] keyWords, ArrayList<File> files, String ext)
      {
        int[][] test = null;
        try
          {
            test = search(files, ext, keyWords);

          } catch (FileNotFoundException ex)
          {
            Logger.getLogger(CSVHandler.class.getName()).log(Level.SEVERE, null, ex);
          }
        return test;
      }

    public ArrayList<File> getFiles(File folder, String ext)
      {
        ArrayList<File> filesList = new ArrayList<>();
        if (folder.isDirectory())
          {
            File[] files = folder.listFiles();
            for (File f : files)
              {
                if (f.isDirectory())
                  {
                    filesList.addAll(getFiles(f, ext));
                  } else
                  {
                    String n = f.getName();
                    if (!n.contains("."))
                      {
                        System.out.println(n);
                      } else if (n.substring(n.lastIndexOf("."), n.length()).equals(ext))
                      {
                        filesList.add(f);
                      }
                  }
              }
          } else
          {
            filesList.add(folder);
          }
        return filesList;
      }

    private int[][] search(ArrayList<File> files, String ext, String[] keyWords) throws FileNotFoundException
      {
        int[][] keysByFiles = new int[files.size()][keyWords.length];
        if (ext.equals(".csv"))
          {
            for (int fileLoop = 0; fileLoop < files.size(); fileLoop++)
              {
                File f = files.get(fileLoop);
                for (int stringLoop = 0; stringLoop < keyWords.length; stringLoop++)
                  {
                    int count = 0;
                    String word = keyWords[stringLoop];
                    Scanner scFile = new Scanner(f);

                    while (scFile.hasNext())
                      {
                        String line = scFile.nextLine();
                        if (line.contains(word))
                          {
                            count++;
                          }
                      }
                    keysByFiles[fileLoop][stringLoop] = count;
                    scFile.close();
                  }

              }

          } else if (ext.equals(".xlsx"))
          {
            for (int fileLoop = 0; fileLoop < files.size(); fileLoop++)
              {
                File f = files.get(fileLoop);
                for (int stringLoop = 0; stringLoop <= keyWords.length; stringLoop++)
                  {
                    int count = 0;
                    String word = keyWords[stringLoop];
                    
                    //Find word
                    
                    keysByFiles[fileLoop][stringLoop] = count;
                  }

              }
          }
        return keysByFiles;
      }
  }
