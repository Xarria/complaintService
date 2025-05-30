package com.recruitment.task.complaint_service.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"productId", "reporter"})
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Complaint {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Version
    @Column(name = "version", nullable = false)
    long version;

    @NotBlank
    @Column(name = "product_id", nullable = false)
    String productId;

    @NotBlank
    @Column(name = "content", nullable = false)
    String content;

    @PastOrPresent
    @Column(name = "creation_date", nullable = false)
    LocalDateTime creationDate;

    @NotBlank
    @Column(name = "reporter", nullable = false)
    String reporter;

    @NotBlank
    @Column(name = "country", nullable = false)
    String country;

    @Positive
    @Column(name = "count", nullable = false)
    int count;

    public static Complaint create(@NotBlank String productId, @NotBlank String content, @NotBlank String reporter,
                                   @NotBlank String country) {
        return Complaint.builder()
                .productId(productId)
                .content(content)
                .reporter(reporter)
                .country(country)
                .count(1)
                .creationDate(LocalDateTime.now())
                .build();
    }

    @Builder
    private Complaint(String productId, String content, String reporter, String country, int count, LocalDateTime creationDate) {
        this.productId = productId;
        this.content = content;
        this.reporter = reporter;
        this.country = country;
        this.count = count;
        this.creationDate = creationDate;
    }

    public void incrementCount() {
        this.count += 1;
    }

    public void updateContent(@NotBlank String content) {
        this.content = content;
    }
}
