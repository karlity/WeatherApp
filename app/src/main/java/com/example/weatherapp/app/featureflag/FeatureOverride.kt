package com.example.weatherapp.app.featureflag

interface FeatureOverride {
    fun isManuallyActive(featureFlag: FeatureFlag): Boolean
}
