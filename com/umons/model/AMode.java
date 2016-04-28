package com.umons.model;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.umons.controller.Controller;
import com.umons.controller.MyMouseListener;
import com.umons.view.BoardGUI;
import com.umons.view.MenuGUI;
import com.umons.view.QuoridorGUI;

public abstract class AMode{
	
	
	protected Player[] players;
	protected Grid board;
	protected JPanel boardPanel;
	protected JFrame frame;
	protected AStarHeuristic heuristic;
	protected IPathFinder finder;
	protected int nbreHumans;
	
	/**
	 * Initalise une game
	 * @param game un objet game ou sont definis certaines fonctions pratique concernant le deroulement d une partie
	 */
	public void init(QuoridorGUI frame, Game game) {
		ARules.setBoard(board);
		JPanel board = new BoardGUI(game, frame);
		
		this.boardPanel = board;
		this.frame = frame;
		
		board.setFocusable(true);
		Controller controller = new Controller(this, board, game, finder);
		MyMouseListener l = new MyMouseListener(controller);
		board.addMouseListener(l);
		board.addMouseMotionListener(l);
		System.out.println(frame + " " + board);
		frame.setPane(board, 0);
		frame.setContentPane(board);
	}
	
	/**
	 * @return le nombre de joueur dans la game
	 */
	public abstract int getNumberOfPlayer();
	
	/**
	 * 
	 * @return les instance de player dans l ordre de leur numero
	 */
	public abstract Player[] getPlayer();
	
	/**
	 * Test si il y a un chemin pour le joueur player apres qu'il ait mis un mur de coordonées coordWall se lon l'algorithme de recherche "finder"
	 * @param player l instance du joueur qui pose le mur
	 * @param finder
	 * @param loc la position du mur qui risque de bloquer un joueur
	 * @return vrai si il y a un chemin, faux sinon
	 */
	public abstract boolean testFinder(Player player, Location coordWall, IPathFinder finder);
	
	public IPathFinder getFinder() {
		return finder;
	}
	
	public JPanel getPane() {
		return boardPanel;
	}
	
	public JFrame getFrame() {
		return frame;
	}
}

