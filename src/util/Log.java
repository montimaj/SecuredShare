package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
* Creates the log file Log.txt
* @author Sayantan Majumdar
* @since 1.0
*/
public class Log
{
  /**
  * Appends exception stacktraces to Log.txt along with the date and time of log generation
  * @param e Exception object 
  * @return Exception message
  */
  public static String createLog(String dir,Exception e)
  {    
    try
    {     
      File f=new File(dir,"Logs");      
      if(!f.exists()) 
    	  f.mkdir();      
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	   
      Date date = new Date();     
      String s=dateFormat.format(date)+"-> ";
      PrintWriter pw=new PrintWriter((new BufferedWriter(new FileWriter(f.toString()+"/Log.txt", true)))); 
      pw.println(s);
      e.printStackTrace(pw);
      pw.println();
      pw.close();
      s="Oops! Errors have been detected!\n"+e.toString()+"\nCheck: "+f.getAbsolutePath()+" for more details";
      return s;
    }
    catch(IOException exception)
    {
      System.out.println(exception.getMessage());
    }
    return "";
  } 
}
