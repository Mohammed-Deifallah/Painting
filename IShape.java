package eg.edu.alexu.csd.oop.drawVector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
//import java.awt.Point;

public interface IShape {
	// color
	public void setColor(Color color);

	public Color getColor();

	// position
	public void setPosition(Point p);

	public Point getPosition();

	// fillColor
	public void setFillColor(Color color);

	public Color getFillColor();

	void draw(Graphics g, Boolean select);

	public Shape clone();
}
