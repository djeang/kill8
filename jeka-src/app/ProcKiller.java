package app;

interface ProcKiller {

    default void kill(int port) throws Exception {
        String pid = pid(port);
        // If no process found
        if (pid == null || pid.trim().isEmpty()) {
            System.err.println("No process found running on port " + port);
            return;
        }
        int exitCode = killPid(pid);
        if (exitCode == 0) {
            System.err.println("Process on port " + port + " killed successfully (PID: " + pid + ")");
        } else {
            System.err.println("Failed to kill process on port " + port);
        }
    }

    String pid(int port) throws Exception;

    int killPid(String pid) throws Exception;


}
