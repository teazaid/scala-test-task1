package org.test.task2.service

/**
 * Created by Alexander on 08.12.2015.
 */
class ItemsService {
  private[task2] var items = Map[String, Int](
    "item1" -> 40
  )

  def addItem(itemName: String, count: Int): Int = {
    if(items.contains(itemName))
      items += (itemName -> (items(itemName) + count))
    else
      items += (itemName -> count)

    items(itemName)
  }

  def getAvailableItems(): Map[String, Int] = items
}
