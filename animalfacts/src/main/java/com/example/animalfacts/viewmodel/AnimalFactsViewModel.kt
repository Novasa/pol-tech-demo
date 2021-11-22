package com.example.animalfacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalfacts.R
import com.example.animalfacts.model.Animal
import com.example.animalfacts.model.AnimalFact
import com.example.animalfacts.model.AnimalImage
import com.example.catfacts.model.CatFact
import com.example.dogfacts.model.DogFact
import com.example.dogfacts.model.DogImage
import com.example.network.repository.FlowRepository
import com.example.network.repository.Repository
import com.example.network.repository.mapResult
import com.example.utility.state.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnimalFactsViewModel @Inject constructor(
    private val catFactsRepository: Repository<Int, List<CatFact>>,
    private val dogFactsRepository: Repository<Int, List<DogFact>>,
    private val dogImageRepository: FlowRepository<Int, DogImage>,
) : ViewModel() {

    private val _animalFacts = MutableStateFlow<List<AnimalFact>>(emptyList())
    val animalFacts = _animalFacts.asStateFlow()

    private val _dogImages = MutableStateFlow<List<AnimalImage>>(emptyList())
    val dogImages: StateFlow<List<AnimalImage>>
        get() = _dogImages.asStateFlow().apply {
            if (value.isEmpty()) {
                loadMoreAnimalImages()
            }
        }

    fun update() {

        Timber.d("Updating animal facts...")
        viewModelScope.launch {

            val dogFacts = async {
                dogFactsRepository.get(10)
                    .map { it.toAnimalFact() }
            }

            val catFacts = async {
                catFactsRepository.get(10)
                    .map { it.toAnimalFact() }
            }

            _animalFacts.value += (dogFacts.await() + catFacts.await()).shuffled()
        }
    }

    fun loadMoreAnimalImages() {
        Timber.d("Loading more animal images...")
        viewModelScope.launch {
            dogImageRepository.flow(3)
                .onEach { Timber.d("Image flow: $it") }
                .mapResult { AnimalImage(it.url) }
                .catch { Timber.e(it) }
                .collect {
                    if (it is Result.Success) {
                        _dogImages.value += it.value
                    }
                }
        }
    }

    private fun DogFact.toAnimalFact() = AnimalFact(Animal.DOG, fact, R.drawable.ic_dog)
    private fun CatFact.toAnimalFact() = AnimalFact(Animal.CAT, fact, R.drawable.ic_cat)
}
