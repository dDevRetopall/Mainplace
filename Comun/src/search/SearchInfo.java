package search;

import java.io.Serializable;

public class SearchInfo implements Serializable{
	private String search;

	public SearchInfo(String search){
		this.search = search;
		
	}

	public String getSearch() {
		return search;
	}
	
}
