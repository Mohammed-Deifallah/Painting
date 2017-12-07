package eg.edu.alexu.csd.oop.drawVector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * This class handles mouse events and uses them to draw shapes. It contains a
 * dynamic stack currShapes which is the shapes drawn on the panel. It contains
 * a dynamic stack clearedShape which is the shapes cleared from the panel. It
 * has many variables for the current shape [type, variable to store shape
 * object, color, fill]. It contains a JLabel called statusLabel for the mouse
 * coordinates It has mutator methods for currentShapeType, currentShapeColor
 * and currentShapeFilled. It has methods for undoing, redoing and clearing
 * shapes. It has a private inner class MouseHandler which extends MouseAdapter
 * and handles mouse and mouse motion events used for drawing the current shape.
 */

public class DrawPanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Shape> arrayOfShapes;// array of shapes
	private Stack<ArrayList<Shape>> currShapes; // dynamic stack of
	// dynamic stack of cleared shapes from undo
	private Stack<ArrayList<Shape>> clearedShapes;
	private Shape square; // a variable for the loaded square
	// current Shape variables
	private int currentShapeType; // 0 for line, 1 for rect, 2 for oval
	private Shape currentShapeObject; // stores the current shape object
	private Color currentShapeColor; // current shape color
	// determine whether shape is filled or not
	private boolean currentShapeFilled;
	private Point dragPoint;

	/**
	 * This constructor initializes the dynamic stack for currShapes and
	 * clearedShapes. It sets the current shape variables to default values. It
	 * initializes statusLabel from JLabel passed in. Sets up the panel and adds
	 * event handling for mouse events.
	 */
	public DrawPanel() {
		dragPoint = new Point();
		arrayOfShapes = new ArrayList<Shape>();
		// initialize currShapes dynamic stack
		currShapes = new Stack<ArrayList<Shape>>();
		// initialize cleardShapes dynamic stack
		clearedShapes = new Stack<ArrayList<Shape>>();
		currShapes.push(new ArrayList<Shape>());
		// Initialize current Shape variables
		currentShapeType = 0;
		currentShapeObject = null;
		square = null;
		currentShapeColor = Color.BLACK;
		currentShapeFilled = false;

		setLayout(new BorderLayout()); // sets layout to border layout; default
										// is flow layout
		setBackground(Color.WHITE); // sets background color of panel to white

		// event handling for mouse and mouse motion events
		MouseHandler handler = new MouseHandler();
		addMouseListener(handler);
		addMouseMotionListener(handler);
	}

	/**
	 * Calls the draw method for the existing shapes.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// draw the shapes
		ArrayList<Shape> shapeArray = arrayOfShapes;
		for (int counter = shapeArray.size() - 1; counter >= 0; counter--)
			if (shapeArray.get(counter) != null)
				shapeArray.get(counter).draw(g);
		// draws the current Shape Object if it is not null
		if (currentShapeObject != null)
			currentShapeObject.draw(g);
	}

	// Mutator methods for currentShapeType, currentShapeColor and
	// currentShapeFilled

	/**
	 * Sets the currentShapeType to type (0 for line, 1 for rect, 2 for oval)
	 * passed in.
	 */
	public void setCurrentShapeType(int type) {
		currentShapeType = type;
	}

	/**
	 * Sets the currentShapeColor to the Color object passed in. The Color
	 * object contains the color for the current shape.
	 */
	public void setCurrentShapeColor(Color color) {
		currentShapeColor = color;
	}

	/**
	 * Sets the boolean currentShapeFilled to boolean filled passed in. If
	 * filled=true, current shape is filled. If filled=false, current shape is
	 * not filled.
	 */
	public void setCurrentShapeFilled(boolean filled) {
		currentShapeFilled = filled;
	}

	/**
	 * Clear the last shape drawn and calls repaint() to redraw the panel if
	 * clearedShapes is not empty
	 */
	public void clearLastShape() {
		if (!currShapes.isEmpty()) {
			currentShapeObject = null;
			clearedShapes.push(currShapes.pop());
			if (!currShapes.empty())
				arrayOfShapes = currShapes.peek();
			else
				arrayOfShapes = new ArrayList<Shape>();
			repaint();
		}
	}

	/**
	 * Redo the last shape cleared if clearedShapes is not empty It calls
	 * repaint() to redraw the panel.
	 */
	public void redoLastShape() {
		currentShapeObject = null;
		if (!clearedShapes.isEmpty()) {
			currShapes.push(arrayOfShapes = clearedShapes.pop());
			repaint();
		}
	}

	/**
	 * Remove all shapes in current drawing. Also makes clearedShapes empty
	 * since you cannot redo after clear. It called repaint() to redraw the
	 * panel.
	 */
	public void clearDrawing() {
		arrayOfShapes.clear();
		currShapes.clear();
		clearedShapes.clear();
		currentShapeObject = null;
		repaint();
	}

	public Shape findShapeSelected(int x, int y) {
		ArrayList<Shape> shapeArray = arrayOfShapes;
		Shape selectedShape = null;
		for (int counter = shapeArray.size() - 1; counter >= 0; counter--) {

			if (shapeArray.get(counter) != null && shapeArray.get(counter).contains(x, y)) {
				selectedShape = shapeArray.get(counter);
			} else {
				if (shapeArray.get(counter) != null)
					shapeArray.get(counter).setSelected(false);
			}
		}
		return selectedShape;
	}

	public void deleteSelectedShape() {
		if (currentShapeObject.getSelected()) {
			arrayOfShapes.remove(currentShapeObject);
			currentShapeObject = null;
			currShapes.push(new ArrayList<Shape>(arrayOfShapes));
			repaint();
		}
	}

	public void saveJson(String path) {
		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.alias("shapes", arrayOfShapes.getClass());
		try {
			File file = new File(path);
			FileWriter file2 = new FileWriter(file);
			file2.write(xstream.toXML(arrayOfShapes));
			file2.flush();
			file2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadJson(String path) {
		try {
			arrayOfShapes.clear();
			Scanner in = new Scanner(new File(path));
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.alias("shapes", arrayOfShapes.getClass());
			String temp = in.useDelimiter("\\Z").next();
			arrayOfShapes = (ArrayList<Shape>) xstream.fromXML(temp);
			in.close();
			boolean q = false;
			for (int i = 0; i < arrayOfShapes.size(); i++) {
				if (arrayOfShapes.get(i).getClass().equals(SquareComponent.class) && square == null)
					q = true;
			}
			if (q) {
				JOptionPane.showMessageDialog(null, "You should load the square to retrieve your work");
			} else {
				repaint();
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public void saveXML(String path) {
		XStream xstream = new XStream(new StaxDriver());
		String s = xstream.toXML(arrayOfShapes);
		try {
			File file = new File(path);
			FileWriter file2 = new FileWriter(file);
			file2.write(s);
			file2.flush();
			file2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadXML(String path) {
		try {
			arrayOfShapes.clear();
			Scanner in = new Scanner(new File(path));
			XStream xstream = new XStream();
			String temp = in.useDelimiter("\\Z").next();
			arrayOfShapes = (ArrayList<Shape>) xstream.fromXML(temp);
			in.close();
			boolean q = false;
			for (int i = 0; i < arrayOfShapes.size(); i++) {
				if (arrayOfShapes.get(i).getClass().equals(SquareComponent.class) && square == null)
					q = true;
			}
			if (q) {
				JOptionPane.showMessageDialog(null, "You should load the square to retrieve your work");
			} else {
				repaint();
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public void loadPlug(String path, String name) {
		File file = new File(/* "C:\\Users\\lenovo pc\\Documents\\" */path);
		try {
			// Convert File to a URL
			@SuppressWarnings("deprecation")
			java.net.URL url = file.toURL(); // file:/c:/myclasses/
			java.net.URL[] urls = new java.net.URL[] { url };

			// Create a new class loader with the directory
			@SuppressWarnings("resource")
			ClassLoader cl = new URLClassLoader(urls);
			path = path.substring(0, path.indexOf("eg"));
			path = path.replace("\\", "\\\\");
			// Load in the class; MyClass.class should be located in
			// the directory file:/c:/myclasses/com/mycompany
			Class<?> cls = cl.loadClass("eg.edu.alexu.csd.oop.drawVector." + name.substring(0, name.indexOf(".")));
			try {
				square = (Shape) cls.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException();
			} catch (IllegalAccessException e) {
				throw new RuntimeException();
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Private inner class that implements MouseAdapter and does event handling
	 * for mouse events.
	 */
	private class MouseHandler extends MouseAdapter {
		/**
		 * When mouse is pressed draw a shape object based on type, color and
		 * filled. X1,Y1 & X2,Y2 coordinate for the drawn shape are both set to
		 * the same X & Y mouse position.
		 */
		@Override
		public void mousePressed(MouseEvent event) {
			switch (currentShapeType) // 0 for line, 1 for rect, 2 for oval
			{
			case 0:
				currentShapeObject = new LineComponent(event.getX(), event.getY(), event.getX(), event.getY(),
						currentShapeColor, currentShapeFilled);
				break;
			case 1:
				currentShapeObject = new RectangleComponent(event.getX(), event.getY(), event.getX(), event.getY(),
						currentShapeColor, currentShapeFilled);
				break;
			case 2:
				currentShapeObject = new EllipseComponent(event.getX(), event.getY(), event.getX(), event.getY(),
						currentShapeColor, currentShapeFilled);
				break;
			case 3:
				currentShapeObject = new CircleComponent(event.getX(), event.getY(), event.getX(), event.getY(),
						currentShapeColor, currentShapeFilled);
				break;
			case 4:
				if (square != null) {
					currentShapeObject = new SquareComponent(event.getX(), event.getY(), event.getX(), event.getY(),
							currentShapeColor, currentShapeFilled);
				} else {
					JOptionPane.showMessageDialog(null, "You should load that shape");
				}
				break;
			case 5:
				currentShapeObject = new TriangleComponent(event.getX(), event.getY(), event.getX(), event.getY(),
						currentShapeColor, currentShapeFilled);
				break;
			case 6:
				currentShapeObject = findShapeSelected(event.getX(), event.getY());
				if (currentShapeObject != null && !currentShapeObject.getSelected()) {
					currentShapeObject.setSelected(true);
					dragPoint.x = event.getX();
					dragPoint.y = event.getY();
				} else if (currentShapeObject != null && currentShapeObject.getSelected()) {
					dragPoint.x = event.getX();
					dragPoint.y = event.getY();
				}
				break;
			case 7:
				currentShapeObject = findShapeSelected(event.getX(), event.getY());
				if (currentShapeObject != null && !currentShapeObject.getFill()) {
					currentShapeObject.setFill(true);
				}

				arrayOfShapes.add(currentShapeObject);
				currShapes.push(new ArrayList<Shape>(arrayOfShapes)); // addFront
																		// currentShapeObject
																		// onto
																		// currShapes

				break;
			}// end switch case
			repaint();
		} // end method mousePressed

		/**
		 * When mouse is released set currentShapeObject's x2 & y2 to mouse pos.
		 * Then addFront currentShapeObject onto the currShapes dynamic Stack
		 * and set currentShapeObject to null [clearing current shape object
		 * since it has been drawn]. Lastly, it clears all shape objects in
		 * clearedShapes [because you cannot redo after a new drawing] and calls
		 * repaint() to redraw panel.
		 */
		@Override
		public void mouseReleased(MouseEvent event) {
			if (currentShapeObject != null && !currentShapeObject.getSelected() && currentShapeType != 7) {
				// sets currentShapeObject x2 & Y2
				currentShapeObject.setX2(event.getX());
				currentShapeObject.setY2(event.getY());
				arrayOfShapes.add(currentShapeObject);
				// addFront currentShapeObject onto currShapes
				currShapes.push(new ArrayList<Shape>(arrayOfShapes));
				clearedShapes.clear(); // clears clearedShapes
			}

			else if (currentShapeObject != null && currentShapeObject.getSelected()
					&& (currentShapeObject.containsCenterBorder(dragPoint.x, dragPoint.y))) {
				currentShapeObject.move(dragPoint, event);
				// arrayOfShapes.add(currentShapeObject); currShapes.push(new
				// ArrayList<Shape>(arrayOfShapes)); //addFront
				// currentShapeObject onto currShapes

			}
			// currentShapeObject.resize(dragPoint, event); }

			else if (currentShapeObject != null && currentShapeObject.getSelected()
			// &&
			// (currentShapeObject.containsFirstBorder(dragPoint.x,dragPoint.y)||
			// currentShapeObject.containsFirstBorder(dragPoint.x,dragPoint.y))
			) {
				currentShapeObject.resize(dragPoint, event);
				// arrayOfShapes.add(currentShapeObject);
				// addFront currentShapeObject onto currShapes
				// currShapes.push(new ArrayList<Shape>(arrayOfShapes));
			}
			// }
			repaint();

		} // end method mouseReleased

		/**
		 * This method gets the mouse pos when it is moving and sets it to
		 * statusLabel.
		 */
		@Override
		public void mouseMoved(MouseEvent event) {

		}

		/**
		 * This method gets the mouse position when it is dragging and sets x2 &
		 * y2 of current shape to the mouse pos It also gets the mouse position
		 * when it is dragging and sets it to statusLabel Then it calls
		 * repaint() to redraw the panel
		 */
		@Override
		public void mouseDragged(MouseEvent event) {
			if (currentShapeObject != null && !currentShapeObject.getSelected()) {
				// sets currentShapeObject x2 & Y2
				currentShapeObject.setX2(event.getX());
				currentShapeObject.setY2(event.getY());
			} else if (currentShapeObject != null && currentShapeObject.getSelected()

					&& currentShapeObject.containsCenterBorder(dragPoint.x, dragPoint.y)) {
				currentShapeObject.move(dragPoint, event);
				// arrayOfShapes.add(currentShapeObject);
				// addFront currentShapeObject onto currShapes
				// currShapes.push(new ArrayList<Shape>(arrayOfShapes));
				// currentShapeObject.resize(dragPoint, event);
			} else if (currentShapeObject != null && currentShapeObject.getSelected()

			// && (currentShapeObject.containsFirstBorder(dragPoint.x,
			// dragPoint.y)||
			// currentShapeObject.containsSecondBorder(dragPoint.x,
			// dragPoint.y))
			) {
				currentShapeObject.resize(dragPoint, event);
				// arrayOfShapes.add(currentShapeObject);
				// addFront currentShapeObject onto currShapes
				// currShapes.push(new ArrayList<Shape>(arrayOfShapes));
				// currentShapeObject.resize(dragPoint, event);
			}
			repaint();

		} // end method mouseDragged

	}// end MouseHandler

} // end class DrawPanel