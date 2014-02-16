package edu.brown.cs032.ltbarnes.stars.startree;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

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
		assertEquals("(3: L:(1: L:(0: L:null R:null) R:(2: L:null R:null)) "
				+ "R:(5: L:(4: L:null R:null) R:(6: L:null R:null)))", st.toString());

		stars.clear();
		stars.add(new Star("0", "", -3, -1, 0));
		stars.add(new Star("1", "", -2, 0, 0));
		stars.add(new Star("2", "", -1, 1, 0));
		stars.add(new Star("3", "", 0, 0, 0));
		stars.add(new Star("4", "", 1, -1, 0));
		stars.add(new Star("5", "", 2, 0, 0));
		stars.add(new Star("6", "", 3, 1, 0));
		st = new StarTree(stars);
		assertEquals("(3: L:(1: L:(0: L:null R:null) R:(2: L:null R:null)) "
				+ "R:(5: L:(4: L:null R:null) R:(6: L:null R:null)))", st.toString());

		stars.clear();
		stars.add(new Star("0", "", -11, -5, -2));
		stars.add(new Star("1", "", -10, -4, -1));
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
		stars.add(new Star("16", "", 5, -1, 2));
		stars.add(new Star("17", "", 6, 0, 0));
		stars.add(new Star("18", "", 7, 1, -2));
		stars.add(new Star("19", "", 8, 2, -1));
		stars.add(new Star("20", "", 9, 3, 0));
		stars.add(new Star("21", "", 10, 4, 1));
		stars.add(new Star("22", "", 11, 5, 2));
		st = new StarTree(stars);
		assertEquals("(11: L:(5: L:(2: L:(1: L:(0: L:null R:null) R:null) R:(4: L:(3: L:null R:null) R:null))"
				+ " R:(8: L:(7: L:(6: L:null R:null) R:null) R:(10: L:(9: L:null R:null) R:null))) "
				+ "R:(17: L:(14: L:(13: L:(12: L:null R:null) R:null) R:(16: L:(15: L:null R:null) R:null))"
				+ " R:(20: L:(19: L:(18: L:null R:null) R:null) R:(22: L:(21: L:null R:null) R:null))))", st.toString());

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
		assertEquals("(9: L:(4: L:(0: L:(2: L:null R:null) R:null) R:(7: L:(6: L:null R:null) "
				+ "R:null)) R:(5: L:(3: L:(1: L:null R:null) R:null) R:(8: L:null R:null)))", st.toString());
	}

	@Test
	public void testAdd() {
		List<Star> stars = new ArrayList<>();
		stars.add(new Star("0", "", -3, -1, 0));
		stars.add(new Star("1", "", -2, 0, 0));
		stars.add(new Star("2", "", -1, 1, 0));
		stars.add(new Star("3", "", 0, 0, 0));
		stars.add(new Star("4", "", 1, -1, 0));
		stars.add(new Star("5", "", 2, 0, 0));
		stars.add(new Star("6", "", 3, 1, 0));
		StarTree st = new StarTree(stars);
		st.addElement(new Star("7", "", 3, 1, 1));
		assertEquals("(3: L:(1: L:(0: L:null R:null) R:(2: L:null R:null)) "
				+ "R:(5: L:(4: L:null R:null) R:(6: L:null R:(7: L:null R:null))))", st.toString());
		st.addElement(new Star("8", "", -7, 4, -1));
		assertEquals("(3: L:(1: L:(0: L:null R:null) R:(2: L:(8: L:null R:null) R:null)) "
				+ "R:(5: L:(4: L:null R:null) R:(6: L:null R:(7: L:null R:null))))", st.toString());

	}

	@Test
	public void testStarDist2() {
		assertEquals(3, StarTree.starDist2(new Star("", "", 1, 1, 1), new Star("", "", 0, 0, 0)), 1e-12);
		assertEquals(3, StarTree.starDist2(new Star("", "", 0, 0, 0), new Star("", "", 1, 1, 1)), 1e-12);
		assertEquals(9, StarTree.starDist2(new Star("", "", 1, 1, -2), new Star("", "", 1, 1, 1)), 1e-12);
		assertEquals(9, StarTree.starDist2(new Star("", "", 1, 1, 1), new Star("", "", 1, 1, -2)), 1e-12);
	}

	@Test
	public void testDist2Plane() {
		StarNode star = new StarNode(new Star("", "", 1, 1, 1));
		StarNode current = new StarNode(new Star("", "", 0, -4, 7),0);
		int dim = current.getDepth() % 3;
		assertEquals(0, dim);
		Star s = star.getStar();
		Double[] coords = new Double[3];
		s.coordinates.toArray(coords);
		coords[dim] = current.getDimension(dim);
		Double[] correct = {0.0, 1.0, 1.0};
		assertArrayEquals(correct, coords);
		assertEquals(1, StarTree.starDist2(new Star("", "", coords[0], coords[1], coords[2]), s), 1e-12);
		
	}

	@Test
	public void testFindNearestSingle() {
		List<Star> stars = new ArrayList<>();
		Star s00 = new Star("00", "", -1, 0, 0);
		Star s11 = new Star("11", "", 0, 0, 0);
		Star s22 = new Star("22", "", 1, 0, 0);

		stars.add(s00);
		stars.add(s11);
		stars.add(s22);

		StarTree st = new StarTree(stars);

		assertEquals("(11: L:(00: L:null R:null) R:(22: L:null R:null))", st.toString());

		Set<Star> starSet = new HashSet<>();
		starSet.add(s11);
		assertEquals(starSet, st.kNNSearch(new Star("", "", 0, 0, 1), 1));
		starSet.clear();
		starSet.add(s22);
		assertEquals(starSet, st.kNNSearch(new Star("", "", 3, 0, 2), 1));

		stars.clear();
		Star s0 = new Star("0", "", -5, -2, 0);
		Star s1 = new Star("1", "", -4, -1, 0);
		Star s2 = new Star("2", "", -3, 0, 0);
		Star s3 = new Star("3", "", -2, 1, 0);
		Star s4 = new Star("4", "", -1, 2, 0);
		Star s5 = new Star("5", "", 0, 0, 0);
		Star s6 = new Star("6", "", 1, -2, 0);
		Star s7 = new Star("7", "", 2, -1, 0);
		Star s8 = new Star("8", "", 3, 0, 0);
		Star s9 = new Star("9", "", 4, 1, 0);
		Star s10 = new Star("10", "", 5, 2, 0);

		stars.add(s0);
		stars.add(s1);
		stars.add(s2);
		stars.add(s3);
		stars.add(s4);
		stars.add(s5);
		stars.add(s6);
		stars.add(s7);
		stars.add(s8);
		stars.add(s9);
		stars.add(s10);

		st = new StarTree(stars);

		starSet.clear();
		starSet.add(s8);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 3, 0, 2), 1));
		starSet.clear();
		starSet.add(s6);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 1, -5, -1), 1));
		starSet.clear();
		starSet.add(s4);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 1, 5, -1), 1));
		starSet.clear();
		starSet.add(s5);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 0, 0, -7), 1));
		starSet.clear();
		starSet.add(s5);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", -1, -1, 0), 1));
		starSet.clear();
		starSet.add(s7);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 4, -2, 1), 1));
		starSet.clear();
		starSet.add(s9);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 5, 0, 0), 1));
		starSet.clear();
		starSet.add(s0);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", -80, 0, 0), 1));

	}

	@Test
	public void testFindNearestMultiple() {
		List<Star> stars = new ArrayList<>();
		Star s0 = new Star("0", "", -5, -2, 0);
		Star s1 = new Star("1", "", -4, -1, 0);
		Star s2 = new Star("2", "", -3, 0, 0);
		Star s3 = new Star("3", "", -2, 1, 0);
		Star s4 = new Star("4", "", -1, 2, 0);
		Star s5 = new Star("5", "", 0, 0, 0);
		Star s6 = new Star("6", "", 1, -2, 0);
		Star s7 = new Star("7", "", 2, -1, 0);
		Star s8 = new Star("8", "", 3, 0, 0);
		Star s9 = new Star("9", "", 4, 1, 0);
		Star s10 = new Star("10", "", 5, 2, 0);

		stars.add(s0);
		stars.add(s1);
		stars.add(s2);
		stars.add(s3);
		stars.add(s4);
		stars.add(s5);
		stars.add(s6);
		stars.add(s7);
		stars.add(s8);
		stars.add(s9);
		stars.add(s10);

		StarTree st = new StarTree(stars);

		Set<Star> starSet = new HashSet<>();
		addStarsToSet(starSet, s8, s7, s6);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 3, 0, 2), 3));
		starSet.clear();
		addStarsToSet(starSet, s6, s7, s5, s8);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 1, 5, -1), 4));
		starSet.clear();
		addStarsToSet(starSet, s5, s6, s4, s3, s7);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 0, 0, -7), 5));
		starSet.clear();
		addStarsToSet(starSet, s5, s6, s3, s2, s7, s4);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", -1, 1, 0), 6));
		starSet.clear();
		addStarsToSet(starSet, s8, s9, s7, s6, s10);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 4, 2, 1), 5));
		starSet.clear();
		addStarsToSet(starSet, s0, s10, s8, s7, s6, s5);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", 5, 0, 0), 6));
		starSet.clear();
		addStarsToSet(starSet, s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10);
		assertEquals(starSet, st.kNNSearch(new Star("11", "", -80, 0, 0), 11));

	}

	public static void addStarsToSet(Collection<Star> c, Star... strs) {
		for (Star str : strs)
			c.add(str);
	}

}
