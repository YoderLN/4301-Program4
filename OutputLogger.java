//********************************************************************
//
//  Author:        Levi Yoder
//
//  Program #:     Four
//
//  File Name:     OutputLogger.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Instructor:    Prof. Fred Kumi 
//
//  Description:   class handles all output functions for driver class
//
//********************************************************************
import java.io.FileNotFoundException;
import java.util.Formatter;

public class OutputLogger {
	private Formatter output = null;
	
	public OutputLogger()
	{
		try {
			output = new Formatter("Program4-output.txt");
		}
		catch (FileNotFoundException e) 
	   	   {
	   	        System.err.println("Issue with creating output file.");
	   	        System.err.println(e.toString());
	   	   }
	}
	   //***************************************************************
	   //
	   //  Method:       outputHeader
	   // 
	   //  Description:  identifies file purpose and author
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
		public void outputHeader()
		{
			toLog("Log File for Program 4");
			toLog("Author: Levi Yoder\n");
		}
	   //***************************************************************
	   //
	   //  Method:       toLog
	   // 
	   //  Description:  writes to log file
	   //
	   //  Parameters:   String to be written
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
		public void toLog(String message)
		{
			output.format("%s%n", message);
		}
	   //***************************************************************
	   //
	   //  Method:       toConsoleAndLog
	   // 
	   //  Description:  writes to both logfile and console
	   //
	   //  Parameters:   String to be written
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************	
		public void toConsoleAndLog(String message)
		{
			System.out.println(message);
			output.format("%s%n", message);
		}
	   //***************************************************************
	   //
	   //  Method:       closeOutputFile
	   // 
	   //  Description:  closes output file
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
		public void closeOutput()
		{
			output.close();
		}
}