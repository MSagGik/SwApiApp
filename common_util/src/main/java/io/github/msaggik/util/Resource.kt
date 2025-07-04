package io.github.msaggik.util

/**
 * Represents a result wrapper that can be either a success with data or an error with a message.
 *
 * This sealed interface models an operation outcome, commonly used for handling responses
 * from network calls, database operations, or any asynchronous processes.
 *
 * @param T The type of the successful result data.
 */
sealed interface Resource<out T> {

    /**
     * Represents a successful result containing data.
     *
     * @param data The successful result data.
     */
    data class Success<out T>(val data: T) : Resource<T>

    /**
     * Represents an error result containing an error message.
     *
     * @param message A description of the error.
     */
    data class Error(val message: String) : Resource<Nothing>
}

/**
 * Extracts the data and error message from a [Resource] instance as a pair.
 *
 * - Returns a pair where the first component is the data if this is a [Resource.Success], otherwise `null`.
 * - The second component is the error message if this is a [Resource.Error], otherwise `null`.
 *
 * This can be useful when you want to quickly destructure the result into its components for
 * UI updates or further processing.
 *
 * @receiver The [Resource] instance to extract from.
 * @return A [Pair] containing the data and error message respectively.
 */
fun <T> Resource<T>.toDataAndError(): Pair<T?, String?> = when (this) {
    is Resource.Success -> data to null
    is Resource.Error -> null to message
}