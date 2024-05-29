package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "deadline", nullable = false)
    private Date deadline;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;

    @ManyToMany(mappedBy = "tournaments")
    private Set<User> users = new HashSet<>();

    @PreRemove
    public void removeUsers(){
        for(User user : this.users)
            user.getTournaments().remove(this);
    }
}
