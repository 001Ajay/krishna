package org.dev.krishna.service.fileutils;

import lombok.*;


@Builder
@ToString
@Data
public class FileInfo {
    private String name;
    private String extension;
    private String size;
    private String location;
    private String path;
}
