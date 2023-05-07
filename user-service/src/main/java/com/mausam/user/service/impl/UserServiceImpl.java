package com.mausam.user.service.impl;


import com.mausam.user.VO.Department;
import com.mausam.user.dao.UserDAO;
import com.mausam.user.entity.User;
import com.mausam.user.service.UserService;
import com.mausam.user.util.Encryptor;
import com.mausam.user.util.MailUtil;
import com.mausam.user.util.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(UserDAO user, RestTemplate restTemplate) {
        this.userDAO = user;
        this.restTemplate = restTemplate;

    }

    public long authUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long userId = ((User) authentication.getPrincipal()).getUserId();
        System.out.println("Token == " + ((User) authentication.getPrincipal()).getToken());
        return userId;
    }


    @Override
    public Department getDepartmentByUserId(Long userId) {

        try {
            Department department =
                    restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + userId
                            , Department.class);

            return department;
        } catch (Exception e) {
            //  Block of code to handle errors
            // TODO: 6/05/2023 HERE HANDLE ERROR CASE
            return null;
        }

    }

    @Override
    public User findByEmailAndStatus(String email, int status) {
        if (email.equals("noEmail")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof User) {
                User userDetails = (User) authentication.getPrincipal();


                email = userDetails.getEmail();
                System.out.println("Authenticated User Email = " + email);

            }
        }

        return userDAO.findByEmailAndStatus(email, status);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public Page<User> findAllUser(PageRequest pageRequest) {
        return userDAO.findAllUser(pageRequest);
    }

    @Override
    public Page<User> findByStatus(int status, PageRequest pageRequest) {
        return userDAO.findByStatus(status, pageRequest);
    }

    @Override
    public Page<User> findByRole(String role, PageRequest pageRequest) {
        return userDAO.findByRole(role, pageRequest);
    }


    @Override
    public User getUserInfo(long id) {
        Optional<User> user = userDAO.findById(id);
        boolean present = user.isPresent();
        if (present) return user.get();
        return new User();
    }

    @Override
    public long addUser(User user) {
        User byEmail = userDAO.findByEmail(user.getEmail());
        long newUserId = 0;
        if (byEmail != null) {
            System.out.println(" User exite and ID = " + byEmail.getUserId());
        } else {
            System.out.println(" User does not exite");

            user.setPassword(Encryptor.hashCode(user.getPassword()));
            Random rand = new Random();
            String hashCode = Integer.toString(rand.nextInt(2000));
            user.setVerification(hashCode);
            User user1 = userDAO.save(user);
            if (user1 != null) newUserId = user1.getUserId();
        }

        return newUserId;
    }

    @Override
    public User updateUser(User user) {
        if (user.getPassword().charAt(0) == '$' && user.getPassword().length() == 60) {
            System.out.println("COntained hash pasword");
            //System.out.println("Password size = "+user.getPassword().length());
        } else {
            user.setPassword(Encryptor.hashCode(user.getPassword()));
            System.out.println("Does not contain hash pasword");
            System.out.println("Password size AFter = " + user.getPassword().length());
        }
        User userInfo = getUserInfo(user.getUserId());
        if (userInfo != null) return userDAO.save(user);
        return new User();

    }

    @Override
    public boolean deleteUser(long userId) {
        User userInfo = getUserInfo(userId);
        boolean deleted = false;
        if (userId == userInfo.getUserId()) {
            userDAO.deleteById(userId);
            deleted = true;
        }
        return deleted;

    }

    @Override
    public boolean verifyUser(long id) {
        User userInfo = getUserInfo(id);
        boolean verifyUser = false;
        if (userInfo != null) {
            userInfo.setStatus(1);
            userDAO.save(userInfo);
            verifyUser = true;
        }
        return verifyUser;
    }

    @Override
    public List<User> findByRoleAndStatus(String role, int status) {
        return userDAO.findByRoleAndStatus(role, status);
    }

    @Override
    public long addNewCustomer(User user) {
        user.setRole("ROLE_USER");
        user.setStatus(0);
        return SendEmail(user);
    }

    @Override
    public User setVerificationCode(User existedUser) {
        return userDAO.save(existedUser);
    }

    @Override
    public User findByEmailAndVerificationAndId(String email, String verificationCode, long id) {
        return userDAO.findByEmailAndVerificationAndUserId(email, verificationCode, id);
    }


    @Override
    public long addNewAdminStaffDelivery(User user) {

        long authId = authUserId();
        User adminUser = getUserInfo(authId);
        String fullName = adminUser.getFirstName() + adminUser.getLastName();

        long returnedId = 0;
        if (adminUser.getRole().equals("ROLE_ADMIN")) {
            switch (user.getVerification()) {
                case SecurityConstant.ADD_DELIVERY:
                    user.setRole("ROLE_DELIVERY");
                    break;
                case SecurityConstant.ADD_OPERATOR:
                    user.setRole("ROLE_OPERATOR");
                    break;
                case SecurityConstant.ADD_ADMIN:
                    user.setRole("ROLE_ADMIN");
                    break;
                default:
                    user.setRole("UNDEFINED");
                    System.out.println("ATTEMPT TO HACK !!");
                    break;
            }

            user.setStatus(0);
            if (!user.getRole().equals("UNDEFINED")) {
                returnedId = SendEmail(user);
            } else {
                List<User> allActive = findByRoleAndStatus("ROLE_ADMIN", 1);
                for (User user2 : allActive) {
                    String message_to_user = "There was unusual activity done by <h3>" + fullName + "</h3>His id is <strong>" + adminUser.getUserId() + "</strong>. His email address is <strong>" + adminUser.getEmail() + "</strong>.<br>He performed to add new person with out of his authority at " + LocalDateTime.now() + "<br> <strong>" + fullName + "</strong> try to add following <strong>information</strong>:<br><br>Name : " + fullName + "<br>Email : " + user.getEmail();

                    String message_subject_to_user = "ATTEMPT TO HACK OR MISSUSED AUTHORITY !!";

                    Thread t = new Thread(() -> {
                        try {
                            MailUtil.sendEmail(user2.getEmail(), message_to_user, message_subject_to_user);
                        } catch (MessagingException e) {

                            e.printStackTrace();
                        }
                    });
                    t.start();
                }
            }
        }
        return returnedId;
    }

    @Override
    public long SendEmail(User user) {
        long returnedId;
        returnedId = addUser(user);
        if (returnedId > 0) {
            User emailModel = findByEmail(user.getEmail());
            try {

                if (emailModel.getEmail().equalsIgnoreCase(user.getEmail())) {

                    String message_to_user = "To activate your account click the given link below : <br><br> <br>   Link =     "
                            + "http://localhost:8081/?email=" + emailModel.getEmail() + ""
                            + "&code=" + emailModel.getVerification() + "&id=" + emailModel.getUserId() + "<br> ";

                    String message_subject_to_user = "Activate  Account.";

                    MailUtil.sendEmail(emailModel.getEmail(), message_to_user, message_subject_to_user);
                    System.out.println("Email matched");


                    MailUtil.sendEmail(emailModel.getEmail(), message_to_user, message_subject_to_user);

                }
            } catch (Exception e) {

                System.out.println("******************  Email  does not match with our database   **************************");

            }

        }
        return returnedId;
    }


}
