package com.umons.model;

public class Game {

	private int numberPlayer;
	private int tour = 0;
	private Grid board;
	
	public Game(Grid board, int numberPlayer) {
		this.numberPlayer = numberPlayer;
		this.board = board;
	}
	
	public void nextPlayer() {
		tour = ((tour+1)%numberPlayer);
	}

	public int getTour() {
		return tour;
	}

	public boolean play(Player player, Location loc) {
		if (loc.lSquare()) {
			return player.move(board, loc);
		}else if (loc.isWallHorizontal() || loc.isWallVertical()) {
			return player.putWall(board, loc);
		}return false;

	}
	
	public boolean win(Player player) {
		if (player.getOrder() == 1) {
			return player.getLoc().isEquals(Player.POS2);
		}else if (player.getOrder() == 2) {
			return player.getLoc().isEquals(Player.POS1);
		}else if (player.getOrder() == 3) {
			return player.getLoc().isEquals(Player.POS4);
		}else if (player.getOrder() == 4) {
			return player.getLoc().isEquals(Player.POS3);
		}return false;
	}

}