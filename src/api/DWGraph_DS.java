package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;



public class DWGraph_DS implements directed_weighted_graph {
	
	private int MC;
	private int numOfEdges;
	private HashMap<Integer, HashMap<Integer, edge_data>> Edges;
	private HashMap<Integer, node_data> Nodes;
	


	public DWGraph_DS() {
		
		MC = 0;
		numOfEdges = 0;
		this.Nodes = new HashMap<Integer, node_data>();
		this.Edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
	}

	/**
	 * returns the node_data by the node_id
	 * @param key - the node_id
	 * @return
	 */
	@Override
	public node_data getNode(int key) {

		return this.Nodes.get(key);

	}

	/**
	 *  returns the data of the edge (src,dest), null if none.
	 * 	this method run in O(1) time
	 * @param src
	 * @param dest
	 * @return
	 */
	@Override
	public edge_data getEdge(int src, int dest) {
		if(Nodes.containsKey(src) && Nodes.containsKey(dest) && Edges.get(src).containsKey(dest)) {
			return Edges.get(src).get(dest);
		}
		return null;
	}

	/**
	 * adds a new node to the graph with the given node_data.
	 * this method run in O(1) time
	 * @param n
	 */
	@Override
	public void addNode(node_data n) {
		if (Nodes.keySet().contains(n.getKey())) {
			System.err.println("Err: key already exists, add fail");
			return;
		}
		if(n.getWeight()<=0)
		{
			System.err.println("The weight must be positive! . The node hadn't been added successfully..");
			return;
		}
		this.Nodes.put(n.getKey(), n);//n used to be casted into (node)
		this.Edges.put(n.getKey(), new HashMap<Integer,edge_data>());
		MC++;

	}

	/**
	 * Connects an edge with weight w between node src to node dest
	 * this method run in O(1) time.
	 * @param src - the source of the edge.
	 * @param dest - the destination of the edge.
	 * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
	 */
	@Override
	public void connect(int src, int dest, double w) {
		if(w<=0)
		{
			System.err.println("The weight must be positive! . connect failed");
			return;
		}
		EdgeData e = new EdgeData(src, dest, w);
		if (!Nodes.containsKey(src) || !Nodes.containsKey(dest)) {
			System.err.println("can't connect");
			return;
		}
		Edges.get(src).put(dest, e);
		numOfEdges++;
		MC++;
	}

	/**
	 * This method returns a pointer (shallow copy) for the collection representing all the nodes in the graph.
	 * 	 this method run in O(1) time.
	 * @return
	 */
	@Override
	public Collection<node_data> getV() {
		return Nodes.values();
	}

	/**
	 * This method returns a pointer (shallow copy) for the collection representing all the edges getting out of
	 * the given node (all the edges starting (source) at the given node).
	 * this method run in O(k) time, k -  collection size.
	 * @param node_id
	 * @return
	 */
	@Override
	public Collection<edge_data> getE(int node_id) {
		// TODO Auto-generated method stub
		return Edges.get(node_id).values();
	}

	/**
	 * Deletes the node (with the given ID) from the graph
	 * and removes all edges which starts or ends at this node.
	 * return the data of the removed node (null if none).
	 * This method  run in O(k), degree-k,  all the edges  removed
	 * @param key
	 * @return
	 */
	@Override
	public node_data removeNode(int key) {
		if (this.Nodes.get(key) == null) {
			return null;
		}

		Set<Integer> edgeKeys = Edges.keySet();
		for(Integer node: edgeKeys) {
			if(Edges.get(node).containsKey(key)) 
			{
				Edges.get(node).remove(key);
				numOfEdges--;
			}
		}

		// remove all edges coming out of key-node.
		numOfEdges -= this.Edges.get(key).values().size();
		this.Edges.remove(key);


		MC++;

		return this.Nodes.remove(key);

	}

	/**
	 *  Deletes the edge from the graph,return the data of the removed edge (null if none).
	 * 	this method run in O(1) time
	 * @param src
	 * @param dest
	 * @return
	 */
	@Override
	public edge_data removeEdge(int src, int dest) {
		if(getEdge(src,dest) == null) {
			return null;
		}
		edge_data e =  this.Edges.get(src).get(dest);
		this.Edges.get(src).remove(dest);
		numOfEdges--;
		MC++;
		return e;
	}

	/**
	 * Returns the number of vertices (nodes) in the graph.
	 * this method run in O(1) time
	 * @return
	 */
	@Override
	public int nodeSize() {
		return this.Nodes.size();
	}

	/**
	 * Returns the number of edges (directional graph).
	 * this method run in O(1) time
	 * @return
	 */
	@Override
	public int edgeSize() {
		return numOfEdges;
	}

	/**
	 * Returns the Mode Count - for testing changes in the graph.
	 * @return
	 */
	@Override
	public int getMC() {
		return MC;
	}




	@Override
	public String toString() {
		String ans = "";
		Set<Integer> s = Edges.keySet();
		Iterator<Integer> iterI = s.iterator();
		for (HashMap<Integer,edge_data> block : Edges.values()) {
			if(!block.isEmpty()) {
				Iterator<edge_data> iter = block.values().iterator();
				ans += iterI.next()+": [";
				for (edge_data e : block.values()) {
					ans += e.getDest()+"; "+e.getSrc()+" -> "+e.getDest()+"("+e.getWeight()+"), ";
				}
			}
			else {
				ans += iterI.next()+": [";
			}
			ans += "]\n";
		}
		return ans;
	}


}
