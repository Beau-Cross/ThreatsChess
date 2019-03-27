import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Pawn extends Pieces {
	Pawn(team color, Columns col, int row){
		super("src/BlackPawn.png");
		if (color == team.black) {
			super.setImage("src/BlackPawn.png");
		} else {
			super.setImage("src/WhitePawn.png");
		}
		setColor(color);
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
		
		//There's 8 possible moves
		//fill the array
		for (int i = 0; i < 4; i++) {
			possible.add(new Coordinate(start));
		}
		
		int pawnMove = 0;
		if (super.color == team.white) {
			pawnMove = -1;
		} else if (super.color == team.black) {
			pawnMove = 1;
		}
		
		for (int dx = -1; dx < 2; dx++) {
			if (dx == 0 && isPieceHere(start.col, start.row+pawnMove, Board.white)) {
			} else if (dx == 0 && isPieceHere(start.col, start.row+pawnMove, Board.black)){
			} else if (dx != 0){
				if (super.color == team.white && dx != 0) {
					if (Coordinate.isShiftInBounds(dx, pawnMove, start) && isPieceHere(Columns.values()[start.col.ordinal()+dx], start.row+pawnMove, Board.black))
						possible.get(dx+1).shift(dx, pawnMove);
				} else if (super.color == team.black && dx != 0) {
					if (Coordinate.isShiftInBounds(dx, pawnMove, start) && isPieceHere(Columns.values()[start.col.ordinal()+dx], start.row+pawnMove, Board.white))
						possible.get(dx+1).shift(dx, pawnMove);
				}
			} else {
				if (Coordinate.isShiftInBounds(dx, pawnMove, start))
					possible.get(1).shift(dx, pawnMove);
			}
		}
		if (Coordinate.isShiftInBounds(0, pawnMove*2, start) && ((super.row == 1) || (super.row == 6)) && !isPieceHere(start.col, start.row+pawnMove, Board.black) && !isPieceHere(start.col, start.row+(2*pawnMove), Board.black) && !isPieceHere(start.col, start.row+pawnMove, Board.white) && !isPieceHere(start.col, start.row+(2*pawnMove), Board.white))
			possible.get(3).shift(0, pawnMove*2);
		
		
		//Removing possibilities
		for (int i = 0; i < possible.size(); i++) {
			//Remove possibilities at the start
			if (possible.get(i).getX() == x && possible.get(i).getY() == y) {
				possible.remove(i);
				i--;
			}
		}
		
		super.allMoves = possible;
		
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
		
		super.noOwnPiecesMoves = possible;
		super.noAllPiecesMoves = possible;
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
