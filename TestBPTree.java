import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.IllegalArgumentException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
public class TestBPTree {
	@Before
	public void setUp(){
	
	}

	//@Test
	public void testLoadFoodItemsPopulatesList() {
		try{
			  // create empty BPTree with branching factor of 3
	        BPTree<Double, Double> bpTree = new BPTree<>(3);

	        // create a pseudo random number generator
	        Random rnd1 = new Random();

	        // some value to add to the BPTree
	        Double[] dd = {0.0d,0.2d,0.2d, 0.5d, 0.2d, 0.8d,};

	        // build an ArrayList of those value and add to BPTree also
	        // allows for comparing the contents of the ArrayList 
	        // against the contents and functionality of the BPTree
	        // does not ensure BPTree is implemented correctly
	        // just that it functions as a data structure with
	        // insert, rangeSearch, and toString() working.
	        List<Double> list = new ArrayList<>();
	        for (int i = 0; i < 5; i++) {
	            Double j = dd[i];
	            list.add(j);
	            bpTree.insert(j, j);
	            System.out.println("\n\nTree structure:\n" + bpTree.toString());
	        }
	        List<Double> filteredValues = bpTree.rangeSearch(0.2d, "==");
	        System.out.println("Filtered values: " + filteredValues.toString());
		
				assertTrue(false);
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void testLTSearch() {
		try{
			  // create empty BPTree with branching factor of 3
			BPTree<Integer,String> tree = new BPTree<>(3);
			tree.insert(1, "apple");
			tree.insert(2, "orange");
			
			List<String> result = tree.rangeSearch(1, "<=");
		   
		        System.out.println("Filtered values: " + result.toString());
				assertTrue( result.size()==1 && 
						result.contains("apple"));
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void testLTSearchAcrossNodes() {
		try{
			  // create empty BPTree with branching factor of 3
			BPTree<Integer,String> tree = new BPTree<>(3);
			tree.insert(1, "apple");
			tree.insert(2, "orange");
			tree.insert(3, "kiwi");
			List<String> result = tree.rangeSearch(2, "<=");
		   
		        System.out.println("Filtered values: " + result.toString());
				assertTrue( result.size()==2 && 
						result.contains("apple")&& 
						result.contains("orange"));
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void testGTSearch() {
		try{
			  // create empty BPTree with branching factor of 3
			BPTree<Integer,String> tree = new BPTree<>(3);
			tree.insert(1, "apple");
			tree.insert(2, "orange");
			tree.insert(3, "kiwi");
			List<String> result = tree.rangeSearch(2, ">=");
		   
		        System.out.println("Filtered values: " + result.toString());
		        assertTrue( result.size()==2 && 
						result.contains("orange")&& 
						result.contains("kiwi"));
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}@Test
	public void testGTSearchAcrossNodes() {
		try{
			  // create empty BPTree with branching factor of 3
			BPTree<Integer,String> tree = new BPTree<>(3);
			tree.insert(1, "apple");
			tree.insert(2, "orange");
			tree.insert(3, "kiwi");
			List<String> result = tree.rangeSearch(1, ">=");
		   
		        System.out.println("Filtered values: " + result.toString());
		        assertTrue( result.size()==3 && 
		        		result.contains("orange")&&
		        		result.contains("apple")&&
						result.contains("kiwi"));
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void testEqualsSearch() {
		try{
			  // create empty BPTree with branching factor of 3
			BPTree<Integer,String> tree = new BPTree<>(3);
			tree.insert(1, "apple");
			tree.insert(2, "apple");
			List<String> result = tree.rangeSearch(2, "<=");
		   
		        System.out.println("Filtered values: " + result.toString());
				assertTrue( result.get(0).equals(result.get(1)));
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void testDuplicateValuesInTree() {
		try{
			  // create empty BPTree with branching factor of 3
			BPTree<Integer,String> tree = new BPTree<>(3);
			tree.insert(1, "apple");
			tree.insert(2, "apple");
			List<String> result = tree.rangeSearch(2, "<=");
		   
		        System.out.println("Filtered values: " + result.toString());
				assertTrue( result.get(0).equals(result.get(1)));
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void testDuplicateKeysInTree() {
		try{
			  // create empty BPTree with branching factor of 3
			BPTree<Integer,String> tree = new BPTree<>(3);
			tree.insert(1, "apple");
			tree.insert(1, "pineapple");
			List<String> result = tree.rangeSearch(1, "==");
		   
		        System.out.println("Filtered values: " + result.toString());
				assertTrue( result.size()==2 && 
						result.contains("apple")&& 
						result.contains("pineapple"));
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void testDuplicateKeysInTreeAcrossNodes() {
		try{
			  // create empty BPTree with branching factor of 3
			BPTree<Integer,String> tree = new BPTree<>(3);
			tree.insert(1, "apple");
			tree.insert(1, "pineapple");
			tree.insert(1, "watermelon");
			List<String> result = tree.rangeSearch(1, "==");
		   
		        System.out.println("Filtered values: " + result.toString());
				assertTrue( result.size()==3 && 
						result.contains("apple")&& 
						result.contains("pineapple")&& 
						result.contains("watermelon")			);
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void testDuplicateKeysInTreeLT() {
		try{
			  // create empty BPTree with branching factor of 3
			BPTree<Integer,String> tree = new BPTree<>(3);
			tree.insert(1, "apple");
			tree.insert(1, "pineapple");
			List<String> result = tree.rangeSearch(1, "<=");
		   
		        System.out.println("Filtered values: " + result.toString());
		        System.out.println("size:" + result.size());
		        System.out.println("contains pine: " + result.contains("pineapple"));
		        
				assertTrue( result.size()==2 && 
						result.contains("apple")&& 
						result.contains("pineapple"));
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}@Test
	public void testDuplicateKeysInTreeGT() {
		try{
			  // create empty BPTree with branching factor of 3
			BPTree<Integer,String> tree = new BPTree<>(3);
			tree.insert(1, "apple");
			tree.insert(1, "pineapple");
			List<String> result = tree.rangeSearch(1, ">=");
		   
		        System.out.println("Filtered values: " + result.toString());
		        System.out.println("size:" + result.size());
		        System.out.println("contains pine: " + result.contains("pineapple"));
		        
				assertTrue( result.size()==2 && 
						result.contains("apple")&& 
						result.contains("pineapple"));
		
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void TestMultipleLevels(){
		BPTree<Integer,String> tree = new BPTree<>(3);
		for (int i=0;i<10;i++){
		tree.insert(i, "apple");
		}
		
		
		 System.out.println("\n\nTree structure:\n" + tree.toString());
	}
}

