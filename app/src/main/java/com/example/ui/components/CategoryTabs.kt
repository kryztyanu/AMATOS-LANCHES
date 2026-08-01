package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MenuTab
import com.example.ui.theme.RedPrimary

@Composable
fun CategoryTabs(
    selectedTab: MenuTab,
    onTabSelected: (MenuTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        MenuTab.SANDUICHES to "SANDUÍCHES",
        MenuTab.PASTEIS to "PASTÉIS",
        MenuTab.PETISCOS to "PETISCOS"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEach { (tab, label) ->
            val isSelected = selectedTab == tab
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) RedPrimary else Color.White,
                label = "tabBg"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else Color(0xFF1C1B1B),
                label = "tabText"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(bgColor)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) RedPrimary else Color(0xFFE6BDBB),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onTabSelected(tab) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    color = textColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
