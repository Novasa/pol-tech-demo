package com.example.animalfacts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class AnimalFactsViewModel @Inject constructor(
    private val catFactsRepository: FlowRepository<List<CatFact>>,
    private val dogFactsRepository: FlowRepository<List<DogFact>>,
    private val dogImageRepository: FlowRepository<DogImage>,
) : ViewModel() {

    private val _animalFacts = MutableLiveData<List<AnimalFact>>()
    val animalFacts: LiveData<List<AnimalFact>>
        get() = _animalFacts

    private val _dogImages = MutableLiveData<List<AnimalImage>>()
    val dogImages: LiveData<List<AnimalImage>>
        get() = _dogImages.also {
            if (it.value == null) {
                loadMoreAnimalImages()
            }
        }

    @ExperimentalCoroutinesApi
    fun update() {

        Timber.d("Updating animal facts...")
        viewModelScope.launch {
            animalFactsFlow
                .catch { Timber.e(it, "Error caught") }
                .collect { _animalFacts.postValue(it) }
        }
    }

    fun loadMoreAnimalImages() {
        Timber.d("Loading more animal images...")
        viewModelScope.launch {
            dogImageRepository.flow
                .onEach { Timber.d("Loaded image: $it") }
                .map { value -> AnimalImage(value.url) }
                .catch { Timber.e(it) }
                .collect { image ->
                    _dogImages.postValue((_dogImages.value ?: emptyList()) + image)
                }
        }
    }

    private val animalFactsFlow: Flow<List<AnimalFact>>
        get() = dogFactsFlow.zip(catFactsFlow) { dogFacts, catFacts ->
            (dogFacts + catFacts).shuffled()
        }

    private val dogFactsFlow: Flow<List<AnimalFact>>
        get() = dogFactsRepository.flow.map { dogFacts ->
            dogFacts.map { it.toAnimalFact() }
        }.catch { emit(emptyList()) }

    private val catFactsFlow: Flow<List<AnimalFact>>
        get() = catFactsRepository.flow.map { catFacts ->
            catFacts.map { it.toAnimalFact() }
        }.catch { emit(emptyList()) }

    private fun DogFact.toAnimalFact() = AnimalFact(Animal.DOG, fact, R.drawable.ic_dog)
    private fun CatFact.toAnimalFact() = AnimalFact(Animal.CAT, fact, R.drawable.ic_cat)
}
