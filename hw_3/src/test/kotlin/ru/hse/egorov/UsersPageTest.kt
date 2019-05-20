package ru.hse.egorov


import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit


class UsersPageTest {
    private lateinit var driver: WebDriver

    @Before
    fun setup() {
        driver = ChromeDriver()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver.get("localhost:8080")
    }

    @Test
    fun createUser() {
        val usersPage =  UsersPage(driver)
        val createUserForm = CreateUserForm(driver)
        val loginPage = LoginPage(driver)

        loginPage.login("root", "12345")
        WebDriverWait(driver, 5).ignoring(StaleElementReferenceException::class.java).
            until(ExpectedConditions.urlToBe("http://localhost:8080/dashboard"))
        goToUsers()
        usersPage.deleteUser("testUser")
        goToUsers()
        usersPage.goToCreateUserForm()
        createUserForm.create("testUser", "pass", "pass")
        WebDriverWait(driver, 5).ignoring(StaleElementReferenceException::class.java).
            until(ExpectedConditions.urlToBe("http://localhost:8080/editUser/testUser"))
        goToUsers()
        print(usersPage.getUsers())
        assertTrue(usersPage.getUsers().contains("testUser"))
    }

    private fun goToUsers(){
        driver.get("localhost:8080/users")
        WebDriverWait(driver, 5).ignoring(StaleElementReferenceException::class.java).
            until(ExpectedConditions.urlToBe("http://localhost:8080/users"))
    }

    @Test
    fun emptyLogin() {
        val usersPage =  UsersPage(driver)
        val createUserForm = CreateUserForm(driver)
        val loginPage = LoginPage(driver)

        loginPage.login("root", "12345")
        WebDriverWait(driver, 5).ignoring(StaleElementReferenceException::class.java).
            until(ExpectedConditions.urlToBe("http://localhost:8080/dashboard"))
        goToUsers()
        usersPage.goToCreateUserForm()
        createUserForm.create("", "", "")

        assertTrue(createUserForm.isError())
    }

    @Test
    fun loginWithForbiddenSymbols(){
        val usersPage =  UsersPage(driver)
        val createUserForm = CreateUserForm(driver)
        val loginPage = LoginPage(driver)

        loginPage.login("root", "12345")
        WebDriverWait(driver, 5).ignoring(StaleElementReferenceException::class.java).
            until(ExpectedConditions.urlToBe("http://localhost:8080/dashboard"))
        goToUsers()
        usersPage.goToCreateUserForm()
        createUserForm.create("/", "1", "1")
        assertTrue(createUserForm.isPopupError())
        createUserForm.create("<", "1", "1")
        assertTrue(createUserForm.isPopupError())
        createUserForm.create(">", "1", "1")
        assertTrue(createUserForm.isPopupError())
    }

    @Test
    fun loginSameUser(){
        val usersPage =  UsersPage(driver)
        val createUserForm = CreateUserForm(driver)
        val loginPage = LoginPage(driver)

        loginPage.login("root", "12345")
        WebDriverWait(driver, 5).ignoring(StaleElementReferenceException::class.java).
            until(ExpectedConditions.urlToBe("http://localhost:8080/dashboard"))
        goToUsers()
        usersPage.goToCreateUserForm()
        createUserForm.create("root", "1", "1")
        assertTrue(createUserForm.isPopupError())
    }
}