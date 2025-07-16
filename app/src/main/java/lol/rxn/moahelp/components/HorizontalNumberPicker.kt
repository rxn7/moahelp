package lol.rxn.moahelp.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lol.rxn.moahelp.R
import kotlin.math.max
import kotlin.math.min

enum class StepMode { INCREMENT, MULTIPLY }

@Composable
fun HorizontalNumberPicker(
    height: Dp = 45.dp,
    min: Float = 0f,
    max: Float = 100f,
    mode: StepMode = StepMode.INCREMENT,
    step: Float = 1f,
    textDecimalPlaces: Int = 0,
    default: Float = min,
    prefix: String = "",
    suffix: String = "",
    onValueChange: (Float) -> Unit = {}
) {
    var value by remember { mutableFloatStateOf(default) }

    Row {
        NumberPickerButton(
            size = height,
            drawable = R.drawable.ic_minus,
            enabled = value > min,
            onClick = {
                value = when(mode) {
                    StepMode.INCREMENT -> max(value - step, min)
                    StepMode.MULTIPLY -> max(value / step, min)
                }

                onValueChange(value)
            }
        )

        val format = "%s%." + textDecimalPlaces + "f%s"

        Text(
            text = format.format(prefix, value, suffix),
            fontSize = (height.value / 2).sp,
            modifier = Modifier
                .padding(10.dp)
                .height(IntrinsicSize.Max)
                .align(Alignment.CenterVertically)
        )

        NumberPickerButton(
            size = height,
            drawable = R.drawable.ic_plus,
            enabled = value < max,
            onClick = {
                value = when(mode) {
                    StepMode.INCREMENT -> min(value + step, max)
                    StepMode.MULTIPLY -> min(value * step, max)
                }

                onValueChange(value)
            }
        )
    }
}