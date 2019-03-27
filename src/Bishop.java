import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Bishop extends Pieces {
	Bishop(team color, Columns col, int row){
		super("src/BlackBishop.png");
		if (color == team.black) {
			super.setImage("src/BlackBishop.png");
		} else {
			super.setImage("src/WhiteBishop.png");
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
		
		int directionX = 1;
		int directionY = 1;
		
		for (int i = 0; i < 4; i++) {
			boolean cont = true;
			boolean hit = false;
			int magnitude = 1;
			
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
				
				if (hit || magnitude == 10) {
					cont = false;
				}
				magnitude += 1;
			}
			
			if (i % 2 == 0) {
				directionX *= -1;
			} else {
				directionY *= -1;
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
