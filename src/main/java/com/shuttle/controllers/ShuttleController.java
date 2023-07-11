package com.shuttle.controllers;

import com.shuttle.models.DriverShuttle;
import com.shuttle.models.StudentShuttle;
import com.shuttle.payload.request.AddPassangerRequest;
import com.shuttle.payload.request.ShuttleLocationRequest;
import com.shuttle.payload.response.MessageResponse;
import com.shuttle.repository.DriverShuttleRepository;
import com.shuttle.repository.StudentShuttleRepository;
import com.shuttle.repository.UserRepository;
import com.shuttle.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class ShuttleController {
    @Autowired
    StudentShuttleRepository studentShuttleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DriverShuttleRepository driverShuttleRepository;
    @Autowired
    JwtUtils jwtUtils;

    @Value("${univerity.longitude}")
    private double universityLongitude;

    @Value("${univerity.latitude}")
    private double universitylatitude;

    @PostMapping("/addPassenger")
    @PreAuthorize("hasRole('DRIVER') or hasRole('ADMIN')")
    public ResponseEntity<?> addPassenger(@Valid @RequestBody AddPassangerRequest addPassangerRequest) {
        if (userRepository.findByUsername(addPassangerRequest.getStudentUsername()).isPresent()) {
            StudentShuttle studentShuttle = new StudentShuttle();
            studentShuttle.setAddress(addPassangerRequest.getAddress());
            studentShuttle.setStatus("TRUE");
            studentShuttle.setUser(userRepository.findByUsername(addPassangerRequest.getStudentUsername()).get());
            studentShuttleRepository.save(studentShuttle);
            return ResponseEntity.ok(new MessageResponse("Passenger is added in Shuttle"));
        } else {
            return ResponseEntity.ok(new MessageResponse("Passenger " + addPassangerRequest.getStudentUsername() + " Not Found"));
        }
    }

    @PostMapping("/shuttleLocation")
    @PreAuthorize("hasRole('DRIVER') or hasRole('ADMIN')")
    public ResponseEntity<?> shuttleLocation(@Valid @RequestBody ShuttleLocationRequest shuttleLocationRequest, HttpServletRequest request) {
      try {
          String headerAuth = request.getHeader("Authorization");
          String username=null;
          if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            username=jwtUtils.getUserNameFromJwtToken(headerAuth.substring(7, headerAuth.length()));
          }
          System.out.println("Username =>"+username);
          DriverShuttle driverShuttle = new DriverShuttle();
          DriverShuttle driverShuttle2 = new DriverShuttle();
          driverShuttle.setLatitude1(shuttleLocationRequest.getLatitude1());
          driverShuttle.setLongitude1(shuttleLocationRequest.getLongitude1());
          driverShuttle2.setLatitude2(shuttleLocationRequest.getLatitude2());
          driverShuttle2.setLongitude2(shuttleLocationRequest.getLongitude2());
          double lat1 = driverShuttle.getLatitude1();
          double lon1 = driverShuttle.getLongitude1();
          double lat2 = driverShuttle2.getLatitude2();
          double lon2 = driverShuttle2.getLongitude2();

          driverShuttle.setUser(userRepository.findByUsername(username).get());
          driverShuttle2.setUser(userRepository.findByUsername(username).get());
          driverShuttleRepository.save(driverShuttle);
          driverShuttleRepository.save(driverShuttle2);
          return ResponseEntity.ok(new MessageResponse("Shuttle Location added."));
      }catch (Exception exception){
          return ResponseEntity.ok(new MessageResponse("Issue with Shuttle Location"));
      }
    }

    @PostMapping("/checkETA")
    @PreAuthorize("hasRole('DRIVER') or hasRole('ADMIN')")
    public ResponseEntity<?> shuttleLocationETA() {
        try {

            DriverShuttle driverShuttle=driverShuttleRepository.findTopByOrderByIdDesc();
            DriverShuttle driverShuttle2 = driverShuttleRepository.findTopByOrderByIdDesc();
            if((driverShuttle.getLatitude1() == universitylatitude && driverShuttle.getLongitude1() == universityLongitude) ||
                    (driverShuttle2.getLatitude2() == universitylatitude && driverShuttle2.getLongitude2() == universityLongitude)){
                return ResponseEntity.ok(new MessageResponse("0"));
            }
            else{
                return ResponseEntity.ok(new MessageResponse("Service unavailable"));
            }
        }catch (Exception exception){
            return ResponseEntity.ok(new MessageResponse("Issue with Shuttle ETA"));
        }
    }
}
