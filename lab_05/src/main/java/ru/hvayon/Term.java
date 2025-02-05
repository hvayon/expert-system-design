package ru.hvayon;

import lombok.Data;

@Data
public class Term {
    private String name;
    private String type;
    private Object value;

    public Term(String name, String type, Object flag) {
        this.name = name;
        this.type = type;

        // если `type` равен `"const"` и `value` не `null`, то `value` присваивается соответствующему полю.
        if ("const".equals(type)) {
            if (flag != null) {
                this.value = flag;
            } else {
                this.value = name;
            }
        } else {
            this.value = null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s:%s", name, type);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}