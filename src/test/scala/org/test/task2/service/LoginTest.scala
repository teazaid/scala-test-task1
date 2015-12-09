package org.test.task2.service

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import org.test.task2.model.{Roles, User}

/**
 * Created by Alexander on 07.12.2015.
 */
class LoginTest extends FlatSpec with Matchers with BeforeAndAfterEach {


  override protected def afterEach(): Unit = {
    service.logOut()
  }

  val service = new LoginService(new Session)

  it should "return login user with role customer" in {
    val userName = "customer"

    val res = service.logIn(userName, userName.toCharArray)
    service.session.currentUser should be {res}

    res.isEmpty should be (false)
    res.get.name should be (userName)
    res.get.password should equal (userName.toCharArray)
    res.get.roles should be (List(Roles.Customer))
  }

  it should "not allow to login is already logged in" in {
    val userName1 = "customer"
    val userName2 = "admin"

    def validateUser(res: Option[User]): Unit = {
      res.isEmpty should be (false)
      res.get.name should be (userName1)
      res.get.password should equal (userName1.toCharArray)
      res.get.roles should be (List(Roles.Customer))
    }

    val res = service.logIn(userName1, userName1.toCharArray)
    service.session.currentUser should be {res}
    validateUser(res)
    val res2 = service.logIn(userName2, userName2.toCharArray)
    validateUser(res2)

  }

  it should "return login user with role admin" in {
    val userName = "admin"

    val res = service.logIn(userName, userName.toCharArray)
    service.session.currentUser should be {res}

    res.isEmpty should be (false)
    res.get.name should be (userName)
    res.get.password should equal (userName.toCharArray)
    res.get.roles should be (List(Roles.Admin))
  }

  it should "not login customer" in {
    val userName = "customer"
    val password = "scutomer".toCharArray

    val res = service.logIn(userName, password)
    service.session.currentUser should be {res}
    res.isEmpty should be (true)
  }

  it should "logout customer" in {
    val userName = "customer"
    val password = "scutomer".toCharArray

    service.logIn(userName, password)
    val res = service.logOut()
    service.session.currentUser should be {None}
    res.isEmpty should be (true)
  }
}
