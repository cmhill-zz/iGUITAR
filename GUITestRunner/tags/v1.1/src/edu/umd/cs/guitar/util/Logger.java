package edu.umd.cs.guitar.util;

import java.io.OutputStream;
import java.io.PrintStream;

public class Logger {
    private boolean logging = true;
    private boolean loggingToStandardOutput = true;
    private PrintStream output = System.out;

    public Logger() {
        // Empty.
    }

    public Logger(boolean loggingToStandardOutput) {
        this.loggingToStandardOutput = loggingToStandardOutput;
    }

    public void setLogging(boolean b) {
        this.logging = b;
    }

    public boolean isLogging() {
        return logging;
    }

    public void setOutput(OutputStream stream) {
        if (stream == null) {
            throw new NullPointerException("Cannot log to null dest!");
        } else {
            output = new PrintStream(stream);
        }
    }

    public PrintStream getOutput() {
        return output;
    }

    public synchronized void logln(String s) {
        if (logging) {
            output.println(s);
            if (loggingToStandardOutput && System.out != output) {
                System.out.println(s);
            }
        }
    }

    public synchronized void logln(Object obj) {
        logln(obj.toString());
    }

    public synchronized void log(String s) {
        if (logging) {
            output.print(s);
            output.flush();
            if (loggingToStandardOutput && System.out != output) {
                System.out.print(s);
                System.out.flush();
            }
        }
    }

    public synchronized void log(Object obj) {
        log(obj.toString());
    }
}
