package eg.edu.alexu.csd.oop.drawVector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 * This class inherits from CloseShape and draw the rectangle
 */
public class RectangleComponent extends Shape {
	/**
	 * constructor which calls CloseShape's constructor
	 */
	public RectangleComponent() {
		super();

	}

	/**
	 * Overloaded constructor that takes coordinates, color and fill. by calling
	 * CloseShape's constructor
	 */
	public RectangleComponent(int x1, int y1, int x2, int y2, Color color, boolean fill) {
		super(x1, y1, x2, y2, color, fill);

	}

	/**
	 * Overrides the draw method in CloseShape. It sets the gets the color from
	 * CloseShape to set the color and the values it needs to draw
	 */
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(getColor()); // sets the color
		BasicStroke s = (BasicStroke) g2.getStroke();
		if (getSelected()) {

			g2.draw(getCenterpoint());

			g2.draw(getFirstpoint());
			g2.draw(getSecondpoint());
			g2.draw(getRightUp());

			g2.setStroke(super.stroke1);

		}

		// g2.drawRect( getX(), getY(), getWidth(), getHeight() ); //draws a
		// regular rectangle

		else if (getDrawing()) {

			setX(Math.min(getX1(), getX2()));
			setY(Math.min(getY1(), getY2()));
			setWidth(Math.abs(getX1() - getX2()));
			setHeight(Math.abs(getY1() - getY2()));
		}
		// else {
		if (getFill()) // determines whether fill is true or false
			g2.fillRect(getX(), getY(), getWidth(), getHeight()); // draws a
																	// filled
																	// rectangle
		else
			g2.drawRect(getX(), getY(), getWidth(), getHeight()); // draws a
																	// regular
																	// rectangle
		// }
		g2.setStroke(s);
	}

	@Override
	public boolean contains(int x, int y) {
		return new Rectangle(getX(), getY(), getWidth(), getHeight()).contains(x, y) || containsCenterBorder(x, y);

	}

	@Override
	public void move(Point x, MouseEvent event) {

		int dx = event.getX() - x.x;// x is first point
		int dy = event.getY() - x.y;// y is first point

		addX(dx);
		addY(dy);

		x.x += dx;
		x.y += dy;
		getCenterpoint().x += dx;
		getCenterpoint().y += dy;
		getFirstpoint().x += dx;
		getFirstpoint().y += dy;
		getSecondpoint().x += dx;
		getSecondpoint().y += dy;
		getRightUp().x += dx;
		getRightUp().y += dy;
		getLeftdown().x += dx;
		getLeftdown().y += dy;

	}

	@Override
	public void resize(Point x, MouseEvent event) {
		// if(x.x==Math.min(getX1(), getX2())&&x.y==Math.min(getY1(), getY2())){
		int w = event.getX() - x.x;// x is first point
		int h = event.getY() - x.y;// y is first point

		addWidth(w);
		addHeight(h);

		x.x += w;
		x.y += h;
	}

	// }

}// end Rectangle Class