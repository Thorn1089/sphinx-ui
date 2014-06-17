package com.atomiccomics.sphinx.ui.persistence;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.atomiccomics.survey.core.Answer;

public class SurveyDataPersister implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		final Object[] args = invocation.getArguments();
		final String questionId = (String)args[0];
		final Answer answer = (Answer)args[1];
		return invocation.proceed();
	}

}
