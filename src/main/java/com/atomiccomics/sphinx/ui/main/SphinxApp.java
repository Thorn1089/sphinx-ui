package com.atomiccomics.sphinx.ui.main;

import java.io.IOException;

import com.atomiccomics.sphinx.ui.config.CommonModule;
import com.atomiccomics.sphinx.ui.config.PersistenceModule;
import com.atomiccomics.sphinx.ui.config.UiModule;
import com.atomiccomics.sphinx.ui.survey.StartSurveyEvent;
import com.atomiccomics.sphinx.ui.survey.SurveyController;
import com.atomiccomics.survey.engine.SurveyBlackboard;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SphinxApp extends Application {

	private Stage primaryStage;
	
	private Injector injector;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.injector = Guice.createInjector(new UiModule(), new CommonModule(), new PersistenceModule());
		
		final EventBus eventBus = injector.getInstance(EventBus.class);
		eventBus.register(this);
		
		final FXMLLoader loader = injector.getInstance(FXMLLoader.class);
		final Parent root = loader.load(getClass().getResourceAsStream("Dashboard.fxml"));
		final DashboardController controller = loader.getController();
		controller.setStage(primaryStage);
		
		final Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@Subscribe
	public void closeApplication(final CloseEvent event) {
		primaryStage.close();
	}
	
	@Subscribe
	public void startSurvey(final StartSurveyEvent event) {
		try {
			final FXMLLoader loader = injector.getInstance(FXMLLoader.class);
			final Parent root = loader.load(SphinxApp.class
					.getResourceAsStream("/com/atomiccomics/sphinx/ui/survey/SurveyFrame.fxml"));
			SurveyController controller = loader.getController();
			controller.loadSurvey(event.getSurvey(), injector.getInstance(SurveyBlackboard.class));
			final Scene scene = new Scene(root);
			
			Platform.runLater(() -> primaryStage.setScene(scene));
		} catch (IOException e) {
			throw new AssertionError("Could not load FXML resource. Please fix project setup", e);
		}
	}
	
	public static void main(String... args) {
		SphinxApp.launch(args);
	}

}