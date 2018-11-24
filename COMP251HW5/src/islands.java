package src;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

/**
 * COMP 251: Algorithms & Datastructures 
 * Assignment #5 
 * Gabriel Negash - 260679520 
 * Question 3: Discovering Islands in the ocean 
 * NO COLLABORATORS
 */

public class islands {
	private static class Graph { // Converts each problem into a graph
		// key = x*y index in table and value = node #, if v=-1 not in graph
		public HashMap<Integer, Integer> nodes;

		public LinkedList<Integer> adj[];
		public int numV; // # vertices

		Graph(int v) {
			numV = v;
			adj = new LinkedList[numV];
			for (int i = 0; i < v; i++) {
				adj[i] = new LinkedList();
			}
			this.nodes = new HashMap<Integer, Integer>();
		}

		public void addEdge(int u, int v) { // adds undirected edge
			adj[u].add(v);
			adj[v].add(u);
		}

		/**
		 * #connected components = # dfs calls dfs marks all reachable nodes in a
		 * component as visited Thus only need to call dfs 1 time per connected
		 * component
		 */
		public int numConnectedComponents() {
			int result = 0;
			boolean visited[] = new boolean[numV]; // init false

			for (int i = 0; i < numV; i++) {
				if (!visited[i]) {
					result++;
					dfs(i, visited);
				}
			}

			return result;
		}

		private void dfs(int v, boolean[] visited) {
			visited[v] = true;
			for (Integer i : adj[v]) { // iterate through adjacency list of v
				if (!visited[i]) {
					dfs(i, visited);
				}
			}
		}

		/*
		 * Maps positive entries in boolean map to nodes in graph, preserving location
		 * in boolean matrix as well
		 */
		private void populateVertices(Boolean[][] t) {
			int vert = 0;
			int index = 0;
			for (int y = 0; y < t.length; y++) {
				for (int x = 0; x < t[0].length; x++) {
					if (t[y][x] == true) {
						nodes.put(index, vert);
						vert++;
					} else {
						nodes.put(index, -1);
					}
					index++;
				}
			}
		}
	} // END GRAPH CLASS

	public static final char LAND = '-';

	public static void main(String[] args) {
		String in = "./testIslands.txt";// args[0];
		// Integer = problem number, Boolean[][] = parsed map
		HashMap<Integer, Boolean[][]> tables = parseInput(in);

		int[] numTags = landMassPerProblem(in);
		Graph[] maps = generateMaps(tables, numTags); // Array of problem maps

		String out = "./testIslands_solution.txt";
		StringBuilder outputText = new StringBuilder();
		for (Graph m : maps) {
			int numIslands = m.numConnectedComponents();
			outputText.append(numIslands);
			outputText.append('\n');
		}
		writeOutput(out, outputText.toString().trim());
	}

	/**
	 * Write result to specified file
	 * 
	 * @param path   output file, to be created or modified
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

	/**
	 * @param path to text file
	 * @return number of LAND symbols in text file
	 */
	public static int[] landMassPerProblem(String path) {
		int[] result = null;
		try {
			Scanner sc = new Scanner(new File(path));
			String tmp = sc.nextLine();
			int numProblems = Integer.parseInt(tmp);
			result = new int[numProblems];

			for (int i = 0; i < numProblems; i++) {
				String[] dimensions = sc.nextLine().split("\\s+");

				int height = Integer.parseInt(dimensions[0]); // num lines of problem
				int numLAND = 0;
				for (int j = 0; j < height; j++) {
					tmp = sc.nextLine().trim();
					// calculate number of LAND symbols per line of problem
					numLAND += tmp.length() - tmp.replace(String.valueOf(LAND), "").length();
				}
				result[i] = numLAND;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}

		return result;
	}

	/**
	 * 
	 * @param tables      HashMap mapping problem number to converted boolean matrix
	 *                    Representing land locations
	 * @param numVertices array holding the amount of land masses per problem
	 * @return array holding graph objects of each problem.
	 */
	public static Graph[] generateMaps(HashMap<Integer, Boolean[][]> tables, int[] numVertices) {
		Graph[] result = new Graph[tables.size()];

		for (int prob = 0; prob < tables.size(); prob++) {
			Boolean[][] t = tables.get(prob);
			Graph g = new Graph(numVertices[prob]);
			g.populateVertices(t);

			int currVertex = 0; // = vertex in graph (vertices = land)
			int index = 0; // index = number of elements deep in map
			for (int y = 0; y < t.length; y++) {
				for (int x = 0; x < t[0].length; x++) {
					if (t[y][x] == true) {
						// 1. Check right
						if (x < t[0].length - 1 && t[y][x + 1] == true) {
							int v = currVertex + 1;// g.nodes.get(index+1);
							g.addEdge(currVertex, v); // adds undirected edge
						}
						// 2. Check down
						if (y < t.length - 1 && t[y + 1][x] == true) {
							int v = g.nodes.get(index + t[0].length);
							g.addEdge(currVertex, v); // adds undirected edge
						}
						// 3. Increment vertex count
						currVertex++;
					}
					index++;
				}
			}
			result[prob] = g;
		}

		return result;
	}

	/**
	 * 
	 * @param path to input text file
	 * @return HashMap: Integer = problem number, Boolean[][] = parsed map The map
	 *         converts chars matching LAND in text to True in map, else false
	 * @throws RuntimeException if file does not exist
	 */
	public static HashMap<Integer, Boolean[][]> parseInput(String path) throws RuntimeException {
		HashMap<Integer, Boolean[][]> resultMap = new HashMap<>();
		Boolean[][] map;

		try {
			Scanner sc = new Scanner(new File(path));
			String tmp = sc.nextLine();
			int numProblems = Integer.parseInt(tmp);

			for (int problem = 0; problem < numProblems; problem++) {
				// First line is the y and x dimensions respectively
				String[] dimensions = sc.nextLine().split("\\s+");
				int height = Integer.parseInt(dimensions[0]);
				int width = Integer.parseInt(dimensions[1]);
				map = new Boolean[height][width];

				// Loop through all lines of data for the current problem
				for (int y = 0; y < height; y++) {
					String currLine = sc.nextLine();

					for (int x = 0; x < width; x++) {
						char currChar = currLine.charAt(x);
						if (currChar == LAND) { // I.e. land
							map[y][x] = true;
						} else {
							map[y][x] = false;
						}
					}
				}
				resultMap.put(problem, map);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}

		return resultMap;
	}

}
