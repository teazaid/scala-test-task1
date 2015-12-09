package org.test.task2.service

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

/**
 * Created by Alexander on 08.12.2015.
 */
class OrderServiceTest extends FlatSpec with Matchers with BeforeAndAfterEach {


  val service = new OrderService(new ItemsService)

  it should "confirm order" in {
    val item = "item1"
    val validCount = 3
    val result = service.order(item, validCount)

    result.code should be (0)
    result.message should be (OrderSupport.confirmationMessage)
    service.itemsService.items.get(item) should be {Some(37)}
  }

  it should "return validation error" in {
    val item = "item1"
    val invalidCount = 41
    val result = service.order(item, invalidCount)

    result.code should be (-1)
    result.message should be (OrderSupport.errorMessage)
  }
}
