package com.atomiccomics.sphinx.ui.persistence;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The {@link SaveData} annotation marks a method as one which should
 * persist the given data from the method arguments to a durable store.
 * 
 * @author Tom
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SaveData {

}
