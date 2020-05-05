package fr.julien.mybooks.networking;

import java.io.IOException;

public class ConnexionInternet {
    public static boolean isConnected() throws InterruptedException, IOException {
        final String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }
}
