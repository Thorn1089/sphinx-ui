package com.atomiccomics.sphinx.ui.main;

import java.io.File;

import javax.inject.Inject;

import com.atomiccomics.sphinx.ui.survey.StartSurveyEvent;
import com.google.common.eventbus.EventBus;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The {@link DashboardController} handles events from the main application dashboard, shown on launch.
 * 
 * @author Tom
 */
public class DashboardController {

	private final EventBus eventBus;
	
	private Stage primaryStage;
	
	/**
	 * Creates a new {@link DashboardController} with the given {@link EventBus}.
	 * @param eventBus An {@code EventBus} used to communicate with different modules of the application.
	 */
	@Inject
	public DashboardController(final EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(this);
	}
	
	/**
	 * Sets the stage used to show the view associated with this controller.
	 * @param primaryStage A {@link Stage} which shows the dashboard view.
	 */
	public void setStage(final Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	/**
	 * Closes the application. Any displayed views are terminated.
	 */
	@FXML
	public void closeApplication() {
		eventBus.post(new CloseEvent());
	}
	
	/**
	 * Runs a survey by asking the user to select a survey file.
	 */
	@FXML
	public void runSurvey() {
		final FileChooser fileChooser = new FileChooser();
		final File file = fileChooser.showOpenDialog(primaryStage);
		if(file != null) {
			eventBus.post(new StartSurveyEvent(file));
		}
	}
	
}