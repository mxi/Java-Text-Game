/**
 * Computation using quaternions.
 *
 * @author Max
 * @since 1.1
 */

package com.magneticstudio.transience.devkit

import kotlin.math.sqrt

class Quaternion constructor(
    var w: Float = 1.0f,
    var x: Float = 0.0f,
    var y: Float = 0.0f,
    var z: Float = 0.0f)
{
    /**
     * Calculates the magnitude of this quaternion.
     *
     * @return The magnitude of this quaternion.
     */
    inline fun magnitude(): Float = sqrt(w * w + x * x + y * y + z * z)

    /**
     * Normalizes this quaternion.
     */
    inline fun normalize() {
        val mag = magnitude()

        if (mag == 0.0f) {
            w = 1.0f
            x = 0.0f
            y = 0.0f
            z = 0.0f
            return
        }

        w /= mag
        x /= mag
        y /= mag
        z /= mag
    }

    /**
     * Sets this quaternion number to its conjugate.
     */
    inline fun conjugate() {
        x = -x
        y = -x
        z = -z
    }

    /**
     * Performs an addition operation with a scalar.
     *
     * @param scalar The scalar to add to this quaternion number.
     * @return The result of adding this quaternion number with a scalar (Quaternion result).
     */
    operator fun plus(scalar: Float): Quaternion = Quaternion(
        w + scalar,
        x,
        y,
        z
    )

    /**
     * Performs an addition operation with another quaternion.
     *
     * @param quaternion Another quaternion number to add to this one.
     * @return The result of adding this quaternion number and another. (Quaternion result).
     */
    operator fun plus(quaternion: Quaternion): Quaternion = Quaternion(
        w + quaternion.w,
        x + quaternion.x,
        y + quaternion.y,
        z + quaternion.z
    )

    /**
     * Performs an addition operation with a scalar and stores the result
     * in this quaternion object.
     *
     * @param scalar The scalar to add to this quaternion number.
     */
    operator fun plusAssign(scalar: Float) {
        w += scalar
    }

    /**
     * Performs an addition operation with a quaternion and stores the result
     * in this quaternion object.
     *
     * @param quaternion The quaternion to add to this quaternion number.
     */
    operator fun plusAssign(quaternion: Quaternion) {
        w += quaternion.w
        x += quaternion.x
        y += quaternion.y
        z += quaternion.z
    }

    /**
     * Performs a subtraction operation with a scalar.
     *
     * @param scalar The scalar to subtract to this quaternion number.
     * @return The result of subtracting this quaternion number with a scalar (Quaternion result).
     */
    operator fun minus(scalar: Float): Quaternion = Quaternion(
        w - scalar,
        x,
        y,
        z
    )

    /**
     * Performs an subtraction operation with another quaternion.
     *
     * @param quaternion Another quaternion number to subtract from this one.
     * @return The result of subtracting this quaternion number and another. (Quaternion result).
     */
    operator fun minus(quaternion: Quaternion): Quaternion = Quaternion(
        w - quaternion.w,
        x - quaternion.x,
        y - quaternion.y,
        z - quaternion.z
    )

    /**
     * Performs an subtraction operation with a scalar and stores the result
     * in this quaternion object.
     *
     * @param scalar The scalar to subtract from this quaternion number.
     */
    operator fun minusAssign(scalar: Float) {
        w -= scalar
    }

    /**
     * Performs an subtraction operation with a quaternion and stores the result
     * in this quaternion object.
     *
     * @param quaternion The quaternion to subtract from this quaternion number.
     */
    operator fun minusAssign(quaternion: Quaternion) {
        w -= quaternion.w
        x -= quaternion.x
        y -= quaternion.y
        z -= quaternion.z
    }

    /**
     * Performs a multiplication operation with a scalar.
     *
     * @param scalar The scalar to multiply with this quaternion number.
     * @return The result of multiplying this quaternion number with a scalar (Quaternion result).
     */
    operator fun times(scalar: Float): Quaternion = Quaternion(
        w * scalar,
        x * scalar,
        y * scalar,
        z * scalar
    )

    /**
     * Performs an multiplication operation with another quaternion.
     *
     * @param quaternion Another quaternion number to multiply with this one.
     * @return The result of multiplying this quaternion number and another. (Quaternion result).
     */
    operator fun times(quaternion: Quaternion): Quaternion = Quaternion(
        (w * quaternion.w  -  x * quaternion.x  -  y * quaternion.y  -  z * quaternion.z),
        (w * quaternion.x  +  x * quaternion.w  +  y * quaternion.z  -  z * quaternion.y),
        (w * quaternion.y  -  x * quaternion.z  +  y * quaternion.w  +  z * quaternion.x),
        (w * quaternion.z  +  x * quaternion.y  -  y * quaternion.x  +  z * quaternion.w)
    )

    /**
     * Performs an multiplication operation with a scalar and stores the result
     * in this quaternion object.
     *
     * @param scalar The scalar to multiply with this quaternion number.
     */
    operator fun timesAssign(scalar: Float) {
        w *= scalar
        x *= scalar
        y *= scalar
        z *= scalar
    }

    /**
     * Performs an multiplication operation with a quaternion and stores the result
     * in this quaternion object.
     *
     * @param quaternion The quaternion to multiply with this quaternion number.
     */
    operator fun timesAssign(quaternion: Quaternion) {
        w = (w * quaternion.w  -  x * quaternion.x  -  y * quaternion.y  -  z * quaternion.z)
        x = (w * quaternion.x  +  x * quaternion.w  +  y * quaternion.z  -  z * quaternion.y)
        y = (w * quaternion.y  -  x * quaternion.z  +  y * quaternion.w  +  z * quaternion.x)
        z = (w * quaternion.z  +  x * quaternion.y  -  y * quaternion.x  +  z * quaternion.w)
    }

    /**
     * Performs a division operation with a scalar.
     *
     * @param scalar The scalar to divide with this quaternion number.
     * @return The result of dividing this quaternion number by a scalar (Quaternion result).
     */
    operator fun div(scalar: Float): Quaternion = Quaternion(
        w / scalar,
        x / scalar,
        y / scalar,
        z / scalar
    )

    /**
     * Performs a division operation with another quaternion.
     *
     * @param quaternion Another quaternion number to divide with this one.
     * @return The result of dividing this quaternion number by another. (Quaternion result).
     */
    operator fun div(quaternion: Quaternion): Quaternion {
        val fractionConstant =
            (quaternion.w * quaternion.w) +
            (quaternion.x * quaternion.x) +
            (quaternion.y * quaternion.y) +
            (quaternion.z * quaternion.z)
        return Quaternion(
            (w * quaternion.w  +  x * quaternion.x  +  y * quaternion.y  +  z * quaternion.z) / fractionConstant,
            (x * quaternion.w  +  z * quaternion.y  -  w * quaternion.x  -  y * quaternion.z) / fractionConstant,
            (y * quaternion.w  +  x * quaternion.z  -  w * quaternion.y  -  z * quaternion.x) / fractionConstant,
            (z * quaternion.w  +  w * quaternion.x  -  w * quaternion.z  -  x * quaternion.y) / fractionConstant
        )
    }

    /**
     * Performs an division operation with a scalar and stores the result
     * in this quaternion object.
     *
     * @param scalar The scalar to divide with this quaternion number.
     */
    operator fun divAssign(scalar: Float) {
        w /= scalar
        x /= scalar
        y /= scalar
        z /= scalar
    }

    /**
     * Performs an division operation with a quaternion and stores the result
     * in this quaternion object.
     *
     * @param quaternion The quaternion to divide with this quaternion number.
     */
    operator fun divAssign(quaternion: Quaternion) {
        val fractionConstant =
            (quaternion.w * quaternion.w) +
            (quaternion.x * quaternion.x) +
            (quaternion.y * quaternion.y) +
            (quaternion.z * quaternion.z)
        w = (w * quaternion.w  +  x * quaternion.x  +  y * quaternion.y  +  z * quaternion.z) / fractionConstant
        x = (x * quaternion.w  +  z * quaternion.y  -  w * quaternion.x  -  y * quaternion.z) / fractionConstant
        y = (y * quaternion.w  +  x * quaternion.z  -  w * quaternion.y  -  z * quaternion.x) / fractionConstant
        z = (z * quaternion.w  +  w * quaternion.x  -  w * quaternion.z  -  x * quaternion.y) / fractionConstant
    }

    /**
     * Checks whether this quaternion number conveys the same "idea" or description
     * of a position in space.
     *
     * If the other object is a Quaternion object:
     *   Returns true if the real and imaginary 'i' components are equal and
     *   the quaternion imaginary 'j' and 'k' components are 0
     *
     * If the other object is a Quaternion object:
     *   Returns true if real = obj.real and imag = obj.imag
     *
     * Else if the other object is a Scalar (Float) object:
     *   Returns true if real = obj and imag = 0
     *
     * Else returns false
     *
     * @param obj The object to compare
     * @return Whether the object and this quaternion number are equal.
     */
    infix fun isSimilarTo(obj: Any?): Boolean {
        if (obj == null) return false

        return when (obj) {
            is Quaternion ->
                (obj.w == w) && (obj.x == x) && (obj.y == y) && (obj.z == z)
            is Complex ->
                (obj.real == w) && (obj.imag == x) && (y == 0.0f) && (z == 0.0f)
            is Float ->
                (obj == w) && (x == 0.0f) && (y == 0.0f) && (z == 0.0f)
            else ->
                false
        }
    }

    /**
     * Gets a string representation of this quaternion number.
     *
     * @return String representation of this quaternion number.
     */
    override fun toString(): String = "${w} + ${x}i + ${y}j + ${z}k"
}