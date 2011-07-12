package edu.umd.cs.guitar.ant;

import org.apache.tools.ant.BuildException;

public class Host
{
    private String name = "machine";
    private String configFile;

    public Host() {
    }

    public void setConfig(String configFile) {
        this.configFile = configFile;
    }

    public String getConfig() {
        if (configFile == null) {
            throw new BuildException("config is a required attribute of host");
        }
        return configFile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
