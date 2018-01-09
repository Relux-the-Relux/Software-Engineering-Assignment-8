import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class KMPTest {

	//folder pointer to parent folder of KMP class, used for all tests, created in method setup()
	public static File classParentFolder;
	
	
	/**
	 * Setup of the folder pointer before the execution of all tests.
	 * @throws IOException
	 */
	@BeforeClass
	public static void setup() throws IOException {
		//get the path of the class files being used
		String tempFolderPath = KMP.class.getClassLoader().getResource(File.separator).getPath();
		//print out said path for testing purposes
		//TODO: delete once tests function as expected
		System.out.println("Zeile 31: "+tempFolderPath);
		
		int charIndex = tempFolderPath.lastIndexOf("/");
		tempFolderPath = tempFolderPath.substring(0, charIndex + 1);
		
		//appropriately instantiate folder pointer
		classParentFolder = new File(tempFolderPath);
		System.out.println("Zeile 39: "+classParentFolder.getPath());
	}
	
	
	//rule used to test if appropriate exceptions are being thrown
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	
	@Test(expected = EmptyStringException.class)
	/**
	 * Tests whether the program responds to an empty input string correctly
	 */
	public void testEmptyInputString() throws Exception {
		//tester input string - empty for this test
		String tester = "";	
		//parameter array
				String[] parameters = new String[2];
				parameters[0] = tester;
								
								
				File testEmptyInputString = classParentFolder.createTempFile("testEmptyInputString", ".txt", classParentFolder);
				BufferedWriter writer = new BufferedWriter(new FileWriter(testEmptyInputString));
				String SearchString = "Hier bin ich!";
				writer.write(SearchString);
				writer.close();
				
				//initialise second argument in arguments array as appropriate file
				parameters[1] = testEmptyInputString.getName();
				

				// catch the Output Stream 
			    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			    PrintStream printstream = new PrintStream(outStream);
			    KMP.main(parameters);
			    // save the old Stream
			    PrintStream old = System.out;
			    // use special stream
			    System.setOut(printstream);
			    KMP.main(parameters);
			    // put things back
			    System.out.flush();
			    System.setOut(old);
			    
			    // assert output
				String wanted = ("");
				
				// Compare character by character
				boolean stringsAreSame = true;
				char [] outputCharArray = outStream.toString().toCharArray();
				char [] wantedCharArray = wanted.toCharArray();
				int lineseparatorLenght = System.lineSeparator().length();
				for(int i=0; i<wanted.length();i++){
					if(outputCharArray[i]!=wantedCharArray[i]|| wanted.length()!=(outStream.toString().length()-lineseparatorLenght)){
						stringsAreSame = false;
						break;
					}
				}
				
				Assert.fail("Expected EmptyStringException");
				testEmptyInputString.delete();
	}
	
	@Test
	/**
	 * Tests whether the program responds correctly to an empty input file.
	 * Can also test the case in which the file is shorter than the input string.
	 */
	public void testEmptyInputFile() throws Exception{
		String tester = "abc";
		
		//parameter array
		String[] parameters = new String[2];
		parameters[0] = tester;
						
						
		File testEmptyInputFile = classParentFolder.createTempFile("testEmptyInputFile", ".txt", classParentFolder);
		BufferedWriter writer = new BufferedWriter(new FileWriter(testEmptyInputFile));
		String SearchString = "";
		writer.write(SearchString);
		writer.close();
		
		//initialise second argument in arguments array as appropriate file
		parameters[1] = testEmptyInputFile.getName();
		

		// catch the Output Stream 
	    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	    PrintStream printstream = new PrintStream(outStream);
	    KMP.main(parameters);
	    // save the old Stream
	    PrintStream old = System.out;
	    // use special stream
	    System.setOut(printstream);
	    KMP.main(parameters);
	    // put things back
	    System.out.flush();
	    System.setOut(old);
	    
	    // assert output
		String wanted = ("");
		
		// Compare character by character
		boolean stringsAreSame = true;
		char [] outputCharArray = outStream.toString().toCharArray();
		char [] wantedCharArray = wanted.toCharArray();
		int lineseparatorLenght = System.lineSeparator().length();
		for(int i=0; i<wanted.length();i++){
			if(outputCharArray[i]!=wantedCharArray[i]|| wanted.length()!=(outStream.toString().length()-lineseparatorLenght)){
				stringsAreSame = false;
				break;
			}
		}
		
		assert(stringsAreSame==true);
		testEmptyInputFile.delete();
	}
	
	//TODO: is this one necessary if we have the above test method?
	//public void testFileShorterThanString()
	
	
	@Test
	/**
	 * Tests whether the program responds correctly to a single character input string
	 */
	public void testSingleCharInputString() throws Exception {
		
		//single character tester pattern
		String tester = "b";
		//parameter array
		String[] parameters = new String[2];
		parameters[0] = tester;
		
		//test first by inputting a file with no occurrences of the single character pattern
		
		File singleCharTester = classParentFolder.createTempFile("singleCharTester", ".txt", classParentFolder);//tempFolder.newFile("singleCharTester.txt");
		FileWriter writer = new FileWriter(singleCharTester);
		writer.write("memes are, in fact, neo-dadaism");
		writer.close();
		
		System.out.println("Zeile 98: "+singleCharTester);
		//initialise second argument in arguments array as appropriate file
		parameters[1] = singleCharTester.getName();
		KMP.main(parameters);
		
		
		//test whether anything has been written to the output stream
		//if correct, nothing should have been written
		//TODO: this implementation is wrong
		assert(System.out.toString() == null);
		
		singleCharTester.delete();
		
		//now test by inputting a file within which the pattern occurs once 
		File oneOccurence = classParentFolder.createTempFile("singleCharTester", ".txt", classParentFolder);//newFile("singleCharTester");
		writer = new FileWriter(oneOccurence);
		writer.write("the bastards will never get away with this");
		writer.close();
		
		parameters[1] = oneOccurence.getName();
		KMP.main(parameters);
		
		assert(System.out.toString() == "1:5: " + oneOccurence.toString()); 
		oneOccurence.delete();
	}
	
	@Test 
	/**
	 * Tests whether the program responds correctly to an input string that 
	 * overlaps with itself within the text
	 */
	public void testOverlappingStringOccurences() throws Exception{
		//Test String
				String tester = "HalloHallo";
						
				//parameter array
				String[] parameters = new String[2];
				parameters[0] = tester;
								
								
				File testOverlapping = classParentFolder.createTempFile("testOverlapping", ".txt", classParentFolder);
				BufferedWriter writer = new BufferedWriter(new FileWriter(testOverlapping));
				String SearchString = "HalloHallo";
				writer.write(SearchString+SearchString);
				writer.close();
				
				
				//initialise second argument in arguments array as appropriate file
				parameters[1] = testOverlapping.getName();
				

				// catch the Output Stream 
			    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			    PrintStream printstream = new PrintStream(outStream);
			    KMP.main(parameters);
			    // save the old Stream
			    PrintStream old = System.out;
			    // use special stream
			    System.setOut(printstream);
			    KMP.main(parameters);
			    // put things back
			    System.out.flush();
			    System.setOut(old);
			    
			    // assert output
				String wanted = ("1: 1:" + KMP.ANSI_RED +"HalloHallo"+KMP.ANSI_RESET+"HalloHallo"+System.lineSeparator()+
								"1: 6:Hallo"+KMP.ANSI_RED+"HalloHallo"+KMP.ANSI_RESET+"Hallo"+System.lineSeparator()+
								"1: 11:HalloHallo"+KMP.ANSI_RED+"HalloHallo"+KMP.ANSI_RESET);
				
				// Compare character by character
				boolean stringsAreSame = true;
				char [] outputCharArray = outStream.toString().toCharArray();
				char [] wantedCharArray = wanted.toCharArray();
				int lineseparatorLenght = System.lineSeparator().length();
				for(int i=0; i<wanted.length();i++){
					if(outputCharArray[i]!=wantedCharArray[i]|| wanted.length()!=(outStream.toString().length()-lineseparatorLenght)){
						stringsAreSame = false;
						break;
					}
				}
				
				assert(stringsAreSame==true);
				testOverlapping.delete();
				
	}
	
	@Test
	/**
	 * Tests whether the program responds correctly when there are empty lines 
	 * in the input file. 
	 * Also tests for the input string being longer than a line.
	 */
	public void testEmptyLines() throws Exception  {
		//Test String
		String tester = "Hallo";
				
		//parameter array
		String[] parameters = new String[2];
		parameters[0] = tester;
						
						
		File emptyLineTester = classParentFolder.createTempFile("emptyLineTester", ".txt", classParentFolder);
		BufferedWriter writer = new BufferedWriter(new FileWriter(emptyLineTester));
		String SearchString = "Hallo World!";
		writer.write("Im your Son.");
		writer.newLine();
		writer.write("Hold the Door!");
		writer.newLine();
		writer.newLine();
		writer.write(SearchString);
		writer.close();
		
		
		//initialise second argument in arguments array as appropriate file
		parameters[1] = emptyLineTester.getName();
		

		// catch the Output Stream 
	    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	    PrintStream printstream = new PrintStream(outStream);
	    KMP.main(parameters);
	    // save the old Stream
	    PrintStream old = System.out;
	    // use special stream
	    System.setOut(printstream);
	    KMP.main(parameters);
	    // put things back
	    System.out.flush();
	    System.setOut(old);
	    
	    // assert output
		String wanted = ("4: 1:" + KMP.ANSI_RED + "Hallo" + KMP.ANSI_RESET + " World!");
		
		// Compare character by character
		boolean stringsAreSame = true;
		char [] outputCharArray = outStream.toString().toCharArray();
		char [] wantedCharArray = wanted.toCharArray();
		int lineseparatorLenght = System.lineSeparator().length();
		for(int i=0; i<wanted.length();i++){
			if(outputCharArray[i]!=wantedCharArray[i]|| wanted.length()!=(outStream.toString().length()-lineseparatorLenght)){
				stringsAreSame = false;
				break;
			}
		}
		
		assert(stringsAreSame==true);
		emptyLineTester.delete();
		
	}
	
	@Test
	/**
	 * Tests whether the program responds correctly to standard input with
	 * input string both present and missing in the file. 
	 */
	public void testStandardInput() {
		
	}
	
	@Test
	public void test() throws Exception {
		
		
		
	}

}
