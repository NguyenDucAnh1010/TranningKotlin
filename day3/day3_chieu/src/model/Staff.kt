package model

abstract class Staff(
    private val firstName: String,
    private val lastName: String,
    protected var basicSalary: Double
) {
    abstract fun caculateSalary(): Double
    fun getFullName(): String = "$firstName $lastName"
    fun getSalary(): String = "Lương của ${getFullName()}: ${caculateSalary()} VND"
}