package routes

import Message
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

//route content


fun Route.messages() {
    route("/messages") {
        createMessage()
        getMessages()
        getMessage()
        //maybe make it able to fetch by content too? ie get({content})


    }
}

private fun Route.createMessage() {
    post {
        val parameters = call.receiveParameters()
        val messageText = parameters["text"] ?: return@post call.respondText(
            "No text parameter for message", status = HttpStatusCode.BadRequest
        )
        val message = Message(messageText)
        MessagesDb.addMessage(message)
        call.respondText("Message created with id ${message.id}", status = HttpStatusCode.Created)
    }
}

private fun Route.getMessages() {
    get {
        if (MessagesDb.getMessages().isEmpty()) call.respondText(
            "No messages found", status = HttpStatusCode.NotFound
        )
        else call.respondText("Messages : " + MessagesDb.getMessages().toString(), status = HttpStatusCode.Found)

    }
}

private fun Route.getMessage() {
    get("{id}") {
        println("GETTING MESSAGE")
        val idNumber = call.parameters["id"]!!.toIntOrNull() ?: return@get call.respondText(
            //we know that parameter id will never be null as this route happens only when it gets past, therefore the '!!'
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )
        println("MESSAGE ID IS $idNumber")
        val message = MessagesDb.getMessages().getOrNull(idNumber)?.toString() ?: return@get call.respondText(
            "No message with id $idNumber", status = HttpStatusCode.NotFound
        )
        println("MESSAGE IS ${message.toString()}")
        call.respondText(message, status = HttpStatusCode.Found)
    }
}