package com.atomiccomics.sphinx.ui.survey;

import com.atomiccomics.survey.common.Instructions;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class InstructionsController implements QuestionController {

	@FXML
	private Text instructionsText;
	
	public void setInstructions(final Instructions instructions) {
		instructionsText.textProperty().set(instructions.getQuestionText());
	}

	@Override
	public BooleanBinding validProperty() {
		return Bindings.createBooleanBinding(() -> true);
	}
	
}
