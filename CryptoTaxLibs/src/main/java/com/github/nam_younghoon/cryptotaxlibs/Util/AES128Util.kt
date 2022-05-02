package com.github.nam_younghoon.cryptotaxlibs.Util

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AES128Util(userSecretKey: String) {
    private var userSecretKey: String = userSecretKey
    private val iv: String = "zmflqxhxortm!@#$"

    fun encrypt(text: String) : String {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val keySpec: SecretKeySpec = SecretKeySpec(userSecretKey.toByteArray(), "AES")
        val ivParameterSpec: IvParameterSpec = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec)
        val encrypted = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(encrypted)
    }

    fun decrypt(cipherText: String) : String {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val keySpec: SecretKeySpec = SecretKeySpec(userSecretKey.toByteArray(), "AES")
        val ivParameterSpec: IvParameterSpec = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec)
        val decodedBytes = Base64.getDecoder().decode(cipherText)
        val decrypted = cipher.doFinal(decodedBytes)
        return String(decrypted, Charsets.UTF_8)
    }
}