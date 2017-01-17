package commons;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class WorkbookManager {

	private static String questionDirectory = "questions";
	private static String codeRunnerDirectory = "codeRunner";
	private static String contendDirectory = "contend";
	
	
	public static void createWorkbook(String user, String workbookName) throws IOException {
		String questionPathString = user + "/" + workbookName + "/" + questionDirectory;
		
		Path questionPath = Paths.get(questionPathString);
		Files.createDirectories(questionPath);
		
		Path codeRunnerPath = Paths.get(questionPathString, "..", codeRunnerDirectory);
		Files.createDirectories(codeRunnerPath);
		
		Path contenthPath = Paths.get(questionPathString, "..", contendDirectory);
		Files.createDirectories(contenthPath);
	}
	
	public static boolean deleteWorkbook(String user, String workbookName) throws IOException {
		String workbookPathString = user + "/" + workbookName;
		Path workbookPath = Paths.get(workbookPathString);
		return Files.deleteIfExists(workbookPath);
	}
	
	public static boolean renameWorkbook(String user, String workbookName,String newNameWorkbook) throws IOException {
		String workbookPathString = user + "/" + workbookName;
		Path workbookPath = Paths.get(workbookPathString);
		
		String newWorkbookPathString = user + "/" + newNameWorkbook;
		Path newWorkbookPath = Paths.get(newWorkbookPathString);
		
		Path path = Files.move(workbookPath, newWorkbookPath, StandardCopyOption.REPLACE_EXISTING);
		return path.endsWith(newWorkbookPath);
	}	
}