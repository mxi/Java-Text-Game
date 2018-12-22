package test.magneticstudio.transience.gl

import com.magneticstudio.transience.devkit.*

fun testLogging() {
    createLogStream("Log.txt")

    logInf("This is my message")
    logInf("These are ", "multiple ", "messages ", null)
    logWar("Testing war")
    logErr("Testing err")

    closeLogStreams()
}

fun testGLError() {
    val l = listOf(1, 2, 3)
    collectGLErrors().reportThemBy(WRITING_TO_LOGS)
}

fun main(args: Array<String>) {

}