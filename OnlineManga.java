import java.util.ArrayList;

//For MangaInn Only!
//If I have enough time, I will add alternate version for MangaFox later :)

public interface OnlineManga {
	
	//Getters 'n Setters
	public LocalWebsite getLocalWebsite();
	public void setLocalWebsite(LocalWebsite localWebsite);
	public LocalManga getLocalManga();
	public void setLocalManga(LocalManga localManga);
	public String getMangaURL() ;
	public void setMangaURL(String mangaURL) ;
	public ArrayList<OnlineChapter> getAllOnlineChapter() ;
	public void setAllOnlineChapter(ArrayList<OnlineChapter> allOnlineChapter) ;
	public String getMangaID();
	public void setMangaID(String mangaID) ;
	
	//Begin fetching data
	public void fetchAllOnlineChapter();
}
