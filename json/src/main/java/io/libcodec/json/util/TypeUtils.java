package io.libcodec.json.util;

public class TypeUtils {
    public static boolean isProxy(Class<?> clazz) {
        // Check if the class name contains Spring Cloud RefreshScope indicators
        String className = clazz.getName();
        int p = className.indexOf('$');
        if (p != -1
                && (className.contains("$EnhancerBySpringCGLIB$")
                || className.contains("$EnhancerByCGLIB$")
                || className.contains("$FastClassBySpringCGLIB$")
                || className.contains("$FastClassByCGLIB$")
                || className.contains("$EnhancerBySpringCGLIB$")
                || className.contains("$EnhancerByCGLIB$"))) {
            return true;
        }

        // Check interfaces for RefreshScope marker or other proxy indicators
        for (Class<?> item : clazz.getInterfaces()) {
            String interfaceName = item.getName();
            switch (interfaceName) {
                case "org.springframework.cloud.context.config.annotation.RefreshScope":
                case "org.springframework.cglib.proxy.Factory":
                case "javassist.util.proxy.ProxyObject":
                case "org.apache.ibatis.javassist.util.proxy.ProxyObject":
                case "org.hibernate.proxy.HibernateProxy":
                case "org.springframework.context.annotation.ConfigurationClassEnhancer$EnhancedConfiguration":
                case "org.mockito.cglib.proxy.Factory":
                case "net.sf.cglib.proxy.Factory":
                    return true;
                default:
                    // Check if interface name contains Spring-related patterns
                    if (interfaceName.startsWith("org.springframework.cloud.context.config.annotation.") &&
                            interfaceName.endsWith("RefreshScope")) {
                        return true;
                    }
                    break;
            }
        }

        // Check if class name follows Spring proxy patterns
        if (className.contains("$SpringCGLIB$")
                || className.contains("$SpringCGLIB$")
                || className.contains("$EnhancerBySpringCGLIB$")) {
            return true;
        }

        return false;
    }
}
