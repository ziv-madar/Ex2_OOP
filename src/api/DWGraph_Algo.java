package api;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class DWGraph_Algo implements dw_graph_algorithms {

	private directed_weighted_graph dwg;


	/**
	 * Init the graph on which this set of algorithms operates on.
	 * @param g
	 */
	@Override
	public void init(directed_weighted_graph g) {
		this.dwg = g;
	}

	/**
	 * Return the underlying graph of which this class works.
	 * @return
	 */
	@Override
	public directed_weighted_graph getGraph() {
		return dwg;
	}

	/**
	 * Compute a deep copy of this weighted graph.
	 * @return
	 */
	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph copy = new DWGraph_DS();
		Collection<node_data> vertex = dwg.getV();
		for (node_data v : vertex) {
			NodeData ver = new NodeData(v.getKey(), v.getLocation(), v.getWeight());
			copy.addNode(ver);// Add node
			copy.getNode(v.getKey()).setInfo(v.getInfo());// Set info to node
			copy.getNode(v.getKey()).setTag(v.getTag());// Set tag to node
		}

		for (node_data n : vertex) {
			Collection<edge_data> edges = dwg.getE(n.getKey());
			if (edges != null) {
				for (edge_data e : edges) {// Add all edges (0 or more) by connecting key,dest and weight
					copy.connect(e.getSrc(), e.getDest(), e.getWeight());
				}
			}
		}
		return copy;
	}

	/**
	 *
	 *Returns true if and only if  there is a valid path from each node to each other node.
	 * @return
	 */
	@Override
	public boolean isConnected() {
		if(dwg.getV().size()==0) return true;

		Iterator<node_data> temp = dwg.getV().iterator();
		while(temp.hasNext()) {
			for(node_data w : dwg.getV()) {
				w.setTag(0);
			}
			node_data n = temp.next();
			Queue<node_data> q = new LinkedBlockingQueue<node_data>();
			n.setTag(1);
			q.add(n);
			while(!q.isEmpty()) {
				node_data v = q.poll();
				Collection<edge_data> arrE = dwg.getE(v.getKey());
				for(edge_data e : arrE) {
					int keyU = e.getDest();
					node_data u = dwg.getNode(keyU);
					if(u.getTag() == 0) {
						u.setTag(1);
						q.add(u);
					}
				}
				v.setTag(2);
			}
			Collection<node_data> col = dwg.getV();
			for(node_data n1 : col) {
				if(n1.getTag() != 2) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * returns the length of the shortest path between src to dest
	 * if no such path --> returns -1
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		for(node_data vertex : dwg.getV()) {
			vertex.setInfo(""+Double.MAX_VALUE);
		}
		node_data start = dwg.getNode(src);
		start.setInfo("0.0");
		Queue<node_data> q = new LinkedBlockingQueue<node_data>();
		q.add(start);
		while(!q.isEmpty()) {
			node_data v = q.poll();
			Collection<edge_data> edgesV = dwg.getE(v.getKey());
			for(edge_data edgeV : edgesV) {
				double newSumPath = Double.parseDouble(v.getInfo()) +edgeV.getWeight();
				int keyU = edgeV.getDest();
				node_data u = dwg.getNode(keyU);
				if(newSumPath < Double.parseDouble(u.getInfo())) {
					u.setInfo(""+newSumPath);
					q.remove(u);
					q.add(u);
				}
			}
		}

		return Double.parseDouble(dwg.getNode(dest).getInfo());
	}

	/**
	 * returns the the shortest path between src to dest - as an ordered List of nodes:
	 * src--> n1-->n2-->...dest
	 * if no such path --> returns null
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		for(node_data vertex : dwg.getV()) {
			vertex.setInfo(""+Double.MAX_VALUE);
		}
		int[] prev = new int[dwg.nodeSize()];
		node_data start = dwg.getNode(src);
		start.setInfo("0.0");
		Queue<node_data> q = new LinkedBlockingQueue<node_data>();
		q.add(start);
		prev[src%dwg.nodeSize()] = -1;
		while(!q.isEmpty()) {
			node_data v = q.poll();
			Collection<edge_data> edgesV = dwg.getE(v.getKey());
			for(edge_data edgeV : edgesV) {
				double newSumPath = Double.parseDouble(v.getInfo()) +edgeV.getWeight();
				int keyU = edgeV.getDest();
				node_data u = dwg.getNode(keyU);
				if(newSumPath < Double.parseDouble(u.getInfo())) {
					u.setInfo(""+newSumPath);
					q.remove(u);
					q.add(u);
					prev[u.getKey()%dwg.nodeSize()] = v.getKey();
				}
			}
		}
		List<node_data> ans = new ArrayList<node_data>();
		int run = dest;
		while(run != src) {
			ans.add(0,dwg.getNode(run));
			run = prev[run%dwg.nodeSize()];
		}
		ans.add(0, dwg.getNode(src));

		return ans;
	}

	/**
	 * Saves this weighted (directed) graph to the given.
	 * @param file - the file name (may include a relative path).
	 * @return
	 */
	@Override
	public boolean save(String file) {
	
		Gson gson = new GsonBuilder().create();
		JsonArray arrEdges = new JsonArray();
		JsonArray arrNodes = new JsonArray();
		Collection<node_data> arrV = this.dwg.getV();
		for(node_data vertex : arrV) {
			JsonObject nodesObj = new JsonObject();
			geo_location gl = vertex.getLocation();
			nodesObj.addProperty("pos",String.valueOf(gl.x())+","+String.valueOf(gl.y())+","+String.valueOf(gl.z()));
			nodesObj.addProperty("id",vertex.getKey());
			arrNodes.add(nodesObj);
			Collection<edge_data> arrE = this.dwg.getE(vertex.getKey());
			for(edge_data edge : arrE) {
				JsonObject edgesObj = new JsonObject();
				edgesObj.addProperty("src",edge.getSrc());
				edgesObj.addProperty("w",edge.getWeight());
				edgesObj.addProperty("dest",edge.getDest());
				arrEdges.add(edgesObj);
			}
		}
		JsonObject graphObj = new JsonObject();
		graphObj.add("Edges",arrEdges);
		graphObj.add("Nodes",arrNodes);
		String ans = gson.toJson(graphObj);

		try{
			PrintWriter pw = new PrintWriter(new File(file));
			pw.write(ans);
			pw.close();
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * This method load a graph to this graph algorithm.
	 * @param file - file name of JSON file
	 * @return
	 */
	@Override
	public boolean load(String file) {
		try {
            GsonBuilder builder=new GsonBuilder();
            builder.registerTypeAdapter(DWGraph_DS.class,new DWG_JsonDeserializer());
            Gson gson=builder.create();

            FileReader reader=new FileReader(file);
            directed_weighted_graph dwg=gson.fromJson(reader,DWGraph_DS.class);
            this.init(dwg);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }







}
