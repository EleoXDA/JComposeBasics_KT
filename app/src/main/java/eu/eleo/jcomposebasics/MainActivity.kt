package eu.eleo.jcomposebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.eleo.jcomposebasics.ui.theme.JComposeBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JComposeBasicsTheme {
                    JCB()
                }
            }
        }
    }

@Composable
fun JCB() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Jet Compose Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ){
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    JComposeBasicsTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items =  names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String) {

    val expanded = remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row (modifier = Modifier.padding(24.dp)) {
            Column (modifier = Modifier.weight(1f).padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                Text(text = name, style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold)
                )
            }
            OutlinedButton(
                onClick = { expanded.value = !expanded.value },
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }

    }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JComposeBasicsTheme {
        JCB()
    }
}