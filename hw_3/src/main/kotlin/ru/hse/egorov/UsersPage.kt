package ru.hse.egorov

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver


class UsersPage(private val driver: WebDriver) {

    private var usersTable = By.id("id_l.U.usersList.usersList")
    private var goToCreateForm = By.id("id_l.U.createNewUser")

    fun getUsers() = driver.findElement(usersTable).findElement(By.tagName("tbody"))
        .findElements(By.tagName("tr"))
        .map {
            it.findElement(By.xpath(".//*[@cn='l.U.usersList.UserLogin.editUser']"))
                .getAttribute("title")
        }

    fun deleteUser(name: String) = driver.findElement(usersTable).findElement(By.tagName("tbody"))
        .findElements(By.tagName("tr"))
        .filter {
            it.findElement(By.xpath(".//*[@cn='l.U.usersList.UserLogin.editUser']"))
                .getAttribute("title") == name
        }
        .forEach {
            it.findElements(By.xpath(".//*[@cn='l.U.usersList.deleteUser']"))[0].click()
            driver.switchTo().alert().accept()
        }

    fun goToCreateUserForm() = driver.findElement(goToCreateForm).click()
}