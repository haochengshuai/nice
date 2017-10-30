package haocs.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ClaAndObj {
			
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, NoSuchFieldException, IllegalArgumentException, InvocationTargetException {
		// Class对象
		Class<?> cla = Class.forName("haocs.utils.reflect.entity.Test");
		// Class + Object 对应的实例对象
		Object newInstance = cla.newInstance();
		//Object的Class
		Class<? extends Object> class1 = newInstance.getClass();
		//访问 无参 公开普通方法
		Method method = class1.getMethod("getPubNotSta");
		method.invoke(newInstance, null);

		// 访问 无参 公开静态方法
		Method method2 = cla.getMethod("getPubStatic");
		method2.invoke(cla,null);

		// 访问 带参 公开静态方法
		Method method3 = cla.getMethod("getPubStatic",Long.class);
		method3.invoke(cla,10L);


		// 访问 带参 私有静态方法
		Method declaredMethod = cla.getDeclaredMethod("getPriStatic");
		declaredMethod.setAccessible(true);
		declaredMethod.invoke(cla);

		// 访问 带参 私有普通方法
		Object newInstance2 = cla.newInstance();
		Class<? extends Object> class2 = newInstance2.getClass();
		Method declaredMethod1 = class2.getDeclaredMethod("getPriNotSta");
		declaredMethod.setAccessible(true);
		declaredMethod.invoke(newInstance2);

		// 访问 静态公开变量
		Field field = cla.getField("str");
		Object object = field.get(null);
		System.out.println(object);


		// 访问 私有静态变量
		Field field1= cla.getDeclaredField("strPri");
		field1.setAccessible(true);
		Object object1 = field1.get(null);
		System.out.println(object1);

		// 访问 私有普通变量
		Field field3= cla.getDeclaredField("trPri");
		field3.setAccessible(true);
		Object object3 = field3.get(cla.newInstance());
		System.out.println(object3);

		// 访问 公开普通变量
		Field field4= cla.getField("tr");
		Object object4 = field4.get(cla.newInstance());
		System.out.println(object4);

		Class<?> type = field4.getType();
		Type genericType = field4.getGenericType();
		System.out.println(type);
		System.out.println(genericType);
		
		if(type.getName().equals("java.lang.String")){
			System.out.println("Eq");
		}
		
		
		}

	
}
