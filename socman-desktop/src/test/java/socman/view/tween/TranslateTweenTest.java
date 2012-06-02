package socman.view.tween;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import socman.view.sprite.Sprite;


public class TranslateTweenTest {

	TranslateTween tween;
	Sprite sprite;
	Tween.Callback mockCallback;
	
	@Before
	public void setup() {
		mockCallback = mock(Tween.Callback.class);
		sprite = new Sprite(0, 0);
		tween = new TranslateTween(sprite, 1000, 1000, 1000, mockCallback);
	}
	
	@Test
	public void shouldFinishImmediately() {
		tween.update(1000);
		assertTrue(tween.isDisposed());
		assertEquals(1000f, sprite.getX(), 0);
		assertEquals(1000f, sprite.getY(), 0);
		verify(mockCallback, times(1)).tweenDisposed();
	}
	
	@Test
	public void shouldNotFinishImmediately() {
		tween.update(999);
		assertFalse(tween.isDisposed());
		assertEquals(999f, sprite.getX(), 0);
		assertEquals(999f, sprite.getY(), 0);
		verify(mockCallback, times(0)).tweenDisposed();
	}
	
	@Test
	public void shouldFinishWithFourUpdates() {
		for (int i=1; i<=4; i++) {
			assertFalse(tween.isDisposed());
			tween.update(250);
			assertEquals(i*250f, sprite.getX(), 0);
			assertEquals(i*250f, sprite.getY(), 0);
		}
		assertTrue(tween.isDisposed());
		verify(mockCallback, times(1)).tweenDisposed();
	}
}
