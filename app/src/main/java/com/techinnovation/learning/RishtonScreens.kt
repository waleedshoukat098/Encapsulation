package com.techinnovation.learning

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

sealed class AppScreen(val title: String) {
    data object Splash : AppScreen("Welcome")
    data object Onboarding : AppScreen("Onboarding")
    data object Home : AppScreen("Home")
    data object CommonIssues : AppScreen("Common Issues")
    data object IssueDetail : AppScreen("Issue Detail")
    data object SolverMode : AppScreen("AI Solver")
    data object SolverInput : AppScreen("Share both sides")
    data object CoolDown : AppScreen("Cool down")
    data object AiResult : AppScreen("AI Result")
    data object DailyTips : AppScreen("Daily Tips")
    data object SavedSolutions : AppScreen("Saved Solutions")
    data object SavedDetail : AppScreen("Saved Detail")
    data object Profile : AppScreen("Profile & Settings")
    data object Safety : AppScreen("Safety")
}

@Composable
fun RishtonApp(onNavigate: (AppScreen) -> Unit) {
    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Splash) }

    val handleNavigate: (AppScreen) -> Unit = { screen ->
        currentScreen = screen
        onNavigate(screen)
    }

    when (currentScreen) {
        AppScreen.Splash -> SplashScreen(onContinue = { handleNavigate(AppScreen.Onboarding) })
        AppScreen.Onboarding -> OnboardingFlow(onFinish = { handleNavigate(AppScreen.Home) })
        AppScreen.Home -> HomeScreen(
            onOpenIssues = { handleNavigate(AppScreen.CommonIssues) },
            onOpenSolver = { handleNavigate(AppScreen.SolverMode) },
            onOpenTips = { handleNavigate(AppScreen.DailyTips) },
            onOpenProfile = { handleNavigate(AppScreen.Profile) }
        )
        AppScreen.CommonIssues -> CommonIssuesScreen(
            onBack = { handleNavigate(AppScreen.Home) },
            onIssueSelected = { handleNavigate(AppScreen.IssueDetail) }
        )
        AppScreen.IssueDetail -> IssueDetailScreen(
            onBack = { handleNavigate(AppScreen.CommonIssues) },
            onSolveWithAi = { handleNavigate(AppScreen.SolverMode) }
        )
        AppScreen.SolverMode -> SolverModeScreen(
            onBack = { handleNavigate(AppScreen.Home) },
            onModeContinue = { handleNavigate(AppScreen.SolverInput) }
        )
        AppScreen.SolverInput -> SolverInputScreen(
            onBack = { handleNavigate(AppScreen.SolverMode) },
            onAnalyze = { handleNavigate(AppScreen.AiResult) },
            onTriggerCooldown = { handleNavigate(AppScreen.CoolDown) }
        )
        AppScreen.CoolDown -> CoolDownScreen(
            onContinue = { handleNavigate(AppScreen.AiResult) }
        )
        AppScreen.AiResult -> AiResultScreen(
            onBack = { handleNavigate(AppScreen.SolverInput) },
            onSave = { handleNavigate(AppScreen.SavedSolutions) },
            onTryAgain = { handleNavigate(AppScreen.SolverInput) },
            onSafety = { handleNavigate(AppScreen.Safety) }
        )
        AppScreen.DailyTips -> DailyTipsScreen(
            onBack = { handleNavigate(AppScreen.Home) }
        )
        AppScreen.SavedSolutions -> SavedSolutionsScreen(
            onBack = { handleNavigate(AppScreen.Home) },
            onOpenDetail = { handleNavigate(AppScreen.SavedDetail) }
        )
        AppScreen.SavedDetail -> SavedDetailScreen(
            onBack = { handleNavigate(AppScreen.SavedSolutions) }
        )
        AppScreen.Profile -> ProfileScreen(
            onBack = { handleNavigate(AppScreen.Home) }
        )
        AppScreen.Safety -> SafetyScreen(
            onBack = { handleNavigate(AppScreen.Home) }
        )
    }
}

@Composable
private fun ScreenScaffold(
    title: String,
    onBack: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        content = content
    )
}

