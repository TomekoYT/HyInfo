package tomeko.hyinfo.utils

class FormattedTextCache(size: Int) {
    private val keys = arrayOfNulls<Any>(size)
    private val pending = arrayOfNulls<Any>(size)
    private var value: String? = null

    fun key(index: Int, key: Any?): FormattedTextCache {
        pending[index] = key
        return this
    }

    fun get(): String? {
        val value = value ?: return null
        return if (keys.contentEquals(pending)) value else null
    }

    fun put(value: String): String {
        pending.copyInto(keys)
        this.value = value
        return value
    }
}