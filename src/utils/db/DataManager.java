package utils.db;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import utils.FailureException;
import utils.ListMap;
import utils.NotPossibleException;

/**
 * A class to demonstrate how to: <br>
 * <ul>
 * <li>create an instance of a class and populates it with some test data
 * </ul>
 * 
 * @author dmle
 * 
 */
public class DataManager {

  // /////// Test Data Definitions ///////////////////////////////////////////
  private static final String[] ADDRESSES;
  private static final String[] PHONES;
  private static final String[] FLOWER_NAMES;
  private static final String[] COLOURS;
  private static final String[] NAMES;
  private static final Double[] PRICES;
  private static final String[] DATES;
  private static final Integer[] QUANTITIES;
  // /////// END Test Data Definitions
  // ///////////////////////////////////////////

  // random number generators
  private static Random genInt = null;
  private static Random genFloat = null;
  private static Random genChar = null;
  // a hash map contain the random id generators for domain classes
  private static Map genIDs;

  // static initialisers
  static {
    genInt = new Random();
    genFloat = new Random();
    genChar = new Random();
    genIDs = new HashMap();

    // /////// Test Data ///////////////////////////////////////////
    ADDRESSES = new String[] { "Cau Giay, Ha Noi", "Dong Da, Ha Noi",
        "Hai Ba Trung, Ha Noi", "Bach Mai, Ha Noi", "Mai Dong, Ha Noi",
        "Tu Liem, Ha Noi", "Co Nhue, Ha Noi" };

    PHONES = new String[] { "01253341234", "01253341235", "01253412343",
        "01253343412", "01252334134", "01253423431", "01252334134",
        "01213341234", "01213341235", "01213412343", "01213343412",
        "01212334134", "01213423431", "01212334134" };

    FLOWER_NAMES = new String[] { "Lily", "Rose", "Sunflower", "Tulips" };

    NAMES = new String[] { "Tran Van Thang", "Tran Thi Hai Linh",
        "Tran Nguyen Than", "Bui Van Tam", "Le Van Thang", "Le Thi Hai Linh",
        "Le Nguyen Than", "Le Van Tam", "Nguyen Van Thang",
        "Nguyen Thi Hai Linh", "Nguyen Nguyen Than", "Nguyen Van Tam",
        "Dinh Van Thang", "Dinh Thi Hai Linh", "Dinh Nguyen Than",
        "Dinh Van Tam" };

    COLOURS = new String[] { "Red", "Yellow", "Orange", "Blue", "Green",
        "Cyan", "Purple", "White" };

    PRICES = new Double[] { 5.0, 5.2, 5.5, 5.6, 5.7, 5.8, 6.0, 7.0, 7.2, 7.5,
        7.6, 7.7, 7.8, 7.0, 10.0, 10.2, 10.5, 10.6, 10.7, 10.8, 10.0, };

    DATES = getDates();

    QUANTITIES = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50,
        60, 70, 80, };
    // /////// END Test Data ///////////////////////////////////////////

  }

  /**
   * Create a new domain object of a given class from some given data values.
   * 
   * @param c
   *          a domain class
   * @param values
   *          a <code>ListMap(String,Object)</code> that maps the name of an
   *          attribute of <code>c</code> to a data value. The map entries are
   *          arranged in the order that they were added to the map.
   * @requires <code>c != null</code>, <code>values.size > 0</code> and the
   *           order of the entries in <code>values</code> may NOT be the same
   *           as the order of the parameters of the constructor of
   *           <code>c</code> that will be used to create a new instance.
   * @effects If no suitable constructor of <code>c</code> exists throws
   *          <code>NotPossibleException</code>, else if could not create a new
   *          instance of <code>c</code> using the constructor throws
   *          <code>FailureException</code>, else returns a new instance of
   *          <code>c</code>, whose attribute values are taken from
   *          <code>values</code>.
   */
  public static Object getObjectUnsorted(Class c, ListMap values)
      throws NotPossibleException, FailureException {

    Object o = null;
    try {

      // create a new object using the default constructor method
      Constructor[] cons = c.getDeclaredConstructors();

      // find the constructor that has the same signature as the attributes
      // specified in values
      Constructor co = null;
      Class[] paramTypes;
      List inputObjects = (List) values.values();
      List sortedInput = new ArrayList();
      OUTER: for (int i = 0; i < cons.length; i++) {
        co = cons[i];
        paramTypes = co.getParameterTypes();
        if (paramTypes.length == inputObjects.size()) {
          // match-sort the input objects according to the paramTypes order
          CONS: for (int k = 0; k < paramTypes.length; k++) {
            Class type = paramTypes[k];
            OBJ: for (Iterator oit = inputObjects.iterator(); oit.hasNext();) {
              Object obj = oit.next();
              // compare the object type with the parameter type
              if (type.equals(obj.getClass())
                  || (type.isPrimitive()
                      && (type.getName().equalsIgnoreCase(obj.getClass()
                          .getSimpleName())) || (type.getName().equals("int") && obj
                      .getClass().getSimpleName().equals("Integer")))) {
                if (!sortedInput.contains(obj)) { 
                  // in case some objects have the same type
                  // TODO: this does not guarantee the correct objects are passed
                  // in in the case that more than one input objects have the same type
                  sortedInput.add(obj);
                  break OBJ;
                }
              }
            } // end OBJ loop
          } // end CONS loop
        } // end IF

        if (paramTypes.length == sortedInput.size()) {
          // found the constructor
          break OUTER;
        }
        sortedInput.clear();
        co = null;
      } // end OUTER for loop

      if (co == null) {
        throw new NotPossibleException(
            "DataManager.getObject: could not find constructor matching the data values");
      }

      // System.out.println("constructor: " + co);

      o = co.newInstance(sortedInput.toArray());
    } catch (InstantiationException e) {
      throw new FailureException(
          "DataManager.getObject: failed to create a new instance for class: "
              + c.getName());
    } catch (IllegalAccessException e) {
      throw new FailureException(
          "DataManager.getObject: failed to create a new instance for class: "
              + c.getName());
    } catch (InvocationTargetException e) {
      throw new FailureException(
          "DataManager.getObject: failed to create a new instance for class: "
              + c.getName());
    }

    return o;
  }

  /**
   * Create a new domain object of a given class from some given data values.
   * 
   * @param c
   *          a domain class
   * @param values
   *          a <code>ListMap(String,Object)</code> that maps the name of an
   *          attribute of <code>c</code> to a data value. The map entries are
   *          arranged in the order that they were added to the map.
   * @requires <code>c != null</code>, <code>values.size > 0</code> and the
   *           order of the entries in <code>values</code> must be the same as
   *           the order of the parameters of the constructor of <code>c</code>
   *           that will be used to create a new instance.
   * @effects If no suitable constructor of <code>c</code> exists throws
   *          <code>NotPossibleException</code>, else if could not create a new
   *          instance of <code>c</code> using the constructor throws
   *          <code>FailureException</code>, else returns a new instance of
   *          <code>c</code>, whose attribute values are taken from
   *          <code>values</code>.
   */
  public static Object getObject(Class c, ListMap values)
      throws NotPossibleException, FailureException {

    Object o = null;
    try {

      // create a new object using the default constructor method
      Constructor[] cons = c.getDeclaredConstructors();

      // find the constructor that has the same signature as the attributes
      // specified in values
      Constructor co = null;
      Class[] paramTypes;
      List inputObjects = (List) values.values();
      OUTER: for (int i = 0; i < cons.length; i++) {
        co = cons[i];
        paramTypes = co.getParameterTypes();
        if (paramTypes.length == inputObjects.size()) {
          boolean match = true;
          CONS: for (int k = 0; k < paramTypes.length; k++) {
            Class type = paramTypes[k];
            Object obj = inputObjects.get(k);
            // compare the object type with the parameter type
            if (!type.equals(obj.getClass())) {
              if (type.isPrimitive()
                  && !(type.getName().equalsIgnoreCase(obj.getClass()
                      .getSimpleName()))) {
                if (!(type.getName().equals("int") && obj.getClass()
                    .getSimpleName().equals("Integer"))) {
                  match = false;
                  break CONS;
                }
              }
            }
          } // end CONS loop
          if (match) {
            // found the constructor
            break OUTER;
          }
        }
        co = null;
      } // end OUTER loop

      if (co == null) {
        throw new NotPossibleException(
            "DataManager.getObject: could not find constructor matching the data values");
      }

      // System.out.println("constructor: " + co);

      o = co.newInstance(values.values().toArray());
    } catch (InstantiationException e) {
      throw new FailureException(
          "DataManager.getObject: failed to create a new instance for class: "
              + c.getName());
    } catch (IllegalAccessException e) {
      throw new FailureException(
          "DataManager.getObject: failed to create a new instance for class: "
              + c.getName());
    } catch (InvocationTargetException e) {
      throw new FailureException(
          "DataManager.getObject: failed to create a new instance for class: "
              + c.getName());
    }

    return o;
  }

  /**
   * Automatically generate an object of a class <code>c</code>. This method
   * uses the Test Data Definitions and Test Data declarations in the header of
   * this file to generate the values for each of the attributes of the object.<br>
   * 
   * More specifically, for those attributes of the object that are 'covered' by
   * the declared test data arrays (e.g. attribute address is covered by the
   * ADDRESS array), the values of these attributes will come from the
   * corresponding arrays. For attributes that are not covered by the declared
   * test arrays, the attribute type (such as integral, float, boolean, or
   * String) is used to generate a random attribute value.<br>
   * 
   * Thus, only extend the Test Data Definition sections if you think that some
   * other attributes need to use some specific values. <br>
   * 
   * @param c
   *          a class of the object to be created
   * @return
   */
  public static Object getObject(Class c) {

    // obtain all the fields of this class
    // In Java, field means attribute
    List fields = ToolKit.getFields(c);

    Object o = null;
    String name = null;
    Object v = null;
    int index = -1;
    Random r = new Random();
    Object[] valueRanges = null;

    try {

      // create a new object using the default constructor method
      o = c.newInstance();

      // a loop to assign a value to each field
      for (Iterator it = fields.iterator(); it.hasNext();) {
        Field f = (Field) it.next();
        // get a random value for this field based on its name and type
        name = f.getName();
        // type = f.getType();

        valueRanges = null;
        v = null;

        if (ToolKit.isIDField(f)) {
          // id field needs a different value generator
          v = getNextID(c);
        } else {
          // if this attribute is covered by the Test Data Definitions then
          // use the specified arrays to generate a random but sensible value
          // for the attribute
          if (isAddress(f)) {
            // address field
            valueRanges = ADDRESSES;
          } else if (isPhone(f)) {
            // phone field
            valueRanges = PHONES;
          } else if (isFlowerName(c, f)) {
            // flower name field
            valueRanges = FLOWER_NAMES;
          } else if (isColour(f)) {
            // flower name field
            valueRanges = COLOURS;
          } else if (isName(f)) {
            // name field
            valueRanges = NAMES;
          } else if (isPrice(f)) {
            // price field
            valueRanges = PRICES;
          } else if (isDate(f)) {
            // date filed
            valueRanges = DATES;
          } else if (isQuantity(f)) {
            // quantity field
            valueRanges = QUANTITIES;
          }

          if (valueRanges != null) {
            index = r.nextInt(valueRanges.length);
            v = valueRanges[index];
          } else {
            // otherwise, the attribute is not covered by any test data
            // definitions,
            // in this case we use random value generator functions to generate
            // a random value
            // for the attribute
            if (isText(f)) {
              v = getNextString();
            } else if (isIntegral(f)) {
              v = getNextInteger();
            } else if (isFloat(f)) {
              v = getNextFloat();
            } else if (isBoolean(f)) {
              v = new Boolean(false);
            }
            // other types here...
          }
        }

        if (v == null) {
          System.err.println("Field " + name + " is not supported");
        }

        f.set(o, v);
      }
    } catch (Exception e) {
      System.err.println("Error processing field: " + c.getName() + "." + name);
      e.printStackTrace();
    }

    return o;
  }

  private static int getNextID(Class c) {
    Random r = (Random) genIDs.get(c);
    if (r == null) {
      r = new Random();
      genIDs.put(c, r);
    }

    return Math.abs(1 + r.nextInt(100));
  }

  /**
   * Generate a series of random dates that are after the current date
   * 
   * @return
   */
  private static String[] getDates() {
    Date d0 = Calendar.getInstance().getTime();
    String[] dates = new String[20];

    Date d = null;
    Random r = new Random();
    final long millis = 24 * 60 * 60 * 1000;
    DateFormat df = DateFormat.getDateInstance();

    // get random dates in the next two months
    for (int i = 0; i < dates.length; i++) {
      int n = r.nextInt(60);
      d = new Date(d0.getTime() + n * millis);
      dates[i] = df.format(d);
    }

    return dates;
  }

  private static int getNextInteger() {
    return Math.abs(1 + genInt.nextInt(10000));
  }

  private static float getNextFloat() {
    return Math.abs(1 + genFloat.nextFloat());
  }

  private static String getNextString() {
    // create a random string of 20 chars
    StringBuffer s = new StringBuffer();
    for (int i = 0; i < 20; i++) {
      s.append((char) ('A' + genChar.nextInt(26)));
    }

    return s.toString();
  }

  private static boolean isAddress(Field f) {
    String name = f.getName().toLowerCase();
    return (name.indexOf("address") > -1);
  }

  private static boolean isName(Field f) {
    String name = f.getName().toLowerCase();
    return (name.indexOf("name") > -1);
  }

  private static boolean isFlowerName(Class c, Field f) {
    String name = f.getName().toLowerCase();
    return (c.getSimpleName().equalsIgnoreCase("Flower") && name
        .indexOf("name") > -1);
  }

  private static boolean isDate(Field f) {
    String name = f.getName().toLowerCase();
    return (name.indexOf("date") > -1);
  }

  private static boolean isColour(Field f) {
    String name = f.getName().toLowerCase();
    return (name.indexOf("colour") > -1 || name.indexOf("color") > -1);
  }

  private static boolean isPhone(Field f) {
    String name = f.getName().toLowerCase();
    return (name.indexOf("phone") > -1);
  }

  private static boolean isPrice(Field f) {
    String name = f.getName().toLowerCase();
    return (name.indexOf("price") > -1);
  }

  private static boolean isQuantity(Field f) {
    String name = f.getName().toLowerCase();
    return (name.indexOf("quantity") > -1 || name.indexOf("qty") > -1);
  }

  private static boolean isText(Field f) {
    Class type = f.getType();
    return (type.equals(String.class));
  }

  private static boolean isIntegral(Field f) {
    Class type = f.getType();
    String name = type.getSimpleName();
    return (type.equals(Integer.class) || type.equals(Long.class)
        || name.equalsIgnoreCase("int") || name.equalsIgnoreCase("long")
        || name.equalsIgnoreCase("byte") || name.equalsIgnoreCase("short"));
  }

  private static boolean isFloat(Field f) {
    Class type = f.getType();
    String name = type.getSimpleName();
    return (type.equals(Float.class) || type.equals(Double.class)
        || name.equalsIgnoreCase("float") || name.equalsIgnoreCase("double"));
  }

  private static boolean isBoolean(Field f) {
    Class type = f.getType();
    String name = type.getSimpleName();
    return (type.equals(Boolean.class) || name.equalsIgnoreCase("boolean"));
  }

}
