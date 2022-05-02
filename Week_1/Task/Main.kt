import kotlin.system.exitProcess

class Main {
    var school: School? = null
    var principal: Principal? = null

    fun mainMenu() {
        if (school != null) {
            if (principal != null) {
                display { thirdMenu() }
                return
            }
            display { secondMenu() }
            return
        } else {
            println("Welcome to Organizer's school management menu")
            println("To create a new school - press 1")
            println("To exit - press 0 \n")

            var response = readLine()
            var answer: MutableList<String> = mutableListOf("1", "0")

            while (!answer.contains(response)) {
                println("")
                println("Wrong input! ")
                println("To create a new school - press 1")
                println("To exit - press 0 ")
                response = readLine()
            }

            if (response == "1") {
                println("")
                println("Provide name of school: ")
                var schoolName = readLine()

                while (schoolName!!.isEmpty()) {
                    println("")
                    println("Please provide a valid name: ")
                    schoolName = readLine()
                }

                school = School(schoolName)
                println("")
                println("School created! \n")
                display { mainMenu() }
            }

            if (response == "0") {
                exit()
            }
        }
    }

    fun secondMenu() {
        println("Menu (${school!!.name}): ")
        println("To view school - press 1") // Done
        println("To provide a principal - press 2") // Done
        println("To employ a staff - press 3")
        println("To view staff(s) - press - 4") // Done
        println("To admit a student - press - 5")
        println("To view students(s) - press - 6") // Done
        println("To expel a student - press - 7")
        println("Assign a student to a class - press - 8")
        println("Assign a subject to a staff(Academic) - press - 9")
//        println("To provide an applicant - press - 8")
//        println("To view applicant(s) - press - 9")
        println("Go back to main menu - press - #")
        println("To exit - press - 0 \n")

        var response = readLine()
        var answer: MutableList<String> = mutableListOf( "1", "2", "3", "4", "5", "6", "7", "8", "9", "#", "0" )

        while (!answer.contains(response)) {
            println("")
            println("Wrong input! ")
            println("To view school - press 1") // Done
            println("To provide a principal - press 2") // Done
            println("To employ a staff - press 3")
            println("To view staff(s) - press - 4") // Done
            println("To admit a student - press - 5")
            println("To view students(s) - press - 6") // Done
            println("To expel a student - press - 7")
            println("Assign a student to a class - press - 8")
            println("Assign a subject to a staff(Academic) - press - 9")
//            println("To provide an applicant - press - 8")
//            println("To view applicant(s) - press - 9")
            println("Go back to main menu - press - #")
            println("To exit - press - 0 \n")
            response = readLine()
        }

        if (response == "1") {
            println("")
            displaySchool({ secondMenu() }, principal)
        }

        if (response == "2") {
            assignPrincipal{ thirdMenu() }
        }

        if (response == "3") {
            employStaff({ secondMenu() }, principal)
        }

        if (response == "4") {
            displayStaffs({ secondMenu() }, principal)
        }

        if (response == "5") {
            admitStudent({ secondMenu() }, principal)
        }

        if (response == "6") {
            displayStudents({ secondMenu() }, principal)
        }

        if (response == "7") {
            expelStudent({ secondMenu() }, principal)
        }

        if (response == "8") {
            assignStudentClass({ secondMenu() }, principal)
        }

        if (response == "9") {
            assignTeacherSubject({ secondMenu() }, principal)
        }

        if (response == "#") {
            display { mainMenu() }
        }

        if (response == "0") {
            exit()
        }
    }

