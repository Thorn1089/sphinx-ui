package com.atomiccomics.sphinx.ui.survey;

import static com.google.common.base.Optional.fromNullable;

import com.atomiccomics.survey.common.FillInTextQuestion;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class FillInTextController implements QuestionController {

	private final Property<String> answerText = new SimpleStringProperty();
	
	@FXML
	private Text question;
	
	@FXML
	private TextArea answer;
	
	@FXML
	public void initialize() {
		answer.textProperty().bindBidirectional(answerText);
	}
	
	public void displayQuestion(final FillInTextQuestion fillInQuestion) {
		question.textProperty().set(fillInQuestion.getQuestionText());
	}
	
	@Override
	public BooleanBinding validProperty() {
		return Bindings.createBooleanBinding(() -> 
				fromNullable(answerText.getValue())
					.transform((string) -> !string.isEmpty())
					.or(false), 
				answerText);
	}

}
