
class School(var name: String) {
    private var idCount: Int = 0
    private lateinit var id: String
    private var students: MutableList<Student> = mutableListOf()
    private var staffs: MutableList<Staff> = mutableListOf()
    private lateinit var principal: Principal

    override fun toString(): String {
        var str = ""
        str += "${this.name} has \n"

        var nonAcademics: Int = 0
        var academics: Int = 0
        for (staff in this.staffs) {
            when (staff.type) {
                StaffType.NON_ACADEMIC -> nonAcademics += 1
                StaffType.ACADEMIC -> academics += 1
            }
        }

        str += "${this.staffs.size} staff(s) \n"
        str += "${this.students.size} student(s) \n"
        str += "${nonAcademics} Non-academic staff(s) \n"
        str += "& ${academics} Academic staff(s) \n"

        return str
    }

    fun getID(): String {
        this.idCount++
        var id = "${this.name.get(0).uppercaseChar()}${this.idCount}"
        return id
    }

    fun setPrincipal(principal: Principal) {
        if (principal != null) {
            this.principal = principal
//            this.principal.setSchool(this)
            println("Principal assigned! \n")
            return
        }

        println("Principal has already been assign to this school! \n")
        return
    }

    fun getPrincipal(): Principal {
        return this.principal
    }

    fun hasPrincipal(): Boolean {
        println(this.principal)
        if (this.principal != null)
            return true
        return false
    }

    fun getLastId(): String {
        return "${this.name.get(0).uppercaseChar()}${this.idCount}"
    }

    /**
     * Student method(s) beginning...
     */
    fun isStudent(id: String): Boolean {
        var student = this.getStudent(id)

        if (student != null && this.students.contains(student))
            return true

        return false
    }

    fun getStudent(id: String): Student {
        lateinit var _student: Student
        for (student in this.students) {
            if (student.id == id)
                _student = student
        }
        return _student
    }

    fun getStudents(): MutableList<Student> {
        var _students: MutableList<Student> = mutableListOf()
        for (student in this.students) {
            _students.add(student)
        }
        return _students
    }

    fun displayStudents() {
        var displayStr = ""
        for (_student in this.students) {
            displayStr += _student.toString()
        }

        if (!displayStr.isEmpty()) {
            println(displayStr)
            return
        }

        println("No student(s) found! \n")
        return
    }

    fun admitStudent(applicant: Applicant): Any {
        if (applicant.age >= 14) {
            if (applicant.score >= 50) {
                this.idCount++
                var id = "${this.name.get(0).uppercaseChar()}${this.idCount}"
                var student: Student = Student(id, applicant.name)
                this.students.add(student)
                return student
            } else {
                return "Applicant did not make the cut off mark \n"
            }
        } else {
            return "Applicant is too young to be admitted \n"
        }
    }

    fun assignClass(id: String, className: ClassLabel): Boolean {
        var student: Student = this.getStudent(id)
        if (student !== null) {
            student.setClassName(className)
            return true
        }
        return false
    }

    fun expelStudent(id: String): Boolean {
        lateinit var _student: Student
        for (student in this.students) {
            if (student.id == id) {
                this.students.remove(student)
                return true
            }
        }
        return false
    }

    fun expelStudents(ids: MutableList<String>) {
        lateinit var _student: Student
        for (id in ids) {
            if (isStudent(id)) {
                expelStudent(id)
            } else {
                println("Student with ID: ${id} not found \n")
            }
        }
        return
    }
    /**
     * Student method(s) ending...
     */

    /**
     * Staff method(s) beginning
     */

    fun employStaff(user: User, type: StaffType): Staff {
        this.idCount++
        var id = "${this.name.get(0).uppercaseChar()}${this.idCount}"
        var staff: Staff = Staff(id, user.name, user.age, type)

        this.staffs.add(staff)
        return staff
    }

    fun isStaff(id: String): Boolean {
        var staff = this.getStaff(id)

        if (staff != null && this.staffs.contains(staff))
            return true

        return false
    }

    fun getStaff(id: String): Staff {
        lateinit var _staff: Staff
        for (staff in this.staffs) {
            if (staff.id == id)
                _staff = staff
        }
        return _staff
    }

    fun getStaffType(id: String): Any {
        for (staff in this.staffs) {
            if (staff.id == id && staff.type == StaffType.ACADEMIC)
                return staff.type
        }
        return "Staff / Class type not found! \n"
    }

    fun getStaffs(): MutableList<Staff> {
        return this.staffs
    }

    fun displayStaffs() {
        var displayStr = ""
        for (_staff in this.staffs) {
            displayStr += _staff.toString()
            displayStr += "\n"
        }

        if (!displayStr.isEmpty()) {
            println(displayStr)
            return
        }

        println("No staff(s) found! \n")
        return
    }

    /**
     * Staff method(s) ending
     */

    /**
     * Teacher method(s) beginning
     */
    fun assignTeacherSubject(id: String, subject: Subjects) {
        if (this.isStaff(id)) {
            var staff = this.getStaff(id)
            if (staff.type == StaffType.ACADEMIC) {
                staff.addSubject(subject)
                println("Subject has been added \n")
                return
            } else {
                println("${staff.name} is not an academic staff \n")
                return
            }
            return
        }
        println("Staff not found!")
    }

    fun assignTeacherSubjects(id: String, subjects: MutableList<Subjects>) {
        if (this.isStaff(id)) {
            var staff = this.getStaff(id)
            if (staff.type == StaffType.ACADEMIC) {
                for (subject in subjects) {
                    staff.addSubject(subject)
                }
                println("Subject(s) has been added! \n")
                return
            } else {
                println("${staff.name} is not an academic staff \n")
                return
            }
            return
        }

        println("Staff not found! \n")
    }

    fun displayTeacherSubjects(id: String) {
        if (this.isStaff(id)) {
            var staff = this.getStaff(id)
            if (staff.type == StaffType.ACADEMIC) {
                println("Subject(s): ${staff.getSubjects().toString()}! \n")
                return
            } else {
                println("${staff.name} is not an academic staff \n")
                return
            }
            return
        }

        println("Staff not found! \n")
    }
    /**
     * Teachers method(s) beginning
     */

}

fun main() {

}