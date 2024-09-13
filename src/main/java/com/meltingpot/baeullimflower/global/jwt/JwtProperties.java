package com.meltingpot.baeullimflower.global.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
// .yml 파일에 있는 property를 자바 클래스에 값을 가져와서(바인딩) 사용할 수 있게 해주는 어노테이션
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String issuer;
    private String secretkey;
}
