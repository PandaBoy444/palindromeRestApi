<h1 align="center">
    <img src="https://user-images.githubusercontent.com/8178413/159716209-0f91e643-2b80-428a-a5f1-c7e8c7aea5d2.svg" alt="Logo" width="125" height="125">
</h1>
<div align="center">
  A REST API that manages messages and preforms basic checks/operations on them
  <br />
  <br />
  <a href="https://github.com/PandaBoy444/palindromeRestApi/issues/new?assignees=&labels=bug">Report a Bug</a>
  ·
  <a href="https://github.com/PandaBoy444/palindromeRestApi/issues/new?assignees=&labels=enhancement">Request a Feature</a>
  .
  <a href="https://github.com/PandaBoy444/palindromeRestApi/discussions">Ask a Question</a>
</div>  

 ---
[![Heroku](https://pyheroku-badge.herokuapp.com/?app=palindrome-rest-api)](https://palindrome-rest-api.herokuapp.com)
[![MIT](https://badgen.net/github/license/PandaBoy444/palindromeRestApi)](https://opensource.org/licenses/MIT)
[![Kotlin style](https://badgen.net/badge/code%20style/kotlin-official/f2a)](https://kotlinlang.org/docs/coding-conventions.html)
[![checks](https://badgen.net/github/checks/PandaBoy444/palindromeRestApi)](https://github.com/PandaBoy444/palindromeRestApi/actions)
[![issues](https://badgen.net/github/open-issues/PandaBoy444/palindromeRestApi)](https://github.com/PandaBoy444/palindromeRestApi/issues)
[![kotlin](https://img.shields.io/badge/build%20with-kotlin-purple)](https://kotlinlang.org/)
[![carrots](https://img.shields.io/badge/favorite%20snack-carrots-orange)](https://rr.noordstar.me/bcd3d25d)


- Built on kotlin and ktor
- [Deployed on Heroku](https://palindrome-rest-api.herokuapp.com/) ~Google cloud~
# Architecture doodle
![image](https://user-images.githubusercontent.com/8178413/159892417-4b00d541-44af-4511-b573-3866605bd89c.png)

The server is built in a way that is independent from palindrome checking, instead palindrome checking is added via a mini dsl like so
```kotlin
fun Application.module() {
    //app logic here
    configureLogicFields { //add any function you would like to evaluate as logic field
        add(::palindrome)
    }
}
```
Then we we query ``v1/messages/{id}/logicfields/palindrome`` we get the output of said function on the given message (in this case true or false).  
Adding additional logic is as simple as referancing a function
```kotlin
fun Application.module() {
    //app logic here
    configureLogicFields { //add any function you would like to evaluate as logic field
        add(::palindrome)
        add(::messageSize)
    }
}
fun messageSize(message: Message): String {
    return message.text.length.toString()
}
```
Now ``v1/messages/{id}/logicfields/messagesize`` will give us the length of the message's text content
# Get started
## Run the server
After cloning, use `gradle run` to run the project locally  
By default the project will use an embedded database when launched, so you don't need to have anything else setup. If you do have a database, please define environment variables `db=true` and `JDBC_DATABASE_URL={your database url}`   
example environment
```
    db=true;JDBC_DATABASE_URL=jdbc:postgresql:messages?user=postgres&password=palindrome
```  
The tests are all under `gradle test`  
When committing, the tests will also run on the cloud and if they pass the code will be deployed on heroku
## Use the live server
You can visit and query the API at [palindrome-rest-api.herokuapp.com](https://palindrome-rest-api.herokuapp.com)
## Use demo client
You can download the client from https://github.com/PandaBoy444/palindromeClient/releases, it is reacommended to use a terminal that supports colors like the [windows terminal](https://github.com/microsoft/terminal)
## API reference
| Action                             | Method | Endpoint                                | Arguments                       |
|------------------------------------|--------|-----------------------------------------|---------------------------------|
| Create message                     | POST   | v1/messages                             | text={Text for new message}     |
| Delete message                     | DELETE | v1/messages/{id}                        |                                 |
| Update message                     | PATCH  | v1/messages/{id}                        | text={New text for message}     |
| Replace (or create) message        | PUT    | v1/messages/{id}                        | text={Text for new message}     |
| Retrieve all messages              | GET    | v1/messages                             |                                 |
| Retrieve message                   | GET    | v1/messages/{id}                        |                                 |
| Retrieve field from message        | GET    | v1/messages/{id}/{field}                |                                 |
| Retrieve logic fields from message | GET    | v1/messages/{id}/logicfields            |                                 |
| Retrieve logic field from message  | GET    | v1/messages/{id}/logicfields/{field}    |                                 |
| Check if a message is palindrome   | GET    | v1/messages/{id}/logicfields/palindrome |                                 |
