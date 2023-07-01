package authorization.domain.usecase

import authorization.domain.repository.AppCloseRepository

class CloseApplication(private val appClose:AppCloseRepository) {
     operator fun invoke(){
         appClose.close()
     }
}