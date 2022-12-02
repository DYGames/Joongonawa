package com.team7.joongonawa

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.String

class Utils {
    companion object {
        fun convertInputStreamToFile(ips: InputStream?): File {
            val tempFile = File.createTempFile(String.valueOf(ips.hashCode()), ".tmp")
            tempFile.deleteOnExit()
            copyInputStreamToFile(ips!!, tempFile)
            return tempFile
        }

        private fun copyInputStreamToFile(inputStream: InputStream, file: File) {
            FileOutputStream(file).use { outputStream ->
                var read: Int
                val bytes = ByteArray(1024)
                while (inputStream.read(bytes).also { read = it } != -1) {
                    outputStream.write(bytes, 0, read)
                }
            }
        }
    }
}