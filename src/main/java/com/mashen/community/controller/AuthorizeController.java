package com.mashen.community.controller;

import com.mashen.community.dto.AccesstokenDto;
import com.mashen.community.dto.GithubUser;
import com.mashen.community.mapper.UserMapper;
import com.mashen.community.model.User;
import com.mashen.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author 陈俊龙
 *
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

    @Value("${git.client.id}")
    private String clientId;
    @Value("${git.client.secret}")
    private String clientSecret;
    @Value("${git.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request){

        AccesstokenDto accesstokenDto = new AccesstokenDto();
        accesstokenDto.setClient_id(clientId);
        accesstokenDto.setClient_secret(clientSecret);
        accesstokenDto.setRedirect_uri(redirectUri);
        accesstokenDto.setCode(code);
        accesstokenDto.setState(state);
        String accessToken = githubProvider.getAccesstoken(accesstokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser != null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setGmtCreated(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreated());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
        //System.out.println(user.getName()+user.getBio());
    }
}

