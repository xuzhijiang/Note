package org.java.core.base.generics;

import java.util.List;

/**
 * Java Generic Classes and Subtyping(子类型)
 * 
 *  我们可  对泛型类或接口进行子类型化  通过extends或implementing它。
 * 
 *  ArrayList<E> implements List<E> that extends Collection<E>,
 *  因此ArrayList <String>是List <String>的子类型，
 * List <String>是Collection <String>的子类型。
 *
 * List <String>的子类型可以是MyList <String，Object>，MyList <String，Integer>等等。
 * @param <E>
 * @param <T>
 */
interface MyList<E, T> extends List<E>{

}
