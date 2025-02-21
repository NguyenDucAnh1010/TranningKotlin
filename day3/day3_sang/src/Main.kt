import model.NhanVien

fun enterValueNhanVien(): NhanVien {
    print("Nhập họ: ")
    val ho1 = readlnOrNull().orEmpty()
    print("Nhập tên: ")
    val ten1 = readlnOrNull().orEmpty()
    print("Nhập số sản phẩm: ")
    val soSP1 = readlnOrNull()?.toIntOrNull() ?: 0

    println("--------------------------------------")
    return NhanVien(ho1, ten1, soSP1)
}

fun printCompareValue(nv1: NhanVien, nv2: NhanVien) {
    println("${nv1.getHo()} ${nv1.getTen()} có nhiều hơn ${nv1.soSP - nv2.soSP} sản phẩm so với ${nv2.getHo()} ${nv2.getTen()}")
}

fun main() {
    println("Nhập thông tin của nhân viên 1")
    val nv1 = enterValueNhanVien()

    println("Nhập thông tin của nhân viên 2")
    val nv2 = enterValueNhanVien()

    nv1.printLuongNhanVien()
    nv2.printLuongNhanVien()
    println("--------------------------------------")

    if (nv1.lonHon(nv2)) {
        printCompareValue(nv1, nv2)
    } else if (nv2.lonHon(nv1)) {
        printCompareValue(nv2, nv1)
    } else {
        println("Cả hai nhân viên có số sản phẩm bằng nhau.")
    }

    if (nv1.soSP > nv2.soSP) {
        printCompareValue(nv1, nv2)
    } else if (nv2.soSP > nv1.soSP) {
        printCompareValue(nv2, nv1)
    } else {
        println("Cả hai nhân viên có số sản phẩm bằng nhau.")
    }
}
