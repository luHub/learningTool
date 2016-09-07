package commons.flashcards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import meta.flashcardsdto.MultipleChoiceFlashCardDTO;

public class FlashCardIO {

	private static final String FILE_PREFIX = "q";
	private static final String QUESTION_DIRECTORY = "questions";

	public static MultipleChoiceFlashCardDTO readFlashCardJsonFile(int id) throws IOException {
		final String fileName = createFileName(id);
		Path path = Paths.get(QUESTION_DIRECTORY + "\\" + fileName);
		MultipleChoiceFlashCardDTO question = readFlashCardFromPath(path);
		return question;
	}

	private static MultipleChoiceFlashCardDTO readFlashCardFromPath(Path path)
			throws IOException, JsonParseException, JsonMappingException {
		BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		StringBuilder stringFromFile = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			stringFromFile.append(line);
		}
		MultipleChoiceFlashCardDTO question = JsonDtoFlashCardConverter.convertToObject(stringFromFile.toString());
		reader.close();
		return question;
	}

	public static Map<Integer, MultipleChoiceFlashCardDTO> readAllFlashCards() throws IOException {
		Map<Integer, MultipleChoiceFlashCardDTO> allFiles = new HashMap<>();
		DirectoryStream<Path> dirs = Files.newDirectoryStream(Paths.get(QUESTION_DIRECTORY));
		for (Path name : dirs) {
			MultipleChoiceFlashCardDTO flashCard = readFlashCardFromPath(name);
			allFiles.put(flashCard.getId(), flashCard);
		}
		return allFiles;
	}

	public static void createFlashCardAsJsonFile(final MultipleChoiceFlashCardDTO multipleChoiceFlashCardDTO)
			throws IOException {
		final String questionAsJson = JsonDtoFlashCardConverter.convertToJson(multipleChoiceFlashCardDTO);
		final String fileName = createFileName(multipleChoiceFlashCardDTO);
		createPathIfNotExist();
		createFile(fileName, questionAsJson);
	}

	private static String createFileName(final MultipleChoiceFlashCardDTO mcfcd) {
		return FILE_PREFIX + "_" + mcfcd.getId() + ".json";
	}

	private static String createFileName(int id) {
		return FILE_PREFIX + "_" + id + ".json";
	}

	public static void createPathIfNotExist() throws IOException {

		// Check if directory exists
		Path path = Paths.get(QUESTION_DIRECTORY);
		if (!Files.exists(path)) {
			// Create Directory
			Files.createDirectories(path);
		}
	}

	private static void createFile(String fileName, String questionAsJson) throws IOException {
		Path path = Paths.get(QUESTION_DIRECTORY + "\\" + fileName);
		if (Files.exists(path)) {
			Files.delete(path);
		}
		Files.createFile(path);
		Charset charset = Charset.forName("US-ASCII");
		BufferedWriter writer = Files.newBufferedWriter(path, charset);
		writer.write(questionAsJson, 0, questionAsJson.length());
		writer.flush();
		writer.close();
	}

	public static Integer getQuestionId(String questionName) {
		return Integer.parseInt(questionName.replace(FILE_PREFIX, "").replace(".json", "").trim());
	}

	public static boolean deleteQuestion(int id) throws IOException {
		Path path = Paths.get(QUESTION_DIRECTORY + "\\" + FILE_PREFIX+"_"+id+".json");
		if (Files.exists(path)) {
			Files.delete(path);
			return true;
		}
		return false;
	}

}