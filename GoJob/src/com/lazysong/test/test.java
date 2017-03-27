package com.lazysong.test;

import com.lazysong.test.beans.User;

public class test {
public static void main(String[] args) {
	Object tableType=new User();
	String tablename = tableType.getClass().getName().replace("com.lazysong.test.beans.","");
	System.out.println(tablename);
}
}
