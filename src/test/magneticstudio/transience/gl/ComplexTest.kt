/**
 * Test program to test the Complex number implementation in
 * Complex.kt
 *
 * @author Max
 * @since 1.1
 */

package test.magneticstudio.transience.gl

import com.magneticstudio.transience.devkit.Complex

fun testComplexOnComplexArithmetic() {
    val c1 = Complex(4.0f, 2.0f)
    val c2 = Complex(-2.0f, 1.0f)

    println(c1 + c2)
    println(c1 - c2)
    println(c1 * c2)
    println(c1 / c2)
    println()
}

fun testComplexOnScalarArithmetic() {
    val c1 = Complex(3.0f, 6.0f)
    val s1 = 12.5f

    println(c1 + s1)
    println(c1 - s1)
    println(c1 * s1)
    println(c1 / s1)
    println()
}

fun main(args: Array<String>) {
    testComplexOnComplexArithmetic()
    testComplexOnScalarArithmetic()
}