package com.muzkat.server.model.request;

import lombok.Data;

@Data
public class AddFavAuthorRequest {
    private String login;
    private String authorName;
}
