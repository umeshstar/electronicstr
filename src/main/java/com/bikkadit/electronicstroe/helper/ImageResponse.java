package com.bikkadit.electronicstroe.helper;

import lombok.*;
import org.springframework.http.HttpStatus;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {
        private String imageName;
        private String message;
        private boolean success;
        private HttpStatus status;
    }


