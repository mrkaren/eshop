package am.itspace.eshop.service.impl;

import am.itspace.eshop.entity.User;
import am.itspace.eshop.repository.UserRepository;
import am.itspace.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        List<User> all = userRepository.findAll();
        log.info("find all users, users list size {}", all.size());
        return all;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
}
