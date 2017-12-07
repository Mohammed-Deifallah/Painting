package eg.edu.alexu.csd.oop.drawVector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

/**
 * This class inherits from MyBoundedShape and is responsible for drawing a
 * oval.
 */
public class CircleComponent extends Shape {
	/**
	 * R is the reduis of circle
	 */
	private int R;

	/**
	 * constructor which calls constructor of CloseShape and set reduis is zero.
	 */

	public CircleComponent() {
		super();
		R = 0;
	}

	/**
	 * Overloaded constructor that takes coordinates, color and fill. and set
	 * them in CloseShape and set reduis zero
	 */
	public CircleComponent(int x1, int y1, int x2, int y2, Color color, boolean fill) {
		super(x1, y1, x2, y2, color, fill);

	}

	/**
	 * Overrides the draw method in CloseShape. It sets the gets the color from
	 * CloseShape to set the color and the values it needs to draw.
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// to calculate the distance between first point and current point
		g.setColor(getColor()); // sets the color
		BasicStroke s = (BasicStroke) g2.getStroke();
		if (getSelected()) {
			getCenterpoint().x = getX1();
			getCenterpoint().y = getY1();
			g2.draw(getCenterpoint());
			g2.setStroke(super.stroke1);

		}
		if (getDrawing()) {
			getCenterpoint().x = getX1();
			getCenterpoint().y = getY1();

		}
		R = (int) Math.round(new Point(super.getX1(), super.getY1()).distance(new Point(super.getX2(), super.getY2())));
		if (getFill()) // determines whether fill is true or false
			g.fillOval(super.getX1() - R, super.getY1() - R, R * 2, R * 2); // draws
																			// a
																			// filled
																			// oval
		else
			g.drawOval(super.getX1() - R, super.getY1() - R, R * 2, R * 2); // draws
																			// a
																			// regular
																			// oval
		/*
		 * Graphics2D g2=(Graphics2D)g;
		 * 
		 * g2.setColor( getColor() ); //sets the color BasicStroke
		 * s=(BasicStroke) g2.getStroke(); if(getSelected()) {
		 * 
		 * g2.draw(getCenterpoint());
		 * 
		 * g2.draw(getFirstpoint()); g2.draw(getSecondpoint());
		 * g2.draw(getRightUp());
		 * 
		 * g2.setStroke(super.stroke1);
		 * 
		 * }
		 * 
		 * // g2.drawRect( getX(), getY(), getWidth(), getHeight() ); //draws a
		 * regular rectangle
		 * 
		 * else if(getDrawing()){
		 * 
		 * setX(Math.min(getX1(), getX2())); setY(Math.min(getY1(), getY2()));
		 * setWidth(Math.abs(getX1() - getX2())); setHeight(Math.abs(getY1() -
		 * getY2())); } //else { if (getFill()) //determines whether fill is
		 * true or false g2.fillRect(getX(), getY(), getWidth(), getHeight() );
		 * //draws a filled rectangle else g2.drawRect(getX(), getY(),
		 * getWidth(), getHeight() ) ; //draws a regular rectangle //}
		 * g2.setStroke(s);
		 */
	}

	@Override
	public boolean contains(int x, int y) {
		return new Ellipse2D.Float(super.getX1() - R, super.getY1() - R, R * 2, R * 2).contains(x, y);

	}

	@Override
	public void move(Point x, MouseEvent event) {

		int dx = event.getX() - x.x;// x is first point
		int dy = event.getY() - x.y;// y is first point

		setX1(getX1() + dx);
		setY1(getY1() + dy);

		x.x += dx;
		x.y += dy;

	}

	@Override
	public void resize(Point x, MouseEvent event) {
		int dx = event.getX() - x.x;// x is first point
		int dy = event.getY() - x.y;// y is first point

		setX2(getX2() + dx);
		setY2(getY2() + dy);

		x.x += dx;
		x.y += dy;

	}

}

// end Circle Class