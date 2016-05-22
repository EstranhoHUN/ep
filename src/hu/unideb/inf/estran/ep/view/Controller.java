package hu.unideb.inf.estran.ep.view;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import hu.unideb.inf.estran.ep.core.EvolutionEngine;
import hu.unideb.inf.estran.ep.dao.ProjectService;
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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Controller implements Initializable{

    @FXML
    private Button runButton;

    @FXML
    private Button initButton;

    @FXML
    private TextField alphaField;

    @FXML
    private TextField omegaField;

    @FXML
    private LineChart<Number, Number> lChart;

    @FXML
    private TextField console;

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

	private Vector<Integer> averageFitness;
	private String allTimeFittestGenome;
	private Vector<Integer> peakFitness;
	private Vector<Project> projects;
	private int allTimePeakFitness;




/*
	@FXML
	private void onClickLoad(ActionEvent event) {
    	ProjectService projectService = new ProjectService();
    	projects = projectService.loadProjects();
	}
*/

    @SuppressWarnings("unchecked")
	@FXML
	private void onClickRunButton(ActionEvent event) {

        //lChart.setTitle("Evolution");
        XYChart.Series<Number, Number> average = new XYChart.Series<>(); average.setName("Average");

        XYChart.Series<Number, Number> peak = new XYChart.Series<>(); peak.setName("Peak");

        EvolutionEngine ee = initializeEvolutionEngine();
    	ee.evolution();

    	averageFitness = ee.getAverageFitness();
    	allTimeFittestGenome = ee.getAllTimeFittestGenome();
    	allTimePeakFitness = ee.getAllTimePeakFitness();
    	peakFitness = ee.getPeakFitness();


    	//for(int i : averageFitness) System.out.println(i);

    //	series = ee.getData(); - pojo to series
  /*
        XYChart.Series<Number, Number> series_ = new XYChart.Series<>();
        series = series_;
*/

    	average.getData().clear();
    	int index = 0;
        for(int i : averageFitness) average.getData().add(new XYChart.Data<Number, Number>(++index, i));
        index = 0;
        for(int i : peakFitness) peak.getData().add(new XYChart.Data<Number, Number>(++index, i));



       // lChart.getData().removeAll();
        //lChart.setAnimated(false);
     //
     //   lChart.getStyleClass().add("thick-chart");




        lChart.getData().retainAll();

     //   button.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
    //    lChart.setStyle("-fx-background-color: slateblue; -fx-stroke-width: 1;");
   //     average.nodeProperty().get().setStyle("-fx-stroke-width: 1;");

//        Node line = lChart.lookup(".default-color0.chart-series-area-line");
  //      line.setStyle("-fx-stroke: rgb(" + redColor + "," + greenColor + "," + blueColor + "); ");


//        average.nodeProperty().get().setStyle("-fx-stroke-width: 3;");
        lChart.getData().add(average);
        lChart.getData().add(peak);
        console.setText("Best candidate:     " + allTimeFittestGenome + " (with " + allTimePeakFitness + "% match)");


	}

	private EvolutionEngine initializeEvolutionEngine() {

		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers = "0123456789";
		String symbols = "+-*/%=<>()[]{}.,;!? _-&|$*'";

		String alpha = alphaField.getText();
		String omega = omegaField.getText();

		int method = methodChoiceBox.getValue()=="Roulette Wheel"?1:methodChoiceBox.getValue()=="Truncation"?0:-1;
		int weight = crossOverChoiceBox.getValue()=="Weighted by fitness"?0:crossOverChoiceBox.getValue()=="Random"?-1:3;
		int mutationRate = 2; //mutationRate 0-10 = 0%-100%
		boolean differentParents = true;


		int populationSize = 250;
		int maxCycle = 250;

		xAxis.setUpperBound(maxCycle*1.05);






		EvolutionEngine ee = new EvolutionEngine(populationSize, omega.length(), alphabet+ALPHABET+symbols, alpha, omega, maxCycle, method, weight, mutationRate, differentParents);
		EvolutionEngine ee_ = new EvolutionEngine(populationSize, omega.length(), alphabet+ALPHABET+symbols, alpha, omega, maxCycle, method, weight, mutationRate, differentParents);

		return ee;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	 omegaField.setText("Hello World!");
	 alphaField.setText("");
	 console.setText("Please set options, then click \"Run\".");

	 methodChoiceBox.setItems(FXCollections.observableArrayList("Roulette Wheel", "Truncation", "Random"));
	 methodChoiceBox.getSelectionModel().selectFirst();

	 crossOverChoiceBox.setItems(FXCollections.observableArrayList("Weighted by fitness", "Random", "Exact 75%"));
	 crossOverChoiceBox.getSelectionModel().selectFirst();

	}

	@FXML
	private void onClickInitButton(ActionEvent event) {
		/*
    	NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Fitness");
        yAxis.setLabel("Cycles");
*/

		/*ProjectService projectService = new ProjectService();
    	  projects = projectService.loadProjects();
		 */

	}

}