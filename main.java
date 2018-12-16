import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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

	// private String nameRule;

	public static void main(String[] args) {
		// read data file
		// call other methods
		// instantiate ADT
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {

			FoodData foodlist = new FoodData();

			Button loadFromFile = new Button("Load From File");
			Button saveToFile = new Button("Save To File");
			Button addOneFood = new Button("Add One Food");

			// creating top menu
			HBox topMenu = new HBox();
			topMenu.getChildren().addAll(loadFromFile, saveToFile, addOneFood);

			// creating rule list section

			/// all the buttons/inputs
			Button applyRules = new Button("Apply Rules");
			Button removeRule = new Button("Remove Rule");
			Button clearRules = new Button("Clear Rules");
			Button addNutrientRule = new Button("Add Rule");
			Button addNameRule = new Button("Add Rule");

			ToggleGroup nutrientTypes = new ToggleGroup();
			RadioButton calorieButton = new RadioButton("Calories");
			calorieButton.setToggleGroup(nutrientTypes);
			RadioButton fatButton = new RadioButton("Fat");
			fatButton.setToggleGroup(nutrientTypes);
			RadioButton carbsButton = new RadioButton("Carbohydrate");
			carbsButton.setToggleGroup(nutrientTypes);
			RadioButton fiberButton = new RadioButton("Fiber");
			fiberButton.setToggleGroup(nutrientTypes);
			RadioButton proteinButton = new RadioButton("Protein");
			proteinButton.setToggleGroup(nutrientTypes);

			ToggleGroup operators = new ToggleGroup();
			RadioButton equalTo = new RadioButton("==");
			equalTo.setToggleGroup(operators);
			RadioButton lessThan = new RadioButton("<=");
			lessThan.setToggleGroup(operators);
			RadioButton greaterThan = new RadioButton(">=");
			greaterThan.setToggleGroup(operators);

			TextField valueInput = new TextField();
			TextField nameInput = new TextField();

			/// creating the list section
			Label ruleListLabel = new Label("Rules");

			ListView<String> ruleList = new ListView<String>();
			ruleList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			ruleList.setMinWidth(280);
			removeRule.setMinWidth(100);
			applyRules.setMinWidth(92);

			HBox ruleListButtons = new HBox();
			ruleListButtons.getChildren().addAll(applyRules, removeRule, clearRules);

			/// creating rule creator section
			Separator ruleSecSep = new Separator();
			Label blankSpace1 = new Label("");
			Label nutrientRuleLabel = new Label("Nutrient Filter Editor");
			Separator ruleSecSep2 = new Separator();

			VBox nutrientTypesSec = new VBox();
			nutrientTypesSec.getChildren().addAll(calorieButton, fatButton, carbsButton, fiberButton, proteinButton);

			VBox operatorsSec = new VBox();
			operatorsSec.getChildren().addAll(equalTo, lessThan, greaterThan);
			operatorsSec.setTranslateX(15);

			valueInput.setMaxWidth(75);
			valueInput.setTranslateX(55);
			VBox valueFieldSec = new VBox();
			addNutrientRule.setTranslateX(56);
			addNutrientRule.setTranslateY(35.5);
			valueFieldSec.getChildren().addAll(valueInput, addNutrientRule);

			HBox nutrientRuleOptions = new HBox();
			nutrientRuleOptions.getChildren().addAll(nutrientTypesSec, operatorsSec, valueFieldSec);

			Separator nameRuleSep = new Separator();
			Label nameRuleLabel = new Label("Name Filter Editor");
			Separator nameRuleSep2 = new Separator();

			HBox nameRuleInputs = new HBox();
			nameRuleInputs.getChildren().addAll(nameInput, addNameRule);
			addNameRule.setTranslateX(37);

			VBox ruleSection = new VBox();
			ruleSection.getChildren().addAll(ruleListLabel, ruleList, ruleListButtons, ruleSecSep, blankSpace1,
					nutrientRuleLabel, ruleSecSep2, nutrientRuleOptions, nameRuleSep, nameRuleLabel, nameRuleSep2,
					nameRuleInputs);
			ruleSection.setBorder(new Border(
					new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

			// creating filtered list section

			/// all the buttons
			Button addToMeal = new Button("Add to Meal");

			/// all variable labels
			Label totalCount = new Label("Total Count: " + foodlist.getAllFoodItems().size());
			Label foodName = new Label("Name: _____");
			Label foodID = new Label("ID: _____");
			Label foodCalories = new Label("Calories: _____");
			Label foodFat = new Label("Fat: _____");
			Label foodCarbohydrates = new Label("Carbohydrates: _____");
			Label foodFiber = new Label("Fiber: _____");
			Label foodProtein = new Label("Protein: _____");

			Label filteredListLabel = new Label("Filtered Food List");

			ListView<FoodItem> filteredList = new ListView<FoodItem>();
			filteredList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			filteredList.setMinWidth(280);

			totalCount.setTranslateY(10);
			addToMeal.setTranslateX(79);

			HBox filteredListButtons = new HBox();
			filteredListButtons.getChildren().addAll(totalCount, addToMeal);

			Separator foodSecSep = new Separator();
			Label blankSpace2 = new Label("");
			Label foodNutLabel = new Label("Food Nutrients");
			Separator foodSecSep2 = new Separator();

			VBox nutrientInfo = new VBox();
			nutrientInfo.getChildren().addAll(foodName, foodID, foodCalories, foodFat, foodCarbohydrates, foodFiber,
					foodProtein);
			nutrientInfo.setTranslateY(8.5);
			nutrientInfo.setTranslateX(20);

			VBox filteredListSection = new VBox();
			filteredListSection.getChildren().addAll(filteredListLabel, filteredList, filteredListButtons, foodSecSep,
					blankSpace2, foodNutLabel, foodSecSep2, nutrientInfo);
			filteredListSection.setBorder(new Border(
					new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

			// creating meal list section

			/// all the buttons
			Button removeFromMeal = new Button("Remove from Meal");
			Button refresh = new Button("Refresh");

			Label mealListLabel = new Label("Meal List");

			ListView<FoodItem> mealList = new ListView<FoodItem>();
			mealList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			mealList.setMinWidth(280);

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

			refresh.setTranslateX(85);

			HBox mealSummary = new HBox();
			mealSummary.getChildren().addAll(mealInfo, refresh);

			Separator mealSecSep = new Separator();

			VBox mealListSection = new VBox();
			mealListSection.getChildren().addAll(mealListLabel, mealList, mealListButtons, mealSecSep, blankSpace3,
					mealSummaryLabel, mealSecSep2, mealSummary);
			mealListSection.setBorder(new Border(
					new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

			HBox lists = new HBox(10);
			lists.setMinHeight(626.5);
			lists.getChildren().addAll(ruleSection, filteredListSection, mealListSection);

			VBox root = new VBox(10);
			root.getChildren().addAll(topMenu, lists);

			// button actions
			addNameRule.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					for (int i = 0; i < ruleList.getItems().size(); i++) {
						String r = ruleList.getItems().get(i);
						String rr[] = r.split(" ");

						if (rr.length == 1) {
							ruleList.getItems().remove(r);
						}
					}
					ruleList.getItems().add(nameInput.getText().replaceAll(" ", ""));
				}
			});

			addNutrientRule.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						if (Double.parseDouble(valueInput.getText()) < 0) {
							throw new Exception();
						} else {
							ruleList.getItems().add(""
									+ (((RadioButton) nutrientTypes.getSelectedToggle()).getText().toLowerCase()) + " "
									+ (((RadioButton) operators.getSelectedToggle()).getText().toLowerCase()) + " "
									+ valueInput.getText());
						}
					} catch (Exception e) {
						nutrientRuleLabel.setText("Nutrient Filter Editor - Non neg/no alpha");
					}
				}
			});

			removeRule.setOnAction(e -> ruleList.getItems().remove(ruleList.getSelectionModel().getSelectedItem()));
			clearRules.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ruleList.getItems().clear();
					filteredList.getItems().clear();
					String nameRule = null;
					for (int i = 0; i < ruleList.getItems().size(); i++) {
						String r = ruleList.getItems().get(i);
						String rr[] = r.split(" ");

						if (rr.length == 1) {
							nameRule = r.toLowerCase();
						}
					}

					List<FoodItem> nameFiltered = foodlist.filterByName(nameRule);
					List<FoodItem> nutrientFiltered = foodlist.filterByNutrients(ruleList.getItems());

					Set<FoodItem> nameFilteredSet = new HashSet<FoodItem>(nameFiltered);
					Set<FoodItem> nutrientFilteredSet = new HashSet<FoodItem>(nutrientFiltered);
					Set<FoodItem> tempSet = new HashSet<FoodItem>(nameFilteredSet);
					tempSet.retainAll(nutrientFilteredSet);

					filteredList.getItems().addAll(new ArrayList<FoodItem>(tempSet));
					totalCount.setText("Total count: " + filteredList.getItems().size());
				}

			});

			applyRules.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					filteredList.getItems().clear();
					String nameRule = null;
					for (int i = 0; i < ruleList.getItems().size(); i++) {
						String r = ruleList.getItems().get(i);
						String rr[] = r.split(" ");

						if (rr.length == 1) {
							nameRule = r.toLowerCase();
						}
					}

					List<FoodItem> nameFiltered = foodlist.filterByName(nameRule);
					List<FoodItem> nutrientFiltered = foodlist.filterByNutrients(ruleList.getItems());

					Set<FoodItem> nameFilteredSet = new HashSet<FoodItem>(nameFiltered);
					Set<FoodItem> nutrientFilteredSet = new HashSet<FoodItem>(nutrientFiltered);
					Set<FoodItem> tempSet = new HashSet<FoodItem>(nameFilteredSet);
					tempSet.retainAll(nutrientFilteredSet);

					filteredList.getItems().addAll(new ArrayList<FoodItem>(tempSet));
					totalCount.setText("Total count: " + filteredList.getItems().size());
				}
			});

			loadFromFile.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					VBox popupbox = new VBox();
					TextField filename = new TextField();
					Button close = new Button("Cancel");
					Button accept = new Button("Accept");
					HBox buttons = new HBox();
					buttons.getChildren().addAll(accept, close);
					Label title = new Label("Filename Input");
					Separator sep = new Separator();
					Label blank = new Label("");
					Label blank2 = new Label("");
					Label instructions = new Label("Enter the name of the file including .csv/.txt:");
					popupbox.getChildren().addAll(title, sep, blank, instructions, filename, blank2, buttons);

					Stage popupStage = new Stage(StageStyle.TRANSPARENT);
					popupStage.initOwner(primaryStage);
					popupStage.initModality(Modality.APPLICATION_MODAL);
					Scene popupScene = new Scene(popupbox, 350, 125);
					popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					popupStage.setScene(popupScene);

					close.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							popupStage.hide();
						}
					});

					accept.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							foodlist.loadFoodItems(filename.getText());
							filteredList.getItems().addAll(foodlist.getAllFoodItems());
							totalCount.setText("Total Count: " + filteredList.getItems().size());
							popupStage.hide();
						}
					});
					popupStage.show();
				}
			});

			saveToFile.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					VBox popupbox = new VBox();
					TextField filename = new TextField();
					Button close = new Button("Cancel");
					Button accept = new Button("Accept");
					HBox buttons = new HBox();
					buttons.getChildren().addAll(accept, close);
					Label title = new Label("Filename Output");
					Separator sep = new Separator();
					Label blank = new Label("");
					Label blank2 = new Label("");
					Label instructions = new Label("Enter the name of the file including .csv/.txt:");
					popupbox.getChildren().addAll(title, sep, blank, instructions, filename, blank2, buttons);

					Stage popupStage = new Stage(StageStyle.TRANSPARENT);
					popupStage.initOwner(primaryStage);
					popupStage.initModality(Modality.APPLICATION_MODAL);
					Scene popupScene = new Scene(popupbox, 350, 125);
					popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					popupStage.setScene(popupScene);

					close.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							popupStage.hide();
						}
					});

					accept.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							FoodData filteredFoodList = new FoodData();
							for (FoodItem temp : filteredList.getItems()) {
								filteredFoodList.addFoodItem(temp);
							}
							filteredFoodList.saveFoodItems(filename.getText());
							popupStage.hide();
						}
					});
					popupStage.show();
				}
			});

			addOneFood.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					VBox popupbox = new VBox();
					Button close = new Button("Cancel");
					Button accept = new Button("Accept");
					HBox buttons = new HBox(accept, close);
					Label title = new Label("New Food Item Input");
					Separator sep = new Separator();
					Label blank = new Label("");
					Label blank2 = new Label("");

					Label id = new Label("ID: ");
					id.setTranslateX(10);
					TextField idtext = new TextField();
					idtext.setTranslateX(54);
					HBox idbox = new HBox(id, idtext);

					Label name = new Label("Name: ");
					name.setTranslateX(10);
					TextField nametext = new TextField();
					nametext.setTranslateX(34);
					HBox namebox = new HBox(name, nametext);

					Label calories = new Label("Calories: ");
					calories.setTranslateX(10);
					TextField caloriestext = new TextField();
					caloriestext.setTranslateX(24);
					HBox calbox = new HBox(calories, caloriestext);

					Label fat = new Label("Fat: ");
					fat.setTranslateX(10);
					TextField fattext = new TextField();
					fattext.setTranslateX(50);
					HBox fatbox = new HBox(fat, fattext);

					Label carbs = new Label("Carbs:");
					carbs.setTranslateX(10);
					TextField carbtext = new TextField();
					carbtext.setTranslateX(39);
					HBox carbbox = new HBox(carbs, carbtext);

					Label fiber = new Label("Fiber: ");
					fiber.setTranslateX(10);
					TextField fibertext = new TextField();
					fibertext.setTranslateX(39);
					HBox fiberbox = new HBox(fiber, fibertext);

					Label protein = new Label("Protein: ");
					protein.setTranslateX(10);
					TextField prottext = new TextField();
					prottext.setTranslateX(28);
					HBox protbox = new HBox(protein, prottext);
					popupbox.getChildren().addAll(title, sep, blank, idbox, namebox, calbox, fatbox, carbbox, fiberbox,
							protbox, blank2, buttons);

					Stage popupStage = new Stage(StageStyle.TRANSPARENT);
					popupStage.initOwner(primaryStage);
					popupStage.initModality(Modality.APPLICATION_MODAL);
					Scene popupScene = new Scene(popupbox, 350, 300);
					popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					popupStage.setScene(popupScene);

					close.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							popupStage.hide();
						}
					});

					accept.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							FoodItem oneoff = new FoodItem(idtext.getText(), nametext.getText());
							try {
								if (Double.parseDouble(caloriestext.getText()) < 0
										|| Double.parseDouble(fattext.getText()) < 0
										|| Double.parseDouble(carbtext.getText()) < 0
										|| Double.parseDouble(fibertext.getText()) < 0
										|| Double.parseDouble(prottext.getText()) < 0) {
									throw new Exception();
								}
								oneoff.addNutrient("calories", Double.parseDouble(caloriestext.getText()));
								oneoff.addNutrient("fat", Double.parseDouble(fattext.getText()));
								oneoff.addNutrient("carbohydrate", Double.parseDouble(carbtext.getText()));
								oneoff.addNutrient("fiber", Double.parseDouble(fibertext.getText()));
								oneoff.addNutrient("protein", Double.parseDouble(prottext.getText()));
								foodlist.addFoodItem(oneoff);
								filteredList.getItems().add(oneoff);
								totalCount.setText("Total Count: " + filteredList.getItems().size());
								popupStage.hide();

							} catch (Exception e) {
								Label errormessage = new Label("All nutrients must be non-negative with no alpha char");
								popupbox.getChildren().clear();
								popupbox.getChildren().addAll(title, sep, blank, errormessage, idbox, namebox, calbox,
										fatbox, carbbox, fiberbox, protbox, blank2, buttons);
							}
						}
					});
					popupStage.show();
				}
			});

			// Add to meal
			addToMeal.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mealList.getItems().addAll(filteredList.getSelectionModel().getSelectedItems());

				}
			});

			// Remove from meal
			removeFromMeal.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mealList.getItems().remove(mealList.getSelectionModel().getSelectedIndex());
				}
			});

			filteredList.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					foodName.setText("Name: " + filteredList.getSelectionModel().getSelectedItem().getName());
					foodID.setText("ID: " + filteredList.getSelectionModel().getSelectedItem().getID());
					foodCalories.setText("Calories: "
							+ (int) filteredList.getSelectionModel().getSelectedItem().getNutrientValue("calories"));
					foodFat.setText(
							"Fat: " + (int) filteredList.getSelectionModel().getSelectedItem().getNutrientValue("fat"));
					foodCarbohydrates.setText("Carbohydrates: " + (int) filteredList.getSelectionModel()
							.getSelectedItem().getNutrientValue("carbohydrate"));
					foodFiber.setText("Fiber: "
							+ (int) filteredList.getSelectionModel().getSelectedItem().getNutrientValue("fiber"));
					foodProtein.setText("Protein: "
							+ (int) filteredList.getSelectionModel().getSelectedItem().getNutrientValue("protein"));
				}
			});

			refresh.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					double calories = 0, fat = 0, carb = 0, fiber = 0, protein = 0;
					for (int i = 0; i < mealList.getItems().size(); i++) {
						calories = calories + mealList.getItems().get(i).getNutrientValue("calories");
						fat = fat + mealList.getItems().get(i).getNutrientValue("fat");
						carb = carb + mealList.getItems().get(i).getNutrientValue("carbohydrate");
						fiber = fiber + mealList.getItems().get(i).getNutrientValue("fiber");
						protein = protein + mealList.getItems().get(i).getNutrientValue("protein");
					}
					mealCalories.setText("Calories: " + (int) calories);
					mealFat.setText("Fat: " + (int) fat);
					mealCarbohydrates.setText("Carbohydrates: " + (int) carb);
					mealFiber.setText("Fiber: " + (int) fiber);
					mealProtein.setText("Protein: " + (int) protein);
				}
			});

			Scene scene = new Scene(root, 864, 665);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Meal Planner");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}