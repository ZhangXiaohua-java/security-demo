package cn.edu.huel.service.impl;

import cn.edu.huel.domain.User;
import cn.edu.huel.mapper.UserMapper;
import cn.edu.huel.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ZhangXiaoHua
 * @description 针对表【acl_user(用户表)】的数据库操作Service实现
 * @createDate 2023-02-16 21:50:30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
		implements UserService {

}




