package ru.hse.egorov

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver


class CreateUserForm(private val driver: WebDriver) {

    private var username = By.name("l.U.cr.login")

    private var password = By.name("l.U.cr.password")

    private var confirmPassword = By.name("l.U.cr.confirmPassword")

    private var errorBulb = By.className("error-bulb2")

    private var errorAlert = By.id("__popup__1")

    private var loginButton = By.id("id_l.U.cr.createUserOk")

    private fun setUserName(strUserName: String) = driver.findElement(username).sendKeys(strUserName)

    private fun setPassword(strPassword: String) = driver.findElement(password).sendKeys(strPassword)

    private fun setConfirmPassword(strPassword: String) = driver.findElement(confirmPassword).sendKeys(strPassword)

    private fun clickLogin() = driver.findElement(loginButton).click()

    fun isError() = driver.findElement(errorBulb).isDisplayed

    fun isPopupError() =  driver.findElement(errorAlert).isDisplayed

    fun create(strUserName: String, strPasword: String, strConfirmPassword: String) {
        setUserName(strUserName)
        setPassword(strPasword)
        setConfirmPassword(strConfirmPassword)
        clickLogin()
    }
}