    fun thirdMenu() {
        println("Menu (${school!!.name}): ")
        println("To view school - press 1")
        println("To view school principal - press 2")
        println("To employ a staff - press 3")
        println("To view staff(s) - press - 4")
        println("To admit a student - press - 5")
        println("To view students(s) - press - 6")
        println("To expel a student - press - 7")
        println("Assign a student to a class - press - 8")
        println("Assign a subject to a staff(Academic) - press - 9")
        println("Switch privilege to principal - press - 10")
//        println("To provide an applicant - press - 8")
//        println("To view applicant(s) - press - 9")
        println("Go back to main menu - press - #")
        println("To exit - press - 0 \n")

        var response = readLine()
        var answer: MutableList<String> = mutableListOf( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "#", "0" )

        while (!answer.contains(response)) {
            println("")
            println("Wrong input! ")
            println("To view school - press 1")
            println("To view school principal - press 2")
            println("To employ a staff - press 3")
            println("To view staff(s) - press - 4")
            println("To admit a student - press - 5")
            println("To view students(s) - press - 6")
            println("To expel a student - press - 7")
            println("Assign a student to a class - press - 8")
            println("Assign a subject to a staff(Academic) - press - 9")
            println("Switch privilege to principal - press - 10")
//            println("To provide an applicant - press - 8")
//            println("To view applicant(s) - press - 9")
            println("Go back to main menu - press - #")
            println("To exit - press - 0 \n")
            response = readLine()
        }

        if (response == "1") {
            println("")
            displaySchool({ thirdMenu() }, principal)
        }

        if (response == "2") {
            println("")
            displayPrincipal({ thirdMenu() })
        }

        if (response == "3") {
            employStaff({ thirdMenu() }, principal)
        }

        if (response == "4") {
            displayStaffs({ thirdMenu() }, principal)
        }

        if (response == "5") {
            admitStudent({ thirdMenu() }, principal)
        }

        if (response == "6") {
            displayStudents({ thirdMenu() }, principal)
        }

        if (response == "7") {
            expelStudent({ thirdMenu() }, principal)
        }

        if (response == "8") {
            assignStudentClass({ thirdMenu() }, principal)
        }

        if (response == "9") {
            assignTeacherSubject({ secondMenu() }, principal)
        }

        if (response == "10") {
            display { fourthMenu() }
        }

        if (response == "#") {
            display { mainMenu() }
        }

        if (response == "0") {
            exit()
        }
    }

    fun fourthMenu() {
        println("Menu (${principal!!.name}): ")
        println("To view school - press 1")
        println("To view school principal - press 2")
        println("To employ a staff - press 3")
        println("To view staff(s) - press - 4")
        println("To admit a student - press - 5")
        println("To view students(s) - press - 6")
        println("To expel a student - press - 7")
        println("Assign a student to a class - press - 8")
        println("Assign a subject to a staff(Academic) - press - 9")
        println("Switch privilege to principal - press - 10")
//        println("To provide an applicant - press - 8")
//        println("To view applicant(s) - press - 9")
        println("Go back to main menu - press - #")
        println("To exit - press - 0 \n")

        var response = readLine()
        var answer: MutableList<String> = mutableListOf( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "#", "0" )

        while (!answer.contains(response)) {
            println("")
            println("Wrong input! ")
            println("To view school - press 1")
            println("To provide a principal - press 2")
            println("To employ a staff - press 3")
            println("To view staff(s) - press - 4")
            println("To admit a student - press - 5")
            println("To view students(s) - press - 6")
            println("To expel a student - press - 7")
            println("Assign a subject to a staff(Academic) - press - 9")
            println("Switch privilege to principal - press - 10")
//            println("To provide an applicant - press - 8")
//            println("To view applicant(s) - press - 9")
            println("Go back to main menu - press - #")
            println("To exit - press - 0 \n")
            response = readLine()
        }

        if (response == "1") {
            println("")
            displaySchool({ fourthMenu() }, principal)
        }

        if (response == "2") {
            println("")
            displayPrincipal({ fourthMenu() })
        }

        if (response == "3") {
            employStaff({ fourthMenu() }, principal)
        }

        if (response == "4") {
            displayStaffs({ fourthMenu() }, principal)
        }

        if (response == "5") {
            admitStudent({ fourthMenu() }, principal)
        }

        if (response == "6") {
            displayStudents({ fourthMenu() }, principal)
        }

        if (response == "7") {
            expelStudent({ fourthMenu() }, principal)
        }

        if (response == "8") {
            assignStudentClass({ fourthMenu() }, principal)
        }

        if (response == "9") {
            assignTeacherSubject({ fourthMenu() }, principal)
        }

        if (response == "10") {
            display { thirdMenu() }
        }

        if (response == "#") {
            display { mainMenu() }
        }

        if (response == "0") {
            exit()
        }
    }

    fun expelStudent (callback: () -> Unit, _principal: Principal? = null) {
        println("Provide student ID:")
        var id = readLine()

        while (id!!.isEmpty()) {
            println("Wrong input")
            println("Provide student ID:")
            id = readLine()
        }
        if (_principal != null) {
            _principal!!.expelStudent(id)
        } else {
            school!!.expelStudent(id)
        }
        callback()
    }

