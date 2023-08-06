package license.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import license.domain.model.License
import license.domain.repository.LicenseRepository
import utils.filePrintLn
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class LicenseImpl : LicenseRepository {
    private val encryptionKeyGenerator = try {
        readEncryptKeyFromFile("keys/encrypted.key.enc")
    } catch (e:Exception){
        filePrintLn(e.localizedMessage)
    SecretKeySpec("".toByteArray(),"AES")
    }
    private fun readEncryptKeyFromFile(filePath: String): SecretKeySpec {
        val file = getFileFromResource(filePath)
        return SecretKeySpec(file?.readBytes(), "AES")
    }


    private fun getFileFromResource(fileName: String): File? {
        return try {
            val inputStream: InputStream? = object {}.javaClass.getResourceAsStream("/$fileName")
            if (inputStream == null) {
                println("File '$fileName' not found in resources.")
                null
            } else {
                val tempFile = File.createTempFile(fileName, "")
                tempFile.deleteOnExit()
                tempFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                tempFile
            }
        } catch (e: Exception) {
            println("An error occurred while obtaining the file: ${e.message}")
            null
        }
    }


    private fun String.fromResource(): File? {
        val path = this
        return try {
            val resource: URL? = object {}.javaClass.getResource("/$path")
            if (resource == null) {
                println("File '$path' not found in resources.")
                null
            } else {
                filePrintLn("File $path found")
                File(resource.file)
            }
        } catch (e: Exception) {
            println("An error occurred while obtaining the file: ${e.message}")
            null
        }
    }

    private fun readKeysFromFile(filePath: String, encryptionKey: Key = encryptionKeyGenerator
    ): List<String> {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, encryptionKey)

        val encryptedKeys = Files.readAllBytes(Paths.get(filePath))
        val decryptedKeys = cipher.doFinal(encryptedKeys)

        return String(decryptedKeys, StandardCharsets.UTF_8).split(",")
    }
    private fun readKeysFromResource(filePath: String, encryptionKey: Key = encryptionKeyGenerator
    ): List<String> {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, encryptionKey)

        val encryptedKeys =getFileFromResource(filePath)?.readBytes()
        val decryptedKeys = cipher.doFinal(encryptedKeys)

        return String(decryptedKeys, StandardCharsets.UTF_8).split(",")
    }
    override fun getLicense(filePath: String): Flow<License> = flow {
        try {
            val userKeys = readKeysFromFile(filePath)
            val licenseAppKeys = readKeysFromResource("keys/keys.enc")
            val selectedLicense: License = if (userKeys.contains(licenseAppKeys[1])) {
                License.Lifetime
            } else if (userKeys.contains(licenseAppKeys[0])) {
                License.TenDay
            } else {
                License.None
            }
            emit(selectedLicense)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.catch {
        println(it.localizedMessage)
        License.None
    }

}