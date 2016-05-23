package hu.unideb.inf.estran.ep.view;

import java.beans.EventHandler;
import java.net.URL;
import java.sql.Savepoint;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import hu.unideb.inf.estran.ep.core.EvolutionEngine;
import hu.unideb.inf.estran.ep.dao.ProjectService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Controller implements Initializable{

    @FXML
    private Button runButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField alphaField;
    @FXML
    private TextField omegaField;
    @FXML
    private LineChart<Number, Number> lChart;
    @FXML
    private TextArea console;
    @FXML
    private Pane pane;
    @FXML
    private Pane paneConsole;
    @FXML
    private ChoiceBox<String> methodChoiceBox;
    @FXML
    private ChoiceBox<String> crossOverChoiceBox;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private TextField cyclesField;
    @FXML
    private TextField populationSizeField;
    @FXML
    private RadioButton differentParentsRadioButton;
    @FXML
    private ChoiceBox<String> mutationRateChoiceBox;
    @FXML
    private RadioButton ucAlphabetRadioButton;
    @FXML
    private RadioButton lcAlphabetRadioButton;
    @FXML
    private RadioButton numbersRadioButton;
    @FXML
    private RadioButton symbolsRadioButton;
    @FXML
    private ListView<String> projectsListView;
    @FXML
    private TextField projectNameTextField;
    @FXML
    private Button newButton;
    @FXML
    private Button deleteButton;



	private Vector<Integer> averageFitness;
	private String allTimeFittestGenome;
	private Vector<Integer> peakFitness;
	private static Vector<Project> projects;
	private int allTimePeakFitness;
	private int actualListMember;

	@FXML
	private void onClickRunButton(ActionEvent event) {


    	validateFields();

        EvolutionEngine ee = initializeEvolutionEngine();
    	ee.evolution();

    	averageFitness = ee.getAverageFitness();
    	allTimeFittestGenome = ee.getAllTimeFittestGenome();
    	allTimePeakFitness = ee.getAllTimePeakFitness();
    	peakFitness = ee.getPeakFitness();

    	updateGraph();
        updateConsole();

	}

    @SuppressWarnings("unchecked")
	private void updateGraph(){


    	XYChart.Series<Number, Number> average;
    	XYChart.Series<Number, Number> peak;
    	average = new XYChart.Series<>(); average.setName("Average");
        peak = new XYChart.Series<>(); peak.setName("Peak");

    	xAxis.setUpperBound(Integer.parseInt(cyclesField.getText())*1.05);

    	average.getData().clear();
    	int index = 0;
        for(int i : averageFitness) average.getData().add(new XYChart.Data<Number, Number>(++index, i));
        index = 0;
        for(int i : peakFitness) peak.getData().add(new XYChart.Data<Number, Number>(++index, i));

        lChart.getData().retainAll();
        lChart.getData().add(average);
        lChart.getData().add(peak);
    }

    private void updateConsole(){
    	 String sMutationRate = mutationRateChoiceBox.getValue()=="OFF"?"0%":mutationRateChoiceBox.getValue();
         String sSeed = alphaField.getText().equals("")?"\t Was not seeded.":"\tWhit seed: \""+alphaField.getText()+"\".";
         String sDifferentParents = differentParentsRadioButton.isSelected()?"ON.":"OFF.";

         console.setText(
        		  "Procet name: " + projectNameTextField.getText()
        		+ "\nBest candidate:\t\"" + allTimeFittestGenome + "\" (" + allTimePeakFitness + "% match)."
         		+ "\t Evolutionary goal: \"" + omegaField.getText() + "\"."
         		+ sSeed
         		+ "\nPopulation size: " + populationSizeField.getText()
         		+ ".\t Maximum " + cyclesField.getText() + " generations."
         		+ "\tDifferent parents option: " + sDifferentParents
         		+ "\nParent-selection method: " + methodChoiceBox.getValue()
         		+ ". Crossover method: " + crossOverChoiceBox.getValue()
         		+ ". With "  + sMutationRate + " mutation rate."
         		);
    }

	private EvolutionEngine initializeEvolutionEngine() {

		String alphabet = lcAlphabetRadioButton.isSelected()?"abcdefghijklmnopqrstuvwxyz":"";
		String ALPHABET = ucAlphabetRadioButton.isSelected()?"ABCDEFGHIJKLMNOPQRSTUVWXYZ":""; //
		String numbers  = numbersRadioButton.isSelected()?"0123456789":""; //
		String symbols  = symbolsRadioButton.isSelected()?"+-*/%=<>().,;!? _-&|$*'":""; //
		String finalAlphabet = alphabet + ALPHABET + numbers + symbols;

		String alpha = alphaField.getText();
		String omega = omegaField.getText();

		int method = methodChoiceBox.getValue()=="Roulette Wheel"?1:methodChoiceBox.getValue()=="Truncation"?0:-1;
		int weight = crossOverChoiceBox.getValue()=="Weighted by fitness"?0:crossOverChoiceBox.getValue()=="Random"?-1:3;
		int mutationRate = mutationRateChoiceBox.getValue()=="10%"?1:mutationRateChoiceBox.getValue()=="20%"?2:mutationRateChoiceBox.getValue()=="30%"?3:mutationRateChoiceBox.getValue()=="40%"?4:mutationRateChoiceBox.getValue()=="50%"?5:0;
		boolean differentParents = differentParentsRadioButton.isSelected();

		int populationSize = Integer.parseInt(populationSizeField.getText());
		int maxCycle = Integer.parseInt(cyclesField.getText());

		EvolutionEngine ee = new EvolutionEngine(populationSize, omega.length(), finalAlphabet, alpha, omega, maxCycle, method, weight, mutationRate, differentParents);

		return ee;
	}

	private void validateFields() {

		int populationSizeFieldCheck;
		try {
			populationSizeFieldCheck = Integer.parseInt(populationSizeField.getText());
		} catch (NumberFormatException e) {
		    populationSizeFieldCheck = 100;
		}
		populationSizeFieldCheck = populationSizeFieldCheck < 10 ? 10 : populationSizeFieldCheck > 1000 ? 1000 : populationSizeFieldCheck;
		populationSizeField.setText(Integer.toString(populationSizeFieldCheck));

		int cyclesFieldCheck;
		try {
			cyclesFieldCheck = Integer.parseInt(cyclesField.getText());
		} catch (NumberFormatException e) {
			cyclesFieldCheck = 100;
		}
		cyclesFieldCheck = cyclesFieldCheck < 10 ? 10 : cyclesFieldCheck > 1000 ? 1000 : cyclesFieldCheck;
		cyclesField.setText(Integer.toString(cyclesFieldCheck));

		if(!(ucAlphabetRadioButton.isSelected()||lcAlphabetRadioButton.isSelected()||numbersRadioButton.isSelected()||symbolsRadioButton.isSelected()))
			ucAlphabetRadioButton.setSelected(true);


	}

	public void loadProjectsFromDao() {
		ProjectService projectService = new ProjectService();
		projects = projectService.loadProjects();

	}

	//public void loadProjectsFromVector() {

//	}//todo

	private void saveProjectsToVector() { //todo

			validateFields();
			if(actualListMember != -1) {

			projects.set(actualListMember,new Project(
					projectNameTextField.getText(),
					Boolean.valueOf(lcAlphabetRadioButton.isSelected()),
					Boolean.valueOf(ucAlphabetRadioButton.isSelected()),
					Boolean.valueOf(numbersRadioButton.isSelected()),
					Boolean.valueOf(symbolsRadioButton.isSelected()),
					alphaField.getText(),
					omegaField.getText(),
					methodChoiceBox.getSelectionModel().getSelectedIndex(),
					crossOverChoiceBox.getSelectionModel().getSelectedIndex(),
					mutationRateChoiceBox.getSelectionModel().getSelectedIndex(),
					Boolean.valueOf(differentParentsRadioButton.isSelected()),
					Integer.valueOf(populationSizeField.getText()),
					Integer.valueOf(cyclesField.getText())
					)
				);

			loadGUI();
			}
			}



	@FXML
	public void onClickProjectListView(MouseEvent event) {

		saveProjectsToVector();

		actualListMember = projectsListView.getSelectionModel().getSelectedIndex();

		loadGUI();
    }


	private void clearGUI() {

		 projectNameTextField.setText("New Project");
		 omegaField.setText("Hello World!");
		 alphaField.setText("");

		 populationSizeField.setText("100");
		 cyclesField.setText("100");
		 differentParentsRadioButton.setSelected(true);
		 ucAlphabetRadioButton.setSelected(true);
		 lcAlphabetRadioButton.setSelected(true);
		 numbersRadioButton.setSelected(false);
		 symbolsRadioButton.setSelected(true);

		 methodChoiceBox.getSelectionModel().selectFirst();
		 crossOverChoiceBox.getSelectionModel().selectFirst();
		 mutationRateChoiceBox.getSelectionModel().selectFirst();

		 updateProjectList();


	}

	private void loadGUI() {

		 projectNameTextField.setText(projects.elementAt(actualListMember).getProjectName());
		 omegaField.setText(projects.elementAt(actualListMember).getOmega());
		 alphaField.setText(projects.elementAt(actualListMember).getAlpha());
		 methodChoiceBox.getSelectionModel().select(projects.elementAt(actualListMember).getMethod());
		 crossOverChoiceBox.getSelectionModel().select(projects.elementAt(actualListMember).getWeight());
		 mutationRateChoiceBox.getSelectionModel().select(projects.elementAt(actualListMember).getMutationRate());
		 populationSizeField.setText(String.valueOf(projects.elementAt(actualListMember).getPopulationSize()));
		 cyclesField.setText(String.valueOf(projects.elementAt(actualListMember).getMaxCycle()));
		 differentParentsRadioButton.setSelected(projects.elementAt(actualListMember).isDifferentParents());
		 ucAlphabetRadioButton.setSelected(projects.elementAt(actualListMember).isALPHABET());
		 lcAlphabetRadioButton.setSelected(projects.elementAt(actualListMember).isAlphabet());
		 numbersRadioButton.setSelected(projects.elementAt(actualListMember).isNumbers());
		 symbolsRadioButton.setSelected(projects.elementAt(actualListMember).isSymbols());

		 updateProjectList();


	}

	public static void onExit() {
		ProjectService projectService = new ProjectService();
		projectService.saveProjects(projects);
	}

	private void updateProjectList() {

		ObservableList<String> projectNames = FXCollections.observableArrayList ();
		for(int i = 0;i<projects.size();i++)projectNames.add(i+1+"# " + projects.get(i).getProjectName());
	    projectsListView.setItems(projectNames);

	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadProjectsFromDao();



	 console.setStyle("-fx-control-inner-background: black; -fx-text-fill: lime;");
	 console.setText("Please set options manually or choose a Project from the list, then click \"Run\".\nNew Projects "
	 		+ "can be created and existing ones can be modified or deleted.\n");

	 methodChoiceBox.setItems(FXCollections.observableArrayList("Roulette Wheel", "Truncation", "Random"));
	 crossOverChoiceBox.setItems(FXCollections.observableArrayList("Weighted by fitness", "Random", "Exact 75%"));
	 mutationRateChoiceBox.setItems(FXCollections.observableArrayList("10%", "20%", "30%", "40%","50%", "OFF"));

	 clearGUI();

	 actualListMember = -1;


	}



	//ELITISM

	@FXML
	private void onClickNewButton(ActionEvent event) {

		projects.add(new Project("New Project",true,true,false,true,"","Hello World!",0,0,1,true,100,100));

		updateProjectList();
	}

	@FXML
	private void onClickDeleteButton(ActionEvent event) {

		projects.remove(actualListMember);
		actualListMember=-1;
		clearGUI();

		}

}