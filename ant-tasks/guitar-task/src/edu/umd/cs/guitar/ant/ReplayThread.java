package edu.umd.cs.guitar.ant;

import java.lang.ProcessBuilder;
import java.lang.StringBuilder;
import java.lang.Thread;
import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

import org.apache.tools.ant.types.Path;

public class ReplayThread extends Thread {

    private ReplayerTask task;
    private Host hostInfo;
    private List<String> tests;
    private List<String> failures;

    public ReplayThread(ReplayerTask task, Host hostInfo,
                        List<String> tests, List<String> failures)
    {
        this.task = task;
        this.hostInfo = hostInfo;
        this.tests = tests;
        this.failures = failures;
    }

    public void run()
    {
        String currentTest;
        try {
            while (true) {
                currentTest = tests.remove(0);

                List<String> args = constructArgs(currentTest);
                int status = invokeJVM(args);
                if (status != 0) {
                    failures.add(currentTest);
                    task.activityLog(true, hostInfo.getName() +
                                     ": Test " + currentTest +
                                     " failed (" + tests.size() + " left)");
                } else {
                    task.activityLog(true, hostInfo.getName() +
                                     ": Test " + currentTest +
                                     " succeeded (" + tests.size() + " left)");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // No more elements to process, finish
        }
    }

    private int invokeJVM(List<String> args) {
        try {
            args.add(0, "java");

            ProcessBuilder pb = new ProcessBuilder(args);
            pb.directory(task.getProject().getBaseDir());
            pb.redirectErrorStream(true);

            Process process = pb.start();

            // Redirect stdout/stderr
            InputStream output = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(output);
            BufferedReader br = new BufferedReader(reader);
            String line;

            while ((line = br.readLine()) != null) {
                task.activityLog(false, line);
            }

            return process.waitFor();
        } catch ( Exception e ) {
            return 1;
        }
    }

    private List<String> constructArgs(String testfile) {
        ArrayList<String> args = new ArrayList<String>();
        args.addAll(constructClasspath());
        args.addAll(constructDatafile());
        args.add("edu.umd.cs.guitar.replayer.IceReplayerMain");
        args.addAll(constructGuiFile());
        args.addAll(constructEfgFile());
        args.add("--test-case-file");
        args.add(testfile);
        args.addAll(constructConfig());

        return args;
    }

    private List<String> constructClasspath() {
        List<String> args = new ArrayList<String>(2);

        Path classpath = task.getClasspath();
        if (classpath != null) {
            StringBuilder builder = new StringBuilder();
            String[] paths = classpath.list();

            if (paths.length > 0) {
                builder.append(paths[0]);
            }

            for (int i = 1; i < paths.length; i++) {
                builder.append(":").append(paths[i]);
            }

            args.add("-classpath");
            args.add(builder.toString());
            return args;
        }
        return args;
    }

    private List<String> constructGuiFile() {
        List<String> args = new ArrayList<String>(2);

        String guiFile = task.getGuiFile();
        if (guiFile != null && guiFile.trim().length() > 0) {
            args.add("--gui-file");
            args.add(guiFile.trim());
        }
        return args;
    }

    private List<String> constructEfgFile() {
        List<String> args = new ArrayList<String>(2);

        String efgFile = task.getEfgFile();
        if (efgFile != null && efgFile.trim().length() > 0) {
            args.add("--efg-file");
            args.add(efgFile);
        }
        return args;
    }

    private List<String> constructDatafile() {
        List<String> args = new ArrayList<String>(1);

        String datafile = task.getDatafile();
        if (datafile != null && datafile.trim().length() > 0) {
            args.add("-Dnet.sourceforge.cobertura.datafile=" + datafile);
        }
        return args;
    }

    private List<String> constructConfig() {
        List<String> args = new ArrayList<String>(2);

        String config = hostInfo.getConfig();
        if (config != null && config.trim().length() > 0) {
            args.add("--config");
            args.add(config);
        }
        return args;
    }
}
