package hu.bme.aut.myplacescomposefirebase.ui.screen.main

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import hu.bme.aut.myplacescomposefirebase.ui.navigation.InnerNavigation
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import hu.bme.aut.myplacescomposefirebase.data.Place

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    var selectedBottomTab by remember { mutableStateOf(0) }
    var innerNavController: NavHostController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MyPlaces App") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                actions = {
                    IconButton(onClick = {
                        //
                    }) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(content = {
                NavigationBar(
                    //containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    NavigationBarItem(selected = selectedBottomTab == 0,
                        onClick = {
                            selectedBottomTab = 0
                            innerNavController.navigate(InnerNavigation.MapView.route) {
                                innerNavController.popBackStack()
                            }
                        },
                        label = {
                            Text(
                                text = "Map",
                                fontWeight = FontWeight.SemiBold,
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Map,
                                contentDescription = "Map",
                            )
                        })
                    NavigationBarItem(selected = selectedBottomTab == 1,
                        onClick = {
                            selectedBottomTab = 1
                            innerNavController.navigate(InnerNavigation.ListView.route) {
                                innerNavController.popBackStack()
                            }
                        },
                        label = {
                            Text(
                                text = "List",
                                fontWeight = FontWeight.SemiBold,
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "List",
                            )
                        })
                }
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            NavHost(
                navController = innerNavController,
                startDestination = InnerNavigation.MapView.route
            ) {
                composable(InnerNavigation.MapView.route) {
                    MapView(mainViewModel)
                }
                composable(InnerNavigation.ListView.route) {
                    ListView(mainViewModel)
                }
            }
            when (mainViewModel.mainUiState) {
                is MainUiState.Error -> {
                    Text(text = (mainViewModel.mainUiState as MainUiState.Error).error!!)
                }

                MainUiState.Init -> {}
                MainUiState.PlaceUploadSuccess -> {}
                MainUiState.UploadPlaceInProgress -> {
                    CircularProgressIndicator()
                }

                is MainUiState.PlacesRetrieved -> {}
            }
        }
    }
}

@Composable
fun MapView(
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current

    var showAdd by remember { mutableStateOf(false) }
    var clickedCoord by remember { mutableStateOf(LatLng(47.0, 19.0)) }

    var uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = true,
                zoomGesturesEnabled = true
            )
        )
    }
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL
            )
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        uiSettings = uiSettings,
        properties = mapProperties,
        onMapClick = {
            showAdd = true
            clickedCoord = it
        }
    )
    {
        val placeListState = mainViewModel.placeList().collectAsState(MainUiState.Init)

        if (placeListState.value is MainUiState.PlacesRetrieved) {
            for (place in (placeListState.value as MainUiState.PlacesRetrieved).placeList) {
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            place.place.lat,
                            place.place.lng
                        )
                    ),
                    title = "${place.place.placeTitle}"
                )
            }
        }
    }


    if (showAdd) {
        AddNewPlaceDialog(latLng = clickedCoord,
            onAddPlace = { placeTitle, placeText ->
                mainViewModel.uploadPlace(clickedCoord, placeTitle, placeText)
            },
            onDialogClose = {
                showAdd = false
            })
    }

}

@Composable
fun ListView(mainViewModel: MainViewModel) {
    val placeListState = mainViewModel.placeList().collectAsState(MainUiState.Init)

    if (placeListState.value == MainUiState.Init) {
        Text(text = "Initializing..")
    } else if (placeListState.value is MainUiState.PlacesRetrieved) {
        LazyColumn() {
            items((placeListState.value as MainUiState.PlacesRetrieved).placeList) {
                PlaceCard(
                    place = it.place,
                    onRemoveItem = {
                        mainViewModel.deletePlace(it.placeId)
                    },
                    currentUserId = mainViewModel.currentUserId
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceCard(
    place: Place,
    onRemoveItem: () -> Unit = {},
    currentUserId: String = ""
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = place.author,
                    )
                    Text(
                        text = place.placeTitle,
                    )
                    Text(
                        text = place.placeText,
                    )
                    Text(
                        text = "${place.lat}, ${place.lng}",
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentUserId.equals(place.uid)) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.clickable {
                                onRemoveItem()
                            },
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}
