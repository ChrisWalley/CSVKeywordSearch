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

    public String[][] readSpreadsheets(String[] fileNames, String[] keyWords, String folder)
      {

        ArrayList<String[]> test = new ArrayList<>();
        //String[][] results = new String[fileNames.length][keyWords.length+1];//[sheets are column 1] [keywords hit fill rest of row, columns 2+]

        int maxDepth = 0;

        for (int loop = 0; loop < fileNames.length; loop++)
          {
            ArrayList<String> res = new ArrayList<>();
            String fileName = fileNames[loop];
            res.add(fileName);
            boolean hit = false;
            int keywordHit = 0;
            for (int wordLoop = 0; wordLoop < keyWords.length; wordLoop++)
              {

                try
                  {
                    Scanner scFile = new Scanner(new File(folder+"/" + fileName));
                    while (scFile.hasNextLine())
                      {
                        String line = scFile.nextLine().toLowerCase();

                        if (line.contains(keyWords[wordLoop]))
                          {/*
                            if (results[numFound][0] == null)
                              {
                                results[numFound][0] = fileName;
                              }*/
                            //results[numFound][keywordHit + 1] = keyWords[wordLoop];
                            res.add(keyWords[wordLoop]);
                              System.out.println("Found keyword " + keyWords[wordLoop] + " in file "+ fileName);
                            keywordHit++;
                            if (!hit)
                              {
                                hit = !hit;
                              }
                            break;
                          }
                      }
                    scFile.close();
                  } catch (NumberFormatException e)
                  {
                    System.out.println(e);
                  } catch (FileNotFoundException ex)
                  {
                    Logger.getLogger(CSVHandler.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
            if (hit)
              {
                test.add(res.toArray(new String[0]));
                if(keywordHit > maxDepth)
                  {
                    maxDepth = keywordHit;
                  }
              }
          }

        CSVKeywordSearch.setMaxDepth(maxDepth);
        return test.toArray(new String[0][0]);
      }
  }
