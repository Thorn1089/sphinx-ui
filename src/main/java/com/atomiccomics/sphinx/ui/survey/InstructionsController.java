package com.atomiccomics.sphinx.ui.survey;

import com.atomiccomics.survey.common.Instructions;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class InstructionsController implements QuestionController {

	@FXML
	private Text instructionsText;
	
	public void setInstructions(final Instructions instructions) {
		instructionsText.textProperty().set(instructions.getQuestionText());
	}
	
}
