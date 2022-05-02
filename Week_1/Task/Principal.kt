class Principal(var id: String, name: String, age: Int): User(name, age) {
    private lateinit var school: School

    override fun toString(): String {
        var str: String = "Principal details: \n"
        str += "ID: ${this.id} \n"
        str += "Name: ${this.name} \n"
        str += "Age: ${this.age} \n"

        if (this.school != null) {
            str += "Assigned school: ${this.school.name} \n"
        } else {
            str += "Assigned school: yet to be assigned a school \n"
        }

        return str
    }

    fun getStaff(id: String): Any {
        if (this.school != null) {
            return this.school.getStaff(id)
        }

        return "You are yet to be assigned a school as a principal!"
    }

    fun getStaffType(id: String): Any {
        if (this.school != null) {
            return this.school.getStaffType(id)
        }

        return "You are yet to be assigned a school as a principal!"
    }

    fun setSchool(school: School) {
        this.school = school
    }

    fun getStudent(id: String): Any {
        if (this.school != null) {
            return this.school.getStudent(id)
        }

        return "You are yet to be assigned a school as a principal!"
    }

    fun assignClass(id: String, className: ClassLabel): Boolean {
        if (this.school != null) {
            return this.school.assignClass(id, className)
        }

        return false
    }

    fun admitStudent(applicant: Applicant) {
        if (this.school != null) {
            this.school.admitStudent(applicant)
            return
        }

        println("You are yet to be assigned a school as a principal!")
        return
    }

    fun expelStudent(id: String) {
        if (this.school != null) {
            this.school.expelStudent(id)
            return
        }

        println("You are yet to be assigned a school as a principal!")
        return
    }

    fun expelStudents(ids: MutableList<String>) {
        if (this.school != null) {
            this.school.expelStudents(ids)
            return
        }

        println("You are yet to be assigned a school as a principal!")
        return
    }

    fun employStaff(user: User, type: StaffType): Staff {
        return this.school.employStaff(user, type)
    }

    fun displayStaffs() {
        if (this.school != null) {
            this.school.displayStaffs()
            return
        }

        println("You are yet to be assigned a school as a principal!")
        return
    }

    fun assignTeacherSubject(id: String, subject: Subjects) {
        if (this.school != null) {
            this.school.assignTeacherSubject(id, subject)
            return
        }

        println("You are yet to be assigned a school as a principal!")
        return
    }

    fun assignTeacherSubjects(id: String, subjects: MutableList<Subjects>) {
        if (this.school != null) {
            this.school.assignTeacherSubjects(id, subjects)
            return
        }

        println("You are yet to be assigned a school as a principal!")
        return
    }

    fun displayTeacherSubjects(id: String) {
        if (this.school != null) {
            this.school.displayTeacherSubjects(id)
            return
        }

        println("You are yet to be assigned a school as a principal!")
        return
    }

    fun displayStudents() {
        this.school.displayStudents()
    }

    fun schoolToString(): String {
        var str = ""
        str += "${this.name}(Principal) school details: \n"

        var nonAcademics: Int = 0
        var academics: Int = 0
        for (staff in this.school!!.getStaffs()) {
            when (staff.type) {
                StaffType.NON_ACADEMIC -> nonAcademics += 1
                StaffType.ACADEMIC -> academics += 1
            }
        }

        str += "${this.school!!.getStaffs().size} staff(s) \n"
        str += "${this.school!!.getStudents().size} student(s) \n"
        str += "${nonAcademics} Non-academic staff(s) \n"
        str += "& ${academics} Academic staff(s) \n"

        return str
    }
}