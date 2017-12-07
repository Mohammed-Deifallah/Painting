package eg.edu.alexu.csd.oop.drawVector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

public class TriangleComponent extends Shape {

	int x_axix[];
	int y_axix[];

	/**
	 * No parameter constructor which calls the no parameter constructor in
	 * MyBoundedShape.
	 */
	public TriangleComponent() {
		super();
		x_axix = new int[3];
		y_axix = new int[3];

	}

	/**
	 * Overloaded constructor that takes coordinates, color and fill. It passes
	 * them into MyBoundedShape's constructor.
	 */
	public TriangleComponent(int x1, int y1, int x2, int y2, Color color, boolean fill) {
		super(x1, y1, x2, y2, color, fill);
		x_axix = new int[3];
		y_axix = new int[3];

	}

	/**
	 * Overrides the draw method in MyBoundedShape. It sets the gets the color
	 * from MyBoundedShape to set the color and the values it needs to draw from
	 * MyBoundedShape as well.
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor()); // sets the color
		BasicStroke s = (BasicStroke) g2.getStroke();
		if (getSelected()) {
			getCenterpoint().x = getX1() + getX1() / 10;
			getCenterpoint().y = (getY1() + getY2()) / 2;
			g2.draw(getCenterpoint());
			g2.setStroke(super.stroke1);

		}
		if (getDrawing()) {
			getCenterpoint().x = (getX1() + getX2()) / 2;
			getCenterpoint().y = (getY1() + getY2()) / 2;
		}
		x_axix[0] = super.getX1();
		x_axix[1] = super.getX1();
		x_axix[2] = super.getX2();
		y_axix[0] = super.getY1();
		y_axix[1] = super.getY2();
		y_axix[2] = super.getY2();
		if (getFill()) // determines whether fill is true or false
			g2.fillPolygon(x_axix, y_axix, 3); // draws a filled oval
		else
			g2.drawPolygon(x_axix, y_axix, 3); // draws a filled oval
	}

	@Override
	public boolean contains(int x, int y) {
		return new Polygon(x_axix, y_axix, 3).contains(x, y);
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
		getCenterpoint().x += dx;
		getCenterpoint().y += dy;
	}

	@Override
	public void resize(Point x, MouseEvent event) {
		int dx = event.getX() - x.x;// x is first point
		int dy = event.getY() - x.y;// y is first point

		setX1(getX1() + dx);
		setY1(getY1() + dy);
		// setX2(getX2()+dx);
		// setY2(getY2()+dy);

		x.x += dx;
		x.y += dy;
		getCenterpoint().x += dx;
		getCenterpoint().y += dy;

	}

}
