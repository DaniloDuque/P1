package org.example.lexer;

import java.util.Objects;

/**
 * Represents a data entry with an identifier and a size.
 * This class is immutable and implements Comparable for sorting based on the identifier.
 */
public class Data implements Comparable<Data> {
    private final String id;   // Identifier
    private final String size; // Size

    /**
     * Constructs a new Data object.
     *
     * @param id   The identifier (must not be null).
     * @param size The size (must not be null).
     * @throws IllegalArgumentException if id or size is null.
     */
    public Data(String id, String size) {
        if (id == null || size == null) {
            throw new IllegalArgumentException("id and size must not be null");
        }
        this.id = id;
        this.size = size;
    }

    /**
     * Returns the identifier.
     *
     * @return The identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the size.
     *
     * @return The size.
     */
    public String getSize() {
        return size;
    }

    /**
     * Compares this Data object with another based on the identifier.
     *
     * @param o The other Data object to compare to.
     * @return A negative integer, zero, or a positive integer if this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Data o) {
        if (o == null) {
            throw new NullPointerException("Cannot compare with null");
        }
        return this.id.compareTo(o.id);
    }

    /**
     * Checks if this Data object is equal to another object.
     *
     * @param obj The object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Data other = (Data) obj;
        return this.id.equals(other.id) && this.size.equals(other.size);
    }

    /**
     * Returns a hash code for this Data object.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, size);
    }

    /**
     * Returns a string representation of this Data object.
     *
     * @return A string in the format "id: size".
     */
    @Override
    public String toString() {
        return id + ": " + size;
    }
}