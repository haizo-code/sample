package com.example.myapplication3.sample.model

import java.util.Random

object PostGeneratorHelper {

    fun generateRandomPost(postType: PostType): Post {
        val random = Random()

        val id = random.nextLong().toString()
        val title = "Random Post Title ${random.nextInt(100)}"
        val content = generateRandomParagraph()
        val author = generateRandomUser()
        val timestamp = System.currentTimeMillis()
        val visibility = PostVisibility.values().random()
        val isPublished = random.nextBoolean()
        val isFeatured = random.nextBoolean()
        val location = "Random Location ${random.nextInt(100)}"
        val imageUrl = "https://example.com/random-image-$id.jpg"
        val videoUrl = "https://example.com/random-video-$id.mp4"

        val stats = PostStats(
            likes = random.nextInt(100),
            shares = random.nextInt(50),
            viewCount = random.nextLong()
        )

        val socialInteraction = SocialInteraction(
            reactions = generateRandomReactions(),
            linkedUsers = List(random.nextInt(5)) { generateRandomUser() },
            userTags = List(random.nextInt(5)) { generateRandomUser() },
            userMentions = List(random.nextInt(5)) { generateRandomUser() }
        )

        val contentDetails = ContentDetails(
            tags = List(random.nextInt(5)) { "Tag ${random.nextInt(100)}" },
            relatedPosts = emptyList(),
            attachedFiles = emptyList(),
            embeddedContent = List(random.nextInt(3)) { generateRandomEmbeddedContent() }
        )

        val engagement = Engagement(
            rating = random.nextDouble() * 5,
            reviews = List(random.nextInt(5)) { generateRandomReview() },
            challenges = List(random.nextInt(3)) { generateRandomChallenge() },
            achievements = List(random.nextInt(3)) { generateRandomAchievement() },
            donations = List(random.nextInt(3)) { generateRandomDonation() },
            sponsor = if (random.nextBoolean()) generateRandomSponsor() else null
        )

        val optionalFeatures = OptionalFeatures(
            poll = if (random.nextBoolean()) generateRandomPoll() else null,
            event = if (random.nextBoolean()) generateRandomEvent() else null,
            product = if (random.nextBoolean()) generateRandomProduct() else null,
            customFields = generateRandomCustomFields(),
            sentimentAnalysis = generateRandomSentimentAnalysis(),
            linkedPosts = emptyList()
        )

        return Post(
            id = id,
            title = title,
            content = content,
            author = author,
            timestamp = timestamp,
            visibility = visibility,
            isPublished = isPublished,
            isFeatured = isFeatured,
            location = location,
            imageUrl = imageUrl,
            videoUrl = videoUrl,
            stats = stats,
            socialInteraction = socialInteraction,
            contentDetails = contentDetails,
            engagement = engagement,
            optionalFeatures = optionalFeatures,
            postType = postType
        )
    }

    fun generateRandomUser(): User {
        val random = Random()
        return User(
            id = random.nextLong().toString(),
            username = "User${random.nextInt(100)}",
            fullName = "Random User ${random.nextInt(100)}",
            profilePictureUrl = "https://example.com/profile-picture-${random.nextInt(10)}.jpg"
        )
    }

    fun generateRandomReactions(): Map<Reaction, Int> {
        val random = Random()
        return Reaction.values().associateWith { random.nextInt(20) }
    }

    fun generateRandomFile(): File {
        val random = Random()
        return File(
            id = random.nextLong(),
            name = "Random File ${random.nextInt(10)}",
            size = random.nextLong(),
            type = FileType.values().random()
        )
    }

    fun generateRandomReview(): Review {
        val random = Random()
        return Review(
            user = generateRandomUser(),
            rating = random.nextDouble() * 5,
            comment = "Random review comment ${random.nextInt(100)}"
        )
    }

    fun generateRandomChallenge(): Challenge {
        val random = Random()
        return Challenge(
            name = "Random Challenge ${random.nextInt(10)}",
            description = "Description for Challenge ${random.nextInt(10)}",
            reward = "Reward for Challenge ${random.nextInt(10)}"
        )
    }

