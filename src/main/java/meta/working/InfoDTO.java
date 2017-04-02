package meta.working;

import java.util.HashMap;
import java.util.Map;

//Create a Content Map
//Text 
//Web
//Id
public class InfoDTO implements ConvertableToJSON{
	
	private String text;
	private String url;
	private String imagePath;
	private Boolean containsImage=false;
	private Boolean containsText=false;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Boolean getContainsImage() {
		return containsImage;
	}
	public void setContainsImage(Boolean containsImage) {
		this.containsImage = containsImage;
	}
	public Boolean getContainsText() {
		return containsText;
	}
	public void setContainsText(Boolean containsText) {
		this.containsText = containsText;
	}


}