package org.test.task2.service

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

/**
 * Created by Alexander on 08.12.2015.
 */
class ItemsServiceTest extends FlatSpec with Matchers with BeforeAndAfterEach {

  override protected def beforeEach(): Unit = {
    service.items = Map()
    service.items += ("item1" -> 40)
  }


  val service =  new ItemsService

  it should "add items" in {
    val item = "item1"
    val count = 5
    val result = service.addItem(item, count)

    service.items.get(item).isEmpty should be (false)
    service.items.get(item).get should be (result)
  }

  it should "add new items" in {
    val item = "item2"
    val count = 5
    service.addItem(item, count)

    service.items.get(item).isEmpty should be (false)
    service.items.get(item).get should be (count)
  }

  it should "show the list of available items" in {
    val item = "item2"
    val count = 5
    service.addItem(item, count)

    val items = service.getAvailableItems()
    items should be (Map("item1" -> 40, "item2" -> 5))
  }

}
