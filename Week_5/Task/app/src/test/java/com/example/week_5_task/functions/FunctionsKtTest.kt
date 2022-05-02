package com.example.week_5_task.functions

import org.junit.Assert.*

import org.junit.Test

class FunctionsKtTest {

    /*
    * Tests for valid name(s)
    * */
    /*
        Test Case 1:
            When given a string of letters
    */
    @Test
    fun isValidName_test_case_1() {
        // Given
        val string = "samuel"

        // When
        val output = string.isValidName()

        // Assert
        assertEquals(output, true)
    }

    /*
        Test Case 2:
            When given a string of letters and numbers
    */
    @Test
    fun isValidName_test_case_2() {
        // Given
        val string = "samuel1234"

        // When
        val output = string.isValidName()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 3:
            When given a string of letters and numbers with space(s)
    */
    @Test
    fun isValidName_test_case_3() {
        // Given
        val string = "samuel1234 organizer"

        // When
        val output = string.isValidName()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 4:
            When given a string of letters & space(s)
    */
    @Test
    fun isValidName_test_case_4() {
        // Given
        val string = "samuel organizer"

        // When
        val output = string.isValidName()

        // Assert
        assertEquals(output, true)
    }

    /*
        Test Case 5:
            When given a string of letters mixed with Capital letters
    */
    @Test
    fun isValidName_test_case_5() {
        // Given
        val string = "samuel organizer"

        // When
        val output = string.isValidName()

        // Assert
        assertEquals(output, true)
    }

    /*
        Test Case 6:
            When given an empty string
    */
    @Test
    fun isValidName_test_case_6() {
        // Given
        val string = ""

        // When
        val output = string.isValidName()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 7:
            When given a string with only space(s)
    */
    @Test
    fun isValidName_test_case_7() {
        // Given
        val string = " "

        // When
        val output = string.isValidName()

        // Assert
        assertEquals(output, false)
    }

    /*
    * Tests for valid phone number
    * */
    /*
        Test Case 1:
            When given a string of numbers that starts with zero
    */
    @Test
    fun isValidPhone_case_1() {
        // Given
        val string = "08181785509"

        // When
        val output = string.isValidPhone()

        // Assert
        assertEquals(output, true)
    }

    /*
        Test Case 2:
            When given a string of numbers that starts with 234
    */
    @Test
    fun isValidPhone_case_2() {
        // Given
        val string = "2348181785509"

        // When
        val output = string.isValidPhone()

        // Assert
        assertEquals(output, true)
    }

    /*
        Test Case 3:
            When given a string of numbers and letters
    */
    @Test
    fun isValidPhone_case_3() {
        // Given
        val string = "2348181785samuel509"

        // When
        val output = string.isValidPhone()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 4:
            When given a string of numbers less than 11
    */
    @Test
    fun isValidPhone_case_4() {
        // Given
        val string = "081817855"

        // When
        val output = string.isValidPhone()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 5:
            When given a string with only space(s)
    */
    @Test
    fun isValidPhone_test_case_5() {
        // Given
        val string = "   "

        // When
        val output = string.isValidPhone()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 5:
            When given an empty string
    */
    @Test
    fun isValidPhone_test_case_6() {
        // Given
        val string = ""

        // When
        val output = string.isValidPhone()

        // Assert
        assertEquals(output, false)
    }

    /*
    * Tests for valid email address
    * */
    /*
        Test Case 1:
            When given a valid email string with the format (example@domain.com)
    */
    @Test
    fun isValidEmail_test_case_1() {
        // Given
        val string = "example@domain.com"

        // When
        val output = string.isValidEmail()

        // Assert
        assertEquals(output, true)
    }

    /*
        Test Case 2:
            When given an email string without the @ symbol. (format: exampledomain.com)
    */
    @Test
    fun isValidEmail_test_case_2() {
        // Given
        val string = "exampledomain.com"

        // When
        val output = string.isValidEmail()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 3:
            When given an email string without the . symbol. (format: example@domaincom)
    */
    @Test
    fun isValidEmail_test_case_3() {
        // Given
        val string = "example@domaincom"

        // When
        val output = string.isValidEmail()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 4:
            When given an email string without the @ and . symbol. (format: exampledomaincom)
    */
    @Test
    fun isValidEmail_test_case_4() {
        // Given
        val string = "exampledomaincom"

        // When
        val output = string.isValidEmail()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 5:
            When given an email string with more than one @ symbol. (format: exa@mple@domain.com)
    */
    @Test
    fun isValidEmail_test_case_5() {
        // Given
        val string = "exa@mple@domain.com"

        // When
        val output = string.isValidEmail()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 6:
            When given an empty email string
    */
    @Test
    fun isValidEmail_test_case_6() {
        // Given
        val string = ""

        // When
        val output = string.isValidEmail()

        // Assert
        assertEquals(output, false)
    }

    /*
    * Tests for valid gender
    * */
    /*
        Test Case 1:
            When given the string "male" in lowercase
    */
    @Test
    fun isValidGender_test_case_1() {
        // Given
        val string = "male"

        // When
        val output = string.isValidGender()

        // Assert
        assertEquals(output, true)
    }

    /*
        Test Case 2:
            When given the string "female" in lowercase
    */
    @Test
    fun isValidGender_test_case_2() {
        // Given
        val string = "female"

        // When
        val output = string.isValidGender()

        // Assert
        assertEquals(output, true)
    }

    /*
        Test Case 3:
            When given the string "Female" in lowercase & uppercase
    */
    @Test
    fun isValidGender_test_case_3() {
        // Given
        val string = "Female"

        // When
        val output = string.isValidGender()

        // Assert
        assertEquals(output, true)
    }

    /*
        Test Case 4:
            When given the string "Male" in lowercase & uppercase
    */
    @Test
    fun isValidGender_test_case_4() {
        // Given
        val string = "Female"

        // When
        val output = string.isValidGender()

        // Assert
        assertEquals(output, true)
    }

    /*
        Test Case 5:
            When given an empty string ""
    */
    @Test
    fun isValidGender_test_case_5() {
        // Given
        val string = ""

        // When
        val output = string.isValidGender()

        // Assert
        assertEquals(output, false)
    }

    /*
        Test Case 6:
            When given any string other than "male" or "female"
    */
    @Test
    fun isValidGender_test_case_6() {
        // Given
        val string = "samuel"

        // When
        val output = string.isValidGender()

        // Assert
        assertEquals(output, false)
    }
}