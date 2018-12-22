/**
 * A collection of useful and utility functions.
 *
 * @author Max
 * @since 1.1
 */

package com.magneticstudio.transience.devkit

import org.lwjgl.opengl.ARBImaging.GL_TABLE_TOO_LARGE
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.GL_INVALID_FRAMEBUFFER_OPERATION
import org.lwjgl.opengl.GL45.GL_CONTEXT_LOST

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.lang.RuntimeException

/**
 * Attempts to open an input stream to a resource
 * in this jar file.
 *
 * @param path The path to the file within the jar where the parent
 *             directory is src/
 */
fun openJarResource(path: String): InputStream? = object {}.javaClass.classLoader.getResourceAsStream(path)

/**
 * Reads all of the contents of the input stream.
 *
 * NOTE(max): It is assumed this input stream does not wait
 *            for incoming messages, for example, over a network.
 *            This function assumes the stream is from a file.
 * NOTE(max): No exception handling is done in this function.
 * @return The contents of the input stream as an ASCII string.
 */
fun InputStream?.readString(): String? {
    if (this == null) return null

    val buffer = ByteArray(256)
    val stringStream = StringBuilder()

    while (this.available() > 0) {
        this.read(buffer)
        stringStream.append(String(buffer))
    }
    return stringStream.toString()
}

/**
 * Writes the contents of the string to the output
 * stream.
 *
 * @param msg The string to write to the output stream.
 */
fun OutputStream?.writeString(msg: String?) {
    if (this == null) return

    // For right now, we can assume the output streams
    // that we write out to are buffered.
    msg?.forEach {
        this.write(it.toInt())
    }
}

/**
 * Writes multiple strings at one to the output stream.
 *
 * @param msg The arbitrary string
 */
fun OutputStream?.writeMulti(vararg msg: String?) {
    if (this == null) return

    msg.forEach {
        this.writeString(it)
    }
}

// A list of output streams to which log messages
// will be sent to.
private val logStreams: MutableList<OutputStream> = mutableListOf()
private const val SEVERITY_NONE = 0
private const val SEVERITY_WARN = 1
private const val SEVERITY_ERROR = 2

var logEnabled: Boolean = true
var logToStd: Boolean = true

val programStartTime = System.currentTimeMillis()

const val msToHour = 1000 * 60 * 60
const val msToMinute = 1000 * 60
const val msToSecond = 1000

/**
 * A timestamp function used by all of the logging
 * functions to display the time of the event since
 * the program started.
 */
private fun getTimeStamp(): String {
    var current = System.currentTimeMillis() - programStartTime

    val hours = current / msToHour
    current %= msToHour

    val minutes = current / msToMinute
    current %= msToMinute

    val seconds = current / msToSecond
    current %= msToSecond

    val millis = current

    return "$hours:$minutes:$seconds:$millis"
}

private fun log(severity: Int, logType: String, messages: Array<out Any?>) {
    if (!logEnabled) return

    val heading = "[$logType ${getTimeStamp()}]: "

    for (stream in logStreams) {
        stream.writeString(heading)
        messages.forEach {
            stream.writeString(it?.toString() ?: "null")
        }
        stream.write('\n'.toInt())
    }

    if (logToStd) {
        val stdStream: OutputStream = when (severity) {
            SEVERITY_NONE -> System.out
            SEVERITY_WARN -> System.out
            else -> System.err
        }

        stdStream.writeString(heading)
        messages.forEach {
            stdStream.writeString(it?.toString() ?: "null")
        }
        stdStream.write('\n'.toInt())
    }
}

/**
 * Logs information to the output streams.
 *
 * @param messages A collection of messages to output to the streams.
 */
fun logInf(vararg messages: Any?) = log(SEVERITY_NONE, "Inf", messages)

/**
 * Logs warnings to the output streams.
 *
 * @param warnings A collection of warnings to output to the streams.
 */
fun logWar(vararg warnings: Any?) = log(SEVERITY_WARN, "War", warnings)

/**
 * Logs errors to the output streams.
 *
 * @param errors A collection of errors to output to the streams.
 */
fun logErr(vararg errors: Any?) = log(SEVERITY_ERROR, "Err", errors)

/**
 * Creates an output stream that will be used to output log
 * messages to a file.
 *
 * @param path The path to the output file for the logs.
 */
