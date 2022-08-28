package jpa.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jpa.dto.UserDTO;
import jpa.dto.UserTokenStateDTO;
import jpa.model.User;
import jpa.security.TokenUtils;
import jpa.security.auth.JwtAuthenticationRequest;
import jpa.service.CustomUserDetailsService;
import jpa.service.UserService;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response){
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		UserTokenStateDTO userTokenStateDTO = new UserTokenStateDTO(jwt, expiresIn);
		
		return ResponseEntity.ok(userTokenStateDTO);
	}
	
	
	/** Metoda koja vraca rolu usera na osnovu tokena*/
	@GetMapping("/getUserRole")
	public ResponseEntity<String> getUserByToken(HttpServletRequest request) {
		String jwtToken = tokenUtils.getToken(request);
		String username = tokenUtils.getUsernameFromToken(jwtToken);
		User loggedUser = userService.findByUsername(username);
		if (loggedUser != null) {
			String role = loggedUser.getRole();
			return new ResponseEntity<>(role, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	/*
	 * @PostMapping("/signup") public ResponseEntity<User> addUser(@RequestBody
	 * UserDTO userRequest, UriComponentsBuilder ucBuilder) {
	 * 
	 * User existUser = this.userService.findByUsername(userRequest.getUsername());
	 * if(existUser != null) { //throw new
	 * ResourceConflictException(userRequest.getId(), "Username already exists"); }
	 * 
	 * User user = this.userService.save(userRequest); HttpHeaders headers = new
	 * HttpHeaders();
	 * headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.
	 * getId()).toUri()); return new ResponseEntity<>(user, HttpStatus.CREATED); }
	 */
	
	@PostMapping(value = "/refresh")
	public ResponseEntity<UserTokenStateDTO> refreshAuthenticationToken(HttpServletRequest request) {
		
		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		
		if(this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = tokenUtils.refreshToken(token);
			int expiresIn = tokenUtils.getExpiredIn();
			
			return ResponseEntity.ok(new UserTokenStateDTO(refreshedToken, expiresIn));
		} else {
			UserTokenStateDTO userTokenState = new UserTokenStateDTO();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}
	
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> changePassword (@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
}
