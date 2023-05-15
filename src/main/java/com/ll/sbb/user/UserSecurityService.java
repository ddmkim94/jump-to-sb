package com.ll.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 시큐리티가 특정 회원의 username을 받았을 때
     * username에 해당하는 회원 정보를 얻는 메서드
     * username은 시큐리티 설정 파일에서 변경할 수 있다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username = 로그인 폼에서 작성한 username 값
        SiteUser siteUser = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 권한들을 담을 리스트
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if (siteUser.getUsername().equals("admin")) {
            authorityList.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorityList.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new User(siteUser.getUsername(), siteUser.getPassword(), authorityList);
    }
}
