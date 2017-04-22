package com.mystudy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JavaSerialUtil {
	public static byte[] serial(Object object) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		ObjectOutputStream ouput = new ObjectOutputStream(bytestream);
		ouput.writeObject(object);
		return bytestream.toByteArray();
	}

	public static Object deserial(byte[] bytes) throws Exception {
		ByteArrayInputStream bytestream = new ByteArrayInputStream(bytes);
		ObjectInputStream input = new ObjectInputStream(bytestream);
		return input.readObject();
	}

	public static void main(String[] args) throws Exception {
		String f = "C:\\java2py.dat";
		InputStream in = new FileInputStream(f);
		int len = in.available();
		byte[] b = new byte[len];
		in.read(b, 0, len);
		in.close();

		Object obj = deserial(b);
		System.out.println(GsonSerialUtil.serial(obj));
	}
}