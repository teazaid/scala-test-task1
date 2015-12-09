package org.test.task2.service

import org.test.task2.model.OrderStatus

/**
 * Created by Alexander on 08.12.2015.
 */
sealed trait OrderSupport {

  def itemsService: ItemsService

  def order(itemName: String, itemsToBuyCount: Int): OrderStatus =
    itemsService.items.get(itemName) match {
      case Some(value) if(value < itemsToBuyCount) => OrderStatus(-1, OrderSupport.errorMessage)
      case Some(value) =>
        itemsService.addItem(itemName, -itemsToBuyCount)
        OrderStatus(0, OrderSupport.confirmationMessage)

      case _ => OrderStatus(-1, OrderSupport.errorMessage)
    }
}
object OrderSupport {
  val confirmationMessage = "Order completed"
  val errorMessage = "Too many items to buy"
}

class OrderService(val itemsService: ItemsService) extends OrderSupport
