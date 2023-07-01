package authorization.domain.usecase

import authorization.domain.repository.AppCloseRepository

class AddToCloseListUseCase(private val appClose: AppCloseRepository) {
     operator fun invoke(onClose:()->Unit){
         appClose.addToCloseList { onClose() }
     }
}