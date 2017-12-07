package eg.edu.alexu.csd.oop.drawVector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

/**
 * This is abstract class contains coordinates of Shapes and its color and
 * functions to access to this variable.
 */
abstract class Shape {
	private boolean fill; // boolean variable that determines whether the shape
							// is filled or not
	private int width;
	private int height;
	int x;
	int y;

	private float[] dashingPattern1 = { 2f, 2f };
	Stroke stroke1 = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f);
	private boolean selected;
	private boolean drawing;
	private int x1, y1, x2, y2; // coordinates of shape
	private Color color; // color of shape
	private Rectangle firstPoint;
	private Rectangle secondPoint;
	private Rectangle rightUp;
	private Rectangle leftdown;
	private Rectangle centerPoint;

	/**
	 * constructor that make coordinates zeros and color to black as default of
	 * shapes.
	 */
	public Shape() {
		width = 0;
		height = 0;
		x = 0;
		y = 0;
		drawing = true;
		fill = false;
		firstPoint = new Rectangle();
		rightUp = new Rectangle();
		leftdown = new Rectangle();
		secondPoint = new Rectangle();
		centerPoint = new Rectangle();
		selected = false;
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 0;
		color = Color.BLACK;
	}

	/**
	 * overloaded constructor which takes variables for coordinates and color of
	 * shape
	 */

	public Shape(int x1, int y1, int x2, int y2, Color color, boolean fill) {
		selected = false;
		this.fill = fill;
		width = 0;
		height = 0;
		x = 0;
		y = 0;
		drawing = true;
		fill = false;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
		firstPoint = new Rectangle(x1, y1, 5, 5);
		secondPoint = new Rectangle(x2, y2, 5, 5);
		rightUp = new Rectangle(x1, y2, 5, 5);
		leftdown = new Rectangle(x2, y1, 5, 5);
		leftdown = new Rectangle();
		centerPoint = new Rectangle((x1 + x2) / 2, (y1 + y2) / 2, 5, 5);
	}

	/**
	 * method to set value of x1
	 *
	 * @param x1
	 *            first point in X-axis
	 */
	public void setX1(int x1) {
		this.x1 = x1;
		firstPoint.x = x1;
		rightUp.x = x1;
		centerPoint.x = (x1 + x2) / 2;
	}

	/**
	 * method to set value of y1
	 *
	 * @param y1
	 *            first point in Y-axis
	 */
	public void setY1(int y1) {
		this.y1 = y1;
		firstPoint.y = y1;
		leftdown.y = y1;
		centerPoint.y = (y1 + y2) / 2;
	}

	/**
	 * method to set value of x2
	 *
	 * @param x2
	 *            current point in X-axis
	 */
	public void setX2(int x2) {
		this.x2 = x2;
		secondPoint.x = x2;
		leftdown.x = x2;
		centerPoint.x = (x1 + x2) / 2;

	}

	/**
	 * method to set value of y2
	 *
	 * @param y2
	 *            second point in Y-axis
	 */
	public void setY2(int y2) {
		this.y2 = y2;
		secondPoint.y = y2;
		rightUp.y = y2;
		centerPoint.y = (y1 + y2) / 2;

	}

	/**
	 * to set color to the shape
	 *
	 * @param color
	 *            the color of shape
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * to get the value of x1
	 *
	 * @return get coordinate first point in X-axis
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * to get the value of y1
	 *
	 * @return get coordinate first point in y-axis
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * to get the value of x2
	 *
	 * @return get coordinate current point in X-axis
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * to get the value of y2
	 *
	 * @return get coordinate current point in y-axis
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * to get the color of shape
	 *
	 * @return color of shape
	 */
	public Color getColor() {
		return color;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		drawing = false;
	}

	public boolean getSelected() {
		return selected;
	}

	public boolean containsFirstBorder(int x, int y) {
		return firstPoint.contains(x, y);
	}

	public boolean containsCenterBorder(int x, int y) {
		return centerPoint.contains(x, y);
	}

	public boolean containsSecondBorder(int x, int y) {
		return secondPoint.contains(x, y);
	}

	public Rectangle getFirstpoint() {
		return firstPoint;
	}

	public Rectangle getSecondpoint() {
		return secondPoint;
	}

	public Rectangle getCenterpoint() {
		return centerPoint;
	}

	public void setLocation(int x1, int y1) {
		this.x = x1;
		this.y = y1;

	}

	public int getWidth() {

		return width;
	}

	public void addX(int x) {
		this.x += x;
	}

	public void addY(int y) {
		this.y += y;
	}

	public void addWidth(int w) {

		this.width += w;
	}

	public void addHeight(int h) {

		this.height += h;
	}

	// public void setLocation(int x1,int y1){
	// this.x1 = x1;
	// this.y1 = y1;
	//
	//
	// }
	/**
	 * to get the height of shape
	 *
	 * @return the height of shape
	 */
	public int getHeight() {

		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean getDrawing() {

		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	/**
	 * return filled or not
	 */
	public boolean getFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	/**
	 * Abstract method for drawing the shape that will be override
	 */
	abstract public void draw(Graphics g);

	abstract public boolean contains(int x, int y);

	abstract public void move(Point x, MouseEvent event);

	abstract public void resize(Point x, MouseEvent event);

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getRightUp() {
		return rightUp;
	}

	public void setRightUp(Rectangle rightUp) {
		this.rightUp = rightUp;
	}

	public Rectangle getLeftdown() {
		return leftdown;
	}

	public void setLeftdown(Rectangle leftdown) {
		this.leftdown = leftdown;
	}
}