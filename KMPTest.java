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
				
				KMP.main(parameters);
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
	    // save the old Stream
	    PrintStream old = System.out;
	    // use special stream
	    System.setOut(printstream);
	    KMP.main(parameters);
	    // put things back
	    System.out.flush();
	    System.setOut(old);
	    //Returns the intercepted output from KMP.main to the console.
	    System.out.println(outStream.toString());
	    
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
		String tester = "A";
		
		//parameter array
		String[] parameters = new String[2];
		parameters[0] = tester;
						
						
		File testSingleCharInputString = classParentFolder.createTempFile("testSingleCharInputString", ".txt", classParentFolder);
		BufferedWriter writer = new BufferedWriter(new FileWriter(testSingleCharInputString));
		String SearchString = "Aber denn noch!";
		writer.write(SearchString);
		writer.close();
		
		//initialise second argument in arguments array as appropriate file
		parameters[1] = testSingleCharInputString.getName();
		

		// catch the Output Stream 
	    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	    PrintStream printstream = new PrintStream(outStream);
	    // save the old Stream
	    PrintStream old = System.out;
	    // use special stream
	    System.setOut(printstream);
	    KMP.main(parameters);
	    // put things back
	    System.out.flush();
	    System.setOut(old);
	    //Returns the intercepted output from KMP.main to the console.
	    System.out.println(outStream.toString());
	    
	    // assert output
		String wanted = ("1: 1:"+KMP.ANSI_RED+"A"+KMP.ANSI_RESET+"ber denn noch!");
		
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
		testSingleCharInputString.delete();
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
								
				//TEST 1	
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
			    // save the old Stream
			    PrintStream old = System.out;
			    // use special stream
			    System.setOut(printstream);
			    KMP.main(parameters);
			    // put things back
			    System.out.flush();
			    System.setOut(old);
			    //Returns the intercepted output from KMP.main to the console.
			    System.out.println(outStream.toString().substring(0, outStream.toString().length()-System.lineSeparator().length()));
			    
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
				
				//TEST 2
				
				testOverlapping = classParentFolder.createTempFile("testOverlapping", ".txt", classParentFolder);
				writer = new BufferedWriter(new FileWriter(testOverlapping));
				SearchString = "HalloHallo";
				writer.write(SearchString+"Hallo"+" Text "+SearchString);
				writer.close();
				
				
				//initialise second argument in arguments array as appropriate file
				parameters[1] = testOverlapping.getName();
				

				// catch the Output Stream 
			    outStream = new ByteArrayOutputStream();
			    printstream = new PrintStream(outStream);
			    // save the old Stream
			    old = System.out;
			    // use special stream
			    System.setOut(printstream);
			    KMP.main(parameters);
			    // put things back
			    System.out.flush();
			    System.setOut(old);
			    //Returns the intercepted output from KMP.main to the console.
			    System.out.println(outStream.toString().substring(0, outStream.toString().length()-System.lineSeparator().length()));
			    
			    // assert output
				wanted = ("1: 1:" + KMP.ANSI_RED +"HalloHallo"+KMP.ANSI_RESET+"Hallo"+" Text "+"HalloHallo"+System.lineSeparator()+
								"1: 6:Hallo"+KMP.ANSI_RED+"HalloHallo"+KMP.ANSI_RESET+" Text "+"HalloHallo"+System.lineSeparator()+
								"1: 22:HalloHalloHallo"+" Text "+KMP.ANSI_RED+"HalloHallo"+KMP.ANSI_RESET);
				
				// Compare character by character
				stringsAreSame = true;
				outputCharArray = outStream.toString().toCharArray();
				wantedCharArray = wanted.toCharArray();
				lineseparatorLenght = System.lineSeparator().length();
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
	    // save the old Stream
	    PrintStream old = System.out;
	    // use special stream
	    System.setOut(printstream);
	    KMP.main(parameters);
	    // put things back
	    System.out.flush();
	    System.setOut(old);
	    //Returns the intercepted output from KMP.main to the console.
	    System.out.println(outStream.toString().substring(0, outStream.toString().length()-System.lineSeparator().length()));
	    
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
	public void testStandardInput() throws Exception {
		
				File testStandardInput = classParentFolder.createTempFile("testStandardInput", ".txt", classParentFolder);
				BufferedWriter writer = new BufferedWriter(new FileWriter(testStandardInput));
				writer.write("Dieses Document soll die Wurzelberechnung beschreiben.");
				writer.newLine();
				writer.newLine();
				writer.write("Als aller erstes muessen wir uns Fragen was ist den die Wurzel?");
				writer.newLine();
				writer.write("nun die Wurzel berechnet den Teiler der Zahl in der Wurzel sodass wenn man Teiler mal Teiler rechnet,");
				writer.newLine();
				writer.write("die Zahl ueber der wir die Wurzel ziehen wieder dabei rauskommt.");
				writer.newLine();
				writer.write("Nun schauen wir uns an wie die Wurzel berechnet wird und die Wurzel von X wird so berechnet:");
				writer.newLine();
				writer.write("Link: zu einer HTML-Seite.");
				writer.close();
				//Test 1
				//Test String
				String tester = "Wurzel";
						
				//parameter array
				String[] parameters = new String[2];
				parameters[0] = tester;
				
				//initialise second argument in arguments array as appropriate file
				parameters[1] = testStandardInput.getName();
				

				// catch the Output Stream 
			    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			    PrintStream printstream = new PrintStream(outStream);
			    // save the old Stream
			    PrintStream old = System.out;
			    // use special stream
			    System.setOut(printstream);
			    KMP.main(parameters);
			    // put things back
			    System.out.flush();
			    System.setOut(old);
			    //Returns the intercepted output from KMP.main to the console.
			    System.out.println(outStream.toString().substring(0, outStream.toString().length()-System.lineSeparator().length()));
			    
			    // assert output
				String wanted = ("1: 26:Dieses Document soll die "+ KMP.ANSI_RED + "Wurzel" + KMP.ANSI_RESET + "berechnung beschreiben."
								  +System.lineSeparator()+
								  "3: 57:Als aller erstes muessen wir uns Fragen was ist den die "+KMP.ANSI_RED+"Wurzel"
								  +KMP.ANSI_RESET+"?"+System.lineSeparator()+
								  "4: 9:nun die "+KMP.ANSI_RED+"Wurzel"+KMP.ANSI_RESET+
								  " berechnet den Teiler der Zahl in der Wurzel sodass wenn man Teiler mal Teiler rechnet,"+System.lineSeparator()+
								  "4: 53:nun die Wurzel berechnet den Teiler der Zahl in der "+KMP.ANSI_RED+"Wurzel"+KMP.ANSI_RESET+
								  " sodass wenn man Teiler mal Teiler rechnet,"+System.lineSeparator()+
								  "5: 28:die Zahl ueber der wir die "+
								  KMP.ANSI_RED+"Wurzel"+KMP.ANSI_RESET+" ziehen wieder dabei rauskommt."+System.lineSeparator()+
								  "6: 32:Nun schauen wir uns an wie die "+KMP.ANSI_RED+"Wurzel"+KMP.ANSI_RESET+" berechnet wird und die Wurzel von X wird so berechnet:"
								  +System.lineSeparator()+
								  "6: 62:Nun schauen wir uns an wie die Wurzel berechnet wird und die "+KMP.ANSI_RED+"Wurzel"+KMP.ANSI_RESET+" von X wird so berechnet:");
				
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
				testStandardInput.delete();
				
				//TODO//
				//Diser Test muss noch gemacht werden
				/*
				//TEST 2
				
				//Test String
				tester = "die Wurzel von X";
						
				//parameter array
				parameters = new String[2];
				parameters[0] = tester;
				
				//initialise second argument in arguments array as appropriate file
				parameters[1] = testStandardInput.getName();
				

				// catch the Output Stream 
			    outStream = new ByteArrayOutputStream();
			    printstream = new PrintStream(outStream);
			    // save the old Stream
			    old = System.out;
			    // use special stream
			    System.setOut(printstream);
			    KMP.main(parameters);
			    // put things back
			    System.out.flush();
			    System.setOut(old);
			    //Returns the intercepted output from KMP.main to the console.
			    System.out.println(outStream.toString().substring(0, outStream.toString().length()-System.lineSeparator().length()));
			    
			    // assert output
		        wanted = ("4: 1:" + KMP.ANSI_RED + "Hallo" + KMP.ANSI_RESET + " World!");
				
				// Compare character by character
				stringsAreSame = true;
				outputCharArray = outStream.toString().toCharArray();
			    wantedCharArray = wanted.toCharArray();
				lineseparatorLenght = System.lineSeparator().length();
				for(int i=0; i<wanted.length();i++){
					if(outputCharArray[i]!=wantedCharArray[i]|| wanted.length()!=(outStream.toString().length()-lineseparatorLenght)){
						stringsAreSame = false;
						break;
					}
				}
				
				assert(stringsAreSame==true);
				testStandardInput.delete();
				*/
				
				
	}

	@Test(expected = FileNotAvailableException.class)
	public void testFileNotAvailable() throws Exception {
		String tester = "HalloHallo";
		
		//parameter array
		String[] parameters = new String[2];
		parameters[0] = tester;
						
						
		File testFileNotAvailable = classParentFolder.createTempFile("filenotavailable", ".txt", classParentFolder);
		BufferedWriter writer = new BufferedWriter(new FileWriter(testFileNotAvailable));
		String SearchString = "HalloHallo";
		writer.write(SearchString+SearchString);
		writer.close();
		
		
		//initialise second argument in arguments array as appropriate file
		parameters[1] = testFileNotAvailable.getName()+"3av2";
		KMP.main(parameters);
		
		
		Assert.fail("Expected FileNotAvailableException");
		testFileNotAvailable.delete();
	}
	
	@Test(expected = InvalidFileInputException.class)
	public void testInvalidFileInput() throws Exception {
		String tester = "HalloHallo";
		
		//parameter array
		String[] parameters = new String[2];
		parameters[0] = tester;
						
						
		File testInvalidFileInput = classParentFolder.createTempFile("invalidfileinput", ".pdf", classParentFolder);
		BufferedWriter writer = new BufferedWriter(new FileWriter(testInvalidFileInput));
		String SearchString = "HalloHallo";
		writer.write(SearchString+SearchString);
		writer.close();
		
		
		//initialise second argument in arguments array as appropriate file
		parameters[1] = testInvalidFileInput.getName();
		KMP.main(parameters);

		
		Assert.fail("Expected InvalidFileInputException");;
		testInvalidFileInput.delete();
	}
	
	@Test(expected = NonASCIIPattern.class)
	public void testNonASCIIPattern() throws Exception {
				String tester = "\u00E6";
						
				//parameter array
				String[] parameters = new String[2];
				parameters[0] = tester;
								
								
				File testNonASCIIPattern = classParentFolder.createTempFile("NonASCIIPattern", ".txt", classParentFolder);
				BufferedWriter writer = new BufferedWriter(new FileWriter(testNonASCIIPattern));
				writer.write("Hallo Welt!");
				writer.close();
				
				
				//initialise second argument in arguments array as appropriate file
				parameters[1] = testNonASCIIPattern.getName();
				KMP.main(parameters);
				
				Assert.fail("Expected NonASCIIPattern");
				testNonASCIIPattern.delete();
				
	}
	
	@Test(expected = NotEnoughArgumentsException.class)
	public void testNotEnoughArguments() throws Exception {
		String tester = "HalloHallo";
		
		//parameter array
		String[] parameters = new String[1];
		parameters[0] = tester;
						
						
		File testNotEnoughArguments = classParentFolder.createTempFile("NotEnoughArguments", ".txt", classParentFolder);
		BufferedWriter writer = new BufferedWriter(new FileWriter(testNotEnoughArguments));
		String SearchString = "HalloHallo";
		writer.write(SearchString+SearchString);
		writer.close();
	
	    KMP.main(parameters);
	    Assert.fail("Expected NotEnoughArgumentsException");
	    
	    //Test 2
	    parameters = new String[0];
	    KMP.main(parameters);
	    
		Assert.fail("Expected NotEnoughArgumentsException");
		
		//Test 3
		parameters = new String[2];
		parameters[0] = "Hi there";
		parameters[1] = testNotEnoughArguments.getName();
		KMP.main(parameters);
		
		testNotEnoughArguments.delete();
	}

}
