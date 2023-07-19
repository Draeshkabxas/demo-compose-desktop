package license.domain.repository

import kotlinx.coroutines.flow.Flow
import license.domain.model.License
import utils.Resource

interface LicenseRepository {
    fun getLicense(filePath:String): Flow<License>
}