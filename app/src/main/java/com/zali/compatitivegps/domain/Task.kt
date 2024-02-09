package com.zali.compatitivegps.domain


data class ContentTask(
    val content: Content,
    val meta: List<Any>
)


data class Task(
    val endDate: String,
    val id: String,
    val name: String,
    val startDate: String,
    val status: String,
    val taskCompletionTime: String
)


data class Quest(
    val authorName: String,
    val category: Category,
    val creationDate: String,
    val description: String,
    val difficulty: Int,
    val endDate: String,
    val id: Int,
    val mainPhoto: String,
    val name: String,
    val photos: List<String>,
    val rating: Int,
    val startDate: String,
    val tags: List<String>,
    val tasks: List<Task>
)

data class Location(
    val lat: String,
    val lng: String
)

data class Content(
    val audios: List<Any>,
    val completionTime: Int,
    val description: String,
    val endDateConstraint: String,
    val finishDate: String,
    val goalType: String,
    val goalValue: String,
    val id: String,
    val location: Location,
    val name: String,
    val photos: List<Any>,
    val quest: Quest,
    val startDate: String,
    val startDateConstraint: String,
    val videos: List<Any>
)

data class Category(
    val description: String,
    val id: Int,
    val name: String,
    val photo: String
)