fun createLogStream(path: String) {
    try {
        logStreams.add(File(path).outputStream())
    }
    catch(ignore: Exception) { }
}

/**
 * Closes all of the output streams registered in the logStreams list.
 */
fun closeLogStreams() {
    for (stream in logStreams) stream.close()
}

typealias GLException = RuntimeException
typealias TexException = RuntimeException
typealias GLErrorFlags = List<Int>?

// GL Error reporting methods:
const val WRITING_TO_LOGS = 0b0011
const val THROWING_EXCEPTION = 0b0110

var allowReportsToThrow = true

/**
 * Reports a GL error.
 *
 * The main difference between this and logging is that if
 * "allowReportsToThrow" is true and the "exception" is true,
 * then this function will also throw an error.
 *
 * @param message The message of the error.
 * @param exception Whether the error is worthy of an exception.
 */
inline fun reportGLError(message: String, exception: Boolean) {
    logErr("GL Report: $message")
    if (allowReportsToThrow && exception)
        throw GLException("GL Report: $message")
}

/**
 * Reports an error when performing operations on a texture.
 * Very similar to reportGLError()
 *
 * @param message The message of the error.
 * @param exception Whether the error is worthy of an exception.
 */
inline fun reportTextureError(message: String, exception: Boolean) {
    logErr("Texture Report: $message")
    if (allowReportsToThrow && exception)
        throw TexException("Texture Report: $message")
}

/**
 * Reports all errors collected from collectGLErrors() by
 * using the specified methods:
 *
 * To make the function more readable, it is setup in a way
 * where you can specify one report method like so:
 *
 * reportThemBy(WRITING_TO_LOGS)
 *
 * or more than one method:
 *
 * reportThemBy(WRITING_TO_LOGS and THROWING_EXCEPTION)
 *
 * @param reportMethod The method(s) by which to report the errors. These
 *                     can be combined using the bitwise and (in kotlin the
 *                     keyword "and"). Methods are defined above this
 *                     function.
 */
fun GLErrorFlags.reportThemBy(reportMethod: Int) {
    if (this == null || this.count() == 0) return

    val logAllErrors = fun () {
        this.forEach { logErr("OpenGL Error: $it") }
    }

    val throwException = fun () {
        if (!allowReportsToThrow) return

        val errorsToString = StringBuilder()
        this.forEach {
            when (it) {
                GL_INVALID_ENUM -> errorsToString.append("GL_INVALID_ENUM")
                GL_INVALID_VALUE -> errorsToString.append("GL_INVALID_VALUE")
                GL_INVALID_OPERATION -> errorsToString.append("GL_INVALID_OPERATION")
                GL_STACK_OVERFLOW -> errorsToString.append("GL_STACK_OVERFLOW")
                GL_OUT_OF_MEMORY -> errorsToString.append("GL_OUT_OF_MEMORY")
                GL_INVALID_FRAMEBUFFER_OPERATION -> errorsToString.append("GL_INVALID_FRAMEBUFFER_OPERATION")
                GL_CONTEXT_LOST -> errorsToString.append("GL_CONTEXT_LOST")
                GL_TABLE_TOO_LARGE -> errorsToString.append("GL_TABLE_TOO_LARGE")
                else -> errorsToString.append("0x" + it.toString(8))
            }
            errorsToString.append(", ")
        }
        throw GLException("OpenGL Error(s): $errorsToString")
    }

    when (reportMethod) {
        WRITING_TO_LOGS -> logAllErrors()
        THROWING_EXCEPTION -> throwException()
        WRITING_TO_LOGS and THROWING_EXCEPTION -> {
            logAllErrors()
            throwException()
        }
    }
}

/**
 * Clears any OpenGL errors specified by glGetError()
 */
fun clearGLErrors() {
    while (glGetError() != GL_NO_ERROR);
}

/**
 * Checks to see if there were any OpenGL errors, and if there
 * were, the list may be used to report them and do other
 * actions.
 *
 * @return The list of GL errors from glGetError() or null
 *         if no errors exist.
 */
fun collectGLErrors(): GLErrorFlags {
    val errList: MutableList<Int> = mutableListOf()
    var nextError: Int = glGetError()

    while (nextError != GL_NO_ERROR) {
        errList.add(nextError)
        nextError = glGetError()
    }

    return if (errList.count() > 0) errList else null
}