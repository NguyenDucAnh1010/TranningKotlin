package model

class NhanVien(private var ho: String, private var ten: String, soSP: Int) {
    var soSP: Int = if (soSP >= 0) soSP else 0
        private set

    fun getHo() = ho
    fun setHo(ho: String) {
        this.ho = ho
    }

    fun getTen() = ten
    fun setTen(ten: String) {
        this.ten = ten
    }

    fun setSoSP(soSP: Int) {
        this.soSP = if (soSP >= 0) soSP else 0
    }

    fun getLuong(): Double {
        return when {
            soSP in 1..199 -> soSP * 0.5
            soSP in 200..399 -> soSP * 0.55
            soSP in 400..599 -> soSP * 0.6
            soSP >= 600 -> soSP * 0.65
            else -> 0.0
        }
    }

    fun printLuongNhanVien() {
        println("Lương của $ho $ten: ${getLuong()}")
    }

    fun lonHon(nv2: NhanVien) = this.soSP > nv2.soSP
}