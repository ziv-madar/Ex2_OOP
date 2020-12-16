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
	@Override
	public node_data getNode(int key) {

		return this.Nodes.get(key);

	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if(Nodes.containsKey(src) && Nodes.containsKey(dest) && Edges.get(src).containsKey(dest)) {
			return Edges.get(src).get(dest);
		}
		return null;
	}

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

	@Override
	public Collection<node_data> getV() {

		return Nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		// TODO Auto-generated method stub
		return Edges.get(node_id).values();
	}

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

	@Override
	public int nodeSize() {

		return this.Nodes.size();
	}

	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub
		return numOfEdges;
	}

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
	public static void main(String[] args) {

		DWGraph_DS g = new DWGraph_DS();
		node_data n0 = new NodeData(1);
		node_data n1 = new NodeData(2);
		node_data n2 = new NodeData(3);
		node_data n3 = new NodeData(4);
		node_data n4 = new NodeData(5);
		node_data n13 = new NodeData(13);
		node_data n15 = new NodeData(15);
		g.addNode(n0);
		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		g.addNode(n4);
		g.addNode(n13);
		g.addNode(n15);
		g.connect(1, 2, 10);
		g.connect(1, 3, 2);
		g.connect(1, 4, 5);
		g.connect(1, 5, 7);
		g.connect(3, 5, 4);
		g.connect(13, 1, 17);
		g.connect(15, 1, 38.5);
		System.out.println(g.getNode(1));
		System.out.println(g.getNode(6));
		System.out.println(g);
		for(int i = 1; i<=5;i++) {
			System.out.println(i+"="+g.getE(i));
		}
		System.out.println(g);
		System.out.println(g.removeNode(1));
		System.out.println(g);
		System.out.println(g.removeEdge(3, 5));






	}


}
