package application.controller;

import dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.interfaces.user.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addProject(@RequestBody User user){
       // logger.debug("Invocation de la resource : POST /User/");
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

 /*   @RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateProject(@RequestBody User project,
                                              @PathVariable("projectId") Long projectId){
        logger.debug("Invocation de la resource : PUT /project/{projectId}");
        try{
            clientInfoService.updateProject(projectId, project);
        }catch (NoResultException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }*/



}
