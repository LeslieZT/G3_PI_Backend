package com.dh.demo.utils;

public interface IDataConversor {
    <T> T getData(String json, Class<T> clase);
}
