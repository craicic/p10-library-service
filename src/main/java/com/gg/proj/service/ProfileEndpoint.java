package com.gg.proj.service;

import com.gg.proj.business.ProfileManager;
import com.gg.proj.business.UserManager;
import com.gg.proj.service.books.ServiceStatus;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import com.gg.proj.service.exceptions.ServiceFaultException;
import com.gg.proj.service.profiles.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.Optional;

@Endpoint
public class ProfileEndpoint {

    private static final Logger log = LoggerFactory.getLogger(ProfileEndpoint.class);

    private static final String NAMESPACE_URI = "http://proj.gg.com/service/profiles";

    private ProfileManager profileManager;

    private UserManager userManager;

    public ProfileEndpoint(ProfileManager profileManager, UserManager userManager) {
        this.profileManager = profileManager;
        this.userManager = userManager;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveProfileRequest")
    @ResponsePayload
    public SaveProfileResponse saveProfile(@RequestPayload SaveProfileRequest request) {
        log.debug("saveProfile : calling the ProfileManager to save profile");
        SaveProfileResponse saveProfileResponse = new SaveProfileResponse();
        try {
            saveProfileResponse.setUser(profileManager.save(request.getUser(), request.getTokenUUID()));
            log.info("hey after SetUser");
        } catch (InvalidTokenException e){
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Invalid connection");
            serviceStatus.setStatusCode("ERROR");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        } catch (OutdatedTokenException e) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Outdated token");
            serviceStatus.setStatusCode("RETRY_LOGIN");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        }
        return saveProfileResponse;
    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteProfileRequest")
//    @ResponsePayload
//    public DeleteProfileResponse deleteProfile(@RequestPayload DeleteProfileRequest request) {
//        profileManager.delete(request.getUser(), request.getTokenUUID());
//        return new DeleteProfileResponse();
//    }
//
//
//    /**
//     * This method takes a request, then build a response : it calls the business to get a profile by id.
//     *
//     * @param request a GetProfileRequest from the network
//     * @return a GetProfileResponse.
//     */
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProfileRequest")
//    @ResponsePayload
//    public GetProfileResponse getProfile(@RequestPayload GetProfileRequest request) throws ServiceFaultException {
//        log.debug("getProfile : calling the ProfileManager to fetch a profile by id");
//        GetProfileResponse response = new GetProfileResponse();
//        Optional<User> opt = profileManager.findById(request.getId(), request.getTokenUUID());
//        opt.ifPresent(response::setUser);
//        return response;
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllProfilesRequest")
//    @ResponsePayload
//    public ListAllProfilesResponse listAllProfiles(@RequestPayload ListAllProfilesRequest request) {
//        ListAllProfilesResponse response = new ListAllProfilesResponse();
//        List<User> users = response.getUsers();
//        users.addAll(profileManager.findAll(request.getTokenUUID()));
//        return response;
//    }
}
