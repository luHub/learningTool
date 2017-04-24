package meta.working;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDTO<T, U> {

	private T id;
	private U contend;
	@Deprecated
	private String extension;
	private Path path;

	
	public FileDTO(){}
	
	//Includes Extension
	public FileDTO(T id,Path fullPath){
		this.path = fullPath;
		this.id=id;
	}
	
	
	public FileDTO(T id,Path path, String ext){
		this.setPath(Paths.get(path.toString(),id.toString()+"."+ext.toString()));
		this.id=id;
		this.extension=ext;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public U getContend() {
		return contend;
	}

	public void setContend(U contend) {
		this.contend = contend;
	}

	//TODO: Remange to getDirectory Path
	public Path getPath() {
		return path;
	}
	
	//TODO: REname to Path fix this magic .json
	public Path getFilePath(){
		return Paths.get(this.path.toString(), this.id+".json");
	}

	public void setPath(Path path) {
		this.path = path;
	}

	
}