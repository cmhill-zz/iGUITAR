package edu.umd.cs.guitar.ant;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

public class ReplayerTask extends Task {

    private boolean verbose = false;

    private String guiFile = "Project.GUI.xml";
    private String efgFile = "Project.EFG.xml";
    private String datafile = "cobertura.ser";

    private Path testcase = null;
    private Path classpath = null;

    private List<Host> hosts = null;

    public ReplayerTask() {
    }

    public final void execute() throws BuildException {
        if (hosts.size() == 0) {
            throw new BuildException("no host configuration files given");
        }

        List<String> failures = Collections.synchronizedList(
            new ArrayList<String>());

        // Create synchronized list for the tests
        List<String> tests = Collections.synchronizedList(
            new LinkedList<String>(Arrays.asList(testcase.list())));

        // Create thread pool (take tests from the synchronized list)
        Queue<ReplayThread> threadPool = new LinkedList<ReplayThread>();
        for (Host h : hosts) {
            ReplayThread thread = new ReplayThread(this, h, tests, failures);
            thread.start();
            threadPool.add(thread);
        }

        // Wait for every thread in the pool to die
        while (threadPool.size() > 0) {
            ReplayThread thread = threadPool.poll();
            try {
                thread.join();
            } catch (InterruptedException e) {
                // Re-add this thread to the thread pool
                threadPool.add(thread);
            }
        }

        if (failures.size() > 0) {
            throw new BuildException(failures.size() + " test cases failed");
        }
    }

    public void setClasspath(Path classpath) {
        if (this.classpath == null) {
            this.classpath = classpath;
        } else {
            this.classpath.append(classpath);
        }
    }

    public Path createClasspath() {
        if (this.classpath == null) {
            this.classpath = new Path(getProject());
        }
        return this.classpath.createPath();
    }

    public Path getClasspath() {
        return classpath;
    }

    public void setTest(Path testcase) {
        if (this.testcase == null) {
            this.testcase = testcase;
        } else {
            this.testcase.append(testcase);
        }
    }

    public Path createTests() {
        if (this.testcase == null) {
            this.testcase = new Path(getProject());
        }
        return this.testcase.createPath();
    }

    public Host createHost() {
        if (hosts == null) {
            hosts = new ArrayList<Host>();
        }
        Host newHost = new Host();
        hosts.add(newHost);
        return newHost;
    }

    public void setGuiFile(String guiFile) {
        this.guiFile = guiFile;
    }

    public String getGuiFile() {
        return guiFile;
    }

    public void setEfgFile(String efgFile) {
        this.efgFile = efgFile;
    }

    public String getEfgFile() {
        return efgFile;
    }

    public void setDatafile(String datafile) {
        this.datafile = datafile;
    }

    public String getDatafile() {
        return datafile;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public final void activityLog(boolean terse, String message) {
        if (message != null) {
            if (terse || verbose) {
                System.out.println(message);
            }
        }
    }
}
