package org.example.lexer;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a data segment that stores a collection of Data objects.
 */
public class DataSegment {
    private final Set<Data> dataSegment; // Set to store Data objects

    /**
     * Constructs a new DataSegment object.
     */
    public DataSegment() {
        dataSegment = new HashSet<>(); // Use HashSet as the concrete implementation
    }

    /**
     * Adds a new Data object to the data segment.
     *
     * @param id   The identifier of the data.
     * @param size The size of the data.
     */
    public void addData(String id, String size) {
        dataSegment.add(new Data(id, size));
    }

    /**
     * Returns a string representation of the data segment.
     *
     * @return A string containing all Data objects in the segment, one per line.
     */
    @Override
    public String toString() {
        if (dataSegment.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Data data : dataSegment) {
            builder.append(data.toString()).append("\n");
        }
        return builder.toString();
    }
}