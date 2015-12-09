package org.test.task2.service

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import org.test.task2.model.{Commands, OrderStatus, Roles, User}

/**
 * Created by Alexander on 08.12.2015.
 */
class TerminalServiceTest extends FlatSpec with Matchers with BeforeAndAfterEach {
  private val itemsService = new ItemsService
  private val service = new TerminalService(new LoginService(new Session), itemsService, new OrderService(itemsService))

  it should "print available commands for admin" in {
    val userName = "admin"
    val user = User(userName, userName.toCharArray, List(Roles.Admin))
    service.getAvailableCommands(Some(user)) should be {Set(Commands.LogIn, Commands.SeeItems, Commands.AddItem, Commands.LogOut, Commands.Exit)}
  }

  it should "print available commands for customer" in {
    val userName = "customer"
    val user = User(userName, userName.toCharArray, List(Roles.Customer))
    service.getAvailableCommands(Some(user)) should be {Set(Commands.LogIn, Commands.SeeItems, Commands.BuyItem, Commands.LogOut, Commands.Exit)}
  }

  it should "print available commands for customerAdmin" in {
    val userName = "customerAdmin"
    val user = User(userName, userName.toCharArray, List(Roles.Admin, Roles.Customer))
    service.getAvailableCommands(Some(user)) should be {Set(Commands.LogIn, Commands.SeeItems, Commands.BuyItem, Commands.AddItem, Commands.LogOut, Commands.Exit)}
  }

  it should "print available commands if there is no logged in user" in {
    service.getAvailableCommands(None) should be {Set(Commands.LogIn, Commands.Exit)}
  }

  it should "test logout delegation" in {
    service.logout().isEmpty should be (true)
  }

  it should "test login delegation" in {
    val userName = "admin"

    val res = service.login(userName, userName.toCharArray)
    res.isEmpty should be (false)
    res.get.name should be (userName)
    res.get.password should equal (userName.toCharArray)
    res.get.roles should be (List(Roles.Admin))
  }

  it should "throw exception if user is null" in {
    intercept[IllegalArgumentException]{
      service.login(null, Array('1'))
    }
  }

  it should "throw exception if user is empty" in {
    intercept[IllegalArgumentException]{
      service.login("", Array('1'))
    }
  }

  it should "throw exception if is null" in {
    intercept[IllegalArgumentException]{
      service.login("1", null)
    }
  }

  it should "throw exception if password is empty" in {
    intercept[IllegalArgumentException]{
      service.login("1", Array())
    }
  }

  it should "test getAvailableItems delegation" in {
    service.getAvailableItems() should be (Map("item1" -> 40))
  }

  it should "order buyItem delegation" in {
    service.order("item1", 4) should be (OrderStatus(0, OrderSupport.confirmationMessage))
  }

  it should "order throw exception if item is null" in {
    intercept[IllegalArgumentException](service.order(null, 4))
  }

  it should "order throw exception if item is empty" in {
    intercept[IllegalArgumentException](service.order("", 4))
  }

  it should "order throw exception if count is negative" in {
    intercept[IllegalArgumentException](service.order("item1", -4))
  }

  it should "test addItems delegation" in {
    service.addItem("itemX", 5) should be (5)
  }

  it should "addItem throw exception if item is null" in {
    intercept[IllegalArgumentException](service.addItem(null, 4))
  }

  it should "addItem throw exception if item is empty" in {
    intercept[IllegalArgumentException](service.addItem("", 4))
  }

  it should "addItem throw exception if count is negative" in {
    intercept[IllegalArgumentException](service.addItem("item1", -4))
  }

}
