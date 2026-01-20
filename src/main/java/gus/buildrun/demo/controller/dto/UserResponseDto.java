package gus.buildrun.demo.controller.dto;

import gus.buildrun.demo.entity.Account;

import java.util.List;

public record UserResponseDto(
        String userId,
        String username,
        String email,
        List<Account> accounts) {
}
