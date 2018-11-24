package src;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class islands {
	private static class Graph{ //Graph //Coordinate of each land patch
		 //key = x*y index in table and value = node #, if v=-1 not in graph
		public HashMap<Integer, Integer> nodes;

		public LinkedList<Integer> adj[];
		public int numV; //# vertices
		
		public int numIslands;
		
		Graph(int v){
			numV = v;
			adj = new LinkedList[numV];
			for (int i=0; i<v; i++){ 
				adj[i] = new LinkedList();
			} 
			this.nodes = new HashMap<Integer, Integer>();
			this.numIslands =0;
		}

		public void addEdge(int u, int v){ //adds undirected edge
			adj[u].add(v);
			adj[v].add(u);
		}

		public int numConnectedComponents(){
			int result = 0;

			//NOTE: might need to refactor into field
			boolean visited[] = new boolean[numV]; //init false
			for(int i=0; i<numV; i++){
				if(!visited[i]){
					result++;
					dfs(i, visited); 
				}
			}
			

			return result;
		}

		private void dfs(int v, boolean[] visited){
			visited[v] = true;
			for(Integer i : adj[v]){ //iterate through adjacency list of v
				if(!visited[i]){
					dfs(i,visited);
				}
			}
		}

		private void populateVertices(Boolean[][] t){
			int vert=0;
			int index=0;
			for(int y=0; y<t.length; y++){
				for(int x=0; x<t[0].length; x++){
					if(t[y][x]==true){
						nodes.put(index,vert);
						vert++;
					}else{
						nodes.put(index,-1);
					}
					index++;
				}
			}
		}


	}

	public static void main(String[] args) {
		String in = "./HW5/testIslands.txt";//args[0];
		HashMap<Integer, Boolean[][]> tables = parseInput(in);
		int[] numTags = numTagsPerProblem(in);
		Graph[] maps = generateMaps(tables,numTags); //Array of problem maps
		
		String out = "./HW5/Results/testIslands_solution.txt"; //TODO Change
		StringBuilder outputText = new StringBuilder();
		for(Graph m : maps){ //TODO
			int numIslands = m.numConnectedComponents();
			outputText.append(numIslands);
			outputText.append('\n');
		}
		writeOutput(out,outputText.toString().trim());
		System.out.println("Results: \n"+outputText.toString()); //TODO remove
		
	}

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

	public static int[] numTagsPerProblem(String path){
		int[] result=null;
		try{
			Scanner sc = new Scanner(new File(path));
			String tmp = sc.nextLine();
			int numProblems = Integer.parseInt(tmp);
			result = new int[numProblems];
			for(int i=0; i<numProblems; i++){
				String[] dimensions = sc.nextLine().split("\\s+");
				int height = Integer.parseInt(dimensions[0]);
				
				int numHashTag=0; 
				for(int j=0; j<height; j++){
					tmp = sc.nextLine().trim();
					//calculate number of hashtags per line of problem
					numHashTag += tmp.length()-tmp.replace("-","").length();
				}
				result[i] = numHashTag;
			}
			sc.close();
		}catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }

		return result;
	}
	
	//ToDO
	public static Graph[] generateMaps(HashMap<Integer,Boolean[][]> tables,int[] numVertices){
		Graph[] result = new Graph[tables.size()];
		
		for(int prob=0; prob<tables.size(); prob++){
			Boolean[][] t = tables.get(prob);
			Graph g = new Graph(numVertices[prob]); //NOTE: might be off
			g.populateVertices(t);
			int currVertex=0;
			int index=0;
			for(int y=0; y<t.length; y++){
				for(int x=0; x<t[0].length; x++){
					if(t[y][x]==true){
						//1. Check right
						if(x<t[0].length-1 && t[y][x+1]==true){
							int v = currVertex+1;//g.nodes.get(index+1);
							g.addEdge(currVertex, v);
							g.addEdge(v,currVertex); //Undirected
						}
						//2. Check down
						if(y<t.length-1 && t[y+1][x]==true){
							int v = g.nodes.get(index+t[0].length);
							g.addEdge(currVertex, v);
							g.addEdge(v,currVertex); //Undirected
						}
						//3. Increment vertex count
						currVertex++;
					}
					index++;
				}
			}
			result[prob] = g;
		}



		return result;
	}



	public static HashMap<Integer,Boolean[][]> parseInput(String path) throws RuntimeException{
		HashMap<Integer, Boolean[][]> resultMap = new HashMap<>();
		Boolean[][] map; //TODO Modify size
		
		try{
			Scanner sc = new Scanner(new File(path));
			String tmp = sc.nextLine();
			int numProblems = Integer.parseInt(tmp);

			for(int problem=0; problem<numProblems; problem++){
				//First line is the y and x dimensions respectively
				String[] dimensions = sc.nextLine().split("\\s+");
				int height = Integer.parseInt(dimensions[0]);
				int width = Integer.parseInt(dimensions[1]); 
				map = new Boolean[height][width];
				
				//Loop through all lines of data for the current problem
				for(int y=0; y<height; y++){
					String currLine = sc.nextLine();

					for(int x=0; x<width; x++){
						char currChar = currLine.charAt(x);
						if(currChar == '-'){ //I.e. land
							map[y][x] = true;	
						} else{
							map[y][x] = false;
						}
					}
				}
				resultMap.put(problem,map);
			}
			sc.close();	
		} catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }

		return resultMap;
	}

}
