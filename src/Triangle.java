import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class Triangle {
	private int[] xPoints=new int[3];
	private int[] yPoints=new int[3];
	private Tile tile;
	
	public void setTile(Tile pt) {
		tile=pt;
	}
	public Tile getTile() {
		return tile;
	}
	
	public Triangle(int[] x,int[]y) {
		
		for(int i=0;i<3;i++) {
			xPoints[i]=x[i];
			yPoints[i]=y[i];
		}
	}
	
	public int[] getXPoints() {
		return xPoints;
	}
	public int[] getYPoints() {
		return yPoints;
	}
	
	public Triangle(Triangle rhs) {
		for	(int i=0;i<3;i++) {
			xPoints[i]=rhs.xPoints[i];
			yPoints[i]=rhs.yPoints[i];
		}
	}
	
	//for testing
	@Override
	public String toString() {
		return Integer.toString(xPoints[0])+" teszt";
	}

	//for test purposes
	public void draw (Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
	    GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 3);

		polygon.moveTo (xPoints[0], yPoints[0]);

		for (int index = 1; index < 3; index++) {
		         polygon.lineTo(xPoints[index], yPoints[index]);
		}

		g2.draw(polygon);
	}
	
	public void fill (Graphics g, Color c) {
	    Graphics2D g2 = (Graphics2D) g;
	    g.setColor(c);
	    GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 3);

		polygon.moveTo (xPoints[0], yPoints[0]);

		for (int index = 1; index < 3; index++) {
		         polygon.lineTo(xPoints[index], yPoints[index]);
		}

		//ez fill/draw
		g2.fill(polygon);
	}


}
