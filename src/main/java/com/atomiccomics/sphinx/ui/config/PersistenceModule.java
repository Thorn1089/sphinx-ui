package com.atomiccomics.sphinx.ui.config;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.subclassesOf;

import com.atomiccomics.sphinx.ui.persistence.SaveData;
import com.atomiccomics.sphinx.ui.persistence.SimpleBlackboard;
import com.atomiccomics.sphinx.ui.persistence.SurveyDataPersister;
import com.atomiccomics.survey.engine.SurveyBlackboard;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class PersistenceModule extends AbstractModule {

	@Override
	protected void configure() {
		bindInterceptor(subclassesOf(SurveyBlackboard.class), 
				annotatedWith(SaveData.class), 
				new SurveyDataPersister());
	}
	
	@Provides
	private SurveyBlackboard blackboard() {
		return new SimpleBlackboard();
	}

}
