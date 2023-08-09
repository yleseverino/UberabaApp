package com.example.uberabaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.uberabaapp.data.Categorie
import com.example.uberabaapp.ui.NavigationItemContent
import com.example.uberabaapp.ui.UberabaApp
import com.example.uberabaapp.ui.theme.UberabaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UberabaAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UberabaApp("Android")
                }
            }
        }
    }
}




@Composable
fun UberabaBottomNavigationBar(
    currentTab: Categorie,
    onTabPressed: ((Categorie) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem.categorie,
                onClick = { onTabPressed(navItem.categorie) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UberabaAppTheme {
        UberabaApp("Android")
    }
}