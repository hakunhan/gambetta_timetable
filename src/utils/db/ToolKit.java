package utils.db;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import utils.DomainConstraint;
import utils.NotFoundException;
import utils.NotPossibleException;

/**
 * A utility class that contains static methods for:
 * <ul>
 * <li>generating SQL statements from Java class and object.
 * <li>performing other common application functions
 * </ul>
 * 
 * @author dmle
 * 
 */
public class ToolKit {
  private ToolKit() {
  }

  public static void print(Object[] objs) {
    for (int i = 0; i < objs.length; i++) {
      print(objs[i]);
    }
  }

  public static void printMessage(String msg) {
    final String line = ("--------------------------------------------------");
    System.out.println(line);
    System.out.println(msg);
    System.out.println(line);
  }

  public static void print(Object o) {
    List fields = getFields(o.getClass());

    System.out.println(">Object: " + o);
    int nl = 0;

    for (Iterator it = fields.iterator(); it.hasNext();) {
      Field f = (Field) it.next();
      String name = f.getName();
      int tmp = name.length();
      if (tmp > nl)
        nl = tmp;
    }

    for (Iterator it = fields.iterator(); it.hasNext();) {
      Field f = (Field) it.next();
      String name = f.getName();
      Class type = f.getType();
      Object v = null;
      try {
        v = f.get(o);
      } catch (Exception ex) {
      }
      int vl = (v + "").length();
      String format = "%" + nl + "s   %" + vl + "s%n";
      System.out.format(format, name, v);
    }
  }

  /**
   * Obtain the attribute value of an object
   * 
   * @param o
   * @param attributeName
   * @return
   */
  public static Object getAttributeValue(Object o, String attributeName) {
    Field f = null;

    // the field could be of this class or of a super-class
    Class c = o.getClass();

    f = getField(c, attributeName);

    if (f != null) {
      try {
        return f.get(o);
      } catch (Exception ex) {
        return null;
      }
    } else {
      System.err.println("Attribute " + attributeName
          + " does not exist in the class " + o.getClass());
      return null;
    }

  }

  /**
   * Returns a declared field of a class.
   * 
   * @param c
   *          the class
   * @param attributeName
   *          the name of the field
   * @return {@link Field} object or <code>null</code> if not found
   */
  public static Field getField(Class c, String attributeName) {
    Field f = null;

    // the field could be of this class or of a super-class
    while (f == null && !c.equals(Object.class)) {
      try {
        f = c.getDeclaredField(attributeName);
      } catch (Exception e) {
        // try the super class
        c = c.getSuperclass();
      }
    }

    return f;
  }

  /**
   * Return the attributes of a class.
   * 
   * @param c
   *          a Java class
   * @param serialisable
   *          a boolean flag
   * @requires If <code>serialisable = true</code> then <code>c</code> must has
   *           a public method annotated with <code>@Serialisable</code>, which
   *           returns a <code>String[]</code> array containing the names of all
   *           the serialisable attributes that are defined either by this class
   *           or by one of its non-object super class.
   * 
   * @effects If <code>serialisable = true</code> and <code>c</code> does not
   *          contain the required annotated method or an error occured while
   *          executing this method throws <code>NotPossibleException</code>,
   *          otherwise returns the serialisable attributes specified by this
   *          method; else returns all the attributes of <code>c</code>. Each
   *          attribute is represented by a <code>Field</code> object and must
   *          be a primitive or <code>String</code> type.
   * 
   */
  public static List getFields(final Class c, final boolean serialisable)
      throws NotPossibleException {
    Field[] myFields = c.getDeclaredFields();
    List _fields = new ArrayList();
    Collections.addAll(_fields, myFields);

    Class superClass = c.getSuperclass();
    if (!superClass.equals(Object.class)) {
      // super-class is not required to support serialisable
      List parentFields = getFields(superClass, false); // superClass.getDeclaredFields();
      if (parentFields != null) {
        _fields.addAll(parentFields);
      }
    }

    String[] names = null;

    if (serialisable) {
      // find serialisable attributes
      // invoke the method annotated with @Serialisable to read the names
      // of the serialisable attributes
      Method serMethod = getSerialAnnotatedMethod(c);
      if (serMethod == null)
        throw new NotPossibleException(
            "ToolKit.getFields: could not find the required annotated method: "
                + c.getName());

      try {
        Object o = serMethod.invoke(null);
        if (!(o instanceof String[]))
          throw new NotPossibleException(
              "ToolKit.getFields: expected String[] but got: "
                  + o.getClass().getName());

        names = (String[]) o;
      } catch (Throwable t) {
        throw new NotPossibleException(
            "ToolKit.getFields: could not invoke the annotated method: "
                + serMethod.getName());
      }
    }

    List fields = new ArrayList();
    Class type = null;
    String n;
    boolean selected = false;
    Field f = null;
    if (names != null) { // get the specified attributes only
      for (int i = 0; i < names.length; i++) {
        selected = false;
        n = names[i];
        for (Iterator it = _fields.iterator(); it.hasNext();) {
          f = (Field) it.next();
          if (names[i].equals(f.getName())) {
            selected = true;
            break;
          }
        }

        if (!selected) {
          // something wrong, the specified attributes are not found
          throw new NotFoundException(
              "ToolKit.getFields: could not find attribute " + n);
        } else {
          type = f.getType();
          // if (!(type.isPrimitive() || type.equals(String.class)))
          // throw new NotPossibleException(
          // "ToolKit.getFields: attribute type not supported " + type);

          // move no and id fields to the front
          if (isIDField(f)) {
            fields.add(0, f);
          } else {
            fields.add(f);
          }
        }
      }
    } else { // get all attributes
      for (Iterator it = _fields.iterator(); it.hasNext();) {
        f = (Field) it.next();
        n = f.getName();
        if (!n.startsWith("this$")) {
          type = f.getType();
          // // only support the basic data type fields
          // if (!(type.isPrimitive() || type.equals(String.class)))
          // continue;

          // move no and id fields to the front
          if (isIDField(f)) {
            fields.add(0, f);
          } else {
            fields.add(f);
          }
        }
      } // end for loop
    }

    return fields;
  }

