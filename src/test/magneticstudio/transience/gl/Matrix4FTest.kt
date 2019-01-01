package test.magneticstudio.transience.gl

import com.magneticstudio.transience.devkit.Mat4

fun main(args: Array<String>) {
    val m1 = Mat4.empty()
    val m2 = Mat4.identity()
    val m3 = Mat4.orthographic(0f, 1920f, 1080f, 0f)

    m1[0, 0] = 5.0f
    m1[1, 0] = 3.0f
    m1[1, 1] = 2.0f
    m1[2, 1] = 1.0f
    m1[2, 2] = -1.0f
    m1[3, 2] = -2.0f
    m1[3, 3] = -3.0f

    println("Display test ----")
    m1.print()
    println()
    m2.print()
    println()
    m3.print()

    println("Transpose test ----")
    m1.print()
    m1.transpose()
    println('\n')

    m1.print()

    println("Transposed * identity test ----")
    (m1 * m2).print()

    println("Transposed *= identity test ----")
    m1 *= m2
    m1.print()


}