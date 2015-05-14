import java.util.ArrayList;
import java.io.File;
public class LocalManga implements Comparable<LocalManga>{

	private String mangaDirectory;
	private ArrayList<LocalChapter> allLocalChapter;
	private String mangaID;
	private String slash=System.getProperty("file.separator");
	
	//Getters 'n Setters
	public String getMangaDirectory() {return mangaDirectory;}
	public void setMangaDirectory(String mangaDirectory) {this.mangaDirectory = mangaDirectory;}
	public ArrayList<LocalChapter> getAllLocalChapter() {return allLocalChapter;}
	public void setAllLocalChapter(ArrayList<LocalChapter> allLocalChapter) {this.allLocalChapter = allLocalChapter;}
	public String getMangaID() {return mangaID;}
	public void setMangaID(String mangaID) {this.mangaID = mangaID;}

	//Constructor
	public LocalManga(String websiteDirectory,String mangaID){
		//System.out.println(mangaID);
		allLocalChapter=new ArrayList<LocalChapter>();
		this.mangaID=mangaID;
		this.mangaDirectory=websiteDirectory+slash+mangaID;
		File folder=new File(this.mangaDirectory);
		if(!folder.exists())folder.mkdirs();
		else {
			File[] listOfFiles = folder.listFiles();
			if (listOfFiles != null) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isDirectory()) {
						String chapterNumber = listOfFiles[i].getName();
						LocalChapter newChapter = new LocalChapter(this,mangaDirectory, chapterNumber, true);
						allLocalChapter.add(newChapter);
					}
				}
			}
		}
	}
	
	//Find chapter with the following chapterNumber
	public LocalChapter findChapter(String chapterNumber){
		for(int i=0;i<this.allLocalChapter.size();i++){
			if((this.allLocalChapter.get(i)).getChapterNumber().equals(chapterNumber)) return allLocalChapter.get(i);
		}
		//Not found one, so create new one instead *** no folder is created!
		LocalChapter newChapter=new LocalChapter(this,mangaDirectory,chapterNumber,false);
		this.allLocalChapter.add(newChapter);
		return newChapter;
	}

	//For Sorting
	public int compareTo(LocalManga o) {
		return this.getMangaID().compareTo(o.getMangaID());
	}
	
	public String toString(){
		return this.getMangaID();
	}
}