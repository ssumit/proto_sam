package open.sam.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IPHelper {

    Logger logger = Logger.getLogger(IPHelper.class.getSimpleName());

    public List<String> getIPAddress() {
        List<String> ipAddresses = new ArrayList<String>();
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : interfaces) {
                logger.log(Level.INFO, "network interface: " + networkInterface.getDisplayName() + " up: " + networkInterface.isUp());
                if (networkInterface.isUp()) {
                    List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());
                    for (InetAddress inetAddress : inetAddresses) {
                        if (!inetAddress.isLoopbackAddress() && !inetAddress.isAnyLocalAddress()) {
                            String hostAddress = inetAddress.getHostAddress().toUpperCase();
                            boolean isIPv4 = InetAddressUtils.isIPv4Address(hostAddress);
                            if (isIPv4) {
                                ipAddresses.add(hostAddress);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "exception", ex);
        }
        return ipAddresses;
    }

    public NetworkType getNetworkType(Context context) {
        //todo: only wifi and mobile data is considered, wimax etc are ignored
        if (isWifiConnected(context)) {
            return NetworkType.WIFI;
        } else if (isMobileDataConnected(context)) {
            TelephonyManager mTelephonyManager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = mTelephonyManager.getNetworkType();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NetworkType._2G;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NetworkType._3G;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NetworkType._4G;
                default:
                    return NetworkType.UNKNOWN;
            }
        } else {
            return NetworkType.UNKNOWN;
        }
    }

    private boolean isMobileDataConnected(Context context) {
        return isNetworkTypeConnected(context, ConnectivityManager.TYPE_MOBILE);
    }

    private boolean isWifiConnected(Context context) {
        return isNetworkTypeConnected(context, ConnectivityManager.TYPE_WIFI);
    }

    private boolean isNetworkTypeConnected(Context context, int networkType) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(networkType);
        return networkInfo.isConnected();
    }
}