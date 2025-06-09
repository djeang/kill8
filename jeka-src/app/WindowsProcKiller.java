package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class WindowsProcKiller implements ProcKiller {

    @Override
    public String pid(int port) throws Exception {
        // Find the process ID using the port
        Process findProcess = Runtime.getRuntime().exec(
                new String[]{"cmd", "/c", "netstat", "-ano", "|", "findstr", ":" + port}
        );

        BufferedReader reader = new BufferedReader(new InputStreamReader(findProcess.getInputStream()));
        String line;
        String pid = null;

        // Read output to find PID
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length > 4) {
                pid = parts[parts.length - 1];
                break;
            }
        }
        reader.close();
        return pid;
    }

    @Override
    public int killPid(String pid) throws Exception {
        Process killProcess = Runtime.getRuntime().exec(
                new String[]{"cmd", "/c", "taskkill", "/PID", pid, "/F"}
        );
        return killProcess.waitFor();
    }
}