    fun assignStudentClass(callback: () -> Unit, _principal: Principal? = null) {
        println("Provide student ID: \n")
        var studentID = readLine()

        while (studentID!!.isEmpty()) {
            println("\n Wrong!")
            println("Please provide student ID: \n")
            studentID = readLine()
        }

        println("Select a class: ")
        println("${ClassLabel.JSS1} - press 1")
        println("${ClassLabel.JSS2} - press 2")
        println("${ClassLabel.JSS3} - press 3")
        println("${ClassLabel.SS1} - press 4")
        println("${ClassLabel.SS2} - press 5")
        println("${ClassLabel.SS3} - press 6")
        var response = readLine()
        var answers = mutableListOf<String>("1", "2", "3", "4", "5", "6")

        while (!answers.contains(response)) {
            println("Wrong input!\n")
            println("Select a class: ")
            println("${ClassLabel.JSS1} - press 1")
            println("${ClassLabel.JSS2} - press 2")
            println("${ClassLabel.JSS3} - press 3")
            println("${ClassLabel.SS1} - press 4")
            println("${ClassLabel.SS2} - press 5")
            println("${ClassLabel.SS3} - press 6")
            response = readLine()
        }

        var className = when (response) {
            "1" -> ClassLabel.JSS1
            "2" -> ClassLabel.JSS2
            "3" -> ClassLabel.JSS3
            "4" -> ClassLabel.SS1
            "5" -> ClassLabel.SS2
            "6" -> ClassLabel.SS3
            else -> {
                ClassLabel.NULL
            }
        }

        if (_principal != null) {
            var student: Any = _principal.getStudent(studentID)
            if (student is Student) {
                if (_principal.assignClass(studentID, className)) {
                    println("Class ${className} has been assigned to ${student.name} \n")
                    callback()
                    return
                }
                println("Class ${className} failed to be assigned to ${student.name} \n")
                callback()
                return
            }
            println("$student \n")
            callback()
            return
        } else {
            var student: Any = school!!.getStudent(studentID)
            if (student is Student) {
                if (school!!.assignClass(studentID, className) == true) {
                    println("Class ${className} has been assigned to ${student.name} \n")
                    callback()
                    return
                }
                println("Class ${className} failed to be assigned to ${student.name} \n")
                callback()
                return
            }
            println("$student \n")
            callback()
            return
        }
    }

    fun assignTeacherSubject(callback: () -> Unit, _principal: Principal? = null) {
        println("Provide staff(Academic) ID: \n")
        var staffID = readLine()

        while (staffID!!.isEmpty()) {
            println("\n Wrong!")
            println("Provide staff(Academic) ID: \n")
            staffID = readLine()
        }

        println("Select a subject: ")
        println("${Subjects.AGRIC} - press 1")
        println("${Subjects.CHEMISTRY} - press 2")
        println("${Subjects.ECONOMICS} - press 3")
        println("${Subjects.MATHS} - press 4")
        println("${Subjects.ENGLISH} - press 5")
        println("${Subjects.PHYSICS} - press 6")
        var response = readLine()
        var answers = mutableListOf<String>("1", "2", "3", "4", "5", "6")

        while (!answers.contains(response)) {
            println("Wrong input!\n")
            println("Select a subject: ")
            println("${Subjects.AGRIC} - press 1")
            println("${Subjects.CHEMISTRY} - press 2")
            println("${Subjects.ECONOMICS} - press 3")
            println("${Subjects.MATHS} - press 4")
            println("${Subjects.ENGLISH} - press 5")
            println("${Subjects.PHYSICS} - press 6")
            response = readLine()
        }

        var subjectName: Subjects = when (response) {
            "1" -> Subjects.AGRIC
            "2" -> Subjects.CHEMISTRY
            "3" -> Subjects.ECONOMICS
            "4" -> Subjects.MATHS
            "5" -> Subjects.ENGLISH
            "6" -> Subjects.PHYSICS
            else -> {
                Subjects.GOVERNMENT
            }
        }

        if (_principal != null) {
            var staff: Any = _principal.getStaff(staffID)
            if (staff is Staff) {
                var staffType: Any = _principal.getStaffType(staff.id)
                if (staffType is StaffType) {
                    if (staffType == StaffType.ACADEMIC) {
                        staff.addSubject(subjectName)
                        println("Subject ${subjectName} has been assigned to ${staff.name} \n")
                        callback()
                        return
                    }
                    println("${staff.name} is a Non academic staff \n")
                    callback()
                    return
                }
                println("$staffType \n")
                callback()
                return
            }
            println("$staff \n")
            callback()
            return
        } else {
            var staff: Any = school!!.getStaff(staffID)
            if (staff is Staff) {
                var staffType: Any = school!!.getStaffType(staff.id)
                if (staffType is StaffType) {
                    if (staffType == StaffType.ACADEMIC) {
                        staff.addSubject(subjectName)
                        println("Subject ${subjectName} has been assigned to ${staff.name} \n")
                        callback()
                        return
                    }
                    println("${staff.name} is a Non academic staff \n")
                    callback()
                    return
                }
                println("$staffType \n")
                callback()
                return
            }
            println("$staff \n")
            callback()
            return
        }
    }

    fun displayPrincipal(callback: () -> Unit, _principal: Principal? = null) {
        if (_principal != null) {
            println(_principal.toString())
        } else {
            println(school!!.getPrincipal().toString())
        }

        callback()
    }

