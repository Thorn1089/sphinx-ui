package com.atomiccomics.sphinx.ui.survey;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import javax.inject.Inject;

import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;

import com.atomiccomics.sphinx.ui.main.CloseEvent;
import com.atomiccomics.survey.common.FillInNumberQuestion;
import com.atomiccomics.survey.common.FillInTextQuestion;
import com.atomiccomics.survey.common.Instructions;
import com.atomiccomics.survey.common.StaticSection;
import com.atomiccomics.survey.common.TrueFalseQuestion;
import com.atomiccomics.survey.core.Question;
import com.atomiccomics.survey.core.Section;
import com.atomiccomics.survey.engine.SurveyBlackboard;
import com.atomiccomics.survey.engine.SurveyDriver;
import com.google.common.eventbus.EventBus;
import com.google.inject.Provider;

public class SurveyController {

	private final Provider<FXMLLoader> loaderProvider;
	
	private final EventBus eventBus;
	
	private final ExecutorService worker;
	
	@FXML
	private ScrollPane contentPane;
	
	@FXML
	private Button back;
	
	@FXML
	private Button forward;
	
	private SurveyDriver driver;
	
	@Inject
	public SurveyController(final Provider<FXMLLoader> loaderProvider, final EventBus eventBus,
			final ExecutorService worker) {
		this.loaderProvider = loaderProvider;
		this.eventBus = eventBus;
		this.worker = worker;
	}
	
	public void loadSurvey(final Path survey, final SurveyBlackboard blackboard) {
		//TODO Actually parse...for now, let's fake a survey with sample instructions
		final Instructions demoInstructions = new Instructions("I-01", (bb) -> true, "Please read these carefully");
		final TrueFalseQuestion checkUnderstanding = new TrueFalseQuestion("I-02", (bb) -> true, "Did you really read them carefully?");
		final FillInNumberQuestion age = new FillInNumberQuestion("D-01", (bb) -> true, "How old are you?");
		final FillInTextQuestion aboutYou = new FillInTextQuestion("D-02", (bb) -> true, "Tell us about yourself.");
		final Section section = new StaticSection((bb) -> true, Arrays.asList(demoInstructions, checkUnderstanding, age, aboutYou));
		driver = new SurveyDriver(section, (question) -> {
			final LoadQuestionTask task = new LoadQuestionTask(question);
			task.setOnSucceeded((event) -> {
				try {
					task.get().accept(contentPane);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			task.setOnFailed((event) -> task.getException().printStackTrace());
			worker.submit(task);
		}, () -> {
			eventBus.post(new CloseEvent());
		}, blackboard);
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
	
	private final class LoadQuestionTask extends Task<Consumer<ScrollPane>> {

		private final Question question;
		
		private LoadQuestionTask(final Question question) {
			this.question = question;
		}
		
		@Override
		protected Consumer<ScrollPane> call() throws Exception {
			if(question instanceof Instructions) {
				final FXMLLoader loader = loaderProvider.get();
				final Node panel = loader.load(SurveyController.class.getResourceAsStream("Instructions.fxml"));
				final InstructionsController instructions = loader.getController();
				return (scrollPane) -> {
					instructions.setInstructions((Instructions)question);
					forward.disableProperty().bind(Bindings.not(instructions.validProperty()));
					scrollPane.setContent(panel);
				};
			} else if(question instanceof FillInNumberQuestion) {
				final FXMLLoader loader = loaderProvider.get();
				final Node panel = loader.load(SurveyController.class.getResourceAsStream("FillInNumber.fxml"));
				final FillInNumberController fillInNumber = loader.getController();
				return (scrollPane) -> {
					fillInNumber.displayQuestion((FillInNumberQuestion)question);
					forward.disableProperty().bind(Bindings.not(fillInNumber.validProperty()));
					scrollPane.setContent(panel);
				};
			} else if(question instanceof TrueFalseQuestion) {
				final FXMLLoader loader = loaderProvider.get();
				final Node panel = loader.load(SurveyController.class.getResourceAsStream("TrueFalse.fxml"));
				final TrueFalseController trueFalse = loader.getController();
				return (scrollPane) -> {
					trueFalse.displayQuestion((TrueFalseQuestion)question);
					forward.disableProperty().bind(Bindings.not(trueFalse.validProperty()));
					scrollPane.setContent(panel);
				};
			} else if(question instanceof FillInTextQuestion) { 
				final FXMLLoader loader = loaderProvider.get();
				final Node panel = loader.load(SurveyController.class.getResourceAsStream("FillInText.fxml"));
				final FillInTextController fillInText = loader.getController();
				return (scrollPane) -> {
					fillInText.displayQuestion((FillInTextQuestion)	question);
					forward.disableProperty().bind(Bindings.not(fillInText.validProperty()));
					scrollPane.setContent(panel);
				};
			} else {
				return (scrollPane) -> {};
			}
		}
		
	}
	
}
