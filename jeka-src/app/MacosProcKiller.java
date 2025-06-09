package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class MacosProcKiller implements ProcKiller {

    @Override
    public String pid(int port) throws Exception {
        // Find the process ID using the port
        Process findProcess = Runtime.getRuntime().exec(new String[]{"lsof", "-ti", "tcp:" + port});
        BufferedReader reader = new BufferedReader(new InputStreamReader(findProcess.getInputStream()));
        String pid = reader.readLine();
        reader.close();
        return pid;
    }

    @Override
    public int killPid(String pid) throws Exception {
        Process killProcess = Runtime.getRuntime().exec(new String[]{"kill", "-9", pid});
        return killProcess.waitFor();
    }
}
