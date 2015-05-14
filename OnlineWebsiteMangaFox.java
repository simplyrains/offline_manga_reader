import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class OnlineWebsiteMangaFox implements OnlineWebsite {
	
	private ArrayList<OnlineManga> allOnlineManga;
	private LocalWebsite localWebsite;

	//Getters 'n Setters
	public ArrayList<OnlineManga> getAllOnlineManga() {return allOnlineManga;}
	public void setAllOnlineManga(ArrayList<OnlineManga> allOnlineManga) {this.allOnlineManga = allOnlineManga;}
	public LocalWebsite getLocalWebsite() {return localWebsite;}
	public void setLocalWebsite(LocalWebsite localWebsite) {this.localWebsite = localWebsite;}

	//Constructor
	public OnlineWebsiteMangaFox(LocalWebsite localWebsite){
		allOnlineManga=new ArrayList<OnlineManga>();
		this.localWebsite=localWebsite;
	}

	//Begin fetching data
	public void fetchAllOnlineManga(){
	    BufferedReader in = null;
	    try {
	    	URL url = new URL("http://mangafox.me/manga/");
	    	in = new BufferedReader(new InputStreamReader(url.openStream()));
	    	String line;
	    	while ((line = in.readLine()) != null) {
    			int begin=line.indexOf("<li><a href=\"http://mangafox.me/manga/");
	    		if(begin>0){
						//extract manga url
	    				int start=line.indexOf("href=\"",begin)+6;
	    				int end=line.indexOf("\"",start+6);
	    				String link=line.substring(start,end);
						//extract manga name
	    				int startName=line.indexOf(">",end)+1;
	    				int endName=line.indexOf("</",startName);
	    				String id=line.substring(startName,endName);
	    				
						//create new manga object consists of data gathered before 
	    				OnlineManga fetchedManga=new OnlineMangaMangaFox(localWebsite,link,id);
	    				this.allOnlineManga.add(fetchedManga);
	    		}
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	if (in != null) {
	    		try {
	    			in.close();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	    	}
		}
	}
	
	//Search data that matches the condition and return as a arraylist of manga
	public ArrayList<OnlineManga> SearchAllOnlineManga(String condition){
		ArrayList<OnlineManga> result=new ArrayList<OnlineManga>();
		for(int i=0;i<this.allOnlineManga.size();i++){
			if((this.allOnlineManga.get(i).getMangaID().toUpperCase()).indexOf(condition.toUpperCase())>=0){
				result.add(this.allOnlineManga.get(i));
			}
		}
		return result;
	}
	
}
