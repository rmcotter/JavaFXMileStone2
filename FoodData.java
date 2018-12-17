import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class represents the backend for managing all the operations associated
 * with FoodItems
 * 
 * @author Rick Cotter, Kyle Berkow, Oliver Kao, Soham Amin
 */
public class FoodData implements FoodDataADT<FoodItem> {

	// List of all the food items.
	private List<FoodItem> foodItemList;

	// Map of nutrients and their corresponding index
	private HashMap<String, BPTree<Double, FoodItem>> indexes;

	/**
	 * Public constructor
	 */
	public FoodData() {
		this.foodItemList = new ArrayList<FoodItem>();
		// Attempt to use BPtrees, but unable to implement successfully
//		this.indexes = new HashMap<String, BPTree<Double, FoodItem>>();
//		indexes.put("calories", new BPTree<Double, FoodItem>(3));
//		indexes.put("fat", new BPTree<Double, FoodItem>(3));
//		indexes.put("carbohydrate", new BPTree<Double, FoodItem>(3));
//		indexes.put("fiber", new BPTree<Double, FoodItem>(3));
//		indexes.put("protein", new BPTree<Double, FoodItem>(3));
	}

	/**
	 * Loads food items in from file
	 */
	@Override
	public void loadFoodItems(String filePath) {
		// TODO : Complete
		List<String> food;
		try {
			food = (List<String>) Files.lines(Paths.get(filePath)).collect(Collectors.toList());

			// Parse through each line and adds item
			for (String var : food) {
				addFoodItem(parseStringToFood(var));
			}
		} catch (IOException e) {
			System.out.println("No file found");
		}
	}

	/**
	 * Private method to help parse the food items
	 * @param foodString
	 * @return FoodItem
	 */
	private FoodItem parseStringToFood(String foodString) {
		String delims = ",";
		String[] details = foodString.split(delims);
		// ID and name of the food item
		FoodItem foodItem = new FoodItem(details[0], details[1]);

		// Each nutrient, text and value (as double)
		for (int i = 2; i < 12; i = i + 2) {
			foodItem.addNutrient(details[i], Double.parseDouble(details[i + 1]));
		}
		return foodItem;
	}

	/**
	 * Name filtering based on characters of the name
	 * If no filters, returns whole list
	 * If filters, case insensitive and checks if it contains the string
	 * @return List of fooditems
	 */
	@Override
	public List<FoodItem> filterByName(String substring) {
		List<FoodItem> filtered = new ArrayList<FoodItem>();
		if (substring == null) {
			return this.foodItemList;
		} else {
			for (int i = 0; i < this.foodItemList.size(); i++) {
				if (this.foodItemList.get(i).getName().toLowerCase().contains(substring)) {
					filtered.add(this.foodItemList.get(i));
				}
			}
		}
		return filtered;
	}

	/**
	 * Filter by nutrients
	 * If no rules, returns entire list
	 * Parses the nutrient filters to determine how to filter
	 * @return List of filtered food items
	 */
	@Override
	public List<FoodItem> filterByNutrients(List<String> rules) {
		List<FoodItem> filtered = new ArrayList<FoodItem>();
		if (rules.isEmpty()) { // If no rules, returns whole list
			return this.getAllFoodItems();
		} else {
			for (int j = 0; j < this.foodItemList.size(); j++) {
				boolean passed = true;
			for (int i = 0; i < rules.size(); i++) {
				// Splitting of the rules from their original strings
				String r = rules.get(i);
				String rr[] = r.split(" ");

				if (rr.length != 1) { // If length != 1, then there was a space and its a
					//nutrient filter not name filter
						if (rr[1].contentEquals("==")) {
							if (this.foodItemList.get(j).getNutrientValue(rr[0]) != Integer.parseInt(rr[2])) {
								passed = false;
							}
						} else if (rr[1].contentEquals(">=")) {
							if (this.foodItemList.get(j).getNutrientValue(rr[0]) < Integer.parseInt(rr[2])) {
								passed = false;
							}
						} else if (rr[1].contentEquals("<=")) {
							if (this.foodItemList.get(j).getNutrientValue(rr[0]) > Integer.parseInt(rr[2])) {
								passed = false;
							}
						}
				}
			}
				if (passed) {
					filtered.add(this.foodItemList.get(j));
				}
			}
		}
		return filtered;
	}


	/**
	 * Filtering if our BpTrees were successful
	 */
//	@Override
//	public List<FoodItem> filterByNutrients(List<String> rules) {
//		List<FoodItem> filtered = new ArrayList<FoodItem>();
//		filtered.addAll(this.getAllFoodItems());
//		if (rules.isEmpty()) {
//			return this.getAllFoodItems();
//		} else {
//			for (int i = 0; i < rules.size(); i++) {
//
//				String r = rules.get(i);
//				String rr[] = r.split(" ");
//
//				if (rr.length != 1) {
//					filtered.retainAll(indexes.get(rr[0]).rangeSearch(Double.parseDouble(rr[2]), rr[1]));
//				}
//			}
//		}
//		return filtered;
//	}

	/**
	 * Add single food item
	 * Would have added to BPTree indices if working
	 */
	@Override
	public void addFoodItem(FoodItem foodItem) {
		foodItemList.add(foodItem);
//		indexes.get("calories").insert(foodItem.getNutrientValue("calories"), foodItem);
//		indexes.get("fat").insert(foodItem.getNutrientValue("fat"), foodItem);
//		indexes.get("carbohydrate").insert(foodItem.getNutrientValue("carbohydrate"), foodItem);
//		indexes.get("fiber").insert(foodItem.getNutrientValue("fiber"), foodItem);
//		indexes.get("protein").insert(foodItem.getNutrientValue("protein"), foodItem);

	}

	/**
	 * Grabs all the fooditems to return in a list
	 * @return list of food items
	 */
	@Override
	public List<FoodItem> getAllFoodItems() {
		return foodItemList;
	}

	/**
	 * Saves food items to file of choice
	 */
	@Override
	public void saveFoodItems(String filename) {
		// TODO Auto-generated method stub
		{

			File outputFile = new File(filename);
			PrintStream writer = null;

			try {
				writer = new PrintStream(outputFile);
				for (FoodItem var : foodItemList) {
					String foodItemStr = parseFoodItemToString(var);
					writer.print(foodItemStr);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
	}

	/**
	 * Helper method to help parse the fooditem into each line of the file
	 * @param var
	 * @return string for each food item
	 */
	private String parseFoodItemToString(FoodItem var) {
		String foodItemString = new String();
		String newline = System.getProperty("line.separator");

		foodItemString = var.getID() + "," + var.getName() + "," + "calories" + "," + var.getNutrientValue("calories")
				+ "," + "fat" + "," + var.getNutrientValue("fat") + "," + "carbohydrate" + ","
				+ var.getNutrientValue("carbohydrate") + "," + "fiber" + "," + var.getNutrientValue("fiber") + ","
				+ "protein" + "," + var.getNutrientValue("protein") + newline;
		return foodItemString;
	}
}