class Staff(var id: String, name: String, age: Int, var type: StaffType): User(name, age) {
    private var teaches: MutableList<Subjects> = mutableListOf()

    override fun toString(): String {
        var str: String = "Staff details: \n"
        str += "ID: ${this.id} \n"
        str += "Name: ${this.name} \n"
        str += "Age: ${this.age} \n"
        str += "Staff type: ${this.type} \n"

        if (this.teaches != null) {
            str += "Assigned subject(s): ${this.teaches.toString()} \n"
        } else {
            str += "Assigned subject(s): yet to be assigned a subject \n"
        }

        return str
    }

    fun addSubject(subject: Subjects) {
        this.teaches.add(subject)
    }

    fun getSubjects(): MutableList<Subjects> {
        return this.teaches
    }

    fun setSubjects(subjects: MutableList<Subjects>) {
        for (subject in subjects) {
            this.teaches.add(subject)
        }
    }

    fun setSubject(subject: Subjects) {
        this.teaches.add(subject)
    }
}