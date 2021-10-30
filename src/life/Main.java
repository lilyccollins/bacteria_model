package life;

public class Main {

	public static void main(String[] args) {
		
		Life game = new Life("griddata//life100.txt");
		System.out.println(game.toString() + "\n\n\n\n");
		game.step(5);
		System.out.println(game.toString());
		System.out.println("Number in row 10:       " + game.getNumInRow(10));
		System.out.println("Number in column 10:    " + game.getNumInCol(10));
		System.out.println("Total number:           " + game.getNumOfCells());

	}

}
