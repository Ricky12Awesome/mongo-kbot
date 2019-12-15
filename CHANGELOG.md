#### 0.0.0
* Command Handlers, MultiCommandHandler, ServerCommandHandler
* Prefix Handler
* Some Utilities

#### 0.0.1
* Added BotConfig Serializable
* Added KBot class to make bot instances
* some other tweaks

#### 0.0.2
* Added Command Arguments, description, permissions to CommandInfo
* Added the start of a command dsl
* Added the start of mongo handler and documents

#### 0.0.3
* Tweaked Dependencies
* Edited README.md

#### 0.0.4
* Implemented a bit of mongo handler
* Changed a few things with ServerDocument
* Added MemberDocument (used with ServerDocument)

#### 0.0.5
* Added CommandException and CommandExceptionHandler
* Added Command Arguments that I mostly copy and pasted from my old bot

#### 0.0.6
* Added a lot of Command Argument stuff

#### 0.0.7
* Moved Server stuff from CommandArguments.kt to ServerCommandArguments.kt
* MultiCommandHandler#commands now gets commands

#### 0.0.8
* Added Sources Jar and Javadoc Jar, now it can be used as a library
(Still new to kind of thing)

#### 0.0.9
* Fixed issue where 0.0.8 wouldn't run if you did not have javacord-core depend
* Added `KBot#register(JavaCordHandler)`

#### 0.0.10
* Aliases and Permissions now work
* Command Info DSL
* Added `executeCommand` to `AbstractCommandHandler`
* Few other tweaks
