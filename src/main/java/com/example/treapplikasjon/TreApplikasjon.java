package com.example.treapplikasjon;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

/**
 * En applikasjon som generere et tre ved hjelp av rekursjon og JavaFX for grafisk fremstilling
 *
 */
public class TreApplikasjon extends Application {
    private final Random random = new Random();
    private final int canvasWidth = 600;
    private final int canvasHeight = 600;

    private final Canvas canvas = new Canvas(canvasWidth, canvasHeight);

    private final Slider recursionSlider = new Slider(1, 10, 5);
    private final Slider randomSlider = new Slider(0, 100, 10);
    private final Slider lengthSlider = new Slider(125, 200, 150);
    private final Slider angleSlider = new Slider(5, 90, 45);

    /**
     * Hovedmetoden for å starte applikasjonen.
     *
     * @param args Array av kommandolinjer argumenter.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starter applikasjonen og setter opp brukergrensesnitt.
     *
     * @param stage Top level kontainer for applikasjonen.
     */
    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                new Label("Rekursjons Nivå:"), recursionSlider,
                new Label("Tilfeldighet:"), randomSlider,
                new Label("Stamme lengde:"), lengthSlider,
                new Label("Grein vinkel:"), angleSlider,
                canvas
        );

        Scene scene = new Scene(root, canvasWidth, canvasHeight + 100);
        stage.setScene(scene);
        stage.setTitle("Oblig1");
        stage.show();

        updateTree();
        addListeners();
    }

    /**
     * Legger til listeners for å oppdatere treet når brukeren justerer på dem.
     */
    private void addListeners() {
        recursionSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> updateTree());
        angleSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> updateTree());
        lengthSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> updateTree());
        randomSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> updateTree());
    }

    /**
     * Oppdaterer treet ved å tegne det på nytt på canvas med de gjeldende verdiene.
     */
    private void updateTree() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        updateBranch(gc, (double) canvasWidth / 2, canvasHeight, -90, lengthSlider.getValue(), (int) recursionSlider.getValue());
    }

    /**
     * Oppdaterer en gren av treet ved hjelp av rekursjon.
     *
     * @param gc     Grafikkonteksten som brukes til å tegne grenen.
     * @param startX Start x koordinat for grenen.
     * @param startY Start y koordinat for grenen.
     * @param angle  Vinkelen for grenen i grader.
     * @param length Lengden på grenen i piksler.
     * @param level  Nivået på rekursjonen for denne grenen.
     */
    private void updateBranch(GraphicsContext gc, double startX, double startY, double angle, double length, int level) {
        if (level < 1 || length < 2) return;

        double angleRad = Math.toRadians(angle);
        double randomScale = randomSlider.getValue() / 100.0;

        // Beregner koordinatene til endepunktene ved hjelp av trigonometri
        double endX = startX + length * Math.cos(angleRad);
        double endY = startY + length * Math.sin(angleRad);

        gc.strokeLine(startX, startY, endX, endY);

        double newLength = length * (0.7 + 0.3 * random.nextDouble() * randomScale);
        double newAngle = angleSlider.getValue() * (0.5 + 1.0 * random.nextDouble() * randomScale);

        // Rekursiv kall for høyre og venstre gren
        updateBranch(gc, endX, endY, angle - newAngle, newLength, level - 1);
        updateBranch(gc, endX, endY, angle + newAngle, newLength, level - 1);
    }
}
