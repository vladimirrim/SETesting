package ru.hse.egorov

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class LoginPage(private val driver: WebDriver) {
    private val password = By.name("l.L.password")
    private val login = By.name("l.L.login")
    private val loginButton = By.id("id_l.L.loginButton")

    fun setPassword(password: String) = driver.findElement(this.password).sendKeys(password)

    fun setLogin(login: String) = driver.findElement(this.login).sendKeys(login)

    fun login(login: String, password: String) {
        setLogin(login)
        setPassword(password)
        driver.findElement(loginButton).click()
    }
}