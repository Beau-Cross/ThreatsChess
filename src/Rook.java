import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Rook extends Pieces {
	Rook(team color, Columns col, int row){
		super("src/BlackRook.png");
		if (color == team.black) {
			super.setImage("src/BlackRook.png");
		} else {
			super.setImage("src/WhiteRook.png");
		}
		super.setColor(color);
		super.setPosition(col, row);
	}
	public void showMoves(Graphics g) {
		ArrayList<Coordinate> possible = possibleMoves();
		Color halfTransRed = new Color(1, 0, 0, (float)0.5);
		g.setColor(halfTransRed);
		for (int i = 0; i < possible.size(); i++) {
			g.fillRect(possible.get(i).col.ordinal()*(View.smallest/8), possible.get(i).row*(View.smallest/8), View.smallest/8, View.smallest/8);
		}
	}
	public ArrayList<Coordinate> possibleMoves() {
		ArrayList<Coordinate> possible = new ArrayList<Coordinate>();
		Coordinate start = new Coordinate(this.col, this.row);
		int x = start.getX();
		int y = start.getY();
		
		int[] directionXList = new int[] { 0, 1, 0, -1};
		int[] directionYList = new int[] { 1, 0, -1, 0};
		
		for (int i = 0; i < 4; i++) {
			boolean cont = true;
			boolean hit = false;
			int magnitude = 1;
			
			int directionX = directionXList[i];
			int directionY = directionYList[i];
			
			while(cont) {
				possible.add(new Coordinate(start));
				if (Coordinate.isShiftInBounds(directionX*magnitude, directionY*magnitude, start)) {
					if ((isPieceHere(Columns.values()[(start.col.ordinal()+directionX*magnitude)], start.row+directionY*magnitude, Board.white) && this.color == team.white) || (isPieceHere(Columns.values()[(start.col.ordinal()+directionX*magnitude)], start.row+directionY*magnitude, Board.black) && this.color == team.black)) {
						hit = true;
					} else if ((isPieceHere(Columns.values()[(start.col.ordinal()+directionX*magnitude)], start.row+directionY*magnitude, Board.white) && this.color == team.black) || ((isPieceHere(Columns.values()[(start.col.ordinal()+directionX*magnitude)], start.row+directionY*magnitude, Board.black) && this.color == team.white))){
						hit = true;
						possible.get(possible.size()-1).shift(directionX*magnitude, directionY*magnitude);
					} else {
						possible.get(possible.size()-1).shift(directionX*magnitude, directionY*magnitude);
					}
				} else {
					hit = true;
				}
				
				if (hit) {
					cont = false;
				}
				magnitude += 1;
			}
		}
		
		
		
		
		
		//Removing possibilities
		for (int i = 0; i < possible.size(); i++) {
			//Remove possibilities at the start
			if (possible.get(i).getX() == x && possible.get(i).getY() == y) {
				possible.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < possible.size(); i++) {
			//Remove possibilities if own team
			if (super.color == team.black && isPieceHere(possible.get(i).col, possible.get(i).row, Board.black)) {
				possible.remove(i);
				i--;
			}
			if (super.color == team.white && isPieceHere(possible.get(i).col, possible.get(i).row, Board.white)) {
				possible.remove(i);
				i--;
			}
		}
		
		super.possibleMoves = possible;
		
		return possible;
	}
	public void updateImage(Graphics g) {
		if (super.selected) {
			showMoves(g);
		}
		super.updateImage(g);
	}
	public void updateState() {
		
	}
}
