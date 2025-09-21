package com.example.allengineeringinone.ui.ar.data.model

import com.google.ar.core.Anchor
import io.github.sceneview.ar.node.AnchorNode

data class ArUIState(
    val anchors: List<Anchor> = emptyList(),
    val distanceMeters: Float? = null,
    val anchorsv2: List<AnchorNode> = emptyList()
)
