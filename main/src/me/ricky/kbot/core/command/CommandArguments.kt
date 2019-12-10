/**
 * Most of this was copy and pasted from my old bot I was working on
 */

package me.ricky.kbot.core.command

typealias ArgumentTransformer<T> = (index: Int) -> T
typealias ContinuesArgumentTransformer<T> = ArgumentTransformer<ContinuesArgument<T>>

/**
 * @param value the return value of the argument
 * @param endIndex the index where the argument ended
 */
data class ContinuesArgument<T>(val value: T, val endIndex: Int)

/**
 * Shorthand for context.argument(context::requireString)
 *
 * @see CommandContext.argument
 */
fun CommandArgumentsHandler.requireString(): String {
  return context.argument(context::requireString)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireString)
 *
 * @see CommandContext.optionalArgument
 */
fun CommandArgumentsHandler.optionalString(default: String): String {
  return context.optionalArgument(default, context::requireString)
}

/**
 * @param index the index of the argument
 *
 * @return a [String] of the argument at [index]
 */
fun CommandContext.requireString(index: Int): String {
  return args[index]
}

/**
 * Shorthand for context.argument(context::requireInt)
 *
 * @see CommandContext.argument
 */
fun CommandArgumentsHandler.requireInt(): Int {
  return context.argument(context::requireInt)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireInt)
 *
 * @see CommandContext.optionalArgument
 */
fun CommandArgumentsHandler.optionalInt(default: Int): Int {
  return context.optionalArgument(default, context::requireInt)
}

/**
 * @param index the index of the argument
 *
 * @return Am [Int] of the argument at [index]
 *
 * @throws CommandException if value is not an [Int]
 */
fun CommandContext.requireInt(index: Int): Int {
  val value = args[index]

  return value.toIntOrNull() ?: throw exceptions.parseException(value, "Int")
}

/**
 * Shorthand for context.argument(context::requireLong)
 *
 * @see CommandContext.argument
 */
fun CommandArgumentsHandler.requireLong(): Long {
  return context.argument(context::requireLong)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireLong)
 *
 * @see CommandContext.optionalArgument
 */
fun CommandArgumentsHandler.optionalLong(default: Long): Long {
  return context.optionalArgument(default, context::requireLong)
}

/**
 * @param index the index of the argument
 *
 * @return a [Long] of the argument at [index]
 *
 * @throws CommandException if value is not a [Long]
 */
fun CommandContext.requireLong(index: Int): Long {
  val value = args[index]

  return value.toLongOrNull() ?: throw exceptions.parseException(value, "Long")
}

/**
 * Shorthand for context.argument(context::requireFloat)
 *
 * @see CommandContext.argument
 */
fun CommandArgumentsHandler.requireFloat(): Float {
  return context.argument(context::requireFloat)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireFloat)
 *
 * @see CommandContext.optionalArgument
 */
fun CommandArgumentsHandler.optionalFloat(default: Float): Float {
  return context.optionalArgument(default, context::requireFloat)
}

/**
 * @param index the index of the argument
 *
 * @return a [Float] of the argument at [index]
 *
 * @throws CommandException if value is not a [Float]
 */
fun CommandContext.requireFloat(index: Int): Float {
  val value = args[index]

  return value.toFloatOrNull() ?: throw exceptions.parseException(value, "Float")
}

/**
 * Shorthand for context.argument(context::requireDouble)
 *
 * @see CommandContext.argument
 */
fun CommandArgumentsHandler.requireDouble(): Double {
  return context.argument(context::requireDouble)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireDouble)
 *
 * @see CommandContext.optionalArgument
 */
fun CommandArgumentsHandler.optionalDouble(default: Double): Double {
  return context.optionalArgument(default, context::requireDouble)
}

/**
 * @param index the index of the argument
 *
 * @return a [Double] of the argument at [index]
 *
 * @throws CommandException if value is not a [Double]
 */
fun CommandContext.requireDouble(index: Int): Double {
  val value = args[index]

  return value.toDoubleOrNull() ?: throw exceptions.parseException(value, "Double")
}

/**
 * Shorthand for context.argument(context::requireBoolean)
 *
 * @see CommandContext.argument
 */
fun CommandArgumentsHandler.requireBoolean(): Boolean {
  return context.argument(context::requireBoolean)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireBoolean)
 *
 * @see CommandContext.optionalArgument
 */
fun CommandArgumentsHandler.optionalBoolean(default: Boolean): Boolean {
  return context.optionalArgument(default, context::requireBoolean)
}

/**
 * @param index the index of the argument
 *
 * @return a [Boolean] of the argument at [index]
 *
 * @throws CommandException if value is not a [Boolean]
 */
fun CommandContext.requireBoolean(index: Int): Boolean {
  val value = args[index]

  return when {
    value.equals("true", true) -> true
    value.equals("false", true) -> false
    else -> throw throw exceptions.parseException(value, "Boolean")
  }
}

