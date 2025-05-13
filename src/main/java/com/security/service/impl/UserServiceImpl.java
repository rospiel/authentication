package com.security.service.impl;

import com.security.DTO.UserRequestDTO;
import com.security.entity.User;
import com.security.mapper.UserMapper;
import com.security.repository.GroupRepository;
import com.security.repository.UserRepository;
import com.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void save(UserRequestDTO request) {
        groupRepository.findByName(request.getGroupName()).ifPresentOrElse(
                (group) -> {
                    userRepository.findByEmail(request.getEmail()).ifPresentOrElse(
                            (user) -> {
                                throw new RuntimeException("Already exist a user with this mail");
                            },
                            () -> {
                                User user = userMapper.toProductEntityBy(request);
                                user.setGroups(Collections.singletonList(group));
                                userRepository.save(user);
                            }
                    );

                },
                () -> {
                    throw new RuntimeException("Group name unknown");
                }

        );
    }

    @Override
    public Optional<User> searchBy(String email) {
        return userRepository.findByEmail(email);
    }
}
