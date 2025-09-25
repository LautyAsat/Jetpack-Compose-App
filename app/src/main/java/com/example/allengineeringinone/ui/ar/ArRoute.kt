package com.example.allengineeringinone.ui.ar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.ar.data.model.ArUIState
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberView


private const val kModelFile = "models/needle.glb" // Modelo del pin

/**
 * Información: ArRoute es la vista stateful de la pantalla de AR.
 * */
@Composable
fun ArRoute(
    arViewModel: ArViewModel = hiltViewModel(),
) {
    val uiState: ArUIState by arViewModel.uiState.collectAsStateWithLifecycle()

    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    var anchorNodes: List<AnchorNode> by remember { mutableStateOf(emptyList()) }

    // Continuamente observando los anchors del viewModel para saber si hay que agregar un anchorNode a la vista.
    LaunchedEffect(uiState.anchors) {
        anchorNodes = uiState.anchors.map { anchor ->
            createAnchorNode(engine, modelLoader, materialLoader, anchor)
        }
    }

    var frame by remember { mutableStateOf<Frame?>(null) }
    var planeRenderer by remember { mutableStateOf(true) }

    ARScene(
        modifier = Modifier.fillMaxSize(),
        engine = engine,
        modelLoader = modelLoader,
        cameraNode = rememberARCameraNode(engine),
        childNodes = anchorNodes, // Le pasamos la lista de nodos a dibujar
        planeRenderer = planeRenderer,

        // Configuración de los settings
        sessionConfiguration = { session, config ->

            //Activamos si el celular tiene modo profundidad
            config.depthMode =
                when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                    true -> Config.DepthMode.AUTOMATIC
                    else -> Config.DepthMode.DISABLED
                }
            config.instantPlacementMode = Config.InstantPlacementMode.LOCAL_Y_UP
            config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
        },

        onGestureListener = rememberOnGestureListener(
            onSingleTapConfirmed = { event, node ->


                if(node == null){
                    val hitResults = frame?.hitTest(event.x, event.y)

                    // si al realizar tap es válido se crea el nodo.
                    hitResults?.firstOrNull {
                        it.isValid(
                            depthPoint = false,
                            point = false
                        )
                    }?.createAnchorOrNull()
                        ?.let { anchor ->
                            arViewModel.onArTap(anchor)
                            planeRenderer = false
                        }
                }
            },
        ),

        // Enable plane detection visualization


        // Frame update callback
        onSessionUpdated = { session, updatedFrame ->
            // Process AR frame updates
            frame = updatedFrame
       },

        // Track camera tracking state changes
        onTrackingFailureChanged = { trackingFailureReason ->
            // Handle tracking failures
        }
    )

    if(uiState.distanceMeters != null){
        Text(
            text = "Distancia: ${uiState.distanceMeters} metros",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp,80.dp,0.dp,0.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
            ,
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}



fun createAnchorNode(
    engine: Engine,
    modelLoader: ModelLoader,
    materialLoader: MaterialLoader,
    anchor: Anchor
): AnchorNode {
    val anchorNode = AnchorNode(engine = engine, anchor = anchor)
    val modelNode = ModelNode(
        modelInstance = modelLoader.createModelInstance(kModelFile),
        scaleToUnits = 0.1f
    ).apply {
        // configuración si es que queremos hacer escalable el modelo
//        isEditable = true
//        editableScaleRange = 0.2f..3.0f
    }
    val boundingBoxNode = CubeNode(
        engine,
        size = modelNode.extents,
        center = modelNode.center,
        materialInstance = materialLoader.createColorInstance(Color.White.copy(alpha = 0.5f))
    ).apply {
        isVisible = false
    }
    modelNode.addChildNode(boundingBoxNode)
    anchorNode.addChildNode(modelNode)

    listOf(modelNode, anchorNode).forEach {
        it.onEditingChanged = { editingTransforms ->
            boundingBoxNode.isVisible = editingTransforms.isNotEmpty()
        }
    }
    return anchorNode
}
