import java.awt.Graphics;
import java.io.IOException;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

import javax.swing.SwingUtilities;
import java.lang.Thread;
import java.util.ArrayList;

import javax.swing.JFrame;

public class App implements MouseListener, KeyListener {
	Board board;
	View view;
	App() throws IOException, Exception {
		board = new Board();
		view = new View(this);
	}
	
	public void update(Graphics g) {
		board.update(g);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyChar() == 'h') {
			System.out.println("Hey");
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (SwingUtilities.isLeftMouseButton(e)) {
			boolean move = false;
			int whichColor = -1;
			Board.updateAllPossibleMoves();
			
			//Find what piece is selected currently and see if the mouse is in an applicable move
			//Then change the selection
			
			int WhiteSelected = board.findSelectedIndex(Board.white);
			int BlackSelected = board.findSelectedIndex(Board.black);
			
			if (WhiteSelected != -1) {
				whichColor = 0;
			} else if (BlackSelected != -1) {
				whichColor = 1;
			} else {
				whichColor = -1;
			}
			
			//See if click is in applicable move space and move
			if (whichColor == 0) {
				ArrayList<Coordinate> tmp = Board.white.get(WhiteSelected).possibleMoves;
				for (int i = 0; i < tmp.size(); i++) {
					if (tmp.get(i).getX() == e.getX()/(View.smallest/8) && tmp.get(i).getY() == e.getY()/(View.smallest/8)) {
						Board.white.get(WhiteSelected).move(tmp.get(i));
						move = true;
					}
				}
			} else if (whichColor == 1) {
				ArrayList<Coordinate> tmp = Board.black.get(BlackSelected).possibleMoves;
				for (int i = 0; i < tmp.size(); i++) {
					if (tmp.get(i).getX() == e.getX()/(View.smallest/8) && tmp.get(i).getY() == e.getY()/(View.smallest/8)) {
						Board.black.get(BlackSelected).move(tmp.get(i));
						move = true;
					}
				}
			}
			
			//Check if there's a piece under the new location
			if (move && whichColor == 0) {
				int index = Pieces.isPieceHereLocation(Board.white.get(WhiteSelected).col, Board.white.get(WhiteSelected).row, Board.black);
				if (index != -1) {
					Board.black.remove(index);
				}
			} else if (move && whichColor == 1) {
				int index = Pieces.isPieceHereLocation(Board.black.get(BlackSelected).col, Board.black.get(BlackSelected).row, Board.white);
				if (index != -1) {
					Board.white.remove(index);
				}
			}
			
			//Deselect current selection
			if (whichColor == 0) {
				Board.white.get(WhiteSelected).select();
			} else if (whichColor == 1) {
				Board.black.get(BlackSelected).select();
			}
			
			if (move) {
				board.changeTurn();
				Board.updateAllPossibleMoves();
			}
			
			//Find selection if there was no move
			if (!move) {
				if (!Board.turn) {
					for (int x = 0; x < Board.white.size(); x++) {
						if (Board.white.get(x) instanceof Pieces) {
							//Tmp is piece that was clicked
							Pieces tmp = (Pieces)Board.white.get(x);
							tmp.changeSelect(e);
						}
					}
				}
				if (Board.turn) {
					for (int x = 0; x < Board.black.size(); x++) {
						if (Board.black.get(x) instanceof Pieces) {
							//Tmp is piece that was clicked
							Pieces tmp = (Pieces)Board.black.get(x);
							tmp.changeSelect(e);
						}
					}
				}
			}
			view.repaint();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) throws Exception {
		new App();
	}
}
