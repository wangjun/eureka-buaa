/**
 * 
 */
package cn.edu.buaa.soft.eureka.test;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * @author dell
 *
 */
public class TestMultiLib extends AndroidTestCase {
	
	public void testOutputLog(){
		System.out.println("this is a test case!");
		Log.i("TestLog","这是一个Log");
	}
}
