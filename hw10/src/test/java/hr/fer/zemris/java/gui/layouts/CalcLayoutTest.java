package hr.fer.zemris.java.gui.layouts;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Test;


public class CalcLayoutTest {

	@Test
	public void checkDimension1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		Assert.assertEquals(dim.width, 152,0.001);
		Assert.assertEquals(dim.height, 158,0.001);
	}
	
	@Test
	public void checkDimension2() {
	JPanel p = new JPanel(new CalcLayout(2));
	JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
	JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
	p.add(l1, new RCPosition(1,1));
	p.add(l2, new RCPosition(3,3));
	Dimension dim = p.getPreferredSize();
	Assert.assertEquals(dim.width, 152,0.001);
	Assert.assertEquals(dim.height, 158,0.001);
	}
}
