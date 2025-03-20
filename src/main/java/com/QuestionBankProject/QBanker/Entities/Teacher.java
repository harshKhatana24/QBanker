package com.QuestionBankProject.QBanker.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher implements UserDetails {

    @Id
    private String Id;
    @Column(name = "teacher_name", nullable = false)
    private String name;
    @Column(name = "teacher_email", nullable = false)
    private String email;
    @Column(name = "teacher_password",nullable = false)
    private String password;

    private boolean enabled=true;
    private boolean emailVerified=false;

    private String picture;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    //FetchType.LAZY --> jab tak contacts ki zrurat nhi tab tak fetch nhi karenge database se (Query)
    //cascade  type matlab agar user delete ho tu uske sare contacts delete ho jaye
    //When orphanRemoval is set to true, it automatically removes child entities that are no longer referenced by the parent entity.
    private List<QuestionBank> contacts = new ArrayList<>();



    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList=new ArrayList<>();

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }








    //User Details interface ke methods jinki body dene hai

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //list of roles
        //convert kiya to List of SimpleGrantedAuthority
        List<SimpleGrantedAuthority> roles=roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        return roles; //jab role aayega user ke pass tab kam ka hai
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }











    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public List<QuestionBank> getContacts() {
        return contacts;
    }

    public void setContacts(List<QuestionBank> contacts) {
        this.contacts = contacts;
    }
}
