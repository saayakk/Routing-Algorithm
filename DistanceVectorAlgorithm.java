//package DistanceVector;

/*
@Author: Subramanian, Anupam, Sarthak
 * Date: 21st November 2014
 * Project: Implementation of Distance Vector Routing Protocol
 */

import java.io.File;
import java.util.Scanner;

public class DistanceVectorAlgorithm {
	
	static int node=1;
	
	static int no_of_vertices;
	static int source = 1;
	static double maxValue = 9999.0;
	
	static double costMatrix[][];
	static double weight[];
	static int iterations =0;
	
	static int previousHop[];
	static int nextHop[];
	
	public static void main(String args[])
	{
		// Reading values from command Line
		int initialNode = Integer.parseInt(args[0]);
		String file = args[1];
		int node1 = Integer.parseInt(args[2]);		
		int node2 = Integer.parseInt(args[3]);
				
		System.out.println("Initial Node is: " + initialNode);
		if(initialNode > 32 || initialNode == 0 || initialNode < 0)
		{
			System.out.println("Wrong Initial Node");
			System.exit(0);
		}
		//reading number of vertices and creating Adjacency matrix from input file
		no_of_vertices = generateCostMatrix(file);
		
		weight = new double[no_of_vertices+1];
		previousHop = new int[no_of_vertices+1];
		nextHop = new int[no_of_vertices+1];
		
		//For Node1 and Node 2
		
		while(node<=2)
		{
			//initialising source node
			if(node==1)
				source = node1;
			else
				source = node2;
			
			//initialising Weight matrix
			initialiseWeightMatrix(maxValue, source);
		
			//Calling BellmanFord Algorithm Function
			bellmanFordAlgorithm(weight);
		
			//Printing the output
			System.out.println("Routing table of Node "+ source);
			printRoutingTable(source);
			System.out.println();
			
			++node;
		} 
		
		System.out.println("Least distance from " + node1 + " to " + node2 + " is " + weight[node1]);
		
		//System.out.println("No of Iterations required: " + iterations);
	}

	public static void printRoutingTable(int source) {
		// TODO Auto-generated method stub
		//System.out.println("Routing Table of Node " + source);
		System.out.println("| ID  |  COST  |  GATEWAY  |   PreviousHop |");
		System.out.println(" -------------------------------------------- ");
		for (int i = 1; i <= no_of_vertices; i++)
		{
			if(i<10)
                System.out.println("|  " + i + "  |   " + weight[i] + "  |     " + nextHop[i] + "     |     "
                						+ previousHop[i] + "         |");
			else
                System.out.println("| " + i + "  |   " + weight[i] + "  |     " + nextHop[i]+ "     |     "
                					+ previousHop[i] + "         |");
		}
		
		/*System.out.println("next hop");
		for(int i=1;i<=no_of_vertices;i++)
		{
			System.out.println(nextHop[i]);
		}*/
	}

	public static void bellmanFordAlgorithm(double[] weight) {
		// TODO Auto-generated method stub
		//System.out.println(source);
		
		//Implementation of the Algorithm
		for (int i = 1; i <= no_of_vertices - 1; i++)
        {	
            for (int j = 1; j <= no_of_vertices; j++)
            {
            	
                for (int k = 1; k <= no_of_vertices; k++)
                {
                	int x = source;
                    if (costMatrix[j][k] != maxValue) //To check if the next node is a neighbor or not
                    {
                        if (weight[k] > weight[j] + costMatrix[j][k])
                        {
                            weight[k] = weight[j] + costMatrix[j][k];     //If Least cost from the Source Node found
                            previousHop[k] = j;
                            x=j;
                            while(costMatrix[source][x] == maxValue)
                            {
                            	x=previousHop[x]; 
                            }
                            nextHop[k] = x;
                            iterations++;                            
                        }
                    }
                }
            }
        }
 
		
		//Logic for Negative Edge cost. Not required here.
        for (int j = 1; j <= no_of_vertices; j++)
        {
            for (int k = 1; k <= no_of_vertices; k++)
            {
                if (costMatrix[j][k] != maxValue)
                {
                    if (weight[k] > weight[j]
                           + costMatrix[j][k])
                        System.out.println("The Graph contains negative egde cycle");
                }
            }
        }
	}

	public static void initialiseWeightMatrix(double maxValue, int source) {
		// TODO Auto-generated method stub
		
		//All values initially set to infinity
		for(int i=1;i<=no_of_vertices;i++)
		{
			weight[i] = maxValue;
			previousHop[i] = 0;
			nextHop[i] = source;
		}
		weight[source] = 0;
		
	}

	public static int generateCostMatrix(String file) {
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
