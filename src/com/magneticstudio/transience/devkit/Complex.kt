/**
 * Provides computation with complex numbers.
 *
 * @author Max
 * @since 1.1
 */

package com.magneticstudio.transience.devkit

import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * The default parameters are of a unit vector (1, 0)
 */
class Complex constructor(
    var real: Float = 1.0f,
    var imag: Float = 0.0f)
{
    /**
     * Calculates the magnitude of the complex number where magnitude
     * c^2 = a^2 + b^2
     *
     * @return The magnitude of the complex number.
     */
    inline fun magnitude(): Float = sqrt(real * real + imag * imag)

    /**
     * Calculates the angle of the complex number using atan2(im, re)
     *
     * @return The angle of the complex number in radians.
     */
    inline fun angle(): Float = atan2(imag, real)

    /**
     * Normalizes this complex number.
     */
    inline fun normalize() {
        val mag = magnitude()

        if (mag == 0.0f) {
            real = 1.0f
            imag = 0.0f
            return
        }

        real /= mag
        imag /= mag
    }

    /**
     * Sets this complex number to its complex conjugate.
     */
    inline fun conjugate() {
        imag = -imag
    }

    /**
     * Performs an addition operation with a scalar.
     *
     * @param scalar The scalar to add to this complex number.
     * @return The result of adding this complex number with a scalar (Complex result).
     */
    operator fun plus(scalar: Float): Complex = Complex(
        real + scalar,
        imag
    )

    /**
     * Performs an addition operation with another complex.
     *
     * @param complex Another complex number to add to this one.
     * @return The result of adding this complex number and another. (Complex result).
     */
    operator fun plus(complex: Complex): Complex = Complex(
        real + complex.real,
        imag + complex.imag
    )

    /**
     * Performs an addition operation with a scalar and stores the result
     * in this complex object.
     *
     * @param scalar The scalar to add to this complex number.
     */
    operator fun plusAssign(scalar: Float) {
        real += scalar
    }

    /**
     * Performs an addition operation with a complex and stores the result
     * in this complex object.
     *
     * @param complex The complex to add to this complex number.
     */
    operator fun plusAssign(complex: Complex) {
        real += complex.real
        imag += complex.imag
    }

    /**
     * Performs a subtraction operation with a scalar.
     *
     * @param scalar The scalar to subtract to this complex number.
     * @return The result of subtracting this complex number with a scalar (Complex result).
     */
    operator fun minus(scalar: Float): Complex = Complex(
        real - scalar,
        imag
    )

    /**
     * Performs an subtraction operation with another complex.
     *
     * @param complex Another complex number to subtract from this one.
     * @return The result of subtracting this complex number and another. (Complex result).
     */
    operator fun minus(complex: Complex): Complex = Complex(
        real - complex.real,
        imag - complex.imag
    )

    /**
     * Performs an subtraction operation with a scalar and stores the result
     * in this complex object.
     *
     * @param scalar The scalar to subtract from this complex number.
     */
    operator fun minusAssign(scalar: Float) {
        real -= scalar
    }

    /**
     * Performs an subtraction operation with a complex and stores the result
     * in this complex object.
     *
     * @param complex The complex to subtract from this complex number.
     */
    operator fun minusAssign(complex: Complex) {
        real -= complex.real
        imag -= complex.imag
    }

    /**
     * Performs a multiplication operation with a scalar.
     *
     * @param scalar The scalar to multiply with this complex number.
     * @return The result of multiplying this complex number with a scalar (Complex result).
     */
    operator fun times(scalar: Float): Complex = Complex(
        real * scalar,
        imag * scalar
    )

    /**
     * Performs an multiplication operation with another complex.
     *
     * @param complex Another complex number to multiply with this one.
     * @return The result of multiplying this complex number and another. (Complex result).
     */
    operator fun times(complex: Complex): Complex = Complex(
        (real * complex.real) - (imag * complex.imag),
        (real * complex.imag) + (imag * complex.real)
    )

    /**
     * Performs an multiplication operation with a scalar and stores the result
     * in this complex object.
     *
     * @param scalar The scalar to multiply with this complex number.
     */
    operator fun timesAssign(scalar: Float) {
        real *= scalar
        imag *= scalar
    }

    /**
     * Performs an multiplication operation with a complex and stores the result
     * in this complex object.
     *
     * @param complex The complex to multiply with this complex number.
     */
    operator fun timesAssign(complex: Complex) {
        real = (real * complex.real) - (imag * complex.imag)
        imag = (real * complex.imag) + (imag * complex.real)
    }

    /**
     * Performs a division operation with a scalar.
     *
     * @param scalar The scalar to divide with this complex number.
     * @return The result of dividing this complex number by a scalar (Complex result).
     */
    operator fun div(scalar: Float): Complex = Complex(
        real / scalar,
        imag / scalar
    )

    /**
     * Performs a division operation with another complex.
     *
     * @param complex Another complex number to divide with this one.
     * @return The result of dividing this complex number by another. (Complex result).
     */
    operator fun div(complex: Complex): Complex {
        val fractionConstant = (complex.real * complex.real) + (complex.imag * complex.imag)
        return Complex(
            (real * complex.real + imag * complex.imag) / fractionConstant,
            (imag * complex.real - real * complex.imag) / fractionConstant
        )
    }

    /**
     * Performs an division operation with a scalar and stores the result
     * in this complex object.
     *
     * @param scalar The scalar to divide with this complex number.
     */
    operator fun divAssign(scalar: Float) {
        real /= scalar
        imag /= scalar
    }

    /**
     * Performs an division operation with a complex and stores the result
     * in this complex object.
     *
     * @param complex The complex to divide with this complex number.
     */
    operator fun divAssign(complex: Complex) {
        val fractionConstant = (complex.real * complex.real) + (complex.imag * complex.imag)
        real = (real * complex.real + imag * complex.imag) / fractionConstant
        imag = (imag * complex.real - real * complex.imag) / fractionConstant
    }

    /**
     * Checks whether this complex number conveys the same "idea" or description
     * of a position in space.
     *
     * If the other object is a Quaternion object:
     *   Returns true if the real and imaginary 'i' components are equal and
     *   the quaternion imaginary 'j' and 'k' components are 0
     *
     * If the other object is a Complex object:
     *   Returns true if real = obj.real and imag = obj.imag
     *
     * Else if the other object is a Scalar (Float) object:
     *   Returns true if real = obj and imag = 0
     *
     * Else returns false
     *
     * @param obj The object to compare
     * @return Whether the object and this complex number are equal.
     */
    infix fun isSimilarTo(obj: Any?): Boolean {
        if (obj == null) return false

        return when (obj) {
            is Quaternion ->
                (obj.w == real) && (obj.x == imag) && (obj.y == 0.0f) && (obj.z == 0.0f)
            is Complex ->
                (obj.real == real) && (obj.imag == imag)
            is Float ->
                (obj == real) && (imag == 0.0f)
            else ->
                false
        }
    }

    /**
     * Gets a string representation of this complex number.
     *
     * @return String representation of this complex number.
     */
    override fun toString(): String = "${real} + ${imag}i"
}