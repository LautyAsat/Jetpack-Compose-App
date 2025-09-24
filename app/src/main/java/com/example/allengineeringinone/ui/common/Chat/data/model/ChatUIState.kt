package com.example.allengineeringinone.ui.common.Chat.data.model

data class ChatUIState(
    val isChatOpen: Boolean = false,
    val currentText: String = "",
    val listMessage: List<Message> = listOf(
        Message(
            text = "Hello! How can I help you today?",
            sender = Sender.BOT,
            timestamp = System.currentTimeMillis() - 100000 // 100 seconds ago
        ),
//        Message(
//            text = "I'm looking for information on civil engineering.",
//            sender = Sender.USER,
//            timestamp = System.currentTimeMillis() - 80000 // 80 seconds ago
//        ),
//        Message(
//            text = "Sure, I can help with that. What specific area are you interested in? Structures, transportation, geotechnical?",
//            sender = Sender.BOT,
//            timestamp = System.currentTimeMillis() - 60000 // 60 seconds ago
//        ),
//        Message(
//            text = "Let's start with structures.",
//            sender = Sender.USER,
//            timestamp = System.currentTimeMillis() - 40000 // 40 seconds ago
//        ),
//        Message(
//            text = "Great! Structural engineering involves the design and analysis of buildings, bridges, and other structures to ensure they are safe and can withstand various loads.",
//            sender = Sender.BOT,
//            timestamp = System.currentTimeMillis() - 20000 // 20 seconds ago
//        ),
//        Message(
//            text = "What are some common materials used in structural engineering?",
//            sender = Sender.USER,
//            timestamp = System.currentTimeMillis() - 15001 // 15 seconds ago
//        ),
//        Message(
//            text = "Common materials include steel, concrete, timber, and masonry. Each has its own strengths and weaknesses depending on the application.",
//            sender = Sender.BOT,
//            timestamp = System.currentTimeMillis() - 10001 // 10 seconds ago
//        ),
//        Message(
//            text = "Interesting. Can you tell me more about steel structures?",
//            sender = Sender.USER,
//            timestamp = System.currentTimeMillis() - 20001 // 20 seconds ago
//        )
    )
)
