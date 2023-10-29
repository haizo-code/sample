package com.example.myapplication3.sample.model

import java.io.File
import java.util.UUID

enum class PostType {
    TEXT, IMAGE, VIDEO
}

data class Post constructor(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val content: String = "",
    val author: User = User(),
    val timestamp: Long = 0,
    val visibility: PostVisibility = PostVisibility.PUBLIC,
    val isPublished: Boolean = true,
    val isFeatured: Boolean = false,
    val location: String = "",
    val imageUrl: String = "",
    val videoUrl: String = "",
    val stats: PostStats = PostStats(),
    val socialInteraction: SocialInteraction = SocialInteraction(),
    val contentDetails: ContentDetails = ContentDetails(),
    val engagement: Engagement = Engagement(),
    val optionalFeatures: OptionalFeatures = OptionalFeatures(),
    val postType: PostType,
)

data class PostStats(
    val likes: Int = 0,
    val shares: Int = 0,
    val viewCount: Long = 0
)

data class SocialInteraction(
    val reactions: Map<Reaction, Int> = emptyMap(),
    val linkedUsers: List<User> = emptyList(),
    val userTags: List<User> = emptyList(),
    val userMentions: List<User> = emptyList()
)

data class ContentDetails(
    val tags: List<String> = emptyList(),
    val relatedPosts: List<Post> = emptyList(),
    val attachedFiles: List<File> = emptyList(),
    val embeddedContent: List<EmbeddedContent> = emptyList()
)

data class Engagement(
    val rating: Double = 0.0,
    val reviews: List<Review> = emptyList(),
    val challenges: List<Challenge> = emptyList(),
    val achievements: List<Achievement> = emptyList(),
    val donations: List<Donation> = emptyList(),
    val sponsor: Sponsor? = null
)

data class OptionalFeatures(
    val poll: Poll? = null,
    val event: Event? = null,
    val product: Product? = null,
    val customFields: Map<String, String> = emptyMap(),
    val sentimentAnalysis: SentimentAnalysis = SentimentAnalysis(),
    val linkedPosts: List<Post> = emptyList()
)

data class Comment(
    val id: Long = 0,
    val text: String = "",
    val author: User = User(),
    val timestamp: Long = 0
)

data class File(
    val id: Long = 0,
    val name: String = "",
    val size: Long = 0,
    val type: FileType = FileType.IMAGE
)

data class Poll(
    val question: String = "",
    val options: List<String> = emptyList(),
    val endDate: Long = 0
)

data class Event(
    val name: String = "",
    val location: String = "",
    val date: Long = 0
)

data class Product(
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0
)

data class Review(
    val user: User = User(),
    val rating: Double = 0.0,
    val comment: String = ""
)

data class Challenge(
    val name: String = "",
    val description: String = "",
    val reward: String = ""
)

data class Achievement(
    val name: String = "",
    val description: String = ""
)

data class Donation(
    val donor: User = User(),
    val amount: Double = 0.0
)

data class Sponsor(
    val sponsorName: String = "",
    val sponsorLogoUrl: String = ""
)

data class SentimentAnalysis(
    val sentimentScore: Double = 0.0,
    val sentimentLabel: String = ""
)

data class EmbeddedContent(
    val type: ContentType = ContentType.OTHER,
    val url: String = ""
)

enum class Reaction {
    LIKE, LOVE, HAHA, WOW, SAD, ANGRY
}

enum class FileType {
    IMAGE, DOCUMENT, AUDIO, VIDEO
}

enum class PostVisibility {
    PUBLIC, PRIVATE, FRIENDS_ONLY
}

enum class ContentType {
    YOUTUBE_VIDEO, TWITTER_TWEET, OTHER
}