package com.codingnight.parallax.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.codingnight.parallax.ui.theme.ParallaxTheme

val darkBlue = Color(18, 32, 47, 255);

@Preview
@Composable
fun Parallax() {
    ParallaxTheme {
        Surface(color = darkBlue) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(locations) { location ->
                    LocationListItem(
                        location.imageUrl,
                        location.name,
                        location.place
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLocationListItem() {
    val location = locations.get(0)
    LocationListItem(
        location.imageUrl,
        location.name,
        location.place
    )
}

@Composable
fun LocationListItem(
    imageUrl: String,
    name: String,
    place: String,
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
                modifier = Modifier.matchParentSize()
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
