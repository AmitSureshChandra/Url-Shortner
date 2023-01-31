package com.github.amitsureshchandra.urlshortner.service

import com.github.amitsureshchandra.urlshortner.dto.FeatureCreateDto
import com.github.amitsureshchandra.urlshortner.entity.Feature
import com.github.amitsureshchandra.urlshortner.repo.FeatureRepo
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class FeatureService(val featureRepo: FeatureRepo) {

    @PostConstruct
    fun  init(){
        seedFeatures()
    }

    private fun seedFeatures() {
        // seed features
        val featuresList = listOf(
            FeatureCreateDto("short name", "EDIT_SHORT"),
            FeatureCreateDto("fallback url", "FALL_BACK_URL"),
            FeatureCreateDto("link expiry", "LINK_EXPIRY"),
        )

        for(feature in featuresList){
            if(!featureRepo.existsByCode(feature.code)){
                featureRepo.save(Feature(feature.name, feature.code, 0))
            }
        }
    }
}