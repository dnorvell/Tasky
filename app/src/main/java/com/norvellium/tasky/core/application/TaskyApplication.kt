package com.norvellium.tasky.core.application

import android.app.Application
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Singleton

@HiltAndroidApp
class TaskyApplication : Application() {

}