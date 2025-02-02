package org.example.datasegment;

import org.example.node.ASTNode;
import org.example.node.LiteralNode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DataSegment {
    private Set<Data> dataSeg;

    public DataSegment() {
        dataSeg = new HashSet<>();
    }

    public void addData(String type, String id, Integer scope) {
        dataSeg.add(new Data(type, id, (type.equals("string")? "\0" : "0"), scope));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DataSegment that = (DataSegment) o;
        return Objects.equals(dataSeg, that.dataSeg);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dataSeg);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Data data : dataSeg) {
            builder.append(data.toString() + "\n");
        }return builder.toString();
    }

}
