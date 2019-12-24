package prv.pgergely.cts.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommonTools {

    public static void launchCommand(String command) throws IOException{
        Process p = Runtime.getRuntime().exec(command);
        try(BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()))){
            System.out.println(bfr.readLine());
            while (bfr.ready()) {
                System.out.println(bfr.readLine());
            }
        }
    }
}
