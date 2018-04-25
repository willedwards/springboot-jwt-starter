package com.bfwg.rest;

import com.bfwg.config.TimeProvider;
import com.bfwg.config.TokenHelper;
import com.bfwg.converters.DefaultUserDetailsConverter;
import com.bfwg.dto.DefaultUserDetails;
import com.bfwg.entities.UserEntity;
import com.bfwg.service.UserService;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Created by fanjin on 2017-09-01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtRefrestTest {

    private MockMvc mvc;

    @MockBean
    private TimeProvider timeProviderMock;

    private static final String TEST_USERNAME = "testUser";

    @Autowired
    private TokenHelper tokenHelper;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext context;




    @Before
    public void setup() {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("username");
        userEntity.setRole("ROLE_USER");
        userEntity.setLastPasswordResetDate(DateUtil.yesterday().getTime());

        DefaultUserDetails defaultUserDetails = DefaultUserDetailsConverter.from(userEntity);

        when(this.userService.findByUsername(eq("testUser"))).thenReturn(defaultUserDetails);
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 100); // 100 sec
        ReflectionTestUtils.setField(tokenHelper, "MOBILE_EXPIRES_IN", 200); // 200 sec
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "queenvictoria");


    }

    @Test
    public void shouldGetEmptyTokenStateWhenGivenValidOldToken() throws Exception {
        when(timeProviderMock.now())
                .thenReturn(DateUtil.yesterday());
        this.mvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer 123"))
                .andExpect(content().json("{access_token:null,expires_in:null}"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldRefreshNotExpiredWebToken() throws Exception {

        given(timeProviderMock.now())
                .willReturn(new Date(30L));

        String token = createToken();
        String refreshedToken = tokenHelper.refreshToken(token);

        this.mvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer " + token))
                .andExpect(content().json("{access_token:" + refreshedToken + ",expires_in:100}"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldRefreshNotExpiredMobileToken() throws Exception {
        given(timeProviderMock.now())
                .willReturn(new Date(30L));

        String token = createToken();
        String refreshedToken = tokenHelper.refreshToken(token);


        this.mvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer " + token))
                .andExpect(content().json("{access_token:" + refreshedToken + ",expires_in:100}"));
    }

    @Test
    public void shouldNotRefreshExpiredWebToken() throws Exception {
        Date beforeSomeTime = new Date(DateUtil.now().getTime() - 15 * 1000);
        when(timeProviderMock.now()).thenReturn(beforeSomeTime);

        String token = createToken();
        this.mvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer " + token))
                .andExpect(content().json("{access_token:null,expires_in:null}"));
    }

    @Test
    public void shouldRefreshExpiredMobileToken() throws Exception {
        Date beforeSomeTime = new Date(DateUtil.now().getTime() - 15 * 1000);
        when(timeProviderMock.now()).thenReturn(beforeSomeTime);

        String token = createToken();
        this.mvc.perform(post("/auth/refresh").header("Authorization", "Bearer " + token))
                .andExpect(content().json("{access_token:null,expires_in:null}"));
    }

    private String createToken() {
        return tokenHelper.generateToken(TEST_USERNAME);
    }
}
