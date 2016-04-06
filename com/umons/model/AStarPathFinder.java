package com.umons.model;

import java.util.ArrayList;
import java.util.Collections;

public class AStarPathFinder implements IPathFinder{

	
	//BUUUUGGGG
	ArrayList<Location>listSquareAvailable;
	private Node[][] nodes;
	private Grid board;
	private int maxDepth;
	private int maxSearchDistance;
	private AStarHeuristic heuristic;
	ArrayList<Node> closeList = new ArrayList<Node>();
	SortedList openList = new SortedList();
	
	public AStarPathFinder(Grid board, int maxSearchDistance, AStarHeuristic heuristic) {
		nodes = new Node[Grid.getLen()][Grid.getLen()];
		for (int i = 0; i < Grid.getLen(); i++) {
			for (int j = 0; j < Grid.getLen(); j++) {
				nodes[j][i] = new Node(j, i);
			}
		}
		for (int i = 0; i < board.getLen(); i++) {
			//ne fonctionne pas, doit changer les == par equals (elle veut pas se surcharger c'est bizarre)
			//Affiche les numéros de lignes pour faciliter les tests
			if (i<10){
				System.out.print("" + i + "  ");
			}else{
				System.out.print("" + i + " ");
			}
			for (int j = 0; j < board.getLen(); j++) {
				System.out.print(board.getItem(new Location(i, j)));
			}
			System.out.println();
		}
		System.out.println("    0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16");
		this.heuristic = heuristic;
		this.board = board;
		this.maxSearchDistance = maxSearchDistance;
	}
	
	@Override
	public Path findPath(Location coordWall, int sx, int sy, int tx, int ty) {
		//ajouter le mur que le joueur veut poser pour simuler
		//ensuite l enlever a la fin et decider pour de vrai si oui ou non on le pose
		
		setWallTo(coordWall, true);
		
		System.out.println("dans le pathfinding");
		//on verifie que la case d arrive n est pas bloque, auquel cas, c est mort pour cette case
		Square targetSquare = new Square(tx, ty);
		if (targetSquare.isBlocked()) {
			setWallTo(coordWall, false);
			return null;
		}
		
		openList.clear();
		closeList.clear();
		nodes[sx][sy].cost = 0;
		nodes[sx][sy].depth = 0;
		
		openList.add(nodes[sx][sy]);
		
		nodes[tx][ty].parent = null;

		maxDepth = 0;
		int x = -1;
		System.out.println("\n\n\n\n\n\n\n\n\n");
		while (maxDepth < maxSearchDistance && openList.size() > 0) {
			boolean checkNode = false;
			x++;
			System.out.println("-------------------------------------------------------------------------------------------ENTRE DANS LA BOUCLE : " + x);
			
			Node current = (Node) openList.getFirst();
			//on regarde si le premier noeud de la liste est bien un noeud on le pion peut se deplacer
			//a cause des calculs parfois un noeud plus haut est autorise (calculs a modifier)
			//si on est pas a notre premiere iteration (auquel cas listSquareAvailable est vide)
			/*if (x!=0) {
				System.out.println("in: " + x);
				//on parcours toute l open list a la recherche d un noeud valide
				for (int i = 0; i < openList.size(); i++){
					if (!listSquareAvailable.contains(new Location(current.x, current.y))) {
						System.out.println(current);
						openList.remove(current);
						if (i < (openList.size()-1)){
							current = (Node) openList.get(i+1);
						}
					}else {
						checkNode = true;
						break;
					}
				}
			}
			//si on en a pas trouve, alors c est qu il y a pas de chemin
			if (!checkNode && x!=0) {
				setWallTo(coordWall, true);
				return null;
			}*/
			
			System.out.println("Node current: "+ current);
			
			if (current == nodes[tx][ty]) {
				break;
			}
			
			openList.remove(current);
			closeList.add(current);
			
			System.out.print("CLOSE LIST: ");
			System.out.print("[");
			for (int j = 0; j < closeList.size(); j++){
				System.out.print(closeList.get(j) +", ");
			}System.out.print("]\n");
			
			//liste des positions valide pour evaluer leur cotes
			listSquareAvailable = ARules.rSquareAvailable(new Location(current.getX(), current.getY()));
			
			System.out.print("[");
			for (int j = 0; j < listSquareAvailable.size(); j++){
				System.out.print(listSquareAvailable.get(j) +", ");
			}System.out.print("]\n");
			
			//on enleve ceux qui sont deja dans la liste ferme
			for (int i = 0; i < listSquareAvailable.size(); i++) {
				if (closeList.contains(new Node(listSquareAvailable.get(i)))) {
					listSquareAvailable.remove(i);
				}
			}
			//on evalue chaque noeud
			for (int i = 0; i < listSquareAvailable.size(); i++) {
				//on calcule le cout de la case voisine qu on est entrain d'analyser
				float nextStepCost = current.cost + board.getMovementCost(new Location(current.getX(), current.getY()), listSquareAvailable.get(i));
				Node neighbour = nodes [listSquareAvailable.get(i).getLocX()][listSquareAvailable.get(i).getLocY()];
				System.out.println("neigbour: " + neighbour + "/////Numero: " + i);
				if (nextStepCost < neighbour.cost) {
					if (openList.contains(neighbour)){
						openList.remove(neighbour);
					}
					if (closeList.contains(neighbour)) {
						closeList.remove(neighbour);
					}
				}
				if (!openList.contains(neighbour) && !closeList.contains(neighbour)) {
					//la comparaison de noeud se fait sur base de l addition des deux cout (voir la methode compareTo dans Node)
					neighbour.cost = nextStepCost;
					neighbour.heuristic = heuristic.getCost(new Location(neighbour.getX(), neighbour.getY()), new Location(tx, ty));
					//A ESSAYER DE COMPRENDRE
					maxDepth = Math.max(maxDepth,  neighbour.setParent(current));
					openList.add(neighbour);
					System.out.print("LISTE APRES AJOUT: ");
					System.out.print("[");
					for (int j = 0; j < openList.size(); j++){
						System.out.print(openList.get(j) +", ");
					}System.out.print("]\n");
				}
			}
		}
		
		System.out.println("je suis avant le test null");
		if (nodes[tx][ty].parent == null) {
			setWallTo(coordWall, false);
			return null;
		}
		System.out.println("je suis apres le test null");
		
		//on cree les chemin vide qu'on va retourner
		Path path = new Path();
		//le noeud finale qui va nous permettre de remonter jusque la source
		Node target = nodes[tx][ty];
		//tant que le noeud target n est pas celui d ou on est parti

		while (target != nodes[sx][sy]) {
			//on ajoute chaque etape du chemin au debut pour au final l avoir trie dans l ordre (source -> target)
			path.prependStep(target.getX(), target.getY());
			//on remonte d une etape jusque la source
			target = target.parent;
		}
		//on termine la boucle, donc c est que target valait la source
		//donc on ajoute la case de depart pour avoir le chemin complet
		path.prependStep(sx, sy);
		System.out.println("je suis avant de retourner un chemin, tehcniquement non nul PATH: ");
		System.out.print("[");
		for (int j = 0; j < path.getLength(); j++){
			System.out.print(path.getStep(j) +", ");
		}System.out.print("]\n");
		//fin de la simulation, on vide le mur au cas ou il aurait fallu ne pas le poser
		setWallTo(coordWall, false);
		return path;
		 
	}
	
