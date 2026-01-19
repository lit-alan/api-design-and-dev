/**
 * Represents an immutable entity for an Author.
 * <p>
 * The use of a {@code record} reduces boilerplate by automatically generating
 * constructor, getters, {@code equals()}, {@code hashCode()}, and {@code toString()}.
 * It ensures immutability, clarity, and conciseness, making it ideal for simple
 * data storage without additional behavior.
 * </p>
 * This record encapsulates basic details about an author, including their
 * unique ID, name, and year of birth.
 * </p>
 *
 * @param authorID   the unique identifier for the author
 * @param firstName  the first name of the author
 * @param lastName   the last name of the author
 * @param yearBorn   the year the author was born
 */

public record Author(long authorID, String firstName, String lastName, int yearBorn) { }