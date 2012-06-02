package socman;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import socman.model.Board;
import socman.model.Direction;

public class BoardPrinterTest {

	String boardStr;
	
	Board board;
	BoardPrinter printer;
	PrintStream mockPrintStream;
	
	@Before
	public void setup() {
		board = new Board(3, 3); 
		mockPrintStream = mock(PrintStream.class);
		printer = new BoardPrinter(board, mockPrintStream);
		boardStr = 
			"+-----+\n" +
			"|    1|\n" +
			"| + + |\n" +
			"|   5 |\n" +
			"| +4+ |\n" +
			"| 3  2|\n" +
			"+-----+\n";
	}
	
	private String emptyBoardStr() {
		return getBoardStrWithoutMarkers(1,2,3,4,5);
	}
	
	private String getBoardStrWithoutMarkers(int... markers) {
		String newBoardStr = boardStr;
		for (int marker : markers) {
			newBoardStr = newBoardStr.replace(""+marker, " ");
		}
		return newBoardStr;
	}
	
	@Test
	public void shouldPrintEmptyBoard() {
		String expectedBoardStr = emptyBoardStr();
		printer.print();
		verify(mockPrintStream, times(1)).println(expectedBoardStr);
	}
	
	@Test
	public void shouldPrintSocmanInUpperRightCorner() {
		String expectedBoardStr = getBoardStrWithoutMarkers(2,3,4,5).replace("1", "S");
		board.addSocman(2, 0);
		printer.print();
		verify(mockPrintStream, times(1)).println(expectedBoardStr);
	}
	
	@Test
	public void shouldPrintPillInDownRightCorner() {
		String expectedBoardStr = getBoardStrWithoutMarkers(1,3,4,5).replace("2", "P");
		board.addPill(2, 2);
		printer.print();
		verify(mockPrintStream, times(1)).println(expectedBoardStr);
	}
	
	@Test
	public void shouldPrintWalls() {
		String expectedBoardStr = getBoardStrWithoutMarkers(1,2,3).replace("4", "-").replace("5", "|");
		board.addWall(1, 1, Direction.DOWN);
		board.addWall(1, 1, Direction.RIGHT);
		printer.print();
		verify(mockPrintStream, times(1)).println(expectedBoardStr);		
	}
}
