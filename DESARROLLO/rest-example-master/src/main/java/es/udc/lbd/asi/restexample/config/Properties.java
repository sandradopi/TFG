package es.udc.lbd.asi.restexample.config;

 import org.springframework.boot.context.properties.ConfigurationProperties;
 import org.springframework.stereotype.Component;

 @Component
 @ConfigurationProperties(prefix = "properties")
 public class Properties {
     private String clientHost;
     private String jwtSecretKey;
     private Long jwtValidity;
     private String resourcePath;
     private String resourcePathSport;

     
     public String getClientHost() {
         return clientHost;
     }

     public void setClientHost(String clientHost) {
         this.clientHost = clientHost;
     }
     public String getJwtSecretKey() {
         return jwtSecretKey;
     }

     public void setJwtSecretKey(String jwtSecretKey) {
         this.jwtSecretKey = jwtSecretKey;
     }

     public Long getJwtValidity() {
         return jwtValidity;
     }

     public void setJwtValidity(Long jwtValidity) {
         this.jwtValidity = jwtValidity;
     }
     public String getResourcePath() {
 		return resourcePath;
 	}

 	public void setResourcePath(String resourcePath) {
 		this.resourcePath = resourcePath;
 	}

	public String getResourcePathSport() {
		return resourcePathSport;
	}

	public void setResourcePathSport(String resourcePathSport) {
		this.resourcePathSport = resourcePathSport;
	}
 	
 }