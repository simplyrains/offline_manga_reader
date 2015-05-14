import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

public class Downloader implements Runnable{
	private boolean isStop;
	private DefaultListModel  downloadData;
	private JTextArea downloadStatus;
	
	public void go(){
		isStop=false;
		synchronized (this) {
			this.notifyAll();
		}
	}
	public void stop() {
		isStop=true;
	}
	public Downloader(OnlineChapter a,DefaultListModel downloadData,JTextArea downloadStatus){
		this.downloadData=downloadData;
		this.downloadStatus=downloadStatus;
		downloadData.addElement(a);
		Thread getIt=new Thread(this);
		getIt.start();
	}
	public synchronized OnlineChapter removeFromQueue(int index){
		OnlineChapter temp=(OnlineChapter)downloadData.elementAt(0);
		downloadData.remove(0);
		return temp;
	}
	public synchronized OnlineChapter getFromQueueu(int index){
		return (OnlineChapter)downloadData.elementAt(index);
	}
	public synchronized void addToQueue(OnlineChapter intoQueue){
		int pos = downloadData.getSize();
		downloadData.add(pos, intoQueue);
	}
	public void downloadFirstInQueue(){
		OnlineChapter firstInQueue=this.removeFromQueue(0);
		if (!firstInQueue.isDownload()) {
			firstInQueue.setDownloadStatus(downloadStatus);
			firstInQueue.fetchAllPageURL();
			ArrayList<String> allURLNeed = firstInQueue.getAllPageURL();
			String direc = firstInQueue.getLocalChapter().getChapterDirectory();
			File x = new File(direc);
			if (!x.exists())
				x.mkdirs();
			for (int i = 0; i < allURLNeed.size(); i++) {
				String spacer="";
				downloadStatus.setText("Currently Downloading: "
						+ firstInQueue.toString() + "\nDownloading page "
						+ (i + 1) + "/" + allURLNeed.size());
				downloadStatus.setBackground(new Color(250,250,200));
				if(i<10) spacer="00";
				else if(i<100) spacer="0";
				downloadPage(allURLNeed.get(i),direc+System.getProperty("file.separator")+i+".jpg");
			}
			firstInQueue.setDownload(true);
		}
	}
	
	public void downloadPage(String URL,String destination){
		try {
			File finalDir=new File(destination);
			URL url = new URL(URL);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(finalDir);
			byte[] b = new byte[1024];
			int length;
			while ((length = is.read(b)) != -1) {
				//System.out.println("sdsdsdsdsdsd "+i );
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(){
		while(true){
			//System.out.print(this.allInQueue.isEmpty());
			if(isStop){
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else if(!this.downloadData.isEmpty()){
				synchronized (this) {
					this.notifyAll();
				}
				downloadFirstInQueue();
				this.downloadStatus.setText("There are no download right now");
				downloadStatus.setBackground(new Color(200,200,255));
			}
			else{
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
