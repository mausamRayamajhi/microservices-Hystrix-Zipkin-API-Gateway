package com.mausam.user.service;

import com.mausam.user.VO.Department;
import com.mausam.user.VO.ResponseTemplateVO;
import com.mausam.user.entity.User;
import com.mausam.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {


    RestTemplate restTemplate;

    private UserRepository userRepository;


    public User saveUser(User user) {
        log.info("Inside saveUser of UserService");
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findByUserId(id);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment of UserService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);
        try {
            Department department =
                    restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId()
                            , Department.class);
            vo.setDepartment(department);
        } catch (Exception e) {
            //  Block of code to handle errors
            // TODO: 6/05/2023 HERE HANDLE ERROR CASE 
        }


        vo.setUser(user);


        return vo;
    }
}
