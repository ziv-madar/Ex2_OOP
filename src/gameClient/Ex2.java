package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Ex2 implements Runnable {
	private static MyFrame _win;
	private static Arena _ar;
	public static game_service gameServ;
	public static dw_graph_algorithms ga;
	public static int k = 0;
	public static final double eps = 0.000000001;
	public static ArrayList<ArrayList<CL_Pokemon>> groups;
//	public static ArrayList<Boolean> isAgentMovingToPokemon;

	public static void main(String[] a) {
		Thread client = new Thread(new Ex2());
		client.start();
	}

	@Override
	public void run() {
		if(k==0) {
			int scenario_num = 0;
			String level = JOptionPane.showInputDialog("To load a level, please enter the level number [0-23]:");
			try {
				scenario_num = Integer.parseInt(level);
			} catch (NumberFormatException ee) {
				JOptionPane.showMessageDialog(null, "Only numbers are allowed! Level 0 will be loaded.");
				scenario_num = 0;
			}
			if (scenario_num < 0 || scenario_num > 23) {
				JOptionPane.showMessageDialog(null, "Invalid number entered, level 0 will be loaded.");
				scenario_num = 0;
			}
			System.out.println("Now starting game number: " + scenario_num);
			gameServ = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
			//	int id = 999;
			//	game.login(id);


//		String g = gameServ.getGraph();
//		String pks = gameServ.getPokemons();

		}

		try {
			if(k==0) {
				PrintWriter pw = new PrintWriter(new File("./level.txt"));
				pw.write(gameServ.getGraph().toString());
				pw.close();
				ga = new DWGraph_Algo();
//			directed_weighted_graph g = new DWGraph_DS();
//			ga.init(g);
				ga.load("./level.txt");
				System.out.println(ga.getGraph());
			}


			directed_weighted_graph gg = ga.getGraph();
			if(k==0) {
				init(gameServ);
			}
			String pStr = gameServ.getPokemons();
			//todo: remove next line
			System.out.println("p: " + pStr);
			ArrayList<CL_Pokemon> arrP = new ArrayList<CL_Pokemon>();
			int i = 0;
			StringTokenizer str = new StringTokenizer(pStr, "{},:[]\"");
			//todo: delete next line
			System.out.println("sss");
			while (str.hasMoreTokens()) {
				if (i == 0) {
					str.nextToken();
					i++;
				} else {
					str.nextToken();
					str.nextToken();
					double value = Double.parseDouble(str.nextToken());
					//System.out.println("v="+value);
					str.nextToken();
					int type = Integer.parseInt(str.nextToken());
					str.nextToken();
					double x = Double.parseDouble(str.nextToken());
					double y = Double.parseDouble(str.nextToken());
					double z = Double.parseDouble(str.nextToken());
					Point3D pos = new Point3D(x, y, z);
					boolean flag = true;
					Collection<node_data> vS = gg.getV();
					for (node_data v : vS) {
						//System.out.println((v+","));
						if (flag) {
							Collection<edge_data> eS = gg.getE(v.getKey());
							for (edge_data e : eS) {
								if (flag) {
									//System.out.println(e);
									node_data nSrc = gg.getNode(e.getSrc());
									node_data nDest = gg.getNode(e.getDest());
									geo_location pSrc = nSrc.getLocation();
									geo_location pDest = nDest.getLocation();
									double tX = (pos.x() - pSrc.x()) * (pDest.y() - pSrc.y());
									double tY = (pos.y() - pSrc.y()) * (pDest.x() - pSrc.x());
									//double tZ = (pos.z()-pSrc.z())/(pDest.z()-pSrc.z());

									if (Math.abs(tX - tY) < eps) {

										if (type == -1) {
											int min = Math.min(nSrc.getKey(), nDest.getKey());
											int max = Math.max(nSrc.getKey(), nDest.getKey());
											CL_Pokemon p = new CL_Pokemon(pos, type, value, 0, new EdgeData(max, min, e.getWeight()));
											arrP.add(p);
											flag = false;

										} else {
											int min = Math.min(nSrc.getKey(), nDest.getKey());
											int max = Math.max(nSrc.getKey(), nDest.getKey());
											CL_Pokemon p = new CL_Pokemon(pos, type, value, 0, new EdgeData(min, max, e.getWeight()));
											arrP.add(p);
											flag = false;

										}
									}
								}
							}
						}
					}
				}
			}


			Iterator<CL_Pokemon> iter = arrP.iterator();
			CL_Pokemon run = iter.next();
			//todo: use JSONObject like in Arena.java, line 83.
			StringTokenizer temp = new StringTokenizer(gameServ.toString(), "{}[],:\"");

			for (int j = 1; j < 19; j++) {
				temp.nextToken();
			}
			int numAgents = Integer.parseInt(temp.nextToken());
//			isAgentMovingToPokemon = new ArrayList<>(numAgents);
			ArrayList<ArrayList<CL_Pokemon>> pS = new ArrayList<ArrayList<CL_Pokemon>>();
			for (int j = 0; j < numAgents; j++) {
				pS.add(new ArrayList<CL_Pokemon>());
			}
			int group = arrP.size() / numAgents;

			System.out.println("Pokemons in each group: " + group);
			System.out.println("Number of pokemon groups to catch: " + pS.size());
			for (int j = 0; j < numAgents; j++) {
				ArrayList<CL_Pokemon> agentI = pS.get(j);
				agentI.add(run);
				for (int k = 0; k < group - 1; k++) {
					arrP.remove(run);
					run = minBetweenPokemon(arrP, run, ga);
					agentI.add(run);
				}
				arrP.remove(run);
				run = minBetweenPokemon(arrP, run, ga);
			}

			if(k==0) {
				for (int j = 0; j < numAgents; j++) {
					gameServ.addAgent(pS.get(j).get(0).get_edge().getSrc());

					//todo: remove this
					System.out.println("agents: " + gameServ.getAgents());
//					System.out.println(pS.get(j).get(0).get_edge().getSrc());
				}
			}
			System.out.println("arr: " + pS);
			groups = pS;
			System.out.println();

			//addAgent should go here
			if(k==0) {
				gameServ.startGame();
				for (int j = 0; j < numAgents; j++) {
					gameServ.chooseNextEdge(j, pS.get(j).get(0).get_edge().getDest());
				}

//				_win.setTitle("Ex2 - OOP: Pokemon Game by ZIV MADAR");
				k++;
			}
			int ind = 0;
			long dt = 300;


			while (gameServ.isRunning()) {
				moveAgants(gameServ, gg);
				try {
					if (ind % 1 == 0) {
//						_win.repaint();
						_win.paintImmediately(0,0,_win.getWidth(),_win.getHeight());
					}
					Thread.sleep(dt);
					ind++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String res = gameServ.toString();

			System.out.println(res);
			System.exit(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static CL_Pokemon minBetweenPokemon(Collection<CL_Pokemon> arrP, CL_Pokemon pok, dw_graph_algorithms graphAlgo) {
		double min = Double.MAX_VALUE;
		CL_Pokemon ans = null;
		for (CL_Pokemon p : arrP) {
			double dis = graphAlgo.shortestPathDist(pok.get_edge().getDest(), p.get_edge().getSrc()) + p.get_edge().getWeight();
			if (dis < min) {
				min = dis;
				ans = p;
			}
		}
		return ans;
	}

	/**
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 *
	 * @param game
	 * @param gg
	 * @param
	 */
	private static void moveAgants(game_service game, directed_weighted_graph gg) {

		String lg = game.move();
//		System.out.println("lg: " + lg);
//        System.out.println("pokemons for agent 2: " + groups.get(2));
		List<String> info = Collections.singletonList("Time Left: " + game.timeToEnd() +"ms. ");
		_ar.set_info(info);
		List<CL_Agent> log = null;
		log = Arena.getAgents(lg, gg);

//			System.out.println("CL_agents: " + log);
		_ar.setAgents(log);
		String fs = game.getPokemons();
//		System.out.println("fs: " + fs);
//		lg = lg.substring(lg.indexOf("Pokemons"));
		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
		_ar.setPokemons(ffs);

		//update edges for pokemons !!
		for (CL_Pokemon pok :
				_ar.getPokemons()) {
			Arena.updateEdge(pok, gg);
		}


		for (int i = 0; i < log.size(); i++) {
			CL_Agent ag = log.get(i);
//			if (ag.getNextNode() == -1 && isAgentMovingToPokemon.get(i)) {
			if (ag.getNextNode() == -1) {
				//go through all pokemons, find nearest pokemon, set edge into its way
				double minDist = Double.MAX_VALUE;
				CL_Pokemon chosenPok = null;
				for (int j = 0; j < _ar.getPokemons().size(); j++) {
					double currDist = ga.shortestPathDist(_ar.getPokemons().get(j).get_edge().getDest(),ag.getSrcNode());
					if(currDist < minDist){
						chosenPok = _ar.getPokemons().get(j);
						minDist = currDist;
					}
				}
				List<node_data> path = ga.shortestPath(ag.getSrcNode(),chosenPok.get_edge().getSrc());
				System.out.println("Path updated for agent " + ag.getID() + ", it is: " + path);
				if(path.size()>1) //on the way, inside the path into pokemon !
					game.chooseNextEdge(ag.getID(),ga.shortestPath(ag.getSrcNode(),chosenPok.get_edge().getSrc()).get(1).getKey());
				else //path is complete, now eating the pokemon !
					game.chooseNextEdge(ag.getID(),chosenPok.get_edge().getDest());


//				for (int j = 0; j < groups.get(i).size(); j++) {
//					System.out.println("agentID: " + groups.get(i).get(j).get_edge().getSrc());
//					game.chooseNextEdge(i, groups.get(i).get(j).get_edge().getDest());
//					System.out.println(_ar.getPokemons());
				//game.chooseNextEdge(i, groups.get(i).get(j).get_edge().getDest());
//				}
				//game.chooseNextEdge(groups.get(i).get(groups.get(i).size() - 1).get_edge().getSrc(), groups.get(i).get(groups.get(i).size() - 1).get_edge().getDest());
			}
		}
//		Thread client = new Thread(new Ex2());
//		client.start();


//		String pStr = gameServ.getPokemons();
//		System.out.println("p: "+pStr);
//		ArrayList<CL_Pokemon> arrP = new ArrayList<CL_Pokemon>();
//		int i = 0;
//		StringTokenizer str = new StringTokenizer(pStr,"{},:[]\"");
//		//todo: delete next line
//		System.out.println("sss");
//		while(str.hasMoreTokens()){
//			if(i==0){
//				str.nextToken();
//				i++;
//			}
//			else {
//				str.nextToken();
//				str.nextToken();
//				double value = Double.parseDouble(str.nextToken());
//				//System.out.println("v="+value);
//				str.nextToken();
//				int type = Integer.parseInt(str.nextToken());
//				str.nextToken();
//				double x = Double.parseDouble(str.nextToken());
//				double y = Double.parseDouble(str.nextToken());
//				double z = Double.parseDouble(str.nextToken());
//				Point3D pos = new Point3D(x,y,z);
//				boolean flag = true;
//				Collection<node_data> vS = gg.getV();
//				for(node_data v : vS) {
//					//System.out.println((v+","));
//					if (flag) {
//						Collection<edge_data> eS = gg.getE(v.getKey());
//						for (edge_data e : eS) {
//							if (flag) {
//								//System.out.println(e);
//								node_data nSrc = gg.getNode(e.getSrc());
//								node_data nDest = gg.getNode(e.getDest());
//								geo_location pSrc = nSrc.getLocation();
//								geo_location pDest = nDest.getLocation();
//								double tX = (pos.x() - pSrc.x()) * (pDest.y() - pSrc.y());
//								double tY = (pos.y() - pSrc.y()) * (pDest.x() - pSrc.x());
//								//double tZ = (pos.z()-pSrc.z())/(pDest.z()-pSrc.z());
//
//								if (Math.abs(tX - tY) < eps) {
//
//									if (type == -1) {
//										int min = Math.min(nSrc.getKey(), nDest.getKey());
//										int max = Math.max(nSrc.getKey(), nDest.getKey());
//										CL_Pokemon p = new CL_Pokemon(pos, type, value, 0, new EdgeData(max, min, e.getWeight()));
//										arrP.add(p);
//										flag = false;
//
//									} else {
//										int min = Math.min(nSrc.getKey(), nDest.getKey());
//										int max = Math.max(nSrc.getKey(), nDest.getKey());
//										CL_Pokemon p = new CL_Pokemon(pos, type, value, 0, new EdgeData(min, max, e.getWeight()));
//										arrP.add(p);
//										flag = false;
//
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//
//
//		Iterator<CL_Pokemon> iter = arrP.iterator();
//		CL_Pokemon run = iter.next();
//		//todo: use JSONObject like in Arena.java, line 83.
//		StringTokenizer temp = new StringTokenizer(gameServ.toString(),"{}[],:\"");
//
//		for (int j=1 ; j<19 ; j++) {
//			temp.nextToken();
//		}
//		int numAgents = Integer.parseInt(temp.nextToken());
//		ArrayList<ArrayList<CL_Pokemon>> pS = new ArrayList<ArrayList<CL_Pokemon>>();
//		for(int j=0; j<numAgents ; j++){
//			pS.add(new ArrayList<CL_Pokemon>());
//		}
//		int group = arrP.size()/numAgents;
//
//		System.out.println("Pokemons in each group: " + group);
//		System.out.println("Number of pokemon groups to catch: " + pS.size());
//		for (int j=0 ; j<numAgents ; j++ ){
//			ArrayList<CL_Pokemon> agentI = pS.get(j);
//			agentI.add(run);
//			for(int k=0 ; k<group-1 ; k++){
//				arrP.remove(run);
//				run = minBetweenPokemon(arrP,run,ga);
//				agentI.add(run);
//			}
//			arrP.remove(run);
//			run = minBetweenPokemon(arrP,run,ga);
//		}

//	}
	}


//		for(int i=0;i<log.size();i++) {
//			CL_Agent ag = log.get(i);
//			int id = ag.getID();
//			int dest = ag.getNextNode();
//			int src = ag.getSrcNode();
//			double v = ag.getValue();
//			if(dest==-1) {
//				dest = nextNode(gg, src);
//				game.chooseNextEdge(ag.getID(), dest);
//				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
//			}
//		}

	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */
	private static int nextNode(directed_weighted_graph g, int src) {
		int ans = -1;
		Collection<edge_data> ee = g.getE(src);
		Iterator<edge_data> itr = ee.iterator();
		int s = ee.size();
		int r = (int)(Math.random()*s);
		int i=0;
		while(i<r) {itr.next();i++;}
		ans = itr.next().getDest();
		return ans;
	}
	private void init(game_service game) {
		String g = game.getGraph();
		String fs = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		//gg.init(g);
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		JFrame frame = new JFrame("Ex2 - OOP: Pokemon Game by ZIV MADAR");
		_win = new MyFrame();
		frame.getContentPane().add(_win);
		frame.setSize(1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);
//		frame.paintImmediately()

		_win.update(_ar);


		_win.show();
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());
			int src_node = 0;  // arbitrary node, you should start at one of the pokemon
			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
			for(int a = 0;a<rs;a++) {
				int ind = a%cl_fs.size();
				CL_Pokemon c = cl_fs.get(ind);
				int nn = c.get_edge().getDest();
				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}

//				game.addAgent(nn);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
}
