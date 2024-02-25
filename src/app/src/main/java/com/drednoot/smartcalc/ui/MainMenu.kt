package com.drednoot.smartcalc.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.drednoot.smartcalc.R
import com.drednoot.smartcalc.ui.theme.SmartcalcTheme

internal data class MenuButton(
    val text: String,
    val onClick: () -> Unit
)
//@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun MainMenu(navController: NavHostController) {
    SmartcalcTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainMenuButtons(listOf(
                MenuButton(stringResource(R.string.calculator_btn_text)) { navController.navigate(NavigationTarget.CALCULATOR) },
//                MenuButton("asdf") {},
            ))
        }
    }
}

@Composable
internal fun MainMenuButtons(buttons: List<MenuButton>) {
    Column(
        Modifier
            .width(IntrinsicSize.Max)
            .padding(40.dp, 0.dp)
    ) {
        buttons.forEach {
           Button(
               modifier = Modifier.fillMaxWidth(),
               onClick = it.onClick,
           ) {
               Text(it.text)
           }
        }
    }
}
