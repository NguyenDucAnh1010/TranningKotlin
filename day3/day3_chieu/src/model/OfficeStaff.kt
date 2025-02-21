package model

class OfficeStaff(
    firstName: String,
    lastName: String,
    basicSalary: Double,
    private val countWorkingDay: Int
) : Staff(firstName, lastName, basicSalary) {
    override fun caculateSalary(): Double {
        val salaryOfDay = 200000.0
        return basicSalary + countWorkingDay * salaryOfDay
    }
}