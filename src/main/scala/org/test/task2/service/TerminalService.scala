package org.test.task2.service

import org.test.task2.model
import org.test.task2.model.Commands.Commands
import org.test.task2.model.{Commands, OrderStatus, Roles, User}

/**
 * Created by Alexander on 08.12.2015.
 */
class TerminalService(loginService: LoginService, itemsService: ItemsService, orderService: OrderService) {

  def getAvailableCommands(user: Option[User]): Set[Commands] = {
    user.map { u =>
      u.roles.flatMap { model.commandsByRole(_)}.toSet
    }.getOrElse(Set(Commands.LogIn, Commands.Exit))
  }

  def login(name: String, password: Array[Char]): Option[User] = {
    def validateNamePassword(): Unit =
      if(Option(name).isEmpty || name.isEmpty || Option(password).isEmpty || password.length == 0)
        throw new IllegalArgumentException("Either name or password are null or empty")

    validateNamePassword()
    loginService.logIn(name, password)
  }

  def logout(): Option[User] = loginService.logOut()

  def getAvailableItems(): Map[String, Int] = itemsService.getAvailableItems()

  def order(itemName: String, count: Int): OrderStatus = {
    validateItemNameCount(itemName, count)
    orderService.order(itemName, count)
  }

  def addItem(itemName: String, count: Int): Int = {
    validateItemNameCount(itemName, count)
    itemsService.addItem(itemName, count)
  }

  private def validateItemNameCount(itemName: String, count: Int): Unit =
    if(Option(itemName).isEmpty || itemName.isEmpty || count < 0)
      throw new IllegalArgumentException("Either name is null or empty or count is negative")
}
