import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

//For MangaInn Only!
//If I have enough time, I will add alternate version for MangaFox later :)

public class OnlineWebsiteMangaInn implements OnlineWebsite {
	
	private ArrayList<OnlineManga> allOnlineManga;
	private LocalWebsite localWebsite;

	//Getters 'n Setters
	public ArrayList<OnlineManga> getAllOnlineManga() {return allOnlineManga;}
	public void setAllOnlineManga(ArrayList<OnlineManga> allOnlineManga) {this.allOnlineManga = allOnlineManga;}
	public LocalWebsite getLocalWebsite() {return localWebsite;}
	public void setLocalWebsite(LocalWebsite localWebsite) {this.localWebsite = localWebsite;}

	//Constructor
	public OnlineWebsiteMangaInn(LocalWebsite localWebsite){
		allOnlineManga=new ArrayList<OnlineManga>();
		this.localWebsite=localWebsite;
	}

	//Begin fetching data
	public void fetchAllOnlineManga(){
	    BufferedReader in = null;
	    try {
	    	URL url = new URL("http://www.mangainn.com/MangaList");
	    	in = new BufferedReader(new InputStreamReader(url.openStream()));
	    	String line;
	    	while ((line = in.readLine()) != null) {
    			int begin=line.indexOf("mangalistItems");
	    		if(begin>0){
	    			while(begin>0){
						//extract manga url
	    				int start=line.indexOf("href=\"",begin)+6;
	    				int end=line.indexOf("\"",start+6);
	    				String link=line.substring(start,end);
						//extract manga name
	    				int startName=line.indexOf(">",end)+1;
	    				int endName=line.indexOf("</",startName);
	    				String id=line.substring(startName,endName);
	    				
	    				/*DELETED
	    				//extract manga id
	    				String id=link.substring(link.indexOf("manga/")+6,link.indexOf("_"));
						*/

						//create new manga object consists of data gathered before 
	    				OnlineManga fetchedManga=new OnlineMangaMangaInn(localWebsite,link,id);
	    				this.allOnlineManga.add(fetchedManga);
	    				
	    				begin=line.indexOf("mangalistItems", begin+1);
	    			}
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