/**
 * Shorthand for context.argument(context::requireSafeBoolean)
 *
 * @see CommandContext.argument
 */
fun CommandArgumentsHandler.requireSafeBoolean(): Boolean {
  return context.argument(context::requireSafeBoolean)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireSafeBoolean)
 *
 * @see CommandContext.optionalArgument
 */
fun CommandArgumentsHandler.optionalSafeBoolean(default: Boolean): Boolean {
  return context.optionalArgument(default, context::requireSafeBoolean)
}

/**
 * @param index the index of the argument
 *
 * @return a [Boolean] of the argument at [index], false if value is not a boolean/
 */
fun CommandContext.requireSafeBoolean(index: Int): Boolean {
  return args[index].toBoolean()
}

/**
 * Shorthand for context.argument(context::requireQuatedText)
 *
 * @see CommandContext.argument
 */
fun CommandArgumentsHandler.requireQuotedText(): String {
  return context.argument(context::requireQuotedText)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireQuotedText)
 *
 * @see CommandContext.optionalArgument
 */
fun CommandArgumentsHandler.optionalQuotedText(default: String): String {
  return context.optionalArgument(default, context::requireQuotedText)
}

/**
 * @param index the index of the argument
 *
 * @return a [String] of the argument starting at [index]
 *
 * @throws CommandException if value is not a "Quoted [String]"
 */
fun CommandContext.requireQuotedText(index: Int): ContinuesArgument<String> {
  var endIndex = index
  var value: String? = null

  val list = args.subList(index, args.size)
  val last = list.indexOfLast { it.endsWith("\"") }

  if (args[index].startsWith("\"")) {

    if (last > -1) {
      endIndex = last + 1
      value = args.subList(index, endIndex + 1).joinToString(" ")
        .removeSuffix("\"")
        .removePrefix("\"")
    }

  }

  value ?: throw exceptions.parseException(list.joinToString(" "), "Quoted Text")

  return ContinuesArgument(value, endIndex)
}

/**
 * Shorthand for context.argument(context::requireText)
 *
 * @see CommandContext.argument
 */
fun CommandArgumentsHandler.requireText(): String {
  return context.argument(context::requireText)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireText)
 *
 * @see CommandContext.optionalArgument
 */
fun CommandArgumentsHandler.optionalText(default: String): String {
  return context.optionalArgument(default, context::requireText)
}

/**
 * @param index the index of the argument
 *
 * @return a [String] of the argument starting at [index]
 */
fun CommandContext.requireText(index: Int): ContinuesArgument<String> {
  return ContinuesArgument(
    value = args.subList(index, args.size).joinToString(" "),
    endIndex = args.lastIndex
  )
}

/**
 * Shorthand for context.argument(context::requireList)
 *
 * @see CommandContext.argument
 */
fun CommandArgumentsHandler.requireList(): List<String> {
  return context.argument(context::requireList)
}

/**
 * Shorthand for context.optionalArgument([default], context::requireList)
 *
 * @see CommandContext.optionalArgument
 */
fun CommandArgumentsHandler.optionalList(default: List<String>): List<String> {
  return context.optionalArgument(default, context::requireList)
}

/**
 * @param index the index of the argument
 *
 * @return a [List] of the arguments starting at [index]
 */
fun CommandContext.requireList(index: Int): ContinuesArgument<List<String>> {
  return ContinuesArgument(
    value = args.subList(index, args.size),
    endIndex = args.lastIndex
  )
}

/**
 * @param transform A function reference to transform arguments to [T]
 * @param throwTooManyArgs if true, throws [CommandExceptionHandler.checkTooManyArguments] if there is too many arguments
 *
 * @return the return value of [transform]
 *
 * @throws CommandExceptionHandler.checkNotEnoughArguments if there is not enough arguments
 * @throws CommandExceptionHandler.checkTooManyArguments if [throwTooManyArgs] is true, will throw if there is too many arguments
 */
inline fun <C : CommandContext, T> C.argument(
  transform: ArgumentTransformer<T>,
  throwTooManyArgs: Boolean = false
): T {
  return argument(arguments.argPos++, transform, throwTooManyArgs)
}

/**
 * @param transform a function reference to transform arguments to [T]
 * @param throwTooManyArgs if true, throws [CommandExceptionHandler.checkTooManyArguments] if there is too many arguments
 *
 * @return the return value of [transform]
 *
 * @throws CommandExceptionHandler.checkNotEnoughArguments if there is not enough arguments
 * @throws CommandExceptionHandler.checkTooManyArguments if [throwTooManyArgs] is true, will throw if there is too many arguments
 */
@JvmName("continuesArgument")
inline fun <C :CommandContext, T> C.argument(
  transform: ContinuesArgumentTransformer<T>,
  throwTooManyArgs: Boolean = false
): T {
  val (value, end) = argument(arguments.argPos, transform, throwTooManyArgs)

  arguments.argPos = end + 1

  return value
}

