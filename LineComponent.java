package eg.edu.alexu.csd.oop.drawVector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * This class inherits from Shape and it can draw the line
 */
public class LineComponent extends Shape {
	private float slope;

	/**
	 * constructor which calls the constructor of Shape
	 */
	public LineComponent() {
		super();
	}

	/**
	 * Overloaded constructor that takes coordinates and color.
	 */
	public LineComponent(int x1, int y1, int x2, int y2, Color color, boolean fill) {
		super(x1, y1, x2, y2, color, fill);
		if (y2 != y1)
			slope = (x2 - x1) / (y2 - y1);
		else
			slope = Float.MAX_VALUE;

	}

	/**
	 * Overrides the draw method in shape. It sets the gets the color from shape
	 * and the coordinates it needs to draw .
	 */
	@Override
	public void draw(Graphics g) {

		if (getY2() != getY1())
			slope = (getX2() - getX1()) / (getY2() - getY1());
		else
			slope = Float.MAX_VALUE;
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(getColor()); // sets the color
		BasicStroke s = (BasicStroke) g2.getStroke();
		if (getSelected()) {

			g2.draw(getCenterpoint());

			g2.draw(getFirstpoint());
			g2.draw(getSecondpoint());

			g2.setStroke(super.stroke1);

		}

		g.setColor(getColor()); // sets the color
		g.drawLine(getX1(), getY1(), getX2(), getY2()); // draws the line
	}

	@Override
	public boolean contains(int x, int y) {

		return (

		(x <= Math.max(getX1(), getX2()) && (x >= Math.min(getX1(), getX2())))
				&& ((getY1() == getY2() && getY1() == y) || slope == ((getX2() - x) / (getY2() - y))));
	}

	@Override
	public void move(Point x, MouseEvent event) {
		int dx = event.getX() - x.x;// x is first point
		int dy = event.getY() - x.y;// y is first point

		setX1(getX1() + dx);
		setY1(getY1() + dy);
		setX2(getX2() + dx);
		setY2(getY2() + dy);

		x.x += dx;
		x.y += dy;

	}

	@Override
	public void resize(Point x, MouseEvent event) {
		int dx = event.getX() - x.x;// x is first point
		int dy = event.getY() - x.y;// y is first point

		setX1(getX1() + dx);
		setY1(getY1() + dy);

		getFirstpoint().x += dx;
		getFirstpoint().y += dy;

		x.x += dx;
		x.y += dy;

	}
}// end the Line Class
