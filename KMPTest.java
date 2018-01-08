import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
		System.out.println(tempFolderPath);
		
		
		int charIndex = tempFolderPath.lastIndexOf("/");
		tempFolderPath = tempFolderPath.substring(0, charIndex + 1);
		
		//appropriately instantiate folder pointer
		classParentFolder = new File(tempFolderPath);
		System.out.println(classParentFolder.getPath());
	}
	
	
	//rule used to test if appropriate exceptions are being thrown
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	
	@Test
	/**
	 * Tests whether the program responds to an empty input string correctly
	 */
	public void testEmptyInputString() {
		//tester input string - empty for this test
		String tester = "";	
	}
	
	@Test
	/**
	 * Tests whether the program responds correctly to an empty input file.
	 * Can also test the case in which the file is shorter than the input string.
	 */
	public void testEmptyInputFile() {
		String tester = "abc";
		
		//leave file empty
		/*
		 * is writing an empty string into the file any different?
		FileWriter writer = new FileWriter(emptyInputStringTester);
		writer.write(" ");
		writer.close();	*/	

		
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
		
		System.out.println(singleCharTester);
		//initialise second argument in arguments array as appropriate file
		parameters[1] = singleCharTester.getName();
		KMP.main(parameters);
		
		
		//test whether anything has been written to the output stream
		//if correct, nothing should have been written
		//TODO: this implementation is wrong
		assert(System.out.toString() == "");
		
		singleCharTester.delete();
		
		//now test by inputting a file within which the pattern occurs once 
		File oneOccurence = classParentFolder.createTempFile("singleCharTester", ".txt", classParentFolder);//newFile("singleCharTester");
		writer = new FileWriter(oneOccurence);
		writer.write("the bastards will never get away with this");
		writer.close();
		
		parameters[1] = oneOccurence.getName();
		KMP.main(parameters);
		
		assert(System.out.toString() == "1:5: " + oneOccurence.toString()); 
		
	}
	
	@Test 
	/**
	 * Tests whether the program responds correctly to an input string that 
	 * overlaps with itself within the text
	 */
	public void testOverlappingStringOccurences() {
		
	}
	
	@Test
	/**
	 * Tests whether the program responds correctly when there are empty lines 
	 * in the input file. 
	 * Also tests for the input string being longer than a line.
	 */
	public void testEmptyLines() {
		
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