/**
 * @param default the default value
 * @param transform a function reference to transform arguments to [T]
 * @param throwTooManyArgs if true, throws [CommandExceptionHandler.checkTooManyArguments] if there is too many arguments
 *
 * @return the return value of [transform] or [default] if there isn't enough arguments
 *
 * @throws CommandExceptionHandler.checkTooManyArguments if [throwTooManyArgs] is true, will throw if there is too many arguments
 */
inline fun <C : CommandContext, T> C.optionalArgument(
  default: T,
  transform: ArgumentTransformer<T>,
  throwTooManyArgs: Boolean = false
): T {
  return optionalArgument(arguments.argPos++, default, transform, throwTooManyArgs)
}

/**
 * @param default the default value
 * @param transform a function reference to transform arguments to [T]
 * @param throwTooManyArgs if true, throws [CommandExceptionHandler.checkTooManyArguments] if there is too many arguments
 *
 * @return the return value of [transform] or [default] if there isn't enough arguments
 *
 * @throws CommandExceptionHandler.checkTooManyArguments if [throwTooManyArgs] is true, will throw if there is too many arguments
 */
@JvmName("optionalContinuesArgument")
inline fun <C : CommandContext, T> C.optionalArgument(
  default: T,
  transform: ContinuesArgumentTransformer<T>,
  throwTooManyArgs: Boolean = false
): T {
  val (value, end) = optionalArgument(arguments.argPos, default, transform, throwTooManyArgs)

  arguments.argPos = end + 1

  return value
}

/**
 * @param index the index of the argument
 * @param transform a function reference to transform arguments to [T]
 * @param throwTooManyArgs if true, throws [CommandExceptionHandler.checkTooManyArguments] if there is too many arguments
 *
 * @return the return value of [transform]
 *
 * @throws CommandExceptionHandler.checkNotEnoughArguments if there is not enough arguments
 * @throws CommandExceptionHandler.checkTooManyArguments if [throwTooManyArgs] is true, will throw if there is too many arguments
 */
inline fun <C : CommandContext, T> C.argument(
  index: Int,
  transform: ArgumentTransformer<T>,
  throwTooManyArgs: Boolean = false
): T {
  exceptions.checkNotEnoughArguments(index)

  if (throwTooManyArgs) {
    exceptions.checkTooManyArguments(index)
  }

  return transform(index)
}

/**
 * @param index the index of the argument
 * @param transform a function reference to transform arguments to [T]
 * @param throwTooManyArgs if true, throws [CommandExceptionHandler.checkTooManyArguments] if there is too many arguments
 *
 * @return the return value of [transform]
 *
 * @throws CommandExceptionHandler.checkNotEnoughArguments if there is not enough arguments
 * @throws CommandExceptionHandler.checkTooManyArguments if [throwTooManyArgs] is true, will throw if there is too many arguments
 */
inline fun <C : CommandContext, T> C.argument(
  index: Int,
  transform: ContinuesArgumentTransformer<T>,
  throwTooManyArgs: Boolean = false
): ContinuesArgument<T> {
  exceptions.checkNotEnoughArguments(index)

  val value = transform(index)

  if (throwTooManyArgs) {
    exceptions.checkTooManyArguments(value.endIndex)
  }

  return value
}

/**
 * @param index the index of the argument
 * @param default the default value
 * @param transform a function reference to transform arguments to [T]
 * @param throwTooManyArgs if true, throws [CommandExceptionHandler.checkTooManyArguments] if there is too many arguments
 *
 * @return the return value of [transform] or [default] if there isn't enough arguments
 *
 * @throws CommandExceptionHandler.checkTooManyArguments if [throwTooManyArgs] is true, will throw if there is too many arguments
 */
inline fun <C : CommandContext, T> C.optionalArgument(
  index: Int,
  default: T,
  transform: ArgumentTransformer<T>,
  throwTooManyArgs: Boolean = false
): T {
  args.getOrNull(index) ?: return default

  return argument(index, transform, throwTooManyArgs)
}

/**
 * @param index the index of the argument
 * @param default the default value
 * @param transform a function reference to transform arguments to [T]
 * @param throwTooManyArgs if true, throws [CommandExceptionHandler.checkTooManyArguments] if there is too many arguments
 *
 * @return the return value of [transform] or [default] if there isn't enough arguments
 *
 * @throws CommandExceptionHandler.checkTooManyArguments if [throwTooManyArgs] is true, will throw if there is too many arguments
 */
inline fun <C : CommandContext, T> C.optionalArgument(
  index: Int,
  default: T,
  transform: ContinuesArgumentTransformer<T>,
  throwTooManyArgs: Boolean = false
): ContinuesArgument<T> {
  args.getOrNull(index) ?: return ContinuesArgument(default, index)

  return argument(index, transform, throwTooManyArgs)
}
