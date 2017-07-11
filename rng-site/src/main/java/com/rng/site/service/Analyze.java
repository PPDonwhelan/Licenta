package com.rng.site.service;

import org.python.core.PyArray;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moldovanm2 on 7/11/2017.
 */
public class Analyze {
    private PyObject python_function=null;

    public Analyze(){
        PythonInterpreter interpreter = new PythonInterpreter();
        String fileUrlPath = "C:/Users/moldovanm2/Documents/Licenta_final";
        String scriptName = "rngtests";
        interpreter.exec("import sys\n" + "import os \n" + "sys.path.append('" + fileUrlPath + "')\n"+ "from "+scriptName+" import * \n");
        String funcName = "detail";
        python_function = interpreter.get(funcName);
        if (python_function == null) {
            //throw new Exception("Could not find Python function: " + funcName);
            System.out.print("bla");
        }

    }
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
    public Object[] analyze(String test_name,int[] sample)
    {
        try {
            PyObject result=null;
            result=this.python_function.__call__(new PyString("PokerTest"),new PyArray(Integer.class, sample));
            Object result_convert=null;
            result_convert=result.__tojava__(Object[].class);
            Object[] array_converted_result=convertToObjectArray(result_convert);
            return array_converted_result;
        } catch (PyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
