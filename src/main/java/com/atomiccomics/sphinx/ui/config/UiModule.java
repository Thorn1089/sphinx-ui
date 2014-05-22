package com.atomiccomics.sphinx.ui.config;

import com.google.inject.AbstractModule;
import javafx.fxml.FXMLLoader;
import com.google.inject.Injector;
import com.google.inject.Provides;

/**
 * The {@link UiModule} configures Guice bindings for JavaFX.
 * 
 * @author Tom
 */
public class UiModule extends AbstractModule {

	@Override
	protected void configure() { }
	
	@Provides
	private FXMLLoader loader(final Injector injector) {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory((type) -> injector.getInstance(type));
		return loader;
	}

}
