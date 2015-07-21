//package LinkState;

/*
 * @Author: Subramanian, Anupam, Sarthak
 * Project: Implementation of Link State Routing Algorithm
 * Date: 22st November 2014
 */

import java.io.File;
//import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

public class LinkStateAlgorithm {
	
	static double costMatrix[][];
	static double cost[];
	
	static double max_value = 9999.0;
	
	static int no_of_vertices;
	static int previousHop[];
	static int nextHop[];
	
	static int source;
	
	static ArrayList<Integer> unvisitedNodes = new ArrayList<Integer>();
	
	public static void main(String args[])
	{
		String file;
		
		//Reading values from Command Line
		file = args[0];
		int node1 = Integer.parseInt(args[1]);
		int node2 = Integer.parseInt(args[2]);
		
		//Generating Cost Adjacency Matrix
		no_of_vertices = generateCostAdjacencyMatrix(file);
		
		previousHop = new int[no_of_vertices+1];
		cost = new double[no_of_vertices+1];
		nextHop = new int[no_of_vertices+1];
		
		source = 0;
		int node=1;
		long time1 = System.nanoTime();
		while(node<=2)
		{
			long timein1 = System.nanoTime();
			//initialising source node
			if(node==1)
				source = node1;
			else
				source = node2;
			
			//Initialise Cost Array
			initCost(source);
		
			//Link State Routing Algorithm Code
			linkStateAlgorithm();
			
			//print Output
			printRoutingTable(source);
			System.out.println();
			
			++node;
			long timein2 = System.nanoTime();
			//System.out.println("Time Required for Source Node: " + source + ": " + (timein2-timein1));
		}
		
		System.out.println("Least distance from " + node1 + " to " + node2 + " is " + cost[node1]);
		
		long time2 = System.nanoTime();
		System.out.println("Total Time Required for program: " + (time2-time1));
	}
	
	public static void printRoutingTable(int source) {
		// TODO Auto-generated method stub
		System.out.println("Routing Table of Node " + source);
		System.out.println("| ID  |  COST  |  GATEWAY  |   PreviousHop |");
		System.out.println(" -------------------------------------------- ");
		for (int i = 1; i <= no_of_vertices; i++)
		{
			if(i<10)
                System.out.println("|  " + i + "  |   " + cost[i] + "  |     " + nextHop[i] + "     |     "
                						+ previousHop[i] + "         |");
			else
                System.out.println("| " + i + "  |   " + cost[i] + "  |     " + nextHop[i]+ "     |     "
                					+ previousHop[i] + "         |");

        }
		System.out.println(" -------------------------------------------- ");
		
	}

	public static void linkStateAlgorithm() 
	{
		// TODO Auto-generated method stub
		
		try{
		while(!(unvisitedNodes.isEmpty()))
		{
			int x = source;
			double min=1000;
			int u = 0;
			
			//Finding the node with min cost. Initially it is the source node. Gradually remaining nodes
			for(int i=1;i<=unvisitedNodes.size();i++)
			{
				if(cost[unvisitedNodes.get(i-1)]<min)
				{
					min = cost[unvisitedNodes.get(i-1)];
					u = unvisitedNodes.get(i-1);
				}
			}
			
			//Removing u from the list
			for(int i=0;i<unvisitedNodes.size();i++)
			{
				if(unvisitedNodes.get(i)==u)
				{
					unvisitedNodes.remove(i);
				}
			}
			
			//Finding cost to neighbor of u
			for(int j=1;j<=no_of_vertices;j++)
			{
				
				if(costMatrix[u][j] != max_value)
				{
					if(cost[j] > cost[u] + costMatrix[u][j])
					{
						cost[j] = cost[u] + costMatrix[u][j];
						previousHop[j] = u;
						x=u;
                        while(costMatrix[source][x] == max_value)
                        {x=previousHop[x]; }
                        nextHop[j] = x;
						//if(costMatrix[source][u] != max_value)
							//{nextHop[j] = u;}
					}
				}
			}
			
		}
		
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		
	}

	public static void initCost(int source) {
		// TODO Auto-generated method stub
		
		for(int i=1;i<=no_of_vertices;i++)
		{
			cost[i] = max_value;
			previousHop[i] = 0;
			unvisitedNodes.add(i-1, i);;
		}
		cost[source] = 0;
	}

	public static int generateCostAdjacencyMatrix(String file) {
		// TODO Auto-generated method stub
		
		int nodes = 0;
		try{
			
			  Scanner sc=new Scanner(new File(file));
			  
			  String str = sc.nextLine();
			  nodes = Integer.parseInt(str);
			  //System.out.println(v);
			  costMatrix = new double[nodes+1][nodes+1];
			  
			  //Generating Cost Adjacency Matrix
			  for(int i=1;i<=nodes;i++)
			  {
				  for(int j=1;j<=nodes;j++)
				  {
					  costMatrix[i][j] = ((i==j)?0.0:9999.0);
				  }
			  }			  
			  
			  while(sc.hasNext())
	          {  
	        	  String[] s = sc.nextLine().trim().split(" ");  //Splitting source destination and weight from textfile
 	        	  int x = Integer.parseInt(s[0]);
	        	  int y = Integer.parseInt(s[1]);
	        	  costMatrix[x][y] = Double.parseDouble(s[2]);	
	        	  costMatrix[y][x] = costMatrix[x][y];
	          } 
			  
			  //Printing Cost Adjacency Matrix
			  /*for(int i=1;i<=nodes;i++)
			  {
				  for(int j=1;j<=nodes;j++)
				  {
					  System.out.print(costMatrix[i][j] + " ");
				  }
				  System.out.println("");
			  }
			
			  System.out.println("Success");*/
			  sc.close();
			    
			}
		
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		
		return nodes;
	}

}
