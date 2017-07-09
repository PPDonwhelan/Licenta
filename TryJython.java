import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class TryJython {
    public static void main(String[] args) {

        PythonInterpreter interpreter = new PythonInterpreter();
        String fileUrlPath = "C:/Users/moldovanm2/Documents/Licenta_final";
        String scriptName = "helloworld";
        interpreter.exec("import sys\n" + "import os \n" + "sys.path.append('" + fileUrlPath + "')\n"+ "from "+scriptName+" import * \n");
        String funcName = "bla";
        PyObject someFunc = interpreter.get(funcName);
        if (someFunc == null) {
            //throw new Exception("Could not find Python function: " + funcName);
            System.out.print("bla");
        }
        try {
            someFunc.__call__(new PyString("ceva"));
        } catch (PyException e) {
            e.printStackTrace();
        }

    }
}