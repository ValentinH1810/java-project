package org.home.hotelmanagementapi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTaskToStaffDto {

    private String taskName;
    private Long staffId;

}
