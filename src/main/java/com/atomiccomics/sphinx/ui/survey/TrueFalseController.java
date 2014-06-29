package com.atomiccomics.sphinx.ui.survey;

import com.atomiccomics.survey.common.TrueFalseQuestion;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class TrueFalseController implements QuestionController {

	private Property<Boolean> answer = new SimpleBooleanProperty();
	
	@FXML
	private Text question;
	
	@FXML
	private RadioButton yes;
	
	@FXML
	private RadioButton no;
	
	@FXML
	private ToggleGroup answers;
	
	@FXML
	public void initialize() {
		answer.bind(yes.selectedProperty());
	}
	
	public void displayQuestion(final TrueFalseQuestion trueFalseQuestion) {
		question.textProperty().set(trueFalseQuestion.getQuestionText());
		yes.textProperty().set(trueFalseQuestion.getTrueText());
		no.textProperty().set(trueFalseQuestion.getFalseText());
	}

	@Override
	public BooleanBinding validProperty() {
		return Bindings.createBooleanBinding(() -> true);
	}
	
}
