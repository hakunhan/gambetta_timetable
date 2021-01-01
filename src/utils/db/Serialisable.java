package utils.db;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @overview <code>Serialisable</code> concerns types or procedures that are subject
 *           to some form of Java storage (e.g. using a relational database to store 
 *           objects of a type). Its use is intended to be more flexible than the 
 *           default <code>java.io.Serializable</code> interface. 
 *            
 * @author dmle
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Serialisable {
}
