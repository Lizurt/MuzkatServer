package com.muzkat.server.model.request;

import lombok.Data;

@Data
public class DeleteFavAuthorRequest {
    private String login;
    private String authorName;
}
