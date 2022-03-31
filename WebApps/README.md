Estas son las dependencias necesarias para una webapp
	
	<dependency>
	    <groupId>com.google.code.gson</groupId>
	    <artifactId>gson</artifactId>
	    <version>2.9.0</version>
	</dependency>

	<dependency>
	    <groupId>javax</groupId>
	    <artifactId>javaee-web-api</artifactId>
	    <version>8.0.1</version>
	    <scope>provided</scope>
	</dependency>
   
	<!-- https://mvnrepository.com/artifact/org.glassfish.jersey.bundles/jaxrs-ri -->
	<dependency>
	    <groupId>org.glassfish.jersey.bundles</groupId>
	    <artifactId>jaxrs-ri</artifactId>
	    <version>3.0.4</version>
	</dependency>
	
	<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
	<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>8.0.15</version>
	</dependency>

## Para Mac y Ubuntu	
Despues de descargar el Tomcat, debe pararse en la consola y entrar en la carpeta bin de tomcat. Luego corra el siguiente comando:
chmod +x *.sh
