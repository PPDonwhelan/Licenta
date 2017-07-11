package Lib.test.javatests;//package javatests;

import java.util.Date;

public class AnonInner
{
    public int doit() {
        Date d = new Date() {
                public int hashCode() {
                    return 2000;
                }
		//XXX: stuck compareTo to make the compiler happier.
		//     hopefully this doesn't mess up the test.
		public int compareToo(Object x) {
		    return 0;
		}
            };
        return d.hashCode();
    }
}
