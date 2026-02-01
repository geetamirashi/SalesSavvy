package com.example.demo.adminService;

import com.example.demo.entity.User;

public interface AdminUserService {
	  public User modifyUser(Integer userId, String username, String email, String role) ;
	  public User getUserById(Integer userId);
        

}
