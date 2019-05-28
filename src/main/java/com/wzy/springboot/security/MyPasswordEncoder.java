package com.wzy.springboot.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 添加该文件是为了解决 There is no PasswordEncoder mapped for the id "null"的问题
 *
 * @author wzy
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
