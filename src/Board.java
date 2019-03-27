import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Window;

import javax.swing.JPanel;
import java.util.ArrayList;

enum Columns {
	a, b, c, d, e, f, g, h;
}

public class Board {
	public boolean color = false;
	public static boolean turn = false;
	public static ArrayList<Pieces> white;
	public static ArrayList<Pieces> black;
	public static Pieces selected;
	
	public static ArrayList<Coordinate> whitePossible;
	public static ArrayList<Coordinate> blackPossible;
	
	Board(){
		white = new ArrayList<Pieces>();
		black = new ArrayList<Pieces>();
		whitePossible = new ArrayList<Coordinate>();
		blackPossible = new ArrayList<Coordinate>();
		initialize();
	}
	public void initialize() {
		while (!white.isEmpty()) {
			white.remove(white.size()-1);
		}
		while (!black.isEmpty()) {
			black.remove(black.size()-1);
		}
		//Adding in the pawns
		for (Columns col : Columns.values()) {
			black.add(new Pawn(team.black, col, 1));
			white.add(new Pawn(team.white, col, 6));
		}
		//Adding in the rooks
		black.add(new Rook(team.black, Columns.a, 0));
		black.add(new Rook(team.black, Columns.h, 0));
		white.add(new Rook(team.white, Columns.a, 7));
		white.add(new Rook(team.white, Columns.h, 7));
		//Adding in the knights
		black.add(new Knight(team.black, Columns.b, 0));
		black.add(new Knight(team.black, Columns.g, 0));
		white.add(new Knight(team.white, Columns.b, 7));
		white.add(new Knight(team.white, Columns.g, 7));
		//Adding in the bishops
		black.add(new Bishop(team.black, Columns.c, 0));
		black.add(new Bishop(team.black, Columns.f, 0));
		white.add(new Bishop(team.white, Columns.c, 7));
		white.add(new Bishop(team.white, Columns.f, 7));
		//Adding in the Queens
		black.add(new Queen(team.black, Columns.d, 0));
		white.add(new Queen(team.white, Columns.d, 7));
		//Adding in the Kings
		black.add(new King(team.black, Columns.e, 0));
		white.add(new King(team.white, Columns.e, 7));
		
		updateAllPossibleMoves();
	}
	
	public int findSelectedIndex(ArrayList<Pieces> set) {
		for (int x = 0; x < set.size(); x++) {
			if (set.get(x).selected == true && set.get(x) instanceof Pieces) {
				return x;
			}
		}
		return -1;
	}
	
	public void changeTurn() {
		turn = !turn;
	}
	
	public static void updateAllPossibleMoves() {
		//Clear all possible moves
		while(whitePossible.size() > 0) {
			whitePossible.remove(whitePossible.size()-1);
		}
		while (blackPossible.size() > 0) {
			blackPossible.remove(blackPossible.size()-1);
		}
		
		//Update all possible moves for white
		int whiteSize = white.size();
		for (int x = 0; x < whiteSize; x++) {
			ArrayList<Coordinate> tmp = white.get(x).possibleMoves();
			//tmp = white.get(x).possibleMoves();
			for(int y = 0; y < tmp.size(); y++) {
				whitePossible.add(tmp.get(y));
			}
		}
		//Update all possible moves for black
		int blackSize = black.size();
		for (int x = 0; x < blackSize; x++) {
			ArrayList<Coordinate> tmp = black.get(x).possibleMoves();
			//tmp = black.get(x).possibleMoves();
			for(int y = 0; y < tmp.size(); y++) {
				blackPossible.add(tmp.get(y));
			}
		}
	}
	
	public void update(Graphics g) {
		//Draw Chess Board
		for (int x = 0; x < 8; x++) {
			color = !color;
			for (int y = 0; y < 8; y++) {
				if (!color) {
					g.setColor(Color.darkGray);
				} else {
					g.setColor(Color.LIGHT_GRAY);
				}
				g.fillRect(x*(View.smallest/8), y*(View.smallest/8), (View.smallest/8)+2, (View.smallest/8)+2);
				color = !color;
			}
		}
		
		//Draw panel on side
		//In the future Draw the gui elements too
		g.setColor(Color.yellow);
		g.fillRect(View.smallest, 0, View.width-View.smallest+1, View.height+1);
		g.setColor(Color.black);
		g.drawRect(View.smallest-1, 0-1, View.width-View.smallest+1, View.height+1);
		
		//Draw Pieces
		for (int x = 0; x < white.size(); x++) {
			white.get(x).updateImage(g);
		}
		for (int x = 0; x < black.size(); x++) {
			black.get(x).updateImage(g);
		}
		
		//Draw text on panel
		g.setColor(Color.black);
		g.drawString("White Possible = "+whitePossible.size(), View.smallest+10, 100);
		g.drawString("Black Possible = "+blackPossible.size(), View.smallest+10, 150);
	}
	public void updateScene() {
		for (int x = 0; x < white.size(); x++) {
			white.get(x).updateState();
		}
		for (int x = 0; x < black.size(); x++) {
			black.get(x).updateState();
		}
	}
}
