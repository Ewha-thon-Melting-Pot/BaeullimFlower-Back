package com.meltingpot.baeullimflower.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member implements UserDetails { // UserDetails 상속받아 인증 객체로 사용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long memberId;

    @Column(nullable = false, length = 16)
    private String name;

    @Column(name = "stu_num", nullable = false, unique = true , length = 16)
    private String studentNum;

    @Enumerated(EnumType.STRING)
    private College college;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String name, String studentNum, College college, String password, Role role) {
        this.name = name;
        this.studentNum = studentNum;
        this.college = college;
        this.password = password;
        this.role = role;
    }

    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + getRole().toString()));

        return authorities;
    }

    //사용자의 id 반환(고유한 값)
    @Override
    public String getUsername() {
        return getStudentNum();
    }

    //사용자의 패스워드 반환
    @Override
    public String getPassword(){
        return getPassword();
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        //만료되었는지 확인하는 로직
        return true; //true: 만료되지 않음
    }

    //계정 장금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        //계정이 잠겨있는지 확인하는 로직
        return true; //true: 잠겨있지 않음
    }

    //패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        //패스워드가 만료되었는지 확인하는 로직
        return true; //true: 만료되지 않음
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true; //true: 사용 가능
    }
}
