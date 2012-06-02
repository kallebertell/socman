package socman.view;

import java.util.List;

import javax.swing.JFrame;

import socman.model.Board;
import socman.model.Board.ChangeListener;
import socman.model.action.Action;
import socman.model.action.Callback;
import socman.model.gameobject.GameObject;
import socman.view.sprite.Sprite;
import socman.view.sprite.SpriteFactory;

/**
 * View implementation.
 *
 */
public class GameWindow extends JFrame implements View {
	private static final long serialVersionUID = 1L;

	private BoardCanvas boardCanvas;
	private ActionAnimator actionAnimator;
		
	private int scale;
	
	public GameWindow(int scale, Board board) {
		super("SocMan");
		
		this.scale = scale;
		int width = board.getWidth();
		int height = board.getHeight();
		
		this.setSize(width*scale, height*scale+20);
		
		this.boardCanvas = new BoardCanvas(board, scale);
		getContentPane().add(boardCanvas);
		setVisible(true);
		
		this.actionAnimator = new ActionAnimator(boardCanvas);
		
		board.setChangeListener(new ChangeListener() {
			@Override public void removed(GameObject gameOb) {
				boardCanvas.removeGameObjectSprite(gameOb);
			}

			@Override public void added(GameObject gameOb) {
				addSpriteForGameObject(gameOb);
			}
		});
		
		setupSprites(board);
	}
	

	private void setupSprites(Board board) {
		List<GameObject> gameObjects = board.getGameObjects();
		for (GameObject gameObject : gameObjects) {
			addSpriteForGameObject(gameObject);
		}
	}
	
	private void addSpriteForGameObject(GameObject gameObject) {
		Sprite sprite = SpriteFactory.createSprite(gameObject, scale);
		boardCanvas.addGameObjectSprite(gameObject, sprite);
	}

	@Override
	public void executeAction(final Action action, final Callback callback) {		
		actionAnimator.executeAction(action, callback);
	}

	@Override
	public void draw() {
		boardCanvas.repaint();
	}

	@Override
	public void update(long timePassed) {
		boardCanvas.update(timePassed);
		actionAnimator.update(timePassed);
	}


}
