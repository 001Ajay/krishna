package org.dev.krishna.service.fileutils;

import lombok.*;


@Builder
@ToString
@Getter
public class FileInfo {
    private String name;
    private String extension;
    private String size;
    private String location;
    private String path;
}
