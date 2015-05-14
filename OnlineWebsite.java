import java.util.ArrayList;

//For MangaInn Only!
//If I have enough time, I will add alternate version for MangaFox later :)

public interface OnlineWebsite{

	//Getters 'n Setters
	public ArrayList<OnlineManga> getAllOnlineManga();
	public void setAllOnlineManga(ArrayList<OnlineManga> allOnlineManga) ;
	public LocalWebsite getLocalWebsite();
	public void setLocalWebsite(LocalWebsite localWebsite) ;

	//Begin fetching data
	public void fetchAllOnlineManga();
	
	//Search data that matches the condition and return as a arraylist of manga
	public ArrayList<OnlineManga> SearchAllOnlineManga(String condition);
	
}
