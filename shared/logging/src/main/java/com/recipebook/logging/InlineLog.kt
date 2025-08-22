package com.recipebook.logging

// Could be timber or something.
// Just for simplification, use android.util.Log
import android.util.Log

/**
 * Allows to enable/disable logs in runtime.
 *
 * Useful for unit tests.
 *
 */
object LoggingConfigurator {
    var enableLogging = false
}

private fun printLog(tag: String, message: String, level: LogLevel, throwable: Throwable?) {
    when (level) {
        LogLevel.VERBOSE -> Log.v(tag, message, throwable)
        LogLevel.DEBUG -> Log.d(tag, message, throwable)
        LogLevel.INFO -> Log.i(tag, message, throwable)
        LogLevel.WARNING -> Log.w(tag, message, throwable)
        LogLevel.ERROR -> Log.e(tag, message, throwable)
    }
}

/**
 * Prints the log.
 * Public because called from the inline function.
 */
fun doLog(builder: LogBuilder) {
    printLog(builder.tag, builder.message, builder.level, builder.throwable)
}

// Do not remove "const". It must be compile-time constant.
const val LOGGING_ENABLED = BuildConfig.BUILD_TYPE == "debug"

/**
 * Print a message to the logcat.
 * Will be cut during compilation in the release build, including StringBuilder.
 *
 * @param block The log message builder.
 */
inline fun debugLog(block: LogBuilder.() -> Unit) {
    if (LOGGING_ENABLED && LoggingConfigurator.enableLogging) {
        doLog(
            LogBuilderImpl()
                .apply { this.block() }
        )
    }
}
