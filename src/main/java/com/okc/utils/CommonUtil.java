package com.okc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


@Slf4j
@Component
public class CommonUtil {

    private static String X_FORWARDED_FOR = "x-forwarded-for";

    private static String UNKNOWN = "unknown";

    private static String PROXY_CLIENT_IP = "Proxy-Client-IP";

    private static String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    private static String LOCALHOST = "127.0.0.1";

    private static Integer FIFTEEN = 15;

    private static String SEPARATOR = ",";


    /**
     * 元转分
     * @param yuan
     * @return
     */
    public static Integer yuan2fen(String yuan) {
        return new BigDecimal(yuan).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
    }






    /**
     * 获取客户端IP
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader(X_FORWARDED_FOR);
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(PROXY_CLIENT_IP);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST.equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("[error]", e);
                }
                ipAddress = inet.getHostAddress();
            }

        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > FIFTEEN) {
            //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(SEPARATOR) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    /**
     * md5加密
     *
     * @param str
     * @return
     */
    public static String MD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] md5Byte = md5.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md5Byte.length; i++) {
                sb.append(hex[(md5Byte[i] & 0xff) / 16]);
                sb.append(hex[(md5Byte[i] & 0xff) % 16]);
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("MD5加密出现错误", e);
        }
        return null;
    }

    public static String decryptAndMD5(String str) {
        return MD5(RSAUtil.decrypt(str));
    }
}