package org.test.task2.service

import org.test.task2.model.User

/**
 * Created by Alexander on 08.12.2015.
 */
class Session {
  private var _currentUser: Option[User] = None

  def currentUser: Option[User] = _currentUser

  def currentUser_=(userOpt: Option[User]): Unit = _currentUser = userOpt
}
