package org.lijun.kotlin_demos.interoperable

class KotlinRunJavaFile {

}
fun main(){
    val javaClass = ClassOfJava();
    val javaReturnString:String = javaClass.methodOfJava();
    println("Kotlin 调用 Java 文件___________________________");
    println(javaReturnString)
    //使用可空类型去接收Java中可能为空的类型 store
    val javaReturnNull:String? = javaClass.methodOfJavaReturnNull();
    javaReturnNull?.uppercase()
    println(javaReturnNull);
}