@Composable
private fun SplashScreen(onContinue: () -> Unit) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.background
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                LogoBadge()
                Text(
                    text = "Rishton ke Masail",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Understanding relationships, one conversation at a time",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Security,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Global • Inclusive • Emotionally safe",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            Button(
                onClick = onContinue,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text(text = "Begin")
            }
        }
    }
}

@Composable
private fun LogoBadge() {
    Box(
        modifier = Modifier
            .size(72.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun OnboardingFlow(onFinish: () -> Unit) {
    var step by remember { mutableStateOf(0) }
    val steps = listOf(
        OnboardingStep(
            title = "App purpose",
            description = "A global, emotion-first guide for couples to restore connection.",
            icon = Icons.Outlined.Spa
        ),
        OnboardingStep(
            title = "AI role",
            description = "The AI listens without judgment and helps both partners feel heard.",
            icon = Icons.Outlined.FavoriteBorder
        ),
        OnboardingStep(
            title = "Privacy promise",
            description = "Your reflections stay private. Safety and confidentiality come first.",
            icon = Icons.Outlined.Lock
        )
    )

    ScreenScaffold(title = "Onboarding") { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                StepIndicator(current = step, total = steps.size)
                OnboardingCard(steps[step])
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { if (step > 0) step -= 1 },
                    modifier = Modifier.weight(1f),
                    enabled = step > 0
                ) {
                    Text(text = "Back")
                }
                Button(
                    onClick = {
                        if (step < steps.lastIndex) step += 1 else onFinish()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = if (step < steps.lastIndex) "Next" else "Start App")
                }
            }
        }
    }
}

private data class OnboardingStep(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
private fun StepIndicator(current: Int, total: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(total) { index ->
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(if (index == current) 32.dp else 14.dp)
                    .background(
                        color = if (index == current) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}

@Composable
private fun OnboardingCard(step: OnboardingStep) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = step.icon, contentDescription = null)
            }
            Text(text = step.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(text = step.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun HomeScreen(
    onOpenIssues: () -> Unit,
    onOpenSolver: () -> Unit,
    onOpenTips: () -> Unit,
    onOpenProfile: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Rishton ke Masail") },
                actions = {
                    IconButton(onClick = onOpenProfile) {
                        Icon(imageVector = Icons.Outlined.Person, contentDescription = "Profile")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            HomeBottomBar(onHome = {}, onTips = onOpenTips, onProfile = onOpenProfile)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "How can we support your relationship today?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            PrimaryCard(
                title = "AI Relationship Solver",
                description = "Share both perspectives and get balanced, respectful guidance.",
                cta = "Start now",
                onClick = onOpenSolver,
                highlight = true
            )
            PrimaryCard(
                title = "Common Relationship Issues",
                description = "Explore global scenarios like trust, communication, and emotional neglect.",
                cta = "Browse issues",
                onClick = onOpenIssues
            )
            PrimaryCard(
                title = "Daily Relationship Tips",
                description = "One gentle suggestion each day, culturally neutral and actionable.",
                cta = "View today",
                onClick = onOpenTips
            )
        }
    }
}

@Composable
private fun HomeBottomBar(onHome: () -> Unit, onTips: () -> Unit, onProfile: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 32.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomNavItem(icon = Icons.Outlined.Home, label = "Home", onClick = onHome)
        BottomNavItem(icon = Icons.Outlined.TipsAndUpdates, label = "Tips", onClick = onTips)
        BottomNavItem(icon = Icons.Outlined.Person, label = "Profile", onClick = onProfile)
    }
}

@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick) {
            Icon(imageVector = icon, contentDescription = label)
        }
        Text(text = label, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun PrimaryCard(
    title: String,
    description: String,
    cta: String,
    onClick: () -> Unit,
    highlight: Boolean = false
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (highlight) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(text = description, style = MaterialTheme.typography.bodySmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = cta, style = MaterialTheme.typography.labelLarge)
                Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = null)
            }
        }
    }
}

