package ir.pattern.persianball.utils

object IDGenerator {
	private var lastID: Long = 0
	fun generateStringID(): String {
		return generateLongID().toString()
	}

	fun generateLongID(): Long {
		return ++lastID
	}
}