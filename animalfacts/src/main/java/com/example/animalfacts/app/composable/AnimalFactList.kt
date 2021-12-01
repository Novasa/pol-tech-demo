package com.example.animalfacts.app.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animalfacts.model.AnimalFact
import com.example.animalfacts.viewmodel.AnimalFactsViewModel
import com.example.theme.FactsTheme

@Composable
fun AnimalFactList(viewModel: AnimalFactsViewModel = viewModel()) {

    val facts: List<AnimalFact> by viewModel.animalFacts.collectAsState()

    FactsTheme {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(facts) { fact ->
                Row(fact)
            }
        }
    }
}

@Composable
fun Row(fact: AnimalFact) {
    Card {
        ConstraintLayout {
            Text(text = fact.text)
        }
    }
}