package commons;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkbookManager {

	private static String questionDirectory = "questions";
	private static String codeRunnerDirectory = "codeRunner";
	
	public static void createWorkbook(String user, String workbookName) throws IOException {
		String questionPathString = user + "/" + workbookName + "/" + questionDirectory;
		Path questionPath = Paths.get(questionPathString);
		Files.createDirectories(questionPath);
		Path codeRunnerPath = Paths.get(questionPathString, "..", codeRunnerDirectory);
		Files.createDirectories(codeRunnerPath);
	}
	
}
