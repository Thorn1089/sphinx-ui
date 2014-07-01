package com.atomiccomics.sphinx.ui.survey;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;

/**
 * The {@link QuestionController} defines a common interface for all
 * question types that are represented visually within the Sphinx application.
 * They can expose a simple {@link BooleanBinding} property that represents whether
 * the currently supplied answer is valid or invalid.
 * 
 * @author Tom
 *
 */
public interface QuestionController {

	/**
	 * @return A {@link BooleanBinding} representing whether the current answer
	 * encapsulated by this controller is valid or not.
	 */
	default BooleanBinding validProperty() {
		return Bindings.createBooleanBinding(() -> true);
	}
	
}
