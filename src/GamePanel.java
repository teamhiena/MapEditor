import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GamePanel extends JPanel {
	private Dimension size=new Dimension(700,700);
	private int tilesperrow=8;
	private int stepx=(size.width-20)/tilesperrow;
	private int stepy=(size.width-20)/tilesperrow;
	private Triangle[][][] triangles=new Triangle[tilesperrow][tilesperrow][4];
	private ArrayList<Tile> tiles=new ArrayList<Tile>();
	private Tile newTile=new Tile(Color.CYAN);
	private MouseClickListener mouselist=new MouseClickListener(stepx, stepy);
	private ButtonPanel bp;
	private ArrayList<Edge> edges=new ArrayList<Edge>();
	
	
	class Edge{
		private int[] source=new int[2]; //2hosszu
		private int[] dest=new int[2];
		
		public Edge(int[] p_source, int[] p_dest) {
			source[0]=p_source[0]; 
			source[1]=p_source[0];
			dest[0]=p_dest[0]; 
			dest[1]=p_dest[1];
		}
		
		public void draw(Graphics g) {
			//System.out.println("LINE");
			
			Graphics2D g2d = (Graphics2D) g;
	        Line2D.Double line = new Line2D.Double(source[0], source[1], dest[0], dest[1]); //ez lehet szar

	        g2d.setColor(Color.RED);
	        g2d.draw(line);
			
		}
	}
	
	public GamePanel() {
		super();
		tiles.add(newTile);
		setPreferredSize(size);
		setBackground(Color.LIGHT_GRAY);
		initTriangles();
		//repaint();//erõsen fakultatív
		addMouseListener(mouselist);

	}
	
	public Tile getNewTile() {
		return newTile;
	}
	
	//removes last tile if there is, returns true if empty
	public boolean removeLastTile() {
		if (tiles.size()>1) tiles.remove(tiles.size()-1);

		//System.out.println(tiles.size());
		return (tiles.size()<=1);
	}
	
	public void setBP(ButtonPanel bp) {
		this.bp=bp;
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public void addTile(Tile t) {
		tiles.add(t);
	}
	
	public Triangle getTriangleAt(int i, int j, int k) {
		return triangles[i][j][k];
	}
	
	public void initTriangles(){ //top: 0;right: 1; bottom: 2; left: 3
		Dimension base=new Dimension(0,0);
		int[] x4=new int[4];
		int[] y4=new int[4];
		int[] x3=new int[3];
		int[] y3=new int[3];
		for(int i=0;i<tilesperrow;i++) {
			base.width=0;
			for(int j=0;j<tilesperrow;j++) {
				x4[0]=base.width;
				y4[0]=base.height;
				x4[1]=base.width+stepx;
				y4[1]=base.height;
				x3[2]=base.width+stepx/2;
				y3[2]=base.height+stepy/2;	
				x4[3]=base.width;
				y4[3]=base.height+stepy;
				x4[2]=base.width+stepx;
				y4[2]=base.height+stepy;

				for(int k=0;k<4;k++){
					x3[0]=x4[k];
					y3[0]=y4[k];
					x3[1]=x4[(k+1)%4];
					y3[1]=y4[(k+1)%4];
					triangles[i][j][k]=new Triangle(x3,y3);
				}
				base.width+=stepx;
				//System.out.println(i+" "+j);
			}
			base.height+=stepy;
		}
	}
	
	//háromszögek körvonalukkal
	@Override
	public void paintComponent(Graphics g) {
		//minden triangle körvonalat megrajzol, majd filleli a kész tileokat.
		super.paintComponent(g);
		for(Triangle[][] t3: triangles)
			for(Triangle[] t2:t3)
				for(Triangle t:t2)
					t.draw(g);
		
		for(Tile t: tiles)
			t.fill(g);
		for(Edge e: edges)
			e.draw(g);
		
	}
	
	public void saveNewTile() {
		Tile toAdd=new Tile(newTile);
		tiles.add(toAdd);
		for(Triangle tr:newTile.getTriangles())
			tr.setTile(toAdd);
		newTile.clearTriangles();

		//System.out.println(tiles.size());
	}
	
	public void setNewTileColor(Color c) {
		newTile.setColor(c);
	}
	
	private class MouseClickListener implements MouseListener {
		private int stepx,stepy;
		
		public MouseClickListener(int sx,int sy) {
			stepx=sx; stepy=sy;
		}
		
		@Override
		public void mouseClicked(MouseEvent ev) {
			int x=ev.getX();
			int y=ev.getY();
			//System.out.println("CLICKED " +ev.getX()+" "+ev.getY());
			
			/*int selected;		
			if((y%stepy)<stepy/stepx*(x%stepx)) //0v1
				if((y%stepy)<-stepy/stepx*(x%stepx)+stepy) selected=0;
				else selected=1;
			else
				if((y%stepy)<-stepy/stepx*(x%stepx)+stepy) selected=3;
				else selected=2;
			*/
			
			//ha meg nem newtile akk zsa bele
			//System.out.println("click");
			Triangle clickedTriangle=getPressedTriangle(ev);
			
			if(!newTile.containsTriangle(clickedTriangle))
			{
				newTile.addTriangle(clickedTriangle);
				//;//na ez itt butaság
				if(bp!=null)
				{
					bp.enableSaveBtn();
					bp.enableDelTrBtn();
				}
				
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		MouseEvent pressedEvent;
		@Override
		public void mousePressed(MouseEvent ev) {
			// TODO Auto-generated method stub
			//System.out.println("PRESSED " +ev.getX()+" "+ev.getY());
			pressedEvent=ev;
			
		}
		public Triangle getPressedTriangle(MouseEvent ev) {
			int x=ev.getX();
			int y=ev.getY();
			int selected;		
			
			if((y%stepy)<stepy/stepx*(x%stepx)) //0v1
				if((y%stepy)<-stepy/stepx*(x%stepx)+stepy) selected=0;
				else selected=1;
			else
				if((y%stepy)<-stepy/stepx*(x%stepx)+stepy) selected=3;
				else selected=2;
			//System.out.println(triangles[y/stepy][x/stepx][selected]);
			return triangles[y/stepy][x/stepx][selected];
		}

		@Override
		public void mouseReleased(MouseEvent ev) {
			// TODO Auto-generated method stub
			//System.out.println("RELEASED "+ev.getX()+" "+ev.getY());
			Tile t1=getPressedTriangle(ev).getTile();
			Tile t2=getPressedTriangle(pressedEvent).getTile();
			
			//System.out.println(t1+" "+t2);
			
			if(t1!=null && t2!=null && t1!=t2) {
				edges.add(new Edge(t1.getCenter(),t2.getCenter()));
				System.out.println("kene edge");
			}
			repaint();
			
		}		
	}	
}
