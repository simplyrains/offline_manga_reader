import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class ViewImage {
	public ViewImage(LocalChapter toRead){
		ArrayList<String>allDirec=toRead.getAllPageAddress();
		JFrame.setDefaultLookAndFeelDecorated(false);
		JFrame frame=new JFrame();
		Container cp=frame.getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel command=new JPanel(new GridLayout(1, 5));
		JButton next=new JButton("Next!!");
		JButton previous=new JButton("Previous!!");
		for(int i=0;i<5;i++){
			if(i==3)command.add(next);
			else if(i==1)command.add(previous);
			else {
				JPanel dummy=new JPanel();
				command.add(dummy);
			}
		}
		JPanel pic=new JPanel(new CardLayout());
		for(int i=0;i<allDirec.size();i++){
			LoadImageApp temp=new LoadImageApp(allDirec.get(i));
			JScrollPane temp1=new JScrollPane(temp);
			pic.add(temp1, ""+i);
		}
		final JPanel pic1=pic;
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				((CardLayout)pic1.getLayout()).next(pic1);
			}
		});
		previous.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				((CardLayout)pic1.getLayout()).previous(pic1);
			}
		});
		cp.add(command,BorderLayout.NORTH);
		cp.add(pic,BorderLayout.CENTER);
		frame.setSize(new Dimension(700,700));
		frame.setVisible(true);
	}
}
