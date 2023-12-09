package com.sysw.suite.audit.domain;


/**
 * é abstract para evitar que um modelo implemente um value object
 * e implemente uma entity, que nao pode acontecer
 * porque ou modelo é definido pelo seu ID, uma entity
 * ou é definido pelos seus atributos, um valueObject
 */
public abstract class ValueObject {
}
