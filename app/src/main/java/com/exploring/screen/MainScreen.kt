package com.exploring.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exploring.R
import com.exploring.source.model.ActivityResponse
import com.exploring.ui.component.AdapterCircularProgress
import com.exploring.ui.component.GeneralErrorInform
import org.koin.compose.koinInject

@Composable
fun MainScreen(mainViewModel: MainViewModel = koinInject()) {
    val state = mainViewModel.viewState.collectAsStateWithLifecycle().value
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    state.isLoading -> AdapterCircularProgress()
                    state.data != null -> CardActivityShow(activityResponse = state.data)
                    state.errorMessage?.isNotEmpty() == true -> GeneralErrorInform()
                }

                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        mainViewModel.requestActivity()
                    }) {
                    Text(text = stringResource(R.string.button_request_activity))
                }
            }
        }
    }
}

@Composable
fun CardActivityShow(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    activityResponse: ActivityResponse,
) {
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.label_title_activity),
                    modifier = modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
                )
                Divider()
                Text(
                    text = activityResponse.activity,
                    modifier = modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
                )
                Text(
                    text = stringResource(
                        id = R.string.label_participants,
                        activityResponse.participants.toString()
                    ),
                    modifier = modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
                )
                Text(
                    text = stringResource(id = R.string.label_activity_type, activityResponse.type),
                    modifier = modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
                )

            }
        }
    }
}
