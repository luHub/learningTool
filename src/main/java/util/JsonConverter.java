package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import commons.flashcards.JsonDtoFlashCardConverter;
import meta.flashcardsdto.MultipleChoiceFlashCardDTO;
import meta.working.ConvertableToJSON;
import meta.working.FileDTO;
import meta.working.InfoDTO;
import meta.working.MapInfoDTO;

public class JsonConverter {
	
	public static <T>  T convertToObject(String jsonString, Class<T> classType) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonString, classType);
	}

	public static String convertToJson(ConvertableToJSON dto) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(dto);
		return jsonInString;
	}

	public static MapInfoDTO convertToInfoObject(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonString, MapInfoDTO.class);
	}
	 
	
	
	
	
}