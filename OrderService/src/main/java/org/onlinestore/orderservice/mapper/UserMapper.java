package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.entity.User;

import java.util.List;

/**
 * Mapper для преобразования сущности {@link User} в DTO {@link UserResponse} и обратно.
 * <p>
 * Используется для маппинга данных пользователя между слоями persistence и DTO.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Преобразует сущность пользователя {@link User} в DTO {@link UserResponse}.
     *
     * @param user сущность пользователя
     * @return DTO пользователя
     */
    UserResponse userToUserResponse(User user);

    /**
     * Преобразует список сущностей пользователей в список DTO пользователей.
     *
     * @param users список сущностей пользователей
     * @return список DTO пользователей
     */
    List<UserResponse> usersToUserResponses(List<User> users);
}