	/**
	 * Rempli un objet plusieurs objets wall dans la board
	 * @param loc la position de la premiere case a gauche ou de la premiere case a droite
	 * @param b vrai si on doit remplir l objet, faux si on doit le vider
	 */
	public void setWallTo(Location loc, boolean b) {
		if (loc.isWallHorizontal()) {
			for (int j = loc.getLocX(); j < loc.getLocX() + 3; j++) {
				board.setItemInGrid(new Location(j, loc.getLocY()), b);
			}
				
		}else if (loc.isWallVertical()) {
			for (int i = loc.getLocY(); i < loc.getLocY() + 3; i++) {
				board.setItemInGrid(new Location(loc.getLocX(), i), b);
			}
		}
	}
	
	/**
	 * Representation d une liste qui se trie apres chaque ajout de noeud
	 * 
	 * @author Inspired by Kevin Glass's code
	 *
	 */
	private class SortedList{
		
		private ArrayList list = new ArrayList();
		
		public SortedList() {
		}
		
		public Object getFirst() {
			return list.get(0);
		}
		
		public Object get(int i) {
			return list.get(i);
		}
		
		public void clear() {
			list.clear();
		}
		
		/**
		 * Ajout un objet puis trie la liste.
		 * @param obj
		 */
		public void add(Object obj) {
			list.add(obj);
			Collections.sort(list);
		}
		
		public void remove(Node node) {
			list.remove(node);
		}
		
		public int size() {
			return list.size();
		}
		
		public boolean contains(Object obj) {
			return list.contains(obj);
		}
	}
	
	/**
	 * Representation d un noeud (selon la theorie des graphes)
	 * @author Inspired by Kevin Glass's code
	 *
	 */
	public class Node implements Comparable{
		
		private int x;
		private int y;
		private float cost;
		private Node parent;
		private float heuristic;
		private int depth;
		
		/**
		 * Cr�e un nouveau noeud
		 * 
		 * @param x la coordonn�es en x du noeud
		 * @param y la coordonn�es en y du noeud
		 */
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		 /**
		  * Cree un nouveau sur base d une Location
		  * @param loc la position du noeud dans la grille
		  */
		public Node(Location loc) {
			this.x = loc.getLocX();
			this.y = loc.getLocY();
		}
		
		/**
		 * D�fini le "parent" de ce noeud
		 * 
		 * @param parent le noeud parent, celui qui nous amene a ce noeud
		 * @return La profondeur qu'on a atteint (The depth we have ?no reached? in searching)
		 */
		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;
			
			return depth;
		}
		
		/**
		 * Utiliser dans la comparaison de noeud
		 * @see Comparable#compareTo(Object)
		 */
		public int compareTo(Object other) {
			Node o = (Node) other;
			
			float f = heuristic + cost;
			float of = o.heuristic + o.cost;
			
			if (f < of) {
				return -1;
			} else if (f > of) {
				return 1;
			} else {
				return 0;
			}
		}
		
		public boolean equals(Object obj) {
			Node no = (Node)obj;
			return (x == no.getX() && y == no.getY());
		}
		
		/**
		 * Accesseur de x
		 */
		public int getX() {
			return x;
		}
		
		/**
		 * Accesseur de y
		 */
		public int getY() {
			return y;
		}
		
		public String toString() {
			String s = "*" + cost + "/" + heuristic + "*" + "(" + x + ", " + y + ")";
			return s;
		}

	}
	
}