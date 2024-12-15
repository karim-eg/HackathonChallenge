package com.karim.hackathon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hackathonchallenge.composeapp.generated.resources.Res
import hackathonchallenge.composeapp.generated.resources.gift
import hackathonchallenge.composeapp.generated.resources.google_img
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    val sharedText = remember { mutableStateOf("") }
    var currentPage by remember { mutableStateOf("home") }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            when (currentPage) {
                "home" -> HomeScreen(onSearchClick = { currentPage = "search" }, sharedText)
                "search" -> SearchScreen(onBackClick = { currentPage = "home" }, sharedText)
            }
        }
    }
}

@Composable
fun ButtonStyle(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    var isHovered by remember { mutableStateOf(false) }
    var isClicked by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                brush = SolidColor(Color.DarkGray),
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = if (isHovered)
                    if (isClicked)
                        Color(0xFFB1B1B9)
                    else
                        Color(0xFFDCDCDC)
                else
                    Color(0xFFECECEC),
                shape = RoundedCornerShape(4.dp)
            )
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter -> isHovered = true
                            PointerEventType.Exit -> isHovered = false
                            PointerEventType.Press -> {
                                isClicked = true
                                onClick()
                            }
                            PointerEventType.Release -> isClicked = false
                        }
                    }
                }
            }
            .padding(horizontal = 5.dp, vertical = 4.dp)
    ) {
        BasicText(
            text = text,
            style = TextStyle(
                color = Color.Black,
                fontSize = 14.sp
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun HomeScreen(onSearchClick: () -> Unit, sharedText: MutableState<String>) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(Res.drawable.google_img),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(120.dp).padding(bottom = 1.dp)
            )

            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "Search the web using Google!"
            )

            BasicTextField(
                value = sharedText.value,
                onValueChange = { sharedText.value = it },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .width(300.dp)
                    .border(
                        width = 1.dp,
                        brush = SolidColor(Color.DarkGray),
                        shape = RoundedCornerShape(4.dp)
                    )
            )

            Row {
                ButtonStyle(
                    text = "Google Search!",
                    modifier = Modifier
                        .padding(start = 0.dp, top = 6.dp, bottom = 0.dp, end = 2.dp)
                        .wrapContentSize(),
                    onClick = onSearchClick
                )

                ButtonStyle(
                    text = "I'm feeling lucky",
                    modifier = Modifier
                        .padding(start = 0.dp, top = 6.dp, bottom = 0.dp, end = 0.dp)
                        .wrapContentSize(),
                    onClick = onSearchClick
                )
            }

            Text(
                text = "Copyright ©1999 Google Inc.",
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@Composable
fun SearchScreen(onBackClick: () -> Unit, sharedText: MutableState<String>) {
    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize()
            .background(Color(0xFFF1F1F1)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Google",
            style = TextStyle(
                color = Color(0xFF4285F4),
                fontSize = 36.sp,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
                .background(Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = sharedText.value,
                onValueChange = { sharedText.value = it },
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .background(Color.White)
            )

            Box(
                modifier = Modifier.clickable {

                }
                    .background(Color(0xFF4285F4), RoundedCornerShape(4.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Search",
                style = TextStyle(color = Color.Red, fontSize = 18.sp)
            )

            ButtonStyle(
                text = "Back to homepage",
                modifier = Modifier
                    .padding(start = 8.dp, top = 0.dp, bottom = 0.dp, end = 0.dp)
                    .wrapContentSize(),
                onClick = onBackClick
            )
        }


        SearchResultItem(sharedText)
    }
}


@Composable
fun SearchResultItem(searchText: MutableState<String>) {
    val uriHandler = LocalUriHandler.current

    repeat(4) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = searchText.value,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Blue
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            val url = "www.${searchText.value}.com/"
            val annotatedLink = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color(0xFF1A0DAB))) {
                    append(url)
                }
            }

            ClickableText(
                text = annotatedLink,
                onClick = {
                    uriHandler.openUri(url)
                },
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0xFF1A0DAB)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
        }
    }
}
