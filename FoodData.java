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
 * @author Rick Cotter
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
		this.indexes = new HashMap<String, BPTree<Double, FoodItem>>();
		indexes.put("calories", new BPTree<Double, FoodItem>(3));
		indexes.put("fat", new BPTree<Double, FoodItem>(3));
		indexes.put("carbohydrate", new BPTree<Double, FoodItem>(3));
		indexes.put("fiber", new BPTree<Double, FoodItem>(3));
		indexes.put("protein", new BPTree<Double, FoodItem>(3));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
	 */
	@Override
	public void loadFoodItems(String filePath) {
		// TODO : Complete
		List<String> food;
		try {
			food = (List<String>) Files.lines(Paths.get(filePath)).collect(Collectors.toList());

			for (String var : food) {
				addFoodItem(parseStringToFood(var));
				System.out.println(var);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No file found");
		}
	}

	private FoodItem parseStringToFood(String foodString) {
		String delims = ",";
		String[] details = foodString.split(delims);
		FoodItem foodItem = new FoodItem(details[0], details[1]);

		for (int i = 2; i < 12; i = i + 2) {
			foodItem.addNutrient(details[i], Double.parseDouble(details[i + 1]));
		}

		return foodItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see skeleton.FoodDataADT#filterByName(java.lang.String)
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

	/* filterByNutrients without the BPTree
	 * (non-Javadoc)
	 * 
	 * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
	 */
	@Override
	public List<FoodItem> filterByNutrients(List<String> rules) {
		List<FoodItem> filtered = new ArrayList<FoodItem>();
		if (rules.isEmpty()) {
			return this.getAllFoodItems();
		} else {
			for (int j = 0; j < this.foodItemList.size(); j++) {
				boolean passed = true;
			for (int i = 0; i < rules.size(); i++) {

				String r = rules.get(i);
				String rr[] = r.split(" ");

				if (rr.length != 1) {

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

//	/* filterByNutrients using the BPTree
//	 * (non-Javadoc)
//	 * 
//	 * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
//	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see skeleton.FoodDataADT#getAllFoodItems()
	 */
	@Override
	public List<FoodItem> getAllFoodItems() {

		return foodItemList;
	}

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