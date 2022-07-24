import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class hw1_kemal_demirel 
{
	public static String parseFile(String in)
	{
		Scanner inputStream = null;
		String myString = "";
		try
		{
			inputStream = new Scanner(new FileInputStream(in));
			while(inputStream.hasNextLine())
			{
				myString += inputStream.nextLine();
				myString += "\n";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		inputStream.close();
		return myString;
	}
	public static boolean isItSpecialWord(char c)
	{
		if(c == '(' || c == ')' || c == ':' || c == '+' || c == '-' || c == '/' || c == '*' || c == ';' || c == ',' || c == '\t' || c == ' ' || c == '\n' || c == '<' || c == '>')
		{
			return true;
		}
		return false;
	}
	public static void save(String out,String parsedString)
	{
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(out);
			String addingString = "";
			for(int i=0; i<parsedString.length(); i++)
			{
				addingString += parsedString.charAt(i);	
				if(parsedString.charAt(i) == '%')
				{
					i++;
					for( ; parsedString.charAt(i) != '%'; i++) {}
					addingString = "";
					continue;
				}
				if(isItSpecialWord(parsedString.charAt(i)))
				{
					char temp = parsedString.charAt(i);
					
					if(isItResWord(addingString.substring(0, addingString.length()-1)))
					{
						outputStream.println("Next token is RES_WORD\t\tNext lexeme is "+addingString.substring(0, addingString.length()-1).toLowerCase() );
						addingString = "";
					}
					else if(isItIdentifier(addingString.substring(0, addingString.length()-1)))
					{
						outputStream.println("Next token is identifier\tNext lexeme is "+addingString.substring(0, addingString.length()-1).toLowerCase());
						addingString = "";
					}
					else if(isItIntegerConstant(addingString.substring(0, addingString.length()-1)))
					{
						outputStream.println("Next token is INT_LIT\t\tNext lexeme is "+addingString.substring(0, addingString.length()-1).toLowerCase());
						addingString = "";
					}
					else if(addingString.length() > 2)
					{
						outputStream.println("Next token is UNKNOWN\t\tNext lexeme is "+addingString.substring(0, addingString.length()-1).toLowerCase());
						addingString = "";
					}
					if(isItSpecialWord(temp) && temp!='\t' && temp != '\n' && temp != ' ')
					{
						if(temp == '(')	outputStream.println("Next token is LPARANT\t\tNext lexeme is "+temp);
						else if(temp == ')') outputStream.println("Next token is RPARANT\t\tNext lexeme is "+temp);
						else if(temp == '+') outputStream.println("Next token is ADD\t\tNext lexeme is "+temp);
						else if(temp == '-') outputStream.println("Next token is SUBT\t\tNext lexeme is "+temp);
						else if(temp == '/') outputStream.println("Next token is DIV\t\tNext lexeme is "+temp);
						else if(temp == '*') outputStream.println("Next token is MULT\t\tNext lexeme is "+temp);
						else if(temp == ';') outputStream.println("Next token is SEMICOLON\t\tNext lexeme is "+temp);
						else if(temp == ',') outputStream.println("Next token is COMMA\t\tNext lexeme is "+temp);
						if(temp == '<') 
						{
							if(parsedString.charAt(i+1) == '>')	outputStream.println("Next token is NOTEQ\t\tNext lexeme is <>");
							else if(parsedString.charAt(i+1) == '=')	outputStream.println("Next token is LESS_EQ\t\tNext lexeme is <=");
							else	outputStream.println("Next token is LESS\t\tNext lexeme is "+temp);
						}
						if(temp == '>') 
						{
							if(parsedString.charAt(i+1) == '=')	outputStream.println("Next token is GRE_EQ\t\tNext lexeme is >=");
							else	outputStream.println("Next token is GREATER\t\tNext lexeme is "+temp);
						}
						if(temp == ':')
						{
							if(parsedString.charAt(i+1) == '=')	outputStream.println("Next token is ASSIGNM\t\tNext lexeme is :=");
							else	outputStream.println("Next token is COLON\t\tNext lexeme is "+temp);
						}
					}
					addingString = "";
				}
			}
			outputStream.println("Next token is EOF\t\tNext lexeme is end of file");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		outputStream.close();
	}
	public static boolean isItResWord(String string)
	{
		if(string.equalsIgnoreCase("begin") || string.equalsIgnoreCase("end") || string.equalsIgnoreCase("if") || string.equalsIgnoreCase("then") || string.equalsIgnoreCase("else") || string.equalsIgnoreCase("while") 
				|| string.equalsIgnoreCase("program") || string.equalsIgnoreCase("integer") || string.equalsIgnoreCase("var") || string.equalsIgnoreCase("end of file"))
		{
			
			return true;
		}
		return false;
	}
	public static boolean isItIdentifier(String string)
	{
		if(string.equals(""))
		{
			return false;
		}
		if(string.length() > 15)
		{
			return false;
		}
		if( (string.charAt(0) >= 'A' && string.charAt(0) <= 'Z') || (string.charAt(0) >= 'a' && string.charAt(0) <= 'z'))
		{
			for(int j=0; j<string.length(); j++)
			{
				if(!((string.charAt(j)>= 'A' && string.charAt(j)<= 'Z') || (string.charAt(j)>= 'a' && string.charAt(j)<= 'z') || (string.charAt(j)>= '1' && string.charAt(j)<= '9')))
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean isItIntegerConstant(String string)
	{
		try
		{
			Integer num = Integer.parseInt(string);
		}catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public static void main(String[] args) 
	{
		String parsedString = parseFile(args[0]);
		save(args[1],parsedString);
	}

}
