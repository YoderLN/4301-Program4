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
import java.security.SecureRandom;

public class Program4 {
	private OutputLogger outputLogger = new OutputLogger();//enclosed all logging operations to class
	private final int MAX_LEVEL_SIZE = 7;
	private ArrayList<Double> levelScores = new ArrayList<>();
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
	//***************************************************************
	//
	//  Method:       main
	// 
	//  Description:  The main method of the program
	//
	//  Parameters:   A String Array
	//
	//  Returns:      N/A 
	//
	//**************************************************************
	public static void main(String[] args)
	{
		Program4 obj = new Program4();
		
		obj.developerInfo();
		try 
		{
			obj.primaryLoop();
			obj.displayAverages();		
		}
		catch (Exception e)
		{
			System.err.println(e.toString() + "in " + e.getStackTrace()[0].getMethodName());
		}
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
			outputLogger.toConsoleAndLog(result);
			}	
			if (levelScores.get(0) < 80)
			{
				outputLogger.toConsoleAndLog("Please ask your teacher for extra help.");
			}
		outputLogger.closeOutput();
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
		   char firstCh = answer.charAt(0);	   
		   //early exit check
		   if (answer.length() < 1 //empty string
				   || answer.length() > 6 //string too long
				   || (answer.length() == 1 && firstCh == '-') //string only a minus
				   || (answer.length() > 1 && (!Character.isDigit(firstCh) && firstCh != '-'))
				   ) //string length more than 1 and first digit not minus or number
		   {
			   result = false;
		   }	   
		   //Initial check passed, moves to here
		   else
		   {
			   for(int i = 1; i < answer.length(); i++) 
			   {
				   char current = answer.charAt(i);
				   if (!Character.isDigit(current))//validates every character after first is digit
				   {
					   result = false;
				   }
			   }
		   }		   		   
		   return result;
	   }
	   //***************************************************************
	   //
	   //  Method:       primaryLoop
	   // 
	   //  Description:  controlling function for individual levels
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
	   public void primaryLoop()
	   {
		   outputLogger.outputHeader();
		   String result1 = "";
		   String result2 = "";
		   String result3 = "";
		   
		   while (!result1.equals("exit") && !result2.equals("exit") && !result3.equals("exit"))
		   {
			   if (result1.equals(""))
			   {
				   //call first level			   
				   outputLogger.toConsoleAndLog("Basic Level");
				   result1 = levelLoop(3);
				   outputLogger.toConsoleAndLog("Basic Level done.");	   
			   }
			   else if (result2.equals(""))
			   {
				   //call second level
				   outputLogger.toConsoleAndLog("Intermediate Level");
				   result2 = levelLoop(5);
				   outputLogger.toConsoleAndLog("Intermediate Level done.");
			   }
			   else if (result3.equals(""))
			   {
				   //call third level
				   outputLogger.toConsoleAndLog("Advanced Level");
				   result3 =levelLoop(7);
				   outputLogger.toConsoleAndLog("Advanced Level done");
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
		   int correctFirst = 0;
		 //keeps user in loop of generate, evaluate, answer until accepted exit statement is given
		   while (!exitStatements.contains(choice))
		   {
			   String answer = "-9999"; //initial value outside bounds of possible entries   
			   int attemptsAtQuestion = 0; //resets to 0 with every new question
			   EquationGenerator myEquation = new EquationGenerator(size);
			   myEquation.populateEquation();
			   String generatedEquation = myEquation.getEquation();
		   
			   while (generatedEquation.contains("% 0"))//prevents potential arithmetic exception due to divide by 0
			   {
				   myEquation = new EquationGenerator(size);
				   myEquation.populateEquation();
				   generatedEquation = myEquation.getEquation();
			   }
		   
			   EquationEvaluator equationEvaluator = new EquationEvaluator(generatedEquation);
			   equationEvaluator.evaluate();
			   int result = equationEvaluator.getResult();
		  
			   outputLogger.toConsoleAndLog(generatedEquation + " = ?");
			   while (Integer.valueOf(answer) != result)//loop continues until correct answer given
			   {
				   answer = input.nextLine().trim();
				   outputLogger.toLog(answer);
				   attemptsAtQuestion++;
				   totalGuesses++;
			   	   
				   while (!isValidFormat(answer)) //validation prevents possible conversion exception
				   {
					   outputLogger.toConsoleAndLog("Invalid answer format, try again");
					   outputLogger.toConsoleAndLog(generatedEquation + " = ?");
					   answer = input.nextLine().trim();
					   outputLogger.toLog(answer);
					   attemptsAtQuestion++;
					   totalGuesses++;
				   }
			   
				   if (Integer.valueOf(answer) == result && attemptsAtQuestion == 1)
				   {
					   correctFirst++; 	
					   outputLogger.toConsoleAndLog(correctAnswer[randomResponse.nextInt(5)]);
				   }
			   
				   else if ( Integer.valueOf(answer) == result)
				   {
					   outputLogger.toConsoleAndLog(correctAnswer[randomResponse.nextInt(5)]);	   
				   }
				   else
				   {
					   outputLogger.toConsoleAndLog(wrongAnswer[randomResponse.nextInt(5)]);
					   outputLogger.toConsoleAndLog(generatedEquation + " = ?");
				   }
			   }//loop exits here when input matches result
			   		//conditionals to allow exit of function primary loop
		   		if (correctFirst >= 5 && size < MAX_LEVEL_SIZE)
		   		{
		   			outputLogger.toConsoleAndLog("\nEnter 'next' to move onto the next difficulty level");
		   			outputLogger.toConsoleAndLog("Enter 'exit' to terminate the program");
		   			outputLogger.toConsoleAndLog("Any other input will continue at current difficulty.");
		   			choice = input.nextLine().toLowerCase();
		   			outputLogger.toLog(choice);
		   		}
		   		else if(correctFirst >= 5)
		   		{
		   			outputLogger.toConsoleAndLog("\nEnter 'exit' to terminate program.");
		   			outputLogger.toConsoleAndLog("Any other input will continue at current difficulty.");
		   			choice = input.nextLine().toLowerCase();
		   			outputLogger.toLog(choice);
		   		}		   
		   }
		   //should not be possible, but prevents possible divide by 0 exception
		   if (totalGuesses != 0) 
		   {
		   averageScore = (double)correctFirst / totalGuesses * 100;
		   }
		   levelScores.add(averageScore); //adds averageScore to global ArrayList of scores
		   return choice;
	   }
	    //***************************************************************
	    //
	    //  Method:       developerInfo
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
}//End of program4 class