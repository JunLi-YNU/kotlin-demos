package org.lijun.kotlin_demos.functional_programming

class CombinationFunction {
    fun abs(d:Double):Double = if(d<0)d else d
    fun sqrt(d:Double):Double = kotlin.math.sqrt(d)
    fun combination(function: (Double)->Double,function2:(Double)->Double):(Double)->Double {
        return { x -> function2(function(x)) }
    }
}
fun main(args:Array<String>){
    val combinationFunction:CombinationFunction = CombinationFunction()
    val function = combinationFunction.combination(combinationFunction::abs,combinationFunction::sqrt)
    println(function(20.0))
}