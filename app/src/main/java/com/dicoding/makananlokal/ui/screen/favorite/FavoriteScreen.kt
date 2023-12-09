package com.dicoding.makananlokal.ui.screen.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.makananlokal.R
import com.dicoding.makananlokal.di.Injection
import com.dicoding.makananlokal.ui.ViewModelFactory
import com.dicoding.makananlokal.ui.common.UiState
import com.dicoding.makananlokal.ui.components.RateItem

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderRewards()
            }
            is UiState.Success -> {
                FavoriteContent(
                    uiState.data,
                    onFoodStarsChanged = { foodId, stars ->
                        viewModel.updateOrderReward(foodId, stars)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteContent(
    state: FavoriteState,
    onFoodStarsChanged: (id: Long, stars: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.menu_favorite),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            items(state.orderReward, key = { it.food.id }) { item ->
                RateItem(
                    foodId = item.food.id,
                    image = item.food.image,
                    title = item.food.title,
                    stars = item.stars,
                    onFoodStarsChanged = onFoodStarsChanged,
                )
                Divider()
            }
        }
    }
}