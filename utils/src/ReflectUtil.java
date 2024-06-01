import java.lang.reflect.*;

import com.nqzero.permit.Permit;
import sun.reflect.ReflectionFactory;

public class ReflectUtil {
    public static void setAccessible(AccessibleObject member) {
        // quiet runtime warnings from JDK9+
        Permit.setAccessible(member);
    }

    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            setAccessible(field);
        } catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }

    public static Object getFieldValue(final Object obj, final String fieldName) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Constructor<?> getFirstCtor(final String name) throws Exception {
        final Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        setAccessible(ctor);
        return ctor;
    }

    public static Object newInstance(String className, Object... args) throws Exception {
        return getFirstCtor(className).newInstance(args);
    }

    public static <T> T createWithoutConstructor(Class<T> classToInstantiate)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return createWithConstructor(classToInstantiate, Object.class, new Class[0], new Object[0]);
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T createWithConstructor(Class<T> classToInstantiate, Class<? super T> constructorClass, Class<?>[] consArgTypes, Object[] consArgs)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? super T> objCons = constructorClass.getDeclaredConstructor(consArgTypes);
        setAccessible(objCons);
        Constructor<?> sc = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(classToInstantiate, objCons);
        setAccessible(sc);
        return (T) sc.newInstance(consArgs);
    }

    public static Method getMethodByClass(Class cs, String methodName, Class[] parameters) {
        Method method = null;
        while (cs != null) {
            try {
                method = cs.getDeclaredMethod(methodName, parameters);
                method.setAccessible(true);
                cs = null;
            } catch (Exception e) {
                cs = cs.getSuperclass();
            }
        }
        return method;
    }

    public static Object getMethodAndInvoke(Object obj, String methodName, Class[] parameterClass, Object[] parameters) {
        try {
            java.lang.reflect.Method method = getMethodByClass(obj.getClass(), methodName, parameterClass);
            if (method != null)
                return method.invoke(obj, parameters);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static void bypassModule(Class currentClass) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class unsafeClass = Class.forName("sun.misc.Unsafe");
        Field field = getField(unsafeClass, "theUnsafe");
        Object unsafe = field.get(null);
        Object baseModule = Class.class.getMethod("getModule").invoke(Object.class);
        long offset = (long) getMethodAndInvoke(unsafe, "objectFieldOffset", new Class[]{Field.class}, new Object[]{Class.class.getDeclaredField("module")});
        getMethodAndInvoke(unsafe, "putObject", new Class[]{Object.class, long.class, Object.class}, new Object[]{currentClass, offset, baseModule});
    }
}
