package com.edon.vigenerecipher.classes

object CryptEngine {
    private var alphabets = "abcdefghijklmnopqrstuvwxyz"

    //find mod26
    fun modulo26(num: Int): Int {
        var mod = num % 26
        if (mod < 0){
            mod += 26
        }
        return mod
    }

    fun encrypt(message: String, shift: Int): String{
        var cypherText: String = ""
        message.lowercase().forEach { messageChar ->
            var cypherTextIndex: Int = 0
            if(messageChar == ' ')
                cypherText += " "
            else {
                alphabets.forEachIndexed{alphIndex, alph ->
                    if (alph == messageChar) {
                        cypherTextIndex = modulo26(alphIndex + shift)
                    }
                }
                cypherText += alphabets[cypherTextIndex]
            }
        }
        return cypherText
    }

    fun decrypt(ciphertext: String, shift: Int): String{
        var message: String = ""
        ciphertext.lowercase().forEach { cipherTextChar ->
            var messageIndex = 0
            if(cipherTextChar == ' ')
                message += " "
            else {
                alphabets.forEachIndexed { alphIndex, alph ->
                    if(alph == cipherTextChar){
                        messageIndex = modulo26( alphIndex - shift)
                    }
                }
                message += alphabets[messageIndex]
            }
        }
        return message
    }

    fun findShiftKey(cipherText: String, message: String){
        var shiftNumber:Int = 0
        var cipherTextCharKey: Int = 0
        var messageCharKey: Int = 0
        //find index of first character in the ciphertext and message since
        // key is consistent in all their characters,
        // there's no need to test for all letter of the ciphertext and message
        alphabets.forEachIndexed{index, char ->
            if(cipherText[0] == char){
                cipherTextCharKey = index
            }
            if(message[0] == char){
                messageCharKey = index
            }
        }
        shiftNumber = cipherTextCharKey - messageCharKey
        println("Shift number: $shiftNumber")
        alphabets.forEachIndexed { index, char ->
            //found index at which keyshift is
            if(index == shiftNumber)
                println("Shift letter: $char")
        }
    }

    fun vigenereCypher(message: String, key: String, op: Int): String{
        if(key.length > message.length){
            return "Error! Key is longer than Message!"
        }

        var cipherText: String = ""

        //extend length of key to fit message
        var newKey = ""
        var i = 0; var j = 0 // use this to track index of key character

        //find new key whose length = message.length
        message.forEachIndexed{index, _ ->
            if(index > (key.length - 1)){
                i = 0
                if(j > key.length - 1)
                    j = 0
            }
            //step through key chars n assign each to the newKey
            i = j; j++
            newKey += key[i]
        }

        //find ciphertex
        if(op == 0){
            message.forEachIndexed{index, mChar ->
                //since message and newKey have same length, they also have
                // same indexes
                if(mChar == ' ')
                    cipherText += " "
                else
                    cipherText += encSingleChars(mChar, newKey[index])
            }
        } else {
            message.forEachIndexed{index, mChar ->
                //since message and newKey have same length, they also have
                // same indexes
                if(mChar == ' ')
                    cipherText += " "
                else
                    cipherText += decSingleChars(mChar, newKey[index])
            }
        }

        return cipherText
    }

    fun encSingleChars(messageChar: Char, keyChar: Char): Char{
        var cipherTextIndex = 0
        var messageCharIndex = 0
        var keyCharIndex = 0
        alphabets.forEachIndexed { index, char ->
            //find index of message char
            if(messageChar == char) {
                messageCharIndex = index
            }
            //find index of key/shift char
            if(keyChar == char) {
                keyCharIndex = index
            }
        }
        //find modulo 26 of the sum of the two numbers
        cipherTextIndex = modulo26(messageCharIndex + keyCharIndex)
        return alphabets[cipherTextIndex] //return char at that result
    }

    fun decSingleChars(messageChar: Char, keyChar: Char): Char{
        var cipherTextIndex = 0
        var messageCharIndex = 0
        var keyCharIndex = 0
        alphabets.forEachIndexed { index, char ->
            //find index of message char
            if(messageChar == char) {
                messageCharIndex = index
            }
            //find index of key/shift char
            if(keyChar == char) {
                keyCharIndex = index
            }
        }
        //find modulo 26 of the sum of the two numbers
        cipherTextIndex = modulo26(messageCharIndex - keyCharIndex)
        return alphabets[cipherTextIndex] //return char at that result
    }
}

