package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class representing an implementation of a calculator similar to the default
 * calculator.
 * 
 * @author KarloFrühwirth
 *
 */
public class Calculator extends JFrame {
	/**
	 * default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * JPanel of the displayed elements
	 */
	private JPanel display;

	/**
	 * Label showing the results of operations
	 */
	private JLabel result;

	/**
	 * Implementation of CalcModel
	 */
	private CalcModelImpl calcImpl = new CalcModelImpl();

	/**
	 * Stack of Doubles
	 */
	private Stack<Double> myStack = new Stack<>();

	/**
	 * boolean representing if checkBox is checked or not
	 */
	private boolean inverse = false;

	/**
	 * Main method, starts the calculator
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Calculator();
			frame.setVisible(true);
		});
	}

	/**
	 * Constructor for the calculator
	 */
	public Calculator() {
		setMinimumSize(new Dimension(600, 250));
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * Method used to initialize the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		this.display = new JPanel(new CalcLayout(3));
		createDisplay();
		createNumbers();
		createBinaryOperations();
		createUnaryOperations();
		createOtherOperations();
		createCheckInvert();
		setPreferredSize(display.getPreferredSize());
		cp.add(display);

	}

	/**
	 * GUI method used to add the inv checkBox
	 */
	private void createCheckInvert() {
		JCheckBox checkBox = new JCheckBox("inv");
		checkBox.setBackground(Color.decode("#72b0cf"));
		checkBox.addActionListener(l -> inverse = !inverse);
		display.add(checkBox, new RCPosition(5, 7));
	}

	/**
	 * GUI method used to add buttons which perform unary operations
	 */
	private void createUnaryOperations() {
		createButton("1/x", new RCPosition(2, 1),
				(l -> unaryOperation(1 / calcImpl.getValue(), 1 / calcImpl.getValue())));
		createButton("sin", new RCPosition(2, 2),
				(l -> unaryOperation(Math.sin(calcImpl.getValue()), Math.asin(calcImpl.getValue()))));
		createButton("log", new RCPosition(3, 1),
				(l -> unaryOperation(Math.log10(calcImpl.getValue()), Math.pow(10, calcImpl.getValue()))));
		createButton("cos", new RCPosition(3, 2),
				(l -> unaryOperation(Math.cos(calcImpl.getValue()), Math.acos(calcImpl.getValue()))));
		createButton("ctg", new RCPosition(5, 2),
				(l -> unaryOperation(Math.tan(1 / calcImpl.getValue()), 1 / Math.atan(calcImpl.getValue()))));
		createButton("ln", new RCPosition(4, 1),
				(l -> unaryOperation(Math.log(calcImpl.getValue()), Math.exp(calcImpl.getValue()))));
		createButton("tan", new RCPosition(4, 2),
				(l -> unaryOperation(Math.tan(calcImpl.getValue()), Math.atan(calcImpl.getValue()))));
	}

	/**
	 * Method which performs the unary operation for the createUnaryOperations based
	 * on the state of inverse value If an error occurs a JOptionPane will pop out.
	 * 
	 * @param val1
	 *            value of the operation if invese is false
	 * @param val2
	 *            value of the operation if invese is true
	 */
	private void unaryOperation(double val1, double val2) {
		try {
			if (inverse == false) {
				calcImpl.setValue(val1);
			} else {
				calcImpl.setValue(val2);
			}
		} catch (Exception e) {
			messageDialog("Invalid result; value is infinity or NaN");
			calcImpl.clear();
		}
	}

	/**
	 * GUI method used to add buttons which perform other operations useful for this
	 * calculator
	 */
	private void createOtherOperations() {
		createButton("clr", new RCPosition(1, 7), (l -> calcImpl.clear()));
		createButton("res", new RCPosition(2, 7), (l -> calcImpl.clearAll()));
		createButton("push", new RCPosition(3, 7), (l -> {
			myStack.push(calcImpl.getValue());
			calcImpl.clear();
		}));
		createButton("pop", new RCPosition(4, 7), (l -> {
			try {
				calcImpl.setValue(myStack.pop());
			} catch (EmptyStackException e) {
				messageDialog("Stack is empty");
			}
		}));
		createButton("+/-", new RCPosition(5, 4), (l -> calcImpl.swapSign()));
		createButton(".", new RCPosition(5, 5), (l -> calcImpl.insertDecimalPoint()));
	}

