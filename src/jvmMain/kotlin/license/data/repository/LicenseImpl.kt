package license.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import license.domain.model.License
import license.domain.repository.LicenseRepository
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

class LicenseImpl : LicenseRepository {
    private val encryptionKeyGenerator = readEncryptKeyFromFile("encrypted.key.enc")
    private fun readEncryptKeyFromFile(filePath: String): SecretKeySpec {
        val file = File(filePath)
        val inputStream = FileInputStream(file)
        val keyBytes = inputStream.readBytes()
        inputStream.close()

        return SecretKeySpec(keyBytes, "AES")
    }
    private fun readKeysFromFile(filePath: String, encryptionKey: Key = encryptionKeyGenerator): List<String> {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, encryptionKey)

        val encryptedKeys = Files.readAllBytes(Paths.get(filePath))
        val decryptedKeys = cipher.doFinal(encryptedKeys)

        return String(decryptedKeys, StandardCharsets.UTF_8).split(",")
    }

    override fun getLicense(filePath: String): Flow<License> = flow{
        println("passed user key work")
        println("FilePath $filePath")
        val userKeys = readKeysFromFile(filePath)
        println("passed user key")
        val licenseAppKeys = readKeysFromFile("keys.enc")
        println("passed app keys")
        val selectedLicense :License= if (userKeys.contains(licenseAppKeys[1])) {
            License.Lifetime
        } else if (userKeys.contains(licenseAppKeys[0])) {
            License.TenDay
        } else {
            License.None
        }
        emit(selectedLicense)
    }.catch {
        println(it.localizedMessage)
        License.None }

}