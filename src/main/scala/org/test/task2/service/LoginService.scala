package org.test.task2.service

import org.test.task2.model.User
import org.test.task2.model

/**
 * Created by Alexander on 07.12.2015.
 */
sealed trait LoginSupport {
  def logIn(name: String, password: Array[Char]): Option[User]
  def logOut(): Option[User]
}

class LoginService(val session: Session) extends LoginSupport {
  import model._

  override def logIn(name: String, password: Array[Char]): Option[User] = {
    if(session.currentUser.isEmpty) {
      val user = model.users.get((name, password.mkString))
      session.currentUser = user
      user
    } else session.currentUser

  }

  override def logOut(): Option[User] = {
    session.currentUser = None
    None
  }
}
