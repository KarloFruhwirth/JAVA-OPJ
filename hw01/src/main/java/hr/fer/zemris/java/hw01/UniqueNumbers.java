package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * @author KarloFrühwirth
 * @version 1.0
 */
public class UniqueNumbers {

	/**
	 * @author KarloFrühwirth
	 * @version 1.0
	 */
	static class TreeNode {
		int value;
		TreeNode left;
		TreeNode right;

		/**
		 * Constructor
		 * 
		 * @param value
		 */
		public TreeNode(int value) {
			this.value = value;
		}
	}

	/**
	 * Method called on start of the program
	 * 
	 * @param args
	 *            arguments from the command line
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number;
		TreeNode glava = null;

		// for preset values
		// glava = addNode(glava, 12);
		// glava = addNode(glava, 20);
		// glava = addNode(glava, 10);
		// glava = addNode(glava, 11);
		// glava = addNode(glava, 9);
		// glava = addNode(glava, 25);
		// glava = addNode(glava, 22);

		while (true) {
			System.out.print("Unesite broj > ");
			String input = sc.next();
			if (input.equals("kraj")) {
				break;
			}
			try {
				number = Integer.parseInt(input);
				if (!containsValue(glava, number)) {
					glava = addNode(glava, number);
					System.out.println("Dodano.");
				} else {
					System.out.println("Broj vec postoji. Preskačem.");
				}

			} catch (NumberFormatException e) {
				System.out.println("'" + input + "' nije cijeli broj");
			}

		}
		sc.close();
		System.out.print("Ispis od najmanjeg: ");
		printFromSmallest(glava);
		System.out.print("\nIspis od najvećeg: ");
		printFromBiggest(glava);

	}

	/**
	 * Method for adding nodes in tree
	 * 
	 * @param glava
	 *            node
	 * @param value
	 * @return
	 */
	private static TreeNode addNode(TreeNode glava, int value) {
		if (glava == null) {
			return (glava = new TreeNode(value));
		} else {
			if (value == glava.value) {
				return glava;
			}
			if (value <= glava.value) {
				glava.left = addNode(glava.left, value);
			} else {
				glava.right = addNode(glava.right, value);
			}
			return glava;
		}
	}

	/**
	 * Method for printing the content of the tree from min to max
	 * 
	 * @param glava
	 *            node
	 */
	public static void printFromSmallest(TreeNode glava) {
		if (glava != null) {
			printFromSmallest(glava.left);
			System.out.printf("%d ", glava.value);
			printFromSmallest(glava.right);
		}
	}

	/**
	 * Method for printing the content of the tree from max to min
	 * 
	 * @param glava
	 *            node
	 */
	public static void printFromBiggest(TreeNode glava) {
		if (glava != null) {
			printFromBiggest(glava.right);
			System.out.printf("%d ", glava.value);
			printFromBiggest(glava.left);

		}
	}

	/**
	 * Method that checks if the tree contains a certain value
	 * 
	 * @param glava
	 *            node
	 * @param value
	 * @return true if it contains otherwise false
	 */
	public static boolean containsValue(TreeNode glava, int value) {
		if (glava == null) {
			return false;
		} else {
			if (glava.value == value) {
				return true;
			} else {
				if (value < glava.value) {
					return (containsValue(glava.left, value));
				} else {
					return (containsValue(glava.right, value));
				}
			}
		}

	}

	/**
	 * Method for calculating the size of the tree
	 * 
	 * @param glava
	 *            node
	 * @return number of elements in the tree
	 */
	public static int treeSize(TreeNode glava) {
		int i = 0;
		if (glava == null) {
			return i;
		} else {
			return (treeSize(glava.left) + 1 + treeSize(glava.right));
		}
	}

}
