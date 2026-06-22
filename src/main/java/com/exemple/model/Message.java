package com.exemple.model;

import java.time.LocalDateTime;

public record Message(
        String user,
        String text,
        LocalDateTime time
) {}
