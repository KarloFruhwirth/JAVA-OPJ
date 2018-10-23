package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector3Test {

	@Test
	public void checkX() {
		Vector3 v1 = new Vector3(2, 3, 4);
		Assert.assertEquals(2, v1.getX(), 0.001);
	}

	@Test
	public void checkY() {
		Vector3 v1 = new Vector3(2, 3, 4);
		Assert.assertEquals(3, v1.getY(), 0.001);
	}

	@Test
	public void checkZ() {
		Vector3 v1 = new Vector3(2, 3, 4);
		Assert.assertEquals(4, v1.getZ(), 0.001);
	}

	@Test
	public void checkNorm() {
		Vector3 v1 = new Vector3(2, 3, 4);
		Assert.assertEquals(Math.sqrt(29), v1.norm(), 0.001);
	}

	@Test
	public void checkNormalized() {
		Vector3 v1 = new Vector3(3, 1, 2);
		double norm = v1.norm();
		Assert.assertEquals(new Vector3(3 / norm, 1 / norm, 2 / norm), v1.normalized());
	}

	@Test
	public void checkAdd() {
		Vector3 v1 = new Vector3(3, 1, 2);
		Assert.assertEquals(new Vector3(4, 4, 4), v1.add(new Vector3(1, 3, 2)));
	}

	@Test
	public void checkSub() {
		Vector3 v1 = new Vector3(3, 1, 2);
		Assert.assertEquals(new Vector3(2, -2, 0), v1.sub(new Vector3(1, 3, 2)));
	}

	@Test
	public void checkDot() {
		Vector3 v1 = new Vector3(1, 2, 3);
		Vector3 v2 = new Vector3(3, 2, 5);
		Assert.assertEquals(22, v1.dot(v2), 0.001);
	}

	@Test
	public void checkCross() {
		Vector3 v1 = new Vector3(1, 2, 3);
		Vector3 v2 = new Vector3(3, 2, 5);
		Assert.assertEquals(new Vector3(4, 4, -4), v1.cross(v2));
	}

	@Test
	public void checkScale() {
		Vector3 v1 = new Vector3(1, 2, 3);
		Assert.assertEquals(new Vector3(3, 6, 9), v1.scale(3));
	}

	@Test
	public void checkAngle() {
		Vector3 v1 = new Vector3(1, 2, 3);
		Vector3 v2 = new Vector3(3, 2, 5);
		Assert.assertEquals(0.953820966476532, v1.cosAngle(v2), 0.001);
	}
}
