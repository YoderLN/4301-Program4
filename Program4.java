//********************************************************************
//
//  Author:        Levi Yoder
//
//  Program #:     Four
//
//  File Name:     Program4.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Instructor:    Prof. Fred Kumi 
//
//  Description:   Driver class for Computer Aided Instruction program
//
//********************************************************************
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.security.SecureRandom;

public class Program4 {
	
	private final int MAX_LEVEL_SIZE = 7;
	private ArrayList<Double> levelScores = new ArrayList<>();
	private Formatter output = null;
	private Scanner input = new Scanner(System.in);
	private String [] correctAnswer = {"Excellent!",
										"Very good!",
										"Nice work!",
										"Way to go!",
										"Keep up the good work!"};
	
	private String [] wrongAnswer = {"That is incorrect!",
										"No. Please try again!",
										"Wrong, try once more!",
										"No. Don't give up!",
										"Incorrect. Keep trying!"};
	
	private SecureRandom randomResponse = new SecureRandom();

	public static void main(String[] args)
	{
		Program4 obj = new Program4();
		obj.developerInfo();
		try {
		obj.openOutputFile();
		obj.outputHeader();
		obj.primaryLoop();
		obj.displayAverages();
		obj.closeOutputFile();	
			}
	catch (Exception e)
	{
		obj.toConsoleAndLog(e.toString());
	}
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
	   //  Method:       displayAverages
	   // 
	   //  Description:  displays user scores
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
		public void displayAverages()
		{
		for (int i = 0; i < levelScores.size(); i++)
			{
			String result = String.format("Level %d score : %.2f Percent", i+1, levelScores.get(i) );
			toConsoleAndLog(result);
			}
		
			if (levelScores.get(0) < 80)
			{
				toConsoleAndLog("Please ask your teacher for extra help.");
			}
		
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
	   //  Method:       openOutputFile
	   // 
	   //  Description:  opens output file
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
	   public void openOutputFile()
	   {
	   	   try 
	   	   	{
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
	   //  Method:       closeOutputFile
	   // 
	   //  Description:  closes output file
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
	   public void closeOutputFile()
	   {
		   output.close();
	   }
	   
	   //***************************************************************
	   //
	   //  Method:       outputHeader
	   // 
	   //  Description:  identifies file purpose
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
	   public void outputHeader()
	   {
		   toLog("Log File for Program 4");
	   }
	   
	   
	   //***************************************************************
	   //
	   //  Method:       isValidFormat
	   // 
	   //  Description:  extra layer of validation to prevent issues converting user input to integer
	   //                checks for empty string, string longer than acceptable answer, and makes sure
	   //                every character after the first is a digit, allowing the first character
	   //                to be a '-' for negative input
	   //
	   //  Parameters:   string entered by user
	   //
	   //  Returns:      true/false
	   //
	   //**************************************************************
	   public boolean isValidFormat(String answer)
	   {
		 //easiest way to handle is assume true, then flip to false if issue found
		   boolean result = true; 
		   
		   
		   //early exit check
		   if (answer.length() < 1 //empty string
				   || answer.length() > 6 //string too long
				   || (answer.length() == 1 && answer.charAt(0) == '-') //string only a minus
				   || (answer.length() > 1 && (!Character.isDigit(answer.charAt(0)) && answer.charAt(0) != '-'))
				   ) //string length more than 1 and first digit not minus or number
		   {
			   result = false;
		   }
		   
		   //Initial check passed, moves to here
		   else
		   
		  //second layer of validation, first character is '-' or a digit
		   //char firstCharacter = answer.charAt(0);
		   //if (Character.isDigit(firstCharacter) 
			//	   || firstCharacter == '-')
		   {
			   for(int i = 1; i < answer.length(); i++) //every character after first is a digit
			   {
				   char ch = answer.charAt(i);
				   if (!Character.isDigit(ch))
				   {
					   result = false;
				   }
			   }
		   }
		   
		  // else //else for second if statement, first character is not digit or '-'
			//   result = false;
		   
		   return result;
	   }
	   //***************************************************************
	   //
	   //  Method:       primaryLoop
	   // 
	   //  Description:  
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
	   public void primaryLoop()
	   {
		   String result1 = "";
		   String result2 = "";
		   String result3 = "";
		   
		   while (!result1.equals("exit") && !result2.equals("exit") && !result3.equals("exit"))
		   {
			   if (result1.equals(""))
			   {
				   //call first level
				   
				   toConsoleAndLog("Basic Level");
				   result1 = levelLoop(3);
				   toConsoleAndLog("Basic Level done.");	   
			   }
			   else if (result2.equals(""))
			   {
				   //call second level
				   toConsoleAndLog("Intermediate Level");
				   result2 = levelLoop(5);
				   toConsoleAndLog("Intermediate Level done.");
			   }
			   else if (result3.equals(""))
			   {
				   //call third level
				   toConsoleAndLog("Advanced Level");

				   result3 =levelLoop(7);
				   toConsoleAndLog("Advanced Level done");
			   }
		   }
		   
		   while (levelScores.size() < 3) //finishes populating arrayList in event of early exit
		   {
			   levelScores.add(0.0);
		   }
	   }
	   	   
	   //***************************************************************
	   //
	   //  Method:       levelLoop
	   // 
	   //  Description:  instantiates object of generator and evaluator classes,
	   //                accepts user input, validates user input
	   //
	   //  Parameters:   size of equation to be generated
	   //
	   //  Returns:      user determined exit status
	   //
	   //**************************************************************
	   public String levelLoop(int size)
	   {
		   ArrayList<String> exitStatements = new ArrayList<>();
		   exitStatements.add("exit");
		   if (size < MAX_LEVEL_SIZE) //prevents next as eligible exit statement if at max level
		   {
			    exitStatements.add("next");
		   }
		  
		   double averageScore = 0;
		   int totalGuesses = 0;
		   String choice = "";
		   String answer = "-9999"; //initial value outside bounds of possible entries
		   int correctFirst = 0;
		   
		   while (!exitStatements.contains(choice))
		   {
		      
		   int attemptsAtQuestion = 0;
		   EquationGenerator myEquation = new EquationGenerator(size);
		   String generatedEquation = myEquation.getEquation();
		   
		   while (generatedEquation.contains("% 0"))//prevents potential arithmetic exception due to divide by 0
		   {
			   myEquation = new EquationGenerator(size);
			   generatedEquation = myEquation.getEquation();
		   }
		   
		   EquationEvaluator equationEvaluator = new EquationEvaluator(generatedEquation);
		   equationEvaluator.evaluate();
		   int result = equationEvaluator.getResult();
		  
		   toConsoleAndLog(generatedEquation + " = ?");
		   while (Integer.valueOf(answer) != result)
		   {
			   answer = input.nextLine().trim();
			   toLog(answer);
			   attemptsAtQuestion++;
			   totalGuesses++;
			   
			   
			   while (!isValidFormat(answer)) //validation prevents possible conversion exception
			   {
				   toConsoleAndLog("Invalid answer format, try again");
				   toConsoleAndLog(generatedEquation + " = ?");
				   answer = input.nextLine().trim();
				   toLog(answer);
				   attemptsAtQuestion++;
				   totalGuesses++;
			   }
			   
			   if ( Integer.valueOf(answer) == result && attemptsAtQuestion == 1)
			   {
				   correctFirst++; 
				
				   toConsoleAndLog(correctAnswer[randomResponse.nextInt(5)]);
			   }
			   
			   else if ( Integer.valueOf(answer) == result)
			   {
				   toConsoleAndLog(correctAnswer[randomResponse.nextInt(5)]);
				   
			   }
			   else
			   {
				   toConsoleAndLog(wrongAnswer[randomResponse.nextInt(5)]);
				   toConsoleAndLog(generatedEquation + " = ?");
			   }
		   }
		   
		   answer = "-9999"; //resets to avoid false positive if back to back questions have same answer
		   
		   
		   //conditionals to allow exit of level loop
		   if (correctFirst >= 5 && size < MAX_LEVEL_SIZE)
		   {
			   toConsoleAndLog("\nEnter 'next' to move onto the next difficulty level");
			   toConsoleAndLog("Enter 'exit' to terminate the program");
			   toConsoleAndLog("Any other input will continue at current difficulty.");
			   choice = input.nextLine();
			   toLog(choice);
		   }
		   else if(correctFirst >= 5)
		   {
			   toConsoleAndLog("\nEnter 'exit' to terminate program.");
			   toConsoleAndLog("Any other input will continue at current difficulty.");
			   choice = input.nextLine();
			   toLog(choice);
		   }
		   
		   }
		   //should not be possible, but just in case
		   if (totalGuesses != 0) 
		   {
		   averageScore = (double)correctFirst / totalGuesses * 100;
		   }
		   levelScores.add(averageScore); //adds averageScore to global ArrayList of scores
		   return choice;
	   }
	   
	    //***************************************************************
	    //
	    //  Method:       developerInfo (Non Static)
	    // 
	    //  Description:  The developer information method of the program
	    //
	    //  Parameters:   None
	    //
	    //  Returns:      N/A 
	    //
	    //**************************************************************
	    public void developerInfo()
	    {
	       System.out.println("Name:     Levi Yoder");
	       System.out.println("Course:   COSC 4301 Modern Programming");
	       System.out.println("Program:  4");
	       System.out.println("Due Date: 04/28/2025\n");
	    } // End of the developerInfo method
	   
}