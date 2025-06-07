package app;

import java.util.Locale;

public class Main {

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
        ProcKiller procKiller = null;
        if (isMacos()) {
            procKiller = new MacosProcKiller();
        } else if (isWindows()) {
            procKiller = new WindowsProcKiller();
        } else if (isLinux()) {
            procKiller = new LinuxProcKiller();
        } else {
            System.err.println("Unhandled host system: " + System.getProperty("os.name"));
            System.exit(1);
        }
        procKiller.kill(port);
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    // Method to check if the current system is Linux
    public static boolean isLinux() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("linux");
    }

    private static boolean isMacos() {
        final String osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        return osName.contains("mac") || osName.contains("darwin");
    }

}