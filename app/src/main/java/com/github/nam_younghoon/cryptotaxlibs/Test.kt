package com.github.nam_younghoon.cryptotaxlibs

import com.github.nam_younghoon.testmodule.TestClass

fun main () {
    val aes128Util = AES128Util()
    val encryptedCI = aes128Util.encrypt("960523-1")
    println("Encrypted CI Value :: $encryptedCI")

    val decryptedCI = aes128Util.decrypt(encryptedCI)
    println("Decrypted encryptedCI :: $decryptedCI")


}