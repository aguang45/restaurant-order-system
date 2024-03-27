package com.restaurant.web.controller.system;

import com.restaurant.common.core.domain.entity.SysRole;
import com.restaurant.common.core.domain.entity.SysUser;
import com.restaurant.common.utils.bean.BeanUtils;
import com.restaurant.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.restaurant.common.core.controller.BaseController;
import com.restaurant.common.core.domain.AjaxResult;
import com.restaurant.common.core.domain.model.RegisterBody;
import com.restaurant.common.utils.StringUtils;
import com.restaurant.framework.web.service.SysRegisterService;
import com.restaurant.system.service.ISysConfigService;

import java.util.List;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysUserService userService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);

        if (StringUtils.isEmpty(msg))
        {
            //        给与普通用户角色
            SysUser sysUser = new SysUser();
            SysRole role = new SysRole();
            role.setRoleId(2L);
            role.setRoleKey("common");
            role.setRoleName("普通用户");
            role.setRoleSort(2);
            role.setStatus("0");
            List<SysRole> roles = List.of(role);

            SysUser sysUser1 = userService.selectUserByUserName(user.getUsername());
            BeanUtils.copyBeanProp(sysUser, sysUser1);
            sysUser.setRoleId(2L);
            sysUser.setDeptId(112L);
            sysUser.setPostIds(new Long[]{4L});
            sysUser.setRoleIds(new Long[]{2L});
            sysUser.setRoles(roles);
            int i = userService.updateUser(sysUser);
            if (i > 0) {
                return success("注册成功");
            } else {
                return error("注册失败");
            }
        }
        return StringUtils.isEmpty(msg) ? success() : error(msg);

    }
}
