package com.example.jetpack.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jetpack.compose.domain.model.Recipe
import com.example.jetpack.compose.network.model.RecipeNetworkEntity
import com.example.jetpack.compose.network.model.RecipeNetworkMapper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapper = RecipeNetworkMapper()
        val recipe = Recipe()
        val networkEntity: RecipeNetworkEntity = mapper.mapToEntity(recipe)

        val r = mapper.mapFromEntity(networkEntity)
        
    }
}