
class Student(var id: String, var name: String) {
    private var className: ClassLabel = ClassLabel.NULL
    private lateinit var school: School

    override fun toString(): String {
        var str: String = ""
        str += "ID: ${this.id} \n"
        str += "Name: ${this.name} \n"
        if (this.className != ClassLabel.NULL)
            str += "Class name: ${this.className} \n"
        else
            str += "Class name: yet to be assigned a class! \n"
        return str
    }

    fun setClassName(classLabel: ClassLabel) {
        this.className = classLabel
    }

    fun getClassName() {
        if (this.className != null) {
            println("Class name: ${this.className} \n")
            return
        }
        println("You have not been assigned a class yet \n")
    }

    fun getSchool() {
        if (this.school != null) {
            println("School: ${this.school.name} \n")
            return
        }
        println("You are yet to be admitted to be admitted to a school! \n")
    }
}