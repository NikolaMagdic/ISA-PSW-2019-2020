package jpa.dto;

public class UserDTO {

	private Long id;
	
	private String username;
	
	private String role;
	
	private UserTokenStateDTO userTokenStateDTO;

	public UserDTO() {
		
	}
	
	public UserDTO(Long id, String username, String role, UserTokenStateDTO userTokenStateDTO) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
		this.userTokenStateDTO = userTokenStateDTO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserTokenStateDTO getUserTokenStateDTO() {
		return userTokenStateDTO;
	}

	public void setUserTokenStateDTO(UserTokenStateDTO userTokenStateDTO) {
		this.userTokenStateDTO = userTokenStateDTO;
	}


}
