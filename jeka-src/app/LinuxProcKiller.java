package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LinuxProcKiller implements ProcKiller {

    @Override
    public String pid(int port) throws Exception {
        // Check lsof is installed
        ProcessBuilder checkLsof = new ProcessBuilder("which", "lsof");
        Process process = checkLsof.start();

        BufferedReader whichReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String lsofPath = whichReader.readLine();
        whichReader.close();

        int lsofExitCode = process.waitFor();

        if (lsofExitCode != 0 || lsofPath == null || lsofPath.trim().isEmpty()) {
            throw new Exception("lsof is not installed on this system. Please install lsof to use this functionality.");
        }

        // Find the process ID using the port
        Process findProcess = Runtime.getRuntime().exec(
                new String[]{"bash", "-c", "lsof -ti tcp:" + port}
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(findProcess.getInputStream()));
        String pid = reader.readLine();
        reader.close();
        return pid;
    }

    @Override
    public int killPid(String pid) throws Exception {
        Process killProcess = Runtime.getRuntime().exec(
                new String[]{"bash", "-c", "kill -9 " + pid}
        );
        return killProcess.waitFor();
    }

}
