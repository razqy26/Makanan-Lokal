package com.dicoding.makananlokal.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun RateItem(
    foodId: Long,
    image: Int,
    title: String,
    stars: Int,
    onFoodStarsChanged: (id: Long, stars: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
        StarsCounter(
            foodId = foodId,
            foodStars = stars,
            onFoodIncreased = { onFoodStarsChanged(foodId, stars + 1) },
            onFoodDecreased = { onFoodStarsChanged(foodId, stars - 1) },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RateItemPreview() {
    MakananLokalTheme {
        RateItem(
            4, R.drawable.bakso, "Bakso", 0,
            onFoodStarsChanged = { foodId, count -> },
        )
    }
}
