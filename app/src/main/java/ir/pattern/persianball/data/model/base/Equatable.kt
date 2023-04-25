package ir.pattern.persianball.data.model.base

interface Equatable {
    override fun equals(other: Any?): Boolean
    fun getUniqueId(): String
}