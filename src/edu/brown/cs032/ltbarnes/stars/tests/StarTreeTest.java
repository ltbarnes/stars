package edu.brown.cs032.ltbarnes.stars.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs032.ltbarnes.stars.startree.Star;
import edu.brown.cs032.ltbarnes.stars.startree.StarTree;

public class StarTreeTest {

	@Test
	public void testBuildTree() {
		List<Star> stars = new ArrayList<>();
		stars.add(new Star("0", "", -3, -1, 0));
		stars.add(new Star("1", "", -2, 0, 0));
		stars.add(new Star("2", "", -1, 1, 0));
		stars.add(new Star("3", "", 0, 0, 0));
		stars.add(new Star("4", "", 1, -1, 0));
		stars.add(new Star("5", "", 2, 0, 0));
		stars.add(new Star("6", "", 3, 1, 0));
		StarTree st = new StarTree(stars);
		assertEquals(
				"(3: L:(1: L:(0: L:null R:null) R:(2: L:null R:null)) "
				+ "R:(5: L:(4: L:null R:null) R:(6: L:null R:null)))",
				st.toString());
		
		stars.clear();
		stars.add(new Star("0", "",-11, -5, -2));
		stars.add(new Star("1", "",-10, -4, -1));
		stars.add(new Star("2", "", -9, -3, 0));
		stars.add(new Star("3", "", -8, -2, 1));
		stars.add(new Star("4", "", -7, -1, 2));
		stars.add(new Star("5", "", -6, 0, 0));
		stars.add(new Star("6", "", -5, 1, -2));
		stars.add(new Star("7", "", -4, 2, -1));
		stars.add(new Star("8", "", -3, 3, 0));
		stars.add(new Star("9", "", -2, 4, 1));
		stars.add(new Star("10", "", -1, 5, 2));
		stars.add(new Star("11", "", 0, 0, 0));
		stars.add(new Star("12", "", 1, -5, -2));
		stars.add(new Star("13", "", 2, -4, -1));
		stars.add(new Star("14", "", 3, -3, 0));
		stars.add(new Star("15", "", 4, -2, 1));
		stars.add(new Star("16", "", 5, 1, 2));
		stars.add(new Star("17", "", 6, 0, 0));
		stars.add(new Star("18", "", 7, 1, -2));
		stars.add(new Star("19", "", 8, 2, -1));
		stars.add(new Star("20", "", 9, 3, 0));
		stars.add(new Star("21", "",10, 4, 1));
		stars.add(new Star("22", "",11, 5, 2));
		st = new StarTree(stars);
		assertEquals(
				"(11: L:(5: L:(2: L:(0: L:null R:(1: L:null R:null)) R:(3: L:null R:(4: L:null R:null)))"
				+ " R:(8: L:(6: L:null R:(7: L:null R:null)) R:(9: L:null R:(10: L:null R:null))))) "
				+ "R:(17: L:(14: L:(12: L:null R:(13: L:null R:null)) R:(15: L:null R:(16: L:null R:null)))"
				+ " R:(20: L:(18: L:null R:(19: L:null R:null)) R:(21: L:null R:(22: L:null R:null)))))",
				st.toString());
		
		stars.clear();
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
		st = new StarTree(stars);
		assertEquals(
				"(9: L:(4: L:(0: L:(2: L:null R:null) R:null) R:(7: L:(6: L:null R:null) "
				+ "R:null)) R:(5: L:(3: L:(1: L:null R:null) R:null) R:(8: L:null R:null)))",
				st.toString());
	}
	
	@Test
	public void testAdd() {
		
	}

}
