package edu.brown.cs032.ltbarnes.stars.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs032.ltbarnes.stars.startree.Star;
import edu.brown.cs032.ltbarnes.stars.startree.StarTree;

public class StarTreeTest {

	@Test
	public void testAdd() {
		List<Star> stars = new ArrayList<>();
		stars.add(new Star("0", "", 0, 0, 0));
		stars.add(new Star("1", "", 282, 0.00449, 5));
		stars.add(new Star("2", "", 43, 0.00285, -15));
		stars.add(new Star("3", "", 277, 0.02422, 223));
		stars.add(new Star("4", "", 79, 0.01164, -101));
		stars.add(new Star("5", "", 264, 0.04601, -226));
		stars.add(new Star("6", "", 53, 0.0168, 3));
		stars.add(new Star("7", "", 52, 0.02084, 19));
		stars.add(new Star("8", "", 174, 0.08288, 84));
		stars.add(new Star("9", "", 166, 0.10297, 123));
		StarTree st = new StarTree(stars);
		assertEquals(
				"(9: L:(4: L:(0: L:(2: L:null R:null) R:null) R:(7: L:(6: L:null R:null) "
				+ "R:null)) R:(5: L:(3: L:(1: L:null R:null) R:null) R:(8: L:null R:null)))",
				st.toString());
	}

}
