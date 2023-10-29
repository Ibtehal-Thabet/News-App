package com.example.newsapp.ui.settings

import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Locale

class SettingViewModel : ViewModel() {

    private val _enableDarkTheme = MutableLiveData<Boolean>()
    val enableDarkTheme = _enableDarkTheme


    fun checkTheme(resources: Resources?) {
        _enableDarkTheme.postValue(resources?.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK != Configuration.UI_MODE_NIGHT_NO)
    }

    fun changeTheme(resources: Resources?) {
        val mode = if ((resources?.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_NO
        ) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    fun changeLanguage(language: String, resources: Resources?) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = resources?.configuration
        configuration?.setLocale(locale)
        configuration?.setLayoutDirection(locale)
        resources?.updateConfiguration(configuration, resources.displayMetrics)
    }
}