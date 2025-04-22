//********************************************************************
//
//  Author:        Levi Yoder
//
//  Program #:     Four
//
//  File Name:     EquationEvaluator.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Instructor:    Prof. Fred Kumi 
//
//  Description:   Class evaluates numerical value of supported string
//
//********************************************************************

import java.util.Stack;
import java.util.Arrays;
import java.util.List;


public class EquationEvaluator {

	private String equation;
	private int result;
	Stack<Integer> numbers = new Stack<>();
    Stack<Character> operators = new Stack<>();
    List<Character> allowedOperators= Arrays.asList('+', '-', '*', '%');
	
	public EquationEvaluator(String equation)
	{
		try
		{
		if(!equation.matches("[0-9 %*+-]+"))
		{
			throw new IllegalArgumentException(
					"Equation contains unsupported characters");	
		}
		
		this.equation = equation;
	}
	
	catch (IllegalArgumentException e)
	{
		System.out.println(e.toString());
	}
	}
		
	public int getResult() //simple getter, no need for further commentary
	{
		return this.result;
	}
	
	
	   //***************************************************************
	   //
	   //  Method:       evaluate
	   // 
	   //  Description:  iterates through equation, pushing and popping
	   //                operands and operators off of relevant stacks
	   //
	   //  Parameters:   None
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
	public void evaluate()
	{
		int i = 0;
        while (i < this.equation.length()) {
            char ch = this.equation.charAt(i);

            if (Character.isDigit(ch)) 
            {
            	int num = Character.getNumericValue(ch);//prefer this way        	
                numbers.push(num);
                i++;
            } 
            else if (allowedOperators.contains(ch)) 
            {
                while (!operators.isEmpty() && operatorPrecedence(operators.peek()) >= 
                		operatorPrecedence(ch)) 
                {
                    processOperator(operators.pop());
                }
                operators.push(ch);
                i++;
            } 
            else //present to allow equation to have spaces for readability
            {
                
                i++;
            }
        }

        while (!operators.isEmpty()) 
        {
            processOperator(operators.pop());
        }

        this.result = numbers.pop();
	}
	
	
	   //***************************************************************
	   //
	   //  Method:       processOperator
	   // 
	   //  Description:  pops the top two results off of number stack
	   //                then pushes result of passed operation applied
	   //                to the popped values back to the stack
	   //
	   //  Parameters:   char representation of a supported operator
	   //
	   //  Returns:      N/A 
	   //
	   //**************************************************************
	   private  void processOperator(char operator) {
	        int b = numbers.pop();
	        int a = numbers.pop();
	        switch (operator) {
	            case '+': numbers.push(a + b); 
	            				break;
	            case '-': numbers.push(a - b); 
	            				break;
	            case '*': numbers.push(a * b); 
	            				break;
	            case '%': numbers.push(a % b); 
	            				break;
	        }
	    }
	   //***************************************************************
	   //
	   //  Method:       operatorPrecedence
	   // 
	   //  Description:  iterates through equation, pushing and popping
	   //                operands and operators off of relevant stacks
	   //
	   //  Parameters:   char representation of operator
	   //
	   //  Returns:      int representation of operator precedence
	   //
	   //**************************************************************
	   private  int operatorPrecedence(char operator) 
	   {
		   int result = 0;
	        switch (operator) {
	            case '+':
	            case '-': result = 1; 
	            			break;
	            case '*':
	            case '%': result =  2; 
	            			break;
	            default: result =  0;
	        }
	        return result;
	   }
}
