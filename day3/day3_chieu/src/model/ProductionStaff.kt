package model

class ProductionStaff(
    firstName: String, lastName: String, basicSalary: Double, private val countProduct: Int
) : Staff(firstName, lastName, basicSalary) {
    override fun caculateSalary(): Double {
        val donGia = if (countProduct > 100) 50000.0 else 30000.0
        return basicSalary + countProduct * donGia
    }
}