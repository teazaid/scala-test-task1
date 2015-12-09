package org.test.task2

import org.test.task2.model.Roles.Roles

/**
 * Created by Alexander on 07.12.2015.
 */
object model {

  case class User(name: String, password: Array[Char], roles: List[Roles])
  case class OrderStatus(code: Int, message: String)

  object Roles extends Enumeration {
    type Roles = Value
    val Customer, Admin = Value
  }

  object Commands extends Enumeration {
    type Commands = Value
    val LogIn, LogOut, SeeItems, AddItem, BuyItem, Exit = Value
  }

  lazy val users = Map[(String, String), User](
    (admin, admin) -> User(admin, admin.toCharArray, List(Roles.Admin)),
    (customer, customer) -> User(customer, customer.toCharArray, List(Roles.Customer)),
    (adminCustomer, adminCustomer) -> User(adminCustomer, adminCustomer.toCharArray, List(Roles.Admin, Roles.Customer))
  )

  lazy val commandsByRole = Map(
    Roles.Admin -> Set(Commands.LogIn, Commands.LogOut, Commands.SeeItems, Commands.AddItem, Commands.Exit),
    Roles.Customer -> Set(Commands.LogIn, Commands.LogOut, Commands.SeeItems, Commands.BuyItem, Commands.Exit)
  )

  private val admin = "admin"
  private val customer = "customer"
  private val adminCustomer = "adminCustomer"
}
