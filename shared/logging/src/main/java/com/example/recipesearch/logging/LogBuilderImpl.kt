package com.example.recipesearch.logging

data class LogBuilderImpl(
    override var tag: String = "",
    override var message: String = "",
    override var level: LogLevel = LogLevel.VERBOSE,
    override var throwable: Throwable? = null,
) : LogBuilder
