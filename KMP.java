import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class KMP {
	

	
	//ANSI colors
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	
	
	static String readLine;// a line of text read from the given document
	static String pattern;// pattern to be searched in the document
	
	/**
	 * Given a pattern and an text document, it uses the KMP algorithm to search the pattern within the document.
	 * Occurrences are then printed using {@link #report(int, int)}.
	 * 
	 * @param args[0] the pattern to be searched
	 * @param args[1] the path of the document to be searched within. The path is relative, so if the document is in the same folder only 
	 * the name is necessary.
	 * @throws NotEnoughArgumentsException if not enough arguments are passed by the command line.
	 * @throws NonASCIIPattern if a line from the loaded file is found to not use an Non-ASCII character(byte).
	 */
	public static void main(String[] args) throws Exception{
		
		if(args.length < 2) {
			throw new NotEnoughArgumentsException("Please provide both a string to be searched and text file to be searched within.");
		}
				
		loadPattern(args);
		
		int[] b = preprocessPattern(pattern);
		
		InputStream fileStream = loadFile(args);
		BufferedReader bufferedFileReader = new BufferedReader(new InputStreamReader(fileStream, StandardCharsets.US_ASCII));
		readLine = bufferedFileReader.readLine();
		
		for(int row = 1; readLine != null; row++) {
			

			//Tests if the file consists of ASCII characters
			if(readLine.matches("\\A\\p{ASCII}*\\z") == false) {
				bufferedFileReader.close();
				throw new NonASCIIPattern("Please give an ASCII File.");
	        }
			
			kmpSearchLine(b, row);
			
			readLine = bufferedFileReader.readLine();
			
		}
		bufferedFileReader.close();
		return;
	}
	
	/**
	 * Searches a line of the given document for the given pattern using the KMP search algorithm.
	 * 
	 * @param b intArray from the preprocessing of the given pattern from {@link #preprocessPattern(String)}.
	 * @param row the line which is being searched.
	 */
	private static void kmpSearchLine(int[] b, int row) {
		
		int i=0;
		int j=0;
		
		while (i < readLine.length()) {
		    while (j>=0 && readLine.charAt(i) != pattern.charAt(j)) {
		    	j=b[j];
		    }
		    
		    i++;
		    j++;
		    
		    if (j==pattern.length()){	
		        report(row, i-j);
		        j=b[j];
		    }
		}
		
		return;
	}
	/**
	 * Loads the text file into a InputStream, if it is not a Text file it interprets its bytes as ASCII characters and loads it.
	 * Throws exception in case no file is accessible with the given path.
	 * 
	 * @param args command line arguments.
	 * @return A InputStream of the wished text file.
	 * @throws FileNotAvailableException if the file can't be found nor open
	 */
	private static InputStream loadFile(String[] args) throws Exception {
		String fileName = args[1];
		
		InputStream fileStream = KMP.class.getResourceAsStream(fileName);
		if(fileStream == null) {
			throw new FileNotAvailableException("File could not be found nor open.");
		}

		return fileStream;
	}
	/**
	 * loads the pattern by checking if it is valid.
	 * 
	 * @param args command line arguments.
	 * @throws EmptyStringException if the given pattern is a empty String.
	 * @throws NonASCIIPattern if the pattern uses non-ASCII characters.
	 */
	private static void loadPattern(String[] args) throws Exception {
		
		pattern = args[0];
		
		if (pattern.equals("")) {
			throw new EmptyStringException("Please provide a non-empty string to be searched.");
		}
		
		if(pattern.matches("\\A\\p{ASCII}*\\z") == false) {
			throw new NonASCIIPattern("Please give an ASCII string to be searched.") ;
		}
	}
	/**
	 * Prints the occurrences of the the pattern. The occurrence is printed as follow "ColumnNumber: RowNumber: LineWithTheOcurrence".
	 * The occurrence is marked with ANSI colors within the printed line.
	 * 
	 * @param row the line where the pattern was found.
	 * @param i the character in that line where the first character of the pattern was found.
	 */
	private static void report(int row, int i) {
		
		System.out.println(row + ": " + (i+1) + ":"+ readLine.substring(0, i) +
						ANSI_RED + readLine.substring(i, i+pattern.length()) + ANSI_RESET +
						readLine.substring(i+pattern.length()));
		
	
		return;
	}
	
	/**
	* Preprocesseses the pattern according to the KMP algorithm.
	* 
 	* @param pattern String contained the pattern that should be searched for.
 	* @return b array of integers with the preprocessed information.
 	*/
	private static int[] preprocessPattern(String pattern) {
		
		int[] b = new int[pattern.length()+1];
		b[0] = -1;
		
		for(int i = 0, j = -1; i < pattern.length(); i++, j++, b[i]=j) {
			
			while (j>=0 && pattern.charAt(i) != pattern.charAt(j)) {
				j=b[j];
			}
	        
		}

		return b;
	}

}
