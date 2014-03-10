package unitestLibs;

import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
				fail("Something wrong with the reflection on newing instance of class: " + ctruct.getClass().getName());
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
				fail("Something wrong with the reflection on getting method: "  + name);
			}
			
			return method;
		}
		
		public static Field getPrivateField(Class cls, String name) {
			
			Field field = null;
			
			try {
				
				field = cls.getDeclaredField(name);
				field.setAccessible(true);
				
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				fail("Something wrong with the reflection on getting field: " + name);
			}
			
			
			return field;
		}
	}
	
}
