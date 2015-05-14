import java.io.File;
import java.util.ArrayList;

public class LocalChapter implements Comparable<LocalChapter>{
	
	private LocalManga localManga;
	private boolean isDownload;
	private String chapterDirectory;
	private ArrayList<String> allPageAddress;
	private String chapterNumber;
	private String slash=System.getProperty("file.separator");
	
	//Getters 'n Setters
	public LocalManga getLocalManga() {return localManga;}
	public void setLocalManga(LocalManga localManga) {this.localManga = localManga;}
	public boolean isDownload() {return isDownload;}
	public void setDownload(boolean isDownload) {this.isDownload = isDownload;}
	public String getChapterDirectory() {return chapterDirectory;}
	public void setChapterDirectory(String chapterDirectory) {this.chapterDirectory = chapterDirectory;}
	public ArrayList<String> getAllPageAddress() {return allPageAddress;}
	public void setAllPageAddress(ArrayList<String> allPageAddress) {this.allPageAddress = allPageAddress;}
	public String getChapterNumber() {return chapterNumber;}
	public void setChapterNumber(String chapterNumber) {this.chapterNumber = chapterNumber;}
	
	//Constructor
	public LocalChapter(LocalManga localManga,String mangaDirectory, String chapterNumber,boolean isDownload){
		//System.out.println(chapterNumber+" "+isDownload);
		this.allPageAddress=new ArrayList<String>();
		this.localManga=localManga;
		this.chapterDirectory=mangaDirectory+slash+chapterNumber;
		this.chapterNumber=chapterNumber;
		this.isDownload=isDownload;
		if(isDownload) readData();
		
		/*DEBUG
		for(int i=0;i<this.allPageAddress.size();i++){
			System.out.println("----"+this.allPageAddress.get(i));
		}*/
	}
	
	//Insert new Page into this chapter
	public void addPage(String pageAddress){
		allPageAddress.add(pageAddress);
	}
	
	private void readData(){
		File folder=new File(chapterDirectory);
		if(!folder.exists())folder.mkdirs();
		File[] listOfFiles=folder.listFiles();
		if (listOfFiles!=null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String pageAddress = this.chapterDirectory+slash+listOfFiles[i].getName(); //each subfolder's name is also its chapter number, so..
					this.addPage(pageAddress);
				}
			}
		}
	}
	
	//For Sorting
	public int compareTo(LocalChapter o) {
		return this.chapterNumber.compareTo(((LocalChapter)o).chapterNumber);
	}
	
	//For Display
	public String toString() {
		return this.chapterNumber;
	}
}
