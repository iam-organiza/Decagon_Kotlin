package com.example.week_5_task.functions

class Functions {
}

fun String.isValidName(): Boolean {
    val str = this.trim()
    if (str.matches("^[a-zA-Z ]*$".toRegex()) && str.length >= 3) {
        return true
    }
    return false
}

fun String.isValidPhone(): Boolean {
    val str = this.trim()
    if (str.matches("^[0-9]*$".toRegex()) && str.length >= 11 && (str.startsWith("0") || str.startsWith("234"))) {
        return true
    }
    return false
}

fun String.isValidEmail(): Boolean {
    val str = this.trim()
    if (str.matches("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$".toRegex())) {
        return true
    }
    return false
}

fun String.isValidGender(): Boolean {
    return when(this.trim().lowercase()) {
        "male" -> true
        "female" -> true
        else -> false
    }
}