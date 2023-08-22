package com.example.uberabaapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uberabaapp.R
import com.example.uberabaapp.UberabaBottomNavigationBar
import com.example.uberabaapp.data.Categorie
import com.example.uberabaapp.data.Place
import com.example.uberabaapp.data.local.LocalPlacesDataProvider
import com.example.uberabaapp.ui.theme.UberabaAppTheme

data class NavigationItemContent(
    val categorie: Categorie,
    val icon: ImageVector,
    val text: String
)

enum class UberabaScreen(@StringRes val title: Int) {
    Home(title = R.string.home_screen),
    Details(title = R.string.details_screen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UberabaApp(
    name: String, modifier: Modifier = Modifier, viewModel: UberabaViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    val uberabaState = viewModel.uiState.collectAsState().value

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = UberabaScreen.valueOf(
        backStackEntry?.destination?.route ?: UberabaScreen.Home.name
    )

    val navigationItemContentList = listOf<NavigationItemContent>(
        NavigationItemContent(
            categorie = Categorie.Home,
            icon = Icons.Default.House,
            text = stringResource(id = R.string.home)
        ),
        NavigationItemContent(
            categorie = Categorie.Fun,
            icon = Icons.Default.Celebration,
            text = stringResource(id = R.string.`fun`)
        ),
        NavigationItemContent(
            categorie = Categorie.Eat,
            icon = Icons.Default.Restaurant,
            text = stringResource(id = R.string.eat)
        ),
        NavigationItemContent(
            categorie = Categorie.Study,
            icon = Icons.Default.School,
            text = stringResource(id = R.string.Study)
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = stringResource(id = currentScreen.title)) })
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = UberabaScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = UberabaScreen.Home.name) {
                UberabaHomeScreen(
                    currentCategoryItems = uberabaState.currentCategoryItems,
                    currentCategory = uberabaState.currentCategory,
                    onSelectCategorie = { category: Categorie -> viewModel.updateCurrentCategory(category) },
                    navigationComponents = navigationItemContentList,
                    onSelectPLace = {place ->
                        navController.navigate(UberabaScreen.Details.name)
                    }
                )
            }
            composable(route = UberabaScreen.Details.name) {
                UberabaDetailsScreen(id = 1)
            }
        }
    }
}

@Composable
fun UberabaHomeScreen(
    currentCategoryItems: List<Place>,
    currentCategory: Categorie,
    onSelectCategorie: (Categorie) -> Unit,
    navigationComponents: List<NavigationItemContent>,
    onSelectPLace: (place: Place) -> Unit
) {
    Column {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                16.dp
            )
        ) {
            items(currentCategoryItems, key = { place -> place.id }) {
                UberabaPlaceCard(
                    it,
                    onSelectPLace = onSelectPLace
                )
            }
        }

        UberabaBottomNavigationBar(
            currentTab = currentCategory,
            onSelectCategorie,
            navigationItemContentList = navigationComponents
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UberabaPlaceCard(
    place: Place,
    modifier: Modifier = Modifier,
    onSelectPLace: (place: Place) -> Unit
) {
    Surface(onClick = {
        onSelectPLace(place)
    }) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = place.image),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        Column(
                            modifier = Modifier
                                .weight(2f)
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                text = stringResource(id = place.title),
                                style = MaterialTheme.typography.labelMedium
                            )
                            CategorieCaption(place.categorie)

                        }
                    }
                    Icon(
                        imageVector = Icons.Default.StarBorder,
                        contentDescription = "Mark as liked"
                    )
                }
                Text(
                    text = stringResource(id = place.title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 6.dp, bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = place.description),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun CategorieCaption(categorie: Categorie) {
    val categorieInfo = getCategorieInfo(categorie)
    Row {
        Icon(
            imageVector = categorieInfo.icon,
            contentDescription = categorieInfo.text,
            modifier = Modifier
                .size(16.dp)
                .padding(end = 2.dp)
        )
        Text(text = categorieInfo.text, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun getCategorieInfo(categorie: Categorie): NavigationItemContent {
    return when (categorie) {
        Categorie.Fun -> NavigationItemContent(
            categorie = Categorie.Fun,
            icon = Icons.Default.Celebration,
            text = stringResource(id = R.string.`fun`)
        )

        Categorie.Eat -> NavigationItemContent(
            categorie = Categorie.Eat,
            icon = Icons.Default.Restaurant,
            text = stringResource(id = R.string.eat)
        )

        Categorie.Study -> NavigationItemContent(
            categorie = Categorie.Study,
            icon = Icons.Default.School,
            text = stringResource(id = R.string.Study)
        )

        else -> NavigationItemContent(
            categorie = Categorie.Home,
            icon = Icons.Default.House,
            text = stringResource(id = R.string.home)
        )
    }
}


@Preview
@Composable
fun UberabaPlaceCardPreview() {
    UberabaAppTheme {
        UberabaPlaceCard(LocalPlacesDataProvider.allPlaces.first(), onSelectPLace = {

        })
    }
}
