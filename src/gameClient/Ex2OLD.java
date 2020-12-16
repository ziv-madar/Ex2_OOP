package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;
import api.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Ex2OLD {

	public static void main(String[] args) {
		int scenario_num = 0;
		String level = JOptionPane.showInputDialog("To load a level, please enter the level number [0-23]:");
		try {
			scenario_num = Integer.parseInt(level);
		} catch (NumberFormatException ee) {
			JOptionPane.showMessageDialog(null, "Only numbers are allowed! Level 0 will be loaded.");
			scenario_num = 0;
		}
		if(scenario_num < 0  ||  scenario_num > 23){
			scenario_num = 0;
		}
		startGame(scenario_num);

		Thread client = new Thread(new Ex2());
		client.start();
	}

	private static void startGame(int level){
		game_service game = Game_Server_Ex2.getServer(level);
		System.out.println(game.getGraph());
		System.out.println(game);
		try{
			PrintWriter pw = new PrintWriter(new File("./level.txt"));
			pw.write(game.getGraph().toString());
			pw.close();
			DWGraph_Algo ga = new DWGraph_Algo();
			directed_weighted_graph g = new DWGraph_DS();
//			ga.init(g);
			ga.load("./level.txt");
			System.out.println(ga.getGraph());
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}



//	@Override
//	public void run() {
//		int scenario_num = 0;
//		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
//		//	int id = 999;
//		//	game.login(id);
//		String g = game.getGraph();
//		String pks = game.getPokemons();
//		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
//		init(game);
//
//		game.startGame();
//		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
//		int ind=0;
//		long dt=100;
//
//		while(game.isRunning()) {
//			moveAgants(game, gg);
//			try {
//				if(ind%1==0) {_win.repaint();}
//				Thread.sleep(dt);
//				ind++;
//			}
//			catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//		String res = game.toString();
//
//		System.out.println(res);
//		System.exit(0);
//	}
}
