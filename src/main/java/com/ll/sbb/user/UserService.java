package com.ll.sbb.user;


import com.ll.sbb.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        try {
            userRepository.save(user);
        } catch (Exception e) {
            if (userRepository.existsByUsername(username)) {
                throw new SignupUsernameDuplicatedException("이미 사용중인 username 입니다.");
            } else {
                throw new SignupEmailDuplicatedException("이미 사용중인 email 입니다.");
            }
        }
        return user;
    }

    public SiteUser getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new DataNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
