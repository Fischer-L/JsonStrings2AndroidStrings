package unitestLibs;

import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Uintility {
	
	public static class Reflection {
		
		public static Object newTestObj(Constructor ctruct, Object[] args) {
			
			Object testObj = null;
			
			try {
				
				ctruct.setAccessible(true);
				testObj = ctruct.newInstance(args);	
				
			} catch (InstantiationException
					 | IllegalAccessException
					 | IllegalArgumentException
					 | InvocationTargetException e
			) {
				e.printStackTrace();
				fail("Something wrong with the reflection on newing instance...");
			}	
			
			return testObj;
		}
		
		public static Method getPrivateMethod(Class cls, String name, Class[] params) {
			
			Method method = null;
			
			try {
				
				method = cls.getDeclaredMethod(name, params);
				method.setAccessible(true);
				
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				fail("Something wrong with the reflection on getting method...");
			}
			
			return method;
		}
	}
	
}
