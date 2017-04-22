package com.mystudy;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mystudy.po.Car;
import com.mystudy.po.Tyre;

public class GsonSerialUtil {
	public static String serial(Object obj){
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	public static <T> T deserial(String pJsonStr,Class<T> pClass){
		T _T;
        try{
            if (pJsonStr!=null && pJsonStr.length()>0){
                Gson _Gson = new Gson();
                _T = (T) _Gson.fromJson(pJsonStr, pClass);
            }else {
                _T = null;
            }
        }catch (Exception e){
            _T = null;
        }
        return _T;
	}
	
	public static void main(String[] args){
		Car car = new Car();
    	car.setName("hello");
    	car.setFlag("world");
    	car.setLength(3.0);
    	car.setWidth(1.5);
    	car.setHeight(1.2);
    	
    	Tyre tyre1 = new Tyre("front1",1,10.2);
    	Tyre tyre2 = new Tyre("front2",2,10.2);
    	List<Tyre> tyrelist = new ArrayList<Tyre>();
    	tyrelist.add(tyre1);
    	tyrelist.add(tyre2);
    	car.setTyrelist(tyrelist);
    	
    	System.out.println(car);
		
    	String s = serial(car);
    	System.out.println(s);
    	
		Car car2 = (Car)deserial(s,Car.class);
		System.out.println(car2);
	}
}
