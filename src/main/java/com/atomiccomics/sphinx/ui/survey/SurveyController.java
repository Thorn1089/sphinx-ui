package com.atomiccomics.sphinx.ui.survey;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;

import com.atomiccomics.sphinx.ui.main.CloseEvent;
import com.atomiccomics.survey.common.FillInNumberQuestion;
import com.atomiccomics.survey.common.Instructions;
import com.atomiccomics.survey.common.StaticSection;
import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.engine.SurveyDriver;
import com.google.common.eventbus.EventBus;
import com.google.inject.Provider;

public class SurveyController {

	private final Provider<FXMLLoader> loaderProvider;
	
	private final EventBus eventBus;
	
	private final ExecutorService worker;
	
	@FXML
	private ScrollPane contentPane;
	
	private SurveyDriver driver;
	
	@Inject
	public SurveyController(final Provider<FXMLLoader> loaderProvider, final EventBus eventBus,
			final ExecutorService worker) {
		this.loaderProvider = loaderProvider;
		this.eventBus = eventBus;
		this.worker = worker;
	}
	
	public void loadSurvey(final Path survey) {
		//TODO Actually parse...for now, let's fake a survey with sample instructions
		final Instructions demoInstructions = new Instructions("I-01", (bb) -> true, "Please read these carefully");
		final Instructions second = new Instructions("I-02", (bb) -> true, "Did you really read them carefully? Go back.");
		final FillInNumberQuestion age = new FillInNumberQuestion("D-01", (bb) -> true, "How old are you?");
		final Section section = new StaticSection((bb) -> true, Arrays.asList(demoInstructions, second, age));
		driver = new SurveyDriver(section, (question) -> {
			final LoadQuestionTask task = new LoadQuestionTask(question);
			task.setOnSucceeded((event) -> {
				try {
					contentPane.setContent(task.get());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			worker.submit(task);
		}, () -> {
			eventBus.post(new CloseEvent());
		}, null);
	}
	
	@FXML
	public void next() {
		//TODO Handle answers
		driver.next();
	}
	
	@FXML
	public void previous() {
		//TODO Undo answers
		driver.previous();
	}
	
	private final class LoadQuestionTask extends Task<Node> {

		private final Question question;
		
		private LoadQuestionTask(final Question question) {
			this.question = question;
		}
		
		@Override
		protected Node call() throws Exception {
			if(question instanceof Instructions) {
				final FXMLLoader loader = loaderProvider.get();
				final Node panel = loader.load(SurveyController.class.getResourceAsStream("Instructions.fxml"));
				final InstructionsController instructions = loader.getController();
				instructions.setInstructions((Instructions)question);
				return panel;
			} else if(question instanceof FillInNumberQuestion) {
				final FXMLLoader loader = loaderProvider.get();
				final Node panel = loader.load(SurveyController.class.getResourceAsStream("FillInNumber.fxml"));
				final FillInNumberController fillInNumber = loader.getController();
				fillInNumber.displayQuestion((FillInNumberQuestion)question);
				return panel;
			} else {
				return null;
			}
		}
		
	}
	
}