    fun generateRandomAchievement(): Achievement {
        val random = Random()
        return Achievement(
            name = "Random Achievement ${random.nextInt(10)}",
            description = "Description for Achievement ${random.nextInt(10)}"
        )
    }

    fun generateRandomDonation(): Donation {
        val random = Random()
        return Donation(
            donor = generateRandomUser(),
            amount = random.nextDouble() * 100
        )
    }

    fun generateRandomSponsor(): Sponsor {
        val random = Random()
        return Sponsor(
            sponsorName = "Random Sponsor ${random.nextInt(10)}",
            sponsorLogoUrl = "https://example.com/sponsor-logo-${random.nextInt(10)}.png"
        )
    }

    fun generateRandomPoll(): Poll {
        val random = Random()
        return Poll(
            question = "Random Poll Question ${random.nextInt(10)}",
            options = List(random.nextInt(5)) { "Option ${random.nextInt(10)}" },
            endDate = System.currentTimeMillis() + (random.nextInt(30) * 24 * 60 * 60 * 1000)
        )
    }

    fun generateRandomEvent(): Event {
        val random = Random()
        return Event(
            name = "Random Event ${random.nextInt(10)}",
            location = "Location for Event ${random.nextInt(10)}",
            date = System.currentTimeMillis() + (random.nextInt(365) * 24 * 60 * 60 * 1000)
        )
    }

    fun generateRandomProduct(): Product {
        val random = Random()
        return Product(
            name = "Random Product ${random.nextInt(10)}",
            description = "Description for Product ${random.nextInt(10)}",
            price = random.nextDouble() * 100
        )
    }

    fun generateRandomSentimentAnalysis(): SentimentAnalysis {
        val random = Random()
        val sentimentLabels = arrayOf("Positive", "Neutral", "Negative")
        return SentimentAnalysis(
            sentimentScore = random.nextDouble() * 2 - 1,
            sentimentLabel = sentimentLabels[random.nextInt(3)]
        )
    }

    fun generateRandomEmbeddedContent(): EmbeddedContent {
        val random = Random()
        return EmbeddedContent(
            type = ContentType.values().random(),
            url = "https://example.com/embedded-content-${random.nextInt(10)}"
        )
    }

    fun generateRandomCustomFields(): Map<String, String> {
        val random = Random()
        val customFields = mutableMapOf<String, String>()
        val numFields = random.nextInt(5)
        repeat(numFields) {
            customFields["Field${it + 1}"] = "Value${random.nextInt(10)}"
        }
        return customFields
    }

    fun generateRandomParagraph(minWords: Int = 20, maxWords: Int = 200): String {
        val random = Random()
        val wordList = listOf(
            "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit",
            "sed", "do", "eiusmod", "tempor", "incididunt", "ut", "labore", "et", "dolore",
            "magna", "aliqua", "Ut", "enim", "ad", "minim", "veniam", "quis", "nostrud",
            "exercitation", "ullamco", "laboris", "nisi", "ut", "aliquip", "ex", "ea", "commodo",
            "consequat", "Duis", "aute", "irure", "dolor", "in", "reprehenderit", "in", "voluptate",
            "velit", "esse", "cillum", "dolore", "eu", "fugiat", "nulla", "pariatur", "Excepteur",
            "sint", "occaecat", "cupidatat", "non", "proident", "sunt", "in", "culpa", "qui",
            "officia", "deserunt", "mollit", "anim", "id", "est", "laborum"
        )

        val paragraph = StringBuilder()
        val paragraphLength = random.nextInt(maxWords - minWords + 1) + minWords

        for (i in 1..paragraphLength) {
            val randomWord = wordList[random.nextInt(wordList.size)]
            paragraph.append(randomWord)

            if (i < paragraphLength) {
                paragraph.append(" ")
            } else {
                paragraph.append(". ")
            }
        }

        return paragraph.toString()
    }
}