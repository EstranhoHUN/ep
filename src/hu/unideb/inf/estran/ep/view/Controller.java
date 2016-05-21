package hu.unideb.inf.estran.ep.view;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import hu.unideb.inf.estran.ep.core.EvolutionEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

public class Controller implements Initializable{

    @FXML
    private Button button;

    @FXML
    private Pane pane;

    @FXML
    private SplitPane spane;

    @FXML
    private LineChart<Number, Number> lChart;

    @FXML
	private void onClickButton(ActionEvent event) {






    	NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Fitness");
      //  yAxis.setLabel("Cycles");


        lChart.setTitle("Line Chart");
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("My Data");
        // populating the series with data
        series.getData().add(new XYChart.Data<Number, Number>(1, 23));
        series.getData().add(new XYChart.Data<Number, Number>(2, 114));
        series.getData().add(new XYChart.Data<Number, Number>(3, 15));
        series.getData().add(new XYChart.Data<Number, Number>(4, 124));


        EvolutionEngine ee = initializeEvolutionEngine();
    	ee.evolution(1, 0); //method  0: truncation, 1: wheel(best), else: random
    						 //weight -1: random, 0: defined by fitness(best), else: given rate
    //	series = ee.getData(); - pojo to series
  /*
        XYChart.Series<Number, Number> series_ = new XYChart.Series<>();
        series = series_;
*/
       lChart.getData().add(series);
       //lChart.getData().add(Arrays.asList(new XYChart.Data<Integer, Integer>(1, 23)));



	}

	private EvolutionEngine initializeEvolutionEngine() {

		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers = "0123456789";
		String symbols = "+-*/%=<>()[]{}.,;!? _-&|$*'";

		String alpha = "HAlL oWXrld!";
		String omega = "Hello World!"; //hosszúnál elszállt

		int populationSize = 100;
		int maxCycle = 100;

		EvolutionEngine ee = new EvolutionEngine(populationSize, omega.length(), alphabet+ALPHABET+symbols, omega, maxCycle);
		EvolutionEngine ee_ = new EvolutionEngine(populationSize, omega.length(), alphabet+ALPHABET+symbols, alpha, omega, maxCycle);




		return ee;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	//	pane.set

	}

}