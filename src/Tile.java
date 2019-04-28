import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.text.Position;

public class Tile {
	private ArrayList<Triangle> triangles=new ArrayList<Triangle>();
	private Color color;
	
	public Triangle getTriangleAt(int i) {
		return triangles.get(i);
	}
	
	//removes last triangle if there is, returns true if empty
	public boolean removeLastTriangle() {
		if (!triangles.isEmpty()) triangles.remove(triangles.size()-1);
		System.out.println("removed, "+triangles.size());
		return triangles.isEmpty();
	}
	
	public String ToString() {
		return (getCenter()[0]+","+getCenter()[1]);
	}
	
	public int[] getCenter() {
		int[] center=new int[2];
		center[0]=0;
		center[1]=0;
		
		for(Triangle tr : triangles) {
			for(int xpos : tr.getXPoints())
				center[0]+=xpos;
			for(int ypos : tr.getYPoints())
				center[1]+=ypos;
		}
		center[0]/=triangles.size()*3;
		center[1]/=triangles.size()*3;
		System.out.println("CENTER "+center[0]+" "+center[1]);
		return center;
	}
	
	public void clearTriangles() {
		triangles.clear();
	}
	
	public Tile(Color c){
		color=c;
	}
	
	//public Tile() {};
	
	public void list() {
		System.out.println();
		for(Triangle t : triangles) System.out.println(t);
	}
	
	public void setColor(Color c) {
		color=c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void addTriangle(Triangle t) {
		triangles.add(t);
		System.out.println("added, new: "+triangles.size());
	}
	
	public void fill(Graphics g) {
		for(Triangle t: triangles)
			t.fill(g, color);
	}
	
	public boolean containsTriangle(Triangle t) {
		return triangles.contains(t);
	}
	
	public Tile(Tile rhs) {
		for(Triangle t:rhs.triangles)
			triangles.add(new Triangle(t));
		color=rhs.color;
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

}
