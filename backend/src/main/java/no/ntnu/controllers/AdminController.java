package no.ntnu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.dto.Course;
import no.ntnu.service.AdminService;

/**
 * Controller class for handling administrative actions related to course management.
 */
@RestController
@RequestMapping("/admin")
@Tag(name = "AdminController", description = "API for course management administrative tasks")

public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @Operation(summary = "Add a new course", description = "Adds a new course to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course added successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Failed to add course",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/courses")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        boolean success = adminService.addCourse(course.getName(), course.getDescription(), course.getCreatedBy());
        if (success) {
            return new ResponseEntity<>("Course added successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to add course", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  
    @Operation(summary = "Remove a course", description = "Removes a course from the system by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course removed successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Failed to remove course",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<String> removeCourse(@PathVariable int id) {
        boolean success = adminService.removeCourse(id);
        if (success) {
            return new ResponseEntity<>("Course removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to remove course", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Update a course", description = "Updates the details of an existing course.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Failed to update course",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/courses/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable int id, @RequestBody Course course) {
        boolean success = adminService.updateCourse(id, course.getName(), course.getDescription());
        if (success) {
            return new ResponseEntity<>("Course updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update course", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


}
