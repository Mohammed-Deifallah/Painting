package eg.edu.alexu.csd.oop.drawVector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Provides the GUI and encapsulates the DrawPanel It creates 3 buttons undo,
 * redo and clear. It creates 2 comboBoxes colors and shapes. It creates 1
 * checkBox filled to select whether shape is filled or not. Has two private
 * inner classes One for handling button events Another for handling both combo
 * box events and checkBox events
 */
public class DrawFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private DrawPanel panel; // draw panel for the shapes
	private JButton delete; // button to undo last drawn shape
	private JButton undo; // button to undo last drawn shape
	private JButton redo; // button to redo an undo
	private JButton clear; // button to clear panel
	private JButton saveXML;
	private JButton loadXML;
	private JButton saveJSON;
	private JButton loadJSON;
	private JButton loadPlug;
	@SuppressWarnings("rawtypes")
	private JComboBox colors; // combobox with color options
	private JFileChooser choose;

	// array of strings containing color options for JComboBox colors
	private String colorOptions[] = { "Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Light Gray", "Magenta",
			"Orange", "Pink", "Red", "White", "Yellow" };

	// aray of Color objects contating Color constants
	private Color colorArray[] = { Color.BLACK, Color.BLUE, Color.CYAN, Color.darkGray, Color.GRAY, Color.GREEN,
			Color.lightGray, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW };

	@SuppressWarnings("rawtypes")
	private JComboBox shapes; // combobox with shape options

	// array of strings containing shape options for JComboBox shapes
	private String shapeOptions[] = { "Line", "Rectangle", "Oval", "Circle", "Square", "Triangle", "Select",
			"Colored" };
	private JCheckBox filled; // checkbox to select whether shape is filled or
								// not

	private JPanel widgetJPanel; // holds the widgets: buttons, comboboxes and
									// checkbox
	private JPanel widgetPadder; // encapsulates widgetJPanel and adds padding
									// around the edges

	/**
	 * This constructor sets the name of the JFrame. It also creates a DrawPanel
	 * object that extends JPanel for drawing the shapes and contains a
	 * statuslabel for mouse position. Initializes widgets for buttons,
	 * comboboxes and checkbox It also adds event handlers for the widgets
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DrawFrame() {
		super("Painting"); // sets the name of DrawFrame

		panel = new DrawPanel(); // create draw panel and pass in JLabel

		// create buttons
		delete = new JButton("Delete");
		undo = new JButton("Undo");
		redo = new JButton("Redo");
		clear = new JButton("Clear");
		saveXML = new JButton("saveXML");
		loadXML = new JButton("loadXML");
		saveJSON = new JButton("saveJson");
		loadJSON = new JButton("loadJson");
		loadPlug = new JButton("load plug-ins");
		choose = new JFileChooser();
		saveXML.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				choose = new JFileChooser();
				int returnValue = choose.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					panel.saveXML(choose.getSelectedFile().getPath());
				}
			}
		});
		loadXML.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				choose = new JFileChooser();
				int returnValue = choose.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						panel.loadXML(choose.getSelectedFile().getPath());
					} catch (Exception p) {
						JOptionPane.showMessageDialog(null, "Wrong file");

					}
				}
			}
		});
		saveJSON.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				choose = new JFileChooser();
				int returnValue = choose.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					panel.saveJson(choose.getSelectedFile().getPath());
				}
			}
		});
		loadJSON.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				choose = new JFileChooser();
				int returnValue = choose.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						panel.loadJson(choose.getSelectedFile().getPath());
					} catch (Exception p) {
						JOptionPane.showMessageDialog(null, "Wrong file");

					}
				}
			}
		});
		loadPlug.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// shapes = new JComboBox(plug);
				// widgetJPanel.add(shapes);
				choose = new JFileChooser();
				int returnValue = choose.showOpenDialog(null);
				// choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);;
				/*
				 * if (returnValue == JFileChooser.APPROVE_OPTION) { //
				 * panel.loadPlug(choose.getSelectedFile().getPath(),
				 * choose.getSelectedFile().getName()); }
				 */
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						panel.loadPlug(choose.getSelectedFile().getPath(), choose.getSelectedFile().getName());
					} catch (Exception p) {
						JOptionPane.showMessageDialog(null, "Wrong file");

					}
				}
			}
		});
		// create comboboxes
		colors = new JComboBox(colorOptions);
		shapes = new JComboBox(shapeOptions);

		// create checkbox
		filled = new JCheckBox("Filled");

		// JPanel object, widgetJPanel, with grid layout for widgets
		widgetJPanel = new JPanel();
		// sets padding between widgets in gridlayout
		widgetJPanel.setLayout(new GridLayout(1, 6, 10, 10));

		// JPanel object, widgetPadder, with flowlayout to encapsulate and pad
		// the widgetJPanel
		widgetPadder = new JPanel();
		// sets padding around the edges
		widgetPadder.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5));

		// add widgets to widgetJPanel
		widgetJPanel.add(undo);
		widgetJPanel.add(redo);
		widgetJPanel.add(delete);
		widgetJPanel.add(clear);

		widgetJPanel.add(saveJSON);
		widgetJPanel.add(loadJSON);
		widgetJPanel.add(saveXML);
		widgetJPanel.add(loadXML);

		widgetJPanel.add(loadPlug);
		widgetJPanel.add(colors);
		widgetJPanel.add(shapes);
		widgetJPanel.add(filled);

		// add widgetJPanel to widgetPadder
		widgetPadder.add(widgetJPanel);

		// add widgetPadder and panel to JFrame
		add(widgetPadder, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		// create new ButtonHandler for button event handling
		ButtonHandler buttonHandler = new ButtonHandler();
		undo.addActionListener(buttonHandler);
		delete.addActionListener(buttonHandler);
		redo.addActionListener(buttonHandler);
		clear.addActionListener(buttonHandler);

		// create handlers for combobox and checkbox
		ItemListenerHandler handler = new ItemListenerHandler();
		colors.addItemListener(handler);
		shapes.addItemListener(handler);
		filled.addItemListener(handler);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(5000, 5000);
		setVisible(true);

	} // end DrawFrame constructor

	/**
	 * private inner class for button event handling
	 */
	private class ButtonHandler implements ActionListener {
		// handles button events
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals("Undo")) {
				panel.clearLastShape();
			} else if (event.getActionCommand().equals("Redo")) {
				panel.redoLastShape();
			} else if (event.getActionCommand().equals("Clear")) {
				panel.clearDrawing();
			} else if (event.getActionCommand().equals("Delete")) {
				panel.deleteSelectedShape();
			}

		} // end method actionPerformed
	} // end private inner class ButtonHandler

	/**
	 * private inner class for checkbox and combobox event handling
	 */
	private class ItemListenerHandler implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			// process filled checkbox events
			if (event.getSource() == filled) {
				boolean checkFill = filled.isSelected() ? true : false; //
				panel.setCurrentShapeFilled(checkFill);
			}

			// determine whether combo box selected
			if (event.getStateChange() == ItemEvent.SELECTED) {
				// if event source is combo box colors pass in colorArray at
				// index selected.
				if (event.getSource() == colors) {
					panel.setCurrentShapeColor(colorArray[colors.getSelectedIndex()]);
				}

				// else if event source is combo box shapes pass in index
				// selected
				else if (event.getSource() == shapes) {
					panel.setCurrentShapeType(shapes.getSelectedIndex());
				}
			}
		} // end method itemStateChanged
	}

	public Color getcolor() {
		return colorArray[colors.getSelectedIndex()];
	}

} // end class DrawFrame