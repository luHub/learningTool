package commons.flashcards;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import meta.flashcardsdto.MultipleChoiceFlashCardDTO;

public  class JsonDtoFlashCardConverter {

	public static MultipleChoiceFlashCardDTO convertToObject(String jsonString) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		MultipleChoiceFlashCardDTO multipleChoice = mapper.readValue(jsonString, MultipleChoiceFlashCardDTO.class);
		return multipleChoice;
	}

	public static String convertToJson(MultipleChoiceFlashCardDTO multipleChoiceFlashCardDTO) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(multipleChoiceFlashCardDTO);
		return jsonInString;
	}
}
