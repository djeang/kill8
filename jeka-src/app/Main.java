package app;

import java.util.Locale;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length > 0) {
            String arg = args[0];
            if ("-h".equals(arg) || "--help".equals(arg)) {
                printHelp();
                System.exit(0);
            }
        }

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

    private static void printHelp() {
        System.out.println("kill8 - Cross-platform port process killer");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  kill8 [PORT]");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("  PORT                  Port number to kill process on (default: 8080)");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -h, --help           Show this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  kill8                Kill process on port 8080 (default)");
        System.out.println("  kill8 3000           Kill process on port 3000");
        System.out.println("  kill8 --help         Show this help message");
        System.out.println();
        System.out.println("Supported platforms: macOS, Linux, Windows");
    }

}