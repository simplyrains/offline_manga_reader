import java.util.ArrayList;

import javax.swing.JTextArea;

//For MangaInn Only!
//If I have enough time, I will add alternate version for MangaFox later :)

public interface OnlineChapter {

	//Getters 'n Setters
	public LocalManga getLocalManga();
	public void setLocalManga(LocalManga localManga);
	public LocalChapter getLocalChapter();
	public void setLocalChapter(LocalChapter localChapter);
	public String getChapterURL();
	public void setChapterURL(String chapterURL);
	public ArrayList<String> getAllPageURL();
	public void setAllPageURL(ArrayList<String> allPageURL);
	public String getChapterNumber();
	public void setChapterNumber(String chapterNumber);
	public String getMangaID();
	public void setMangaID(String mangaName);
	public JTextArea getDownloadStatus();
	public void setDownloadStatus(JTextArea downloadStatus);

	//Loop through each page in the chapter and find its image url
	public void fetchAllPageURL();
	
	//check whether we've already download this chapter or not
	public boolean isDownload();
	
	//Save to which folder in this computer
	public String saveTo();
	
	//Set its LocalChapter's download status to true
	public void setDownload(boolean isDownload);
	
	public String toString ();
}
