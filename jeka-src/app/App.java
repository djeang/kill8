package app;

import com.github.lalyos.jfiglet.FigletFont;
import dev.jeka.core.api.utils.JkUtilsSystem;
import dev.jeka.core.tool.JkDep;

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

    private static void killOnMacOs(int port) {

        ProcessBuilder pb = new ProcessBuilder("lsof", "-ti", "tcp:" + port, "|", "xargs", "kill", "-9");
    }

    private static boolean isMacos() {
        final String osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        return osName.contains("mac") || osName.contains("darwin");
    }

}