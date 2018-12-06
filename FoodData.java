import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
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
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
        // TODO : Complete
    	List<String> food;
		try {
			food = (List<String>) Files.lines(Paths.get(filePath))
					.collect(Collectors.toList());
		
		for (String var : food) 
		{ 
			addFoodItem(parseStringToFood(var));
			 System.out.println(var);
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
    private FoodItem parseStringToFood(String foodString){
    	String delims = ",";
    	String[] details = foodString.split(delims);
    	FoodItem foodItem = new FoodItem(details[0],details[1]);
    	
    	for(int i =2; i< 12;i=i+2){
    	foodItem.addNutrient(details[i], Double.parseDouble(details[i+1]));
    	}  	
    	    	
    	return foodItem;
    }
    	

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        // TODO : Complete
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
        // TODO : Complete
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        foodItemList.add(foodItem);
        //TODO: update indexes
    }

    /*
     * (non-Javadoc)
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
		
		    Path path = Paths.get(filename);

		    try (
		        final BufferedWriter writer = Files.newBufferedWriter(path);
		    ) {
		    	for (FoodItem var : foodItemList) 
				{ 
		    		String foodItemStr = parseFoodItemToString(var);
					//addFoodItem(parseStringToFood(var));
					 System.out.println(var);
					 writer.write(foodItemStr);
				        writer.flush();
				}
		       
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	}


	private String parseFoodItemToString(FoodItem var) {
		String foodItemString = new String();
		String newline = System.getProperty("line.separator");

		
		foodItemString = var.getID() + "," +
				var.getName() + "," +
				"calories" + "," +
				var.getNutrientValue("calories") + "," +
				"fat" + "," +
				var.getNutrientValue("fat") + "," +
				"carbohydrate" + "," +
				var.getNutrientValue("carbohydrate") + "," +
				"fiber" + "," +
				var.getNutrientValue("fiber") + "," +
				"protein" + "," +
				var.getNutrientValue("protein")+ newline;				
		return foodItemString;
	}
}

