package com.example.diffutilrv

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 * @property data the wrapped data
 */
class OneOffEvent<out T>(private val data: T) {
    /**
     * Flag to indicated the data is consumed.
     */
    var isConsumed: Boolean = false
        private set

    /**
     * Get the wrapped data.
     *
     * After the first call, it will return null.
     */
    fun getDataIfNotConsumed(): T? {
        return if (!isConsumed) {
            isConsumed = true
            data
        } else {
            null
        }
    }

    inline fun runIfNotConsumed(block: (T) -> Unit) {
        getDataIfNotConsumed()?.let(block)
    }

    /**
     * Get the wrapped data no matter it is consumed or not.
     */
    fun peek(): T = data

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OneOffEvent<*>

        if (data != other.data) return false
        if (isConsumed != other.isConsumed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data?.hashCode() ?: 0
        result = 31 * result + isConsumed.hashCode()
        return result
    }

    override fun toString(): String {
        return "Event(data=$data, isConsumed=$isConsumed)"
    }
}
