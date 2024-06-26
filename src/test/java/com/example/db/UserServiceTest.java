
/*
 * Licensed to Elasticsearch B.V. under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch B.V. licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.example.db;

import com.example.document.user.LoginDTO;
import com.example.document.user.RegisterDTO;
import com.example.document.user.User;
import com.example.document.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.Objects;

// This test uses test container, therefore the Docker engine needs to be installed to run it
// The testcontainer will take ~30 seconds to start
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(classes = {UserService.class, UserServiceTest.class, ElasticClientTest.class})
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void testCreateUpdateUser() throws IOException {
        RegisterDTO register = new RegisterDTO("user", "mail", "pw");
        User result = service.newUser(register);
        assert (result.username().equals(register.username()));
        assert (result.email().equals(register.email()));
        assert (Objects.nonNull(result.token()));
        String token = "Bearer " + result.token();

        LoginDTO login = new LoginDTO("mail", "pw");
        result = service.authenticateUser(login);
        assert (result.username().equals(register.username()));

        UserDTO update = new UserDTO("new-user", "mail", "", "bio", "image");
        result = service.updateUser(update, token);
        assert (result.username().equals(update.username()));
        assert (result.email().equals(update.email()));
        assert (result.bio().equals(update.bio()));
        assert (result.image().equals(update.image()));
    }
}
