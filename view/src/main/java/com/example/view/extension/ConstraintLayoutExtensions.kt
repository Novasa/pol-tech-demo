package com.example.view.extension

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.AutoTransition
import androidx.transition.Scene
import androidx.transition.TransitionManager

class ConstraintUpdateOptions {
    var animate = true
}

fun ConstraintLayout.updateConstraints(update: ConstraintSet.(ConstraintUpdateOptions) -> Unit) {
    ConstraintSet().let { constraints ->
        val options = ConstraintUpdateOptions()

        constraints.clone(this)
        update(constraints, options)
        constraints.applyTo(this)

        if (options.animate) {
            TransitionManager.go(Scene(this), AutoTransition())
        }
    }
}
