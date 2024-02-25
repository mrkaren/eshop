package am.itspace.eshop.service.impl;

import am.itspace.eshop.entity.User;
import am.itspace.eshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSchedulerService {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 10 * * MON-FRI")
    public void removeNotActiveUsers() {
        List<User> allByActive = userRepository.findAllByActive(false);
        log.info("removeNotActiveUsers scheduler start working. Inactive users size = {}", allByActive.size());
//        for (User user : allByActive) {
//            log.info("user with {} email is not active", user.getEmail());
//            userRepository.delete(user);
//        }
    }

}
