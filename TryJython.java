import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class TryJython {
    static Object[] convertToObjectArray(Object array) {
        Class ofArray = array.getClass().getComponentType();
        if (ofArray.isPrimitive()) {
            List ar = new ArrayList();
            int length = Array.getLength(array);
            for (int i = 0; i < length; i++) {
                ar.add(Array.get(array, i));
            }
            return ar.toArray();
        }
        else {
            return (Object[]) array;
        }
    }
    public static void main(String[] args) {
//        Properties props = new Properties();
////        props.put("python.packages.paths","C:/Python27/Lib");
//        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
//        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
//        props.put("python.import.site","false");
//
//        Properties preprops = System.getProperties();

//        PythonInterpreter.initialize(preprops, props, new String[0]);

        PythonInterpreter interpreter = new PythonInterpreter();
        String fileUrlPath = "C:/Users/moldovanm2/Documents/Licenta_final";
        String scriptName = "rngtests";
        interpreter.exec("import sys\n" + "import os \n" + "sys.path.append('" + fileUrlPath + "')\n"+ "from "+scriptName+" import * \n");
        String funcName = "detail";
        PyObject someFunc = interpreter.get(funcName);
        if (someFunc == null) {
            //throw new Exception("Could not find Python function: " + funcName);
            System.out.print("bla");
        }
        try {

            int[] sample={2,3,4,1,5,6,6,2,3,4,1,5,2,1,2,4,3,6,5,2,3,1,4,3,2,6,3,4,2,1,4,2,5,6,1,4,2,3,6,4,5,2,6,3,1,6,2,5,1,4};
            int[] sample2={7,2,4,5,8,3,2,10,1,6,1,5,3,2,6,2,3,7,8,10,10,5,4,10,4,5,1,9,1,9,4,3,2,1,4,7,4,5,9,8,2,1,4,10,3,9,5,2,1,4,3,7,8,10,9,2,7,8,6,5,4,2,9,7,10,4,3,10,1,2,9,3,1,4,7,6,10,9,3,5,1,9,10,7,5,4,10,5,8,2,1,8,7,6,2,3,4,10,9,1};

            PyFloat x=new PyFloat(1.635);
            PyFloat y=new PyFloat(12.59);
//            PyTuple tup= new PyTuple();
//            tup.add(1.635);
//            tup.add(12.59);
            PyObject a =null;
            a=someFunc.__call__(new PyString("PokerTest"),new PyArray(Integer.class, sample2),new PyInteger(11));
            System.out.print(a);

            Object b=null;
            b=a.__tojava__(Object[].class);
            Object[] c=convertToObjectArray(b);
//            Double c =(Double)b;
            System.out.println("object");
            System.out.println(c);
            for (int i = 0; i < c.length; i++) {
                System.out.println("objectiiii");
                System.out.println(c[i]);
            }


        } catch (PyException e) {
            e.printStackTrace();
        }


    }
}