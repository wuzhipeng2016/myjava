package com.mystudy;
import java.security.MessageDigest;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;

public class SShaUtil {  
    private static Base64 base64=new Base64();
    private static String SHA1="SHA-1";
    
    public String getDigest(String salt, String data) throws Exception{  
    	MessageDigest messageDigest = MessageDigest.getInstance(SHA1);
    	messageDigest.update(data.getBytes());  
    	messageDigest.update(salt.getBytes());  
        byte[] pwhash = messageDigest.digest();
        return new String(base64.encode(mergeByte(pwhash,salt.getBytes())));  
    }  
      
    private static byte[] mergeByte(byte[] first, byte[] second) {  
        byte[] b = new byte[first.length + second.length];  
        System.arraycopy(first, 0, b, 0, first.length);  
        System.arraycopy(second, 0, b, first.length, second.length);  
        return b;  
    }  
    
    public boolean verifyDigest(String digest, String data) throws Exception{  
        try {  
        	MessageDigest messageDigest = MessageDigest.getInstance(SHA1);
            byte[] decodeBase64 = base64.decode(digest);  
            byte[][] hs =splitByte(decodeBase64, 20);  
            byte[] hash = hs[0];  
            byte[] salt = hs[1];  
            messageDigest.reset();  
            messageDigest.update(data.getBytes());  
            messageDigest.update(salt);  
            byte[] pwhash = messageDigest.digest();  
            return MessageDigest.isEqual(hash, pwhash);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return false;  
    }  
    
    private byte[][] splitByte(byte[] src, int n) {  
        byte[] l;  
        byte[] r;  
        if (src.length <= n) {  
            l = src;  
            r = new byte[0];  
        } else {  
            l = new byte[n];  
            r = new byte[src.length - n];  
            System.arraycopy(src, 0, l, 0, n);  
            System.arraycopy(src, n, r, 0, r.length);  
        }  
        byte[][] lr = { l, r };  
        return lr;  
    }  
    private static boolean b;
    public static void main(String[] args) throws Exception{
    	
        System.out.println(b);
    }
}  