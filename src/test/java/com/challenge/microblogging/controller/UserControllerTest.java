package com.challenge.microblogging.controller;

import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@AutoConfigureDataMongo
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any())).thenReturn(getUserDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getUserDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(getUserDTO().getId()));
    }

    @Test
    void testGetUserById() throws Exception {
        String userId = "1";
        when(userService.getUserById(userId)).thenReturn(getUserDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(getUserDTO().getId()));
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDTO> users = Arrays.asList(
                getUserDTO(),
                getUserDTO()
        );
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(users.size()));
    }

    @Test
    void testUpdateUser() throws Exception {
        String userId = "1";
        when(userService.updateUser(eq(userId), any())).thenReturn(getUserDTO());

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getUserDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(getUserDTO().getId()));
    }

    @Test
    void testDeleteUser() throws Exception {
        String userId = "1";

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(userService, times(1)).deleteUser(anyString());
    }

    @Test
    void testFollowUser() throws Exception {
        String followerId = "1";
        String followingId = "2";
        when(userService.followUser(eq(followerId), eq(followingId))).thenReturn(getUserDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{followerId}/follow/{followingId}", followerId, followingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(getUserDTO().getId()));
    }

    @Test
    void testUnfollowUser() throws Exception {
        String followerId = "1";
        String followingId = "2";
        when(userService.unfollowUser(eq(followerId), eq(followingId))).thenReturn(getUserDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{followerId}/unfollow/{followingId}", followerId, followingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(getUserDTO().getId()));
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("1");
        userDTO.setUsername("john_doe");
        userDTO.setName("John Doe");
        return userDTO;
    }
}