@Composable
private fun CommonIssuesScreen(onBack: () -> Unit, onIssueSelected: () -> Unit) {
    ScreenScaffold(title = "Common issues", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Search issues") },
                leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            )
            val issues = listOf(
                "Communication gap",
                "Trust & transparency",
                "Financial stress",
                "Family or external pressure",
                "Emotional neglect"
            )
            issues.forEach { issue ->
                Card(
                    onClick = onIssueSelected,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = issue, style = MaterialTheme.typography.titleSmall)
                        Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Composable
private fun IssueDetailScreen(onBack: () -> Unit, onSolveWithAi: () -> Unit) {
    ScreenScaffold(title = "Issue detail", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionCard(title = "Issue explanation", body = "Communication gaps appear when expectations and emotions are not expressed clearly.")
            SectionCard(title = "Why it happens", body = "Stress, different communication styles, or unresolved hurt can create distance.")
            SectionCard(title = "Partner A perspective", body = "Feels unheard and unsure if their needs are understood.")
            SectionCard(title = "Partner B perspective", body = "Feels overwhelmed and may avoid difficult conversations.")
            SectionCard(title = "Practical solutions", body = "Use reflective listening, schedule calm check-ins, and agree on clear next steps.")
            SectionCard(title = "Daily practice", body = "Share one appreciation and one gentle request each day.")
            Button(
                onClick = onSolveWithAi,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Solve this with AI")
            }
        }
    }
}

@Composable
private fun SectionCard(title: String, body: String) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(text = body, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun SolverModeScreen(onBack: () -> Unit, onModeContinue: () -> Unit) {
    var selected by remember { mutableStateOf(2) }
    ScreenScaffold(title = "AI Solver", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Choose whose perspective to analyze.",
                style = MaterialTheme.typography.bodyMedium
            )
            SolverOptionCard(
                title = "Partner A",
                description = "One-sided reflection from Partner A.",
                selected = selected == 0,
                onClick = { selected = 0 }
            )
            SolverOptionCard(
                title = "Partner B",
                description = "One-sided reflection from Partner B.",
                selected = selected == 1,
                onClick = { selected = 1 }
            )
            SolverOptionCard(
                title = "Both partners (recommended)",
                description = "Balanced, dual-perspective analysis.",
                selected = selected == 2,
                onClick = { selected = 2 }
            )
            Button(
                onClick = onModeContinue,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Continue")
            }
        }
    }
}

@Composable
private fun SolverOptionCard(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        border = if (selected) null else androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(text = description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun SolverInputScreen(
    onBack: () -> Unit,
    onAnalyze: () -> Unit,
    onTriggerCooldown: () -> Unit
) {
    var partnerA by remember { mutableStateOf("") }
    var partnerB by remember { mutableStateOf("") }

    ScreenScaffold(title = "AI Solver input", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoBanner(
                icon = Icons.Outlined.Notifications,
                message = "Use respectful language. Abuse, threats, or hate speech are not allowed."
            )
            OutlinedTextField(
                value = partnerA,
                onValueChange = { partnerA = it },
                label = { Text(text = "Partner A statement") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )
            OutlinedTextField(
                value = partnerB,
                onValueChange = { partnerB = it },
                label = { Text(text = "Partner B statement") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onTriggerCooldown,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Need a pause")
                }
                Button(
                    onClick = onAnalyze,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Analyze & suggest")
                }
            }
        }
    }
}

@Composable
private fun InfoBanner(icon: androidx.compose.ui.graphics.vector.ImageVector, message: String) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Text(text = message, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun CoolDownScreen(onContinue: () -> Unit) {
    ScreenScaffold(title = "Cool-down") { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Take a slow breath",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Inhale for 4 • Hold for 4 • Exhale for 6",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    shape = CircleShape
                                )
                        )
                    }
                }
                Text(
                    text = "When you're ready, continue with a calmer mindset.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Continue")
            }
        }
    }
}

