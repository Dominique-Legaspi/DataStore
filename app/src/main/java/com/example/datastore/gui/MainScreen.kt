package com.example.datastore.gui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.datastore.R

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    myAppViewModel: MyAppViewModel = viewModel(factory = MyAppViewModel.Factory)
) {
    val savedUserName by myAppViewModel.uiState.collectAsState()
    var userInput by remember {
        mutableStateOf("")
    }

    val savedUserEmail by myAppViewModel.uiState.collectAsState()
    var userEmail by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Name: ${savedUserName.userName}",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 12.dp)
            )
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text(stringResource(R.string.username)) },
                modifier = Modifier.padding(vertical = 12.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Email: ${savedUserEmail.userEmail}",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 12.dp)
            )
            TextField(
                value = userEmail,
                onValueChange = { userEmail = it },
                label = { Text(stringResource(R.string.email)) },
                modifier = Modifier.padding(vertical = 12.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            // Save button
            Button(onClick = { myAppViewModel.saveUserNameEmail(userInput, userEmail) }) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}
