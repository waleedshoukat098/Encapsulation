package com.techinnovation.learning

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val AppBg = Color(0xFF070B14)
private val CardBg = Color(0xFF121B2D)
private val Accent = Color(0xFFA7F53B)
private val SoftText = Color(0xFF8E98AA)

private enum class TrackerTab { LogMeal, Trends, Scan, Profile }

sealed class AppScreen {
    data object Home : AppScreen()
}

private sealed class TrackerScreen {
    data object Main : TrackerScreen()
    data class Detail(val meal: MealLog) : TrackerScreen()
}

data class MealLog(
    val name: String,
    val category: String,
    val quantity: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double
)

data class ScanSummary(
    val items: List<String>,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double
)

@Composable
fun RishtonApp(onNavigate: (AppScreen) -> Unit) {
    LaunchedEffect(Unit) { onNavigate(AppScreen.Home) }

    val mealLogs = remember {
        mutableStateListOf(
            MealLog("Blueberry Oatmeal", "Breakfast", "320g serving", 420, 13.0, 72.0, 9.0),
            MealLog("Avocado Toast", "Breakfast", "2 slices + 80g", 285, 8.0, 32.0, 14.0),
            MealLog("Grilled Chicken Salad", "Lunch", "450g serving", 540, 46.0, 31.0, 22.0),
            MealLog("Protein Shake", "Dinner", "Whey + Milk 300ml", 210, 24.0, 10.0, 6.0)
        )
    }

    var activeTab by remember { mutableStateOf(TrackerTab.LogMeal) }
    var screen by remember { mutableStateOf<TrackerScreen>(TrackerScreen.Main) }
    var latestScan by remember {
        mutableStateOf(
            ScanSummary(
                items = listOf("Ready to scan"),
                calories = 0,
                protein = 0.0,
                carbs = 0.0,
                fat = 0.0
            )
        )
    }

    Surface(color = AppBg, modifier = Modifier.fillMaxSize()) {
        when (val current = screen) {
            is TrackerScreen.Main -> Scaffold(
                containerColor = AppBg,
                bottomBar = {
                    TrackerBottomBar(active = activeTab, onTabSelected = { activeTab = it })
                }
            ) { padding ->
                when (activeTab) {
                    TrackerTab.LogMeal -> LogMealScreen(
                        padding = padding,
                        meals = mealLogs,
                        onMealClick = { screen = TrackerScreen.Detail(it) }
                    )

                    TrackerTab.Trends -> TrendsScreen(padding, mealLogs)

                    TrackerTab.Scan -> ScanScreen(
                        padding = padding,
                        scan = latestScan,
                        onScanUpdate = { latestScan = it },
                        onLogMeal = {
                            if (latestScan.calories <= 0 || latestScan.items.firstOrNull() == "No food identified") return@ScanScreen
                            val meal = MealLog(
                                name = latestScan.items.joinToString(" + "),
                                category = "Scan",
                                quantity = "Detected via ML Kit live scan",
                                calories = latestScan.calories,
                                protein = latestScan.protein,
                                carbs = latestScan.carbs,
                                fat = latestScan.fat
                            )
                            mealLogs.add(0, meal)
                            activeTab = TrackerTab.LogMeal
                        }
                    )

                    TrackerTab.Profile -> PlaceholderProfile(padding)
                }
            }

            is TrackerScreen.Detail -> MealDetailScreen(
                meal = current.meal,
                onBack = { screen = TrackerScreen.Main }
            )
        }
    }
}

@Composable
private fun LogMealScreen(padding: PaddingValues, meals: List<MealLog>, onMealClick: (MealLog) -> Unit) {
    var selectedChip by remember { mutableStateOf("All") }
    var searchText by remember { mutableStateOf("") }
    val chips = listOf("All", "Breakfast", "Lunch", "Dinner", "Scan")

    val filteredMeals = meals.filter { meal ->
        val matchesCategory = selectedChip == "All" || meal.category == selectedChip
        val matchesSearch = meal.name.contains(searchText, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    val totalCalories = filteredMeals.sumOf { it.calories }
    val totalProtein = filteredMeals.sumOf { it.protein }
    val totalCarbs = filteredMeals.sumOf { it.carbs }
    val totalFat = filteredMeals.sumOf { it.fat }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Spacer(Modifier.height(8.dp)) }
        item {
            Text("Calorie Tracker", color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Today Summary", color = SoftText)
                    Text("$totalCalories kcal", color = Accent, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MacroCard("Protein", "${totalProtein.toInt()}g")
                        MacroCard("Carbs", "${totalCarbs.toInt()}g")
                        MacroCard("Fat", "${totalFat.toInt()}g")
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                leadingIcon = { Icon(Icons.Outlined.Search, null, tint = SoftText) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search your meal log...", color = SoftText) },
                shape = RoundedCornerShape(18.dp)
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                chips.forEach { chip ->
                    FilterChip(
                        selected = selectedChip == chip,
                        onClick = { selectedChip = chip },
                        label = { Text(chip) }
                    )
                }
            }
        }

        item {
            Text("Logged Meals", color = Color.White, fontWeight = FontWeight.SemiBold)
        }

        items(filteredMeals) { meal ->
            Card(
                colors = CardDefaults.cardColors(containerColor = CardBg),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMealClick(meal) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(meal.name, color = Color.White, fontWeight = FontWeight.Bold)
                        Text("${meal.category} • ${meal.quantity}", color = SoftText)
                    }
                    Text("${meal.calories} kcal", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        item { Spacer(Modifier.height(70.dp)) }
    }
}

@Composable
private fun TrendsScreen(padding: PaddingValues, meals: List<MealLog>) {
    val intake = meals.sumOf { it.calories }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Fitness Trends", color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(26.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("CALORIE INTAKE", color = SoftText)
                    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(intake.toString(), color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        Text("target 2200", color = Accent)
                    }
                    Spacer(Modifier.height(12.dp))
                    MiniLineChart()
                }
            }
        }
        item { Text("Micronutrients", color = Color.White, fontWeight = FontWeight.Bold) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                NutrientRing("Iron", "75%")
                NutrientRing("Vit D", "50%")
                NutrientRing("Calcium", "30%")
            }
        }
    }
}

