package flash;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;

import commons.flashcards.FlashCardCreator;
import commons.flashcards.FlashCardIO;
import commons.flashcards.JsonDtoFlashCardConverter;
import meta.flashcardsdto.MultipleChoiceFlashCardDTO;

public class FlashCardTest {

	int id=1;
	String type="exception";
	String questionText = "Which one is a checked Exception?";
	String choiceOne="IOException";
	String choiceTwo="AnnotationTypeMismatchException";
	String choiceThree="FileSystemAlreadyExistsException";
	MultipleChoiceFlashCardDTO question;
	String answer="IOException";
	
	@Before
	public void initialize(){
		//Create FlashCard
		FlashCardCreator flashCardCreator = new FlashCardCreator();
		//Add Question
		flashCardCreator.prepareQuestion(id,type, questionText);
		//Add Choices
		flashCardCreator.addToChoiceList(1, choiceOne);
		flashCardCreator.addToChoiceList(2, choiceTwo);
		flashCardCreator.addToChoiceList(3, choiceThree);
		//Add Answer
		flashCardCreator.prepareAnswer(1, answer,true,false,false,false);
		//Create Question
		question = flashCardCreator.create();
	}
	
	@Test
	public void test() {
		assertSame(question.getQuestion().getType(), type);
		assertSame(question.getQuestion().getQuestion(), questionText);
		assertSame(question.getChoiceList().get(0).getDescription(),choiceOne);
		assertSame(question.getChoiceList().get(1).getDescription(),choiceTwo);
		assertSame(question.getChoiceList().get(2).getDescription(),choiceThree);
		assertSame(question.getAnswer().getAnswer(),answer);
	}

	@Test
	public void dTOToJsonFlashCardConverter() throws IOException {
		try {
			//Create Json
			String jsonDto=JsonDtoFlashCardConverter.convertToJson(question);
			//Get Expected Json Result From File
			String expectedJson = readFlashCardTestFile();
			MultipleChoiceFlashCardDTO BackToquestion = JsonDtoFlashCardConverter.convertToObject(expectedJson);
			MultipleChoiceFlashCardDTO Expectedquestion = JsonDtoFlashCardConverter.convertToObject(jsonDto);
			assertEquals(BackToquestion, Expectedquestion);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void jsonToDTOFlashCardConverter() {
		String json = readFlashCardTestFile();
		try {
			MultipleChoiceFlashCardDTO multipleChoiceFlashCardDTO = JsonDtoFlashCardConverter.convertToObject(json);
			assertEquals(this.question,multipleChoiceFlashCardDTO);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readFlashCardTestFile(){
		StringBuilder jsonStringFromFile=new StringBuilder();
		Path currentRelativePath = Paths.get("");
		Path path = Paths.get(currentRelativePath.toAbsolutePath().toString()+"/src/test/resources/FlashCartTest.json");
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        jsonStringFromFile.append(line);
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		return jsonStringFromFile.toString().replaceAll("\n", "").replaceAll("\t", "").replaceAll(": ",":");
	}
	
	@Test
	public void createAndReadFlashCardFile(){
		//Add a Question as MultipleChoiceFlashCardDTO 
		try {
			FlashCardIO.createFlashCardAsJsonFile(this.question);
			this.question.setId(1);
			FlashCardIO.createFlashCardAsJsonFile(this.question);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			MultipleChoiceFlashCardDTO mcfcd = FlashCardIO.readFlashCardJsonFile(this.question.getId());
			assertEquals(this.question,mcfcd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void readAllFiles() throws JsonParseException, JsonMappingException, IOException{
		this.question.setId(1);
		FlashCardIO.createFlashCardAsJsonFile(this.question);
		this.question.setId(2);
		FlashCardIO.createFlashCardAsJsonFile(this.question);
		this.question.setId(3);
		FlashCardIO.createFlashCardAsJsonFile(this.question);
		Map<Integer,MultipleChoiceFlashCardDTO> allFiles = new HashMap<>();
		allFiles = FlashCardIO.readAllFlashCards();  
		assertTrue(!allFiles.isEmpty());
		assertTrue(allFiles.containsKey(1));
		assertTrue(allFiles.containsKey(1));
		assertTrue(allFiles.containsKey(1));
	}
	
	@Test
	public void deleteQuestionFromFile() throws IOException{
		int id=10;
		MultipleChoiceFlashCardDTO question = createQuestion(id);
		FlashCardIO.createFlashCardAsJsonFile(question);
		boolean isDeleted = FlashCardIO.deleteQuestion(id);
		assertTrue(isDeleted);
	}

	private MultipleChoiceFlashCardDTO createQuestion(int id) {
		FlashCardCreator flashCardCreator = new FlashCardCreator();
		//Add Question
		flashCardCreator.setId(id);
		flashCardCreator.prepareQuestion(id,type, questionText);
		//Add Choices
		flashCardCreator.addToChoiceList(1, choiceOne);
		flashCardCreator.addToChoiceList(2, choiceTwo);
		flashCardCreator.addToChoiceList(3, choiceThree);
		//Add Answer
		flashCardCreator.prepareAnswer(1, answer,true,false,false,false);
		//Create Question
		MultipleChoiceFlashCardDTO question = flashCardCreator.create();
		return question;
	}
	
}