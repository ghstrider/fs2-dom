/*
 * Copyright 2022 Arman Bilge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fs2.dom

import cats.effect.kernel.Async
import org.scalajs.dom

import scala.scalajs.js

abstract class Navigator[F[_]] private {

  def clipboard: Clipboard[F]

  def locks: LockManager[F]

}

object Navigator {

  private[dom] def apply[F[_]](navigator: dom.Navigator)(implicit F: Async[F]): Navigator[F] =
    new Navigator[F] {

      def clipboard = Clipboard(navigator.clipboard)

      def locks = LockManager(
        navigator.asInstanceOf[js.Dynamic].locks.asInstanceOf[facade.LockManager]
      )

    }

}