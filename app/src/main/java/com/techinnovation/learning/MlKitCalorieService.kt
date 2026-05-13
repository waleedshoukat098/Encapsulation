package com.techinnovation.learning

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

/**
 * Uses Google ML Kit on-device image labeling (free) and a local nutrition map.
 * No paid nutrition API is required.
 */
object MlKitCalorieService {

    private val nutritionMap = mapOf(
        "apple" to MealLog("Apple", "Scan", "1 medium", 95, 0.5, 25.0, 0.3),
        "rice" to MealLog("White Rice", "Scan", "1 cup", 205, 4.0, 45.0, 0.4),
        "banana" to MealLog("Banana", "Scan", "1 medium", 105, 1.3, 27.0, 0.4),
        "egg" to MealLog("Egg", "Scan", "1 large", 78, 6.0, 0.6, 5.0),
        "chicken" to MealLog("Chicken Breast", "Scan", "100 g", 165, 31.0, 0.0, 3.6),
        "salad" to MealLog("Green Salad", "Scan", "1 bowl", 90, 3.2, 12.0, 3.5),
        "bread" to MealLog("Bread Slice", "Scan", "1 slice", 80, 3.0, 15.0, 1.0)
    )

    private val labeler by lazy {
        ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
    }

    fun estimateFromLabels(labels: List<String>): ScanSummary {
        val matched = labels.mapNotNull { raw ->
            nutritionMap.entries.firstOrNull { raw.contains(it.key, ignoreCase = true) }?.value
        }

        if (matched.isEmpty()) {
            return ScanSummary(items = listOf("No food identified"), calories = 0, protein = 0.0, carbs = 0.0, fat = 0.0)
        }

        return ScanSummary(
            items = matched.map { it.name }.distinct(),
            calories = matched.sumOf { it.calories },
            protein = matched.sumOf { it.protein },
            carbs = matched.sumOf { it.carbs },
            fat = matched.sumOf { it.fat }
        )
    }

    fun detectFromBitmap(
        bitmap: Bitmap,
        onResult: (ScanSummary) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val image = InputImage.fromBitmap(bitmap, 0)
        labeler.process(image)
            .addOnSuccessListener { labels ->
                val names = labels.filter { it.confidence >= 0.65f }.map { it.text }
                onResult(estimateFromLabels(names))
            }
            .addOnFailureListener(onError)
    }

    fun detectFromImageProxy(
        imageProxy: ImageProxy,
        onResult: (ScanSummary) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val mediaImage = imageProxy.image
        if (mediaImage == null) {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        labeler.process(image)
            .addOnSuccessListener { labels ->
                val names = labels.filter { it.confidence >= 0.65f }.map { it.text }
                onResult(estimateFromLabels(names))
            }
            .addOnFailureListener(onError)
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}
