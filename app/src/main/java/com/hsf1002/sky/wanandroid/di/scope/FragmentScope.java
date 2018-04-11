package com.hsf1002.sky.wanandroid.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by hefeng on 18-4-11.
 */

@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
