//********************************************************************
//
//  Author:        Levi Yoder
//
//  Program #:     Four
//
//  File Name:     EquationGenerator.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Instructor:    Prof. Fred Kumi 
//
//  Description:   Class generated random equation based on allowable 
//                 equation size and operators
//
//********************************************************************

import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class EquationGenerator {

	//backbone of randomization, do not remove
	SecureRandom secureRandom = new SecureRandom();
	
	//establishes what is and is not supported by class
	private char [] legalOperators = {'+', '-', '%', '*'};
	private List<Integer> legalSize = Arrays.asList(3, 5, 7);
	
	//variables for generating equation
	private List<String> equationTerms = new ArrayList<>();
	private String equation; 
	
	//controls number of operants and operands
	int equationSize;
	
	public EquationGenerator(int equationSize)
	{
		try
		{
		if (!legalSize.contains(equationSize)) //validation for handling improper instantiation
		{
			throw new IllegalArgumentException(
					"Equation size outside of current allowable parameters.");
		}
		
		
		this.equationSize = equationSize;
		}
		catch (IllegalArgumentException e)
		{
			System.err.println(e.toString());
		}
	}
	
	public String getEquation()
	{
		return this.equation;
	}
	
	   //***************************************************************
	   //
	   //  Method:       populateEquation
	   // 
	   //  Description:  alternates adding a random integer less than 10
	   //                and a supported operator to an arrayList of Strings
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
	public void populateEquation()
	{
		for (int i = 0; i< this.equationSize; i++)
		{
			if (i %2 == 0)
			{
				int num = secureRandom.nextInt(10);
				equationTerms.add(String.valueOf(num));
			}
			
			else
			{
				int num = secureRandom.nextInt(4);
				equationTerms.add(Character.toString(legalOperators[num]));
			}
		}
		buildEquationString();
		
	}
	
	   //***************************************************************
	   //
	   //  Method:       buildStringEquation
	   // 
	   //  Description:  iterates through ArrayList of strings,
	   //                adding each term in sequence to destination string
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************	
	public void buildEquationString()
	{
		for(String element : equationTerms)
		{
			if (this.equation == null)
			{
				this.equation = element;
			}
			
			else
			{
			this.equation = this.equation + " " + element;
			}
		}
	}
}
