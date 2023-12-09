package com.dicoding.makananlokal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.makananlokal.R
import com.dicoding.makananlokal.ui.theme.MakananLokalTheme
import com.dicoding.makananlokal.ui.theme.Shapes

@Composable
fun FoodItem(
    image: Int,
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp)
                .clip(Shapes.medium)
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FoodItemPreview() {
    MakananLokalTheme {
        FoodItem(R.drawable.bakso, "Bakso")
    }
}