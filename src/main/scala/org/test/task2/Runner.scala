package org.test.task2

import org.test.task2.model.Commands
import org.test.task2.model.Commands._
import org.test.task2.service._

import scala.annotation.tailrec
import scala.io.StdIn

/**
 * Created by Alexander on 09.12.2015.
 */
object Runner {
  def main(arg: Array[String]): Unit = {
    val loginService = new LoginService(new Session)
    val itemService = new ItemsService
    val orderService = new OrderService(itemService)
    val terminalService = new TerminalService(loginService, itemService, orderService)

    enterCommand(terminalService, loginService)
  }

  @tailrec
  def enterCommand(terminalService: TerminalService, loginService: LoginService): Unit = {
    val availableCommands = terminalService.getAvailableCommands(loginService.session.currentUser)
    println("Available commands:")
    availableCommands.foreach{println(_)}
    val enteredCommand = Commands.withName(StdIn.readLine())
      if(availableCommands.contains(enteredCommand)) processCommand(enteredCommand, terminalService)
    println
    enterCommand(terminalService, loginService)
  }

  def processCommand(command: Commands, terminalService: TerminalService): Unit =
    command match {
      case Commands.LogIn =>
        println("Enter user name:")
        val userName = StdIn.readLine()
        println("Enter password:")
        val password = StdIn.readLine().toCharArray
        terminalService.login(userName, password)
      case Commands.LogOut => terminalService.logout()
      case Commands.SeeItems => terminalService.getAvailableItems().foreach(item => println(s"${item._1} ${item._2}"))
      case Commands.BuyItem =>
        println("Enter item name:")
        val itemName = StdIn.readLine()
        println("Enter amount of items you want to by:")
        val count = StdIn.readInt()
        val orderResult = terminalService.order(itemName, count)
        println(orderResult.message)
      case Commands.AddItem =>
        println("Enter item name:")
        val itemName = StdIn.readLine()
        println("Enter amount of items you want to add:")
        val count = StdIn.readInt()
        terminalService.addItem(itemName, count)
      case Commands.Exit => System.exit(1)
      case _ => throw new UnsupportedOperationException("Unsupported operation")
    }
}
