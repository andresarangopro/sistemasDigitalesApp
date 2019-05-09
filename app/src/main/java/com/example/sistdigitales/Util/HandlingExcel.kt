package com.example.sistdigitales.Util

import android.util.Log
import au.com.bytecode.opencsv.CSVWriter
import java.io.File

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter

class HandlingExcel {


    fun writeToExcelFile(filepath: String) {
       var baseDir: String = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
       var fileName:String  = "AnalysisData.csv"
       var filePath:String  = baseDir + File.separator + fileName
       var f: File = File(filePath)
       var writer: CSVWriter

        // File exist
        if(f.exists()&&!f.isDirectory())
        {
            var mFileWriter =  FileWriter(filePath, true);
            writer = CSVWriter(mFileWriter);
        }
        else
        {
            writer = CSVWriter(FileWriter(filePath));
        }


       var data= "Ship Name,"+ "Scientist Name,"+ "...,"+"asfsdf"

        writer.writeNext(data);

        writer.close();
    }


}