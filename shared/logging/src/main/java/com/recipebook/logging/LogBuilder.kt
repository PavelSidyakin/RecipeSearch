package com.recipebook.logging

/**
 * Log message builder.
 */
interface LogBuilder {
    /**
     * [tag] Log Tag
     */
    var tag: String

    /**
     * [message] The log message
     */
    var message: String

    /**
     * [level] Log level. One of [LogLevel]
     */
    var level: LogLevel

    /**
     * [throwable] Throwable stack will be printed to log.
     */
    var throwable: Throwable?
}
