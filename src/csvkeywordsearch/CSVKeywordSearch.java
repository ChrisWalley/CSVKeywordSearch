/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvkeywordsearch;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Christopher
 */
public class CSVKeywordSearch
  {

    private static String[] filenames;
    private static String[][] matchedFiles;
    private static int numFound = 0;
    private static final int XLSX = 0;
    private static final int CSV = 1;
    private static CSVHandler H = new CSVHandler();
    private static int maxDepth;

    /**
     * @param CurrType type of file
     * @param keys keywords
     * @param args the command line arguments
     */
    public static void run(int CurrType, String[] keys, String folder)
      {
        //int CurrType = CSV;
        String input = "";
        ArrayList<String> keyWords = new ArrayList<>();
        /*
        while(true)
          {
            input = JOptionPane.showInputDialog("Please enter a keyword to search for");
            if(input.equals("end"))
              {
                break;
              }
           keyWords.add(input);
          }
         */

        filenames = readFilenames(folder, CurrType);

        //matchedFiles = H.readSpreadsheets(filenames, keyWords.toArray(new String [0]));
        matchedFiles = H.readSpreadsheets(filenames, keys, folder);
        numFound = matchedFiles.length;

        saveList(matchedFiles, keys);

      }

    private static String[] readFilenames(String folder, int type)
      {
        ArrayList<String> result = new ArrayList<>();

        String extension;
        /*
            TYPES:
                0 == .xlsx
                1 == .csv

         */
        //ADD CODE TO READ FILENAMES IN FOLDER

        switch (type)
          {
            case XLSX:
                extension = ".xlsx";
                break;
            case CSV:
                extension = ".csv";
                break;
            default:
                extension = ".csv";
                break;
          }
        try
          {
            Runtime r = Runtime.getRuntime();

            Process p = r.exec(new String [] {"dir.bat",folder});

            try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream())))
              {
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                  {
                    if (inputLine.length() > 0 && inputLine.substring(inputLine.length() - extension.length()).equalsIgnoreCase(extension))
                      {
                        result.add(inputLine);
                      }
                  }
              }

          } catch (IOException e)
          {
            System.out.println(e);
          }
        return result.toArray(new String[0]);
      }

    public static int getNumFound()
      {
        return numFound;
      }

    public static void setMaxDepth(int d)
      {
        maxDepth = d;
      }

    public static int getMaxDepth()
      {
        return maxDepth;
      }

    private static void saveList(String[][] matchedFiles, String[] keys)
      {

        try
          {
            PrintWriter writeFile = new PrintWriter(new FileWriter("Output.csv"));
            writeFile.print("Filename;");

            for (int loop = 0; loop < maxDepth; loop++)
              {
                writeFile.print("Match;");
              }

            writeFile.println();

            for (String[] matchedFile : matchedFiles)
              {
                writeFile.print(matchedFile[0] + ";");
                for (int loop = 1; loop < matchedFile.length; loop++)
                  {
                    writeFile.print(matchedFile[loop] + ";");
                  }
                writeFile.println();
              }
            writeFile.close();
          } catch (java.io.IOException E)
          {
            System.out.println(E);
          }

      }

  }
