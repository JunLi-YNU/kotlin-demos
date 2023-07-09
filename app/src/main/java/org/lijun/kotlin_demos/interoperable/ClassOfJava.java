package org.lijun.kotlin_demos.interoperable;

public class ClassOfJava {
    public static void main(String[] args){
        System.out.println(method());
    }
    public static String method(){
        return "Java Method";
    }

    public String  methodOfJava(){
        return "Java Method";
    }

    public String methodOfJavaReturnNull(){
        return null;
    }
}
