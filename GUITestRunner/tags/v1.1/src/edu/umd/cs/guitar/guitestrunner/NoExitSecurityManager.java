package edu.umd.cs.guitar.guitestrunner;

public abstract class NoExitSecurityManager extends SecurityManager {
    public void checkExit(int status) {
        fireExit();
        StackTraceElement[] es = Thread.currentThread().getStackTrace();
        for (int i = 0; i < es.length; ++i) {
            String s = es[i].toString();
            if (s.contains("java.lang.Runtime.exit") || s.contains("java.lang.Runtime.halt")) {
                throw new SecurityException("Application under test exits!");
            }
        }
    }

    public void checkPermission(java.security.Permission perm, Object context) {
        // allow everything
    }

    public void checkPermission(java.security.Permission perm) {
        // allow everything
    }

    public abstract void fireExit();
}
