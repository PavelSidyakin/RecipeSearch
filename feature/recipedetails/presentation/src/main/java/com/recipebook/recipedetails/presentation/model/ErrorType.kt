package com.recipebook.recipedetails.presentation.model

/**
 * Error type to be displayed on the UI.
 */
internal enum class ErrorType {
    /**
     * Network connection error.
     */
    NETWORK,

    /**
     * General error, including remote API errors.
     */
    GENERAL,
}