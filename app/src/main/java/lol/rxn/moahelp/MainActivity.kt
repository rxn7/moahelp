package lol.rxn.moahelp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import lol.rxn.moahelp.screens.CalculationScreen
import lol.rxn.moahelp.ui.theme.MoahelpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MoahelpTheme {
                CalculationScreen()
            }
        }
    }
}