import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class main extends Application {
	
	public static void main(String[] args) {
		// read data file
		// call other methods
		// instantiate ADT
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {try {
		Button button1 = new Button("Load From File");
		Button button2 = new Button("Save To File");
		Button button3 = new Button("Add One Food");
		
		//creating top menu
		HBox topMenu = new HBox();
		topMenu.getChildren().addAll(button1, button2, button3);
		
		//creating rule list section
		Label ruleListLabel = new Label("Rules");
		
		ListView<String> ruleList = new ListView<String>();
		ruleList.getItems().addAll("Rule 1", "Rule 2", "Rule 3");
		ruleList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		ruleList.setMinWidth(280);
		
		Button applyRules = new Button("Apply Rules");
		Button removeRule = new Button("Remove Rule");
		Button clearRules = new Button("Clear Rules");
		removeRule.setMinWidth(100);
		applyRules.setMinWidth(92);
		
		HBox ruleListButtons = new HBox();
		ruleListButtons.getChildren().addAll(applyRules, removeRule, clearRules);

		Separator ruleSecSep = new Separator();
		Label blankSpace1 = new Label("");
		Label ruleEditorLabel = new Label("Rule Editor");
		Separator ruleSecSep2 = new Separator();
		
		Button nutrientFilter = new Button("Nutrient Filter");
		Button nameFilter = new Button("Name Filter");
		
		HBox ruleCreateButtons = new HBox();
		ruleCreateButtons.getChildren().addAll(nutrientFilter, nameFilter);
		
		//creating nutrient rule creator section
		
		ToggleGroup nutrientTypes = new ToggleGroup();
		RadioButton calorieButton = new RadioButton("Calories");
		calorieButton.setToggleGroup(nutrientTypes);
		RadioButton fatButton = new RadioButton("Fat");
		fatButton.setToggleGroup(nutrientTypes);
		RadioButton carbsButton = new RadioButton("Carbohydrates");
		carbsButton.setToggleGroup(nutrientTypes);
		RadioButton fiberButton = new RadioButton("Fiber");
		fiberButton.setToggleGroup(nutrientTypes);
		RadioButton proteinButton = new RadioButton("Protein");
		proteinButton.setToggleGroup(nutrientTypes);
		VBox nutrientTypesSec = new VBox();
		nutrientTypesSec.getChildren().addAll(calorieButton, fatButton, carbsButton, fiberButton, proteinButton);
		
		ToggleGroup operators = new ToggleGroup();
		RadioButton equalTo = new RadioButton("=");
		equalTo.setToggleGroup(operators);
		RadioButton lessThan = new RadioButton("<");
		lessThan.setToggleGroup(operators);
		RadioButton greaterThan = new RadioButton(">");
		greaterThan.setToggleGroup(operators);
		VBox operatorsSec = new VBox();
		operatorsSec.getChildren().addAll(equalTo, lessThan, greaterThan);
		operatorsSec.setTranslateX(25);
		
		TextField valueInput = new TextField();
		valueInput.setMaxWidth(75);
		valueInput.setTranslateX(55);
		VBox valueFieldSec = new VBox();
		Button addRuleButton = new Button("Add Rule");
		addRuleButton.setTranslateX(56);
		addRuleButton.setTranslateY(35.5);
		valueFieldSec.getChildren().addAll(valueInput, addRuleButton);
		
		
		HBox nutrientRuleOptions = new HBox();
		nutrientRuleOptions.getChildren().addAll(nutrientTypesSec, operatorsSec, valueFieldSec);
		nutrientRuleOptions.setTranslateY(20);
		
		
		VBox ruleSection = new VBox();
		ruleSection.getChildren().addAll(ruleListLabel, ruleList, ruleListButtons, ruleSecSep, blankSpace1, ruleEditorLabel, ruleSecSep2, ruleCreateButtons, nutrientRuleOptions);
		ruleSection.setBorder(new Border(new BorderStroke(Color.GREY,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		//creating filtered list section
		Label filteredListLabel = new Label("Filtered Food List");
		
		ListView<String> filteredList = new ListView<String>();
		filteredList.getItems().addAll("Food 1", "Food 2", "Food 3", "Food 4");
		filteredList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		filteredList.setMinWidth(280);
		
		Label totalCount = new Label("Total Count: _____");
		totalCount.setTranslateY(10);
		Button addToMeal = new Button("Add to Meal");
		addToMeal.setTranslateX(79);
		
		HBox filteredListButtons = new HBox();
		filteredListButtons.getChildren().addAll(totalCount, addToMeal);

		Separator foodSecSep = new Separator();
		Label blankSpace2 = new Label("");
		Label foodNutLabel = new Label("Food Nutrients");
		Separator foodSecSep2 = new Separator();

		Label foodName = new Label("Name: _____");
		Label foodID = new Label("ID: _____");
		Label foodCalories = new Label("Calories: _____");
		Label foodFat = new Label("Fat: _____");
		Label foodCarbohydrates = new Label("Carbohydrates: _____");
		Label foodFiber = new Label("Fiber: _____");
		Label foodProtein = new Label("Protein: _____");
		VBox nutrientInfo = new VBox();
		nutrientInfo.getChildren().addAll(foodName, foodID, foodCalories, foodFat, foodCarbohydrates, foodFiber, foodProtein);
		nutrientInfo.setTranslateY(8.5);
		nutrientInfo.setTranslateX(20);
		
		VBox filteredListSection = new VBox();
		filteredListSection.getChildren().addAll(filteredListLabel, filteredList, filteredListButtons, foodSecSep, blankSpace2, foodNutLabel, foodSecSep2, nutrientInfo);
		filteredListSection.setBorder(new Border(new BorderStroke(Color.GREY,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		//creating filtered list section
		Label mealListLabel = new Label("Meal List");
		
		ListView<String> mealList = new ListView<String>();
		mealList.getItems().addAll("Food 1", "Food 2", "Food 4");
		mealList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		mealList.setMinWidth(280);
		
		Button removeFromMeal = new Button("Remove from Meal");
		removeFromMeal.setTranslateX(146);
		
		HBox mealListButtons = new HBox();
		mealListButtons.getChildren().addAll(removeFromMeal);

		Label blankSpace3 = new Label("");
		Label mealSummaryLabel = new Label("Meal Summary");
		Separator mealSecSep2 = new Separator();
		
		Label mealCalories = new Label("Calories: _____");
		Label mealFat = new Label("Fat: _____");
		Label mealCarbohydrates = new Label("Carbohydrates: _____");
		Label mealFiber = new Label("Fiber: _____");
		Label mealProtein = new Label("Protein: _____");
		
		VBox mealInfo = new VBox();
		mealInfo.getChildren().addAll(mealCalories, mealFat, mealCarbohydrates, mealFiber, mealProtein);
		mealInfo.setTranslateY(8.5);
		mealInfo.setTranslateX(20);
		
		Button refresh = new Button("Refresh");
		refresh.setTranslateX(85);
		
		HBox mealSummary = new HBox();
		mealSummary.getChildren().addAll(mealInfo, refresh);
		
		Separator mealSecSep = new Separator();
		
		VBox mealListSection = new VBox();
		mealListSection.getChildren().addAll(mealListLabel, mealList, mealListButtons, mealSecSep, blankSpace3, mealSummaryLabel, mealSecSep2, mealSummary);
		mealListSection.setBorder(new Border(new BorderStroke(Color.GREY,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		HBox lists = new HBox(10);
		lists.setMinHeight(623.5);
		lists.getChildren().addAll(ruleSection,filteredListSection, mealListSection);
		
		VBox root = new VBox(10);
		root.getChildren().addAll(topMenu, lists);
		
		Scene scene = new Scene(root,864,665);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Meal Planner");
		primaryStage.show();
		
	} catch(Exception e) {
		e.printStackTrace();
		
	}
	}
}