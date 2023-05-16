package com.SmartPannel.userData.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class fileUpload {
    private String filename;
    private String downloadUri;
    private long size;

}
