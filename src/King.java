import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class King extends Pieces {
	King(team color, Columns col, int row){
		super("src/BlackKing.png");
		if (color == team.black) {
			super.setImage("src/BlackKing.png");
		} else {
			super.setImage("src/WhiteKing.png");
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
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if (Coordinate.isShiftInBounds(i, j, start)) {
					possible.add(new Coordinate(start.col.ordinal()+i,start.row+j));
				}
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
		
		for (int i = 0; i < possible.size(); i++) {
			//Remove possibilities if in threat
			for (int j = 0; j < Board.blackPossible.size(); j++) {
				if (super.color == team.white && Coordinate.isSameCoordinate(Board.blackPossible.get(j), possible.get(i))) {
					possible.remove(i);
					if (possible.size() > 1)
						i--;
				}
			}
			for (int j = 0; j < Board.whitePossible.size(); j++) {
				if (super.color == team.black && Coordinate.isSameCoordinate(Board.whitePossible.get(j), possible.get(i))) {
					possible.remove(i);
					if (possible.size() > 1)
						i--;
				}
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
