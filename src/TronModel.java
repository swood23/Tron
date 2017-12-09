import java.awt.Color;

//the problem is here
public class TronModel {
	private Color[][] board;

	private Player player1;
	private Player player2;

	boolean gameOver;
	Color winner;

	public final int NORTH = 0;
	public final int EAST = 1;
	public final int SOUTH = 2;
	public final int WEST = 3;

	// Creates the board and sets the starting position for the players
	public TronModel(int width) {
		board = new Color[width][width];
		
		int midpoint = width / 2;
		int player1Start = midpoint - (midpoint / 2);
		int player2Start = midpoint + (midpoint / 2);
		
		player1 = new Player(StdDraw.YELLOW, player1Start, midpoint, EAST);
		player2 = new Player(StdDraw.BLUE, player2Start, midpoint, WEST);
		// updateBoard();
		gameOver = false;
		winner = null;
	}

	public void updateBoard() {
		board[player1.getX()][player1.getY()] = player1.getColor();
		board[player2.getX()][player2.getY()] = player2.getColor();
	}

	// getter for board
	public Color[][] getBoard() {
		return board;
	}

	// checks to see if location is empty
	public boolean isEmpty(int x, int y) {
		if (board[x][y] == null) {
			return true;
		} else
			return false;
	}

	// checks to see if game is over
	public boolean isGameOver() {
		return gameOver;
	}

	// keep returning null if nobody has won yet, otherwise return player that has
	// won
	public Color getWinner() {
		return winner;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer1Direction(int direction) {
		//stops player from committing suicide: any direction thats 2 away from the
		//integer value of the current direction is the opposite of the current direction,
		//and therefore illegal
		if(Math.abs(direction - player1.getDirection()) != 2){
			player1.setDirection(direction);
		}
	}

	public void setPlayer2Direction(int direction) {
		if(Math.abs(direction - player2.getDirection()) != 2){
			player2.setDirection(direction);
		}
	}

	public void movePlayers() {
		// Find destinations
		Location destination1;
		Location destination2;

		int direction = player1.getDirection();
		if (direction == WEST) {
			destination1 = new Location(player1.getX() - 1, player1.getY());
		} else if (direction == NORTH) {
			destination1 = new Location(player1.getX(), player1.getY() + 1);
		} else if (direction == EAST) {
			destination1 = new Location(player1.getX() + 1, player1.getY());
		}
		// Direction is south
		else {
			destination1 = new Location(player1.getX(), player1.getY() - 1);
		}

		direction = player2.getDirection();
		if (direction == WEST) {
			destination2 = new Location(player2.getX() - 1, player2.getY());
		} else if (direction == NORTH) {
			destination2 = new Location(player2.getX(), player2.getY() + 1);
		} else if (direction == EAST) {
			destination2 = new Location(player2.getX() + 1, player2.getY());
		}
		// Direction is south
		else {
			destination2 = new Location(player2.getX(), player2.getY() - 1);
		}

		// Tests for collisions
		if(destination1.getX() == destination2.getX() && destination1.getY() == destination2.getY()){
			gameOver = true;
			winner = null;
			System.out.println("draw game!");
		}
		else if (destination1.getY() >= board.length || destination1.getX() >= board.length
				|| destination1.getY() <= 0 || destination1.getX() <= 0) {
			gameOver = true;
			winner = player2.getColor();
			System.out.println("Player 1 ran into wall at " + destination1);
		} else if (destination2.getY() >= board.length || destination2.getX() >= board.length
				|| destination2.getY() <= 0 || destination2.getX() <= 0) {
			gameOver = true;
			winner = player1.getColor();
			System.out.println("Player 2 ran into wall at " + destination2);
		} else if (board[destination1.getY()][destination1.getX()] != null) {
			gameOver = true;
			winner = player2.getColor();
			System.out.println("Player 1 ran into light trail at " + destination1);
		} else if (board[destination2.getY()][destination2.getX()] != null) {
			gameOver = true;
			winner = player1.getColor();
			System.out.println("Player 2 ran into light trail at " + destination2);
		}

		if (!gameOver) {
			player1.setX(destination1.getY());
			player1.setY(destination1.getX());

			player2.setX(destination2.getY());
			player2.setY(destination2.getX());

			System.out.println("Player 1 at (" + destination1 + ")");
			System.out.println("Player 2 at (" + destination2 + ")");

			updateBoard();
		}
	}

	public Color getWinnerColor() {
		return winner;
	}

	//Accessors for tests
	//Accessors and Modifiers for tests
	public void setColor(int x, int y, Color color){
		board[y][x] = color;
	}

}
