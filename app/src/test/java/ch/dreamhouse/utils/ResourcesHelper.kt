package ch.dreamhouse.utils

import java.io.File

class ResourcesHelper {
    companion object {
        /**
         * Helper function which will load JSON data from the path specified
         * The file must be under test/resources
         *
         * @param path : Path of JSON file
         * @return json : JSON from file at given path
         */
        fun loadJson(path: String): String {
            val uri = this.javaClass.classLoader.getResource(path)
            val file = File(uri.path)
            return String(file.readBytes())
        }
    }
}