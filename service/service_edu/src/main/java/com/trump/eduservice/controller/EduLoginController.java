package com.trump.eduservice.controller;

import com.trump.commonutils.R;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    @PostMapping("/login")
    public R login( ){
        Map<String,Object> map = new HashMap<>();
        map.put("token", "admin");
        return R.ok().data(map);
    }
    @GetMapping("/info")
    public R info(){
        Map<String,Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("name", "admin");
        map.put("avatar", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597485632987&di=2804ccdc0ca35776e90f0716ad8453af&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3Da9714efaaf86c91708035231f93c70c6%2Fddd3ab59d109b3dea0394e6ac4bf6c81810a4c48.jpg");
        return R.ok().data(map);
    }
}
