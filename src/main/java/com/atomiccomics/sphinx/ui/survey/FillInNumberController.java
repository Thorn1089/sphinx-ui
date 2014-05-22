package com.atomiccomics.sphinx.ui.survey;

import com.atomiccomics.survey.common.FillInNumberQuestion;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FillInNumberController {

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
	
}
