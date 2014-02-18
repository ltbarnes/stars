package edu.brown.cs032.ltbarnes.stars.startree;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import edu.brown.cs032.ltbarnes.kdtree.GenericKDTree;
import edu.brown.cs032.ltbarnes.kdtree.KDTree;
import edu.brown.cs032.ltbarnes.stars.StarsMain;

public class StarTreeTest {

	@Test
	public void testStarsEqual() {
		assertTrue(new Star("0", "", -5, -2, 0).equals(new Star("0", "", -5, -2, 0)));
		assertEquals(new Star("0", "", -5, -2, 0), new Star("0", "", -5, -2, 0));
		assertEquals(new StarNode(new Star("1823", "Blah", 2, 1, 2)), new StarNode(new Star("1823", "Blah", 2, 1, 2)));
	}

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
		KDTree<Star> st = new StarTree(stars);
		assertEquals("(3: L:(1: L:(0: L:null R:null) R:(2: L:null R:null)) "
				+ "R:(5: L:(4: L:null R:null) R:(6: L:null R:null)))", st.toString());
		assertEquals(7, st.size());

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
		assertEquals(7, st.size());

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
		assertEquals(23, st.size());

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
		assertEquals(10, st.size());
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
		KDTree<Star> st = new StarTree(stars);
		assertEquals(7, st.size());

		st.addElement(new Star("7", "", 3, 1, 1));
		assertEquals("(3: L:(1: L:(0: L:null R:null) R:(2: L:null R:null)) "
				+ "R:(5: L:(4: L:null R:null) R:(6: L:null R:(7: L:null R:null))))", st.toString());
		assertEquals(8, st.size());

