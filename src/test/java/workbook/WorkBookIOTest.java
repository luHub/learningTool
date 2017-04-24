package workbook;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import commons.WorkbookIO;
import commons.coderunner.CodeRunnerCreator;
import commons.info.InfoIO;
import junit.framework.AssertionFailedError;
import meta.coderunner.CodeDTO;
import meta.coderunner.CompileInstructions;
import meta.coderunner.ConfigDTO;
import meta.coderunner.Language;
import meta.coderunner.RunInstructions;
import meta.working.ConvertableToJSON;
import meta.working.FileDTO;
import meta.working.INFO_TYPE;
import meta.working.InfoDTO;
import meta.working.MapInfoDTO;
import meta.working.WorkingSpaceDTO;


/**
 * Functional Tests for Entire Workbook
 * @author mey
 *
 */
public class WorkBookIOTest {
	
	private static final String user = "foo";
	private static final String workbookName = "MyFirstCodeHomeWork";
	private static final String questions = "questions";
	private static final String codeRunnerDirectory = "code";
    private static final String infoDirectory = "info";
    private static final String imageDirectory = "img";
	
	@After
	public void after() throws IOException{
		Path userPath = Paths.get(user);
		deleteFiles(userPath);
	}

	private void deleteFiles(Path path) throws IOException {
		//Recursively Delete All SubDirectories
		File currentDirectory = path.toFile();
		//Base Case
		if (currentDirectory.list()==null || currentDirectory.list().length == 0) {
			Files.deleteIfExists(path);
		}
		//Go to each subfolder
		else {
			List<File> subDirectories = Arrays.asList(currentDirectory.listFiles());
			subDirectories.stream().forEach((subdirectory) -> {
				Path subdirPath = Paths.get(subdirectory.getPath());
				try {
					if(Files.exists(subdirPath)){
						deleteFiles(subdirPath);	
					}
					if(Files.exists(path)){
						deleteFiles(path);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

	/**
	 * Creates the bare bones of a new workbook divided into
	 * question, info, code directories. 
	 * @throws IOException
	 */
	@Test
	public void createDirectoriesForWorkbook() throws IOException {
		WorkbookIO.createWorkbook(user, workbookName);
		Path userPath = Paths.get(user);
		Path workbookPath = Paths.get(user, workbookName);
		Path imagePath = Paths.get(user,workbookName,imageDirectory);
		Path questionPath = Paths.get(user,workbookName,questions);
		Path infoPath = Paths.get(user,workbookName,infoDirectory);
		
		Path codePath = Paths.get(user,workbookName,codeRunnerDirectory);

		FileVisitor<? super Path> visitor = new FileVisitor<Path>() { 

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				if (dir.getFileName().toString().equals(user)) {
					Path foundUserPath = Paths.get(dir.toString()).normalize();
					assertEquals(foundUserPath.toString(), userPath.toString());
					return FileVisitResult.CONTINUE;
				}
				
				if (dir.getFileName().toString().equals(questions)) {
					Path founDquestionPath = Paths.get(dir.toString()).normalize();
					assertEquals(founDquestionPath.toString(), questionPath.toString());
					return FileVisitResult.CONTINUE;
				}
				
				if (dir.getFileName().toString().equals(infoDirectory)) {
					Path founPath = Paths.get(dir.toString()).normalize();
					assertEquals(founPath.toString(), infoPath.toString());  
					return FileVisitResult.CONTINUE;  
				}  
				
				if (dir.getFileName().toString().equals(codeRunnerDirectory)) {
					Path foundUserPath = Paths.get(dir.toString()).normalize();
					assertEquals(foundUserPath.toString(), codePath.toString());
					return FileVisitResult.CONTINUE;
				}
				
				if (dir.getFileName().toString().equals(imageDirectory)) {
					Path foundUserPath = Paths.get(dir.toString()).normalize();
					assertEquals(foundUserPath.toString(), imagePath.toString());
					return FileVisitResult.CONTINUE;
				}
				
				if (dir.getFileName().toString().equals(workbookName)) {
					Path foundUserPath = Paths.get(dir.toString()).normalize();
					assertEquals(foundUserPath.toString(), workbookPath.toString());
					return FileVisitResult.CONTINUE;
				}
			
				throw new AssertionFailedError(); 
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}
		};
		
		Files.walkFileTree(userPath, visitor);
	}
	

	/**
	 * Creates an Info File inside info folder
	 * @throws IOException 
	 */
	@Test
	public void createInfoFile() throws IOException{
		//1. Creates a workbook
		WorkbookIO.createWorkbook(user, workbookName);
		//2. Create an Infofile		
		InfoDTO info = new InfoDTO();
		info.setText("Hello Text!");
		info.setType(INFO_TYPE.TEXT);	 
		final Integer fileId=1;
		final Integer position = 1;
		final String ext = "json";
		final MapInfoDTO mapInfoDTO = new MapInfoDTO();
		mapInfoDTO.getMap().put(position, info);
		Path infoPath = Paths.get(user,workbookName,infoDirectory); 
		FileDTO<Integer,ConvertableToJSON> fileDTO = new FileDTO(fileId, infoPath, ext);
		fileDTO.setContend(mapInfoDTO);
		InfoIO.createFile(fileDTO);  
		//3. Read Created Info file
		Path infoFilePath = Paths.get(infoPath.toString(), fileId.toString()+"."+ext);
		MapInfoDTO retrievedFile = InfoIO.readFile(infoFilePath); 
		//4. Asserts
		assertEquals(info.getText(),retrievedFile.getMap().get(position).getText()); 
	}
	
	@Test
	public void getInfoFileListFromWorkbook() throws IOException{
		//1. Creates a workbook
		WorkbookIO.createWorkbook(user, workbookName);
		//2. Creates 10 info files		
		//3. Reads Folder and retrieves expected list
		//4. assert expected list
	}
	
	
	
	@Ignore
	@Test
	public void createCodeRunnerProject(){}

	@Test
	public void createCodeRunnerSource() throws IOException {
		String projectName="myJavaFirstProject";
		CodeDTO javaHelloWorld = new CodeDTO();
		javaHelloWorld.setFileName("HelloWorld");
		Language lang = new Language();
		lang.setName("java");
		lang.setExtension(".java");
		javaHelloWorld.setLanguage(lang);
		
		WorkingSpaceDTO workingSpaceInfo = new WorkingSpaceDTO();
		workingSpaceInfo.setUserName(user);
		workingSpaceInfo.setCurrentWorkbook(workbookName);
		workingSpaceInfo.setCurrentType("codeRunner");
		javaHelloWorld.setWorkingSpaceInfo(workingSpaceInfo);
		javaHelloWorld.setProjectName(projectName);

		javaHelloWorld
				.setCodeText("public static void main(String[] args){" + "System.out.print(\"Hello Wordl!\");" + "}");
		CodeRunnerCreator.createCodeRunner(javaHelloWorld);
		
		//Find File Assert Extension
		CodeDTO readDTO = new CodeDTO();
		readDTO.setFileName("HelloWorld");
		readDTO.setLanguage(lang);
		readDTO.setProjectName(projectName);
		readDTO.setWorkingSpaceInfo(workingSpaceInfo);
		
		CodeDTO readJavaHelloWorld = CodeRunnerCreator.readCodeRunner(readDTO);
		//Assert String
		assertEquals(readJavaHelloWorld.getCodeText(), javaHelloWorld.getCodeText());
		
	}
	
	@Test
	public void createCodeRunnerConfig() throws IOException {

		String projectName = "myJavaFirstProject";

		CodeDTO javaHelloWorld = new CodeDTO();
		javaHelloWorld.setFileName("HelloWorld");
		Language lang = new Language();
		lang.setName("java");
		lang.setExtension(".java");
		javaHelloWorld.setLanguage(lang);

		WorkingSpaceDTO workingSpaceInfo = new WorkingSpaceDTO();
		workingSpaceInfo.setUserName(user);
		workingSpaceInfo.setCurrentWorkbook(workbookName);
		workingSpaceInfo.setCurrentType("codeRunner");
		javaHelloWorld.setWorkingSpaceInfo(workingSpaceInfo);
		javaHelloWorld.setProjectName(projectName);

		// Create Compile and run config file called config.json
		CompileInstructions compileInstructions = new CompileInstructions();
		compileInstructions
				.setInstructions("javac " + javaHelloWorld.getFileName() + javaHelloWorld.getLanguage().getExtension());
		RunInstructions runInstructions = new RunInstructions();
		runInstructions.setRunInstructions(
				"java " + javaHelloWorld.getFileName() + javaHelloWorld.getLanguage().getExtension());
		javaHelloWorld.setCompileInstructions(compileInstructions);
		javaHelloWorld.setRunInstruccions(runInstructions);

		CodeRunnerCreator.createConfigFile(javaHelloWorld);
		CodeDTO readConfigDTO = new CodeDTO();

		readConfigDTO.setProjectName(projectName);
		readConfigDTO.setWorkingSpaceInfo(workingSpaceInfo);
		ConfigDTO configDTO = CodeRunnerCreator.readConfigFile(readConfigDTO);
		ConfigDTO sentConfigDTO = javaHelloWorld.getConfigDTO();

		assertEquals(configDTO, sentConfigDTO);

	} 
	
	//TODO
	@Test
	public void editCode(){
		
	}
	
	//TODO
	@Test
	public void deleteProject(){
		
	}
	
}