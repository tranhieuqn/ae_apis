package com.ae.apis.service.payment.common;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class CommonUtils {

    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    public static String Sha256(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes("UTF-8"));

            // converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            digest = sb.toString();

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    public static String hashAllFields(Map fields, String vnp_HashSecret) throws UnsupportedEncodingException {
        // create a list and sort it
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        // create a buffer for the md5 input and add the secure secret first
        StringBuilder sb = new StringBuilder();
        sb.append(vnp_HashSecret);
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(URLDecoder.decode(fieldValue, StandardCharsets.UTF_8));
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return Sha256(sb.toString());
    }

    public static String buildQueryString(Map<String, String> input, boolean isEndCode) throws UnsupportedEncodingException {
        //Build data to hash and query string
        List<String> fieldNames = new ArrayList<>(input.keySet());
        Collections.sort(fieldNames);
        StringBuilder query = new StringBuilder();

        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = input.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                if (isEndCode) {
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                } else {
                    //Build hash data
                    query.append(fieldName);
                    query.append('=');
                    query.append(fieldValue);
                }

                if (itr.hasNext()) {
                    query.append('&');
                }
            }
        }

        return query.toString();
    }

    public static String buildVnpPaymentUrl(String vnpPayUrl, String queryUrl, String vnpSecureHash) {
        return String.join("",
                vnpPayUrl, "?",
                queryUrl, "&vnp_SecureHashType=SHA256&vnp_SecureHash=", vnpSecureHash);
    }

    public static Date StringToDate(String str, String pattern) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        Date date = null;
        SimpleDateFormat sim = new SimpleDateFormat(pattern);
        try {
            date = sim.parse(str);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return date;
    }

    public static String generateVnpTxnRef() {
        return generateCode(8, 0);
    }

    private static String generateCode(int numbericLength, int alphabeticLength) {
        char[] charArr = new StringBuilder().append(randomNumeric(numbericLength))
                .append(randomAlphabetic(alphabeticLength))
                .toString()
                .toCharArray();

        Character[] characterArr = ArrayUtils.toObject(charArr);

        List<Character> characterList = Arrays.asList(characterArr);

        Collections.shuffle(characterList);

        return characterList.stream().map(String::valueOf).collect(joining()).toUpperCase();
    }
}
