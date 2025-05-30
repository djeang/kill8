package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class App {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            String portStr = args[0];
            try {
                port = Integer.parseInt(portStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number: " + portStr);
                System.exit(1);
            }
        }
        if (isMacos()) {
            killOnMacOs(port);
        } else {
            System.err.println("Unhandled host system: " + System.getProperty("os.name"));
            System.exit(1);
        }
    }

    private static void killOnMacOs(int port) throws IOException, InterruptedException {
        // Find the process ID using the port
        Process findProcess = Runtime.getRuntime().exec(new String[]{"lsof", "-ti", "tcp:" + port});
        BufferedReader reader = new BufferedReader(new InputStreamReader(findProcess.getInputStream()));
        String pid = reader.readLine();
        reader.close();

        // If no process found
        if (pid == null || pid.trim().isEmpty()) {
            System.err.println("No process found running on port " + port);
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

    private static boolean isMacos() {
        final String osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        return osName.contains("mac") || osName.contains("darwin");
    }

}