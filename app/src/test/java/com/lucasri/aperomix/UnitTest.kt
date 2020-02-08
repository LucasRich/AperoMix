package com.lucasri.aperomix

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AddPropertyFragmentTest {

    //DATA

    // ---------------------
    // FUNCTION TO TEST
    // ---------------------

    private fun passwordIsGood(password: String): Boolean{
        return passwordContainEightChar(password) && passwordContainUpperCase(password) && passwordContainNumber(password)
    }

    private fun passwordContainEightChar(password: String): Boolean{
        return password.length >= 8
    }

    private fun passwordContainUpperCase(password: String): Boolean {
        var result : Boolean = false

        for (i in 0 until password.length) {
            val char = password[i]
            if (char.toInt() in 90 downTo 65) result = true
        }
        return result
    }

    private fun passwordContainNumber(password: String): Boolean {
        var result : Boolean = false

        for (i in 0 until password.length) {
            val char = password[i]
            if (char.toInt() in 57 downTo 48) result = true
        }
        return result
    }

    // ---------------------
    // TEST
    // ---------------------

    @Test
    fun passwordIsGood_isCorrect() {
        val result = passwordIsGood("Abcdef12")
        assertEquals(true, result)
    }

    @Test
    fun passwordContainEightChar_isCorrect() {
        val result = passwordContainEightChar("abcd")
        assertEquals(false, result)
    }

    @Test
    fun passwordContainUpperCase_isCorrect() {
        val result = passwordContainUpperCase("A")
        assertEquals(true, result)
    }

    @Test
    fun passwordContainNumber_isCorrect() {
        val result = passwordContainNumber("AZDZ")
        assertEquals(false, result)
    }

}