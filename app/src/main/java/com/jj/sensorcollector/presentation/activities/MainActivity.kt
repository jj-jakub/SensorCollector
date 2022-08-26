package com.jj.sensorcollector.presentation.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.jj.core.framework.presentation.navigation.MainViewRoot
import com.jj.domain.base.initializer.ProgramInitializer
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val programInitializer: ProgramInitializer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainViewRoot()
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        runBlocking {
            programInitializer.initialize()
        }
    }
}
