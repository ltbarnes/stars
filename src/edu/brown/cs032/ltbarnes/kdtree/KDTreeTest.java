package edu.brown.cs032.ltbarnes.kdtree;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class KDTreeTest {

	@Test
	public void test2DBuild() {
		List<KDElement> stars = new ArrayList<>();
		stars.add(new KDElement("0", -3, -1));
		stars.add(new KDElement("1", -2, 0));
		stars.add(new KDElement("2", -1, 1));
		stars.add(new KDElement("3", 0, 0));
		stars.add(new KDElement("4", 1, -1));
		stars.add(new KDElement("5", 2, 0));
		stars.add(new KDElement("6", 3, 1));
		KDTree<KDElement> st = new GenericKDTree<>(stars, 3);
		assertEquals("(3: L:(1: L:(0: L:null R:null) R:(2: L:null R:null)) "
				+ "R:(5: L:(4: L:null R:null) R:(6: L:null R:null)))", st.toString());
		assertEquals(7, st.size());
	}

	@Test
	public void test4DBuild() {
		List<KDElement> stars = new ArrayList<>();
		stars.add(new KDElement("0", -11, -5, -2, 0));
		stars.add(new KDElement("1", -10, -4, -1, 0));
		stars.add(new KDElement("2", -9, -3, 0, 0));
		stars.add(new KDElement("3", -8, -2, 1, 0));
		stars.add(new KDElement("4", -7, -1, 2, 0));
		stars.add(new KDElement("5", -6, 0, 0, 0));
		stars.add(new KDElement("6", -5, 1, -2, 0));
		stars.add(new KDElement("7", -4, 2, -1, 0));
		stars.add(new KDElement("8", -3, 3, 0, 0));
		stars.add(new KDElement("9", -2, 4, 1, 0));
		stars.add(new KDElement("10", -1, 5, 2, 0));
		stars.add(new KDElement("11", 0, 0, 0, 0));
		stars.add(new KDElement("12", 1, -5, -2, 0));
		stars.add(new KDElement("13", 2, -4, -1, 0));
		stars.add(new KDElement("14", 3, -3, 0, 0));
		stars.add(new KDElement("15", 4, -2, 1, 0));
		stars.add(new KDElement("16", 5, -1, 2, 0));
		stars.add(new KDElement("17", 6, 0, 0, 0));
		stars.add(new KDElement("18", 7, 1, -2, 0));
		stars.add(new KDElement("19", 8, 2, -1, 0));
		stars.add(new KDElement("20", 9, 3, 0, 0));
		stars.add(new KDElement("21", 10, 4, 1, 0));
		stars.add(new KDElement("22", 11, 5, 2, 0));
		KDTree<KDElement> st = new GenericKDTree<>(stars, 3);
		assertEquals("(11: L:(5: L:(2: L:(1: L:(0: L:null R:null) R:null) R:(4: L:(3: L:null R:null) R:null))"
				+ " R:(8: L:(7: L:(6: L:null R:null) R:null) R:(10: L:(9: L:null R:null) R:null))) "
				+ "R:(17: L:(14: L:(13: L:(12: L:null R:null) R:null) R:(16: L:(15: L:null R:null) R:null))"
				+ " R:(20: L:(19: L:(18: L:null R:null) R:null) R:(22: L:(21: L:null R:null) R:null))))", st.toString());
		assertEquals(23, st.size());
	}
}
