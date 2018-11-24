package src;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class mancala {
	public static final int SIZE = 12;
	public static void main(String[] args) {
		String in = "./HW5/testMancalaTEMP.txt";//args[0]; //TODO CHANGE
		String out = "./HW5/Results/testmANCALA_solution.txt"; //TODO: change
		
		int numProblems = countProblems(in);
		boolean[][] problems = parseProblems(in, numProblems);
		
		StringBuilder outputText = new StringBuilder();
		for(int i=0; i<numProblems; i++){
			int score = play(problems[i]);
			//outputText.append(numArrows);
			outputText.append('\n');
		}

		writeOutput(out,outputText.toString().trim()); //TODO
		System.out.println("Results: \n"+outputText.toString()); //TODO remove
	
	}


	/**
	 * Parse input text and return boolean arrays of each problem
	 * @param path input text file path
	 * @param numProblems 
	 * @return boolean array per problem 0 = false, 1 = true
	 */
	public static boolean[][] parseProblems(String path, int numProblems){
		boolean[][] result = new boolean[numProblems][SIZE];
		try{
			Scanner sc = new Scanner(new File(path));
			String tmp = sc.nextLine(); //Scanner now at first problem
			for(int i=0; i<numProblems; i++){
				String[] entries = sc.nextLine().split("\\s+");
				int j=0;
				//convert string entries to ints and store
				for(String s : entries){ 
					if(s.equals("1")){
						result[i][j] = true;
					}
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


	public static int countProblems(String path){
		int number=0;
		try{
			Scanner sc = new Scanner(new File(path));
			String count = sc.nextLine();
			number = Integer.parseInt(count);
			sc.close();
		}catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }

		return number;
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
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(result);
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
