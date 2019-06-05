package com.lovejjfg.circle

import org.junit.Test

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
//        var xxx = TestData("zhang", "10086")
//        println("x:$xxx")
//        testValue(xxx)
//        println("x:$xxx")
        val x  = 1.shl(30)
        val x1  = 0.shl(30)
        val x2  = 2.shl(30)
        println(Integer.toBinaryString(x))
        println(Integer.toBinaryString(x1))
        println(Integer.toBinaryString(x2))
    }

    private fun testValue(input: TestData?) {
        var b: TestData? = input
        println("b:$b;;s$input")
        b?.name = "li"
        println("b:$b;;s$input")
        b = null
        println("b:$b;;s$input")
    }

    data class TestData(var name: String?, var id: String?)
}
