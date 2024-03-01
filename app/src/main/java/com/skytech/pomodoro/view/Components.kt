package com.skytech.pomodoro.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skytech.pomodoro.R
import com.skytech.pomodoro.view_model.HomeViewModel

@Composable
fun PomoButton(height: Dp, width: Dp, backgroundColor: Color, buttonIcon: ImageVector, onClick: () -> Unit){
    Box(modifier = Modifier
        .size(height = height, width = width)
        .background(color = backgroundColor, shape = RoundedCornerShape(25.dp))
        .padding(20.dp)
        .clickable(indication = null, interactionSource = MutableInteractionSource(),onClick= { onClick() })


    ){
        Icon(modifier = Modifier.fillMaxSize(),imageVector = buttonIcon ,contentDescription ="" )
    }





}


@Composable
fun ChangeValueButton(state: HomeViewModel.PomodoroState, lenght: MutableState<Int>,){

    Row (verticalAlignment = Alignment.CenterVertically){
        IconButton(onClick = {
            if(lenght.value-1>0){ lenght.value-=1
                state.updateStateDuration(lenght.value*60)}
           }) {
            Icon(painter = painterResource(id = R.drawable.increment_icon), contentDescription = "Increment")
        }
        Text(text = (lenght.value).toString())
        IconButton(onClick = {
            lenght.value+=1
            state.updateStateDuration(lenght.value*60)
        }) {
            Icon(painter = painterResource(id = R.drawable.increment_icon), contentDescription = "Decrement")
        }
    }
}


