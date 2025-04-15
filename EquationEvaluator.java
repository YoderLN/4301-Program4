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
		System.err.println(e.toString());
	}
	}
	
	public String getEquation()
	{
		return this.equation;
	}
	
	public int getResult()
	{
		return this.result;
	}
	
	public void evaluate()
	{
		int i = 0;
        while (i < this.equation.length()) {
            char ch = this.equation.charAt(i);

            if (Character.isDigit(ch)) {
                // Convert char digit (e.g., '3') to int (3)
                //int num = ch - '0';
            	
            	int num = Character.getNumericValue(ch);
                
            	
                numbers.push(num);
                i++;
            } else if (allowedOperators.contains(ch)) 
            {
                while (!operators.isEmpty() && operatorPrecedence(operators.peek()) >= 
                		operatorPrecedence(ch)) 
                {
                    processOperator(numbers, operators.pop());
                }
                operators.push(ch);
                i++;
            } else 
            {
                //present to allow equation to have spaces for readability
                i++;
            }
        }

        while (!operators.isEmpty()) {
            processOperator(numbers, operators.pop());
        }

        this.result = numbers.pop();
	}
	
	   private  void processOperator(Stack<Integer> numbers, char operator) {
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
	   
	   
	   private  int operatorPrecedence(char op) 
	   {
		   int result = 0;
	        switch (op) {
	            case '+':
	            case '-': result = 1; break;
	            case '*':
	            case '%': result =  2; break;
	            default: result =  0;
	        }
	        return result;
	   }
	@Override
	public String toString()
	{
		return "haha";
	}
}
