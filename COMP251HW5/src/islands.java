package src;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class islands {
	private static class Land{ //Coordinate of each land patch
		public int x;
		public int y;

		Land(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	private static class Isle {
		public int level;
		public HashSet<Land> landMass; //along a level

		Isle(int lvl){
			this.level = lvl;
			this.landMass = new HashSet<Land>();
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String in = "testIslands.txt";//args[0];
		Chart[] maps = parseInput(in); //Array of problem maps

		String out = "testIslands_solution.txt";
		StringBuilder outputText = new StringBuilder();
		for(Chart m : maps){ //TODO
			m.findIslands();
			outputText.append(m.islands.size());
			outputText.append('\n');

		}
		//writeOutput(out,outputText.toString().trim());
		System.out.println(outputText.toString()); //TODO remove
	}

	public static void writeOutput(String path, String result){

	}

	
	public static Chart[] parseInput(String path) throws RuntimeException{
		Chart[] maps = null; //TODO Modify size

		try{
			Scanner sc = new Scanner(new File(path));
			String tmp = sc.nextLine();
			int numProblems = Integer.parseInt(tmp);
			maps = new Chart[numProblems];
			for(int problem=0; problem<numProblems; problem++){
				Chart c = new Chart();
				//First line is the y and x dimensions respectively
				String[] dimensions = sc.nextLine().split("\\s+");
				int height = Integer.parseInt(dimensions[0]);
				int width = Integer.parseInt(dimensions[1]); 
				//Loop through all lines of data for the current problem
				for(int y=0; y<height; y++){
					String currLine = sc.nextLine();
					Boolean chain = false;
					HashSet<Isle> currLvl = new HashSet<Isle>();
					Isle local=new Isle(y);
					for(int x=0; x<width; x++){
						char currChar = currLine.charAt(x);
						if(currChar == '#'){ //I.e. land
							Land l = new Land(x,y);
							if(!chain){
								chain=true;
								local = new Isle(y);
								
							}
							local.landMass.add(l);
						} else if(chain){
							chain = false;
							currLvl.add(local);
						}
					}
					c.longitude.put(y, currLvl);
				}
			}
			sc.close();	
		} catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }

		return maps;
	}

	private static class Chart { //Map corresponding to each individual problem
		//For each line of the map, a set of all the connected land mass sets
		public static HashMap<Integer, HashSet<Isle>> longitude; 
		public static HashSet<Isle> islands;

		Chart(){

		}

		public static void findIslands(){
			int y = longitude.size();
			for(int i = 1; i<y; i++){
				HashSet<Isle> currLvl = longitude.get(i);
				HashSet<Isle> prevLvl = 
			}
		}

		
	}



}
