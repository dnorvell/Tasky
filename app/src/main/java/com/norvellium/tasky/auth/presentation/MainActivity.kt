package com.norvellium.tasky.auth.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.norvellium.tasky.R
import com.norvellium.tasky.core.application.TaskyApplication
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isAuthenticated.value
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenCreated {
            viewModel.isAuthenticated.collect {
                authenticated ->
                val controller = findNavController(R.id.fragment_container)
                controller.popBackStack()
                if(authenticated) controller.navigate(R.id.nav_agenda) else controller.navigate(R.id.nav_login)
            }
        }
    }
}