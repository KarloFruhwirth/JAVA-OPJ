package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Vector2DTest {

	@Test
	public void TestVectorGetters() {
		Vector2D v = new Vector2D(0, 2);
		double x = v.getX();
		double y = v.getY();
		assertEquals(x, 0, 0.0001);
		assertEquals(y, 2, 0.0001);
	}

	@Test
	public void TestTranslate() {
		Vector2D v = new Vector2D(0, 2);
		Vector2D offset = new Vector2D(2, 0);
		v.translate(offset);
		double x = v.getX();
		double y = v.getY();
		assertEquals(x, 2, 0.0001);
		assertEquals(y, 2, 0.0001);
	}

	@Test
	public void TestTranslated() {
		Vector2D v = new Vector2D(0, 2);
		Vector2D offset = new Vector2D(2, 0);
		Vector2D v2 = v.translated(offset);
		double x = v.getX();
		double y = v.getY();
		assertEquals(x, 0, 0.0001);
		assertEquals(y, 2, 0.0001);
		double x2 = v2.getX();
		double y2 = v2.getY();
		assertEquals(x2, 2, 0.0001);
		assertEquals(y2, 2, 0.0001);
	}

	@Test
	public void TestRotate() {
		Vector2D v = new Vector2D(2, 2);
		double angle = -45;
		v.rotate(angle);
		double x = v.getX();
		double y = v.getY();
		assertEquals(x, Math.sqrt(8), 0.0001);
		assertEquals(y, 0, 0.0001);
	}

	@Test
	public void TestRotated() {
		Vector2D v = new Vector2D(2, 2);
		double angle = -45;
		Vector2D v2 = v.rotated(angle);
		double x = v2.getX();
		double y = v2.getY();
		assertEquals(x, Math.sqrt(8), 0.0001);
		assertEquals(y, 0, 0.0001);
	}

	@Test
	public void TestScale() {
		Vector2D v = new Vector2D(2, 2);
		double scaler = 0.5;
		v.scale(scaler);
		double x = v.getX();
		double y = v.getY();
		assertEquals(x, 1, 0.0001);
		assertEquals(y, 1, 0.0001);
	}

	@Test
	public void TestScaled() {
		Vector2D v = new Vector2D(2, 2);
		double scaler = 0.5;
		Vector2D v2 = v.scaled(scaler);
		double x = v2.getX();
		double y = v2.getY();
		assertEquals(x, 1, 0.0001);
		assertEquals(y, 1, 0.0001);
	}
	
	@Test
	public void TestCopy() {
		Vector2D v1 = new Vector2D(2, 2);
		Vector2D v2 = v1.copy();
		double x1 = v1.getX();
		double y1 = v1.getY();
		double x2 = v2.getX();
		double y2 = v2.getY();
		assertEquals(x1, x2, 0.0001);
		assertEquals(y1, y2, 0.0001);
	}
}
