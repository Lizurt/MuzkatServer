package com.muzkat.server.model.request;

import lombok.Data;

@Data
public class GetMatchingMusicRequest {
    private String login;
    private int page;
    private int amount;
}
