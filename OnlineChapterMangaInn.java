import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JTextArea;

//For MangaInn Only!
//If I have enough time, I will add alternate version for MangaFox later :)

public class OnlineChapterMangaInn implements OnlineChapter {

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
	public OnlineChapterMangaInn(LocalManga localManga,String chapterURL, String chapterNumber){
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
    			int before=line.indexOf("<img id=\"imgPage\"");
	    		if(before>0){
	    			line = in.readLine();
	    			int begin=line.indexOf("src")+5;
	    			int end=line.indexOf("\"/>");
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
		for(int i=1;i<=100;i++){
		    String urltext = chapterURL+"/page_"+i;
		    downloadStatus.setText("Currently Downloading: "+this.toString()+"\nFetching page "+i);
			String extractedURL=extractURL(urltext);
		    if(extractedURL.indexOf("///")>0){
		    	break;
		    }
		    else{
		    	allPageURL.add(extractedURL);
		    }
		}
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
