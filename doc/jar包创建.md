#建立jar包#
1.路径  
com\mystudy\SerializeUtil.class  
com\mystudy\BeanUtil.class  
com同级有两个jar包:  
gson-2.3.1.jar  
po.jar  

2.建立mf文件  
com同级建立a.mf  
Manifest-Version: 1.0  
Main-Class: com.mystudy.SerializeUtil  
Class-Path: po.jar gson-2.3.1.jar  

：后必须空一格  
Class-Path中多个文件用空格，不加Class-Path会找不到类  
把第三方包打到一个jar文件中没有成功过  

3.打包  
jar cvfm a.jar a.mf com  

4.运行（当前路径下要有第三方jar包）    
java -jar a.jar  

