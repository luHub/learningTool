package commons.info;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import meta.working.ConvertableToJSON;
import meta.working.FileDTO;
import meta.working.MapInfoDTO;
import util.JsonConverter;

public class InfoIO { 

	public static void createFile(FileDTO<Integer, ? extends ConvertableToJSON> file) throws IOException {
		if (Files.exists(file.getPath())) {
			Files.delete(file.getPath());
		}
		Files.createFile(file.getPath());
		Charset charset = Charset.forName("US-ASCII");
		BufferedWriter writer = Files.newBufferedWriter(file.getPath(), charset);
		String jsonInString = JsonConverter.convertToJson(file.getContend());
		writer.write(jsonInString, 0, jsonInString.length());
		writer.flush();
		writer.close();
	}

	// To return a layoutDTO file
	public static <T> T readFile(Path path, Class<T> classType)
			throws IOException, JsonParseException, JsonMappingException {
		BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		String line;
		StringBuilder stringFromFile = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			stringFromFile.append(line);
		}
		reader.close();
		return JsonConverter.convertToObject(stringFromFile.toString(), classType);
	}

	// Use Generic
	@Deprecated
	public static MapInfoDTO readFile(Path infoPath) throws JsonParseException, JsonMappingException, IOException {
		return readJsonFile(infoPath);
	}

	@Deprecated
	private static MapInfoDTO readJsonFile(Path path) throws IOException, JsonParseException, JsonMappingException {
		BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		StringBuilder stringFromFile = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			stringFromFile.append(line);
		}

		MapInfoDTO fileAsDTO = JsonConverter.convertToInfoObject(stringFromFile.toString());
		reader.close();
		return fileAsDTO;
	}

	// TODO Write this method:
	public static Map<Integer, FileDTO<Integer, MapInfoDTO>> readAllInfoFiles(Path path)
			throws JsonParseException, JsonMappingException, IOException {
		Map<Integer, FileDTO<Integer, MapInfoDTO>> allFiles = new HashMap<>();
		DirectoryStream<Path> dirs = Files.newDirectoryStream(path);
		for (Path name : dirs) {
			MapInfoDTO mapInfoDTO = readJsonFile(name);
			// Extract Id from FileName:
			Integer id = Integer.valueOf(name.getFileName().toString().replace(".json", ""));
			FileDTO<Integer, MapInfoDTO> fileDTO = new FileDTO<>(id, name);
			fileDTO.setContend(mapInfoDTO);
			allFiles.put(fileDTO.getId(), fileDTO);
		}
		return allFiles;
	}

	public static boolean deleteFile(FileDTO fileDTO) throws IOException {
		Path path = fileDTO.getFilePath();
		if (Files.exists(path)) {
			Files.delete(path);
			return true;
		}
		return false;
	}
}