@Composable
private fun AiResultScreen(
    onBack: () -> Unit,
    onSave: () -> Unit,
    onTryAgain: () -> Unit,
    onSafety: () -> Unit
) {
    ScreenScaffold(title = "AI Result", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ResultCard(title = "Issue summary", body = "Both partners want closeness but feel misunderstood when tensions rise.")
            ResultCard(title = "Emotional analysis", body = "Partner A feels unseen. Partner B feels pressured and retreats.")
            ResultCard(title = "Possible misunderstandings", body = "Tone and timing made requests sound like criticism.")
            ResultCard(title = "Advice for Partner A", body = "Use calm check-ins and focus on one concern at a time.")
            ResultCard(title = "Advice for Partner B", body = "Acknowledge emotions before explaining your perspective.")
            ResultCard(title = "Shared solution", body = "Agree on a weekly reset conversation with clear boundaries.")
            ResultCard(title = "Small daily practices", body = "Share one gratitude and one gentle request each day.")
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    text = "You’re doing brave work. Small steps build lasting trust.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = onSave, modifier = Modifier.weight(1f)) {
                    Text(text = "Save solution")
                }
                OutlinedButton(onClick = onTryAgain, modifier = Modifier.weight(1f)) {
                    Text(text = "Try again")
                }
            }
            OutlinedButton(onClick = onSafety, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Safety & emergency")
            }
        }
    }
}

@Composable
private fun ResultCard(title: String, body: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(text = body, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun DailyTipsScreen(onBack: () -> Unit) {
    ScreenScaffold(title = "Daily tips", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Outlined.TipsAndUpdates, contentDescription = null)
                        Text(
                            text = "Today’s tip",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "Ask your partner how they want to feel supported this week.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = {}) { Text(text = "Refresh") }
                        OutlinedButton(onClick = {}) { Text(text = "Save") }
                    }
                }
            }
            Text(
                text = "Swipe for more tips",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun SavedSolutionsScreen(onBack: () -> Unit, onOpenDetail: () -> Unit) {
    ScreenScaffold(title = "Saved solutions", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf(
                "Communication reset" to "Apr 12",
                "Trust rebuilding plan" to "Apr 4",
                "Financial stress check-in" to "Mar 27"
            ).forEach { (title, date) ->
                Card(onClick = onOpenDetail, shape = RoundedCornerShape(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                            Text(text = date, style = MaterialTheme.typography.bodySmall)
                        }
                        Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Composable
private fun SavedDetailScreen(onBack: () -> Unit) {
    ScreenScaffold(title = "Saved detail", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionCard(title = "Title", body = "Communication reset")
            SectionCard(title = "Date", body = "Apr 12")
            SectionCard(title = "Summary", body = "Agree on calm check-ins and validate each other before problem solving.")
            SectionCard(title = "Next steps", body = "Plan a 15-minute weekly reset conversation.")
        }
    }
}

@Composable
private fun ProfileScreen(onBack: () -> Unit) {
    var aiLength by remember { mutableStateOf(0.5f) }
    var selectedLang by remember { mutableStateOf("English") }
    ScreenScaffold(title = "Profile & settings", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsCard(
                title = "Language",
                description = "Choose your preferred language.",
                icon = Icons.Outlined.Language
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("English", "Urdu", "Roman Urdu").forEach { lang ->
                        FilterChip(
                            selected = selectedLang == lang,
                            onClick = { selectedLang = lang },
                            label = { Text(text = lang) }
                        )
                    }
                }
            }
            SettingsCard(
                title = "AI response length",
                description = "Control how detailed the responses feel.",
                icon = Icons.Outlined.Notifications
            ) {
                Slider(value = aiLength, onValueChange = { aiLength = it })
            }
            SettingsRow(title = "Cultural / religious references", description = "Optional")
            SettingsRow(title = "Clear history", description = "Remove saved sessions")
            SettingsRow(title = "Privacy policy", description = "Read policy and terms")
        }
    }
}

@Composable
private fun SettingsCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null)
                Column {
                    Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Text(text = description, style = MaterialTheme.typography.bodySmall)
                }
            }
            content()
        }
    }
}

@Composable
private fun SettingsRow(title: String, description: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(text = description, style = MaterialTheme.typography.bodySmall)
            }
            Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
        }
    }
}

@Composable
private fun SafetyScreen(onBack: () -> Unit) {
    ScreenScaffold(title = "Safety", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Text(
                            text = "You deserve safety",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                    Text(
                        text = "If abuse, violence, or self-harm risks are present, seek professional help immediately.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            Text(
                text = "Reach out to local emergency services or a trusted person in your region.",
                style = MaterialTheme.typography.bodySmall
            )
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Return home")
            }
        }
    }
}
