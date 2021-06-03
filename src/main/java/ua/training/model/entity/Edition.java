package ua.training.model.entity;

public class Edition {
    private long id;
    private String name;

    public static class Builder {
        private long id;
        private String name;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Edition build() {
            return new Edition(this);
        }
    }

    private Edition(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
