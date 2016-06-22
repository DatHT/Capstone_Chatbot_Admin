package com.psib.constant;

public class Role {

	public enum ROLE {
		STAFF("STAFF"), ADMIN("ADMIN");

		private String value;

		public String getValue() {
			return value;
		}

		private ROLE(String value) {
			this.value = value;
		}
	}
}
