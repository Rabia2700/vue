package com.isj.gestiondenote.ClientWeb.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.InputStream;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        addMixIn(InputStream.class, InputStreamMixin.class);
    }

    @JsonSerialize
    public static class InputStreamMixin {
        @JsonIgnore
        public int read() { return -1; }
    }
}