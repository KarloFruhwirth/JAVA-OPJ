package demo;

import java.util.Scanner;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * @author KarloFrühwirth
 * @version 1.0
 * 
 * Demo class that shows the functions of our stack
 */
public class StackDemo {

	/**
	 * Method called on start of program
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
        String input = sc.nextLine();
        input = input.substring(1, input.lastIndexOf("\""));
        System.out.println(input);
        String[] array = input.split("\\s+");
        ObjectStack stack = new ObjectStack();
        for(int i = 0; i<array.length;i++) {
        	if(isNumeric(array[i])) {
        		Integer num = new Integer(Integer.parseInt(array[i]));
        		stack.push(num);
        	}else {
        		Integer num1 = (Integer) stack.pop();
        		Integer num2 = (Integer) stack.pop();
        		try {
        			Integer rez = calculate(num1,num2,array[i]);
        			stack.push(rez);
				} catch (UnsupportedOperationException e) {
					System.err.println("This operation is not supported");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					System.exit(0);
				}
        	}
        }
        if(stack.size()!=1) {
        	System.err.println("Stack size is not 1!");
        }else {
        	System.out.println("Expression evaluates to "+stack.pop());
        }
        sc.close();
	}
	
	/**
	 * Method performs callculation of a certain function based on the operation string for two Integers
	 * if the operation is unknown method throws UnsupportedOperationException
	 * @param num2
	 * @param num1
	 * @param operation
	 * @return 
	 */
	private static Integer calculate(Integer num2, Integer num1, String operation) {
		if(operation.equals("+")) {
			return (num1+num2);
		}else if(operation.equals("-")) {
			return (num1-num2);
		}else if(operation.equals("/")) {
			if(num2.equals(0)) {
				throw new IllegalArgumentException("Division with 0");
			}
			return (num1/num2);
		}else if(operation.equals("*")) {
			return (num1*num2);
		}else if(operation.equals("%")) {
			return (num1%num2);
		}else {
			throw new UnsupportedOperationException("Operation is not supported....");
		}
	}

	/**
	 * Method checks if a string is a number or not
	 * @param input
	 * @return true if number else false
	 */
	static boolean isNumeric(String input) {
		try {
			int num = Integer.parseInt(input);
		} catch (NumberFormatException  e) {
			return false;
		}
		return true;
	}

}