		st.addElement(new Star("8", "", -7, 4, -1));
		assertEquals("(3: L:(1: L:(0: L:null R:null) R:(2: L:(8: L:null R:null) R:null)) "
				+ "R:(5: L:(4: L:null R:null) R:(6: L:null R:(7: L:null R:null))))", st.toString());
		assertEquals(9, st.size());

	}

	@Test
	public void testIterator() {
		List<Star> stars = new ArrayList<>();
		stars.add(new Star("0", "", -3, -1, 0));
		stars.add(new Star("1", "", -2, 0, 0));
		stars.add(new Star("2", "", -1, 1, 0));
		stars.add(new Star("3", "", 0, 0, 0));
		stars.add(new Star("4", "", 1, -1, 0));
		stars.add(new Star("5", "", 2, 0, 0));
		stars.add(new Star("6", "", 3, 1, 0));

		KDTree<Star> st = new StarTree(stars);

		int i = 0;
		for (Star s : st) {
			assertEquals(stars.get(i), s);
			i++;
		}
		 assertEquals(i, st.size());

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
		stars.add(new Star("21", "", -10, 4, 1));
		stars.add(new Star("22", "", -11, 5, 2));
		st = new StarTree(stars);

		i = 0;
		for (Star s : st) {
			assertEquals(stars.get(i), s);
			i++;
		}
		 assertEquals(i, st.size());
	}

	@Test
	public void testStarDist2() {
		assertEquals(3, GenericKDTree.pointDist2(new Star("", "", 1, 1, 1), new Star("", "", 0, 0, 0)), 1e-12);
		assertEquals(3, GenericKDTree.pointDist2(new Star("", "", 0, 0, 0), new Star("", "", 1, 1, 1)), 1e-12);
		assertEquals(9, GenericKDTree.pointDist2(new Star("", "", 1, 1, -2), new Star("", "", 1, 1, 1)), 1e-12);
		assertEquals(9, GenericKDTree.pointDist2(new Star("", "", 1, 1, 1), new Star("", "", 1, 1, -2)), 1e-12);
	}

	@Test
	public void testDist2Plane() {
		StarNode star = new StarNode(new Star("", "", 1, 1, 1));
		StarNode current = new StarNode(new Star("", "", 0, -4, 7), 0);
		int dim = current.getDepth() % 3;
		assertEquals(0, dim);
		Star s = star.getStar();
		Double[] coords = new Double[3];
		s.coordinates.toArray(coords);
		coords[dim] = current.getDimension(dim);
		Double[] correct = { 0.0, 1.0, 1.0 };
		assertArrayEquals(correct, coords);
		assertEquals(1, GenericKDTree.pointDist2(new Star("", "", coords[0], coords[1], coords[2]), s), 1e-12);

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

		KDTree<Star> st = new StarTree(stars);

		assertEquals("(11: L:(00: L:null R:null) R:(22: L:null R:null))", st.toString());

		List<Star> starList = new ArrayList<>();
		starList.add(s11);
		assertEquals(starList, st.kNNSearch(new Star("", "", 0, 0, 1), 1));
		starList.clear();
		starList.add(s22);
		assertEquals(starList, st.kNNSearch(new Star("", "", 3, 0, 2), 1));

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

		starList.clear();
		starList.add(s8);
		assertEquals(starList, st.kNNSearch(new Star("", "", 3, 0, 2), 1));
		starList.clear();
		starList.add(s6);
		assertEquals(starList, st.kNNSearch(new Star("", "", 1, -5, -1), 1));
		starList.clear();
		starList.add(s4);
		assertEquals(starList, st.kNNSearch(new Star("", "", 1, 5, -1), 1));
		starList.clear();
		starList.add(s5);
		assertEquals(starList, st.kNNSearch(new Star("", "", 0, 0, -7), 1));
		starList.clear();
		starList.add(s5);
		assertEquals(starList, st.kNNSearch(new Star("", "", -1, -1, 0), 1));
		starList.clear();
		starList.add(s7);
		assertEquals(starList, st.kNNSearch(new Star("", "", 4, -2, 1), 1));
		starList.clear();
		starList.add(s9);
		assertEquals(starList, st.kNNSearch(new Star("", "", 5, 0, 0), 1));
		starList.clear();
		starList.add(s0);
		assertEquals(starList, st.kNNSearch(new Star("", "", -80, 0, 0), 1));

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

		KDTree<Star> st = new StarTree(stars);

		List<Star> starList = new ArrayList<>();
		addStarsToSet(starList, s8, s7, s9);
		assertEquals(starList, st.kNNSearch(new Star("", "", 3, 0, 2), 3));
		starList.clear();
		addStarsToSet(starList, s6, s7, s5, s8);
		assertEquals(starList, st.kNNSearch(new Star("", "", 1, -5, -1), 4));
		starList.clear();
		addStarsToSet(starList, s4, s9, s10, s3, s5);
		assertEquals(starList, st.kNNSearch(new Star("", "", 1, 5, -1), 5));
		starList.clear();
		addStarsToSet(starList, s5, s3, s4, s6, s7);
		assertEquals(starList, st.kNNSearch(new Star("", "", 0, 0, -7), 5));
		starList.clear();
		addStarsToSet(starList, s5, s3, s4, s6, s7, s2, s8);
		assertEquals(starList, st.kNNSearch(new Star("", "", 0, 0, -7), 7));
		starList.clear();
		addStarsToSet(starList, s5, s3, s4, s6, s7, s2, s8, s1, s9);
		assertEquals(starList, st.kNNSearch(new Star("", "", 0, 0, -7), 9));
		starList.clear();
		addStarsToSet(starList, s5, s2, s3, s6, s1, s4);
		assertEquals(starList, st.kNNSearch(new Star("", "", -1, -1, 0), 6));
		starList.clear();
		addStarsToSet(starList, s5, s2, s3, s6, s1, s4, s7);
		assertEquals(starList, st.kNNSearch(new Star("", "", -1, -1, 0), 7));
		starList.clear();
		addStarsToSet(starList, s7, s8, s6, s9, s10);
		assertEquals(starList, st.kNNSearch(new Star("", "", 4, -2, 1), 5));
		starList.clear();
		addStarsToSet(starList, s9, s8, s10, s7, s6, s5);
		assertEquals(starList, st.kNNSearch(new Star("", "", 5, 0, 0), 6));
		starList.clear();
		addStarsToSet(starList, s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10);
		assertEquals(starList, st.kNNSearch(new Star("", "", -80, 0, 0), 11));
	}

	@Test
	public void testFindNearestMultipleWithStars() {
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

		KDTree<Star> st = new StarTree(stars);

		List<Star> starList = new ArrayList<>();
		addStarsToSet(starList, s1, s2, s3, s5, s4);
		assertEquals(starList, st.kNNSearch(new Star("0", "", -5, -2, 0), 5));
		starList.clear();
		addStarsToSet(starList, s4, s2, s5, s1);
		assertEquals(starList, st.kNNSearch(new Star("3", "", -2, 1, 0), 4));
		starList.clear();
		addStarsToSet(starList, s3);
		assertEquals(starList, st.kNNSearch(new Star("5", "", 0, 0, 0), 1));
		starList.clear();
		addStarsToSet(starList, s7, s9, s6);
		assertEquals(starList, st.kNNSearch(new Star("8", "", 3, 0, 0), 3));
		starList.clear();
		addStarsToSet(starList, s10, s8, s7, s5, s6, s4, s3, s2, s1, s0);
		assertEquals(starList, st.kNNSearch(new Star("9", "", 4, 1, 0), 100));
	}

	@Test
	public void testFindNearestWithRadius() {
		List<Star> stars;
		String[] args = { "data/startest.csv" };
		if ((stars = StarsMain.parseInput(args)) == null)
			return;

		KDTree<Star> st = new StarTree(stars);

		Star s0 = new Star("0", "Sol", 0, 0, 0);
		Star s1 = new Star("1", "", 282.43485, 0.00449, 5.36884);
		Star s2 = new Star("2", "", 43.04329, 0.00285, -15.24144);
		Star s3 = new Star("3", "", 277.11358, 0.02422, 223.27753);
		Star s4 = new Star("4", "", 79.62896, 0.01164, -101.53103);
		Star s5 = new Star("5", "", 264.58918, 0.04601, -226.71007);
		Star s6 = new Star("6", "", 53.06535, 0.0168, 3.66089);
		Star s7 = new Star("7", "", 52.95794, 0.02084, 19.31343);
		Star s8 = new Star("8", "", 174.01562, 0.08288, 84.44669);
		Star s9 = new Star("9", "", 166.9363, 0.10297, 123.9143);

		List<Star> starList = new ArrayList<>();
		addStarsToSet(starList, s0, s2, s6, s7);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", -1, -1, -1), 60));
		starList.clear();
		addStarsToSet(starList, s0, s2, s6, s7);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 0, 0, 0), 60));
		starList.clear();
		addStarsToSet(starList, s7, s6, s2, s0);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 52.95794, 0.02084, 19.31343), 100));
		starList.clear();
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 1, -5, -1), 5));
		starList.clear();
		addStarsToSet(starList, s0, s2, s6, s7);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 1, -5, -1), 60));
		starList.clear();
		addStarsToSet(starList, s0, s2, s6, s7, s4, s8);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 1, -5, -1), 200));
		starList.clear();
		addStarsToSet(starList, s0, s2, s6, s7, s4, s8, s9, s1);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 1, -5, -1), 300));
		starList.clear();
		addStarsToSet(starList, s0, s2, s6, s7, s4, s8, s9, s1, s5, s3);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 1, -5, -1), 360));
		starList.clear();
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 300, 0, -300), 50));
		starList.clear();
		addStarsToSet(starList, s5);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 300, 0, -300), 100));
		starList.clear();
		addStarsToSet(starList, s5);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 300, 0, -300), 200));
		starList.clear();
		addStarsToSet(starList, s5, s4);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 300, 0, -300), 300));
		starList.clear();
		addStarsToSet(starList, s5, s4, s1, s2, s6);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 300, 0, -300), 400));
		starList.clear();
		addStarsToSet(starList, s5, s4, s1, s2, s6, s7, s8, s0, s9);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 300, 0, -300), 500));
		starList.clear();
		addStarsToSet(starList, s5, s4, s1, s2, s6, s7, s8, s0, s9, s3);
		assertEquals(starList, st.kNNSearchWithRadius(new Star("", "", 300, 0, -300), 550));
	}

	@Test
	public void testFindNearestWithRadiusWithStars() {
		List<Star> stars;
		String[] args = { "data/startest.csv" };
		if ((stars = StarsMain.parseInput(args)) == null)
			return;

		KDTree<Star> st = new StarTree(stars);

		Star s0 = new Star("0", "Sol", 0, 0, 0);
		Star s1 = new Star("1", "", 282.43485, 0.00449, 5.36884);
		Star s2 = new Star("2", "", 43.04329, 0.00285, -15.24144);
		Star s3 = new Star("3", "", 277.11358, 0.02422, 223.27753);
		Star s4 = new Star("4", "", 79.62896, 0.01164, -101.53103);
		Star s5 = new Star("5", "", 264.58918, 0.04601, -226.71007);
		Star s6 = new Star("6", "", 53.06535, 0.0168, 3.66089);
		Star s7 = new Star("7", "", 52.95794, 0.02084, 19.31343);
		Star s8 = new Star("8", "", 174.01562, 0.08288, 84.44669);
		Star s9 = new Star("9", "", 166.9363, 0.10297, 123.9143);

		List<Star> starList = new ArrayList<>();
		addStarsToSet(starList, s2, s6, s7);
		assertEquals(starList, st.kNNSearchWithRadius(s0, 60));
		starList.clear();
		addStarsToSet(starList, s6, s2, s0);
		assertEquals(starList, st.kNNSearchWithRadius(s7, 100));
		starList.clear();
		assertEquals(starList, st.kNNSearchWithRadius(s7, 5));
		starList.clear();
		addStarsToSet(starList, s2);
		assertEquals(starList, st.kNNSearchWithRadius(s4, 100));
		starList.clear();
		addStarsToSet(starList, s2, s6, s7, s0);
		assertEquals(starList, st.kNNSearchWithRadius(s4, 150));
		starList.clear();
		addStarsToSet(starList, s2, s6, s7, s0, s8, s5, s1, s9, s3);
		assertEquals(starList, st.kNNSearchWithRadius(s4, 400));
		starList.clear();
		assertEquals(starList, st.kNNSearchWithRadius(s8, 25));
		starList.clear();
		addStarsToSet(starList, s9);
		assertEquals(starList, st.kNNSearchWithRadius(s8, 50));
		starList.clear();
		addStarsToSet(starList, s9, s1, s7, s6, s2, s3, s0);
		assertEquals(starList, st.kNNSearchWithRadius(s8, 200));
		starList.clear();
		addStarsToSet(starList, s9, s1, s7, s6, s2, s3, s0, s4);
		assertEquals(starList, st.kNNSearchWithRadius(s8, 300));
		starList.clear();
		addStarsToSet(starList, s9, s1, s7, s6, s2, s3, s0, s4, s5);
		assertEquals(starList, st.kNNSearchWithRadius(s8, 350));
		starList.clear();
		addStarsToSet(starList, s8);
		assertEquals(starList, st.kNNSearchWithRadius(s9, 123));
		starList.clear();
		addStarsToSet(starList, s8, s3, s7, s1);
		assertEquals(starList, st.kNNSearchWithRadius(s9, 165.6));
		starList.clear();
		addStarsToSet(starList, s8, s3, s7, s1, s6, s2);
		assertEquals(starList, st.kNNSearchWithRadius(s9, 200));
		starList.clear();
		addStarsToSet(starList, s8, s3, s7, s1, s6, s2, s0, s4);
		assertEquals(starList, st.kNNSearchWithRadius(s9, 300));
		starList.clear();
		addStarsToSet(starList, s8, s3, s7, s1, s6, s2, s0, s4, s5);
		assertEquals(starList, st.kNNSearchWithRadius(s9, 400));
	}

	public static void addStarsToSet(Collection<Star> c, Star... strs) {
		for (Star str : strs)
			c.add(str);
	}

}