    fun assignPrincipal( callback: () -> Unit ) {
        println("")
        println("Provide name of principal: ")
        var principalName = readLine()

        while (principalName!!.isEmpty()){
            println("Wrong input! ")
            println("Provide name of principal: ")
            principalName = readLine()
        }

        println("Provide age of principal: ")
        var principalAge = readLine()

        while (principalAge!!.isEmpty()){
            println("Wrong input! ")
            println("Provide age of principal: ")
            principalAge = readLine()
        }

        var id = school!!.getID()
        principal = Principal(id, principalName, principalAge.toInt())
        school!!.setPrincipal(principal!!)
        principal!!.setSchool(school!!)

        if (principal != null) {
            println("")
            println("Principal has been assigned to ${school!!.name}")
            println("Principal ID: ${principal!!.id} \n")
        }

        callback()
    }

    fun employStaff(callback: () -> Unit, _principal: Principal? = null) {
        println("")
        println("Provide name of staff: ")
        var staffName = readLine()

        while (staffName!!.isEmpty()){
            println("Wrong input! ")
            println("Provide name of staff: ")
            staffName = readLine()
        }

        println("Provide age of staff: ")
        var staffAge = readLine()

        while (staffAge!!.isEmpty()){
            println("Wrong input! ")
            println("Provide age of staff: ")
            staffAge = readLine()
        }

        println("")
        println("Select type for staff: ")
        println("${StaffType.ACADEMIC} - press 1")
        println("${StaffType.NON_ACADEMIC} - press 2 \n")

        var type = readLine()
        var answer: MutableList<String> = mutableListOf( "1", "2" )

        while (!answer.contains(type)) {
            println("")
            println("Wrong input! ")
            println("Select a type! ")
            println("${StaffType.ACADEMIC} - press 1")
            println("${StaffType.NON_ACADEMIC} - press 2 \n")
            type = readLine()
        }

        lateinit var staffType: StaffType
        if (type == "1") {
            staffType = StaffType.ACADEMIC
        }

        if (type == "2") {
            staffType = StaffType.NON_ACADEMIC
        }

        var id = school!!.getID()
        var user = User(staffName, staffAge.toInt())

        lateinit var staff: Staff
        if (_principal != null) {
            staff = _principal!!.employStaff(user, staffType)
        } else {
            staff = school!!.employStaff(user, staffType)
        }

        println("")
        println("Staff has been employed to ${school!!.name}")
        println("Staff ID: ${staff.id} \n")
        callback()
    }

    fun admitStudent(callback: () -> Unit, _principal: Principal? = null) {
        println("")
        println("Provide name of applicant: ")
        var applicantName = readLine()

        while (applicantName!!.isEmpty()){
            println("Wrong input! ")
            println("Provide name of applicant: ")
            applicantName = readLine()
        }

        println("Provide age of applicant: ")
        var applicantAge = readLine()

        while (applicantAge!!.isEmpty()){
            println("Wrong input! ")
            println("Provide age of applicant: ")
            applicantAge = readLine()
        }

        println("Provide score of applicant: ")
        var applicantScore = readLine()

        while (applicantScore!!.isEmpty()){
            println("Wrong input! ")
            println("Provide age of applicant: ")
            applicantScore = readLine()
        }

        var applicant = Applicant(applicantName, applicantAge.toInt(), applicantScore.toInt())

        lateinit var result: Any
        if (_principal != null) {
            result = _principal!!.admitStudent(applicant)
        } else {
            result = school!!.admitStudent(applicant)
        }

        if (result is Student) {
            println("Student has been admitted to ${school!!.name}")
            println("Student ID: ${result.id} \n")
        } else {
            println("")
            println(result)
        }
        callback()
    }

    fun displayStaffs(callback: () -> Unit, _principal: Principal? = null) {
        if (_principal != null) {
            _principal!!.displayStaffs()
        } else {
            school!!.displayStaffs()
        }
        callback()
    }

    fun displayStudents(callback: () -> Unit, _principal: Principal? = null) {
        if (_principal != null) {
            _principal!!.displayStudents()
        } else {
            school!!.displayStudents()
        }
        callback()
    }

    fun exit() {
        println("")
        println("Goodbye")
        exitProcess(1)
    }

    fun display(callback: () -> Unit) {
        callback()
    }

    fun displaySchool(callback: () -> Unit, _principal: Principal? = null) {
        println("School details: ")

        if (_principal != null) {
            println(_principal.schoolToString())
        } else {
            println(school.toString())
        }
        callback()
    }
}
fun main() {
    var main = Main()
    main.mainMenu()
}