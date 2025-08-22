package com.recipebook.logging

/**
 * Log message builder default implementation.
 * Public because called from the inline function.
 */
data class LogBuilderImpl(
    override var tag: String = "",
    override var message: String = "",
    override var level: LogLevel = LogLevel.VERBOSE,
    override var throwable: Throwable? = null,
) : LogBuilder