	/**
	 * GUI method used to add buttons which perform binary operations <br>
	 * uses methods calculate() and binaryOperation()
	 */
	private void createBinaryOperations() {
		createButton("=", new RCPosition(1, 6), (l -> calculate()));
		createButton("/", new RCPosition(2, 6), (l -> binaryOperation((x, y) -> x / y)));
		createButton("*", new RCPosition(3, 6), (l -> binaryOperation((x, y) -> x * y)));
		createButton("-", new RCPosition(4, 6), (l -> binaryOperation((x, y) -> x - y)));
		createButton("x^n", new RCPosition(5, 1),
				(l -> binaryOperation(inverse == false ? (x, y) -> Math.pow(x, y) : (x, y) -> Math.pow(x, 1.0 / y))));
		createButton("+", new RCPosition(5, 6), (l -> binaryOperation((x, y) -> x + y)));
	}

	/**
	 * Method used to return the end result of operations to the result lable<br>
	 * If an error occurs a JOptionPane will pop out.
	 */
	private void calculate() {
		try {
			if (calcImpl.getPendingBinaryOperation() != null) {
				DoubleBinaryOperator op = calcImpl.getPendingBinaryOperation();
				calcImpl.setActiveOperand(op.applyAsDouble(calcImpl.getActiveOperand(), calcImpl.getValue()));
				calcImpl.setPendingBinaryOperation(null);
				calcImpl.setValue(calcImpl.getActiveOperand());
			}
			calcImpl.clearActiveOperand();
		} catch (Exception e) {
			messageDialog("Value is infinity or NaN");
			calcImpl.clearAll();
		}
	}

	/**
	 * Method which performs the binary operation for the createBinaryOperations
	 * 
	 * @param operation
	 *            DoubleBinaryOperator
	 */
	private void binaryOperation(DoubleBinaryOperator operation) {
		if (calcImpl.getPendingBinaryOperation() != null) {
			DoubleBinaryOperator op = calcImpl.getPendingBinaryOperation();
			calcImpl.setActiveOperand(op.applyAsDouble(calcImpl.getActiveOperand(), calcImpl.getValue()));
			calcImpl.setPendingBinaryOperation(operation);
			calcImpl.clear();
		} else {
			calcImpl.setActiveOperand(calcImpl.getValue());
			calcImpl.setPendingBinaryOperation(operation);
			calcImpl.clear();
		}
	}

	/**
	 * GUI method used to add number buttons
	 */
	private void createNumbers() {
		createButton("7", new RCPosition(2, 3), (l -> calcImpl.insertDigit(7)));
		createButton("8", new RCPosition(2, 4), (l -> calcImpl.insertDigit(8)));
		createButton("9", new RCPosition(2, 5), (l -> calcImpl.insertDigit(9)));
		createButton("4", new RCPosition(3, 3), (l -> calcImpl.insertDigit(4)));
		createButton("5", new RCPosition(3, 4), (l -> calcImpl.insertDigit(5)));
		createButton("6", new RCPosition(3, 5), (l -> calcImpl.insertDigit(6)));
		createButton("1", new RCPosition(4, 3), (l -> calcImpl.insertDigit(1)));
		createButton("2", new RCPosition(4, 4), (l -> calcImpl.insertDigit(2)));
		createButton("3", new RCPosition(4, 5), (e -> calcImpl.insertDigit(3)));
		createButton("0", new RCPosition(5, 3), (e -> calcImpl.insertDigit(0)));
	}

	/**
	 * GUI method used to add result label
	 */
	private void createDisplay() {
		result = new JLabel("0", SwingConstants.RIGHT);
		calcImpl.addCalcValueListener(e -> result.setText(e.toString()));
		result.setBackground(Color.decode("#ffd20c"));
		Font font = new Font("Arial", Font.BOLD, 15);
		result.setFont(font);
		result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		result.setOpaque(true);
		display.add(result, new RCPosition(1, 1));
	}

	/**
	 * Creates JOptionPane with appropriate error message
	 * 
	 * @param errorText
	 *            error message
	 */
	private void messageDialog(String errorText) {
		JOptionPane.showMessageDialog(this, errorText, "Errror", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Method used to creates a button and sets its name,position and adds an
	 * {@link ActionListener}
	 * 
	 * @param name
	 *            Name of the button
	 * @param position
	 *            RCPosition of the button
	 * @param action
	 *            ActionListener
	 */
	private void createButton(String name, RCPosition position, ActionListener action) {
		JButton button = new JButton(name);
		button.addActionListener(action);
		button.setBackground(Color.decode("#72b0cf"));
		display.add(button, position);
	}
}
