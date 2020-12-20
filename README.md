# Ex2_OOP
Project Ex2 - Directed Graphs and the Pokemon Challenge

This project includes two packages: api & gameClient.
-package api ; Responsible for implementing directed graphs and algorithmic operations on it
This package contains the following interfaces and classes:
NodeData implements node_data

-package gameClient; Responsible for the game of Pokemon, graphical representation of the game, and communication between the game server and the client.

## package api
This package contains the following interfaces and classes:
class NodeData implements node_data
class EdgeData implements edge_data
class GoeLocation implements geo_location
class DWGraph_DS implements directed_weighted_graph
class DWGraph_Algo implements dw_graph_algorithms
class DWG_JsonDeserializer implements JsonDeserializer<DWGraph_DS>
interface game_service extends Serializable

### class NodeData contains the following methods:

public int getKey():
Returns the key (id) associated with this node.
	 
public geo_location getLocation():
Returns the location of this node, if none return null.
	
public void setLocation(geo_location p)
Allows changing this node's location.
param p - new new location  (position) of this node.
	
public double getWeight():
Returns the weight associated with this node.
	
public void setWeight(double w):
Allows changing this node's weight.
	 
public String getInfo():
Returns the remark (meta data) associated with this node.
	
public void setInfo(String s):
Allows changing the remark (meta data) associated with this node.
	 
public int getTag():
Temporal data (aka color: e,g, white, gray, black) which can be used be algorithms	 			 
	  
public void setTag(int t):
Allows setting the "tag" value for temporal marking an node - common
practice for marking by algorithms.


### class EdgeData contains the following methods:

public int getSrc():
The id of the source node of this edge.
	 
public int getDest():
The id of the destination node of this edge
	
public double getWeight():
return the weight of this edge (positive value).
	 
public String getInfo():
Returns the remark (meta data) associated with this edge.
	 
public void setInfo(String s):
Allows changing the remark (meta data) associated with this edge.
	
public int getTag():
Temporal data (aka color: e,g, white, gray, black) 
which can be used be algorithms 
	 
public void setTag(int t):
This method allows setting the "tag" value for temporal marking an edge - common
practice for marking by algorithms.
	 


### class DWGraph_DS contains the following methods:

public node_data getNode(int key)
returns the node_data by the node_id,
param key - the node_id
return the node_data by the node_id, null if none.
	
public edge_data getEdge(int src, int dest):
returns the data of the edge (src,dest), null if none.
this method run O(1) time.
	
public void addNode(node_data n):
adds a new node to the graph with the given node_data.
this method run in O(1) time.
	
	
public void connect(int src, int dest, double w):
Connects an edge with weight w between node src to node dest.

param src - the source of the edge.
param dest - the destination of the edge.
param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
this method run in O(1) time.
	
public Collection<node_data> getV():
This method returns a pointer (shallow copy) for the
collection representing all the nodes in the graph. 
this method run in O(1) time.
	
public Collection<edge_data> getE(int node_id):
This method returns a pointer (shallow copy) for the
collection representing all the edges getting out of 
the given node (all the edges starting (source) at the given node). 
this method run in O(k) time, k -  collection size.
	
	
	
public node_data removeNode(int key):
Deletes the node (with the given ID) from the graph
and removes all edges which starts or ends at this node.
return the data of the removed node (null if none). 
This method  run in O(k), degree-k,  all the edges  removed.
	
public edge_data removeEdge(int src, int dest):
Deletes the edge from the graph,return the data of the removed edge (null if none).
this method run in O(1) time.
	 
	 
public int nodeSize():
Returns the number of vertices (nodes) in the graph.
this method run in O(1) time
	
public int edgeSize():
Returns the number of edges (directional graph).
this method run in O(1) time.


public int getMC():
Returns the Mode Count - for testing changes in the graph.



### class DWGraph_Algo contains the following methods:

public void init(directed_weighted_graph g):
Init the graph on which this set of algorithms operates on.
   
public directed_weighted_graph getGraph():
Return the underlying graph of which this class works.

    
public directed_weighted_graph copy():
Compute a deep copy of this weighted graph.
    
  
public boolean isConnected():
Returns true if and only if  there is a valid path from each node to each
other node.
   
   
public double shortestPathDist(int src, int dest):
returns the length of the shortest path between src to dest
if no such path --> returns -1
    
   
public List<node_data> shortestPath(int src, int dest):
returns the the shortest path between src to dest - as an ordered List of nodes:
src--> n1-->n2-->...dest
if no such path --> returns null
     

public boolean save(String file):
Saves this weighted (directed) graph to the given
file name - in JSON format
param file - the file name 
return true - iff the file was successfully saved


public boolean load(String file):
This method load a graph to this graph algorithm.
param file - file name of JSON file
return true - iff the graph was successfully loaded.


## package gameClient
This package contains the following interfaces and classes:
class Ex2 implements Runnable
class Arena
class CL_Agent
class CL_Pokemon     
class MyFrame extends JPanel

### class Ex2 contains the following methods:

public void run():
This method allows the customer to choose a stage in the game between 0-23
The method first places the agents in the strategic position relative to the Pokemon
 so that they are caught in as short a time as possible as Pokemon and then moves them in the best way so that they will perform this task.

private static CL_Pokemon minBetweenPokemon(Collection<CL_Pokemon> arrP, CL_Pokemon pok, dw_graph_algorithms graphAlgo):
This method accepts: Pokemon list, Pokemon and graph.
The method takes the Pokemon and finds in the list of Pokemon the closest Pokemon to it and returns it.


private static void moveAgants(game_service game, directed_weighted_graph gg):
This method accepts: server and graph.
The method moves the agents on the graph
So that in a minimum of time as many Pokemon as possible will be captured


 









 
	



	


