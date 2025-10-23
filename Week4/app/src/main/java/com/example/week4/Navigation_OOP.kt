package com.example.week4

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week4.ui.theme.Week4Theme
import kotlinx.coroutines.launch

val primaryBlue = Color(0xFF2196F3)

// ---OOP---
data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

val onboardingPages = listOf(
    OnboardingPage(
        imageRes = R.drawable.uth,
        title = "UTH SmartTasks",
        description = ""
    ),
    OnboardingPage(
        imageRes = R.drawable.pic1,
        title = "Easy Time Management",
        description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first."
    ),
    OnboardingPage(
        imageRes = R.drawable.pic2,
        title = "Increase Work Effectiveness",
        description = "Time management and the determination of more important tasks will give you job statistics better and always improve."
    ),
    OnboardingPage(
        imageRes = R.drawable.pic3,
        title = "Reminder Notification",
        description = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set."
    )
)

// ---GIAO DIỆN---
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationOOPScreen(navController: NavController) {

    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(
                currentPage = pagerState.currentPage,
                totalPages = onboardingPages.size,
                onSkip = {
                    navController.popBackStack()
                }
            )
        },
        bottomBar = {
            BottomBar(
                currentPage = pagerState.currentPage,
                totalPages = onboardingPages.size,
                onBack = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                onNext = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                onGetStarted = {
                    navController.popBackStack()
                }
            )
        },
        containerColor = Color.White
    ) { paddingValues ->

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { pageIndex ->
            val page = onboardingPages[pageIndex]
            PageUI(page = page, pageIndex = pageIndex)
        }
    }
}

// ---COMPONENT PHỤ---
@Composable
fun PageUI(page: OnboardingPage, pageIndex: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (pageIndex == 0) {
            Image(
                painter = painterResource(id = page.imageRes),
                contentDescription = page.title,
                modifier = Modifier
                    .width(150.dp)
                    .aspectRatio(1.5f),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = page.title,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color(0xFF006EE9)
            )
            Spacer(modifier = Modifier.height(150.dp))
        }
        else {
            Image(
                painter = painterResource(id = page.imageRes),
                contentDescription = page.title,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .aspectRatio(1.1f),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
@Composable
fun TopBar(currentPage: Int, totalPages: Int, onSkip: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(totalPages) { index ->
                if (index == 0) return@repeat

                val isSelected = (index == currentPage)
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) primaryBlue else Color.LightGray // <-- Dùng màu xanh của bạn
                        )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        if (currentPage < totalPages - 1) {
            TextButton(onClick = onSkip) {
                Text(text = "skip", color = primaryBlue, fontSize = 16.sp)
            }
        }
    }
}
@Composable
fun BottomBar(
    currentPage: Int,
    totalPages: Int,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onGetStarted: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (currentPage > 0) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(primaryBlue)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        val isLastPage = (currentPage == totalPages - 1)

        if (currentPage == 0) {
            Button(
                onClick = onNext,
                shape = CircleShape,
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp), //
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue), //
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                Text(
                    text = "Next",
                    fontSize = 16.sp
                )
            }
        } else {
            Button(
                onClick = {
                    if (isLastPage) onGetStarted() else onNext()
                },
                shape = CircleShape,
                modifier = Modifier
                    .height(50.dp)
                    .widthIn(min = if (isLastPage) 150.dp else 50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                Text(
                    text = if (isLastPage) "Get Started" else "Next",
                    fontSize = 16.sp
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun NavigationOOPScreenPreview() {
    Week4Theme {
        val navController = rememberNavController()
        NavigationOOPScreen(navController = navController)
    }
}