  /**
   * Find a method annotated by <code>Serialisable</code> of a given class
   * <code>c</code>.
   * 
   */
  private static Method getSerialAnnotatedMethod(Class c) {
    Method[] methods = c.getDeclaredMethods();

    if (methods == null)
      return null;

    Method m;
    for (int i = 0; i < methods.length; i++) {
      m = methods[i];
      if (m.isAnnotationPresent(Serialisable.class)) {
        return m;
      }
    }

    return null;
  }

  /**
   * Determines whether a class supports the <code>Serialisable</code>
   * annotation type.
   * 
   * @param c
   *          a class
   * @effects If <code>c</code> contains one method annotated with
   *          <code>Serialisable</code> type returns true, else returns false.
   */
  public static boolean isSerialisableSupported(Class c) {
    return (getSerialAnnotatedMethod(c) != null);
  }

  /**
   * Check if a type is a Java's built-in type
   * @param type  a <code>Class</code> object 
   * @effects     If <code>type</code> is one of the built-in types returns true, 
   *              else returns false.
   * @version 1.0 the supported built-in types are primitives (e.g. <code>Integer</code>, etc.) 
   *              and <code>String</code>.             
   */
  public static boolean isBuiltInType(Class type) {
    //TODO: add other built-in types here
    return (type.isPrimitive() || type.equals(String.class));
  }
  
  /**
   * @effects Returns the getter method of a given field of a class
   */
  private static Method getGetterMethod(Class c, Field f) {
    Method m = null;
    final String methodName = "get" + f.getName();
    Method[] methods = null;
    while (!c.equals(Object.class)) { // try c and non-object super-class of c
      methods = c.getDeclaredMethods();
      if (methods != null) {
        // getter method name (case insensitive), e.g. getID(), getYear(), etc.
        for (int i = 0; i < methods.length; i++) {
          m = methods[i];
          if (m.getName().equalsIgnoreCase(methodName)) {
            return m;
          }
        }
      }

      // try parent class (if any)
      c = c.getSuperclass();
    }

    return null;
  }

  /**
   * @effects Returns a <code>Constructor</code> method object that has the 
   *          same number of and argument types <code>fields</code>.
   */
  public static Constructor getConstructor(Class cls, List fields) {
    Constructor c = null;
    
    Constructor[] cons = cls.getConstructors();

    List checked = new ArrayList();
    
    OUTER: for (int i = 0; i < cons.length; i++) {
      c = cons[i];
      Class[] paramTypes = c.getParameterTypes();
      if (paramTypes.length == fields.size()) {
        // match-sort the input objects according to the paramTypes order
        CONS: for (int k = 0; k < paramTypes.length; k++) {
          Class type = paramTypes[k];
          OBJ: for (Iterator oit = fields.iterator(); oit.hasNext();) {
            Object obj = oit.next();
            if (checked.contains(obj)) {
              continue;
            }
            Class ft =((Field)obj).getType();
            
            // compare the object type with the parameter type
            if (type.equals(ft)
                || (type.isPrimitive()
                    && (type.getName().equalsIgnoreCase(ft
                        .getSimpleName())) || (type.getName().equals("int") && ft.getSimpleName().equals("Integer")))) {
                checked.add(obj);
                break OBJ;
            }
          } // end OBJ loop
        } // end CONS loop
      
        if (checked.size() == paramTypes.length) {
          // found constructor
          break OUTER;
        } 
      } // end IF
      c = null;
    } // end OUTER
    
    return c;
  }
  
  /**
   * Return all the attributes of a class.
   * 
   * @param c
   *          A class that will be used to create a relational table
   * @return
   */
  public static List getFields(Class c) {
    boolean serialisable = isSerialisableSupported(c);
    return getFields(c, serialisable);
  }

