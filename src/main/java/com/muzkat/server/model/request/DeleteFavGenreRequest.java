package com.muzkat.server.model.request;

import lombok.Data;

@Data
public class DeleteFavGenreRequest {
    private String login;
    private String genreName;
}
