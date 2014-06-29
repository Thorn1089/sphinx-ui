package com.atomiccomics.sphinx.ui.survey;

import static com.google.common.base.Optional.fromNullable;

import java.util.regex.Pattern;

import com.atomiccomics.survey.common.FillInNumberQuestion;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FillInNumberController implements QuestionController {
	
	private static final Pattern IS_INTEGER = Pattern.compile("^-?\\d+$");

	private final Property<String> answerText = new SimpleStringProperty();

	@FXML
	private Text question;
	
	@FXML
	private TextField answer;
	
	@FXML
	public void initialize() {
		answer.textProperty().bindBidirectional(answerText);
	}
	
	public void displayQuestion(final FillInNumberQuestion fillInQuestion) {
		question.textProperty().set(fillInQuestion.getQuestionText());
	}

	@Override
	public BooleanBinding validProperty() {
		return Bindings.createBooleanBinding(() -> 
			fromNullable(answerText.getValue()).transform((string) -> IS_INTEGER.matcher(string).matches()).or(false), 
			answerText);
	}
	
}
