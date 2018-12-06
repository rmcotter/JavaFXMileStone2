
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.IllegalArgumentException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class TestFoodData {
	private FoodData FoodDataObj;
	@Before
	public void setUp(){
		FoodDataObj = new FoodData();
	}
	
	@Test
	public void testLoadFoodItems() {
		try{
			FoodDataObj.loadFoodItems("testFoodFile.txt");
			assertTrue(true);
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void testLoadFoodItemsPopulatesList() {
		try{
		//	File filepath = Paths.get("testFoodFile.txt").toFile();
			
			FoodDataObj.loadFoodItems("C:\\Users\\rcotter\\workspace\\application\\testFoodFile.txt");
			List<FoodItem> list=FoodDataObj.getAllFoodItems();
			if(list.isEmpty()){
				assertTrue(false);
			}else	assertTrue(true);
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	
	
	

	@Test
	public void testLoadFoodItemsListEmpty() {
		try{
			
			List<FoodItem> list=FoodDataObj.getAllFoodItems();
			if(list.isEmpty()){
				assertTrue(true);
			}else	assertTrue(false);
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void filterByNameString() {
		assertFalse(true);
	}
	@Test
	public void filterByNutrients() {
		assertFalse(true);
	}
	@Test
	public void addFoodItem() {
		try{
			FoodItem Food1 = new FoodItem("Id","Name"); 
			FoodDataObj.addFoodItem(Food1);
			List<FoodItem> list=FoodDataObj.getAllFoodItems();
			if(list.isEmpty()){
				assertTrue(false);
			}else	assertTrue(true);
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}
	@Test
	public void getAllFoodItems() {
		try{
			FoodItem Food1 = new FoodItem("Id","Name");
			FoodItem Food2 = new FoodItem("Id2","Name2");
			FoodDataObj.addFoodItem(Food1);
			FoodDataObj.addFoodItem(Food2);
			List<FoodItem> list=FoodDataObj.getAllFoodItems();
			if(list.isEmpty()){
				
				assertTrue(false);
			}else	assertTrue(list.contains(Food1)&&list.contains(Food2));
		}
		catch(Exception e){
			assertFalse(true);
		}
	}
	@Test
	public void saveFoodItems() {
		try{
		FoodDataObj.loadFoodItems("C:\\Users\\rcotter\\workspace\\application\\testFoodFile.txt");
		FoodDataObj.saveFoodItems("C:\\Users\\rcotter\\workspace\\application\\testSaveFile.txt");
		assertTrue(true);
		}
		catch(Exception e){
			assertFalse(true);
		}
		
	}

}