@Composable
private fun MiniLineChart() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {
        val points = listOf(0.9f, 0.8f, 0.65f, 0.75f, 0.45f, 0.7f, 0.4f)
        val spacing = size.width / (points.size - 1)
        val path = Path().apply {
            points.forEachIndexed { index, value ->
                val x = index * spacing
                val y = size.height * value
                if (index == 0) moveTo(x, y) else quadraticTo(x - spacing / 2, y, x, y)
            }
        }
        drawPath(
            path = path,
            brush = Brush.horizontalGradient(listOf(Accent.copy(alpha = .6f), Accent)),
            style = Stroke(width = 8f, cap = StrokeCap.Round)
        )
        repeat(7) { i ->
            val x = i * spacing
            drawCircle(SoftText.copy(alpha = .2f), radius = 4f, center = Offset(x, size.height + 10f))
        }
    }
}

@Composable
private fun ScanScreen(
    padding: PaddingValues,
    scan: ScanSummary,
    onScanUpdate: (ScanSummary) -> Unit,
    onLogMeal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Live Meal Scanner", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .background(Color(0xFF0E1118), RoundedCornerShape(26.dp))
                .border(2.dp, Accent.copy(alpha = .5f), RoundedCornerShape(26.dp))
        ) {
            MlKitCameraScanner(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0E1118), RoundedCornerShape(26.dp)),
                onScanResult = onScanUpdate
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                scan.items.take(3).forEach {
                    Text(
                        it,
                        color = Color.Black,
                        modifier = Modifier
                            .background(Accent, RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(22.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Current Scan", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("${scan.calories} KCAL", color = Accent, fontWeight = FontWeight.Bold)
                }
                Text("Detected: ${scan.items.joinToString()}", color = SoftText)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MacroCard("Protein", "${scan.protein}g")
                    MacroCard("Carbs", "${scan.carbs}g")
                    MacroCard("Fat", "${scan.fat}g")
                }
                ActionButton("Log this meal", onLogMeal, dark = false)
            }
        }
    }
}

@Composable
private fun MealDetailScreen(meal: MealLog, onBack: () -> Unit) {
    Scaffold(
        containerColor = AppBg,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Meal Details", color = Color.White) },
                navigationIcon = {
                    IconButton(onBack) {
                        Icon(Icons.Outlined.ArrowBack, null, tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(24.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(meal.name, color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("${meal.category} • ${meal.quantity}", color = SoftText)
                    Text("${meal.calories} kcal", color = Accent, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MacroCard("Protein", "${meal.protein}g")
                MacroCard("Carbs", "${meal.carbs}g")
                MacroCard("Fat", "${meal.fat}g")
            }
            Card(colors = CardDefaults.cardColors(containerColor = CardBg), shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Calorie formula", color = Color.White, fontWeight = FontWeight.SemiBold)
                    Text(
                        "Estimated using ML Kit food labels and local nutrition mapping. Values are approximate and should be reviewed for exact tracking.",
                        color = SoftText
                    )
                }
            }
        }
    }
}

@Composable
private fun TrackerBottomBar(active: TrackerTab, onTabSelected: (TrackerTab) -> Unit) {
    val items = listOf(
        TrackerTab.LogMeal to Icons.Outlined.Home,
        TrackerTab.Trends to Icons.Outlined.ShowChart,
        TrackerTab.Scan to Icons.Outlined.CameraAlt,
        TrackerTab.Profile to Icons.Outlined.Person
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0B101B))
            .padding(horizontal = 10.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items.forEach { (tab, icon) ->
            val selected = active == tab
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onTabSelected(tab) }
            ) {
                Box(
                    modifier = Modifier
                        .background(if (selected) Accent else Color.Transparent, CircleShape)
                        .padding(8.dp)
                ) {
                    Icon(icon, null, tint = if (selected) Color.Black else SoftText)
                }
                Spacer(Modifier.height(2.dp))
                Text(tab.name, color = if (selected) Accent else SoftText, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun NutrientRing(title: String, value: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardBg),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.width(110.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .border(3.dp, Accent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(value, color = Color.White, style = MaterialTheme.typography.labelMedium)
            }
            Text(title, color = SoftText)
        }
    }
}

@Composable
private fun MacroCard(title: String, value: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0A1222)),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.weight(1f)
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, color = SoftText)
            Text(value, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ActionButton(text: String, onClick: () -> Unit, dark: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (dark) Color(0xFF1D263B) else Accent, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = if (dark) Color.White else Color(0xFF202B10), fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun PlaceholderProfile(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Outlined.Timeline, contentDescription = null, tint = SoftText)
    }
}
