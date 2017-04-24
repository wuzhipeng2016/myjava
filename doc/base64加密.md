#base64加密#
import sun.misc.BASE64Encoder;
private static BASE64Encoder base64enc = new BASE64Encoder();
System.out.println(base64enc.encodeBuffer(SecretKey.getBytes()));

#推荐方法
import org.apache.commons.codec.binary.Base64;
private static Base64 base64=new Base64();
System.out.println(new String(base64.encode(SecretKey.getBytes())));

import java.util.UUID;
private static String SecretKey = UUID.randomUUID().toString();