import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

enum team {
	white, black;
}

public class Pieces {
	public Columns col = Columns.a;
	public int row;
	public team color;
	public boolean selected = false;
	public boolean captured = false;
	
	public ArrayList<Coordinate> allMoves = new ArrayList<Coordinate>();
	public ArrayList<Coordinate> noOwnPiecesMoves = new ArrayList<Coordinate>();
	public ArrayList<Coordinate> noAllPiecesMoves = new ArrayList<Coordinate>();
	public ArrayList<Coordinate> possibleMoves = new ArrayList<Coordinate>();
	
	
	private Image image;
	
	Pieces(String file){
		setImage(file);
	}
	
	public void changeSelect(MouseEvent e) {
		if (e.getX() > col.ordinal()*(View.smallest/8) && e.getX() < (col.ordinal()+1)*(View.smallest/8)) {
			if (e.getY() > row*(View.smallest/8) && e.getY() < (row+1)*(View.smallest/8)) {
				selected = !selected;
				if (selected)
					System.out.println("I have been selected... col: "+col.ordinal()+" row: "+row);
			}
		}
		
	}
	
	public void move(Coordinate location) {
		setPosition(location.col, location.row);
		selected = false;
	}
	
	public void select() {
		if (this.selected == false) {
			this.selected = true;
		}
		if (this.selected == true) {
			this.selected = false;
		}
	}
	
	public ArrayList<Coordinate> possibleMoves() {
		System.out.println("This shouldn't be used");
		return null;
	}
	
	public void showMoves(Graphics g) {
		
	}
	
	public void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
	}

	public void updateImage(Graphics g) {
		g.drawImage(getImage(), col.ordinal()*(View.smallest/8), row*(View.smallest/8), View.smallest/8, View.smallest/8, null);
	}
	
	public void updateState() {
		
	}
	
	public void setPosition(Columns col, int row) {
		this.col = col;
		this.row = row;
	}
	
	public void setColor(team color) {
		this.color = color;
	}
	
	public Image getImage() {
		return image;
	}
	
	public team getColor() {
		return this.color;
	}
	
	public static Boolean isPieceHere(Columns col, int row, ArrayList<Pieces> pieces) {
		for (int x = 0; x < pieces.size(); x++) {
			if (pieces.get(x).col == col && pieces.get(x).row == row) {
				return true;
			}
		}
		return false;
	}
	
	public static int isPieceHereLocation(Columns col, int row, ArrayList<Pieces> pieces) {
		for (int x = 0; x < pieces.size(); x++) {
			if (pieces.get(x).col == col && pieces.get(x).row == row) {
				return x;
			}
		}
		return -1;
	}
	
	//public void paintComponent(Graphics g) {
	//	g.drawImage(getImage(), col.ordinal()*(Board.smallest/8), row*(Board.smallest/8), Board.smallest/8, Board.smallest/8, null);
	//}
	
}