  public static String genCreate(Class c, boolean serialisable)
      throws NotPossibleException {
    // get the declared fields of this class
    List fields = getFields(c, serialisable);

    StringBuffer sb = null;

    DomainConstraint dc = null;
    final String DBTYPE_STRING = "varchar";
    final int DEFAULT_LENGTH = 100;

    if (!fields.isEmpty()) {
      sb = new StringBuffer("create table ");

      // table name is same as class name
      sb.append(c.getSimpleName()).append("(");

      for (int i = 0; i < fields.size(); i++) {
        Field f = (Field) fields.get(i);
        String name = f.getName();
        String typeName = f.getType().getSimpleName();
        // field type is either the native type or
        // the type specified in the DomainConstraint annotation of the field
        dc = f.getAnnotation(DomainConstraint.class);
        if (dc != null && !dc.type().equals("null")) {
          typeName = dc.type();
        }
        // Class type = f.getType();
        int length = DEFAULT_LENGTH;
        sb.append(name.toLowerCase()); // to lower case
        sb.append(" ");
        if (typeName.equals("String")) {
          // use varchar for String type
          if (dc != null) {
            length = dc.length();
          }
          sb.append(DBTYPE_STRING + "(" + length + ")");
          // sb.append("varchar(100)"); // default 100 chars
        } else if (f.getType().isPrimitive()) {
          // other types keep the same
          sb.append(typeName.toLowerCase());
        } else {
          throw new NotPossibleException(
              "ToolKit.genCreate: attribute type not supported " + typeName);
        }
        if (i < fields.size() - 1)
          sb.append(", ");
      }

      sb.append(")");
    }

    return (sb != null) ? sb.toString() : null;
  }

  /**
   * Generate an SQL CREATE statement for a Java class
   * 
   * @param c
   * @return
   */
  public static String genCreate(Class c) {
    return genCreate(c, false);
  }

  /**
   * Generate an SQL insert statement for an object. This is a short-cut for
   * {@link #genInsert(o, true)}.
   * 
   * @param o
   *          an object
   */
  public static String genInsert(Object o) throws NotPossibleException {
    return genInsert(o, false);
  }

  /**
   * Generate an SQL insert statement for an object.
   * 
   * @param o
   *          an object
   * @param serialisable
   *          a flag to indicate whether or not to read only the serialisable
   *          attributes of <code>o</code>
   * @requires The database table to which <code>o</code> is to be inserted must
   *           have been created by an SQL statement that had been generated by
   *           the method {@link #genCreate(Class)} class using the class of
   *           <code>o</code> as an argument.
   * 
   * @effects If some serialisable fields of <code>o</code> are not accessible
   *          throws <code>NotPossibleException</code>; else returns an SQL
   *          insert statement for <code>o</code>.
   */
  public static String genInsert(Object o, final boolean serialisable)
      throws NotPossibleException {
    // get the declared fields of this class
    Class c = o.getClass();
    List fields = getFields(c, serialisable);

    StringBuffer sb = null;

    if (!fields.isEmpty()) {
      sb = new StringBuffer("insert into  ");

      sb.append(c.getSimpleName()).append("(");

      // append column names
      for (int i = 0; i < fields.size(); i++) {
        Field f = (Field) fields.get(i);
        String name = f.getName();

        sb.append(name.toLowerCase()); // to lower case
        if (i < fields.size() - 1) {
          sb.append(",");
        }
      }

      sb.append(")");
      sb.append(" values(");

      // append values (object values)
      for (int i = 0; i < fields.size(); i++) {
        Field f = (Field) fields.get(i);
        String name = f.getName();
        String typeName = f.getType().getSimpleName();

        // field type is either the native type or
        // the type specified in the DomainConstraint annotation of the field
        DomainConstraint dc = f.getAnnotation(DomainConstraint.class);
        if (dc != null && !dc.type().equals("null")) {
          typeName = dc.type();
        }

        // Class type = f.getType();
        Object v = null;

        try {
          // support two field access methods:
          // (1) direct (requires: package-level)
          // (2) indirect through getter methods
          v = f.get(o); // method 1
        } catch (Exception e) {
          // try method 2
          Method getter = getGetterMethod(c, f);
          if (getter == null) {
            // could not access field value at all!
            throw new NotPossibleException(
                "ToolKit.genInsert: could not access field: " + name);
          } else {
            try {
              v = getter.invoke(o);
            } catch (Exception e2) {
              // could not invoke getter to get field value
              throw new NotPossibleException(
                  "ToolKit.genInsert: could not invoke getter: "
                      + getter.getName());
            }
          }
        }

        if (typeName.equals("String")) {
          sb.append("'" + v + "'");
        } else if (f.getType().isPrimitive()) {
          sb.append(v + ""); // to lower case
        } else {
          throw new NotPossibleException(
              "ToolKit.genInsert: attribute type not supported " + typeName);
        }

        if (i < fields.size() - 1) {
          sb.append(",");
        }
      }

    }

    sb.append(")");

    return sb.toString();
  }

  public static boolean isIDField(Field f) {
    return f.getName().indexOf("id") > -1 || f.getName().indexOf("no") > -1;
  }
}
