package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class WindowsProcKiller implements ProcKiller {

    public void kill(int port) throws Exception {
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

        // If no process found
        if (pid == null || pid.trim().isEmpty()) {
            System.err.println("No process found running on port " + port);
            return;
        }

        // Kill the process using taskkill
        Process killProcess = Runtime.getRuntime().exec(
                new String[]{"cmd", "/c", "taskkill", "/PID", pid, "/F"}
        );

        int exitCode = killProcess.waitFor();

        if (exitCode == 0) {
            System.err.println("Process on port " + port + " killed successfully (PID: " + pid + ")");
        } else {
            System.err.println("Failed to kill process on port " + port);
        }
    }
}
