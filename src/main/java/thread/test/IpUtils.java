package thread.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {

    public static String getIp() throws UnknownHostException {
        InetAddress addr = null;
        addr = InetAddress.getLocalHost();
        return addr.getHostAddress();
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(getIp());
    }
}