package com.example.recipeappfetchanddisplay

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecipeScreen() {
    val rViewModel: MainViewModel = viewModel()
    val viewState = rViewModel.categories.value
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            viewState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(text = "error found: ${viewState.error}")
            }
            else -> {

                CategoryScreen(category = viewState.categories) { clickedCategory ->
                    selectedCategory = clickedCategory
                }
            }
        }

        // Show dialog if category selected
        selectedCategory?.let { category ->
            CategoryDescriptionDialog(
                category = category,
                onDismiss = { selectedCategory = null }
            )
        }
    }
}

@Composable
fun CategoryDescriptionDialog(category: Category, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = category.strCategory) },
        text = { Text(text = category.strCategoryDescription) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

// Updated CategoryScreen with click lambda
@Composable
fun CategoryScreen(
    category: List<Category>,
    onItemClick: (Category) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(category) { category ->
            CategoryItem(category = category, onClick = { onItemClick(category) })
        }
    }
}

// Updated CategoryItem to accept onClick and handle it
@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(160.dp) // fixed size, avoid fillMaxSize inside grid item
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = null,
            modifier = Modifier.size(100.dp).aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.strCategory.trim(),
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}
