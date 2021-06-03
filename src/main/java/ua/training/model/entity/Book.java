package ua.training.model.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Book {
    private long id;
    private String title;
    private List<Author> authors;
    private String description;
    private String language;
    private Edition edition;
    private LocalDate publicationDate;
    private float price;
    private int count;

    public static class Builder {
        private long id;
        private String title;
        private List<Author> authors;
        private String description;
        private String language;
        private Edition edition;
        private LocalDate publicationDate;
        private float price;
        private int count;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder authors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder edition(Edition edition) {
            this.edition = edition;
            return this;
        }

        public Builder publicationDate(LocalDate publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }

        public Builder price(float price) {
            this.price = price;
            return this;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    public Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.authors = builder.authors;
        this.description = builder.description;
        this.language = builder.language;
        this.edition = builder.edition;
        this.publicationDate = builder.publicationDate;
        this.price = builder.price;
        this.count = builder.count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getId() == book.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
