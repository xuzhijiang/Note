package com.spring.condition.annotation;

import java.util.Arrays;
import java.util.List;

public class MongoUserDAO implements UserDAO {
    @Override
    public List<String> getAllUserNames() {
        return Arrays.asList("MongoUserDAO","James","Bond");
    }
}