package com.atomiccomics.sphinx.ui.persistence;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.atomiccomics.survey.core.Answer;
import com.atomiccomics.survey.engine.SurveyBlackboard;

public class SimpleBlackboard implements SurveyBlackboard {

	private final ConcurrentHashMap<String, Answer> answers = new ConcurrentHashMap<>();
	
	@Override
	public Optional<? extends Answer> check(String question) {
		return Optional.ofNullable(answers.get(question));
	}

	@SaveData
	@Override
	public void answer(String question, Answer answer) {
		answers.put(question, answer);
	}

}