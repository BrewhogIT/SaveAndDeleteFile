package com.practicum.saveanddeletefile

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.saveanddeletefile.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var fileNameList = mutableListOf<String>()
    val adapter = MainAdapter(this::delFile)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()

        binding.saveButton.setOnClickListener {
            saveData()
        }
        binding.getButton.setOnClickListener {
            getData()
        }

    }

    fun initAdapter(){
        createList()

        binding.fileRecyclerView.adapter = adapter
        binding.fileRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter.list = fileNameList
    }

    private fun getData() {
        val file= File(filesDir,Constants.FILE_TEXT_NAME)
        val data=FileInputStream(file).use {
            String((it.readBytes()))
        }

        binding.textView.text = data
        Log.d("data file",data)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveData() {
        val text=binding.editText.text.toString()
        val file= File(filesDir,Constants.FILE_TEXT_NAME)

        FileOutputStream(file).use {
            val bytes=text.toByteArray()
            it.write(bytes)
            it.flush()
            it.close()
        }

        addFileToList()
    }

    fun createList (){
        val list = filesDir.listFiles()

        if (list!=null){
            for (myFiles in list){
                if (myFiles.isFile){
                    Log.d("myList",myFiles.name)
                    fileNameList.add(myFiles.name)
                }
            }
        }
    }

    fun delFile(filePosition : Int){
        val fileName = fileNameList[filePosition]
        Toast.makeText(this, "file was deleted: "+ fileName + "lenth" + fileNameList.size + " index " + filePosition, Toast.LENGTH_SHORT).show()

        val deleteFile = File(filesDir,fileName)
        if (deleteFile.exists()){
            deleteFile.delete()
        }

        fileNameList.removeAt(filePosition)
        adapter.notifyItemRemoved(filePosition)
    }

    fun addFileToList(){
        val insertPosition = fileNameList.size

        if (!fileNameList.contains(Constants.FILE_TEXT_NAME)){
            fileNameList.add(Constants.FILE_TEXT_NAME)
            adapter.notifyItemInserted(insertPosition)
        }
    }
}