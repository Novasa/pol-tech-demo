package com.example.animalfacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalfacts.R
import com.example.animalfacts.model.Animal
import com.example.animalfacts.model.AnimalFact
import com.example.animalfacts.model.AnimalImage
import com.example.catfacts.model.CatFact
import com.example.catfacts.model.CatUser
import com.example.catfacts.repository.CatFactsRepository
import com.example.dogfacts.model.DogFact
import com.example.dogfacts.model.DogImage
import com.example.dogfacts.repository.DogFactsRepository
import com.example.utility.coroutines.Data
import com.example.utility.coroutines.FlowChannel
import com.example.utility.coroutines.mapResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnimalFactsViewModel @Inject constructor(
    private val catFactsRepository: CatFactsRepository,
    private val dogFactsRepository: DogFactsRepository,
    private val dogImageChannel: FlowChannel<Int, DogImage>,
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

    private val _catUser = MutableStateFlow<CatUser?>(null)
    val catUser: StateFlow<CatUser?>
        get() {
            if (_catUser.value == null) {
                viewModelScope.launch {
                    catFactsRepository.getCatUser()
                        .flow()
                        .filter { it.isSuccess() }
                        .map { (it as Data.Success).value }
                        .collect { _catUser.value = it }
                }
            }
            return _catUser.asStateFlow()
        }


    fun updateCatUser(name: String) {
        viewModelScope.launch {
            catFactsRepository.updateCatUser()
                .flow(CatUser(name))
                .filter { it.isSuccess() }
                .map { (it as Data.Success).value }
                .collect { _catUser.value = it }
        }
    }

    fun updateAnimalFacts() {

        Timber.d("Updating animal facts...")
        viewModelScope.launch {

            val dogFacts = async {
                try {
                    dogFactsRepository.getDogFacts(10)
                        .map { it.toAnimalFact() }
                } catch (e: Exception) {
                    emptyList()
                }
            }

            val catFactsChannel = Channel<List<AnimalFact>>()

            launch {
                catFactsRepository.getCatFacts().flow(10)
                    .filter { it.isNotState() }
                    .mapResult { facts -> facts.map { it.toAnimalFact() } }
                    .map {
                        when (it) {
                            is Data.Success -> it.value
                            else -> emptyList()
                        }
                    }
                    .collect { catFactsChannel.send(it) }
            }

            _animalFacts.value += (dogFacts.await() + catFactsChannel.receive()).shuffled()
        }
    }

    fun loadMoreAnimalImages() {
        Timber.d("Loading more animal images...")
        viewModelScope.launch {
            dogImageChannel.flow(3)
                .onEach { Timber.d("Image flow: $it") }
                .mapResult { AnimalImage(it.url) }
                .catch { Timber.e(it) }
                .collect {
                    if (it is Data.Success) {
                        _dogImages.value += it.value
                    }
                }
        }
    }

    private fun DogFact.toAnimalFact() = AnimalFact(Animal.DOG, fact, R.drawable.ic_dog)
    private fun CatFact.toAnimalFact() = AnimalFact(Animal.CAT, fact, R.drawable.ic_cat)
}
