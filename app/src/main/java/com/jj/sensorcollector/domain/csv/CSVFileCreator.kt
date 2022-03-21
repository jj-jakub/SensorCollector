package com.jj.sensorcollector.domain.csv

interface CSVFileCreator {

    fun createCSVFile(list: List<List<String>>, fileName: String)
}