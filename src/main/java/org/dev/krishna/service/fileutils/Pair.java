package org.dev.krishna.service.fileutils;


import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Pair<K,V> {
    private FileInfo left;
    private FileInfo right;
}
