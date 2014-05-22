package com.atomiccomics.sphinx.ui.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;

/**
 * The {@link CommonModule} provides dependencies for types used widely throughout the entire application,
 * such as event bus dispatchers.
 * @author Tom
 */
public class CommonModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EventBus.class).toInstance(new EventBus());
		bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
	}

}
