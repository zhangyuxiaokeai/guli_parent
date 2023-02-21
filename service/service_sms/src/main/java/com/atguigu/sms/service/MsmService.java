package com.atguigu.sms.service;

import java.util.Map;

public interface MsmService {
    boolean send(String code, String mail);
}
