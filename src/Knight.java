import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Knight extends Pieces {
	Knight(team color, Columns col, int row){
		super("src/BlackKnight.png");
		if (color == team.black) {
			super.setImage("src/BlackKnight.png");
		} else {
			super.setImage("src/WhiteKnight.png");
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
		
		//There's 8 possible moves
		//fill the array
		for (int i = 0; i < 8; i++) {
			possible.add(new Coordinate(start));
		}
		
		if (Coordinate.isShiftInBounds(1, 2, start))
			possible.get(0).shift(1, 2);
		if (Coordinate.isShiftInBounds(2, 1, start))
			possible.get(1).shift(2, 1);
		if (Coordinate.isShiftInBounds(2, -1, start))
			possible.get(2).shift(2, -1);
		if (Coordinate.isShiftInBounds(1, -2, start))
			possible.get(3).shift(1, -2);
		if (Coordinate.isShiftInBounds(-1, -2, start))
			possible.get(4).shift(-1, -2);
		if (Coordinate.isShiftInBounds(-2, -1, start))
			possible.get(5).shift(-2, -1);
		if (Coordinate.isShiftInBounds(-2, 1, start))
			possible.get(6).shift(-2, 1);
		if (Coordinate.isShiftInBounds(-1, 2, start))
			possible.get(7).shift(-1, 2);
		
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
