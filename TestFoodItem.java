
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

public class TestFoodItem {
	/**
	 * Tests that an FoodItem can exist
	 */
	@Test
	public void food1IsNotNull() {
		FoodItem food1 = new FoodItem("key","name");
		assertTrue(food1!=null);
	}/**
	 * Tests that an FoodItem can return an ID
	 */
	@Test
	public void food1getID() {
		String id="TestID";
		FoodItem food1 = new FoodItem(id,"name");
		assertTrue(food1.getID().compareTo(id)==0);
	}
	/**
	 * Tests that an FoodItem can return a name
	 */
	@Test
	public void food1getName() {
		String id="TestID";
		String name="TestName";
		FoodItem food1 = new FoodItem(id,name);
		assertTrue(food1.getName().compareTo(name)==0);
	}
	/**
	 * Tests that an FoodItem can return a name
	 */
	@Test
	public void food1setAndRetrieveNurtrient() {
		String id="TestID";
		String name="TestName";
		FoodItem food1 = new FoodItem(id,name);
		double fatAmount=100;
		food1.addNutrient("fat", fatAmount);
		assertTrue(food1.getNutrientValue("fat")==fatAmount);
		
	}
	/**
	 * Tests that an FoodItem can return a name
	 */
	@Test
	public void food1getNutrients() {
		String id="TestID";
		String name="TestName";
		FoodItem food1 = new FoodItem(id,name);
		HashMap<String, Double> nutrients;
		double fatAmount=100;
		food1.addNutrient("fat", fatAmount);
		nutrients = food1.getNutrients();
		assertTrue(nutrients.get("fat")==fatAmount);
		
	}

}
