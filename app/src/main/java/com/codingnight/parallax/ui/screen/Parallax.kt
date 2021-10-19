package com.codingnight.parallax.ui.screen

import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.compose.rememberImagePainter
import com.codingnight.parallax.ui.theme.ParallaxTheme
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val darkBlue = Color(18, 32, 47, 255)

@Composable
fun ParallaxScreen(window: Window) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    ParallaxTheme {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = MaterialTheme.colors.isLight
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
        }
        ProvideWindowInsets {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Parallax Scrolling Demo")
                        },
                        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.95f),
                        contentPadding = rememberInsetsPaddingValues(
                            LocalWindowInsets.current.statusBars,
                            applyBottom = false,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            ) { contentPadding ->
                Surface(color = darkBlue) {
                    LocationList(contentPadding)
                }
            }
        }
    }
}

@Composable
fun LocationList(contentPadding: PaddingValues) {
    val lazyListState: LazyListState = rememberLazyListState()
    val offsetSpeed = 0.8f
    var offset by remember {
        mutableStateOf(0f)
    }
    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.y
            val layoutInfo = lazyListState.layoutInfo
            if(lazyListState.firstVisibleItemIndex == 0) {
                if (lazyListState.firstVisibleItemScrollOffset == 0) {
                    return Offset.Zero
                }
            }
            if(layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1) {
                return Offset.Zero
            }
            offset += delta * offsetSpeed
            if (offset > 1500f) {
                offset = 1500f
            }
            if (offset < -1500f) {
                offset = -1500f
            }
            return Offset.Zero
        }
    }
    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier.fillMaxSize()
            .nestedScroll(nestedScrollConnection),
        state = lazyListState
    ) {
        items(locations) { location ->
            LocationListItem(
                location.imageUrl,
                location.name,
                location.place,
                offset
            )
        }
    }
}

@Composable
fun LocationListItem(
    imageUrl: String,
    name: String,
    place: String,
    offset: Float,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .aspectRatio(ratio = 1.8f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box{
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.matchParentSize(),
                alignment = BiasAlignment(0f, offset / 1500f)
            )
            GradientForground(name, place)
        }
    }
}

@Composable
fun GradientForground(name: String, place: String) {
    val gradient = Brush.verticalGradient(
        listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = gradient
            ),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)) {
            Column(modifier = Modifier) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
                Text(
                    text = place,
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.White
                )
            }
        }
    }
}

class Location(
    val name: String, val place: String, val imageUrl: String
)

val urlPrefix = "https://flutter.dev/docs/cookbook/img-files/effects/parallax"
val locations = arrayOf(
    Location(
        name = "Mount Rushmore",
        place = "U.S.A",
        imageUrl = "$urlPrefix/01-mount-rushmore.jpg",
    ),
    Location(
        name = "Gardens By The Bay",
        place = "Singapore",
        imageUrl = "$urlPrefix/02-singapore.jpg",
    ),
    Location(
        name = "Machu Picchu",
        place = "Peru",
        imageUrl = "$urlPrefix/03-machu-picchu.jpg",
    ),
    Location(
        name = "Vitznau",
        place = "Switzerland",
        imageUrl = "$urlPrefix/04-vitznau.jpg",
    ),
    Location(
        name = "Bali",
        place = "Indonesia",
        imageUrl = "$urlPrefix/05-bali.jpg",
    ),
    Location(
        name = "Mexico City",
        place = "Mexico",
        imageUrl = "$urlPrefix/06-mexico-city.jpg",
    ),
    Location(
        name = "Cairo",
        place = "Egypt",
        imageUrl = "$urlPrefix/07-cairo.jpg",
    )
)
