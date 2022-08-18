package com.example.weatherapp.app.featureflag

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

// This serves no purpose now but will be used when Remote Config is set up
@Singleton
class PreferenceManager @Inject constructor(
    private val app: Application
) {

    companion object {
        private const val FEATURE_FLAG_PREFS = "healthcare.nice.provider.prefs.FeatureFlag"
    }

    val featureFlagConfigPrefs: SharedPreferences by lazy {
        return@lazy app.getSharedPreferences(FEATURE_FLAG_PREFS, Context.MODE_PRIVATE)
    }
}
