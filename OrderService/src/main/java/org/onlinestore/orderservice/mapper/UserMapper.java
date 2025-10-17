package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    List<UserResponse> usersToUserResponses(List<User> users);
}
