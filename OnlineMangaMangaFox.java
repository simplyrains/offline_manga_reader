import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

//For MangaInn Only!
//If I have enough time, I will add alternate version for MangaFox later :)

public class OnlineMangaMangaFox implements OnlineManga {

	private LocalWebsite localWebsite;
	private LocalManga localManga;
	private String mangaURL;
	private ArrayList<OnlineChapter> allOnlineChapter;
	private String mangaID;
	
	//Getters 'n Setters
	public LocalWebsite getLocalWebsite() {return localWebsite;}
	public void setLocalWebsite(LocalWebsite localWebsite) {this.localWebsite = localWebsite;}
	public LocalManga getLocalManga() {return localManga;}
	public void setLocalManga(LocalManga localManga) {this.localManga = localManga;}
	public String getMangaURL() {return mangaURL;}
	public void setMangaURL(String mangaURL) {this.mangaURL = mangaURL;}
	public ArrayList<OnlineChapter> getAllOnlineChapter() {return allOnlineChapter;}
	public void setAllOnlineChapter(ArrayList<OnlineChapter> allOnlineChapter) {this.allOnlineChapter = allOnlineChapter;}
	public String getMangaID() {return mangaID;}
	public void setMangaID(String mangaID) {this.mangaID = mangaID;}

	//Constructor
	public OnlineMangaMangaFox(LocalWebsite localWebsite,String mangaURL, String mangaID){
		allOnlineChapter=new ArrayList<OnlineChapter>();
		this.localWebsite=localWebsite;
		this.mangaURL=mangaURL;
		this.mangaID=mangaID;
	}
	
	//Begin fetching data
	public void fetchAllOnlineChapter(){
		//match this manga with the local ones (if there's no localChapter, create one)
		localManga=localWebsite.findID(mangaID);
		
		//Begin fetch operation
	    BufferedReader in = null;
	    try {
	    	URL url = new URL(mangaURL);
	    	in = new BufferedReader(new InputStreamReader(url.openStream()));
	    	String line;
	    	while ((line = in.readLine()) != null) {
    			int begin=line.indexOf("title=\"Thanks for Contributing!");
    			if(begin>0){
    				//extract chapter url
    				int start=line.indexOf("href=\"")+6;
    				int end=line.indexOf("\"",start+6)-6;
    				String link=line.substring(start,end);
    				//extract chapter number
    				String chapterNo=line.substring(line.lastIndexOf(" ")+1,line.length()-4);
    				//create new chapter object consists of data gathered before 
    				OnlineChapter fetchedChapter=new OnlineChapterMangaFox(localManga,link,chapterNo);
    				this.allOnlineChapter.add(fetchedChapter);
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
}
