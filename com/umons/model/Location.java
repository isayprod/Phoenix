package com.umons.model;

import java.io.Serializable;

public class Location implements Serializable{

	private static final long serialVersionUID = -414141962435812650L;

	private int x;
	private int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	
	
	/**
	 * Permet d'obtenir l'objet Location situé 2 coordonnées au dessus de la location actuelle
	 * @return un Objet Location 
	 */
	public Location itemUp() {
		return new Location(x, y-2);
	}
	
	
	/**
	 * Permet d'obtenir l'objet Location situé 2 coordonnées en bas de la location actuelle
	 * @return un Objet Location 
	 */
	public Location itemDown() {
		return new Location(x, y+2);
	}
	
	
	/**
	 * Permet d'obtenir l'objet Location situé 2 coordonnées à gauche de la location actuelle
	 * @return un Objet Location 
	 */
	public Location itemLeft() {
		return new Location (x-2, y);
	}
	
	
	/**
	 * Permet d'obtenir l'objet Location situé 2 coordonnées à droite de la location actuelle
	 * @return un Objet Location 
	 */
	public Location itemRight() {
		return new Location (x+2, y);
	}
	
	
	//POUR TEST CONSOLE
	/**
	 * Modifie les entrées consoles en coordonées de type Location
	 * @param direction String représentant la direction du pion
	 * @return Location représentant les coordonnées de l'endroit ou le player (le pion) va se déplacer
	 */
	public Location stringToCoord(String direction) {
		// tableau de la forme {x, y}
		switch (direction) {
		case "z":
			return new Location(getLocX(), getLocY()-2);
		case "s":
			return new Location(getLocX(), getLocY()+2);
		case "d":
			return new Location(getLocX()+2, getLocY());
		case "q":
			return new Location(getLocX()-2, getLocY());
		case "zz":
			return new Location(getLocX(), getLocY()-4);
		case "ss":
			return new Location(getLocX(), getLocY()+4);
		case "dd":
			return new Location(getLocX()+4, getLocY());
		case "qq":
			return new Location(getLocX()-4, getLocY());
		case "dbd":
			return new Location(getLocX()+2, getLocY()+2);
		case "dbg":
			return new Location(getLocX()-2, getLocY()+2);
		case "dhg":
			return new Location(getLocX()-2, getLocY()-2);
		case "dhd":
			return new Location(getLocX()+2, getLocY()-2);
		default:
			return null;
		}
	}
	
	
	/**
	 * Verifie si l'objet Location est une case
	 * @return true si la case est square, false si c'est un wall
	 */
	public boolean isSquare() {
		return getLocX() % 2 == 0 && getLocY() % 2 == 0;
	}
	
	
	/**
	 * Verifie si un Mur est horizontal
	 * @return true si il est horizontal,sinon false
	 */
	public boolean isWallHorizontal() {
		return getLocX() % 2 == 0 && getLocY()%2 != 0;
	}
	
	
	/**
	 * Verifie si un Mur est vertical
	 * @return true si il est vertical,sinon false
	 */
	public boolean isWallVertical() {
		return getLocY() % 2 == 0 && getLocX()%2 != 0;
	}
	
	
	/**
	 * Verifie si la Location est dans la grillze
	 * @return true si le déplacement est autorisé,sinon false
	 */
	public boolean inGrid(Grid board) {
		return ((getLocX() >= 0) && (getLocX() < board.getLen()) && (getLocY() >= 0 && getLocY() < board.getLen()));
	}

	
	/**
	 * Verifie si deux objets Locations ont les même attributs x et y
	 * @param loc Objet location
	 * @return true si les Objets Location ont les mêmes attributs x et y, sinon false
	 */
	public boolean equals(Object obj) {
		Location loc = (Location) obj;
		return getLocX() == loc.getLocX() && getLocY() == loc.getLocY();
	}
	
	
	/**
	 * Accesseur de L'attribut x (Correspond aux colones dans la grille)
	 * @return la valeur de x
	 */
	public int getLocX() { return this.x; }
	
	
	/**
	 * Accesseur de L'attribut y (Correspond aux lignes dans la grille)
	 * @return la valeur de y
	 */
	public int getLocY() { return this.y; }
	
	
	/**
	 * Mutateur de l'attribut x(Correspond aux colones dans la grille)
	 * @param x nouvelle valeur de x 
	 */
	public void setLocX(int x) {
		this.x = x;
	}
	
	
	/**
	 * Mutateur de l'attribut y(Correspond aux lignes dans la grille)
	 * @param x (int) nouvelle valeur de y 
	 */
	public void setLocY(int y) {
		this.y = y;
	}
	
	/**
	 * Override de la methode toString
	 * Ex : " 5, 2 " 
	 */
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
	/**
	 * @return ls coordonnees Location du mur du dessus
	 */
	public Location wallUp() {
		return (new Location(x , y -1));
	}
	
	/**
	 * @return ls coordonnees Location du mur du dessus
	 */
	public Location wallLeft() {
		return (new Location(x - 1, y));
	}
	
	/**
	 * @return ls coordonnees Location du mur du dessus
	 */
	public Location wallRight() {
		return (new Location(x + 1, y));
	}
	
	/**
	 * @return ls coordonnees Location du mur du dessus
	 */
	public Location wallDown() {
		return (new Location(x , y +1));
	}
	
	
	
	
	
}
