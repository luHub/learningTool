package meta.working;

import java.util.HashMap;
import java.util.Map;

public class InfoCoverMapDTO  implements ConvertableToJSON {
	
	private Map<Integer,InfoIndexDTO> infoCovers = new HashMap<>();

	public InfoCoverMapDTO(){}
	
	public Map<Integer, InfoIndexDTO> getInfoCovers() {
		return infoCovers;
	}

	public void setInfoCovers(Map<Integer, InfoIndexDTO> infoCovers) {
		this.infoCovers = infoCovers;
	}
	
	
}
