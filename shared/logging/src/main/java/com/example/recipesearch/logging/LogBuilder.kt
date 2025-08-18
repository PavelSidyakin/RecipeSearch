package com.example.recipesearch.logging


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
     * If [message] is not empty, the stack will be printed with [message],
     * if [message] is empty, the stack will be printed with the first message from [messages].
     */
    var throwable: Throwable?
}
