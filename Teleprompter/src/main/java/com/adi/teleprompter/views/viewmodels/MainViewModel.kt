package com.adi.teleprompter.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adi.teleprompter.TeleprompterApplication
import com.adi.teleprompter.db.TextFileDatabase
import com.adi.teleprompter.model.TextFile
import javax.inject.Inject

class MainViewModel : ViewModel() {
    @JvmField
    @Inject
    var textFileDatabase: TextFileDatabase? = null
    val itemList: LiveData<List<TextFile>>
        get() = textFileDatabase!!.textFileContentDao().listOfTextFiles

    init {
        TeleprompterApplication.getAppComponent().inject(this)
    }
}