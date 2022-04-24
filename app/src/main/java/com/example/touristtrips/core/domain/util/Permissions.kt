package com.example.touristtrips.core.domain.util

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

@RequiresApi(Build.VERSION_CODES.M)
fun requestFineLocationPermission(activity: Activity, requestCode: Int) {
    if (activity.shouldShowRequestPermissionRationale(
            Manifest.permission.ACCESS_FINE_LOCATION)) {
        activity.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
    } else {
        activity.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
    }
}