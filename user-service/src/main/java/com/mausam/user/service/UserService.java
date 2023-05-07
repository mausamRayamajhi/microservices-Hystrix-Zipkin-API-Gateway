package com.mausam.user.service;

import com.mausam.user.VO.Department;
import com.mausam.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

//@Service
//@Slf4j
//@AllArgsConstructor(onConstructor_ = {@Autowired})
public interface UserService {


//    RestTemplate restTemplate;
//
//    private UserRepository userRepository;
//
//
//    public User saveUser(User user) {
//        log.info("Inside saveUser of UserService");
//        return userRepository.save(user);
//    }
//
//    public User getUser(Long id) {
//        return userRepository.findByUserId(id);
//    }
//
//    public List<User> getAllUser() {
//        return userRepository.findAll();
//    }
//
//    public ResponseTemplateVO getUserWithDepartment(Long userId) {
//        log.info("Inside getUserWithDepartment of UserService");
//        ResponseTemplateVO vo = new ResponseTemplateVO();
//        User user = userRepository.findByUserId(userId);
//        try {
//            Department department =
//                    restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId()
//                            , Department.class);
//            vo.setDepartment(department);
//        } catch (Exception e) {
//            //  Block of code to handle errors
//            // TODO: 6/05/2023 HERE HANDLE ERROR CASE
//        }
//
//
//        vo.setUser(user);
//
//
//        return vo;
//    }

    public Department getDepartmentByUserId(Long userId);

    /*
     * Method check if user is verified or not email and status must be sent as
     * argument status = 0 (for inactive user) , 1 = for valid and active user
     */
    User findByEmailAndStatus(String email, int status);

    /*
     * Method check if user is verified or not email and status must be sent as
     * argument status = 0 (for inactive user) , 1 = for valid and active user
     */
    User findByEmail(String email);

    /*
     * Method return User Object from given ID ID must be passed as argument ID
     * must be long
     */
    User getUserInfo(long id);

    /*
     * Method Add User User must be provided as argument Anyone can access
     * this method for registration of new User But when ADMIN add new STAFF or
     * ADMIN he needs token to access this method
     *
     */
    long addUser(User user);

    /*
     * Method updates the information of User Must have token to access this method
     * Method is restricted to update the information of other user by spring
     * security
     */
    @PreAuthorize("#user.id == authentication.principal.id")
    User updateUser(User jwtUser);

    /*
     * Method delete the User from DB Must have valid token to access this method
     * Only ADMIN can access this method ID of User must be provided to delete from
     * DB ID must be of type long
     */
    @Secured("ROLE_ADMIN")
    boolean deleteUser(long userId);

    /*
     * Method Set status = 1 to mark user as verified user, after email verification
     * ID must be provide as only argument ID must be of type long
     */
    boolean verifyUser(long id);

    /*
     * Method Get Authenticated userId , on the basis on applicationContext Must
     * have token Only ADMIN, USER AND STAFF can access this method
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER", "ROLE_STAFF"})
    long authUserId();

    /*
     * Method return all active and verified USER whose status = 1 status is set to
     * 1 automatically ROLE must be provided as and only argument
     *
     */
    @Secured("ROLE_ADMIN")
    List<User> findByRoleAndStatus(String role, int status);

    @Secured({"ROLE_ADMIN", "ROLE_STAFF"})
    Page<User> findAllUser(PageRequest pageRequest);

    @Secured({"ROLE_ADMIN", "ROLE_STAFF"})
    Page<User> findByStatus(int status, PageRequest pageRequest);

    @Secured({"ROLE_ADMIN", "ROLE_STAFF"})
    Page<User> findByRole(String role, PageRequest pageRequest);


    long SendEmail(User user);

    long addNewCustomer(User user);

    @Secured("ROLE_ADMIN")
    long addNewAdminStaffDelivery(User user);

    // Below Method Are For Forget Password Use Only
    User setVerificationCode(User user);

    User findByEmailAndVerificationAndId(String email, String verificationCode, long id);
}
