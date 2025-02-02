package org.example.datasegment;

import java.util.Objects;

public class Data {
    private String type;
    private String id;
    private String value;
    private Integer scope;

    public Data(String type, String id, String value, Integer scope) {
        this.type = type;
        this.id = id;
        this.value = value;
        this.scope = scope;
    }

    public Data(String type, String id, Integer scope) {
        this.type = type;
        this.id = id;
        this.scope = scope;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Objects.equals(type, data.type) && Objects.equals(id, data.id) && Objects.equals(value, data.value) && Objects.equals(scope, data.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id, value, scope);
    }

    @Override
    public String toString() {
        //String r = id+scope.toString()+": ";
        String r = id+": ";
        if(type.equals("int")) r+=".word" + (value == null ? "" : " " + value);
        else if(type.equals("float")) r+=".float" + (value == null ? "" : " " + value);
        else if(type.equals("char")) r+=".byte" + (value == null ? "" : " " + value);
        else if(type.equals("bool")) r+=".bool" + (value == null ? "" : " " + value);
        else r+=".asciiz" + (value == null ? "" : " " + value);
        return r;
    }
}
