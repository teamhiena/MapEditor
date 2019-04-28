import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;


public class ButtonPanel extends JPanel{

	private JButton savebtn=new JButton("Save new tile");
	private JButton selectcolorbtn=new JButton("Choose color");
	//private JColorChooser jcc=new JColorChooser();
	private JButton deltilebtn=new JButton("deltile");
	private JButton deltrianglebtn=new JButton("deltriangle");
	
	private ButtonListener btnlist=new ButtonListener();
	private GamePanel gp;
	
	public ButtonPanel() {
		super();
		setLayout(new FlowLayout());
		
		selectcolorbtn.setActionCommand("jcc");
		selectcolorbtn.addActionListener(btnlist);
		
		savebtn.setActionCommand("save");
		savebtn.addActionListener(btnlist);
		savebtn.setEnabled(false);
		
		deltilebtn.setActionCommand("deltile");
		deltilebtn.addActionListener(btnlist);
		deltilebtn.setEnabled(false);
		
		deltrianglebtn.setActionCommand("deltriangle");
		deltrianglebtn.addActionListener(btnlist);
		deltrianglebtn.setEnabled(false);
		
		add(savebtn);
		add(selectcolorbtn);
		add(deltilebtn);
		add(deltrianglebtn);
		
	}
	
	public void setGP(GamePanel gp) {
		this.gp=gp;
	}
	
	public void enableSaveBtn() {
		savebtn.setEnabled(true);
	}
	
	public void enableDelTrBtn() {
		deltrianglebtn.setEnabled(true);
	}
	
	public void enableDelTileBtn() {
		deltilebtn.setEnabled(true);
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ev) {//inkabb trycatchbe kene a nullexceptiont

			System.out.println(ev.getActionCommand());
			//SAVE BUTTON
			if (ev.getActionCommand().equals("save")&&gp!=null) {
				savebtn.setEnabled(false);
				gp.saveNewTile();
				deltrianglebtn.setEnabled(false);
				deltilebtn.setEnabled(true);
			}
			//CHOOSE COLOR BUTTON
			else if(ev.getActionCommand().equals("jcc")&&gp!=null)
				gp.setNewTileColor(JColorChooser.showDialog(null, "Select a color", Color.CYAN));	
			
			//DELETE TILE BUTTON
			else if(ev.getActionCommand().equals("deltile")&&gp!=null) {
				if(gp.removeLastTile()) //removes last tile if there is, returns true if empty
					deltilebtn.setEnabled(false);
			}
			//DELETE TRIANGLE BUTTON
			else if(ev.getActionCommand().equals("deltriangle")&&gp!=null){
				
				System.out.println("remove clicked");
				if(gp.getNewTile().removeLastTriangle()) //removes last triangle if there is, returns true if empty
					deltrianglebtn.setEnabled(false);

			}
			gp.repaint();
		}		
	}
}


