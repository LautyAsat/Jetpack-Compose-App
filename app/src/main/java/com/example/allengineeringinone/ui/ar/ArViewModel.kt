package com.example.allengineeringinone.ui.ar

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.allengineeringinone.ui.ar.data.model.ArUIState
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.Pose
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.sqrt

private const val kModelFile = "models/spiderbot.glb"

@HiltViewModel
class ArViewModel @Inject constructor() : ViewModel() {
    private val viewModelState = MutableStateFlow(ArUIState())
    val uiState: StateFlow<ArUIState> = viewModelState.asStateFlow()


    fun onArTap(anchor: Anchor){
        val currentAnchors = viewModelState.value.anchors.toMutableList()

        if(currentAnchors.size == 2){
            currentAnchors.clear()
            currentAnchors.add(anchor)
        }
        else{
            currentAnchors.add(anchor)
        }

        var distance: Float? = null

        if (currentAnchors.size == 2){
            distance = calculateDistance(currentAnchors[0].pose, currentAnchors[1].pose)
        }

        Log.i("info", "ajajajs")

        viewModelState.update {
            it.copy(
                anchors = currentAnchors,
                distanceMeters = distance
            )
        }

    }

    private fun calculateDistance(pose1: Pose, pose2: Pose): Float {
        val dx = pose1.tx() - pose2.tx()
        val dy = pose1.ty() - pose2.ty()
        val dz = pose1.tz() - pose2.tz()
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    fun clearAnchors() {
        viewModelState.update { it.copy(anchors = emptyList(), distanceMeters = null) }
    }


}