package com.example.weatherapp.app.featureflag

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureFlagManager @Inject constructor(
    private val featureOverride: FeatureOverride
) {

    fun isInFeature(featureFlag: FeatureFlag): Boolean {
        featureFlag.dependencies.forEach { dependency ->
            if (!isInFeature(dependency)) {
                Timber.w("Not flagged in feature because dependencies are not active")
                return false
            }
        }

        // Check to see if manually activated in debug drawer
        // Only activation method as of 7/20/22 so if this isn't true, then its not activated
        if (featureOverride.isManuallyActive(featureFlag)) {
            Timber.d("Forcing activation of feature: ${featureFlag.id}")
            return true
        }

        // when remote config is set up, add remote check here

        return false
    }
}
