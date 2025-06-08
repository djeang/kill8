package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class MacosProcKiller implements ProcKiller {

    public void kill(int port) throws Exception {
        // Find the process ID using the port
        Process findProcess = Runtime.getRuntime().exec(new String[]{"lsof", "-ti", "tcp:" + port});
        BufferedReader reader = new BufferedReader(new InputStreamReader(findProcess.getInputStream()));
        String pid = reader.readLine();
        reader.close();

        // If no process found
        if (pid == null || pid.trim().isEmpty()) {
            System.err.printf("..No process found on port %s. Use 'kill8 <port>' to kill a process on a different port.%n"
                    , port);
            return;
        }

        // Kill the process
        Process killProcess = Runtime.getRuntime().exec(new String[]{"kill", "-9", pid});
        int exitCode = killProcess.waitFor();

        if (exitCode == 0) {
            System.err.println("Process on port " + port + " killed successfully (PID: " + pid + ")");
        } else {
            System.err.println("Failed to kill process on port " + port);
        }
    }
}
