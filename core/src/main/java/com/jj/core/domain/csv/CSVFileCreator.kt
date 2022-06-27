package com.jj.core.domain.csv

interface CSVFileCreator {

    fun createCSVFile(list: List<List<String>>, fileName: String)
}