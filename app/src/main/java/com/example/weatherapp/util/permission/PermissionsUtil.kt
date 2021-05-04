package com.example.weatherapp.util.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import io.reactivex.Single

class PermissionsUtil:
    IPermissionsUtil {

    override fun isPermissionGranted(context: Context, vararg permissionsNames: String): Single<Boolean> {

        if ((ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    )) return Single.just(false)

        return Single.create<Boolean> { emitter ->
            Dexter.withContext(context)
                .withPermissions(permissionsNames.toList())
                .withListener(object : BaseMultiplePermissionsListener() {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        super.onPermissionsChecked(report)
                        if (report.areAllPermissionsGranted()) {
                            emitter.onSuccess(true)
                        } else {
                            emitter.onSuccess(false)
                        }
                    }
                })
                .check()
        }
    }

}