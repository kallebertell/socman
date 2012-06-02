package socman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import socman.model.Board;
import socman.model.BoardGenerator;

public class GameStarter {

	@SuppressWarnings("serial")
	private static final Map<String, Command> commandMap = new HashMap<String,Command>() {{
		put("q", Command.quit);
		put("l", Command.moveLeft);
		put("r", Command.moveRight);
		put("u", Command.moveUp);
		put("d", Command.moveDown);
	}};
	
	public static void main(String[] args) {
		Board board = BoardGenerator.generateRandomBoard(5, 4);
		BoardPrinter boardPrinter = new BoardPrinter(board, System.out);
		System.out.println("Welcome to the Adventures of Separation of Concerns-Man!");

		while (true) {
			boardPrinter.print();
			System.out.println("Enter next move: (l)eft, (r)ight, (u)p, (d)own or (q)uit > ");
			getNextCommand().doCommand(board);
		}
		
	}
	
	
	private static Command getNextCommand() {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String readLine = bufferedReader.readLine().trim().toLowerCase();
			return parseCommand(readLine);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Command parseCommand(String readLine) {
        if (!commandMap.containsKey(readLine)) {
        	return Command.noOp;
        }
        
		return commandMap.get(readLine);
	}
}
