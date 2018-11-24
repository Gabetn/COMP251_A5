package src;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

/**
 * COMP 251: Algorithms & Datastructures Assignment #5 Gabriel Negash -
 * 260679520 Question 2: Mancala Leapfrog NO COLLABORATORS
 * 
 * NOTE: Please count this as the third problem
 */

public class mancala {
	public static final int SIZE = 12;

	public static void main(String[] args) {
		String in = "./testMancala.txt";// args[0];
		String out = "./testMancala_solution.txt";

		int numProblems = countProblems(in);
		boolean[][] problems = parseProblems(in, numProblems);

		StringBuilder outputText = new StringBuilder();
		for (int i = 0; i < numProblems; i++) {
			int score = play(problems[i]);
			outputText.append(score);
			outputText.append('\n');
		}

		writeOutput(out, outputText.toString().trim()); // TODO
	}

	public static int play(boolean[] board) {
		int score = 0;
		boolean moveExists = true;
		int[] bounds = findBounds(board);
		int start = bounds[0];
		int end = bounds[1];

		if (start == end) {
			score = (board[start]) ? 1 : 0;
			return score;
		}

		start = (start - 1 < 0) ? start : start - 1;
		end = (end + 1 >= SIZE) ? end : end + 1;
		int mid = (end - start) / 2;
		while (moveExists) {
			moveExists = false;
			if ((end - start) < 3) {
				continue;
			}
			for (int i = start; i < mid; i++) {
				if (board[i] && board[i + 1]) { // TTx
					if (!board[i + 2]) {
						board[i] = false;
						board[i + 1] = false;
						board[i + 2] = true;
						moveExists = true;
					} else if (!board[i - 1]) {
						board[i] = false;
						board[i + 1] = false;
						board[i - 1] = true;
						moveExists = true;
					}
				}
			}
			for (int j = mid; j < end; j++) {
				if (board[j] && board[j + 1]) { // TTx
					if (!board[j - 1]) {
						board[j] = false;
						board[j + 1] = false;
						board[j - 1] = true;
						moveExists = true;
					} else if (!board[j + 2]) {
						board[j] = false;
						board[j + 1] = false;
						board[j + 2] = true;
						moveExists = true;
					}
				}
			}

		}

		for (boolean b : board) {
			if (b) {
				score++;
			}
		}
		return score;
	}

	/**
	 * HELPER given boolean array find first and last truth
	 * 
	 * @param board boolean array
	 * @return array with values of index of first and last truth
	 */
	private static int[] findBounds(boolean[] board) {
		int[] result = new int[2]; // 0 = start, 1 = end
		int start = 0;
		int end = 0;
		for (int i = 0; i < SIZE; i++) { // find first 1 right -> left
			if (start == 0) {
				start = (board[i]) ? i : 0; // TODO: check
			}
			if (end == 0) { // find first 1 left -> right
				int offset = SIZE - 1 - i;
				end = (board[offset]) ? offset : 0;
			}
		}
		result[0] = start;
		result[1] = end;
		return result;
	}

	/**
	 * Parse input text and return boolean arrays of each problem
	 * 
	 * @param path        input text file path
	 * @param numProblems
	 * @return boolean array per problem 0 = false, 1 = true
	 */
	public static boolean[][] parseProblems(String path, int numProblems) {
		boolean[][] result = new boolean[numProblems][SIZE];
		try {
			Scanner sc = new Scanner(new File(path));
			String tmp = sc.nextLine(); // Scanner now at first problem
			for (int i = 0; i < numProblems; i++) {
				String[] entries = sc.nextLine().split("\\s+");
				int j = 0;
				// convert string entries to ints and store
				for (String s : entries) {
					if (s.equals("1")) {
						result[i][j] = true;
					}
					j++; // TODO: Should I check if j != rez[i].length @ end?
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}

		return result;
	}

	public static int countProblems(String path) {
		int number = 0;
		try {
			Scanner sc = new Scanner(new File(path));
			String count = sc.nextLine();
			number = Integer.parseInt(count);
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}

		return number;
	}

	/**
	 * Write result to specified file
	 * 
	 * @param path   path to output file, to be created or modified
	 * @param result string holding result of algorithm to be written
	 */
	public static void writeOutput(String path, String result) {
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