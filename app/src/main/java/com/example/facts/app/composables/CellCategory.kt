package com.example.facts.app.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.facts.R
import com.example.facts.model.Category
import com.example.facts.viewmodel.FactsViewModel
import com.example.theme.FactsTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.ZonedDateTime

@Composable
fun CellCategory(
    category: Category,
    viewModel: FactsViewModel = viewModel()
){
    FactsTheme {
        Card {
            ConstraintLayout (modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()){
                val (itemCategory, itemId, itemTimestamp, itemDelete) = createRefs()

                Text(
                    text = category.name,
                    modifier = Modifier.constrainAs(itemCategory) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = category.id.toString(),
                    modifier = Modifier.constrainAs(itemId){
                        start.linkTo(parent.start)
                        top.linkTo(itemCategory.bottom)
                        bottom.linkTo(parent.bottom)
                    },
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = category.created.toString(),
                    modifier = Modifier.constrainAs(itemTimestamp){
                        start.linkTo(itemId.end, 8.dp)
                        top.linkTo(itemCategory.bottom)
                    },
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "deleteimage",
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = {
                                viewModel.deleteCategory(category = category)
                            })
                        .constrainAs(itemDelete) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }.height(40.dp).width(40.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF244662)),
                    contentScale = ContentScale.None
                )
            }
        }
    }
}

@Preview
@Composable
fun CellPreview() {
    CellCategory(category = Category(id = 1, name = "hej", created = ZonedDateTime.now()))

}
