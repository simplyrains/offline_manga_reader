import java.util.ArrayList;
import java.io.File;

public class LocalWebsite {

	private String websiteDirectory;
	private ArrayList<LocalManga> allLocalManga;
	
	public String getWebsiteDirectory() {return websiteDirectory;}
	public void setWebsiteDirectory(String websiteDirectory) {this.websiteDirectory = websiteDirectory;}
	public ArrayList<LocalManga> getAllLocalManga() {return allLocalManga;}
	public void setAllLocalManga(ArrayList<LocalManga> allLocalManga) {this.allLocalManga = allLocalManga;}

	public LocalWebsite(String websiteDirectory){
		allLocalManga= new ArrayList<LocalManga>();
		this.websiteDirectory=websiteDirectory;
		File folder=new File(this.websiteDirectory);
		if(!folder.exists())folder.mkdirs();
		File[] listOfFiles=folder.listFiles();
		if (listOfFiles!=null) {
			//Read all the subfolder in this.websiteDirectory and create LocalManga from each subfolder
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isDirectory()) {
					String mangaID = listOfFiles[i].getName(); //each subfolder's name is also its chapter number, so..
					LocalManga newManga = new LocalManga(websiteDirectory,mangaID);
					allLocalManga.add(newManga);
				}
			}
		}
	}
	
	//Find manga with the following mangaID
	public LocalManga findID(String mangaID){
		for(int i=0;i<this.allLocalManga.size();i++){
			if(this.allLocalManga.get(i).getMangaID().equals(mangaID)){
				return this.allLocalManga.get(i);
			}
		}
		//Not found one, so create new one instead
		LocalManga newManga=new LocalManga(websiteDirectory,mangaID);
		this.allLocalManga.add(newManga);
		return newManga;
	}
}
