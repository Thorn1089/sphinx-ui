package com.atomiccomics.sphinx.ui.survey;

import java.io.File;
import java.nio.file.Path;

/**
 * The {@link StartSurveyEvent} represents a request from one part of the application
 * to run the survey contained in the given file.
 * 
 * @author Tom
 */
public final class StartSurveyEvent {

	private final Path survey;
	
	/**
	 * Creates a new {@link StartSurveyEvent} using the given {@link Path} to a survey.
	 * @param survey A {@code Path} pointing to a survey file.
	 */
	public StartSurveyEvent(final Path survey) {
		this.survey = survey;
	}
	
	/**
	 * Creates a new {@link StartSurveyEvent} using the given {@link File survey file}. 
	 * @param survey A {@code File} pointing to a survey.
	 */
	public StartSurveyEvent(final File survey) {
		this(survey.toPath());
	}
	
	/**
	 * @return A {@link Path} containing a survey.
	 */
	public Path getSurvey() {
		return survey;
	}
	
}
