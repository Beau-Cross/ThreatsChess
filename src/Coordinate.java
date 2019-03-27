
public class Coordinate {
		Columns col;
		int row;
		Coordinate(){
			this.col = Columns.a;
			this.row = 0;
		}
		Coordinate(Columns col, int row){
			this.col = col;
			this.row = row;
		}
		Coordinate(int col, int row){
			this.col = Columns.values()[col];
			this.row = row;
		}
		Coordinate(Coordinate copy){
			this.col = copy.col;
			this.row = copy.row;
		}
		public void change(Columns col, int row) {
			this.col = col;
			this.row = row;
		}
		public void shift(int dx, int dy) {
			this.col = Columns.values()[this.col.ordinal()+dx];
			this.row = this.row+dy;
		}
		public int getX() {
			return col.ordinal();
		}
		public int getY() {
			return row;
		}
		public static boolean isShiftInBounds(int dx, int dy, Coordinate base) {
			int x = base.col.ordinal();
			int y = base.row;
			
			if (x+dx < 0 || x+dx > 7) {
				return false;
			}
			if (y+dy < 0 || y+dy > 7) {
				return false;
			}
			return true;
		}
		public static boolean isSameCoordinate(Coordinate first, Coordinate second) {
			boolean x = false;
			boolean y = false;
			if (first.row == second.row)
				y = true;
			if (first.col == second.col)
				x = true;
			
			return x && y;
		}
	}
