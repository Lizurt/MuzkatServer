package com.muzkat.server.model.request;

import lombok.Data;

@Data
public class AddMusicRequest {
    private String musicName;
    private String authorName;
    private String genreName;
}
