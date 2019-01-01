/**
 * An implementation of a column major order 4x4 float matrix.
 *
 * @author Max
 * @since 1.1
 */

package com.magneticstudio.transience.devkit

import org.lwjgl.BufferUtils
import java.nio.FloatBuffer

class Mat4 private constructor(initFunc: (Int) -> Float) {

    private val matrix: FloatArray = FloatArray(16, initFunc)

    companion object Factory {

        /**
         * Creates an empty matrix (all 0's)
         *
         * @return A zero-ed out matrix.
         */
        fun empty(): Mat4 = Mat4 { 0.0f }

        /**
         * Creates an identity matrix.
         *
         * @return An identity matrix.
         */
        fun identity(): Mat4 = Mat4 { if (it % 5 == 0) 1.0f else 0.0f }

        /**
         * The orthographic projection matrix (Straight from wikipedia).
         */
        fun orthographic(
            left:   Float,
            right:  Float,
            bottom: Float,
            top:    Float,
            near:   Float =  1f,
            far:    Float = -1f): Mat4
        {
            val result: Mat4 = empty()
            // XYZ modifiers:
            result[0, 0] =  2f / (right - left)
            result[1, 1] =  2f / (top - bottom)
            result[2, 2] = -2f / (far - near)
            // W modifiers:
            result[0, 3] = - (right + left) / (right - left)
            result[1, 3] = - (top + bottom) / (top - bottom)
            result[2, 3] = - (far + near) / (far - near)
            result[3, 3] = 1f
            return result
        }
    }

    private inline fun swap(ind1: Int, ind2: Int) {
        val temp = matrix[ind2]
        matrix[ind2] = matrix[ind1]
        matrix[ind1] = temp
    }

    /**
     * Gets the value in the matrix at the specified row and column.
     *
     * @param row The row of the desired value.
     * @param col The column of the desired value.
     * @return The value as a scalar float at the specified location.
     */
    operator fun get(row: Int, col: Int): Float = matrix[(col * 4) + row]

    /**
     * Sets the value in the matrix at the specified row and column.
     *
     * @param row The row of the desired location.
     * @param col The column of the desired location.
     * @param value The value that the specified location in the matrix shall contain.
     */
    operator fun set(row: Int, col: Int, value: Float) {
        matrix[(col * 4) + row] = value
    }

    /**
     * Transposes this matrix.
     */
    fun transpose() {
        swap( 1,  4)
        swap( 2,  8)
        swap( 3, 12)
        swap( 6,  9)
        swap( 7, 13)
        swap(11, 14)
    }

    /**
     * Multiplies this matrix by a scalar and returns a new matrix
     * with the result.
     *
     * @param scalar The scalar to multiply this matrix by.
     * @return The resulting matrix.
     */
    operator fun times(scalar: Float): Mat4 {
        val result = empty()

        for (i in 0 until matrix.size)
            result.matrix[i] = scalar * matrix[i]

        return result
    }

    /**
     * Multiplies this matrix by another matrix and returns a new matrix
     * with the result.
     *
     * @param matrix The matrix to multiply this matrix by.
     * @return The resulting matrix.
     */
    operator fun times(matrix: Mat4): Mat4 {
        val result = empty()

        for (itr in 0 until 16) {
            for (dot in 0 until 4) {
                result.matrix[itr] += this[dot, itr / 4] * matrix[itr % 4, dot]
            }
        }

        return result
    }

    /**
     * Multiplies this matrix by a scalar and stores the result in this
     * matrix object.
     *
     * @param scalar The scalar to multiply this matrix by.
     */
    operator fun timesAssign(scalar: Float) {
        for (i in 0 until matrix.size)
            matrix[i] = scalar * matrix[i]
    }

    /**
     * Multiplies this matrix by another matrix and returns a new matrix
     * with the result.
     *
     * @param matrix The matrix to multiply this matrix by.
     * @return The resulting matrix.
     */
    operator fun timesAssign(matrix: Mat4) {
        for (itr in 0 until 16) {
            var accumulator = 0.0f
            for (dot in 0 until 4) {
                accumulator += this[dot, itr / 4] * matrix[itr % 4, dot]
            }
            this.matrix[itr] = accumulator
        }
    }

    /**
     * Updates the float buffer with the current values of the matrix
     * to be sent to a shader.
     */
    fun getShaderBuffer(): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(matrix.size)
        buffer.put(matrix)
        buffer.flip()
        return buffer
    }

    fun print() {
        for (row in 0 until 4) {
            for (col in 0 until 4) {
                print(this[row, col])
                print(' ')
            }
            print('\n')
        }
    }
}