package com.jj.core.data.csv

import android.content.Context
import android.os.Environment
import android.util.Log
import com.jj.core.domain.csv.CSVFileCreator
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter

class DefaultCSVFileCreator(
        private val context: Context
) : CSVFileCreator {

    override fun createCSVFile(list: List<List<String>>, fileName: String) {
        try {
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val file = File(dir, fileName)
            val path = file.path
            Log.d("ABAB", "Saving files to $path")
            val csvWriter = CSVWriter(FileWriter(file))
            csvWriter.writeAll(list.map { it.toTypedArray() })
            csvWriter.close()
            Log.d("ABAB", "Saved files to $path")
        } catch (e: Exception) {
            Log.e("ABAB", "Saving samples failure", e)
        }
    }
}