package com.dicoding.makananlokal.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.makananlokal.R
import com.dicoding.makananlokal.di.Injection
import com.dicoding.makananlokal.ui.ViewModelFactory
import com.dicoding.makananlokal.ui.common.UiState
import com.dicoding.makananlokal.ui.components.RatingButton
import com.dicoding.makananlokal.ui.components.StarsCounter

@Composable
fun DetailScreen(
    foodId: Long,
    viewModel: DetailFoodViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToFavorite: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFoodById(foodId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.food.image,
                    data.food.title,
                    data.food.description,
                    data.stars,
                    onAddToFavorite = { stars ->
                        viewModel.addToFavorite(data.food, stars)
                        navigateToFavorite()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    descriptionResId: Int,
    stars : Int,
    onAddToFavorite: (stars: Int) -> Unit,
    modifier: Modifier = Modifier,

) {
    var foodStars by rememberSaveable { mutableStateOf(stars) }
    val description = stringResource(descriptionResId)
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )

            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(4.dp).background(LightGray))
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            StarsCounter(
                1,
                foodStars,
                onFoodIncreased = { foodStars++ },
                onFoodDecreased = { if (foodStars > 0) foodStars-- },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
            )
            RatingButton(
                text = stringResource(R.string.addToFav),
                enabled = foodStars > 0,
                onClick = {
                    onAddToFavorite(foodStars)
                }
            )
        }
    }
}