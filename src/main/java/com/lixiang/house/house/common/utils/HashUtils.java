package com.lixiang.house.house.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * @author lixiang1234_李祥
 * @site www.lixiang.com
 * @create 2020-03-09 13:07
 */
@SuppressWarnings("all")
public class HashUtils {

    private static final HashFunction FUNCTION = Hashing.md5();
    private static final String SALT = "lixiang";

    public static String encryPassword(String password){
        HashCode hashCode = FUNCTION.hashString(password + SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }

}
