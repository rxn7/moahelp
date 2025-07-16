package lol.rxn.moahelp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lol.rxn.moahelp.components.HorizontalNumberPicker
import lol.rxn.moahelp.components.StepMode
import lol.rxn.moahelp.utils.MoaMath

@Composable
fun CalculationScreen() {
    val defaultDistance: Int = 100
    var distanceInput by remember { mutableIntStateOf(defaultDistance) }

    val defaultMoaPrecision: Int = 8
    var moaPrecisionInput by remember { mutableIntStateOf(defaultMoaPrecision) }

    val defaultCmValue: Int = 1
    var cmInput by remember { mutableIntStateOf(defaultCmValue) }

    val defaultMoaValue: Float = 1f
    var moaInput by remember { mutableFloatStateOf(defaultMoaValue) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.displayCutout)
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalNumberSelectorWithLabel(
                label = "Precision",
                min = 2f,
                max = 8f,
                mode = StepMode.MULTIPLY,
                step = 2f,
                default = defaultMoaPrecision.toFloat(),
                prefix = "1/",
                suffix = " MOA",
                onValueChange = { value -> moaPrecisionInput = value.toInt() }
            )

            HorizontalNumberSelectorWithLabel(
                label = "Distance",
                min = 50f,
                max = 5000f,
                step = 50f,
                default = defaultDistance.toFloat(),
                mode = StepMode.INCREMENT,
                suffix = "m",
                onValueChange = { value -> distanceInput = value.toInt() }
            )
        }

        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalNumberPicker(
                min = 0f,
                max = 100f,
                step = 1f,
                default = defaultCmValue.toFloat(),
                mode = StepMode.INCREMENT,
                suffix = "cm",
                onValueChange = { value -> cmInput = value.toInt() }
            )

            val radiusToMoa: Float = MoaMath.roundToPrecision(MoaMath.radiusToMoa(cmInput.toFloat(), distanceInput), moaPrecisionInput)
            Text("%dcm @ %dm = %.3f MOA".format(cmInput, distanceInput, radiusToMoa))
        }

        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalNumberPicker(
                min = 0f,
                max = 100f,
                step = 1.0f / moaPrecisionInput,
                textDecimalPlaces = 3,
                default = defaultMoaValue,
                mode = StepMode.INCREMENT,
                suffix = " MOA",
                onValueChange = { value -> moaInput = value }
            )

            val oneMoaRadius = MoaMath.moaToRadius(1f, distanceInput)

            Text("%.3f MOA @ %dm = %.2f cm".format(moaInput, distanceInput, oneMoaRadius * moaInput))

            Text("1 MOA @ %dm = %.2fcm".format(distanceInput, oneMoaRadius))
            Text(
                "1/%s MOA @ %dm = %.2fcm".format(
                    moaPrecisionInput,
                    distanceInput,
                    oneMoaRadius / moaPrecisionInput.toFloat()
                )
            )
        }
    }
}

@Composable
private fun HorizontalNumberSelectorWithLabel(
    label: String,
    min: Float,
    max: Float,
    mode: StepMode,
    step: Float,
    textDecimalPlaces: Int = 0,
    default: Float,
    prefix: String = "",
    suffix: String = "",
    onValueChange: (Float) -> Unit = {}
) {
    Row(
    ) {
        Text(
            text = label,
            modifier = Modifier
                .padding(10.dp)
                .height(IntrinsicSize.Max)
                .align(Alignment.CenterVertically),
            fontSize = (45/2).sp,
        )

        HorizontalNumberPicker(
            min = min,
            max = max,
            mode = mode,
            step = step,
            textDecimalPlaces = textDecimalPlaces,
            default = default,
            prefix = prefix,
            suffix = suffix,
            onValueChange = onValueChange
        )
    }
}