import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JTextArea;

//For MangaInn Only!
//If I have enough time, I will add alternate version for MangaFox later :)

public class OnlineChapterMangaFox implements OnlineChapter {

	private LocalManga localManga;
	private LocalChapter localChapter;
	private String chapterURL;
	private ArrayList<String> allPageURL;
	private String chapterNumber,mangaID;
	private JTextArea downloadStatus;

	//Getters 'n Setters
	public LocalManga getLocalManga() {return localManga;}
	public void setLocalManga(LocalManga localManga) {this.localManga = localManga;}
	public LocalChapter getLocalChapter() {return localChapter;}
	public void setLocalChapter(LocalChapter localChapter) {this.localChapter = localChapter;}
	public String getChapterURL() {return chapterURL;}
	public void setChapterURL(String chapterURL) {this.chapterURL = chapterURL;}
	public ArrayList<String> getAllPageURL() {return allPageURL;}
	public void setAllPageURL(ArrayList<String> allPageURL) {this.allPageURL = allPageURL;}
	public String getChapterNumber() {return chapterNumber;}
	public void setChapterNumber(String chapterNumber) {this.chapterNumber = chapterNumber;}
	public String getMangaID() {return mangaID;}
	public void setMangaID(String mangaName) {this.mangaID = mangaName;}
	public JTextArea getDownloadStatus() {return downloadStatus;}
	public void setDownloadStatus(JTextArea downloadStatus) {this.downloadStatus = downloadStatus;}
	
	//Constructor
	public OnlineChapterMangaFox(LocalManga localManga,String chapterURL, String chapterNumber){
		allPageURL=new ArrayList<String>();
		this.localManga=localManga;
		this.chapterURL=chapterURL;
		this.chapterNumber=chapterNumber;
		//match this chapter with the local ones (if there's no localChapter, create one)
		localChapter=localManga.findChapter(chapterNumber);
	}
	
	//Open each page in this chapter and search for the image url
	private String extractURL(String urltext){
	    BufferedReader in = null;
	    try {
	    	URL url = new URL(urltext);
	    	in = new BufferedReader(new InputStreamReader(url.openStream()));
	    	String line;
	    	while ((line = in.readLine()) != null) {
    			int before=line.indexOf("onclick=\"return enlarge()\">");
	    		if(before>=0){
	    			int begin=line.indexOf("img src",before)+9;
	    			int end=line.indexOf("\"",begin+1);
	    			String extractedURL=line.substring(begin,end);
	    			return extractedURL;
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
	    return "";
	}

	//Loop through each page in the chapter and find its image url
	public void fetchAllPageURL(){
		allPageURL=new ArrayList<String>();
		
		int lastPage=findLastPage();
		
		for(int i=1;i<=lastPage;i++){
		    String urltext = chapterURL+i+".html";
		    downloadStatus.setText("Currently Downloading: "+this.toString()+"\nFetching page "+i);
			String extractedURL=extractURL(urltext);
		    allPageURL.add(extractedURL);
		}
	}
	
	private int findLastPage(){
		BufferedReader in = null;
	    try {
	    	URL url = new URL(chapterURL+1+".html");
	    	in = new BufferedReader(new InputStreamReader(url.openStream()));
	    	String line;
	    	while ((line = in.readLine()) != null) {
    			int before=line.indexOf("var total_pages=");
	    		if(before>=0){
	    			return Integer.parseInt(line.substring(before+16,line.length()-1));
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
	    return 0;
	}
	
	//check whether we've already download this chapter or not
	public boolean isDownload(){
		return this.getLocalChapter().isDownload();
	}
	
	//Save to which folder in this computer
	public String saveTo(){
		return this.getLocalChapter().getChapterDirectory();
	}
	
	//Set its LocalChapter's download status to true
	public void setDownload(boolean isDownload){
		this.localChapter.setDownload(isDownload);
	}
	
	public String toString (){
		return this.getChapterNumber()+" / "+this.getLocalManga().getMangaID();
	}
}
