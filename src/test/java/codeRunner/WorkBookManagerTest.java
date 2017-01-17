package codeRunner;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commons.WorkbookManager;
import commons.coderunner.CodeRunnerCreator;
import junit.framework.AssertionFailedError;
import meta.coderunner.CodeDTO;
import meta.coderunner.CompileInstructions;
import meta.coderunner.ConfigDTO;
import meta.coderunner.Language;
import meta.coderunner.RunInstructions;
import meta.working.WorkingSpaceDTO;

public class WorkBookManagerTest {
	
	private String user = "foo";
	private String workbookName = "MyFirstCodeHomeWork";
	private String questions = "questions";
	private String codeRunner = "codeRunner";
	private String contend = "contend";
	
	
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

	@Test
	public void createDirectoriesForWorkbook() throws IOException {
		/**********************************************************
		 * Test the following directory creation:
		 * 
		 * foo |- workbook | |-questions |-codeRunner
		 ***********************************************************/
		WorkbookManager.createWorkbook(user, workbookName);
		Path userPath = Paths.get(user);
		Path workbookPath = Paths.get(user, workbookName);
		visit(userPath, workbookPath,this.workbookName);
	}

	private void visit(Path userPath, Path workbookPath,String workbookName) throws IOException {
		FileVisitor<? super Path> visitor = new FileVisitor<Path>() {

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				if (dir.getFileName().toString().equals(questions)) {
					Path questionPath = Paths.get(dir.toString(), "..").normalize();
					assertEquals(questionPath.toString(), workbookPath.toString());
					return FileVisitResult.CONTINUE;
				}
				if (dir.getFileName().toString().equals(codeRunner)) {
					Path codeRunnerPath = Paths.get(dir.toString(), "..").normalize();
					assertEquals(codeRunnerPath.toString(), workbookPath.toString());
					return FileVisitResult.CONTINUE;
				}
				if (dir.getFileName().toString().equals(contend)) {
					Path contendsPath = Paths.get(dir.toString(), "..").normalize();
					assertEquals(contendsPath.toString(), workbookPath.toString());
					return FileVisitResult.CONTINUE;
				}
				
				if (dir.getFileName().toString().equals(workbookName)) {
					Path workPath = Paths.get(dir.toString()).normalize();
					assertEquals(workPath.toString(), workbookPath.toString());
					return FileVisitResult.CONTINUE;
				}
				if (dir.getFileName().toString().equals(user)) {
					Path foundUserPath = Paths.get(dir.toString()).normalize();
					assertEquals(foundUserPath.toString(), userPath.toString());
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
	
	@Test
	public void renameWorkbook() throws IOException{
		//Create Workbook
		WorkbookManager.createWorkbook(user, workbookName);
		Path questionPath = Paths.get(user, workbookName,questions,"question.txt");
		Path codePath = Paths.get(user,workbookName,codeRunner,"test.java");
		Path infoPath = Paths.get(user, workbookName,contend,"textInformation.txt");
		
		Files.createFile(questionPath);
		Files.createFile(codePath);
		Files.createFile(infoPath);
		
		//Rename Workbook:
		String newWorkbookName="changedName";
		assertTrue(WorkbookManager.renameWorkbook(user, workbookName, newWorkbookName));
		
		//Check File Structure
		Path userPath = Paths.get(user);
		Path workbookPath = Paths.get(user, newWorkbookName);
		visit(userPath, workbookPath,newWorkbookName);
		
		
	}
		
	
	//TODO Move to Code test runner
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