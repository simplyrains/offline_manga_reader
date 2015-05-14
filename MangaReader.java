import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class MangaReader {
	
	//Universal Part
	private JTextArea status;
	//MangaInn Variable
	private LocalWebsite localMangaInn;
	private OnlineWebsite onlineMangaInn;
	private JButton reloadMangaInn;
	private JButton reloadMangaInnLocal;
	//MangaFox Variable
	private LocalWebsite localMangaFox;
	private OnlineWebsite onlineMangaFox;
	private JButton reloadMangaFox;
	private JButton reloadMangaFoxLocal;
	//Downloader Part
	private Downloader downloader;
	private JScrollPane downloadPane;
	private JTextArea downloadStatus;
	private DefaultListModel downloadData;
	private JList downloadQueue;
	private JButton addToDownload;
	private JButton start;
	private JButton stop;
	//Search Part
	private JTextArea searchCondition;
	private JList mangaBox;
	private JScrollPane mangaPane;
	private DefaultListModel  mangaBoxData;
	private JList chapterBox;
	private JScrollPane chapterPane;
	private DefaultListModel  chapterBoxData;
	private ArrayList<OnlineManga> searchResult;
	private ArrayList<OnlineChapter> allChapter;
	private OnlineManga choosenManga;
	//Viewer Part
	private JComboBox mangaBoxLocal;
	private DefaultComboBoxModel  mangaBoxDataLocal;
	private JComboBox chapterBoxLocal;
	private DefaultComboBoxModel  chapterBoxDataLocal;
	private ArrayList<LocalManga> allLocalManga;
	private ArrayList<LocalChapter> allLocalChapter;
	private LocalChapter choosenLocalChapter;
	private JButton readThis;

	private JPanel viewer,s1,s2;
	
	public MangaReader(){
		//INITIALIZE
		JFrame.setDefaultLookAndFeelDecorated(false);
		JFrame frame=new JFrame();
		frame.setTitle("MangaReader");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		Container cp=frame.getContentPane();
		cp.setLayout(new GridLayout(3,1));
		cp=frame.getContentPane();
		cp.setLayout(new BorderLayout());
		
		//STATUS
		status=new JTextArea();
		status.setEditable(false);
		
		//VIEWER PART
		mangaBoxDataLocal=new DefaultComboBoxModel();
		chapterBoxDataLocal=new DefaultComboBoxModel();
		mangaBoxLocal=new JComboBox();
		mangaBoxLocal.setPreferredSize(new Dimension(200,25));
		mangaBoxLocal.setBackground(new Color(255,158,159));		
		chapterBoxLocal=new JComboBox();
		chapterBoxLocal.setPreferredSize(new Dimension(200,25));
		chapterBoxLocal.setBackground(new Color(255,232,162));
		readThis=new JButton("Read This Chapter");
		reloadMangaInnLocal=new JButton("MangaInn");
		reloadMangaFoxLocal=new JButton("MangaFox");
		
		viewer=new JPanel();
		FlowLayout f=new FlowLayout();
		f.setHgap(10);
		viewer.setLayout(f);
		viewer.add(new JLabel("Read Manga from "));
		viewer.add(reloadMangaInnLocal);
		viewer.add(reloadMangaFoxLocal);
		viewer.add(mangaBoxLocal);
		viewer.add(chapterBoxLocal);
		viewer.add(readThis);
		
		//Searcher
		mangaBoxData=new DefaultListModel();
		chapterBoxData=new DefaultListModel();
		mangaBox=new JList(mangaBoxData);
		mangaBox.setBackground(new Color(59,225,167));
		mangaPane=new JScrollPane(mangaBox);
		chapterBox=new JList(chapterBoxData);
		chapterBox.setBackground(new Color(184,162,255));
		chapterPane=new JScrollPane(chapterBox);
		reloadMangaInn=new JButton("Search from MangaInn");
		reloadMangaFox=new JButton("Search from MangaFox");
		addToDownload=new JButton("Add to Download");
		downloadData=new DefaultListModel();
		downloadQueue=new JList(downloadData);
		downloadQueue.setBackground(new Color(84,187,240));
		downloadPane=new JScrollPane(downloadQueue);
		start=new JButton("Start");
		stop=new JButton("Stop");
		searchCondition=new JTextArea("");
		searchCondition.setPreferredSize(new Dimension(300,25));
		searchCondition.setBackground(new Color(200,200,200));
		downloadStatus=new JTextArea();
		downloadStatus.setEditable(false);
		
		//Searcher: UPPER PART
		s1=new JPanel();
		FlowLayout ff=new FlowLayout();
		s1.setLayout(ff);
		s1.add(new JLabel("Search Term: "));
		s1.add(searchCondition);
		s1.add(reloadMangaFox);
		s1.add(reloadMangaInn);
		
		//Searcher: LOWER PART(RESULT+DOWNLOAD)
		s2=new JPanel();
		GridLayout g=new GridLayout(1,3);
		g.setHgap(10);
		g.setVgap(10);
		s2.setLayout(g);
		
		JPanel s20=new JPanel(new BorderLayout());
		s20.add(new JLabel("Search Result: "),BorderLayout.NORTH);
		s20.add(mangaPane,BorderLayout.CENTER);

		JPanel s21=new JPanel(new BorderLayout());
		s21.add(new JLabel("Chapter: "),BorderLayout.NORTH);
		s21.add(chapterPane,BorderLayout.CENTER);
		
		JPanel s22=new JPanel(new BorderLayout());
		
		JPanel s220=new JPanel(new GridLayout(3,1));
		s220.add(new JLabel("Current Download:"));
		s220.add(downloadStatus);
		s220.add(new JLabel("Download Queue:"));
		
		JPanel s222=new JPanel(new GridLayout(1,3));
		s222.add(addToDownload);
		s222.add(start);
		s222.add(stop);
		
		s22.add(s220,BorderLayout.NORTH);
		s22.add(downloadPane,BorderLayout.CENTER);
		s22.add(s222,BorderLayout.SOUTH);
		
		s2.add(s20);
		s2.add(s21);
		s2.add(s22);
		
		//FINALIZE
		cp.add(viewer,BorderLayout.NORTH);
		JPanel searcher=new JPanel(new BorderLayout());
		searcher.add(s1,BorderLayout.NORTH);
		searcher.add(s2,BorderLayout.CENTER);
		cp.add(searcher,BorderLayout.CENTER);
		cp.add(status,BorderLayout.SOUTH);
		
		//CODING: LOCAL PART
		status.setText("Reading Local Storage...");
		String userHome=System.getProperty("user.home");
		String slash=System.getProperty("file.separator");
		localMangaInn=new LocalWebsite(userHome+slash+"MangaReader"+slash+"MangaInn");
		localMangaFox=new LocalWebsite(userHome+slash+"MangaReader"+slash+"MangaFox");
		
		reloadMangaInnLocal.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	boolean change=false;
	        	LocalManga first=null;
	        	allLocalManga=localMangaInn.getAllLocalManga();
	    		mangaBoxDataLocal=new DefaultComboBoxModel();
	    		if (allLocalManga!=null) {
	    			for (int i = 0; i < allLocalManga.size(); i++) {
	    				if(allLocalManga.get(i).getAllLocalChapter().size()>0){
	    					mangaBoxDataLocal.addElement(allLocalManga.get(i));
	    					if(change==false) first=allLocalManga.get(i);
	    					change=true;
	    				}
	    			}
	    		}
	    		if(mangaBoxDataLocal.getSize()>0&&change){
	    			setManga(first);
	    		}
	    		mangaBoxLocal.setModel(mangaBoxDataLocal);
	        }
	    });
		reloadMangaFoxLocal.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	boolean change=false;
	        	LocalManga first=null;
	        	allLocalManga=localMangaFox.getAllLocalManga();
	    		mangaBoxDataLocal=new DefaultComboBoxModel();
	    		if (allLocalManga!=null) {
	    			for (int i = 0; i < allLocalManga.size(); i++) {
	    				if(allLocalManga.get(i).getAllLocalChapter().size()>0){
	    					mangaBoxDataLocal.addElement(allLocalManga.get(i));
	    					if(change==false) first=allLocalManga.get(i);
	    					change=true;
	    				}
	    			}
	    		}
	    		if(mangaBoxDataLocal.getSize()>0&&change){
	    			setManga(first);
	    		}
	    		mangaBoxLocal.setModel(mangaBoxDataLocal);
	        }
	    });
	    mangaBoxLocal.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	if (mangaBoxDataLocal.getSize()>0) {
					LocalManga choosenLocalManga = (LocalManga) mangaBoxLocal.getSelectedItem();
					if ("comboBoxChanged".equals(evt.getActionCommand())) {
						setManga(choosenLocalManga);
					}
				}
	        }
	    });
	    chapterBoxLocal.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	choosenLocalChapter = (LocalChapter)chapterBoxLocal.getSelectedItem();
	        }
	    });
	    readThis.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	if(choosenLocalChapter!=null){
	    			ArrayList<String> allPageAddress=choosenLocalChapter.getAllPageAddress();
	    	    	ViewImage a=new ViewImage(choosenLocalChapter);
	    		}
	        }
	    });
		

		frame.setSize(1000,400);
		frame.setVisible(true);
		
		//CONDING: SEARCHER PART
		downloadStatus.setText("There are no download right now");
		downloadStatus.setBackground(new Color(200,200,255));

		status.setText("[CANNOT SEARCH RIGHT NOW] Reading Online Website: MangaFox");
		status.setBackground(new Color(225,99,99));
		onlineMangaFox=new OnlineWebsiteMangaFox(localMangaFox);
		onlineMangaFox.fetchAllOnlineManga();
		
		status.setText("[CANNOT SEARCH RIGHT NOW] Reading Online Website: MangaInn");
		status.setBackground(new Color(225,199,199));
		onlineMangaInn=new OnlineWebsiteMangaInn(localMangaInn);
		onlineMangaInn.fetchAllOnlineManga();
		

		status.setText("[CAN SEARCH NOW] Fetch data complete.");
		status.setBackground(Color.WHITE);
		
		reloadMangaInn.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0) {
				searchResult=onlineMangaInn.SearchAllOnlineManga(searchCondition.getText());
				mangaBoxData=new DefaultListModel();
				for(int i=0;i<searchResult.size();i++){
					mangaBoxData.addElement(searchResult.get(i).getMangaID());
				}
				mangaBox.setModel(mangaBoxData);
			}
		});
		reloadMangaFox.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0) {
				searchResult=onlineMangaFox.SearchAllOnlineManga(searchCondition.getText());
				mangaBoxData=new DefaultListModel();
				for(int i=0;i<searchResult.size();i++){
					mangaBoxData.addElement(searchResult.get(i).getMangaID());
				}
				mangaBox.setModel(mangaBoxData);
			}
		});
		mangaBox.addMouseListener(new MouseAdapter() {	
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() >= 2){
					if (searchResult!=null) {
						int index=mangaBox.locationToIndex(evt.getPoint());
						choosenManga=searchResult.get(index);
						status.setText(choosenManga.getMangaID()+": Begin Fetch Chapter Data");
						choosenManga.fetchAllOnlineChapter();
						status.setText(choosenManga.getMangaID()+": Fetch Complete");
						allChapter=choosenManga.getAllOnlineChapter();
						chapterBoxData=new DefaultListModel();
						for(int i=0;i<allChapter.size();i++){
							chapterBoxData.addElement(allChapter.get(i).getChapterNumber());
						}
						chapterBox.setModel(chapterBoxData);
					}
				}
			}
		});
		addToDownload.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0) {
				if (allChapter!=null) {
					for (int i = 0; i < allChapter.size(); i++) {
						if (chapterBox.isSelectedIndex(i)) {
							//addToQueue(allChapter.get(i));
							if (downloader == null)
								downloader = new Downloader(allChapter.get(i),downloadData, downloadStatus);
							else {
								downloader.addToQueue(allChapter.get(i));
							}
						}
					}
				}
			}
		});
		start.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0) {
				if(downloader!=null){
					downloader.go();
					start.setText("Stop after this");
				}
			}
		});

		stop.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0) {
				if(downloader!=null){
					downloader.stop();
				}
			}
		});
	}
	
	private void setManga(LocalManga choosenLocalManga){
		if(choosenLocalManga==null) return;
		allLocalChapter = choosenLocalManga.getAllLocalChapter();
		chapterBoxDataLocal = new DefaultComboBoxModel();
		for (int i = 0; i < allLocalChapter.size(); i++) {
			if(allLocalChapter.get(i).getAllPageAddress().size()>0){
				chapterBoxDataLocal.addElement(allLocalChapter.get(i));
			}
		}
		chapterBoxLocal.setModel(chapterBoxDataLocal);
		//Choose the first chapter as the Default Chapter
		choosenLocalChapter=allLocalChapter.get(0);
	}
	
	public static void main(String[] args) {
		MangaReader a=new MangaReader();
	}
}
