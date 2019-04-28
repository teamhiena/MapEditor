import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonListener;

public class MyFrame extends JFrame{

	private GamePanel gp=new GamePanel();
	private ButtonPanel bp=new ButtonPanel();
	
	public GamePanel getGamePanel() {
		return gp;
	}
	
	public MyFrame() {
		super();
		
		add(gp,BorderLayout.CENTER);
		add(bp,BorderLayout.NORTH);
		gp.setBP(bp);
		bp.setGP(gp);
		
		gp.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		Dimension dim=new Dimension(gp.getSize().width+10, gp.getSize().height+75);
		setSize(dim);
		
	}
	
}
