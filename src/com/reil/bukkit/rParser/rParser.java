package com.reil.bukkit.rParser;
import java.util.ArrayList;

import org.bukkit.ChatColor;


public class rParser {
	protected static final int lineLength = 312; 
	
	public static String parseMessage(String message, String [] replace, String[] with){
		String parsed = message;
		for(int i = 0; i < replace.length; i++) {
			parsed = parsed.replaceAll(replace[i], with[i]);
		}
		String parsed2 = new String();
		StringBuilder output = new StringBuilder();
		for (String toParse : parsed.split("\n")) {
			output.append(lastColor(parsed2) + combineSplit(0, wordWrap(toParse), "\n") + "\n");
		}
		return output.toString();
	}
	/*
	 * Finds the last color sequence used in the string
	 */
	public static String lastColor(String findColor) {
		int i = findColor.lastIndexOf('�'); 
		if (i != -1 && i != findColor.length() - 1)
			return "�" + findColor.charAt(i+1);		
		else return "";
	}
	
	/* 
	 * 
	 */
	public static String combineSplit(int beginHere, String [] split, String seperator){
		String combined = new String(split[beginHere]);
		for (int i = beginHere + 1; i < split.length; i++){
			combined = combined + seperator + split[i];
		}
		return combined;
	}
	
	
	public static String[] wordWrap(String msg){
		return wordWrap(msg, "", lineLength);
	}
	public static String[] wordWrap(String msg, String prefix){
		return wordWrap(msg, prefix, lineLength);
	}
	public static String[] wordWarp(String msg, int lineLength){
		return wordWrap(msg, "", lineLength);
	}
	public static String[] wordWrap(String msg, String prefix, int lineLength){
    	//Split each word apart
    	ArrayList<String> split = new ArrayList<String>();
    	for(String in : msg.split(" "))
			split.add(in);
    	
    	//Create an arraylist for the output
    	ArrayList<String> out = new ArrayList<String>();
    	//While i is less than the length of the array of words
    	while(!split.isEmpty()){
    		int len = 0;
        	
        	//Create an arraylist to hold individual words
        	ArrayList<String> words = new ArrayList<String>();

    		//Loop through the words finding their length and increasing
    		//j, the end point for the sub string
    		while(!split.isEmpty() && split.get(0) != null && len <= lineLength)
    		{
    			int wordLength = msgLength(split.get(0)) + 4;
    			
    			//If a word is too long for a line
    			if(wordLength > lineLength)
    			{
        			String[] tempArray = wordCut(len, split.remove(0), lineLength);
    				words.add(tempArray[0]);
    				
        			split.add(tempArray[1]);
    			}

    			//If the word is not too long to fit
    			len += wordLength;
    			if( len < lineLength)
    				words.add(split.remove(0));
    		}
    		//Merge them and add them to the output array.
    		out.add( combineSplit(0,
    				words.toArray(new String[words.size()]), " ") + " " );
    	}
    	//Convert to an array and return
    	return out.toArray(new String[out.size()]);
    }
	
	//=====================================================================
	//Function:	msgLength
	//Input:	String str: The string to find the length of
	//Output:	int: The length on the screen of a string
	//Use:		Finds the length on the screen of a string. Ignores ChatColor.
	//=====================================================================
	 public static int msgLength(String str){
		int length = 0;
		//Loop through all the characters, skipping any color characters
		//and their following color codes
		for(int x = 0; x<str.length(); x++)
		{
			if(str.charAt(x) == '�' /*|| str.charAt(x) == ChatColor.White.charAt(0)*/)
			{
				if(colorChange(str.charAt(x + 1)) != null)
				{
					x++;
					continue;
				}
			}
			int len = charLength(str.charAt(x));
			length += len;
		}
		return length;
    }
	 
	//=====================================================================
	//Function:	wordCut
	//Input:	String str: The string to find the length of
	//Output:	String[]: The cut up word
	//Use:		Cuts apart a word that is too long to fit on one line
	//=====================================================================
	 private static String[] wordCut(int lengthBefore, String str, int lineLength){
		int length = lengthBefore;
		//Loop through all the characters, skipping any color characters
		//and their following color codes
		String[] output = new String[2];
		int x = 0;
		while(length < lineLength && x < str.length())
		{
			int len = charLength(str.charAt(x));
			if( len > 0)
				length += len;
			else
				x++;
			x++;
		}
		if(x > str.length())
			x = str.length();
		//Add the substring to the output after cutting it
		output[0] = str.substring(0, x);
		//Add the last of the string to the output.
		output[1] = str.substring(x);
		return output;
    }
	 
	 
	//=====================================================================
	//Function:	charLength
	//Input:	char x: The character to find the length of.
	//Output:	int: The length of the character
	//Use:		Finds the visual length of the character on the screen.
	//=====================================================================
    private static int charLength(char x)
    {
    	if("i.:,;|!".indexOf(x) != -1)
			return 2;
		else if("l'".indexOf(x) != -1)
			return 3;
		else if("tI[]".indexOf(x) != -1)
			return 4;
		else if("fk{}<>\"*()".indexOf(x) != -1)
			return 5;
		else if("abcdeghjmnopqrsuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890\\/#?$%-=_+&^".indexOf(x) != -1)
			return 6;
		else if("@~".indexOf(x) != -1)
			return 7;
		else if(x==' ')
			return 4;
		else
			return -1;
    }
    
  //=====================================================================
	//Function:	colorChange
	//Input:	char colour: The color code to find the color for
	//Output:	String: The color that the code identified 
	//Use:		Finds a color giving a color code
	//=====================================================================
	public static String colorChange(char colour)
	{
		ChatColor color;
		switch(colour)
		{
			case '0':
				color = ChatColor.BLACK;
				break;
			case '1':
				color = ChatColor.DARK_BLUE;
				break;
			case '2':
				color = ChatColor.DARK_GREEN;
				break;
			case '3':
				color = ChatColor.DARK_AQUA;
				break;
			case '4':
				color = ChatColor.DARK_RED;
				break;
			case '5':
				color = ChatColor.DARK_PURPLE;
				break;
			case '6':
				color = ChatColor.GOLD;
				break;
			case '7':
				color = ChatColor.GRAY;
				break;
			case '8':
				color = ChatColor.DARK_GRAY;
				break;
			case '9':
				color = ChatColor.BLUE;
				break;
			case 'a':
				color = ChatColor.GREEN;
				break;
			case 'b':
				color = ChatColor.AQUA;
				break;
			case 'c':
				color = ChatColor.RED;
				break;
			case 'd':
				color = ChatColor.LIGHT_PURPLE;
				break;
			case 'e':
				color = ChatColor.YELLOW;
				break;
			case 'f':
				color = ChatColor.WHITE;
				break;
			case 'A':
				color = ChatColor.GREEN;
				break;
			case 'B':
				color = ChatColor.AQUA;
				break;
			case 'C':
				color = ChatColor.RED;
				break;
			case 'D':
				color = ChatColor.LIGHT_PURPLE;
				break;
			case 'E':
				color = ChatColor.YELLOW;
				break;
			case 'F':
				color = ChatColor.WHITE;
				break;
			default:
				return null;
		}
		return color.toString();
	}
}
