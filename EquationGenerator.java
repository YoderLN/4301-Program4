import java.util.ArrayList;
import java.security.SecureRandom;

public class EquationGenerator {

	private char [] legalOperators = {'+', '-', '%', '*'};
	private ArrayList<String> equationTerms = new ArrayList<>();
	private String equation = "";
	SecureRandom secureRandom = new SecureRandom();
	int equationSize;
	
	public EquationGenerator(int equationSize)
	{
		
		this.equationSize = equationSize;
		populateEquation();
	}
	
	public String getEquation()
	{
		return this.equation.trim();
	}
	
	public void populateEquation()
	{
		for (int i = 0; i< equationSize; i++)
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
		
		for(String element : equationTerms)
		{
			equation = equation + " " + element;
		}
	}
	
}
