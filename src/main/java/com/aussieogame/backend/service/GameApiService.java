package com.aussieogame.backend.service;

import com.aussieogame.backend.dto.ApiOkResponse;
import org.springframework.stereotype.Service;


@Service
public class GameApiService {
    public ApiOkResponse getProtectedResource() {
        String somethingProtected = "this should only be available to authenticated users";

        return ApiOkResponse.from(somethingProtected);
    }

    public ApiOkResponse getPublicResource() {
        String somethingPublic = "this is available to everyone";

        return ApiOkResponse.from(somethingPublic);
    }
}
