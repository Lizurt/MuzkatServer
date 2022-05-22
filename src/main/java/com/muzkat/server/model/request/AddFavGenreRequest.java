package com.muzkat.server.model.request;

import lombok.Data;

@Data
public class AddFavGenreRequest {
    private String login;
    private String genreName;
}
