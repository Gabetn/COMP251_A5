package src;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class balloon {

	public static void main(String[] args) {
		String in = "./HW5/testIslands.txt";//args[0];
		String out = "./HW5/Results/testIslands_solution.txt";
		
		int[] sizes = problemInfo(in);
		int numProblems = sizes.length;

		int[][] problems = parseProblems(in, sizes);
		StringBuilder outputText = new StringBuilder();
		for(int i=0; i<numProblems; i++){
			int numArrows = balloonPopper(problems[i]);
			outputText.append(numArrows);
			outputText.append('\n');
		}

		writeOutput(out,outputText.toString().trim());
		System.out.println("Results: \n"+outputText.toString()); //TODO remove
	}

	
	/**
	 * Main algorithm method
	 * @param specs 1d array holding the integer heights of each balloon in this problem
	 * @return String of the number of arrows required to pop all balloons
	 */
	public static int balloonPopper(int[] specs){ //TODO
		int result = specs.length; // i.e. num balloons in worst case
		
		return result;
	}



	/**
	 * 	Helper Function for parsing information about problems from input
	 * @param path path to input file
	 * @return int[] info, each index holds the length of the corresponding problem
	 */
	public static int[] problemInfo(String path){
		int[] info=null;
		try{
			Scanner sc = new Scanner(new File(path));
			String tmp = sc.nextLine();
			int numProblems = Integer.parseInt(tmp);
			info = new int[numProblems];
			String[] sizes = sc.nextLine().split("\\s+");
			for(int i=0; i<numProblems; i++){
				info[i] = Integer.parseInt(sizes[i]);
			}
			sc.close();
		}catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }

		return info;
	}

	/**
	 * 	Parse input text and return int arrays of each problem
	 * @param path input text file path
	 * @param sizes array containing the sizes of each problem
	 * @return int[i][j] arr, each arr[i] holds int array corresponding to 
	 * 					heights of balloons for the respective problem i 
	 */
	public static int[][] parseProblems(String path, int[] sizes){
		int numProblems = sizes.length;
		int[][] result = new int[numProblems][];
		for(int i=0; i<numProblems; i++){
			result[i] = new int[sizes[i]];
		}
		try{
			Scanner sc = new Scanner(new File(path));
			String tmp = sc.nextLine();
			tmp = sc.nextLine();//scanner now before first problem
			for(int i=0; i<numProblems; i++){
				String[] entries = sc.nextLine().split("\\s+");
				int j=0;
				//convert string entries to ints and store
				for(String s : entries){ 
					result[i][j] = Integer.parseInt(s);
					j++; //TODO: Should I check if j != rez[i].length @ end?
				}
			}
			sc.close();
		}catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }

		return result;
	}

	/**
	 * Write result to specified file
	 * @param path path to output file, to be created or modified
	 * @param result string holding result of algorithm to be written
	 */
	public static void writeOutput(String path, String result){
		BufferedReader br = null;
		File file = new File(path);

		// if file doesnt exists, then create it
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(result + "\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
