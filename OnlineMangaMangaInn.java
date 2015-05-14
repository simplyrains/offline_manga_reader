import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

//For MangaInn Only!
//If I have enough time, I will add alternate version for MangaFox later :)

public class OnlineMangaMangaInn implements OnlineManga {

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
	public OnlineMangaMangaInn(LocalWebsite localWebsite,String mangaURL, String mangaID){
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
		allOnlineChapter=new ArrayList<OnlineChapter>();
	    BufferedReader in = null;
	    try {
	    	URL url = new URL(mangaURL);
	    	in = new BufferedReader(new InputStreamReader(url.openStream()));
	    	String line;
	    	boolean startFinding=false;
	    	while ((line = in.readLine()) != null) {
	    		if(!startFinding){
	    			if(line.indexOf("Chapter Name")>0) startFinding=true;
	    		}
	    		if(startFinding){
    				int begin=line.indexOf("<span class=\"BlackLabel14\"><a href=");
    				if(begin>0){
    					while(begin>0){
    						//extract chapter url
    						int start=line.indexOf("href=\"",begin)+6;
    						int end=line.indexOf("\"",start+6);
    						String link=line.substring(start,end);
    						//extract chapter number
    						int endNo=line.indexOf("</strong>", end);
    						String num1= line.substring(endNo-17,endNo-3);
    						String num2=num1.substring(num1.lastIndexOf(" ")+1,num1.length());
    						
    						/*UNUSE
    						//extract chapter name
    						int startName=line.indexOf("</strong>",end)+10;
    						int endName=line.indexOf("</a>",startName);
    						String name=line.substring(startName,endName);
    						*/
    						
    						//create new chapter object consists of data gathered before 
    						OnlineChapter fetchedChapter=new OnlineChapterMangaInn(localManga,link,num2);
    						this.allOnlineChapter.add(fetchedChapter);
    						
    						begin=line.indexOf("<span class=\"BlackLabel14\"><a href=",begin+1);
    					}
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
}
